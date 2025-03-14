laraImport("weaver.Query");
laraImport("kadabra.KadabraNodes");

// Print AST
console.log(Query.search('file').first().ast);

// Print modifiers
var $field = Query.search('field').first();
console.log("Modifiers: " + $field.modifiers.sort());
console.log("Is final: " + $field.isFinal);
console.log("Is static: " + $field.isStatic);
console.log("Has public: " + $field.hasModifier("public"));
console.log("Has private: " + $field.hasModifier("PrIvAtE"));

// Test localVariable.init
var $localVariable = Query.search('method', { name: 'setLocalVariable' }).search('localVariable').first();
console.log("Local var init: " + $localVariable.init.code);
$localVariable.init = KadabraNodes.literal("20", $localVariable.type);
console.log("New local var init: " + $localVariable.init.code);
$localVariable.init = undefined;
console.log("Local var init after removing: " + $localVariable.code);

// Test assignment.lhs and rhs
var $assignment = Query.search('method', { name: 'setAssignment' }).search('assignment').first();
console.log("Assignment: " + $assignment.code);
console.log("Assignment lhs: " + $assignment.lhs.code);
console.log("Assignment rhs: " + $assignment.rhs.code);
$assignment.rhs = KadabraNodes.literal("30", $assignment.rhs.type);
console.log("Assignment after set: " + $assignment.code);

for (var $call of Query.search('method', 'call').search('call').get()) {
	console.log("sub call: " + $call.getAncestor('call'));
}

for (var $assign of Query.search('constructor').search('assignment').get()) {
	var $var = $assign.lhs;
	console.log("Var: " + $var.code);
	console.log("Var is final: " + $var.isFinal);
	console.log("Var decl: " + $var.declaration.code);
	console.log("Var decl is final: " + $var.declaration.isFinal);
	console.log("Var num: " + $var.numChildren);
	console.log("Var child 1: " + $var.child(0).code);
	console.log("Var child 2: " + $var.child(1).code);
	console.log("Var chain length: " + $assign.lhs.referenceChain.length);
}


console.log("Search inclusive: " + Query.searchFromInclusive(Query.search('method', "call").first(), 'localVariable').first().code);


console.log("UnaryExpr node: " + KadabraNodes.unaryExpression("-", KadabraNodes.literal("1", "int")).code);
console.log("BinaryExpr node: " + KadabraNodes.binaryExpression("+", KadabraNodes.literal("1", "int"), KadabraNodes.literal("2", "int")).code);
