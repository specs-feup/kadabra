/**
 * Copyright 2015 SPeCS.
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

package weaver.kadabra.joinpoints;

import java.util.Collections;
import java.util.List;

import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.abstracts.joinpoints.ADeclaration;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.utils.element.CtTypeReferenceUtils;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.TypeUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JDeclaration<T> extends ADeclaration {

    protected CtVariable<T> node;

    private JDeclaration(CtVariable<T> node) {
        this.node = node;
    }

    public static <T> JDeclaration<T> newInstance(CtVariable<T> node) {
        return new JDeclaration<>(node);
    }

    @Override
    public String getNameImpl() {
        return node.getSimpleName();
    }

    @Override
    public String getTypeImpl() {

        CtTypeReference<?> type2 = node.getType();
        if (type2 == null) {
            return "unknown";
        }
        String type = CtTypeReferenceUtils.getType(type2);
        return type != null ? type : "unknown";
    }

    @Override
    public Boolean getIsArrayImpl() {
        return CtTypeReferenceUtils.getIsArray(node.getType());
    }

    @Override
    public Boolean getIsPrimitiveImpl() {
        return CtTypeReferenceUtils.getIsPrimitive(node.getType());
    }

    @Override
    public List<? extends AExpression> selectInit() {
        final CtExpression<?> expr = node.getDefaultExpression();
        if (expr == null) {
            return Collections.emptyList();
        }

        final List<AExpression> exprs = SelectUtils.expression2JoinPointList(expr);
        return exprs;
    }

    @Override
    public CtVariable<T> getNode() {
        return node;
    }

    @Override
    public String getCompleteTypeImpl() {
        return TypeUtils.getTypeString(node.getType());
    }

    @Override
    public String toString() {
        return getNameImpl();
    }

    @Override
    public AExpression getInitImpl() {
        var init = node.getDefaultExpression();
        if (init == null) {
            return null;
        }

        return (AExpression) CtElement2JoinPoint.convert(init);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void defInitImpl(AExpression value) {
        if (value == null) {
            node.setDefaultExpression(null);
            return;
        }

        node.setDefaultExpression((CtExpression<T>) value.getNode());
    }

    @Override
    public void setInitImpl(AExpression value) {
        defInitImpl(value);
    }
}
