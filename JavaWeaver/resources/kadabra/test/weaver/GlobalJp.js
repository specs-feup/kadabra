laraImport("weaver.Query");

// App children
for(var $app of Query.search("app")){
	console.log("app num children: " + $app.numChildren);	
}
	
// File children
for(var $file of Query.search("file")){
	console.log("file num children: " + $file.numChildren);	
}

// Statement children
for(var $statement of Query.search("type").search("method","foo").search("statement")){
	console.log("stmt children: " + $statement.children);
	console.log("stmt child 0: " + $statement.child(0));
	console.log("stmt num children: " + $statement.numChildren);
	break;
}


// App ast
for(var $app of Query.search("app")){
	console.log("app ast:\n" + $app.ast);	
}
