laraImport("kadabra._KadabraJavaTypes");

/**
 * Utility methods related with the creation of new join points.
 *
 * @class
 */
var KadabraNodes = {};


/**
 * Creates a comment node.
 *
 * @param {string} comment - The string representing the contents of a comment.
 * @param {string} [type="block"] - The type of the comment. Can be one of 'File', 'Javadoc', 'Inline' or 'Block'. If not specified, uses Block as default.
 *
 * @return {comment} a node representing the comment
 */
KadabraNodes.comment= function(comment, type) {
	if(comment === undefined) {
		comment = "";
	}

	// Default type is block
	if(type === undefined) {
		type = "block";
	}

	checkString(type, "KadabraNodes.comment(_, type)");

	comment = comment.toString();
	type = type.toString();	
	
	return _KadabraJavaTypes.getKadabraJoinPoints().comment(comment, type);
}

/**
 * Creates a literal node.
 *
 * @param {string} literal- The string representing the literal. Can pass negative literals.
 * @param {string} type - The type of the literal. Can be one of int, long, float, double, char, String or boolean.
 *
 * @return {expression} an expression representing the literal. If the literal is a negative number, it will return a unaryExpression, otherwise returns a literal.
 */
KadabraNodes.literal = function(literal, type) {
	checkString(literal, "KadabraNodes.literal(literal, _)");
	checkString(type, "KadabraNodes.literal(_, type)");

	return _KadabraJavaTypes.getKadabraJoinPoints().literal(literal, type);
}

/**
 * Creates a null literal node
 * @param {jp} referenceJp Optionally indicate a node as a reference (can be useful for casting the null to a specific type)
 * @returns 
 */
KadabraNodes.nullLiteral = function(referenceJp) {
	return _KadabraJavaTypes.getKadabraJoinPoints().nullLiteral(referenceJp);
}


/**
 * Creates a unary expression.
 *
 * @param {string} operator - The string representing the operator, can be one of "+", "-", "!", "~", "++_", "--_", "_++", "_--", "++" or "--".
 * @param {expression} operand - The operand, must be a join point of type expression.
 *
 * @return {expression} an expression representing the literal. If the literal is a negative number, it will return a unaryExpression, otherwise returns a literal.
 */
KadabraNodes.unaryExpression= function(operator, operand) {
	checkString(operator, "KadabraNodes.unaryExpression(operator, _)");
	checkJoinPoint(operand, "KadabraNodes.unaryExpression(_, operand)");

	return _KadabraJavaTypes.getKadabraJoinPoints().unaryOperator(operator, operand);
}


/**
 * Creates a binary expression.
 *
 * @param {string} operator - The string representing the operator, can be one of "+", "-", "*", "/", "%", "||", "&&", "|", "^", "&", "==", "!=", "<", ">", "<=", ">=", "<<", ">>", ">>>" or "instanceof".
 * @param {expression} lhs - The left-hand side, must be a join point of type expression.
 * @param {expression} rhs - The right-hand side, must be a join point of type expression. 
 *
 * @return {expression} an expression representing the literal. If the literal is a negative number, it will return a unaryExpression, otherwise returns a literal.
 */
KadabraNodes.binaryExpression= function(operator, lhs, rhs) {
	checkString(operator, "KadabraNodes.binaryExpression(operator, _, _)");
	checkJoinPoint(lhs, "KadabraNodes.binaryExpression(_, lhs, _)");
	checkJoinPoint(rhs, "KadabraNodes.binaryExpression(_, _, rhs)");	

	return _KadabraJavaTypes.getKadabraJoinPoints().binaryOperator(operator, lhs, rhs);
}


/**
 * Creates an assignment.
 *
 * @param {expression} lhs - The left-hand side, must be a join point of type expression.
 * @param {expression} rhs - The right-hand side, must be a join point of type expression. 
 *
 * @return {assignment} an assignment statement.
 */
KadabraNodes.assignment= function(lhs, rhs) {
	return _KadabraJavaTypes.getKadabraJoinPoints().assignment(lhs, rhs);
}

/**
 * Creates a reference to a local variable.
 *
 * @param {localVariable} localVariable - The local variable declaration to which we will create a variable reference.
 * @param {boolean} [isWrite = false] - True if the variable will be written, false if it will be read. By default creates variables for reading.
 *
 * @return {var} a reference to a variable.
 *
 */
KadabraNodes.var = function(localVariable, isWrite) {
    // In Spoon to create a CtVariableAccess, you need a CtVariableReference, which you can get from a CtLocalVariable. However, you need to decide if it will be a variable for read or writing
	checkJoinPoint(localVariable, "KadabraNodes.var(_, localVariable, _)");

	// Default value is false
	if(isWrite === undefined) {
		isWrite = false;
	}
	
	return _KadabraJavaTypes.getKadabraJoinPoints().var(localVariable, isWrite);
}


/**
 * Creates an expression from code snippet.
 *
 * @param {string} code - The literal code that represents the expression.
 *
 * @return {expression} an expression representing the code snippet.
 */
KadabraNodes.snippetExpr= function(code) {
	checkString(code, "KadabraNodes.snippetExpr(code)");

	return _KadabraJavaTypes.getKadabraJoinPoints().snippetExpression(code);
}


KadabraNodes.xmlNode = function(xmlCode) {
	checkString(xmlCode, "KadabraNodes.xmlNode(xmlCode)");

	var androidResources = Java.type("weaver.utils.android.AndroidResources");

	return androidResources.parseXml(xmlCode);
}