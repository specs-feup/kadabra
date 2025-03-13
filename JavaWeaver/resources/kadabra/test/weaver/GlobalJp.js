laraImport("weaver.Query");

// App children
for($app of Query.search("app").get()){
	console.log("app num children: " + $app.numChildren);	
}
	
// File children
for($file of Query.search("file").get()){
	console.log("file num children: " + $file.numChildren);	
}

// Statement children
for($statement of Query.search("type").search("method","foo").search("statement").get()){
	console.log("stmt children: " + $statement.children);
	console.log("stmt child 0: " + $statement.child(0));
	console.log("stmt num children: " + $statement.numChildren);
	break;
}


// App ast
for($app of Query.search("app").get()){
	console.log("app ast:\n" + $app.ast);	
}
