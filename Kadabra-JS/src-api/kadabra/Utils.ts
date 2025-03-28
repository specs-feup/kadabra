import Query from "@specs-feup/lara/api/weaver/Query.js";
import { App, Body, Class, Method, Return } from "../Joinpoints.js";

/**
 * Opens a window with the AST of Spoon.
 *
 * @param name - The name of the AST window.
 */
export function showAST(name: string = "Spoon Tree"): void {
    Query.search(App).getFirst().showAST(name);
}

/**
 * Converts a primitive type to its wrapper class.
 *
 * @param type - The primitive type to convert.
 * @returns An object containing the wrapper class and whether the type is primitive.
 */
export function convertPrimitive(type: string): { wrapper: string; isPrimitive: boolean } {
    let wrapper: string;
    let isPrimitive = true;

    switch (type) {
        case "bool":
            wrapper = "Boolean";
            break;
        case "int":
            wrapper = "Integer";
            break;
        case "char":
            wrapper = "Character";
            break;
        case "void":
        case "byte":
        case "short":
        case "long":
        case "float":
        case "double":
            wrapper = type.charAt(0).toUpperCase() + type.slice(1);
            break;
        default:
            wrapper = type;
            isPrimitive = false;
            break;
    }
    return { wrapper, isPrimitive };
}

/**
 * Converts a primitive type to its wrapper class as a string.
 *
 * @param type - The primitive type to convert.
 * @returns The wrapper class as a string.
 */
export function primitive2Class(type: string): string {
    switch (type) {
        case "bool":
            return "Boolean";
        case "int":
            return "Integer";
        case "char":
            return "Character";
        case "void":
        case "byte":
        case "short":
        case "long":
        case "float":
        case "double":
            return type.charAt(0).toUpperCase() + type.slice(1);
        default:
            return type;
    }
}

/**
 * Inserts multiple print statements in the main method of a class.
 *
 * @param messages - The messages to print.
 */
export function printOnMain(...messages: string[]): void {
    for (const message of messages) {
        beforeExitMain(`System.out.println(${JSON.stringify(message)});`);
    }
}

/**
 * Inserts a print statement in the main method of a class.
 *
 * @param message - The message to print.
 */
export function printOnMain2(message: string): void {
    for (const $method of Query.search(Class).search(Method, "main")) {
        beforeExit($method, `System.out.println(${message});`);
    }
}

/**
 * Inserts code before the exit of the main method.
 *
 * @param code - The code to insert.
 */
export function beforeExitMain(code: string): void {
    for (const $method of Query.search(Class).search(Method, "main")) {
        beforeExit($method, code);
    }
}

/**
 * Inserts code before the exit of a method.
 *
 * @param method - The method join point.
 * @param code - The code to insert.
 */
export function beforeExit(method: Method, code: string): void {
    let inserted = false;

    // Try to insert before return statements
    for (const stmt of Query.searchFrom(method.body, Return)) {
        stmt.insert("before", code);
        inserted = true;
    }
    if (inserted) return;

    // Try to insert after the last statement (for void methods)
    for (const stmt of Query.searchFrom(method).search("body.lastStmt")) {
        stmt.insert("after", code);
        inserted = true;
    }
    if (inserted) return;

    // Else, insert into an empty method
    for (const body of Query.searchFrom(method).search(Body)) {
        body.insert("replace", code);
    }
}

/**
 * Generates API names for concurrent package components.
 *
 * @returns An object containing the API names.
 */
export function getAPINames(): { concurrentPackage: string; channel: string; thread: string; product: string } {
    return {
        concurrentPackage: "weaver.kadabra.concurrent",
        channel: "weaver.kadabra.concurrent.KadabraChannel",
        thread: "weaver.kadabra.concurrent.KadabraThread",
        product: "weaver.kadabra.concurrent.Product",
    };
}

/**
 * Generates code to retrieve an integer property from the system.
 *
 * @param name - The name of the property.
 * @param defaultValue - The default value of the property (optional).
 * @returns The generated code as a string.
 */
export function integerProperty(name: any, defaultValue?: string): string {
    let code = `Integer.parseInt(System.getProperty(${JSON.stringify(name)}`;
    if (defaultValue !== undefined) {
        code += `, "${defaultValue}\\"`;
    }
    return code + "))";
}

/**
 * Retrieves methods based on class and method names.
 *
 * @param className - The name of the class.
 * @param methodName - The name of the method.
 * @returns An array of method join points.
 */
export function getMethod(className: string = ".*", methodName?: string): Method | Method[] {
    if (!methodName) {
        methodName = className;
        className = ".*";
    }

    let methods: Method | Method[] = undefined;
    for (const method of Query.search(Class, { qualifiedName: className }).search(Method, { name: methodName })) {
        if (methods == undefined) {
            methods = method;
        } else if (Array.isArray(methods)) {
            methods.push(method);
        } else {
            methods = [methods, method];
        }
    }
    return methods;
}

/**
 * Retrieves classes based on class names.
 *
 * @param className - The name of the class.
 * @returns An array of class join points.
 */
export function getClass(className: string = ".*"): Class | Class[] {
    let classes: Class | Class[] = undefined;
    for (const cls of Query.search(Class, { name: className })) {
        if (classes == undefined) {
            classes = cls;
        } else if (Array.isArray(classes)) {
            classes.push(cls);
        } else {
            classes = [classes, cls];
        }
    }
    return classes;
}
