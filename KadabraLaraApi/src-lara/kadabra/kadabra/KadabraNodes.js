import KadabraJavaTypes from "./KadabraJavaTypes.js";
export class KadabraNodes {
    /**
     * Creates a comment node.
     *
     * @param comment - The string representing the contents of a comment.
     * @param type- The type of the comment. Can be one of 'File', 'Javadoc', 'Inline' or 'Block'. If not specified, uses Block as default.
     *
     * @returns A node representing the comment
     */
    static comment(comment = "", type = "Block") {
        return KadabraJavaTypes.KadabraJoinPoints.comment(comment, type);
    }
    /**
     * Creates a literal node.
     *
     * @param literal - The string representing the literal. Can pass negative literals.
     * @param type - The type of the literal. Can be one of int, long, float, double, char, String or boolean.
     *
     * @returns An expression representing the literal.
     */
    static literal(literal, type) {
        return KadabraJavaTypes.KadabraJoinPoints.literal(literal, type);
    }
    /**
     * Creates a null literal node.
     *
     * @param referenceJp - Optionally indicate a node as a reference (can be useful for casting the null to a specific type).
     *
     * @returns A null literal node.
     */
    static nullLiteral(referenceJp) {
        return KadabraJavaTypes.KadabraJoinPoints.nullLiteral(referenceJp);
    }
    /**
     * Creates a unary expression.
     *
     * @param operator - The string representing the operator, can be one of "+", "-", "!", "~", "++_", "--_", "_++", "_--", "++" or "--".
     * @param operand - The operand, must be a join point of type expression.
     *
     * @returns An expression representing the literal. If the literal is a negative number, it will return a unaryExpression, otherwise returns a literal.
     */
    static unaryExpression(operator, operand) {
        return KadabraJavaTypes.KadabraJoinPoints.unaryOperator(operator, operand);
    }
    /**
     * Creates a binary expression.
     *
     * @param operator - The string representing the operator, can be one of "+", "-", "*", "/", "%", "||", "&&", "|", "^", "&", "==", "!=", "\<", "\>", "\<=", "\>=", "\<\<", "\>\>", "\>\>\>" or "instanceof".
     * @param lhs - The left-hand side, must be a join point of type expression.
     * @param rhs - The right-hand side, must be a join point of type expression.
     *
     * @returns An expression representing the literal. If the literal is a negative number, it will return a unaryExpression, otherwise returns a literal.
     */
    static binaryExpression(operator, lhs, rhs) {
        return KadabraJavaTypes.KadabraJoinPoints.binaryOperator(operator, lhs, rhs);
    }
    /**
     * Creates an assignment.
     *
     * @param lhs - The left-hand side, must be a join point of type expression.
     * @param rhs - The right-hand side, must be a join point of type expression.
     *
     * @returns An assignment statement.
     */
    static assignment(lhs, rhs) {
        return KadabraJavaTypes.KadabraJoinPoints.assignment(lhs, rhs);
    }
    /**
     * Creates a reference to a local variable.
     *
     * @param localVariable - The local variable declaration to which we will create a variable reference.
     * @param isWrite - True if the variable will be written, false if it will be read. By default creates variables for reading.
     *
     * @returns A reference to a variable.
     *
     */
    static var(localVariable, isWrite = false) {
        return KadabraJavaTypes.KadabraJoinPoints.var(localVariable, isWrite);
    }
    /**
     * Creates an expression from a code snippet.
     *
     * @param code - The literal code that represents the expression.
     *
     * @returns An expression representing the code snippet.
     */
    static snippetExpr(code) {
        return KadabraJavaTypes.KadabraJoinPoints.snippetExpression(code);
    }
    /**
     * Creates an XML node from a string of XML code.
     *
     * @param xmlCode - The XML code to parse.
     *
     * @returns The parsed XML node.
     */
    static xmlNode(xmlCode) {
        return KadabraJavaTypes.AndroidResources.parseXml(xmlCode);
    }
}
//# sourceMappingURL=KadabraNodes.js.map