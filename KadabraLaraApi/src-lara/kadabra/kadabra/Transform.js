import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Method, Class, App, FileJp } from "../Joinpoints.js";
import { generateFunctionalInterface } from "./Factory.js";
/**
 * Prepares a given method call by:
 * - Extracting a functional interface.
 * - Creating a field of that type.
 * - Initializing the field with the called method.
 * - Replacing the call with invocation of the field method.
 *
 * @param call - The method call join point.
 * @param method - The method join point (optional).
 * @param fieldLocation - The location to insert the field (optional).
 * @param newFile - Whether to create the interface in a new file (default: true).
 * @param funcInterface - The functional interface join point (optional).
 * @returns An object containing the extracted field, interface, and related information.
 */
export function extractToField(call, method, fieldLocation, newFile = true, funcInterface = null) {
    if (call === undefined) {
        return null;
    }
    if (method === undefined) {
        method = Query.searchFrom(call, Method).getFirst();
    }
    if (funcInterface === undefined) {
        const extracted = generateFunctionalInterface(call.name, call.declarator, undefined, undefined, newFile);
        funcInterface = extracted.interface;
        if (funcInterface !== undefined) {
            console.log(`[LOG] Extracted a functional interface "${funcInterface.name}" based on method "${call.name}"`);
        }
    }
    const defaultMethod = `${call.qualifiedDecl}::${call.name}`;
    if (fieldLocation === undefined) {
        fieldLocation = Query.search(Class, (cls) => cls.qualifiedName === method.declarator).getFirst();
    }
    if (fieldLocation === undefined) {
        throw new Error("Could not get a location to insert new field. Please verify the input arguments of extractToField.");
    }
    let field = undefined;
    let interfaceMethod = undefined;
    for (const m of Query.searchFrom(funcInterface, Method, (method) => method.name === call.name)) {
        const interfaceMethod1 = m;
        const field1 = fieldLocation.newField(method.isStatic ? ["static"] : [], funcInterface.qualifiedName, interfaceMethod1.name, defaultMethod);
        if (field1 !== undefined) {
            console.log(`[LOG] Extracted a field "${field1.name}", from call "${call.name}", to ${field1.declarator}`);
            call.def("target", { name: field1.name });
            call.def("executable", interfaceMethod1);
        }
        field = field1;
        interfaceMethod = interfaceMethod1;
    }
    if (funcInterface !== undefined && field !== undefined) {
        console.log(`[LOG] Call to "${call.name}" (in method "${method.name}") is ready!`);
    }
    return { field, interface: funcInterface, interfaceMethod, defaultMethod };
}
/**
 * Generates a new mapping class for functional mapping.
 *
 * @param interfaceJp - The functional interface join point.
 * @param methodName - The name of the method.
 * @param getterType - The type of the getter.
 * @param target - The target join point (optional).
 * @returns An object containing the mapping class and related methods.
 */
export function newMappingClass(interfaceJp = null, methodName = null, getterType = null, target) {
    const DEFAULT_PACKAGE = "pt.up.fe.specs.lara.kadabra.utils";
    if (target === undefined) {
        target = Query.search(App).getFirst();
    }
    const targetMethodFirstCap = methodName.charAt(0).toUpperCase() + methodName.slice(1);
    const mapClassName = `${DEFAULT_PACKAGE}.${targetMethodFirstCap}Caller`;
    console.log(`[LOG] Creating new functional mapping class: ${mapClassName}`);
    let mapClass = undefined;
    if ((target instanceof App) || (target instanceof FileJp) || (target instanceof Class)) { //  (target instanceof InterfaceType)
        mapClass = target.mapVersions(mapClassName, getterType, interfaceJp, methodName);
    }
    else {
        throw new Error("Target join point for new functional method caller has to be: app, file, class, or interface.");
    }
    return {
        mapClass: mapClass,
        put: (key, value) => `${mapClass.qualifiedName}.put(${key}, ${value})`,
        contains: (key) => `${mapClass.qualifiedName}.contains(${key})`,
        get: (param, defaultMethod) => defaultMethod ? `${mapClass.qualifiedName}.get(${param}, ${defaultMethod})` : `${mapClass.qualifiedName}.get(${param})`,
    };
}
/**
 * Generates a new functional method caller.
 *
 * @param interfaceJp - The functional interface join point.
 * @param methodName - The name of the method.
 * @param getterType - The type of the getter.
 * @param defaultMethodStr - The default method string.
 * @returns An object containing the mapping class and related methods.
 */
export function newFunctionalMethodCaller(interfaceJp = null, methodName = null, getterType = null, defaultMethodStr = null) {
    if (interfaceJp === undefined || methodName === undefined || getterType === undefined || defaultMethodStr === undefined) {
        return null;
    }
    const DEFAULT_PACKAGE = "pt.up.fe.specs.lara.kadabra.utils";
    const targetMethodFirstCap = methodName.charAt(0).toUpperCase() + methodName.slice(1);
    const mapClassName = `${DEFAULT_PACKAGE}.${targetMethodFirstCap}Caller`;
    console.log(`[LOG] Creating new functional mapping class: ${mapClassName}`);
    const mapClass = Query.search(App).getFirst().mapVersions(mapClassName, getterType, interfaceJp, methodName);
    return {
        mapClass,
        put: "put",
        contains: "contains",
        get: (param) => `${mapClass.qualifiedName}.get(${param}, ${defaultMethodStr})`,
    };
}
//# sourceMappingURL=Transform.js.map