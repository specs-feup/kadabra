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

import java.util.Arrays;
import java.util.List;

import pt.up.fe.specs.util.SpecsLogs;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.UnaryOperatorKind;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AUnaryExpression;
import weaver.utils.element.OperatorUtils;
import weaver.utils.weaving.SelectUtils;

public class JUnaryExpression<T> extends AUnaryExpression {

    public CtUnaryOperator<T> node;

    public JUnaryExpression(CtUnaryOperator<T> expr) {
        super(new JExpression<>(expr));
        node = expr;
    }

    public static <T> JUnaryExpression<T> newInstance(CtUnaryOperator<T> expr) {
        return new JUnaryExpression<>(expr);
    }

    @Override
    public String getOperatorImpl() {
        return OperatorUtils.convert(node.getKind());
    }

    @Override
    public String toString() {
        return "Unary Expression: " + node;
    }

    @Override
    public CtUnaryOperator<?> getNode() {
        return node;
    }

    @Override
    public void defOperatorImpl(String value) {
        // Convert string to kind
        UnaryOperatorKind kind = OperatorUtils.parseUnaryTry(value).orElse(null);

        if (kind == null) {
            SpecsLogs.msgInfo("Could not parse unary operator '" + value + "'");
            return;
        }

        node.setKind(kind);
    }

    @Override
    public void setOperatorImpl(String operator) {
        defOperatorImpl(operator);
    }

    @Override
    public AExpression getOperandImpl() {
        return SelectUtils.expression2JoinPoint(node.getOperand());
    }

}
