/**
 * Copyright 2019 SPeCS.
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

package weaver.kadabra.importable;

import java.util.Arrays;
import java.util.Objects;

import org.lara.interpreter.weaver.interf.JoinPoint2;

import pt.up.fe.specs.util.SpecsCheck;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtComment.CommentType;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.joinpoints.JComment;
import weaver.kadabra.joinpoints.JLocalVariable;
import weaver.utils.SpoonLiterals;
import weaver.utils.element.OperatorUtils;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class KadabraJoinPoints {

    /**
     * Creates a new comment join point.
     * 
     * @param comment
     *                the contents of the comment
     * @param type
     *                the type of comment, according to CtComment.CommentType
     * @return
     */
    public static JComment comment(JWeaver weaver, String comment, String type) {
        // Convert the type
        CommentType typeEnum = null;

        try {
            typeEnum = CommentType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException(
                    "Comment type not supported: '" + type + "'. Use one of " + Arrays.toString(CommentType.values()));
        }

        return CtElement2JoinPoint.convert(weaver.getFactory().comment(comment, typeEnum), weaver, JComment.class);
    }

    /**
     * Creates a new expression join point that represents the given literal.
     * 
     * @param type
     *                the type of the literal
     * @param literal
     *                a string representing a Java literal. In the case it is
     *                signed, returns a unaryExpression instead of a literal
     * @return
     */
    public static Object literal(JWeaver weaver, String literal, String type) {
        boolean isNegative = false;

        // Check if negative
        if (literal.startsWith("-")) {
            isNegative = true;
            literal = literal.substring(1);
        }

        // Check type of literal
        var decodedValue = SpoonLiterals.decodeLiteralValue(type, literal);

        CtExpression<?> expressionNode = weaver.getFactory().literal(decodedValue);

        if (isNegative) {
            expressionNode = weaver.getFactory().unaryOperator(UnaryOperatorKind.NEG, expressionNode);
        }

        return CtElement2JoinPoint.convert(expressionNode, weaver);
    }

    public static Object nullLiteral(JWeaver weaver, Object referenceJp) {
        if (referenceJp != null) {
            SpecsCheck.checkArgument(referenceJp instanceof AJoinpoint<?>,
                    () -> "Reference join point must be a join point, it is a "
                            + referenceJp.getClass().getSimpleName());
        }

        var factory = weaver.getFactory().getSpoonFactory();

        CtElement nullLiteral = factory.createLiteral(null);

        return CtElement2JoinPoint.convert(nullLiteral, weaver);
    }

    /**
     * Creates a new unary operator for the given operation and expression.
     * 
     * @param operator
     *                 the operator of the unary expression
     * @param operand
     *                 an expression join point
     * @return
     */
    public static Object unaryOperator(JWeaver weaver, String operator, Object operand) {

        SpecsCheck.checkArgument(operand instanceof AJoinpoint<?>,
                () -> "Operand must be a join point, it " + operator.getClass().getSimpleName());

        AJoinpoint<?> jpOperand = (AJoinpoint<?>) operand;

        SpecsCheck.checkArgument(jpOperand.instanceOf("expression"),
                () -> "Operand must be a join point of type 'expression', is " + jpOperand.get_class());

        CtExpression<?> nodeExpr = (CtExpression<?>) jpOperand.getNodeImpl();

        // Convert string to kind
        UnaryOperatorKind opKind = OperatorUtils.parseUnary(operator);

        return CtElement2JoinPoint.convert(weaver.getFactory().unaryOperator(opKind, nodeExpr), weaver);
    }

    /**
     * Creates a new unary operator for the given operation and expression.
     * 
     * @param operator
     *                 the operator of the binary expression
     * @param lhs
     *                 a join point representing the left hand of the binary
     *                 expression
     * @param rhs
     *                 a join point representing the right hand of the binary
     *                 expression
     * @return
     */
    public static Object binaryOperator(JWeaver weaver, String operator, Object lhs, Object rhs) {

        SpecsCheck.checkArgument(lhs instanceof AJoinpoint<?>,
                () -> "Lhs must be a join point, it " + operator.getClass().getSimpleName());
        SpecsCheck.checkArgument(rhs instanceof AJoinpoint<?>,
                () -> "Rhs must be a join point, it " + operator.getClass().getSimpleName());

        AJoinpoint<?> jpLhs = (AJoinpoint<?>) lhs;
        AJoinpoint<?> jpRhs = (AJoinpoint<?>) rhs;

        SpecsCheck.checkArgument(jpLhs.instanceOf("expression"),
                () -> "Lhs must be a join point of type 'expression', is " + jpLhs.get_class());
        SpecsCheck.checkArgument(jpRhs.instanceOf("expression"),
                () -> "Rhs must be a join point of type 'expression', is " + jpRhs.get_class());

        CtExpression<?> nodeLhs = (CtExpression<?>) jpLhs.getNodeImpl();
        CtExpression<?> nodeRhs = (CtExpression<?>) jpRhs.getNodeImpl();

        // Convert string to kind
        BinaryOperatorKind opKind = OperatorUtils.parseBinary(operator);

        return CtElement2JoinPoint.convert(weaver.getFactory().binaryOperator(opKind, nodeLhs, nodeRhs), weaver);
    }

    public static Object assignment(JWeaver weaver, Object lhs, Object rhs) {
        Objects.requireNonNull(lhs, () -> "lhs cannot be null");
        Objects.requireNonNull(rhs, () -> "rhs cannot be null");
        SpecsCheck.checkArgument(lhs instanceof AJoinpoint<?>,
                () -> "Lhs must be a join point, it is a " + lhs.getClass().getSimpleName());
        SpecsCheck.checkArgument(rhs instanceof AJoinpoint<?>,
                () -> "Rhs must be a join point, it is a " + rhs.getClass().getSimpleName());

        AJoinpoint<?> jpLhs = (AJoinpoint<?>) lhs;
        AJoinpoint<?> jpRhs = (AJoinpoint<?>) rhs;

        SpecsCheck.checkArgument(jpLhs.instanceOf("expression"),
                () -> "Lhs must be a join point of type 'expression', is " + jpLhs.get_class());
        SpecsCheck.checkArgument(jpRhs.instanceOf("expression"),
                () -> "Rhs must be a join point of type 'expression', is " + jpRhs.get_class());

        CtExpression<?> nodeLhs = (CtExpression<?>) jpLhs.getNodeImpl();
        CtExpression<?> nodeRhs = (CtExpression<?>) jpRhs.getNodeImpl();

        return CtElement2JoinPoint.convert(weaver.getFactory().assignment(nodeLhs, nodeRhs), weaver);

    }

    public static Object var(JWeaver weaver, JLocalVariable localVariable, boolean isWrite) {
        var localVarSpoon = localVariable.getNodeImpl();
        return CtElement2JoinPoint.convert(weaver.getFactory().var(localVarSpoon, isWrite), weaver);
    }

    /**
     * Creates an expression from code snippet.
     *
     * @param code
     * @return
     */
    public static Object snippetExpression(JWeaver weaver, String code) {
        return CtElement2JoinPoint
                .convert(SnippetFactory.createSnippetExpression(weaver.getFactory().getSpoonFactory(), code), weaver);
    }
}
