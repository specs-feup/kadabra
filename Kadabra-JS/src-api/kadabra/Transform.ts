import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Method, InterfaceType, Class, Call, Field, App, FileJp } from "../Joinpoints.js";
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
export function extractToField(call: Call, method?: Method, fieldLocation?: Class, newFile: boolean = true, funcInterface: InterfaceType | null = null)
: { field: Field | null; interface: InterfaceType | null; interfaceMethod: Method | null; defaultMethod: string | null } {
    if (call === undefined) {
        return { field: null, interface: funcInterface, interfaceMethod: null, defaultMethod: null };
    }

    if (method === undefined) {
        method = Query.searchFrom(call, Method).getFirst();
        if (!method) {
            throw new Error("No method found for the given call.");
        }
    }

    if (funcInterface === undefined || funcInterface === null) {
        const extracted = generateFunctionalInterface(call.name, call.declarator, undefined, undefined, newFile);
        funcInterface = extracted.interface;

        if (funcInterface !== undefined) {
            console.log(`[LOG] Extracted a functional interface "${funcInterface.name}" based on method "${call.name}"`);
        }
    }

    const defaultMethod = `${call.qualifiedDecl}::${call.name}`;

    if (fieldLocation === undefined) {
        fieldLocation = Query.search(Class, (cls: Class) => cls.qualifiedName === method.declarator).getFirst();
    }

    if (fieldLocation === undefined) {
        throw new Error("Could not get a location to insert new field. Please verify the input arguments of extractToField.");
    }
    let field: Field | undefined = undefined;
    let interfaceMethod: Method | undefined = undefined;

    for (const m of Query.searchFrom(funcInterface, Method, (method: Method) => method.name === call.name)) {
        const interfaceMethod1 = m;
        const field1 = fieldLocation.newField(
            method.isStatic ? ["static"] : [],
            funcInterface.qualifiedName,
            interfaceMethod1.name,
            defaultMethod
        );

        if (field1 !== undefined) {
            console.log(`[LOG] Extracted a field "${field1.name}", from call "${call.name}", to ${field1.declarator}`);
            call.setTarget(field1.name);
            call.setExecutable(interfaceMethod1)
        }
        field = field1;
        interfaceMethod = interfaceMethod1;
    }

    if (!field) {
        throw new Error("Could not create a field for the functional interface.");
    }
    
    if (!interfaceMethod) {
        throw new Error("Could not create an interfaceMethod for the functional interface.");
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
export function newMappingClass(interfaceJp: InterfaceType | null = null, methodName: string | null = null, getterType: string | null = null, target?: Class | App | FileJp)
: {mapClass: Class; put: (key: string, value: string) => string; contains: (key: string) => string; get: (param: string, defaultMethod?: string) => string;} {
    const DEFAULT_PACKAGE = "pt.up.fe.specs.lara.kadabra.utils";

    if (target === undefined) {
        target = Query.search(App).getFirst();
    }

    if (methodName == null) {
        throw new Error("Method name cannot be null.");
    }

    if (getterType == null) {
        throw new Error("Getter type cannot be null.");
    }

    if (interfaceJp == null) {
        throw new Error("Functional interface cannot be null.");
    }

    const targetMethodFirstCap = methodName.charAt(0).toUpperCase() + methodName.slice(1);
    const mapClassName = `${DEFAULT_PACKAGE}.${targetMethodFirstCap}Caller`;

    console.log(`[LOG] Creating new functional mapping class: ${mapClassName}`);

    let mapClass = undefined;
    if ((target instanceof App) || (target instanceof FileJp) || (target instanceof Class)) { //  (target instanceof InterfaceType)
        mapClass = target.mapVersions(mapClassName, getterType, interfaceJp, methodName);
    } else {
        throw new Error("Target join point for new functional method caller has to be: app, file, class, or interface.");        
    }

    return {
        mapClass: mapClass,
        put: (key: string, value: string) => `${mapClass.qualifiedName}.put(${key}, ${value})`,
        contains: (key: string) => `${mapClass.qualifiedName}.contains(${key})`,
        get: (param: string, defaultMethod?: string) => defaultMethod ? `${mapClass.qualifiedName}.get(${param}, ${defaultMethod})` : `${mapClass.qualifiedName}.get(${param})`,
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
export function newFunctionalMethodCaller(interfaceJp: InterfaceType | null = null, methodName: string | null = null, getterType: string | null = null, defaultMethodStr: string | null = null)
: { mapClass: Class | null; put: string | null; contains: string | null; get: (param: string) => string | null } {
    if (interfaceJp === null || methodName === null || getterType === null || defaultMethodStr === null) {
        return { mapClass: null, put: null, contains: null, get: () => null };
    }

    const DEFAULT_PACKAGE = "pt.up.fe.specs.lara.kadabra.utils";

    const targetMethodFirstCap = methodName.charAt(0).toUpperCase() + methodName.slice(1);
    const mapClassName = `${DEFAULT_PACKAGE}.${targetMethodFirstCap}Caller`;

    console.log(`[LOG] Creating new functional mapping class: ${mapClassName}`);

    const appInstance = Query.search(App).getFirst();
    if (!appInstance) {
        throw new Error("No App instance found.");
    }
    const mapClass = appInstance.mapVersions(mapClassName, getterType, interfaceJp, methodName);

    return {
        mapClass,
        put: "put",
        contains: "contains",
        get: (param: string) => `${mapClass.qualifiedName}.get(${param}, ${defaultMethodStr})`,
    };
}