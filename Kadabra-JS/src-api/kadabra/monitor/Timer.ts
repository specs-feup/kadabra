import { primitive2Class } from "../Utils.js";
import { newClass } from "../Factory.js";
import TimerBase from "@specs-feup/lara/api/lara/code/TimerBase.js";
import { Class, Field, Joinpoint } from "../../Joinpoints.js";
import Query from "@specs-feup/lara/api/weaver/Query.js";
import { TimerUnit } from "@specs-feup/lara/api/lara/util/TimeUnits.js";

type insertOptions = "before" | "after" | "replace";

export default class Timer extends TimerBase<Joinpoint> {
    static readonly DEFAULT_CLASS_NAME = "kadabra.utils.Timers";
    access: string;
    name: string;
    $timerClass: Field;

    constructor(
        name: string,
        $timerClass: Field,
        unit: TimerUnit,
        fullPath: boolean
    ) {
        super(unit);

        this.name = name;
        this.$timerClass = $timerClass;
        this.access = fullPath ? $timerClass.staticAccess : name;
    }

    static millisTimer(
        $targetClass?: Class,
        timerName = "timer",
        fullPath = true
    ) {
        return new Timer(
            timerName,
            TimeMonitor($targetClass, timerName, "MillisTimer"),
            TimerUnit.MILLISECONDS,
            fullPath
        );
    }

    static nanoTimer(
        $targetClass?: Class,
        timerName: string = "timer",
        fullPath: boolean = true
    ) {
        return new Timer(
            timerName,
            TimeMonitor($targetClass, timerName, "NanoTimer"),
            TimerUnit.NANOSECONDS,
            fullPath
        );
    }

    start($target: Joinpoint, when: insertOptions) {
        return this.measureCode("start", $target, when);
    }

    stop($target: Joinpoint, when: insertOptions) {
        return this.measureCode("stop", $target, when);
    }

    pause($target: Joinpoint, when: insertOptions) {
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

    get($target?: Joinpoint, when: insertOptions, message: string) {
        let code = this.getTime();
        if ($target == undefined) {
            return code;
        }
        code = `System.out.println("${message}"+${code}+"${this.getUnit().getUnitsString()}");`;

        Timer.insertTimerCode(code, $target, when);
    }

    measure($target: Joinpoint, message?: string, $end: Joinpoint = $target) {
        let ret: string | undefined = undefined;

        this.start($target, "before");
        if (message != undefined) {
            //this order to guarantee correct code injection
            ret = this.get($end, "after", message);
        }
        ret = this.stop($end, "after");

        return ret;
    }

    time($start: Joinpoint, prefix?: string, $end?: Joinpoint) {
        return this.measure($start, prefix, $end);
    }

    measureCode(action: string, $target?: Joinpoint, when?: insertOptions) {
        const code = this.access + "." + action + "();";
        if ($target == undefined) {
            return code;
        }

        Timer.insertTimerCode(code, $target, when);
    }

    static insertTimerCode(
        code: string,
        $target: Joinpoint,
        when: insertOptions = "before"
    ) {
        $target.insert(when, code);
    }
}

/**
 * Monitor the occurences of a given join point;
 */
function TimeMonitor(
    $class: Class = NewTimerClass(),
    timerName: string = "timer",
    timeProvider: string = "NanoTimer"
) {
    const $timer = $class.newField(
        ["public", "static"],
        "weaver.kadabra.monitor.CodeTimer",
        timerName,
        "CodeTimer." + timeProvider + "()"
    );

    return $timer;
}

function NewTimerClass() {
    const $class = Query.search(Class, {
        qualifiedName: Timer.DEFAULT_CLASS_NAME,
    }).getFirst();
    return $class ?? newClass(Timer.DEFAULT_CLASS_NAME);
}

/**
 * Creates a timed task, which will execute 'time' ms after invoking execute
 */
export function TaskTimer(
    $class: Class = NewTimerClass(),
    code: string = "return null;",
    time: string = "1",
    returnType: string = "Object",
    timerName: string = "timedTask",
    fullPath: boolean = false
) {
    const wrapper = primitive2Class(returnType);
    code = "()-> " + code;

    const $field = $class.newField(
        ["public", "static"],
        "weaver.kadabra.monitor.TaskTimer<" + wrapper + ">",
        timerName,
        "new TaskTimer<>(" + code + ", " + time + ")"
    );

    const prefix = $class.qualifiedName + ".";
    const newName = fullPath ? prefix + $field.name : $field.name;

    return {
        $field,
        start: newName + ".execute()",
        stop: newName + ".cancel()",
        ready: newName + ".ready()",
        get: newName + ".get()",
        getAndStart: newName + ".getAndExecute()",
    };
}
