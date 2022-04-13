/**
 * Copyright 2017 SPeCS.
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

import java.util.List;

import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLocalVariable;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.ALocalVariable;
import weaver.kadabra.abstracts.joinpoints.ATypeReference;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JLocalVariable<T> extends ALocalVariable {

    private CtLocalVariable<T> node;
    private JDeclaration<T> declaration;

    private JLocalVariable(CtLocalVariable<T> statement) {
        super(new JStatement(statement));
        this.node = statement;
        this.declaration = JDeclaration.newInstance(statement);
    }

    public static <T> JLocalVariable<T> newInstance(CtLocalVariable<T> statement) {
        return new JLocalVariable<>(statement);
    }

    @Override
    public String getNameImpl() {
        return declaration.getNameImpl();
    }

    @Override
    public ATypeReference getTypeReferenceImpl() {
        return declaration.getTypeReferenceImpl();
    }

    @Override
    public String getTypeImpl() {
        return getTypeReferenceImpl().toString();
    }

    // @Override
    // public AType getTypeJpImpl() {
    // var typeRef = (CtVariable<?>) declaration.getTypeReferenceImpl().getNode();
    // JType<?>()
    // typeRef.getType().getTypeDeclaration()
    // // TODO Auto-generated method stub
    // return null;
    // }

    @Override
    public Boolean getIsArrayImpl() {
        return declaration.getIsArrayImpl();
    }

    @Override
    public Boolean getIsPrimitiveImpl() {
        return declaration.getIsPrimitiveImpl();
    }

    @Override
    public String getCompleteTypeImpl() {
        return declaration.getCompleteTypeImpl();
    }

    @Override
    public List<? extends AExpression> selectInit() {
        return declaration.selectInit();
    }

    @Override
    public CtLocalVariable<T> getNode() {
        return node;
    }

    @Override
    public AExpression getInitImpl() {
        var defaultExpr = declaration.getNode().getDefaultExpression();
        if (defaultExpr == null) {
            return null;
        }

        return (AExpression) CtElement2JoinPoint.convert(defaultExpr);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void defInitImpl(AExpression value) {
        if (value == null) {
            declaration.getNode().setDefaultExpression(null);
            return;
        }
        declaration.getNode().setDefaultExpression((CtExpression<T>) value.getNode());
    }

    @Override
    public void setInitImpl(AExpression init) {
        defInitImpl(init);
    }

}
