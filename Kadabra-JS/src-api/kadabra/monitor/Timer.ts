import { primitive2Class } from "../Utils.js";
import { newClass } from "../Factory.js";
import TimerBase from "@specs-feup/lara/api/lara/code/TimerBase.js";
import { Class, Field, Joinpoint } from "../../Joinpoints.js";
import Query from "@specs-feup/lara/api/weaver/Query.js";
import { TimerUnit } from "@specs-feup/lara/api/lara/util/TimeUnits.js";

type insertOptions = "before" | "after" | "replace";

abstract class IntermediateTimer extends TimerBase<Joinpoint> {
    static readonly DEFAULT_CLASS_NAME = "kadabra.utils.Timers";
    access: string;
    name: string;
    $timerClass: Field;

    constructor(
        name: string,
        $timerClassInCode: Field,
        unit: TimerUnit,
        fullPath: boolean
    ) {
        super(unit);

        this.name = name;
        this.$timerClass = $timerClassInCode;
        this.access = fullPath ? $timerClassInCode.staticAccess : name;
    }

    abstract start($target: Joinpoint, when: insertOptions): string | undefined;

    abstract stop($target: Joinpoint, when: insertOptions): string | undefined;

    abstract get(
        $target?: Joinpoint,
        when?: insertOptions,
        message?: string
    ): string | undefined;

    measure($target: Joinpoint, message?: string, $end: Joinpoint = $target) {
        let ret1: string | undefined = undefined;

        this.start($target, "before");
        if (message != undefined) {
            //this order to guarantee correct code injection
            ret1 = this.get($end, "after", message);
        }
        const ret2 = this.stop($end, "after");

        return message != undefined ? ret1 : ret2;
    }

    time($start: Joinpoint, prefix?: string, $end?: Joinpoint) {
        return this.measure($start, prefix, $end);
    }

    measureCode(action: string, $target?: Joinpoint, when?: insertOptions) {
        const code = this.access + "." + action + "();";
        if ($target == undefined) {
            return code;
        }

        return IntermediateTimer.insertTimerCode(code, $target, when).code;
    }

    static insertTimerCode(
        code: string,
        $target: Joinpoint,
        when: insertOptions = "before"
    ) {
        return $target.insert(when, code) as Joinpoint;
    }
}

/**
 * Monitor the occurences of a given join point;
 */
function TimeMonitor(
    $class: Class = NewTimerClassInCode(),
    timerName: string = "timer",
    timeProvider: string = "NanoTimer"
): Field {
    return $class.newField(
        ["public", "static"],
        "weaver.kadabra.monitor.CodeTimer",
        timerName,
        "CodeTimer." + timeProvider + "()"
    );
}

function NewTimerClassInCode(): Class {
    const $class = Query.search(Class, {
        qualifiedName: IntermediateTimer.DEFAULT_CLASS_NAME,
    }).getFirst();
    return $class ?? newClass(IntermediateTimer.DEFAULT_CLASS_NAME);
}

export default class Timer extends IntermediateTimer {
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

    start($target?: Joinpoint, when?: insertOptions) {
        return this.measureCode("start", $target, when);
    }

    stop($target?: Joinpoint, when?: insertOptions) {
        return this.measureCode("stop", $target, when);
    }

    pause($target?: Joinpoint, when?: insertOptions) {
        return this.measureCode("pause", $target, when);
    }

    getTime() {
        return this.measureCode("getTime");
    }

    getCount() {
        return this.measureCode("getCount");
    }

    getAvg() {
        return this.measureCode("getAverage");
    }

    get(
        $target?: Joinpoint,
        when: insertOptions = "before",
        message: string = ""
    ): string | undefined {
        let code = this.getTime();
        if ($target == undefined) {
            return code;
        }
        code = `System.out.println("${message}"+${code}+"${this.getUnit().getUnitsString()}");`;

        IntermediateTimer.insertTimerCode(code, $target, when);
    }
}

/**
 * Creates a timed task, which will execute 'time' ms after invoking execute
 */
export class TaskTimer extends IntermediateTimer {
    constructor(
        $class: Class = NewTimerClassInCode(),
        code: string = "return null;",
        delay: number = 1,
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
            "new TaskTimer<>(" + code + ", " + delay + ")"
        );

        super(timerName, $field, TimerUnit.MILLISECONDS, fullPath);
    }

    start() {
        return this.measureCode("execute");
    }

    stop() {
        return this.measureCode("cancel");
    }

    ready() {
        return this.measureCode("ready");
    }

    get() {
        return this.measureCode("get");
    }

    getAndStart() {
        return this.measureCode("getAndExecute");
    }
}
