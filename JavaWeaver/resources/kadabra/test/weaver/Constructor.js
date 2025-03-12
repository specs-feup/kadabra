laraImport("lara.Strings");
laraImport("weaver.Query");

for(var $constructor of Query.search('constructor').get()){
	var replaceString = "(app.ClassMutator app";
	if($constructor.params.length > 0) {
		replaceString = replaceString + ", ";
	}
		
	var code =  Strings.replacer( $constructor.srcCode,/\(/ , replaceString);
	$constructor.insertBefore(code);
}
	

for(var $app of Query.search('app').get()){
	console.log($app.srcCode);	
}

