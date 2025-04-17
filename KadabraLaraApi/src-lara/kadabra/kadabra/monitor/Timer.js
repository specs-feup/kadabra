import { primitive2Class } from "../Utils.js";
import { newClass } from "../Factory.js";
import TimerBase from "@specs-feup/lara/api/lara/code/TimerBase.js";
import { Class } from "../../Joinpoints.js";
import Query from "@specs-feup/lara/api/weaver/Query.js";
import { TimerUnit } from "@specs-feup/lara/api/lara/util/TimeUnits.js";
class IntermediateTimer extends TimerBase {
    static DEFAULT_CLASS_NAME = "kadabra.utils.Timers";
    access;
    name;
    $timerClass;
    constructor(name, $timerClassInCode, unit, fullPath) {
        super(unit);
        this.name = name;
        this.$timerClass = $timerClassInCode;
        this.access = fullPath ? $timerClassInCode.staticAccess : name;
    }
    getCount() {
        return this.access + ".getCount()";
    }
    getAvg() {
        return this.access + ".getAverage()";
    }
    measure($target, message, $end = $target) {
        let ret1 = undefined;
        this.start($target, "before");
        if (message != undefined) {
            //this order to guarantee correct code injection
            ret1 = this.get($end, "after", message);
        }
        const ret2 = this.stop($end, "after");
        return message != undefined ? ret1 : ret2;
    }
    time($start, prefix, $end) {
        return this.measure($start, prefix, $end);
    }
    measureCode(action, $target, when) {
        const code = this.access + "." + action + "();";
        if ($target == undefined) {
            return code;
        }
        IntermediateTimer.insertTimerCode(code, $target, when);
    }
    static insertTimerCode(code, $target, when = "before") {
        $target.insert(when, code);
    }
}
/**
 * Monitor the occurences of a given join point;
 */
function TimeMonitor($class = NewTimerClassInCode(), timerName = "timer", timeProvider = "NanoTimer") {
    return $class.newField(["public", "static"], "weaver.kadabra.monitor.CodeTimer", timerName, "CodeTimer." + timeProvider + "()");
}
function NewTimerClassInCode() {
    const $class = Query.search(Class, {
        qualifiedName: IntermediateTimer.DEFAULT_CLASS_NAME,
    }).getFirst();
    return $class ?? newClass(IntermediateTimer.DEFAULT_CLASS_NAME);
}
export default class Timer extends IntermediateTimer {
    static millisTimer($targetClass, timerName = "timer", fullPath = true) {
        return new Timer(timerName, TimeMonitor($targetClass, timerName, "MillisTimer"), TimerUnit.MILLISECONDS, fullPath);
    }
    static nanoTimer($targetClass, timerName = "timer", fullPath = true) {
        return new Timer(timerName, TimeMonitor($targetClass, timerName, "NanoTimer"), TimerUnit.NANOSECONDS, fullPath);
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
        return this.measureCode("getTime");
    }
    get($target, when = "before", message = "") {
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
    constructor($class = NewTimerClassInCode(), code = "return null;", time = TimerUnit.NANOSECONDS, returnType = "Object", timerName = "timedTask", fullPath = false) {
        const wrapper = primitive2Class(returnType);
        code = "()-> " + code;
        const $field = $class.newField(["public", "static"], "weaver.kadabra.monitor.TaskTimer<" + wrapper + ">", timerName, "new TaskTimer<>(" + code + ", " + time + ")");
        super(timerName, $field, time, fullPath);
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
//# sourceMappingURL=Timer.js.map