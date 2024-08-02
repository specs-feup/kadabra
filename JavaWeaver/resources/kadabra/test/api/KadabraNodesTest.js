laraImport("kadabra.KadabraNodes");
laraImport("weaver.Query");
laraImport("lara.Strings");

// Insert a join point before
var $expr = Query.search("method", { name: "foo" })
  .search("expression")
  .getFirst();
$expr.insertBefore(KadabraNodes.comment("A comment"));

// Replace a join point
var $foo2 = Query.search("method", { name: "foo2" }).getFirst();
$foo2.insertReplace(KadabraNodes.comment("A comment replacing foo2"));

// Remove a join point
var $foo3 = Query.search("method", { name: "foo3" }).getFirst();
$foo3.remove();

var $foo4 = Query.search("method", { name: "foo4" }).getFirst();
var $commentStart = $foo4.insertBefore("/*");
var $commentEnd = $foo4.insertAfter("*/");

$commentStart.remove();
$commentEnd.remove();

const $foo5LocalVar = Query.search("method", { name: "foo5" })
  .search("localVariable")
  .getFirst();

// Create write reference to local variable
const $foo5Var = KadabraNodes.var($foo5LocalVar, true);

// Get initialization, and remove it from declaration
//const $foo5Lhs = $foo5LocalVar.init;
//$foo5LocalVar.init = undefined;
const $foo5Lhs = $foo5LocalVar.init.copy();
$foo5LocalVar.init.remove();

// Create assignment
const $foo5Assign = KadabraNodes.assignment($foo5Var, $foo5Lhs);

// Add assignment after initialization
$foo5LocalVar.insertAfter($foo5Assign);

//console.log(Query.search("class").getFirst().code);

/*	
	// Change name
	var previousName = $foo4.setName($foo4.name + "_" + Strings.uuid()); 
		
	// Restore name
	$foo4.name = previousName; 
*/

/*
	console.log("Original AST: " + Query.search("file").getFirst().ast);
	console.log("Original Code: " + Query.search("file").getFirst().code);
	
	var $foo4 = Query.search("method", {name: "foo4"}).getFirst();
	
	
	
	// Replace method with a comment, store the comment node
	var $comment = $foo4.insertReplace("// A comment replacing foo4");
	//var $comment = $foo4.insertReplace(KadabraNodes.comment("A comment replacing foo4"));	

	console.log("AST after replacing foo4: " + Query.search("file").getFirst().ast);
	console.log("Code after replacing foo4: " + Query.search("file").getFirst().code);	
	
	// Replace the comment with the previous method
	$comment.insertReplace($foo4);




	console.log("Code: " + Query.search("file").getFirst().code);
	console.log("Foo4: " + $foo4.code);
	console.log("Comment: " + $comment.code);	
	*/

for (var $chainJp of Query.search("method", "snippetExpr").search("new").chain()) {
  $chainJp["new"].insertReplace(KadabraNodes.snippetExpr("null"));
  //console.log($chainJp["method"].code);
}

// Print modified file
console.log(Query.search("file").getFirst().code);
