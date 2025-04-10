// import kadabra.Utils;
// import kadabra.Factory;
import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Class, Field, Joinpoint } from "../../Joinpoints.js";

function primitive2Class(returnType: string): string {
    // import from Utils.ts
    throw new Error("Function not implemented.");
}

function NewClass(DEFAULT_CLASS_NAME: string): Class {
    // import from Factory.ts
    throw new Error("Function not implemented.");
}

export default class Timer {
    name: string;
    $timer: Field;
    access: string;
    unit: string;

    constructor(name: string, $timer: Field, unit: string, fullPath: boolean) {
        this.name = name;
        this.$timer = $timer;
        if (fullPath) {
            this.access = $timer.staticAccess;
        } else {
            this.access = name;
        }
        this.unit = unit;
    }
    static readonly DEFAULT_CLASS_NAME = "kadabra.utils.Timers";

    static millisTimer(
        $targetClass: Class | null = null,
        timerName = "timer",
        fullPath = true
    ) {
        const timer = MillisTimer($targetClass, timerName, fullPath);
        return new Timer(timerName, timer.$timer, "ms", fullPath);
    }

    static nanoTimer(
        $targetClass: Class | null = null,
        timerName = "timer",
        fullPath = true
    ) {
        const timer = NanoTimer($targetClass, timerName, fullPath);
        // call timer();
        return new Timer(timerName, timer.$timer, "ns", fullPath);
    }

    start($target: Joinpoint | undefined | null, when: string) {
        return this.measureCode("start", $target, when);
    }

    stop($target: Joinpoint | undefined | null, when: string) {
        return this.measureCode("stop", $target, when);
    }

    pause($target: Joinpoint | undefined | null, when: string) {
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

    get($target: Joinpoint | undefined | null, when: string, message: string) {
        let code = this.getTime();
        if ($target == undefined) {
            return code;
        }
        code = `System.out.println("${message}"+${code}+"${this.unit}");`;
        Timer.insertTimerCode(code, $target, when);
    }

    measure(
        $target: Joinpoint | undefined | null,
        message: string | undefined | null,
        $end: Joinpoint | undefined | null
    ) {
        this.start($target, "before");
        if (message != undefined) {
            //this order to guarantee correct code injection
            this.get($end || $target, "after", message);
        }
        this.stop($end || $target, "after");
    }

    measureCode(
        action: string,
        $target: Joinpoint | undefined | null,
        when: string
    ) {
        const code = this.access + "." + action + "();";
        if ($target == undefined) {
            return code;
        }
        Timer.insertTimerCode(code, $target, when);
    }

    static insertTimerCode(code: string, $target: Joinpoint, when: string) {
        switch (when) {
            case "before":
                $target.insertBefore(code);
                break;
            case "after":
                $target.insertAfter(code);
                break;
            case "replace":
                $target.replaceWith(code);
                break;
            default:
                $target.insertBefore(code);
                break;
        }
    }
}

interface TimerInterface {
    $timer: Field;
    unit?: string;
    start: string;
    stop: string;
    pause: string;
    getTime: string;
}

export function MillisTimer(
    $class: Class | null = null,
    timerName = "timer",
    fullPath = true
): TimerInterface {
    const timer = TimeMonitor($class, timerName, fullPath, "MillisTimer");

    return {
        $timer: timer.$timer,
        unit: "ms",
        start: timer.start,
        stop: timer.stop,
        pause: timer.pause,
        getTime: timer.getTime,
    };
}

export function NanoTimer(
    $class: Class | null = null,
    timerName = "timer",
    fullPath = true
): TimerInterface {
    const timer = TimeMonitor($class, timerName, fullPath, "NanoTimer");

    return {
        $timer: timer.$timer,
        unit: "ns",
        start: timer.start,
        stop: timer.stop,
        pause: timer.pause,
        getTime: timer.getTime,
    };
}

/**
	Monitor the occurences of a given join point;
*/
export function TimeMonitor(
    $class: Class | undefined | null = null,
    timerName = "timer",
    fullPath = true,
    timeProvider = "NanoTimer"
): TimerInterface {
    if ($class === undefined || $class === null) {
        $class = NewTimerClass();
    }

    const $timer = $class.newField(
        ["public", "static"],
        "weaver.kadabra.monitor.CodeTimer",
        timerName,
        "CodeTimer." + timeProvider + "()"
    );

    let newName = $timer.name;
    if (fullPath) {
        const prefix = $class.qualifiedName + ".";
        newName = prefix + newName;
    }

    return {
        $timer: $timer,
        start: newName + ".start()",
        stop: newName + ".stop();",
        pause: newName + ".pause();",
        getTime: newName + ".getTime()",
    };
}

export function NewTimerClass() {
    const search = Query.search(Class, {
        qualifiedName: Timer.DEFAULT_CLASS_NAME,
    });

    for (const c of search) {
        return c;
    }
    return NewClass(Timer.DEFAULT_CLASS_NAME);
}

/**
 * Creates a timed task, which will execute 'time' ms after invoking execute
 */
export function TaskTimer(
    $class: Class | null = null,
    code = "return null;",
    time = "1",
    returnType = "Object",
    timerName = "timedTask",
    fullPath = false
) {
    if ($class === null) {
        throw new Error(
            "Expected variable $class to be of type Class or null."
        );
    }

    code = `()-> ${code}`;

    const wrapper = primitive2Class(returnType);
    const $field = $class.newField(
        ["public", "static"],
        `weaver.kadabra.monitor.TaskTimer<${wrapper}>`,
        timerName,
        `new TaskTimer<>(${code}, ${time})`
    );

    let newName = $field.name;
    if (fullPath) {
        const prefix = $class.qualifiedName + ".";
        newName = prefix + newName;
    }

    return {
        start: newName + ".execute()",
        stop: newName + ".cancel()",
        ready: newName + ".ready()",
        get: newName + ".get()",
        getAndStart: newName + ".getAndExecute()",
    };
}
