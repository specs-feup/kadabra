/**
 * Copyright 2017 SPeCS.
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

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.ALocalVariable;
import weaver.kadabra.abstracts.joinpoints.ATypeReference;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JLocalVariable<Self extends JLocalVariable<Self>> extends ALocalVariable<Self> {

    private final JDeclaration<?> declaration;

    public JLocalVariable(spoon.reflect.code.CtLocalVariable statement, JWeaver weaver) {
        super(statement, weaver);
        this.declaration = new JDeclaration<>(statement, weaver);
    }

    @Override
    public String getNameImpl() {
        return declaration.getNameImpl();
    }

    @Override
    public ATypeReference<?> getTypeReferenceImpl() {
        return declaration.getTypeReferenceImpl();
    }

    @Override
    public String getTypeImpl() {
        return getTypeReferenceImpl().toString();
    }

    @Override
    public boolean getIsArrayImpl() {
        return declaration.getIsArrayImpl();
    }

    @Override
    public boolean getIsPrimitiveImpl() {
        return declaration.getIsPrimitiveImpl();
    }

    @Override
    public String getCompleteTypeImpl() {
        return declaration.getCompleteTypeImpl();
    }

    @Override
    public spoon.reflect.code.CtLocalVariable<?> getNodeImpl() {
        return (spoon.reflect.code.CtLocalVariable<?>) super.getNodeImpl();
    }

    @Override
    public AExpression<?> getInitImpl() {
        var defaultExpr = getNodeImpl().getDefaultExpression();
        if (defaultExpr == null) {
            return null;
        }

        return (AExpression<?>) CtElement2JoinPoint.convert(defaultExpr, getWeaverEngine());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setInitImpl(AExpression<?> value) {
        if (value == null) {
            getNodeImpl().setDefaultExpression(null);
            return;
        }
        ((spoon.reflect.code.CtLocalVariable) getNodeImpl())
                .setDefaultExpression((spoon.reflect.code.CtExpression) value.getNodeImpl());
    }

}
