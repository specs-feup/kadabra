laraImport("weaver.Weaver");
laraImport("weaver.Query");
laraImport("lara.Io");
	
//var counter = 0;

// Select binary operators in each file
for($op of Query.search("binaryExpression").get()) {

	// Go over each ancestor to trigger construction of join points not yet implemented
	$op.getAncestor("app");
}
	
console.log("Success");
	
