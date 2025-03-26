import Query from "@specs-feup/lara/api/weaver/Query.js";
import { App, Joinpoint } from "../Joinpoints.js";

export class Utils {
    /**
     * Opens a window with the AST of Spoon.
     *
     * @param name - The name of the AST window.
     * 
     * TODO: No idea if this is the correct way to do this
     */
    static showAST(name: string = "Spoon Tree"): void {
        (Query.root() as App).showAST(name);
    }

    /**
     * Converts a primitive type to its wrapper class.
     *
     * @param type - The primitive type to convert.
     * @returns An object containing the wrapper class and whether the type is primitive.
     */
    static convertPrimitive(type: string): { wrapper: string; isPrimitive: boolean } {
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
    static primitiveToClass(type: string): string {
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
    static printOnMain(message: string): void {
        for (const $method of Query.search("class").search("method", "main")) {
            this.beforeExit($method as Joinpoint, `System.out.println(${message});`);
        }
    }

    /**
     * Inserts code before the exit of the main method.
     *
     * @param code - The code to insert.
     */
    static beforeExitMain(code: string): void {
        for (const $method of Query.search("class").search("method", "main")) {
            this.beforeExit($method as Joinpoint, code);
        }
    }

    /**
     * Inserts code before the exit of a method.
     *
     * @param method - The method join point.
     * @param code - The code to insert.
     */
    static beforeExit(method: Joinpoint, code: string): void {
        let inserted = false;

        // Try to insert before return statements
        for (const stmt of Query.searchFrom(method).search("body.return")) {
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
        for (const body of Query.searchFrom(method).search("body")) {
            body.insert("replace", code);
        }
    }

    /**
     * Generates API names for concurrent package components.
     *
     * @returns An object containing the API names.
     */
    static getAPINames(): { concurrentPackage: string; channel: string; thread: string; product: string } {
        const concurrentPackage = "weaver.kadabra.concurrent";
        const channel = `${concurrentPackage}.KadabraChannel`;
        const thread = `${concurrentPackage}.KadabraThread`;
        const product = `${concurrentPackage}.Product`;

        return { concurrentPackage, channel, thread, product };
    }

    /**
     * Generates code to retrieve an integer property from the system.
     *
     * @param name - The name of the property.
     * @param defaultValue - The default value of the property (optional).
     * @returns The generated code as a string.
     */
    static getIntegerProperty(name: string, defaultValue?: string): string {
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
    static getMethods(className: string = ".*", methodName?: string): Joinpoint[] {
        if (!methodName) {
            methodName = className;
            className = ".*";
        }
        // select class{qualifiedName~=className}.method{name==methodName} end
        // This is most likely wrong but I don't know how to do it properly
        let methods: Joinpoint[] = [];
        for (const method of Query.search('class', { qualifiedName: className }).search('method', {name: methodName })) {
            if (methods.length === 0) {
                methods.push(method as Joinpoint);
            } else if (Array.isArray(methods)) {
                methods.push(method as Joinpoint);
            } else {
                methods = [methods, method as Joinpoint];
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
    static GetClass(className: string = ".*"): Joinpoint[] {

        // select class{name~=className} end
        // Same here, don't know how to express the ~= operator
        let classes: Joinpoint[] = [];
        for (const cls of Query.search('class', {name: className})) {
            if (classes.length === 0) {
                classes.push(cls as Joinpoint);
            } else if (Array.isArray(classes)) {
                classes.push(cls as Joinpoint);
            } else {
                classes = [classes, cls as Joinpoint];
            }
        }
        return classes;
    }
}
