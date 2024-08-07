/**
	Monitor the occurences of a given join point;
*/
aspectdef CountingMonitorList
	input
		$targetClass,
		monitorPackage = DEFAULT_PACKAGE
	end
	output 
		name,
		increment,
		get,
		reset,
		init
	end
	static
		var DEFAULT_PACKAGE = "pt.up.fe.specs.lara.kadabra.utils";
		var CLASS_NAME = "CountingMonitorList";
	end

	call getter: GetCountingMonitorList(monitorPackage, CLASS_NAME);
	$monitor = getter.$monitor;
//	$monitor = {name: "CountingMonitorList", qualifiedName: "org.lara.CountingMonitorList"};
	name = "kadabra"+$monitor.name;
	

	var counter = 0;
	select $targetClass.field{name == name+counter} end
	apply
		counter++;
	end

	function callMethod(method,arg){
		return name+"."+method+"("+arg+");";
	}
	
	name+=counter;
	get = function(id){
		return callMethod("get",id);
	};
	reset = function(id){
		return callMethod("reset",id);
	};
	increment = function(id){
		return callMethod("increment",id);
	};
	init = function(size){
		var modifiers = ["private", "static"];
		$targetClass.exec newField(modifiers, $monitor.qualifiedName, name, "new "+$monitor.name+"("+size+")");
	};
end

/**
	Returns the counting monitor list. if it does not exist creates a new class
*/
aspectdef GetCountingMonitorList
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
	call generator: NewCountingMonitorList(package, simpleName);
	$monitor = generator.$monitorClass;
end


aspectdef NewCountingMonitorList
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
		$file.exec newField(["private"], "int[]","counter",null);
		$file.exec newConstructor(["public"], [new Pair("int", "size")]);
		$file.exec newMethod(["public"], "void","increment",[new Pair("int", "id")]);
		$file.exec newMethod(["public"], "int","get",[new Pair("int", "id")]);
		$file.exec newMethod(["public"], "void","reset",[new Pair("int", "id")]);
	end

//	console.log("Adding increment");
	select file.class{name==simpleName}.method{"increment"}.body end
	apply
		insert replace 'counter[id]++;';
	end
//	console.log("Adding getValue");
	select file.class{name==simpleName}.method{"get"}.body end
	apply
		insert replace 'return counter[id];';
	end
//	console.log("Adding reset");
	select file.class{name==simpleName}.method{"reset"}.body end
	apply
		insert replace 'counter[id] = 0;';
	end
	select file.class{name==simpleName}.constructor.body end
	apply
		insert replace 'counter = new int[size];';
	end
end
