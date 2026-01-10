/**
 * Copyright 2016 SPeCS.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package weaver.utils.weaving;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import pt.up.fe.specs.util.SpecsCollections;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.ACall;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.kadabra.abstracts.joinpoints.AVar;
import weaver.kadabra.joinpoints.*;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.scanners.NodeConverter;
import weaver.utils.scanners.NodeSearcher;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SelectUtils {

    /**
     * Select an element of type J, from a starting element, and convert the element
     * into a join point of type J
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
     * Select an element of type J, from a starting element, and convert the element
     * into a join point of type J
     * 
     * @param startNode
     * @param searchClass
     * @param converter
     * @param ignore      sub-types that should not be selected
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

    public static <T extends CtElement, V extends AJavaWeaverJoinPoint> List<V> node2JoinPointList(T element,
            NodeConverter<T, V> converter) {

        final V joinPoint = SelectUtils.node2JoinPoint(element, converter);
        final List<V> joinPoints = SpecsCollections.newArrayList();
        joinPoints.add(joinPoint);

        return joinPoints;
    }

    public static List<AExpression> expression2JoinPointList(
            CtExpression<?> element, JavaWeaver weaver) {

        final AExpression joinPoint = SelectUtils.expression2JoinPoint(element, weaver);
        final List<AExpression> joinPoints = SpecsCollections.newArrayList();
        joinPoints.add(joinPoint);
        return joinPoints;
    }

    public static AExpression expression2JoinPoint(CtExpression<?> element, JavaWeaver weaver) {
        return JExpression.newInstance(element, weaver);
    }

    public static AJavaWeaverJoinPoint statement2JoinPoint(CtStatement element, JavaWeaver weaver) {
        return JStatement.newInstance(element, weaver);
    }

    public static <T extends CtElement, V extends AJavaWeaverJoinPoint> V node2JoinPoint(T element,
            NodeConverter<T, V> converter) {

        return converter.toJoinPoint(element);
    }

    public static List<? extends AVar> selectVar(CtStatement node, JavaWeaver weaver) {
        List<Class<? extends CtElement>> ignoreTypes = SpecsCollections.newArrayList();
        List<Class<? extends CtElement>> prune = SpecsCollections.newArrayList();
        List<AVar> select = select(node, CtVariableAccess.class, (var -> JVar.newInstance(var, weaver)), ignoreTypes,
                prune);
        return select;

    }

    public static List<? extends ACall> selectCall(CtElement node, JavaWeaver weaver) {
        final List<JCall<?>> calls = SelectUtils.select(node, CtInvocation.class,
                (call -> JCall.newInstance(call, weaver)));

        // Filter constructors
        return calls.stream()
                .filter(call -> !call.getNameImpl().equals("<init>"))
                .collect(Collectors.toList());
    }

    public static List<JLibClass> selectLibClasses(JApp app) {
        Set<ClassInfo> allClassesInfo = getAllClassesInfo(app);
        List<JLibClass> libClasses = allClassesInfo.stream()
                .map(libClass -> JLibClass.newInstance(libClass, app.getWeaverEngine()))
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
