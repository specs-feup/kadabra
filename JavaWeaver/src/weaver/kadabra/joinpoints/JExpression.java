/**
 * Copyright 2015 SPeCS.
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

package weaver.kadabra.joinpoints;

import org.lara.interpreter.weaver.interf.JoinPoint;
import spoon.reflect.code.*;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.*;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.SpoonUtils;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.converters.CtExpression2AExpression;

import java.util.Optional;

public class JExpression<T> extends AExpression {

    CtExpression<T> node;
    Integer test = 10;

    public JExpression(CtExpression<T> expr, JavaWeaver weaver) {
        super(weaver);
        node = expr;
    }

    @Override
    public Integer getTestImpl() {
        return test;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + node.getClass().getSimpleName();
    }

    public static <K> AExpression newInstanceDefault(CtExpression<K> expr, JavaWeaver weaver) {
        return new JExpression<>(expr, weaver);
    }

    public static <K> AExpression newInstance(CtExpression<K> expr, JavaWeaver weaver) {
        return CtExpression2AExpression.convertToExpression(expr, weaver);
    }

    @Override
    public void setTestImpl(AExpression value) {
        setTestImpl(value.getLineImpl());
    }

    @Override
    public void setTestImpl(int value) {
        test = value;
    }

    @Override
    public String getKindImpl() {
        return get_class();
    }

    @Override
    public ATypeReference getTypeReferenceImpl() {
        var children = getChildrenArrayImpl();

        if (children.length > 0) {
            var firstChild = children[0];
            // First child should be a TypeReference
            if (firstChild instanceof ATypeReference) {
                return (ATypeReference) firstChild;
            }
        }

        // Fallback
        var type = node.getType();

        if (type == null) {
            KadabraLog.info("Currrent expression, of join point type '" + getJoinPointType()
                    + "', does not have a type defined: '" + getCodeImpl() + "'");
            return null;
        }

        return new JTypeReference<>(type, getWeaverEngine());
    }

    @Override
    public String getTypeImpl() {
        try {
            return getTypeReferenceImpl().toString();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getQualifiedTypeImpl() {
        var typeRef = getTypeReferenceImpl();

        var packageName = typeRef.getPackageNameImpl();
        var sanitizedPackageName = packageName != null ? packageName + "." : "";

        return sanitizedPackageName + getTypeImpl();
    }

    @Override
    public void extractImpl(String varName, AStatement location, String position) {

        Optional<CtStatement> targetO = getTarget(location);

        if (!targetO.isPresent()) {
            throw new JavaWeaverException("Could not get the target location");
        }

        targetO.ifPresent(t -> SpoonUtils.extract(node, varName, t, position, getWeaverEngine()));
    }

    private Optional<CtStatement> getTarget(AStatement location) {
        if (location == null) {
            return SpoonUtils.getAncestorIncludeSelf(node, CtStatement.class);
        }

        Object stmt = location.getNode();
        if (stmt instanceof CtStatement) {
            return Optional.of((CtStatement) stmt);
        }

        return Optional.empty();
    }

    @Override
    public CtExpression<T> getNode() {
        return node;
    }

    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return new AJoinPoint[] { insertImplJExpression(position, code) };
    }

    public AJavaWeaverJoinPoint insertImplJExpression(String position, String code) {
        if (position.equals("replace") || position.equals("around")) {
            return ActionUtils.replaceExpression(position, code, node, getWeaverEngine());
        } else {
            return ActionUtils.insert(position, code, node, getWeaverEngine());
        }
    }

    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint JoinPoint) {
        return new AJoinPoint[] { insertImplJExpression(position, (AJavaWeaverJoinPoint) JoinPoint) };
    }

    public AJavaWeaverJoinPoint insertImplJExpression(String position, AJavaWeaverJoinPoint joinPoint) {
        var ctElement = joinPoint.getNode();

        if (position.equals("replace") || position.equals("around")) {
            if (!(ctElement instanceof CtExpression<?>)) {
                KadabraLog.info("Cannot replace a join point of type " + joinPoint.getJoinPointType()
                        + " inside an expression, it has to be another expression");
                return null;
            }

            CtExpression<?> expression = (CtExpression<?>) ctElement;

            return ActionUtils.replaceExpression(position, expression, node, getWeaverEngine());
        } else {
            return ActionUtils.insert(position, ctElement, node, getWeaverEngine());
        }
    }

    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return insertImplJExpression("before", code);
    }

    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return insertImplJExpression("after", code);
    }

    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return insertImplJExpression("replace", code);
    }
}
