import lara.Strings;

aspectdef ConstructorTest

	select constructor end
	apply
		var replaceString = "(app.ClassMutator app";
		if($constructor.params.length > 0) {
			replaceString = replaceString + ", ";
		}
		
		var code =  Strings.replacer( $constructor.srcCode,/\(/ , replaceString);
		$constructor.insertBefore(code);
		
	end
	

	select app end
	apply
		console.log($app.srcCode);
	end
end

