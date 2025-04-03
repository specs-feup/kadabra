import kadabra.Utils;

/**
 * Create an atomic field in the given class. This aspect provides outputs such as get and set of the field
 */
aspectdef NewAtomic
	input 
		$class, type, fieldName, initValue, isStatic = true
	end
	output reference, name, $field, get,set end
	call convert: Utils.ConvertPrimitive(type);
	atomicType = "java.util.concurrent.atomic.Atomic";
	atomicTypeSimple = "Atomic";
	if(convert.isPrimitive){
		atomicType += convert.wrapper;
		atomicTypeSimple +=convert.wrapper;
	}else{
		atomicType +="Reference<"+type+">";
		atomicTypeSimple +="Reference<>";
	}
	var init = initValue || '';
	var mods = ["private"];
	if(isStatic){
		mods.push("static");
	}
	$class.exec $field: newField(mods, atomicType, fieldName, "new "+atomicTypeSimple+"("+init+")");
	reference = $field.declarator+"."+$field.name;
	name = $field.name;
	get = reference+".get()";
	set = function(value){ return reference+".set("+value+")";};
end

aspectdef NewThread
	input $class, threadName = "thread", adaptCode ="" end
	output start, stop, running, setCode, name, reference end //setCode, $method
	call names: APINames;

	//Add thread field 
	$class.exec $thread: newField(["private", "static"], names.thread, threadName, "new "+names.thread+"()");
	//And the method to execute
	$class.exec $threadMethod: newMethod(["private", "static"], "void", threadName+"Method",[], adaptCode);
	
	name = $thread.name;
	reference = $thread.declarator+"."+name;
	start = reference+".start("+$threadMethod.declarator+"::"+$threadMethod.name+")";
	stop = reference+'.terminate()';
	running = reference+'.isRunning()';

	select $threadMethod.body end
	apply
		setCode = function(code){
			$body.replace "[[code]]"; //allows adaptation code rewritting
		};
	end
end

aspectdef NewChannel
	input keyTypeI, valueTypeI, $class, capacity, channelName = "channel", isStatic=true end
	output $channel, reference, offer, take, keyType, valueType, channelType, productType end

	call convert: ConvertPrimitive(keyTypeI);
	keyType = convert.wrapper;
	call convert: ConvertPrimitive(valueTypeI);
	valueType = convert.wrapper;
	call names: APINames;
	var genericTuple = "<"+keyType+","+valueType+">";
	channelType = names.channel+genericTuple;
	productType = names.product+genericTuple;
	var init = "KadabraChannel.newInstance("+capacity+")";
	var mods = ["private"];
	if(isStatic){
		mods.push("static");
	}
	$class.exec $channeltemp: newField(mods, channelType, channelName, init);
	$channel = $channeltemp;
	reference = $channel.declarator+"."+$channel.name;
	offer = function(key,value){
		return reference+".offer("+key+","+value+")";
	};
	take = reference+".take()";

end