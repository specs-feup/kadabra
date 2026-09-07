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

import spoon.reflect.declaration.CtVariable;
import spoon.reflect.reference.CtTypeReference;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ADeclaration;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.ATypeReference;
import weaver.utils.element.CtTypeReferenceUtils;
import weaver.utils.weaving.TypeUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JDeclaration<Self extends JDeclaration<Self>> extends ADeclaration<Self> {

    public JDeclaration(CtVariable node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public String getNameImpl() {
        return getNodeImpl().getSimpleName();
    }

    @Override
    public ATypeReference<?> getTypeReferenceImpl() {
        CtTypeReference<?> type2 = getNodeImpl().getType();
        return type2 != null ? new JTypeReference<>(type2, getWeaverEngine()) : null;
    }

    @Override
    public String getTypeImpl() {
        var typeReference = getTypeReferenceImpl();
        return typeReference != null ? typeReference.toString() : "unknown";
    }

    @Override
    public boolean getIsArrayImpl() {
        return CtTypeReferenceUtils.getIsArray(getNodeImpl().getType());
    }

    @Override
    public boolean getIsPrimitiveImpl() {
        return CtTypeReferenceUtils.getIsPrimitive(getNodeImpl().getType());
    }

    @Override
    public CtVariable<?> getNodeImpl() {
        return (CtVariable<?>) super.getNodeImpl();
    }

    @Override
    public String getCompleteTypeImpl() {
        return TypeUtils.getTypeString(getNodeImpl().getType());
    }

    @Override
    public String getToStringImpl() {
        return getNameImpl();
    }

    @Override
    public AExpression<?> getInitImpl() {
        var init = getNodeImpl().getDefaultExpression();
        if (init == null) {
            return null;
        }

        return CtElement2JoinPoint.convert(init, getWeaverEngine(), AExpression.class);
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void setInitImpl(AExpression<?> value) {
        if (value == null) {
            getNodeImpl().setDefaultExpression(null);
            return;
        }

        ((CtVariable) getNodeImpl()).setDefaultExpression((spoon.reflect.code.CtExpression) value.getNodeImpl());
    }
}
