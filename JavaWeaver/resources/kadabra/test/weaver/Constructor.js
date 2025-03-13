laraImport("lara.Strings");
laraImport("weaver.Query");

for(const $constructor of Query.search("constructor")){
	let replaceString = "(app.ClassMutator app";
	if($constructor.params.length > 0) {
		replaceString = replaceString + ", ";
	}
		
	const code =  Strings.replacer( $constructor.srcCode,/\(/ , replaceString);
	$constructor.insertBefore(code);
}
	

for(const $app of Query.search("app")){
	console.log($app.srcCode);	
}

