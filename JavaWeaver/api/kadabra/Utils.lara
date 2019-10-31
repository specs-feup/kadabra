/** 
	Opens a window with the AST of Spoon
*/
aspectdef ShowAST
	input name = "Spoon Tree" end
	select app end
	apply
		$app.showAST(name);
	end
end


aspectdef ConvertPrimitive
	input type end
	output wrapper, isPrimitive = true end
	switch(type){
		case 'bool':
			wrapper = 'Boolean';
			break;
		case 'int':
			wrapper = 'Integer';
			break;
		case 'char': 
			wrapper = 'Character';
			break;
		case 'void': 
		case 'byte': 
		case 'short':
		case 'long': 
		case 'float':
		case 'double':
			wrapper = type.firstCharToUpper(); 
			break;
		default: 
			wrapper= type; 
			isPrimitive = false;
			break;
	}
end

function primitive2Class(type){

	switch(type){
		case 'bool':
			return 'Boolean';
		case 'int':
			return 'Integer';
		case 'char': 
			return 'Character';
		case 'void': 
		case 'byte': 
		case 'short':
		case 'long': 
		case 'float':
		case 'double':
			return type.firstCharToUpper(); 
		default: 
			return type;
	}
}


function printOnMain(){
	for(var i =0; i< arguments.length; i++){
		call BeforeExitMain(%{System.out.println([[ arguments[i] ]]);}%);
	}
}

aspectdef PrintOnMain
	input message end
	select class.method{"main"} end
	apply
		call BeforeExit($method,%{System.out.println([[message]]);}%);
	end
end

aspectdef BeforeExitMain
	input code end
	select class.method{"main"} end
	apply
		call BeforeExit($method,code);
	end
end


aspectdef BeforeExit
	input $method, code end
	var inserted = false;
	//Try to insert before returns
	select $method.body.return end
	apply
		insert before '[[code]]';
		inserted = true;
	end
	if(inserted){
		return;
	}
	
	//Try to insert after last stmt (void methods)
	select $method.body.lastStmt end
	apply
		insert after '[[code]]';
		inserted = true;
	end
	if(inserted){
		return;
	}

	//Else, is an empty method
	select $method.body end
	apply
		replace '[[code]]';
	end
end


//aspectdef Primitive2Class
//	input value end
//	output wrapper end
//	if(value === "int"){
//		wrapper = "Integer";
//		return;
//	}
//	var primitives = ["char","float","double","boolean", "byte", "short"];
//	var pos = primitives.indexOf(value);
//	
//	wrapper = pos > -1?  primitives[pos]: value;
//end

aspectdef APINames
	output 
		concurrentPackage = "weaver.kadabra.concurrent",
		channel = concurrentPackage+ ".KadabraChannel", 
		thread = concurrentPackage+ ".KadabraThread", 
		product = concurrentPackage+ ".Product" 
	end

end

aspectdef IntegerProperty
	input name, defaultValue end
	output code = "Integer.parseInt(System.getProperty("+JSON.stringify(name) end
	if(defaultValue !== undefined){
		code+=', "'+defaultValue+'\"';
	}
	code+="))";
end

//function GetMethod(/*String*/ name, /*String*/ methodName){
//	
//	getter = call RetrieveMethods(name, methodName);
//	return getter.methods;
//}

function selectMethods(className,methodName){
	var x = call GetMethod(className, methodName);
	return x.methods;
}

aspectdef GetMethod
input 
	className=".*", methodName
end
output methods end
	if(methodName == undefined){
		methodName = className;
		className = ".*";
	}
	select class{qualifiedName~=className}.method{name==methodName} end
	apply
		if(methods == undefined){
			methods = $method;
		}else{
			if(Array.isArray(methods)){
				methods.push($method);
			}else{
				methods = [methods, $method];
			}
		}
	end
end

aspectdef GetClass
input 
	className=".*"
end
output classes end
	select class{name~=className} end
	apply
		if(classes == undefined){
			classes = $class;
		}else{
			if(Array.isArray(classes)){
				classes.push($class);
			}else{
				classes = [classes, $class];
			}
		}
	end
end