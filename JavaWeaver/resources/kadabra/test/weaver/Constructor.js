laraImport("lara.Strings");
laraImport("weaver.Query");

for(var $constructor of Query.search("constructor")){
	var replaceString = "(app.ClassMutator app";
	if($constructor.params.length > 0) {
		replaceString = replaceString + ", ";
	}
		
	var code =  Strings.replacer( $constructor.srcCode,/\(/ , replaceString);
	$constructor.insertBefore(code);
}
	

for(var $app of Query.search("app")){
	console.log($app.srcCode);	
}

