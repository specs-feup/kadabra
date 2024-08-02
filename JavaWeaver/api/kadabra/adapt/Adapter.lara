import kadabra.Factory;
aspectdef CreateClassGenerator
	input 
		adapterMethod,
		adapterClass=".*",
		$interfaceMethod,
		$storingClass
	end
	output  $adaptMethod, generate, generateQualified end
	select file.class{name~=adapterClass}.method{adapterMethod} end
	apply
		if($adaptMethod != undefined){
			throw 'More than one target method generator was found, please define a finer selection';
		}
		adapter = call FunctionGenerator($method,$interfaceMethod,$storingClass);
		$adaptMethod= adapter.$adaptMethod;
		generate= adapter.generate;
		generateQualified = adapter.generateQualified;
	end
	if($adaptMethod == undefined){
		throw 'Could not find given method generator: '+adapterMethod+" in "+adapter.class;
	}
end

aspectdef FunctionGenerator
	input $adapterMethod, $interfaceMethod, $storingClass end
	output  $adaptMethod, generate, generateQualified end
	if($storingClass == undefined){
		creator = call GetOrNewClass("kadabra.utils.Adapters");
		$storingClass = creator.$class;
	}
	console.log("[FunctionGenerator] Creating new functional class with "+$interfaceMethod +" and "+$adapterMethod);
	$adaptMethod = $storingClass.exec newFunctionalClass($interfaceMethod, $adapterMethod);
	generate = function(){
		var invoke = $adaptMethod +"(";
		var args = Array.prototype.slice.call(arguments);
		invoke += args.join(", ");
		invoke +=")";
		return invoke;
	  };
	 generateQualified = function(){
		var invoke = $storingClass.qualifiedName+"."+$adaptMethod +"(";
		var args = Array.prototype.slice.call(arguments);
		invoke += args.join(", ");
		invoke +=")";
		return invoke;
	  };
end

/**
 *
 */
aspectdef CreateAdapter
	input 
		target,
		adapter,
		targetClass=".*",
		adapterClass=".*",
		name
	end
	output  $adaptClass, addField end
//	output
//		adapter;
//	end
	targetMethod: select app.$ft=file.($target=class){name~=targetClass}.method{name==target} end
	adaptMethod: select app.$fa=file.($adapter=class){name~=adapterClass}.($adaptMethod = method){name==adapter} end
	apply to targetMethod::adaptMethod
		if($adaptClass != undefined){
			throw 'More than one target class/adapter method was found, please define a finer selection';
		}
		name = name || $method.name.firstCharToUpper()+"_"+$adapter.name+"_"+$adaptMethod.name.firstCharToUpper()+"_Adapter";
		adapter = call TransformMethod($method, $adaptMethod, name);//, $target.name.firstCharToUpper()+"Adapter");
		$adaptClass = adapter.$adaptClass;
		addField = adapter.addField;
	end
end

/**
 * Create an adapter based on the target class and the method that transforms the class bytecodes.
 * 
 */
aspectdef TransformMethod
	input $target, $adaptMethod, name end
	output  $adaptClass, addField end //fieldName, $adaptField, addField, addAdapter, adapt,
	name = name || $target.name.firstCharToUpper()+$adaptMethod.name.firstCharToUpper()+"Adapter";
	select class{name} end
	apply
		$adaptClass = $class;
	end
	if($adaptClass == undefined){
		try {
		$adaptClass = $target.exec createAdapter($adaptMethod, name);
		}catch(e){
			e.printStackTrace();
		}
	}
	//this method returns information regarding the field and class, as well as the methods that can be invoked in the field
	addField = function($class, name, init){
		var $class = $class || $adaptClass;
		var fieldName = name || $adaptClass.name.firstCharToLower();
		var init = init || false;
		$newField = $class.exec newField(["public", "static"], $adaptClass.qualifiedName, fieldName, "new "+$adaptClass.name+"()");
		
		var field = {
			name: fieldName,
			$field: $newField,
			addAdapter: "weaver.kadabra.agent.AgentUtils.addAdapter("+fieldName+");",
			adapt: function(){
			    		var invoke = $newField.staticAccess+".adapt(";
			    		var args = Array.prototype.slice.call(arguments);
			    		invoke += args.join(", ");
			    		invoke +=");";
			    		return invoke;
				  },
		};
		if(init){
			$class.exec insertStatic %{[[field.addAdapter]]}%;
		}
	
		return field;
	};
end
