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
export function getOrNewClass(qualifiedName, extend = "", implement = [], target) {
    const existingClass = Query.search(Class, (cls) => RegExp(qualifiedName).exec(cls.qualifiedName) !== null).getFirst();
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
export function newClass(qualifiedName, extend = "", implement = [], target) {
    target ??= Query.search(App).getFirst();
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
    const class1 = Query.search(App)
        .search(FileJp, (file) => RegExp(targetFile).exec(file.name) !== null)
        .search(Class, (cls) => RegExp(targetClass).exec(cls.qualifiedName) !== null);
    if (class1 === undefined) {
        throw new Error("Could not find the class specified by the conditions: " +
            "file{" +
            targetFile +
            "}.class{" +
            targetClass +
            "}");
    }
    const method = class1.search(Method, { name: targetMethod });
    if (method === undefined) {
        throw new Error("Could not find the method to extract a functional interface, specified by the conditions: " +
            "file{" +
            targetFile +
            "}.class{" +
            targetClass +
            "}.method{" +
            targetMethod +
            "}");
    }
    let interface1 = undefined;
    let defaultMethod1 = undefined;
    let tempClass = undefined;
    for (const m of method) {
        const firstClass = class1.getFirst();
        if (firstClass === undefined) {
            throw new Error("Could not extract functional interface because the class is undefined.");
        }
        if (interface1 !== undefined) {
            throw Error("More than one method to be extracted, please redefine this aspect call. Target method: " +
                targetMethod +
                ". 1st Location" +
                (tempClass?.qualifiedName ?? "undefined") +
                ". 2nd Location " +
                firstClass.qualifiedName);
        }
        console.log("[LOG] Extracting functional interface from " +
            firstClass.name +
            "#" +
            m.name);
        const interfacePackage = firstClass.packageName;
        const interfaceName = "I" + m.name.charAt(0).toUpperCase() + m.name.slice(1);
        const newInterface = firstClass.extractInterface(interfaceName, interfacePackage, m, associate, newFile);
        if (newFile === undefined) {
            newInterface.setModifiers(["static"]);
            firstClass.addInterface(newInterface);
        }
        interface1 = newInterface;
        defaultMethod1 = m;
        tempClass = firstClass;
    }
    if (!interface1) {
        throw new Error("Failed to generate functional interface: interface1 is undefined.");
    }
    if (!defaultMethod1) {
        throw new Error("Failed to generate functional interface: defaultMethod1 is undefined.");
    }
    return {
        interface: interface1,
        defaultMethod: defaultMethod1,
        function: (() => {
            const method1 = Query.searchFrom(interface1)
                .search(Method, { name: targetMethod })
                .getFirst();
            if (!method1) {
                throw new Error("Failed to find the function method in the generated interface.");
            }
            return method1;
        })(),
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