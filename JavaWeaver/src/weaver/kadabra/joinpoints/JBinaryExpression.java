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
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.joinpoints;

import pt.up.fe.specs.util.SpecsLogs;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.joinpoints.ABinaryExpression;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.utils.element.OperatorUtils;
import weaver.utils.weaving.SelectUtils;

public class JBinaryExpression<T> extends ABinaryExpression {

    public CtBinaryOperator<T> node;

    public JBinaryExpression(CtBinaryOperator<T> expr, JavaWeaver weaver) {
        super(new JExpression<>(expr, weaver), weaver);
        node = expr;
    }

    public static <T> JBinaryExpression<T> newInstance(CtBinaryOperator<T> expr, JavaWeaver weaver) {
        return new JBinaryExpression<>(expr, weaver);
    }

    @Override
    public String getOperatorImpl() {
        return OperatorUtils.convert(node.getKind());
    }

    @Override
    public CtBinaryOperator<?> getNode() {
        return node;
    }

    @Override
    public void setOperatorImpl(String value) {
        // Convert string to kind
        BinaryOperatorKind kind = OperatorUtils.parseBinaryTry(value).orElse(null);

        if (kind == null) {
            SpecsLogs.msgInfo("Could not parse binary operator '" + value + "'");
            return;
        }

        node.setKind(kind);
    }

    @Override
    public AExpression[] getOperandsArrayImpl() {
        AExpression[] operands = { getLhsImpl(), getRhsImpl() };
        return operands;
    }

    @Override
    public AExpression getLhsImpl() {
        return SelectUtils.expression2JoinPoint(node.getLeftHandOperand(), getWeaverEngine());
    }

    @Override
    public AExpression getRhsImpl() {
        return SelectUtils.expression2JoinPoint(node.getRightHandOperand(), getWeaverEngine());
    }

}
