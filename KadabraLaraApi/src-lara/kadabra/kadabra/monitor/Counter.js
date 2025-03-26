/**
    Monitor the occurences of a given join point;
*/
import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Class, Field, FileJp, } from "../../Joinpoints.js";
export function NewCounter(jpClass, name = "counter", fullPath = false) {
    const jpField = jpClass.newField(["public", "static"], "weaver.kadabra.monitor.Counter", name, "new Counter()");
    let $counter;
    let newName = jpField.name;
    if (fullPath) {
        const prefix = jpClass.qualifiedName + ".";
        newName = prefix + newName;
    }
    const increment = newName + ".increment()";
    const reset = newName + ".reset()";
    const getValue = newName + ".getValue()";
    return {
        $counter: $counter,
        increment: increment,
        getValue: getValue,
        reset: reset,
        newName: newName,
    };
}
export function CountingMonitor(targetClass, targetMethod, targetStmt, location = "before", monitorPackage = "pt.up.fe.specs.lara.kadabra.utils") {
    const monitorClass = GetCountingMonitor(monitorPackage, "CountingMonitor");
    let name = "kadabra" + monitorClass.name;
    let counter = 0;
    const search = Query.searchFrom(targetClass, Field, {
        name: (n) => n == name + counter,
    });
    for (const _ of search) {
        counter++;
    }
    name += counter;
    const getValue = name + ".getValue()";
    const reset = name + ".reset()";
    const increment = name + ".increment()";
    const modifiers = ["private"];
    if (targetMethod?.isStatic) {
        modifiers.push("static");
    }
    targetClass.newField(modifiers, monitorClass.qualifiedName, name, "new " + monitorClass.name + "()");
    if (targetStmt != undefined) {
        targetStmt.insert(location, "[[increment]];");
    }
    return {
        name: name,
        increment: increment,
        getValue: getValue,
        reset: reset,
    };
}
/**
    Returns the counting monitor. if it does not exist creates a new class
*/
function GetCountingMonitor(packageName, simpleName) {
    const jpClasses = Query.search(FileJp).search(Class, {
        name: simpleName,
        packageName: packageName,
    });
    for (const jpClass of jpClasses) {
        return jpClass;
    }
    return NewCountingMonitor(packageName, simpleName);
}
function NewCountingMonitor(packageName, simpleName) {
    const className = packageName + "." + simpleName;
    Query.root().newClass(className);
    let monitorClass;
    for (const c of Query.search(FileJp).search(Class, simpleName)) {
        monitorClass = c;
        c.newField(["private"], "int", "counter");
        c.newMethod(["public"], "void", "increment", [], [], "counter++;");
        c.newMethod(["public"], "int", "getValue", [], [], "return counter;");
        c.newMethod(["public"], "void", "reset", [], [], "counter = 0;");
    }
    return monitorClass;
}
//# sourceMappingURL=Counter.js.map