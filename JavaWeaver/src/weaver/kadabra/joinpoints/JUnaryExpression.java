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

import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.UnaryOperatorKind;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AUnaryExpression;
import weaver.utils.element.OperatorUtils;
import weaver.utils.weaving.SelectUtils;

public class JUnaryExpression<Self extends JUnaryExpression<Self>> extends AUnaryExpression<Self> {

    public JUnaryExpression(CtUnaryOperator node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public String getOperatorImpl() {
        return OperatorUtils.convert(getNodeImpl().getKind());
    }

    @Override
    public String getToStringImpl() {
        return "Unary Expression: " + getNodeImpl();
    }

    @Override
    public CtUnaryOperator<?> getNodeImpl() {
        return (CtUnaryOperator<?>) super.getNodeImpl();
    }

    @Override
    public void setOperatorImpl(String operator) {
        // Convert string to kind
        UnaryOperatorKind kind = OperatorUtils.parseUnaryTry(operator).orElse(null);

        if (kind == null) {
            SpecsLogs.msgInfo("Could not parse unary operator '" + operator + "'");
            return;
        }

        getNodeImpl().setKind(kind);
    }

    @Override
    public AExpression<?> getOperandImpl() {
        return SelectUtils.expression2JoinPoint(getNodeImpl().getOperand(), getWeaverEngine());
    }

}
