import { Joinpoint } from "../../Joinpoints.js";
import TimerBase from "@specs-feup/lara/api/lara/code/TimerBase.js";

export default class Timer extends TimerBase<Joinpoint> {
    name: string;
    timer;
    access: string;
    unit;

    constructor(name: string, timer, unit, fullPath) {
        super();
        this.name = name;
        this.timer = timer;
        if (fullPath) {
            this.access = timer.staticAccess;
        } else {
            this.access = name;
        }
        this.unit = unit;
    }

    start($target, when) {
        return this.measureCode("start", $target, when);
    }

    stop($target, when) {
        return this.measureCode("stop", $target, when);
    }
    pause($target, when) {
        return this.measureCode("pause", $target, when);
    }

    getTime() {
        return this.access + ".getTime()";
    }

    getCount() {
        return this.access + ".getCount()";
    }

    getAvg() {
        return this.access + ".getAverage()";
    }

    insertTimerCode(code, $target, when: string) {
        switch (when) {
            case "before":
                $target.insertBefore(code);
                break;
            case "after":
                $target.after(code);
                break;
            case "replace":
                $target.replaceWith(code);
                break;
            default:
                $target.insertBefore(code);
                break;
        }
    }

    get($target, when: string, message) {
        let code = this.getTime();
        if ($target == undefined) {
            return code;
        }

        code = `System.out.println("${message}"+${code}+"${this.unit}");`;

        this.insertTimerCode(code, $target, when);
    }

    measure($target, message: string | undefined, $end) {
        this.start($target, "before");
        if (message != undefined) {
            //this order to guarantee correct code injection
            this.get($end || $target, "after", message);
        }
        this.stop($end || $target, "after");
    }

    measureCode(action, $target, when: string) {
        const code = this.access + "." + action + "();";
        if ($target == undefined) {
            return code;
        }
        this.insertTimerCode(code, $target, when);
    }

    MillisTimer($targetClass, timerName: string, fullPath) {
        $targetClass = $targetClass || null;
        timerName = timerName || "timer";
        fullPath = fullPath || true;
        const timer: MillisTimer = new MillisTimer(
            $targetClass,
            timerName,
            fullPath
        );
        return new Timer(timerName, timer.timer, "ms", fullPath);
    }

    NanoTimer($targetClass, timerName: string, fullPath) {
        $targetClass = $targetClass || null;
        timerName = timerName || "timer";
        fullPath = fullPath || true;
        const timer: NanoTimer = new NanoTimer(
            $targetClass,
            timerName,
            fullPath
        );
        return new Timer(timerName, timer.timer, "ns", fullPath);
    }
}

export class MillisTimer extends Timer {
    start;
    stop;
    pause;
    getTime;

    constructor($class = null, timerName: string = "timer", fullPath = true) {
        const timer: TimeMonitor = new TimeMonitor(
            $class,
            timerName,
            fullPath,
            "MillisTimer"
        );

        super(timerName, timer.timer, "ms", fullPath);
        this.start = timer.start;
        this.stop = timer.stop;
        this.pause = timer.pause;
        this.getTime = timer.getTime;
    }
}

export class NanoTimer extends Timer {
    start;
    stop;
    pause;
    getTime;

    constructor($class = null, timerName: string = "timer", fullPath = true) {
        const timer: TimeMonitor = new TimeMonitor(
            $class,
            timerName,
            fullPath,
            "NanoTimer"
        );

        super(timerName, timer.timer, "ms", fullPath);
        this.start = timer.start;
        this.stop = timer.stop;
        this.pause = timer.pause;
        this.getTime = timer.getTime;
    }
}

/**
	Monitor the occurences of a given join point;
*/

export class TimeMonitor {
    timer;
    start;
    stop;
    pause;
    getTime;

    constructor(
        $class = null,
        timerName: string = "timer",
        fullPath = true,
        timeProvider: string = "NanoTimer"
    ) {
        if ($class === undefined || $class === null) {
            const nc: NewTimerClass = new NewTimerClass();
            $class = nc.class;
        }
        const f = $class.newField(
            ["public", "static"],
            "weaver.kadabra.monitor.CodeTimer",
            timerName,
            "CodeTimer." + timeProvider + "()"
        );
        this.timer = f;
        let newName: string = this.timer.name;

        if (fullPath) {
            const prefix = $class.qualifiedName + ".";
            newName = prefix + newName;
        }

        this.start = newName + ".start();";
        this.stop = newName + ".stop();";
        this.pause = newName + ".pause();";
        this.getTime = newName + ".getTime()";
    }
}

export class NewTimerClass {
    class;
}

/*
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
*/

/**
 * Creates a timed task, which will execute 'time' ms after invoking execute
 */

export class TaskTimer {
    task;
    start;
    stop;
    ready;
    get;
    getAndStart;

    constructor(
        $class,
        code,
        time: number,
        returnType: string,
        timerName: string,
        fullPath: boolean
    ) {
        const wrapper: string = "temp"; //primitive2Class(returnType);  in Utils.lara ->Utils.ts, which is being converted in another branch
        code = "()-> " + code;

        const f = $class.newField(
            ["public", "static"],
            "weaver.kadabra.monitor.TaskTimer<" + wrapper + ">",
            timerName,
            "new TaskTimer<>(" + code + ", " + time + ")"
        );
        //$class.exec $f: newField(["public","static"], "weaver.kadabra.monitor.TaskTimer<"+wrapper+">", timerName, "new TaskTimer<>("+code+", " +time+")");
        const $field = f;

        let newName: string = $field.name;
        if (fullPath) {
            const prefix = $class.qualifiedName + ".";
            newName = prefix + newName;
        }
        this.start = newName + ".execute()";
        this.stop = newName + ".cancel()";
        this.ready = newName + ".ready()";
        this.get = newName + ".get()";
        this.getAndStart = newName + ".getAndExecute()";
    }
}
