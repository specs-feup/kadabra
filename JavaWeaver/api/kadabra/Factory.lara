
aspectdef GetOrNewClass
	input qualifiedName, extend = null, implement = [], $target end
	output $class end
	select $sel=class{qualifiedName~=qualifiedName} end
	apply
		$class = $sel;
		return;
	end
	
	newClass =	call NewClass(qualifiedName, extend, implement, $target);
	$class = newClass.$class;
end
aspectdef NewClass
	input qualifiedName, extend = null, implement = [], $target end
	output $class end
	
	select app end
	apply
		$target = $app;
	end
	condition $target === undefined end

	if(!Weaver.isJoinPoint($target) && !($target.instanceof('app')  || $target.instanceof('file')  )){
		throw 'The target join point for a new class must be "app" or "file"';
	}
	$target.exec $c:newClass(qualifiedName, extend, implement);
	$class = $c;
end



function providerOf(code, args){
	var providerCode = "(";
	if(args != undefined && args.length > 0){
		providerCode +=args[0];
		for (var i = 1; i < args.length; i++) {
	    	providerCode += "," +args[i];
	  	}
  	}
  	providerCode +=") -> "+code;
  	return providerCode;
}

/*
 This aspect is used to generate a functional interface based on a method of a class
 The interface is generated in the same package as the target class
 TODO: deal with overloading
*/
aspectdef GenerateFunctionalInterface //Will assume method without overloading (next step)
	input
		targetMethod,
		targetClass = ".*",
		targetFile = ".*",
		associate = false,
		newFile = true
	end
	output
		$interface,
		$defaultMethod,
		$function,
		targetMethodName = targetMethod
	end
		
	select app.file{name~=targetFile}.class{qualifiedName~=targetClass}.method{name==targetMethod} end //
	apply

		
		if($interface != undefined){

			var message = "More than one method to be extracted, please redefine this aspect call. Target method: "+targetMethod;
			message+=". 1st Location"+tempClass.qualifiedName +". 2nd Location "+$class.qualifiedName;
			throw message;
		}
		console.log("[LOG] Extracting functional interface from "+$class.name+"#"+$method.name);
		var interfacePackage = $class.packageName;
		var interfaceName = 'I' + $method.name.firstCharToUpper();
		newInterface = $class.exec extractInterface(interfaceName, interfacePackage, $method, associate, newFile);
		if(!newFile){
			newInterface.def modifiers = 'static';
			$class.exec addInterface(newInterface);
		}
		$interface = newInterface;
		$defaultMethod = $method;
		tempClass = $class;
	end

	if($method == undefined){
		throw ('Could not find the method to extract a functional interface, specified by the conditions: '
			+ 'file{"'+targetFile+ '"}'
	    	+ '.class{"' + targetClass+ '"}'
			+ '.method{"' + targetMethod+'"}'
		);
	}
		
	select $interface.method{$method.name} end
	apply
		$function = $method;
	end
end

function Mod(){}
Mod.PRIVATE = "private";
Mod.PUBLIC = "private";
Mod.PROTECTED = "private";
Mod.STATIC = "private";
Mod.PUBLIC_STATIC = ["public", "static"];
Mod.PRIVATE_STATIC = ["private", "static"];