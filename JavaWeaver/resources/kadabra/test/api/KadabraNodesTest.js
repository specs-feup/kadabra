laraImport("kadabra.KadabraNodes");
laraImport("weaver.WeaverJps");
laraImport("lara.Strings");

// Insert a join point before
var $expr = WeaverJps.search("method", { name: "foo" })
  .search("expression")
  .getFirst();
$expr.insertBefore(KadabraNodes.comment("A comment"));

// Replace a join point
var $foo2 = WeaverJps.search("method", { name: "foo2" }).getFirst();
$foo2.insertReplace(KadabraNodes.comment("A comment replacing foo2"));

// Remove a join point
var $foo3 = WeaverJps.search("method", { name: "foo3" }).getFirst();
$foo3.remove();

var $foo4 = WeaverJps.search("method", { name: "foo4" }).getFirst();
var $commentStart = $foo4.insertBefore("/*");
var $commentEnd = $foo4.insertAfter("*/");

$commentStart.remove();
$commentEnd.remove();

const $foo5LocalVar = WeaverJps.search("method", { name: "foo5" })
  .search("localVariable")
  .getFirst();

// Create write reference to local variable
const $foo5Var = KadabraNodes.var($foo5LocalVar, true);

// Get initialization, and remove it from declaration
const $foo5Lhs = $foo5LocalVar.init;
$foo5LocalVar.init = undefined;

// Create assignment
const $foo5Assign = KadabraNodes.assignment($foo5Var, $foo5Lhs);

// Add assignment after initialization
$foo5LocalVar.insertAfter($foo5Assign);

//println(WeaverJps.search("class").getFirst().code);

/*	
	// Change name
	var previousName = $foo4.setName($foo4.name + "_" + Strings.uuid()); 
		
	// Restore name
	$foo4.name = previousName; 
*/

/*
	println("Original AST: " + WeaverJps.search("file").getFirst().ast);
	println("Original Code: " + WeaverJps.search("file").getFirst().code);
	
	var $foo4 = WeaverJps.search("method", {name: "foo4"}).getFirst();
	
	
	
	// Replace method with a comment, store the comment node
	var $comment = $foo4.insertReplace("// A comment replacing foo4");
	//var $comment = $foo4.insertReplace(KadabraNodes.comment("A comment replacing foo4"));	

	println("AST after replacing foo4: " + WeaverJps.search("file").getFirst().ast);
	println("Code after replacing foo4: " + WeaverJps.search("file").getFirst().code);	
	
	// Replace the comment with the previous method
	$comment.insertReplace($foo4);




	println("Code: " + WeaverJps.search("file").getFirst().code);
	println("Foo4: " + $foo4.code);
	println("Comment: " + $comment.code);	
	*/

for (var $chainJp of search("method", "snippetExpr").search("new").chain()) {
  $chainJp["new"].insertReplace(KadabraNodes.snippetExpr("null"));
  //println($chainJp["method"].code);
}

// Print modified file
println(WeaverJps.search("file").getFirst().code);
