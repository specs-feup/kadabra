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

import java.util.Optional;

import org.lara.interpreter.weaver.interf.enums.InsertPosition;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.ATypeReference;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.SpoonUtils;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.converters.CtExpression2AExpression;

public class JExpression<Self extends JExpression<Self>> extends AExpression<Self> {

    int test = 10;

    public JExpression(spoon.reflect.code.CtExpression expr, JWeaver weaver) {
        super(expr, weaver);
    }

    @Override
    public String getToStringImpl() {
        if (getClass() == JExpression.class) {
            return getNodeImpl().toString() + " - " + getNodeImpl().getClass().getSimpleName();
        }
        return super.getToStringImpl();
    }

    @Override
    public int getTestImpl() {
        return test;
    }

    public static AExpression<?> newInstance(spoon.reflect.code.CtExpression<?> expr, JWeaver weaver) {
        return CtExpression2AExpression.convertToExpression(expr, weaver);
    }

    @Override
    public void setTestImpl(AExpression<?> value) {
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
    public ATypeReference<?> getTypeReferenceImpl() {
        var children = getChildrenImpl();

        if (children.length > 0) {
            var firstChild = children[0];
            // First child should be a TypeReference
            if (firstChild instanceof ATypeReference<?> typeReference) {
                return typeReference;
            }
        }

        // Fallback
        var type = getNodeImpl().getType();

        if (type == null) {
            KadabraLog.info("Currrent expression, of join point type '" + get_class()
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
    public void extractImpl(String varName, AStatement<?> location, String position) {

        Optional<spoon.reflect.code.CtStatement> targetO = getTarget(location);

        if (!targetO.isPresent()) {
            throw new JavaWeaverException("Could not get the target location");
        }

        targetO.ifPresent(t -> SpoonUtils.extract(getNodeImpl(), varName, t, position, getWeaverEngine()));
    }

    private Optional<spoon.reflect.code.CtStatement> getTarget(AStatement<?> location) {
        if (location == null) {
            return SpoonUtils.getAncestorIncludeSelf(getNodeImpl(), spoon.reflect.code.CtStatement.class);
        }

        Object stmt = location.getNodeImpl();
        if (stmt instanceof spoon.reflect.code.CtStatement) {
            return Optional.of((spoon.reflect.code.CtStatement) stmt);
        }

        return Optional.empty();
    }

    @Override
    public spoon.reflect.code.CtExpression<?> getNodeImpl() {
        return (spoon.reflect.code.CtExpression<?>) super.getNodeImpl();
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, String code) {
        return new AJoinpoint<?>[] { insertImplJExpression(position, code) };
    }

    public AJoinpoint<?> insertImplJExpression(InsertPosition position, String code) {
        if (position == InsertPosition.REPLACE) {
            return ActionUtils.replaceExpression(position.name().toLowerCase(), code, getNodeImpl(),
                    getWeaverEngine());
        } else {
            return ActionUtils.insert(position.name().toLowerCase(), code, getNodeImpl(), getWeaverEngine());
        }
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, AJoinpoint<?> joinPoint) {
        return new AJoinpoint<?>[] { insertImplJExpression(position, joinPoint) };
    }

    public AJoinpoint<?> insertImplJExpression(InsertPosition position, AJoinpoint<?> joinPoint) {
        var ctElement = joinPoint.getNodeImpl();

        if (position == InsertPosition.REPLACE) {
            if (!(ctElement instanceof spoon.reflect.code.CtExpression<?>)) {
                KadabraLog.info("Cannot replace a join point of type " + joinPoint.get_class()
                        + " inside an expression, it has to be another expression");
                return null;
            }

            spoon.reflect.code.CtExpression<?> expression = (spoon.reflect.code.CtExpression<?>) ctElement;

            return ActionUtils.replaceExpression(position.name().toLowerCase(), expression, getNodeImpl(),
                    getWeaverEngine());
        } else {
            return ActionUtils.insert(position.name().toLowerCase(), ctElement, getNodeImpl(), getWeaverEngine());
        }
    }

    @Override
    public AJoinpoint<?> insertBeforeImpl(String code) {
        return insertImplJExpression(InsertPosition.BEFORE, code);
    }

    @Override
    public AJoinpoint<?> insertAfterImpl(String code) {
        return insertImplJExpression(InsertPosition.AFTER, code);
    }

    @Override
    public AJoinpoint<?> insertReplaceImpl(String code) {
        return insertImplJExpression(InsertPosition.REPLACE, code);
    }
}
