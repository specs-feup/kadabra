import kadabra.Factory;
import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Class, FileJp, Method } from "../../Joinpoints.js";

function CreateClassGenerator(adapterMethod :string, adapterClass:string, $interfaceMethod:Method, $storingClass:Class){
	if(adapterClass == undefined) adapterClass ="*";

	let $adaptMethod;
	let generate;
	let generateQualified


	for(const $method of Query.search(FileJp).search(Class, { "name": (c) => c !== adapterClass }).search(Method,adapterMethod)){
		if($adaptMethod != undefined){
			throw 'More than one target method generator was found, please define a finer selection';
		}
		const adapter =FunctionGenerator($method,$interfaceMethod,$storingClass);
		$adaptMethod= adapter.$adaptMethod;
		generate= adapter.generate;
		generateQualified = adapter.generateQualified;
	}
	if($adaptMethod == undefined){
		throw 'Could not find given method generator: '+adapterMethod+" in "+adapterClass;
	}
	
	return { $adaptMethod:$adaptMethod, generate, generateQualified};
}
function FunctionGenerator($adapterMethod:Method, $interfaceMethod:Method, $storingClass:Class){

	if($storingClass == undefined){
		const creator =GetOrNewClass("kadabra.utils.Adapters");
		$storingClass = creator.$class;
	}
	console.log("[FunctionGenerator] Creating new functional class with "+$interfaceMethod +" and "+$adapterMethod);
	const $adaptMethod = $storingClass.newFunctionalClass($interfaceMethod, $adapterMethod);
	const generate = function(){
		var invoke = $adaptMethod +"(";
		var args = Array.prototype.slice.call(arguments);
		invoke += args.join(", ");
		invoke +=")";
		return invoke;
	  };
	 const generateQualified = function(){
		var invoke = $storingClass.qualifiedName+"."+$adaptMethod +"(";
		var args = Array.prototype.slice.call(arguments);
		invoke += args.join(", ");
		invoke +=")";
		return invoke;
	  };
	return {$adaptMethod:$adapterMethod, generate:generate, generateQualified:generateQualified};
}
/**
 *
 */
function CreateAdapter(target:string, adapter:string, targetClass:string, adapterClass:string,name:string){
		
	let $adaptClass;
	let addField;
	const $target= Query.search(FileJp).search(Class, { "name": (c) => c !== targetClass});
	const $adapter = Query.search(FileJp).search(Class, {"name": (c) => c !== adapterClass});
	for (const $method of $target.search(Method, {"name" : target})){
		for(const $adaptMethod of $adapter.search(Method, {"name": adapter})){
			if($adaptClass != undefined){
				throw 'More than one target class/adapter method was found, please define a finer selection';
			}
			name.charAt(0).toUpperCase()
			name = name || $method.name.charAt(0).toUpperCase()+$method.name.substring(1)+"_"+adapter+"_"+$adaptMethod.name.charAt(0).toUpperCase()+$adaptMethod.name.substring(1)+"_Adapter";
			let _adapter = TransformMethod($method, $adaptMethod, name);//, $target.name.firstCharToUpper()+"Adapter");
			$adaptClass = _adapter.$adaptClass;
			addField = _adapter.addField;
		}
	}
	return {$adaptClas: $adaptClass, addField: addField };
}
/**
 * Create an adapter based on the target class and the method that transforms the class bytecodes.
 * 
 */
function TransformMethod($target: Method, $adaptMethod: Method, name:string){
	name = name || $target.name.charAt(0).toUpperCase()+$target.name.substring(1)+$adaptMethod.name.charAt(0).toUpperCase+$adaptMethod.name.substring(1)+"Adapter";
	
	let $adaptClass :Class;
	for(const $class of Query.search(FileJp).search(Class, {"name":name})){
		$adaptClass = $class;
	}
	if($adaptClass == undefined){
		try {
		$adaptClass = $target.createAdapter($adaptMethod, name);
		}catch(e){
			e.printStackTrace();
		}
	}
	//this method returns information regarding the field and class, as well as the methods that can be invoked in the field
	const addField= ($class:Class, name:string, init:boolean): any=>{
		let _$class = $class || $adaptClass;
		const fieldName = name || $adaptClass.name.charAt(0).toUpperCase()+$adaptClass.name.substring(1);
		const _init = init || false;
		const $newField = _$class.newField(["public", "static"], $adaptClass.qualifiedName, fieldName, "new "+$adaptClass.name+"()");
		
		let field = {
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
			_$class.insertStatic(field.addAdapter);
		}
	
		return field;
	};
	return {$adaptClass:$adaptClass, addField:addField};//fieldName, $adaptField, addField, addAdapter, adapt,

}
