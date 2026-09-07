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

import pt.up.fe.specs.util.SpecsLogs;

import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ABinaryExpression;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.utils.element.OperatorUtils;
import weaver.utils.weaving.SelectUtils;

public class JBinaryExpression<Self extends JBinaryExpression<Self>> extends ABinaryExpression<Self> {

    public JBinaryExpression(CtBinaryOperator node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public String getOperatorImpl() {
        return OperatorUtils.convert(getNodeImpl().getKind());
    }

    @Override
    public CtBinaryOperator<?> getNodeImpl() {
        return (CtBinaryOperator<?>) super.getNodeImpl();
    }

    @Override
    public void setOperatorImpl(String operator) {
        // Convert string to kind
        BinaryOperatorKind kind = OperatorUtils.parseBinaryTry(operator).orElse(null);

        if (kind == null) {
            SpecsLogs.msgInfo("Could not parse binary operator '" + operator + "'");
            return;
        }

        getNodeImpl().setKind(kind);
    }

    @Override
    public AExpression<?>[] getOperandsImpl() {
        AExpression<?>[] operands = { getLhsImpl(), getRhsImpl() };
        return operands;
    }

    @Override
    public AExpression<?> getLhsImpl() {
        return SelectUtils.expression2JoinPoint(getNodeImpl().getLeftHandOperand(), getWeaverEngine());
    }

    @Override
    public AExpression<?> getRhsImpl() {
        return SelectUtils.expression2JoinPoint(getNodeImpl().getRightHandOperand(), getWeaverEngine());
    }

}
