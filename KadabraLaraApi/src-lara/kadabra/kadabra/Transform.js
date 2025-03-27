import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Method, Class } from "../Joinpoints.js";
import { Factory } from "./Factory.js";
/**
 * Utility class for transforming method calls and generating mapping classes.
 */
export class Transform {
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
    static extractToField(call, method, fieldLocation, newFile = true, funcInterface = null) {
        // check $call end
        // Is 'check' this:
        if (!call) {
            return null;
        }
        if (!method) {
            method = call.getAncestor('method');
        }
        if (!funcInterface) {
            const extracted = Factory.generateFunctionalInterface(call.name, call.declarator, undefined, undefined, newFile);
            funcInterface = extracted.interface;
            if (funcInterface) {
                console.log(`[LOG] Extracted a functional interface "${funcInterface.name}" based on method "${call.name}"`);
            }
        }
        const defaultMethod = `${call.qualifiedDecl}::${call.name}`;
        if (!fieldLocation) {
            fieldLocation = Query.search(Class, (cls) => cls.qualifiedName === method.declarator).getFirst();
        }
        if (!fieldLocation) {
            throw new Error("Could not get a location to insert new field. Please verify the input arguments of PrepareCall");
        }
        let field = undefined;
        let interfaceMethod = undefined;
        // select $interface.($imethod = method){$call.name} end
        for (const m of Query.searchFrom(funcInterface, Method, (method) => method.name === call.name)) {
            const interfaceMethod1 = m;
            const field1 = fieldLocation.newField(method.isStatic ? ["static"] : [], funcInterface.qualifiedName, interfaceMethod1.name, defaultMethod);
            if (field1) {
                console.log(`[LOG] Extracted a field "${field1.name}", from call "${call.name}", to ${field1.declarator}`);
                call.def("target", { name: field1.name });
                call.def("executable", interfaceMethod1);
            }
            field = field1;
            interfaceMethod = interfaceMethod1;
        }
        if (funcInterface && field) {
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
    static newMappingClass(interfaceJp = null, methodName = null, getterType = null, target) {
        const DEFAULT_PACKAGE = "pt.up.fe.specs.lara.kadabra.utils";
        if (!target) {
            target = Query.root();
        }
        const targetMethodFirstCap = methodName.charAt(0).toUpperCase() + methodName.slice(1);
        const mapClassName = `${DEFAULT_PACKAGE}.${targetMethodFirstCap}Caller`;
        console.log(`[LOG] Creating new functional mapping class: ${mapClassName}`);
        if (!["file", "app", "class", "interface"].includes(target.name)) {
            throw new Error("Target join point for new functional method caller has to be: app, file, class, or interface.");
        }
        const mapClass = target.mapVersions(mapClassName, getterType, interfaceJp, methodName);
        return {
            mapClass,
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
    static newFunctionalMethodCaller(interfaceJp = null, methodName = null, getterType = null, defaultMethodStr = null) {
        /*
        output
            $mapClass,
            // get = "get",
            put = "put",
            contains = "contains",
            get
        end

        This is the original lara output. Should I keep the default returns here?
        */
        if (!interfaceJp || !methodName || !getterType || !defaultMethodStr) {
            return null;
        }
        const DEFAULT_PACKAGE = "pt.up.fe.specs.lara.kadabra.utils";
        const targetMethodFirstCap = methodName.charAt(0).toUpperCase() + methodName.slice(1);
        const mapClassName = `${DEFAULT_PACKAGE}.${targetMethodFirstCap}Caller`;
        console.log(`[LOG] Creating new functional mapping class: ${mapClassName}`);
        const mapClass = Query.root().mapVersions(mapClassName, getterType, interfaceJp, methodName);
        return {
            mapClass,
            get: (param) => `${mapClass.qualifiedName}.get(${param}, ${defaultMethodStr})`,
        };
    }
}
//# sourceMappingURL=Transform.js.map