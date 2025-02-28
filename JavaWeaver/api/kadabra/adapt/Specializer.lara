/**
 * Prepares a given method call by: <br>
 * * extracting a functional interface <br>
 * * create a field of that type <br>
 * * initialize the field with the called method <br>
 * * replace the call with invocation of the field method <br>
 * NOTE: This is an alias for "PrepareCall" aspect
 */
aspectdef ExtractToField
	input
		$call,
		$method,
   		$fieldLocation,
   		newFile = true,
		$funcInterface = null
   	end
   	output
		$field,
		$interface,
		$interfaceMethod,
		defaultMethod
	end
	check !$call end
	if(!$method){
		$method = $call.getAncestor('method');
	}
	var prep = call PrepareCall($method, $call,$fieldLocation,newFile,$funcInterface);
	$field = prep.$field;
	$interface = prep.interface;
	$interfaceMethod = prep.$interfaceMethod;
	defaultMethod = prep.defaultMethod;
end


/**
 * Prepares a given method call by: <br>
 * * extracting a functional interface <br>
 * * create a field of that type <br>
 * * initialize the field with the called method <br>
 * * replace the call with invocation of the field method
 */
aspectdef PrepareCall
	input
		$method = null,
   		$call = null,
   		$fieldLocation = null,
   		newFile = true,
		$funcInterface = null
   	end
   	output
		$field = null,
		$interface = $funcInterface,
		$interfaceMethod,
		defaultMethod
	end
   
   	check $call != null; $method != null; end
	if($funcInterface == null || $funcInterface == undefined ){
		
		extracted = call ExtractFunctionalInterface(targetClass:$call.declarator,targetMethod:$call.name, newFile: newFile);

		$interface = extracted.$interface;
		
		if($interface != null){
	
			console.log('[LOG] Extracted a functional interface "'+$interface.name+'" based on method "'+$call.name+'"');
		}
	}
	
	defaultMethod = $call.qualifiedDecl+'::'+$call.name;
	select class{qualifiedName==$method.declarator} end
	apply
		$fieldLocation = $class;
	end
	condition $fieldLocation == null end

	if($fieldLocation == null){
		throw 'Could not get a location to insert new field. please verify the input arguments of PrepareCall';
	}
	
	select $interface.($imethod = method){$call.name} end
	apply
		$interfaceMethod = $imethod;
		var baseName = $imethod.name;
		var modifiers = [];
		if($method.isStatic){
			modifiers.push("static");
		}
		
		$field = $fieldLocation.exec newField(modifiers, $interface.qualifiedName, baseName, defaultMethod);

		if($field != null){
			console.log('[LOG] Extracted a field "'+$field.name+'", from call "'+$call.name+'", to '+$field.declarator);
			//Changing call of medianNeighbor to call the local variable
			$call.def target = $field.name;
			$call.def executable = $imethod;
		}
		
	end

	
	if($interface != null && $field != null){
		console.log('[LOG] Call to "'+$call.name+'" (in method "'+$method.name+'") is ready!');
	}
end

/*
 This aspect is used to extract a functional interface for a method of a class
 The interface is extracted to the same package as the target class
 TODO: deal with overloading
*/
aspectdef ExtractFunctionalInterface //Will assume method without overloading (next step)
	input
		targetMethod,
		targetClass = ".*",
		targetFile = ".*",
		associate = false,
		newFile = true
	end
	output
//		$extractedInterfaces = [],
//		$defaultMethods = []
		$interface,
		$defaultMethod,
		$function,
		targetMethodName = targetMethod
	end
		
	//Utils.laraPackage;
	// + targetClass
	select app.file{name~=targetFile}.class{qualifiedName~=targetClass}.method{name==targetMethod} end //
	apply

		
		if($interface != undefined){

			var message = "More than one method to be extracted, please redefine this aspect call. Target method: "+targetMethod;
			message+=". 1st Location"+tempClass.qualifiedName +". 2nd Location "+$class.qualifiedName;
			throw message;
		}
//		if($method.isStatic){ //Need to verify stuff like static, private, protected methods
//			console.log("Current version of the aspect does not allow interface extraction from a static method: "+$class.name+"#"+$method.name);
//			continue;
//		}

		console.log("[LOG] Extracting functional interface from "+$class.name+"#"+$method.name);
		var interfacePackage = $class.packageName;
		var interfaceName = 'I' + $method.name.firstCharToUpper();
		newInterface = $class.exec extractInterface(interfaceName, interfacePackage, $method, associate, newFile);
		if(!newFile){
			newInterface.def modifiers = 'static';
			$class.exec addInterface(newInterface);
		}
//		$extractedInterfaces.push($interface);
//		$defaultMethods.push($method);
		//console.log("[LOG] Setting interface with "+newInterface.name);
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

/* Generate class for functional mapping*/
aspectdef NewMappingClass
	input 
		$interface = null,
		methodName = null,
		getterType = null,
		$target
	end
	output
		$mapClass,
		put,
		contains,
		get
	end
	static
		var DEFAULT_PACKAGE = "pt.up.fe.specs.lara.kadabra.utils";
	end
	
	
	select app end
	apply
		//If no target is specified, then use $app as target
		$target = $target || $app;
	end
	
	var targetMethodFirstCap = methodName.firstCharToUpper();
	var mapClass = DEFAULT_PACKAGE +"."+targetMethodFirstCap+"Caller";
	
	console.log("[LOG] Creating new functional mapping class: "+mapClass);

	var newClass;
	if($target._class.equals('file') || $target._class.equals("app") || 
		$target._class.equals('class') || $target._class.equals('interface')){

		$mapClass = $target.exec mapVersions(mapClass, getterType,$interface, methodName);
	}else{
		throw 'Target join point for new functional method caller has to be: app, file, class or interface';
	}
	put = function(key, value){ return $mapClass.qualifiedName+'.put('+key+','+value+')';},
	contains = function(key){ return $mapClass.qualifiedName+'.contains('+key+')';},
	get = function(param, defaultMethod){

			if(defaultMethod === undefined){
				return $mapClass.qualifiedName+'.get('+param+')';
			}
			return $mapClass.qualifiedName+'.get('+param+','+defaultMethod+')';
	};
end







/* Generate class for functional mapping*/
aspectdef NewFunctionalMethodCaller2
	input 
		$interface = null,
		methodName = null,
		getterType = null,
		defaultMethodStr = null
	end
	output
		$mapClass,
		// get = "get",
		put = "put",
		contains = "contains",
		get
	end

	static
		var DEFAULT_PACKAGE = "pt.up.fe.specs.lara.kadabra.utils";
	end
	check 
		$interface != null;
		methodName != null;
		getterType != null;
		defaultMethodStr != null;
	end
	
	
	
	var targetMethodFirstCap = methodName.firstCharToUpper();
	var mapClass = DEFAULT_PACKAGE +"."+targetMethodFirstCap+"Caller";
	
	console.log("[LOG] Creating new functional mapping class: "+mapClass);
	select app end
	apply
		$mapClass = $app.exec mapVersions(mapClass, getterType,$interface, methodName);
	
		
		get = function(param){
				return $mapClass.qualifiedName+'.get('+param+','+defaultMethodStr+')';
		};
	end
end