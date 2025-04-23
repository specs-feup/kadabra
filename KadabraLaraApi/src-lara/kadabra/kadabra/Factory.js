import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Class, FileJp, Method } from "../Joinpoints.js";
/**
 * Retrieves an existing class or creates a new one.
 *
 * @param qualifiedName - The qualified name of the class.
 * @param extend - The class to extend (optional).
 * @param implement - The interfaces to implement (optional).
 * @param target - The target join point (optional).
 * @returns The class join point.
 */
export function getOrNewClass(qualifiedName, extend = "", implement = [], target) {
    const existingClass = Query.search(Class, (cls) => RegExp(qualifiedName).exec(cls.qualifiedName) !== null).getFirst();
    return existingClass ?? newClass(qualifiedName, extend, implement, target);
}
/**
 * Creates a new class.
 *
 * @param qualifiedName - The qualified name of the class.
 * @param extend - The class to extend (optional).
 * @param implement - The interfaces to implement (optional).
 * @param target - The target join point (optional).
 * @returns The newly created class join point.
 */
export function newClass(qualifiedName, extend = "", implement = [], target = Query.root()) {
    if (target === undefined) {
        throw new Error("The target join point for a new class must be of type App or FileJp.");
    }
    return target.newClass(qualifiedName, extend, implement);
}
/**
 * Generates a provider function.
 *
 * @param code - The code to be executed by the provider.
 * @param args - The arguments for the provider (optional).
 * @returns The provider function as a string.
 */
export function providerOf(code, args) {
    let providerCode = "(";
    if (args !== undefined && args.length > 0) {
        providerCode += args[0];
        for (let i = 1; i < args.length; i++) {
            providerCode += "," + args[i];
        }
    }
    return providerCode + ") -> " + code;
}
/**
 * Generates a functional interface based on a method of a class.
 *
 * @param targetMethod - The name of the target method.
 * @param targetClass - The name of the target class (optional).
 * @param targetFile - The name of the target file (optional).
 * @param associate - Whether to associate the interface with the class (optional).
 * @param newFile - Whether to create the interface in a new file (optional).
 * @returns The generated functional interface and related information.
 */
export function generateFunctionalInterface(targetMethod, targetClass = ".*", targetFile = ".*", associate = false, newFile = true) {
    let $interface = undefined;
    let tempClass = undefined;
    const search = Query.search(FileJp, (file) => RegExp(targetFile).exec(file.name) !== null)
        .search(Class, (cls) => RegExp(targetClass).exec(cls.qualifiedName) !== null)
        .search(Method, targetMethod)
        .chain();
    let method = undefined;
    for (const result of search) {
        const cls = result.class;
        method = result.method;
        if (tempClass !== undefined) {
            throw new Error(`More than one method to be extracted, please redefine this aspect call. Target method: ${targetMethod}. ` +
                `1st Location${tempClass.qualifiedName}. 2nd Location ${cls.qualifiedName}`);
        }
        console.log(`[LOG] Extracting functional interface from ${cls.name}#${method.name}`);
        const interfacePackage = cls.packageName;
        const interfaceName = "I" + method.name.charAt(0).toUpperCase() + method.name.slice(1);
        const newInterface = cls.extractInterface(interfaceName, interfacePackage, method, associate, newFile);
        if (!newFile) {
            newInterface.modifiers = ["static"];
            cls.addInterface(newInterface);
        }
        $interface = newInterface;
        tempClass = cls;
    }
    if (method === undefined || $interface === undefined) {
        throw new Error("Could not find the method to extract a functional interface, specified by the conditions: " +
            `file{"${targetFile}"}.class{"${targetClass}"}.method{"${targetMethod}"}`);
    }
    const $function = Query.searchFrom($interface, Method, method.name).getFirst();
    return {
        $interface: $interface,
        $defaultMethod: method,
        $function: $function,
        targetMethodName: targetMethod,
    };
}
/**
 * Utility class for defining common modifiers.
 */
export class Mod {
    static PRIVATE = "private";
    static PUBLIC = "public";
    static PROTECTED = "protected";
    static STATIC = "static";
    static PUBLIC_STATIC = ["public", "static"];
    static PRIVATE_STATIC = ["private", "static"];
}
//# sourceMappingURL=Factory.js.map