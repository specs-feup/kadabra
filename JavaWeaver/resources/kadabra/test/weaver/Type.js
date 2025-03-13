laraImport("weaver.Query");

for (var $chain of Query.search("var").chain()) {
	console.log("Var type: " + $chain["var"].type);
}

/*
({ a, b } = { a: 10, b: 20 });
console.log(a); // 10
console.log(b); // 20
*/

//var $insertedJp = $var.insertBefore(%{if(true == true) {throw new RuntimeException("TEST");}}%);
