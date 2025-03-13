laraImport("weaver.Query");

// new expression
for (var $new of Query.search("method", "newExpressionTest").search("new")) {
	console.log("New expr: " + $new);
}	

// body
var $body1 = Query.search("method", "bodyTest1").first().body;
$body1.insertBegin("// Comment1");
console.log($body1.code);

var $body2 = Query.search("method", "bodyTest2").first().body;
$body2.insertBegin("// Comment2");
console.log($body2.code);	

// method
var $method1 = Query.search("method", "testMethod1").first();
for (var $param of $method1.params) {
	console.log("Param " + $param.name);
	console.log("Param is primitive? " + $param.isPrimitive);
}
console.log("Return is primitive? " + $method1.returnRef.isPrimitive);

var $method2 = Query.search("method", "testMethod2").first();
for (var $param of $method2.params) {
	console.log("Param " + $param.name);
	console.log("Param is primitive? " + $param.isPrimitive);
}
console.log("Return is primitive? " + $method2.returnRef.isPrimitive);

// ternary
var $ternary = Query.search("method", "testTernary").search("ternary").first();
console.log($ternary.code);

console.log("Statements test");
for (var $stmt of Query.search("constructor").search("statement")) {
	console.log($stmt.joinPointType);
}
