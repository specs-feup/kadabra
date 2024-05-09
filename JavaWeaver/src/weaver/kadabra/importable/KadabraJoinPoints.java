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
 * specific language governing permissions and limitations under the License. under the License.
 */

package weaver.kadabra.importable;

import java.util.Arrays;

import org.lara.interpreter.weaver.interf.JoinPoint;
import org.lara.interpreter.weaver.utils.JoinPointsUtils;

import pt.up.fe.specs.util.SpecsCheck;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtComment.CommentType;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
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
     *            the contents of the comment
     * @param type
     *            the type of comment, according to CtComment.CommentType
     * @return
     */
    public static JComment comment(String comment, String type) {
        // Convert the type
        CommentType typeEnum = null;

        try {
            typeEnum = CommentType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException(
                    "Comment type not supported: '" + type + "'. Use one of " + Arrays.toString(CommentType.values()));
        }

        return (JComment) CtElement2JoinPoint.convert(JavaWeaver.getFactory().comment(comment, typeEnum));
    }

    /**
     * Creates a new expression join point that represents the given literal.
     * 
     * @param type
     *            the type of the literal
     * @param literal
     *            a string representing a Java literal. In the case it is signed, returns a unaryExpression instead of a
     *            literal
     * @return
     */
    public static Object literal(String literal, String type) {
        boolean isNegative = false;

        // Check if negative
        if (literal.startsWith("-")) {
            isNegative = true;
            literal = literal.substring(1);
        }

        // Check type of literal

        var decodedValue = SpoonLiterals.decodeLiteralValue(type, literal);

        // If a number, check if it is a negative value
        // boolean isNegative = decodedValue instanceof Number ? ((Number) decodedValue).doubleValue() < 0 : false;

        CtExpression<?> expressionNode = JavaWeaver.getFactory().literal(decodedValue);

        if (isNegative) {
            expressionNode = JavaWeaver.getFactory().unaryOperator(UnaryOperatorKind.NEG, expressionNode);
        }

        return CtElement2JoinPoint.convert(expressionNode);
    }

    public static Object nullLiteral(Object referenceJp) {
        if (referenceJp != null) {
            SpecsCheck.checkArgument(referenceJp instanceof JoinPoint,
                    () -> "Reference join point must be a join point, it is a "
                            + referenceJp.getClass().getSimpleName());
        }

        var factory = JavaWeaver.getFactory().getSpoonFactory();

        CtElement nullLiteral = factory.createLiteral(null);

        //
        // if (referenceJp != null) {
        // var node = ((AJavaWeaverJoinPoint) referenceJp).getNode();
        // var children = node.getDirectChildren();
        // if (!children.isEmpty()) {
        // var firstChild = children.get(0);
        //
        // if (firstChild instanceof CtTypeReference) {
        // var copy = (CtTypeReference) firstChild.clone();
        //
        // var castNode = factory.Code().createTypeCast(copy, nullLiteral);
        // }
        // // System.out.println("FIRST CHILD: " + firstChild);
        // // System.out.println("FIRST CHILD CLASS: " + firstChild.getClass());
        // }
        // }

        return CtElement2JoinPoint.convert(nullLiteral);
    }

    /**
     * Creates a new unary operator for the given operation and expression.
     * 
     * @param operator
     *            the operator of the unary expression
     * @param operand
     *            an expression join point
     * @return
     */
    public static Object unaryOperator(String operator, Object operand) {

        SpecsCheck.checkArgument(operand instanceof JoinPoint,
                () -> "Operand must be a join point, it " + operator.getClass().getSimpleName());

        AJavaWeaverJoinPoint jpOperand = (AJavaWeaverJoinPoint) operand;

        SpecsCheck.checkArgument(jpOperand.instanceOf("expression"),
                () -> "Operand must be a join point of type 'expression', is " + jpOperand.getJoinPointType());

        CtExpression<?> nodeExpr = (CtExpression<?>) jpOperand.getNode();

        // Convert string to kind
        UnaryOperatorKind opKind = OperatorUtils.parseUnary(operator);

        return CtElement2JoinPoint.convert(JavaWeaver.getFactory().unaryOperator(opKind, nodeExpr));
    }

    /**
     * Creates a new unary operator for the given operation and expression.
     * 
     * @param operator
     *            the operator of the binary expression
     * @param lhs
     *            a join point representing the left hand of the binary expression
     * @param rhs
     *            a join point representing the right hand of the binary expression
     * @return
     */
    public static Object binaryOperator(String operator, Object lhs, Object rhs) {

        SpecsCheck.checkArgument(lhs instanceof JoinPoint,
                () -> "Lhs must be a join point, it " + operator.getClass().getSimpleName());
        SpecsCheck.checkArgument(rhs instanceof JoinPoint,
                () -> "Rhs must be a join point, it " + operator.getClass().getSimpleName());

        AJavaWeaverJoinPoint jpLhs = (AJavaWeaverJoinPoint) lhs;
        AJavaWeaverJoinPoint jpRhs = (AJavaWeaverJoinPoint) rhs;

        SpecsCheck.checkArgument(jpLhs.instanceOf("expression"),
                () -> "Lhs must be a join point of type 'expression', is " + jpLhs.getJoinPointType());
        SpecsCheck.checkArgument(jpRhs.instanceOf("expression"),
                () -> "Rhs must be a join point of type 'expression', is " + jpRhs.getJoinPointType());

        CtExpression<?> nodeLhs = (CtExpression<?>) jpLhs.getNode();
        CtExpression<?> nodeRhs = (CtExpression<?>) jpRhs.getNode();

        // Convert string to kind
        BinaryOperatorKind opKind = OperatorUtils.parseBinary(operator);

        return CtElement2JoinPoint.convert(JavaWeaver.getFactory().binaryOperator(opKind, nodeLhs, nodeRhs));
    }

    public static Object assignment(Object lhs, Object rhs) {
        SpecsCheck.checkNotNull(lhs, () -> "lhs cannot be null");
        SpecsCheck.checkNotNull(rhs, () -> "rhs cannot be null");
        SpecsCheck.checkArgument(lhs instanceof JoinPoint,
                () -> "Lhs must be a join point, it is a " + lhs.getClass().getSimpleName());
        SpecsCheck.checkArgument(rhs instanceof JoinPoint,
                () -> "Rhs must be a join point, it is a " + rhs.getClass().getSimpleName());

        AJavaWeaverJoinPoint jpLhs = (AJavaWeaverJoinPoint) lhs;
        AJavaWeaverJoinPoint jpRhs = (AJavaWeaverJoinPoint) rhs;

        SpecsCheck.checkArgument(jpLhs.instanceOf("expression"),
                () -> "Lhs must be a join point of type 'expression', is " + jpLhs.getJoinPointType());
        SpecsCheck.checkArgument(jpRhs.instanceOf("expression"),
                () -> "Rhs must be a join point of type 'expression', is " + jpRhs.getJoinPointType());

        CtExpression<?> nodeLhs = (CtExpression<?>) jpLhs.getNode();
        CtExpression<?> nodeRhs = (CtExpression<?>) jpRhs.getNode();

        return CtElement2JoinPoint.convert(JavaWeaver.getFactory().assignment(nodeLhs, nodeRhs));

    }

    public static Object var(Object localVariable, boolean isWrite) {
        var localVarJp = JoinPointsUtils.toJavaJoinPoint(localVariable, "localVariable", JLocalVariable.class);

        var localVarSpoon = localVarJp.getNode();
        return CtElement2JoinPoint.convert(JavaWeaver.getFactory().var(localVarSpoon, isWrite));
    }

    /**
     * Creates an expression from code snippet.
     *
     * @param code
     * @return
     */
    public static Object snippetExpression(String code) {
        return CtElement2JoinPoint
                .convert(SnippetFactory.createSnippetExpression(JavaWeaver.getFactory().getSpoonFactory(), code));
    }

    // public static Object literal2(String literal, String type) {
    // var expressionNode = JavaWeaver.getFactory().literal(Integer.valueOf(1));
    // return CtElement2JoinPoint.convert(expressionNode);
    // }
}
