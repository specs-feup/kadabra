import Query from "@specs-feup/lara/api/weaver/Query.js";

/**
 * Opens a window with the AST of Spoon
 */
function ShowAST(name: string = "Spoon Tree"): void {
    const app = Query.root();
    for (const $app1 of app) {
        $app1.ShowAST(name);
    }
}

/**
 * Converts a primitive type to its wrapper class
 */
function convertPrimitive(type: string): { wrapper: string; isPrimitive: boolean } {
    let wrapper: string;
    let isPrimitive = true;

    switch (type) {
        case 'bool':
            wrapper = 'Boolean';
            break;
        case 'int':
            wrapper = 'Integer';
            break;
        case 'char':
            wrapper = 'Character';
            break;
        case 'void':
        case 'byte':
        case 'short':
        case 'long':
        case 'float':
        case 'double':
			wrapper = type.charAt(0).toUpperCase() + type.slice(1);
            break;
        default:
            wrapper = type;
            isPrimitive = false;
            break;
    }
    return { wrapper, isPrimitive };
}

function primitive2Class(type: string): string {
	switch(type) {
		case 'bool':
			return 'Boolean';
		case 'int':
			return 'Integer';
		case 'char': 
			return 'Character';
		case 'void': 
		case 'byte': 
		case 'short':
		case 'long': 
		case 'float':
		case 'double':
			return type.charAt(0).toUpperCase() + type.slice(1);
		default: 
			return type;
	}
}


function PrintOnMain(message: string): void {
	for (const $method of Query.search('class').search('method', 'main')) {
        BeforeExit($method, `System.out.println(${message});`);
    }
}

/**
 * Inserts code before the exit of the main method.
 */
function BeforeExitMain(code: string): void {
	for (const $method of Query.search('class').search('method', 'main')) {
        BeforeExit($method, code);
    }
}

/**
 * Inserts code before the exit of a method.
 */
function BeforeExit(method: any, code: string): void {
    var inserted = false;

    // Try to insert before returns
    for (const stmt of Query.search(method).search('body.return')) {
        stmt.insert("before", code);
        inserted = true;
    }
    if (inserted) return;

    // Try to insert after the last statement (for void methods)
    for (const stmt of Query.search(method).search('body.lastStmt')) {
        stmt.insert("after", code);
        inserted = true;
    }
    if (inserted) return;

    // Else, insert into an empty method
    for (const body of Query.search(method).search('body')) {
        body.insert("replace", code);
    }
}

/**
 * Generates API names for concurrent package components.
 */
function APINames(): {concurrentPackage: string; channel: string; thread: string; product: string} {
    const concurrentPackage = "weaver.kadabra.concurrent";
    const channel = `${concurrentPackage}.KadabraChannel`;
    const thread = `${concurrentPackage}.KadabraThread`;
    const product = `${concurrentPackage}.Product`;

    return {concurrentPackage, channel, thread, product};
}

/**
 * Generates code to retrieve an integer property from the system.
 */
function IntegerProperty(name: string, defaultValue?: string): string {
    let code = `Integer.parseInt(System.getProperty(${name}`;
    if (defaultValue !== undefined) {
        code += `, "${defaultValue}"`;
    }
    code += "))";

    return code;
}

function selectMethods(className: string, methodName: string): string[] {
	return GetMethod(className, methodName);
}
/**
 * Retrieves methods based on class and method names.
 */
function GetMethod(className: string = ".*", methodName?: string): any[] {
    if (!methodName) {
        methodName = className;
        className = ".*";
    }

    var methods: any[] = [];
    for (const method of Query.search('class').search('className','qualifiedName').search('name', 'methodName')) {
        if (methods.length === 0) {
            methods.push(method);
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
 */
function GetClass(className: string = ".*"): any[] {
    var classes: any[] = [];

	for (const cls of Query.search('class').search('name', 'className')) {
        if (classes.length === 0) {
            classes.push(cls);
        } else if (Array.isArray(classes)) {
            classes.push(cls);
        } else {
            classes = [classes, cls];
        }
    }
    return classes;
}
