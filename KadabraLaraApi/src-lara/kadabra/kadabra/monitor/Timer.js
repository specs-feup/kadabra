import TimerBase from "@specs-feup/lara/api/lara/code/TimerBase.js";
//import { newField } from "@specs-feup/lara/api/lara/core/AType.js"; //está mal
export default class Timer extends TimerBase {
    name;
    timer;
    access;
    unit;
    constructor(name, timer, unit, fullPath) {
        super();
        this.name = name;
        this.timer = timer;
        if (fullPath) {
            this.access = timer.staticAccess;
        }
        else {
            this.access = name;
        }
        this.unit = unit;
    }
}
/*
    //$timer,
    //		start, stop, pause, getTime
    start($target, when){
        return this.measureCode('start',$target, when);
    }
    stop($target, when){
        return this.measureCode('stop',$target, when);
    }
    pause($target, when){
        return this.measureCode('pause',$target, when);
    }

    getTime(){
        return this.access+'.getTime()';
    }

    getCount(){
        return this.access+'.getCount()';
    }

    getAvg(){
        return this.access+'.getAverage()';
    }

    insertTimerCode(code, $target, when: String){
        switch(when){
        case 'before':
            $target.insertBefore(code);
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

    get($target, when: String, message){

        var code = this.getTime();
        if($target == undefined){
            return code;
        }
        code = %{System.out.println("[[message]]"+[[code]]+"[[this.unit]]");}%; //?
        this.insertTimerCode(code, $target, when);
    }

    measure($target, message, $end){
        this.start($target, "before");
        if(message != undefined){ //this order to guarantee correct code injection
            this.get($end || $target, "after", message);
        }
        this.stop($end || $target, "after");
    }

    measureCode(action, $target, when: String){
        var code = this.access+'.'+action+'();';
        if($target == undefined){
            return code;
        }
        this.insertTimerCode(code, $target, when);
    }

    DEFAULT_CLASS_NAME = "kadabra.utils.Timers";
}

    MillisTimer($targetClass, timerName, fullPath){
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
/*
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
/*
function TaskTimer($class, code, time: number, returnType: String, timerName: String, fullPath: Boolean) {
    var wrapper = primitive2Class(returnType);
    code = '()-> '+code;

    $class.exec $f: newField(["public","static"], "weaver.kadabra.monitor.TaskTimer<"+wrapper+">", timerName, "new TaskTimer<>("+code+", " +time+")");
    const $field = $f;

    newName = $field.name;
    if(fullPath){
        prefix = $class.qualifiedName+".";
        newName = prefix + newName;
    }
    const start = newName+".execute()";
    const stop = newName+".cancel()";
    const ready = newName+".ready()";
    const get = newName+".get()";
    const getAndStart = newName+".getAndExecute()";

    return {$task, start, stop, ready, get, getAndStart}
}

}
/*
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
*/
//# sourceMappingURL=Timer.js.map