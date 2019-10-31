import kadabra.Utils;
import kadabra.Factory;
function Timer(name, $timer, unit, fullPath){
	this.name = name;
	this.$timer = $timer;
	if(fullPath){
		this.access = $timer.staticAccess;
	}else{
		this.access = name;
	}
	this.unit = unit;
}
//$timer,
//		start, stop, pause, getTime

Timer.millisTimer = function($targetClass, timerName, fullPath){
	$targetClass = $targetClass || null;
	timerName = timerName || "timer";
	fullPath = fullPath || true;
	call timer: MillisTimer($targetClass, timerName, fullPath);
	return new Timer(timerName, timer.$timer, "ms", fullPath);
}

Timer.nanoTimer = function($targetClass, timerName, fullPath){
	$targetClass = $targetClass || null;
	timerName = timerName || "timer";
	fullPath = fullPath || true;
	var timer = new kadabra$monitor$Timer$NanoTimer($targetClass, timerName, fullPath);
	call timer();
	return new Timer(timerName, timer.$timer, "ns", fullPath);
}

Timer.prototype.start = function($target, when){
	return this.measureCode('start',$target, when);
}
Timer.prototype.stop = function($target, when){
	return this.measureCode('stop',$target, when);
}
Timer.prototype.pause = function($target, when){
	return this.measureCode('pause',$target, when);
}

Timer.prototype.getTime = function(){
	return this.access+'.getTime()';
}

Timer.prototype.getCount = function(){
	return this.access+'.getCount()';
}

Timer.prototype.getAvg = function(){
	return this.access+'.getAverage()';
}


Timer.prototype.get = function($target, when, message){

	var code = this.getTime();
	if($target == undefined){
		return code;
	}
	code = %{System.out.println("[[message]]"+[[code]]+"[[this.unit]]");}%;
	Timer.insertTimerCode(code, $target, when);
}

Timer.prototype.measure = function($target, message, $end){
	this.start($target, "before");
	if(message != undefined){ //this order to guarantee correct code injection
		this.get($end || $target, "after", message);
	}
	this.stop($end || $target, "after");
}

Timer.prototype.measureCode = function(action, $target, when){
	var code = this.access+'.'+action+'();';
	if($target == undefined){
		return code;
	}
	Timer.insertTimerCode(code, $target, when);
}

Timer.insertTimerCode = function(code, $target, when){
	switch(when){
	case 'before':
		$target.insert before code;
		break;
	case 'after':
		$target.insert after code;
		break;
	case 'replace':
		$target.replace code;
		break;
	default:
		$target.insert before code;
		break;
	}
}
Timer.DEFAULT_CLASS_NAME = "kadabra.utils.Timers";

aspectdef MillisTimer
	input
		$class = null,
		timerName = "timer",
		fullPath = true//false
	end
	output
		$timer, unit = "ms",
		start, stop, pause, getTime
	end
	call timer: TimeMonitor($class,timerName,fullPath,"MillisTimer");
	$timer = timer.$timer;
	start = timer.start;
	stop = timer.stop;
	pause = timer.pause;
	getTime = timer.getTime;
end

aspectdef NanoTimer
	input
		$class = null,
		timerName = "timer",
		fullPath = true//false
	end
	output
		$timer, unit = "ns",
		start, stop, pause, getTime
	end
	call timer: TimeMonitor($class,timerName,fullPath,"NanoTimer");
	$timer = timer.$timer;
	start = timer.start;
	stop = timer.stop;
	pause = timer.pause;
	getTime = timer.getTime;
end

/**
	Monitor the occurences of a given join point;
*/
aspectdef TimeMonitor
	input
		$class = null,
		timerName = "timer",
		fullPath = true,//false,
		timeProvider = "NanoTimer";
	end
	output
		$timer,
		start, stop, pause, getTime
	end
	if($class === undefined || $class === null){
		call nc: NewTimerClass();
		$class = nc.$class;
	}
	$class.exec $f: newField(["public","static"], "weaver.kadabra.monitor.CodeTimer", timerName, "CodeTimer."+timeProvider+"()");
	$timer = $f;
	newName = $timer.name;
	if(fullPath){
		prefix = $class.qualifiedName+".";
		newName = prefix + newName;
	}
	start = newName+".start();";
	stop = newName+".stop();";
	pause = newName+".pause();";
	getTime = newName+".getTime()";
end

aspectdef NewTimerClass
	output $class end
	select $c=class{qualifiedName==Timer.DEFAULT_CLASS_NAME} end
	apply
		$class = $c;
		return;
	end
	call nc: NewClass(Timer.DEFAULT_CLASS_NAME);
	$class = nc.$class;
end

/**
 * Creates a timed task, which will execute 'time' ms after invoking execute
 */
aspectdef TaskTimer
	input
		$class = null,
		code = 'return null;',
		time = '1',
		returnType = 'Object',
		timerName = "timedTask",
		fullPath = false;
	end
	output
		$task,
		start, stop, ready, get, getAndStart
	end
	var wrapper = primitive2Class(returnType);
	code = '()-> '+code;
	$class.exec $f: newField(["public","static"], "weaver.kadabra.monitor.TaskTimer<"+wrapper+">", timerName, "new TaskTimer<>("+code+", " +time+")");
	$field = $f;
	newName = $field.name;
	if(fullPath){
		prefix = $class.qualifiedName+".";
		newName = prefix + newName;
	}
	start = newName+".execute()";
	stop = newName+".cancel()";
	ready = newName+".ready()";
	get = newName+".get()";
	getAndStart = newName+".getAndExecute()";
end