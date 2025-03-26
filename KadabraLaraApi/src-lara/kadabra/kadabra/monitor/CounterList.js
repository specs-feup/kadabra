/**
    Monitor the occurences of a given join point;
*/
import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Body, Class, Constructor, Field, FileJp, Method, } from "../../Joinpoints.js";
export function CountingMonitorList(targetClass, monitorPackage = "pt.up.fe.specs.lara.kadabra.utils") {
    const monitor = GetCountingMonitorList(monitorPackage, "CountingMonitorList");
    let name = "kadabra" + monitor.name;
    let counter = 0;
    const search = Query.searchFrom(targetClass, Field, {
        name: (n) => n == name + counter,
    });
    for (const _ of search) {
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
function GetCountingMonitorList(packageName, simpleName) {
    const jpClasses = Query.search(FileJp).search(Class, {
        name: simpleName,
        packageName: packageName,
    });
    for (const jpClass of jpClasses) {
        return jpClass;
    }
    return NewCountingMonitorList(packageName, simpleName);
}
function NewCountingMonitorList(packageName, simpleName) {
    const className = packageName + "." + simpleName;
    Query.root().newClass(className);
    let monitorClass;
    const classSearch = Query.search(FileJp).search(Class, simpleName);
    for (const c of classSearch) {
        monitorClass = c;
        c.newField(["private"], "int[]", "counter");
        c.newConstructor(["public"], ["int"], ["size"]);
        c.newMethod(["public"], "void", "increment", ["int"], ["id"]);
        c.newMethod(["public"], "int", "get", ["int"], ["id"]);
        c.newMethod(["public"], "void", "reset", ["int"], ["id"]);
    }
    for (const b of classSearch.search(Method, "increment").search(Body)) {
        b.insertReplace("counter[id]++");
    }
    for (const b of classSearch.search(Method, "get").search(Body)) {
        b.insertReplace("return counter[id];");
    }
    for (const b of classSearch.search(Method, "reset").search(Body)) {
        b.insertReplace("counter[id] = 0;");
    }
    for (const b of classSearch.search(Constructor).search(Body)) {
        b.insertReplace("counter = new int[size];");
    }
    return monitorClass;
}
//# sourceMappingURL=CounterList.js.map