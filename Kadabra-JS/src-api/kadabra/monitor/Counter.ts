/**
	Monitor the occurences of a given join point;
*/

import Query from "@specs-feup/lara/api/weaver/Query.js";
import {
    App,
    Class,
    Field,
    FileJp,
    Method,
    Statement,
} from "../../Joinpoints.js";

export function NewCounter(jpClass: Class, name = "counter", fullPath = false) {
    const jpField = jpClass.newField(
        ["public", "static"],
        "weaver.kadabra.monitor.Counter",
        name,
        "new Counter()"
    );

    let newName = jpField.name;
    if (fullPath) {
        const prefix = jpClass.qualifiedName + ".";
        newName = prefix + newName;
    }
    const increment = newName + ".increment()";
    const reset = newName + ".reset()";
    const getValue = newName + ".getValue()";

    return {
        increment: increment,
        getValue: getValue,
        reset: reset,
        newName: newName,
    };
}

export function CountingMonitor(
    targetClass: Class,
    targetMethod: Method,
    targetStmt: Statement,
    location: "replace" | "after" | "before" = "before",
    monitorPackage = "pt.up.fe.specs.lara.kadabra.utils"
) {
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

    targetClass.newField(
        modifiers,
        monitorClass.qualifiedName,
        name,
        "new " + monitorClass.name + "()"
    );

    if (targetStmt != undefined) {
        targetStmt.insert(location, `${increment};`);
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
function GetCountingMonitor(packageName: string, simpleName: string) {
    const monitorClass = Query.search(FileJp)
        .search(Class, {
            name: simpleName,
            packageName: packageName,
        })
        .getFirst();

    if (monitorClass !== undefined) {
        return monitorClass;
    }

    return NewCountingMonitor(packageName, simpleName);
}

function NewCountingMonitor(packageName: string, simpleName: string) {
    const className = packageName + "." + simpleName;
    const monitorClass = (Query.root() as App).newClass(className);

    if (monitorClass === undefined) {
        return undefined;
    }

    monitorClass.newField(["private"], "int", "counter");
    monitorClass.newMethod(
        ["public"],
        "void",
        "increment",
        [],
        [],
        "counter++;"
    );
    monitorClass.newMethod(
        ["public"],
        "int",
        "getValue",
        [],
        [],
        "return counter;"
    );
    monitorClass.newMethod(["public"], "void", "reset", [], [], "counter = 0;");

    return monitorClass;
}
