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

import spoon.reflect.code.CtComment.CommentType;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.UnaryOperatorKind;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.joinpoints.JComment;
import weaver.utils.SpoonLiterals;
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
     *            literal.
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

    // public static Object literal2(String literal, String type) {
    // var expressionNode = JavaWeaver.getFactory().literal(Integer.valueOf(1));
    // return CtElement2JoinPoint.convert(expressionNode);
    // }
}
