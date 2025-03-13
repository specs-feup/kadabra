laraImport("weaver.Query");

for(var $op of Query.search("binaryExpression").get()) {
	console.log("operator: " + $op.operator);
	console.log("operands: " + $op.operands);
	console.log("lhs: " + $op.lhs);
	console.log("rhs: " + $op.rhs);
}

// Ast of the operator copy
console.log(Query.search("binaryExpression").getFirst().copy().ast);

