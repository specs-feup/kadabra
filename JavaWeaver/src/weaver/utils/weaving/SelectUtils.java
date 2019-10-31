/**
 * Copyright 2016 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

package weaver.utils.weaving;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import pt.up.fe.specs.util.SpecsCollections;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.ACall;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.kadabra.abstracts.joinpoints.AVar;
import weaver.kadabra.joinpoints.JApp;
import weaver.kadabra.joinpoints.JCall;
import weaver.kadabra.joinpoints.JExpression;
import weaver.kadabra.joinpoints.JLibClass;
import weaver.kadabra.joinpoints.JStatement;
import weaver.kadabra.joinpoints.JVar;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.scanners.NodeConverter;
import weaver.utils.scanners.NodeSearcher;

public class SelectUtils {

    /**
     * Select an element of type J, from a starting element, and convert the element into a join point of type J
     * 
     * @param startNode
     * @param searchClass
     * @param converter
     * @return
     */
    public static <J extends CtElement, JP extends AJavaWeaverJoinPoint> List<JP> select(CtElement startNode,
            Class<J> searchClass, NodeConverter<J, JP> converter) {
        return SelectUtils.select(startNode, searchClass, converter, Collections.emptyList(), Collections.emptyList());

    }

    /**
     * Select an element of type J, from a starting element, and convert the element into a join point of type J
     * 
     * @param startNode
     * @param searchClass
     * @param converter
     * @param ignore
     *            sub-types that should not be selected
     * @return
     */
    public static <J extends CtElement, JP extends AJavaWeaverJoinPoint> List<JP> select(CtElement startNode,
            Class<J> searchClass, NodeConverter<J, JP> converter, Collection<Class<? extends CtElement>> ignore,
            Collection<Class<? extends CtElement>> prune) {
        return NodeSearcher.searchAndConvert(searchClass, startNode, converter, ignore, prune);

    }

    /**
     * Convert a list of Spoon nodes to a list of elements of type T
     * 
     * @param elements
     * @param converter
     * @return
     */
    public static <T extends CtElement, V extends AJavaWeaverJoinPoint> List<V> nodeList2JoinPointList(
            Collection<T> elements,
            NodeConverter<T, V> converter) {

        final List<V> joinPoints = elements.stream().map(e -> converter.toJoinPoint(e)).collect(Collectors.toList());

        return joinPoints;
    }

    /**
     * Convert a list of {@link CtStatement} to a list of join points of type {@link AStatement}
     * 
     * @param elements
     * @param converter
     * @return
     */
    public static List<AStatement> statementList2JoinPointList(
            Collection<CtStatement> elements) {

        final List<AStatement> joinPoints = elements.stream().map(SelectUtils::statement2JoinPoint)
                .collect(Collectors.toList());

        return joinPoints;
    }

    public static <T extends CtElement, V extends AJavaWeaverJoinPoint> List<V> node2JoinPointList(T element,
            NodeConverter<T, V> converter) {

        final V joinPoint = SelectUtils.node2JoinPoint(element, converter);
        final List<V> joinPoints = SpecsCollections.newArrayList();
        joinPoints.add(joinPoint);

        return joinPoints;
    }

    public static List<AStatement> statement2JoinPointList(
            CtStatement statement) {

        final AStatement joinPoint = SelectUtils.statement2JoinPoint(statement);
        final List<AStatement> joinPoints = SpecsCollections.newArrayList();
        joinPoints.add(joinPoint);
        return joinPoints;
    }

    public static List<AExpression> expression2JoinPointList(
            CtExpression<?> element) {

        final AExpression joinPoint = SelectUtils.expression2JoinPoint(element);
        final List<AExpression> joinPoints = SpecsCollections.newArrayList();
        joinPoints.add(joinPoint);
        return joinPoints;
    }

    public static AExpression expression2JoinPoint(CtExpression<?> element) {

        return JExpression.newInstance(element);
    }

    public static AStatement statement2JoinPoint(CtStatement element) {

        return JStatement.newInstance(element);
    }

    public static <T extends CtElement, V extends AJavaWeaverJoinPoint> V node2JoinPoint(T element,
            NodeConverter<T, V> converter) {

        return converter.toJoinPoint(element);
    }

    public static List<? extends AVar> selectVar(CtStatement node) {
        // List<Class<? extends CtElement>> ignoreTypes = SpecsCollections.newArrayList();
        // ignoreTypes.add(CtFieldReference.class);
        List<Class<? extends CtElement>> ignoreTypes = SpecsCollections.newArrayList();
        // ignoreTypes.add(CtFieldReference.class);
        List<Class<? extends CtElement>> prune = SpecsCollections.newArrayList();
        // prune.add(CtFieldAccess.class);
        List<AVar> select = select(node, CtVariableAccess.class, JVar::newInstance, ignoreTypes, prune);
        return select;

    }

    public static List<? extends ACall> selectCall(CtElement node) {
        final List<JCall<?>> calls = SelectUtils.select(node, CtInvocation.class, JCall::newInstance);

        // Filter constructors
        return calls.stream()
                .filter(call -> !call.getNameImpl().equals("<init>"))
                .collect(Collectors.toList());
    }

    public static List<JLibClass> selectLibClasses(JApp app) {
        Set<ClassInfo> allClassesInfo = getAllClassesInfo(app);
        List<JLibClass> libClasses = allClassesInfo.stream().map(JLibClass::newInstance)
                .collect(Collectors.toList());
        return libClasses;

    }

    private static Set<ClassInfo> getAllClassesInfo(JApp app) {
        ImmutableSet<ClassInfo> topLevelClasses;
        try {
            topLevelClasses = ClassPath.from(getClassLoaderWithContext(app))
                    .getTopLevelClasses();
            return topLevelClasses;
        } catch (IOException e) {
            KadabraLog.warning("Could not load classpath used for compiling the project. " + e.getMessage());
        }
        return Collections.emptySet();
    }

    private static ClassLoader getClassLoaderWithContext(JApp app) {

        return app.getSpoon().getEnvironment().getInputClassLoader();
    }

}
