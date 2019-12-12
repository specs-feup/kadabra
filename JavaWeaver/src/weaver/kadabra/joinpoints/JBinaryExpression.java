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
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import weaver.kadabra.abstracts.joinpoints.ABinaryExpression;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.utils.element.OperatorUtils;
import weaver.utils.weaving.SelectUtils;

public class JBinaryExpression<T> extends ABinaryExpression {

    public CtBinaryOperator<T> node;

    public JBinaryExpression(CtBinaryOperator<T> expr) {
        super(new JExpression<>(expr));
        node = expr;
    }

    public static <T> JBinaryExpression<T> newInstance(CtBinaryOperator<T> expr) {
        return new JBinaryExpression<>(expr);
    }

    @Override
    public String getOperatorImpl() {
        return OperatorUtils.convert(node.getKind());
    }

    @Override
    public List<? extends AExpression> selectLhs() {
        return Arrays.asList(getLhsImpl());
        // final List<AExpression> exprs = SelectUtils.expression2JoinPointList(node.getLeftHandOperand());
        // return exprs;
    }

    // @Override
    // public String toString() {
    // return "Binary Expression: " + node;
    // }

    @Override
    public List<? extends AExpression> selectRhs() {
        return Arrays.asList(getRhsImpl());
        // final List<AExpression> exprs = SelectUtils.expression2JoinPointList(node.getRightHandOperand());
        // return exprs;
    }

    @Override
    public List<? extends AExpression> selectOperands() {
        return Arrays.asList(getOperandsArrayImpl());
    }

    @Override
    public CtBinaryOperator<?> getNode() {
        return node;
    }

    @Override
    public void defOperatorImpl(String value) {
        // Convert string to kind
        BinaryOperatorKind kind = OperatorUtils.parseBinaryTry(value).orElse(null);

        if (kind == null) {
            SpecsLogs.msgInfo("Could not parse binary operator '" + value + "'");
            return;
        }

        node.setKind(kind);
    }

    @Override
    public void setOperatorImpl(String operator) {
        defOperatorImpl(operator);
    }

    @Override
    public AExpression[] getOperandsArrayImpl() {
        AExpression[] operands = { getLhsImpl(), getRhsImpl() };
        return operands;
    }

    @Override
    public AExpression getLhsImpl() {
        return SelectUtils.expression2JoinPoint(node.getLeftHandOperand());
    }

    @Override
    public AExpression getRhsImpl() {
        return SelectUtils.expression2JoinPoint(node.getRightHandOperand());
    }

}
