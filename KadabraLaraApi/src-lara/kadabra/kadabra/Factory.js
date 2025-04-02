import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Class, FileJp, Method, App } from "../Joinpoints.js";
/**
 * Retrieves an existing class or creates a new one.
 *
 * @param qualifiedName - The qualified name of the class.
 * @param extend - The class to extend (optional).
 * @param implement - The interfaces to implement (optional).
 * @param target - The target join point (optional).
 * @returns The class join point.
 */
export function getOrNewClass(qualifiedName, extend = null, implement = [], target) {
    const existingClass = Query.search(Class, (cls) => cls.qualifiedName == qualifiedName).getFirst();
    if (existingClass !== undefined) {
        return existingClass;
    }
    return newClass(qualifiedName, extend, implement, target);
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
export function newClass(qualifiedName, extend = null, implement = [], target) {
    if (target === undefined) {
        target = Query.search(App).getFirst();
    }
    if (!(target instanceof App) || !(target instanceof FileJp)) {
        throw new Error('The target join point for a new class must be of type App or FileJp.');
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
    const class1 = Query.search(App).search(FileJp, (file) => file.name == targetFile).search(Class, (cls) => cls.qualifiedName == targetClass);
    if (class1 === undefined) {
        throw new Error("Could not find the class specified by the conditions: " + "file{" + targetFile + "}.class{" + targetClass + "}");
    }
    const method = class1.search(Method, { name: targetMethod });
    if (method === undefined) {
        throw new Error("Could not find the method to extract a functional interface, specified by the conditions: " +
            "file{" + targetFile + "}.class{" + targetClass + "}.method{" + targetMethod + "}");
    }
    let interface1 = undefined;
    let defaultMethod1 = undefined;
    let tempClass = undefined;
    for (const m of method) {
        if (interface1 !== undefined) {
            throw Error("More than one method to be extracted, please redefine this aspect call. Target method: " +
                targetMethod + ". 1st Location" + tempClass.qualifiedName + ". 2nd Location " + class1.getFirst().qualifiedName);
        }
        console.log("[LOG] Extracting functional interface from " + class1.getFirst().name + "#" + m.name);
        const interfacePackage = class1.getFirst().packageName;
        const interfaceName = "I" + m.name.charAt(0).toUpperCase() + m.name.slice(1);
        const newInterface = class1.getFirst().extractInterface(interfaceName, interfacePackage, m, associate, newFile);
        if (newFile === undefined) {
            newInterface.def('static', class1.getFirst().modifiers);
            class1.getFirst().addInterface(newInterface);
        }
        interface1 = newInterface;
        defaultMethod1 = m;
        tempClass = class1;
    }
    return {
        interface: interface1,
        defaultMethod: defaultMethod1,
        function: Query.searchFrom(interface1).search(Method, { name: targetMethod }).getFirst(),
        targetMethodName: targetMethod
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