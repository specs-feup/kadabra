import Query from "@specs-feup/lara/api/weaver/Query.js";
import { App, Body, Class, Method, Return } from "../Joinpoints.js";

export class Utils {
    /**
     * Opens a window with the AST of Spoon.
     *
     * @param name - The name of the AST window.
     */
    static showAST(name = "Spoon Tree") {
        Query.search(App).getFirst().showAST(name);
    }
    /**
     * Converts a primitive type to its wrapper class.
     *
     * @param type - The primitive type to convert.
     * @returns An object containing the wrapper class and whether the type is primitive.
     */
    static convertPrimitive(type) {
        let wrapper;
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
    static primitiveToClass(type) {
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
     * Inserts a print statement in the main method of a class.
     *
     * @param message - The message to print.
     */
    static printOnMain(message) {
        for (const $method of Query.search(Class).search(Method, "main")) {
            this.beforeExit($method, `System.out.println(${message});`);
        }
    }
    /**
     * Inserts code before the exit of the main method.
     *
     * @param code - The code to insert.
     */
    static beforeExitMain(code) {
        for (const $method of Query.search(Class).search(Method, "main")) {
            this.beforeExit($method, code);
        }
    }
    /**
     * Inserts code before the exit of a method.
     *
     * @param method - The method join point.
     * @param code - The code to insert.
     */
    static beforeExit(method, code) {
        let inserted = false;
        // Try to insert before return statements
        for (const stmt of Query.searchFrom(method.body, Return)) { 
            stmt.insert("before", code);
            inserted = true;
        }
        if (inserted)
            return;
        // Try to insert after the last statement (for void methods)
        for (const stmt of Query.searchFrom(method).search("body.lastStmt")) {
            stmt.insert("after", code);
            inserted = true;
        }
        if (inserted)
            return;
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
    static getAPINames() {
        return {
            concurrentPackage: "weaver.kadabra.concurrent",
            channel: "weaver.kadabra.concurrent.KadabraChannel",
            thread: "weaver.kadabra.concurrent.KadabraThread",
            product: "weaver.kadabra.concurrent.Product"
        };
    }
    /**
     * Generates code to retrieve an integer property from the system.
     *
     * @param name - The name of the property.
     * @param defaultValue - The default value of the property (optional).
     * @returns The generated code as a string.
     */
    static getIntegerProperty(name, defaultValue) {
        let code = `Integer.parseInt(System.getProperty(${JSON.stringify(name)}`;
        if (defaultValue !== undefined) {
            code += `, "${defaultValue}"`;
        }
        code += "))";
        return code;
    }
    /**
     * Retrieves methods based on class and method names.
     *
     * @param className - The name of the class.
     * @param methodName - The name of the method.
     * @returns An array of method join points.
     */
    static getMethod(className = ".*", methodName) {
        if (!methodName) {
            methodName = className;
            className = ".*";
        }
        // select class{qualifiedName~=className}.method{name==methodName} end
        // This is most likely wrong but I don't know how to do it properly
        let methods = undefined;
        for (const method of Query.search(Class, { qualifiedName: className }).search(Method, { name: methodName })) {
            if (methods == undefined) {
                methods = method;
            }
            else if (Array.isArray(methods)) {
                methods.push(method);
            }
            else {
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
    static getClass(className = ".*") {
        // select class{name~=className} end
        // Same here, don't know how to express the ~= operator
        let classes = undefined;
        for (const cls of Query.search(Class, {name: className})) {
            if (classes == undefined) {
                classes = cls;
            }
            else if (Array.isArray(classes)) {
                classes.push(cls);
            }
            else {
                classes = [classes, cls];
            }
        }
        return classes;
    }
}
//# sourceMappingURL=Utils.js.map