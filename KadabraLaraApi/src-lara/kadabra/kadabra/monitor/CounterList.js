/**
    Monitor the occurences of a given join point;
*/
import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Class, Constructor, Field, FileJp } from "../../Joinpoints.js";
export function CountingMonitorList(targetClass, monitorPackage = "pt.up.fe.specs.lara.kadabra.utils") {
    const monitor = GetCountingMonitorList(monitorPackage, "CountingMonitorList");
    let name = "kadabra" + monitor.name;
    let counter = 0;
    while (Query.searchFrom(targetClass, Field, {
        name: (n) => n == name + counter,
    }).get().length !== 0) {
        counter++;
    }
    function callMethod(method, arg) {
        return name + "." + method + "(" + arg + ");";
    }
    name += counter;
    const get = function (id) {
        return callMethod("get", id);
    };
    const reset = function (id) {
        return callMethod("reset", id);
    };
    const increment = function (id) {
        return callMethod("increment", id);
    };
    const init = function (size) {
        const modifiers = ["private", "static"];
        targetClass.newField(modifiers, monitor.qualifiedName, name, "new " + monitor.name + "(" + size + ")");
    };
    return {
        name: name,
        increment: increment,
        get: get,
        reset: reset,
        init: init,
    };
}
/**
    Returns the counting monitor list. if it does not exist creates a new class
*/
export function GetCountingMonitorList(packageName, simpleName) {
    const monitorClass = Query.search(FileJp)
        .search(Class, {
        name: simpleName,
        packageName: packageName,
    })
        .getFirst();
    if (monitorClass !== undefined) {
        return monitorClass;
    }
    return NewCountingMonitorList(packageName, simpleName);
}
export function NewCountingMonitorList(packageName, simpleName) {
    const className = packageName + "." + simpleName;
    const monitorClass = Query.root().newClass(className);
    if (monitorClass === undefined) {
        return undefined;
    }
    monitorClass.newField(["private"], "int[]", "counter");
    monitorClass.newMethod(["public"], "void", "increment", ["int"], ["id"], "counter[id]++;");
    monitorClass.newMethod(["public"], "int", "get", ["int"], ["id"], "return counter[id];");
    monitorClass.newMethod(["public"], "void", "reset", ["int"], ["id"], "counter[id] = 0;");
    monitorClass.newConstructor(["public"], ["int"], ["size"]);
    Query.searchFrom(monitorClass, Constructor, "increment")
        .getFirst()
        .body.insertReplace("counter = new int[size];");
    return monitorClass;
}
//# sourceMappingURL=CounterList.js.map