/**
	Monitor the occurences of a given join point;
*/

aspectdef NewCounter
	input $class,
		name = "counter",
		fullPath = false;
	end
	output
		$counter,
		inc, get, reset, newName
	end

	$class.exec $f: newField(["public","static"], "weaver.kadabra.monitor.Counter", name, "new Counter()");
	$counter;
	newName = $f.name;
	if(fullPath){
		prefix = $class.qualifiedName+".";
		newName = prefix + newName;
	}
	inc = newName+".increment()";
	reset = newName+".reset()";
	get = newName+".getValue()";
end


aspectdef CountingMonitor
	input
		$targetClass,
		$targetMethod,
		$targetStmt,
		location = "before",
		monitorPackage = DEFAULT_PACKAGE
	end
	output 
		name,
		increment,
		getValue,
		reset
	end
	
	static
		var DEFAULT_PACKAGE = "pt.up.fe.specs.lara.kadabra.utils";
		var CLASS_NAME = "CountingMonitor";
	end
	//if($targetStmt == undefined || $targetClass == undefined || $targetMethod == undefined) ...
	
//	if(!$targetStmt.instanceOf("statement")){
//		errorln('The monitored join point must be of type \'statement\' ');
//		return;
//	}

	call getter: GetCountingMonitor(monitorPackage, CLASS_NAME);
	$monitor = getter.$monitor;
	name = "kadabra"+$monitor.name;
	

	var counter = 0;
	select $targetClass.field{name == name+counter} end
	apply
		counter++;
	end
	
	name+=counter;
	getValue = name + ".getValue()";
	reset = name +".reset()";
	increment = name +".increment()";
	var modifiers = ["private"];
	if($targetMethod != undefined && $targetMethod.isStatic){
		modifiers.push("static");
	}
	
	$targetClass.exec newField(modifiers, $monitor.qualifiedName, name, "new "+$monitor.name+"()");
		
//	console.log("Adding code to monitor the statement");
	if($targetStmt != undefined){
		$targetStmt.insert(location, '[[increment]];');
	}
end

/**
	Returns the counting monitor. if it does not exist creates a new class
*/
aspectdef GetCountingMonitor
	input 
		package,
		simpleName
	end
	output $monitor end
	
	select file.class{(name==simpleName, package==package)} end
	apply
		$monitor = $class;
		return;
	end
	call generator: NewCountingMonitor(package, simpleName);
	$monitor = generator.$monitorClass;
end


aspectdef NewCountingMonitor
	input 
		package,
		simpleName
	end
	output
		$monitorClass
	end

	className = package + "." + simpleName;
//	console.log("New Monitoring Class");
	select app end
	apply
		$app.exec newClass(className,null,null);
	end

//	console.log("Adding fields");
	select file.class{name==simpleName} end
	apply
		$monitorClass = $class;
		$file.exec newField(["private"], "int","counter",null);
		$file.exec newMethod(["public"], "void","increment",[],'counter++;');
		$file.exec newMethod(["public"], "int","getValue",[], 'return counter;');
		$file.exec newMethod(["public"], "void","reset",[],'counter = 0;');
	end

end