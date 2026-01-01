import Query from "@specs-feup/lara/api/weaver/Query.js";
import {
    App,
    Call,
    Class,
    FileJp,
    InterfaceType,
    Method,
} from "../../Joinpoints.js";

/**
 * Prepares a given method call by: <br>
 * * extracting a functional interface <br>
 * * create a field of that type <br>
 * * initialize the field with the called method <br>
 * * replace the call with invocation of the field method <br>
 * NOTE: This is an alias for "PrepareCall" aspect
 */
export function ExtractToField(
    $call: Call | undefined | null,
    $method: Method | undefined | null,
    $fieldLocation: Class | undefined | null,
    newFile = true,
    $funcInterface: InterfaceType | undefined | null = null
) {
    if ($call === undefined || $call === null) {
        return {
            $field: undefined,
            $interface: undefined,
            $interfaceMethod: undefined,
            defaultMethod: undefined,
        };
    }

    if ($method === undefined || $method === null) {
        const ancestor = $call.getAncestor("method") as Method | undefined;
        if (ancestor === undefined) {
            throw new Error("No method found for the given call.");
        }
        $method = ancestor;
    }

    return PrepareCall($method, $call, $fieldLocation, newFile, $funcInterface);
}

/**
 * Prepares a given method call by: <br>
 * * extracting a functional interface <br>
 * * create a field of that type <br>
 * * initialize the field with the called method <br>
 * * replace the call with invocation of the field method
 */
export function PrepareCall(
    $method: Method | null = null,
    $call: Call | null = null,
    $fieldLocation: Class | undefined | null = null,
    newFile = true,
    $funcInterface: InterfaceType | undefined | null = null
) {
    if ($call === null || $method === null) {
        return {
            $field: null,
            $interface: $funcInterface,
            $interfaceMethod: undefined,
            defaultMethod: undefined,
        };
    }

    if ($funcInterface === null || $funcInterface === undefined) {
        const extracted = ExtractFunctionalInterface(
            $call.name,
            $call.declarator,
            ".*",
            false,
            newFile
        );

        $funcInterface = extracted.$interface;

        console.log(
            `[LOG] Extracted a functional interface "${$funcInterface.name}" based on method "${$call.name}"`
        );
    }

    const defaultMethod = $call.qualifiedDecl + "::" + $call.name;
    $fieldLocation ??= Query.search(Class, {
        qualifiedName: $method.declarator,
    }).getFirst();

    if ($fieldLocation === undefined || $fieldLocation === null) {
        throw new Error(
            "Could not get a location to insert new field. please verify the input arguments of PrepareCall"
        );
    }

    let $interfaceMethod;
    let $field;
    for (const method of Query.searchFrom($funcInterface, Method, $call.name)) {
        $interfaceMethod = method;
        const baseName = method.name;
        const modifiers = $method.isStatic ? ["static"] : [];

        $field = $fieldLocation.newField(
            modifiers,
            $funcInterface.qualifiedName,
            baseName,
            defaultMethod
        );

        console.log(
            `[LOG] Extracted a field "${$field.name}", from call "${$call.name}", to ${$field.declarator}`
        );
        //Changing call of medianNeighbor to call the local variable
        $call.target = $field.name;
        $call.executable = method;
    }

    if ($funcInterface != null && $field != null) {
        console.log(
            `[LOG] Call to "${$call.name}" (in method "${$method.name}") is ready!`
        );
    }

    return {
        $field,
        $interface: $funcInterface,
        $interfaceMethod,
        defaultMethod,
    };
}

/*
 This aspect is used to extract a functional interface for a method of a class
 The interface is extracted to the same package as the target class
 TODO: deal with overloading
 //Will assume method without overloading (next step)
*/
export function ExtractFunctionalInterface(
    targetMethod: string,
    targetClass = ".*",
    targetFile = ".*",
    associate = false,
    newFile = true
) {
    const search = Query.search(App)
        .search(FileJp, (f) => RegExp(targetFile).exec(f.name) !== null)
        .search(
            Class,
            (c) => RegExp(targetClass).exec(c.qualifiedName) !== null
        )
        .search(Method, targetMethod)
        .chain();

    let $interface: InterfaceType | undefined = undefined;
    let tempClass: Class | undefined = undefined;
    let $defaultMethod: Method | undefined = undefined;

    let $method: Method | undefined = undefined;
    for (const result of search) {
        const $class = result.class as Class;
        $method = result.method as Method;

        if (tempClass !== undefined) {
            throw new Error(
                `More than one method to be extracted, please redefine this aspect call. Target method: ${targetMethod}. ` +
                    `1st Location${tempClass.qualifiedName}. 2nd Location ${$class.qualifiedName}`
            );
        }

        console.log(
            `[LOG] Extracting functional interface from ${$class.name}#${$method.name}`
        );

        const interfacePackage = $class.packageName;
        const interfaceName =
            "I" + $method.name.charAt(0).toUpperCase() + $method.name.slice(1);
        const newInterface = $class.extractInterface(
            interfaceName,
            interfacePackage,
            $method,
            associate,
            newFile
        );
        if (!newFile) {
            newInterface.modifiers = ["static"];
            $class.addInterface(newInterface);
        }

        $interface = newInterface;
        $defaultMethod = $method;
        tempClass = $class;
    }

    if ($method === undefined || $interface === undefined) {
        throw new Error(
            "Could not find the method to extract a functional interface, specified by the conditions: " +
                `file{"${targetFile}"}.class{"${targetClass}"}.method{"${targetMethod}"}`
        );
    }

    const $function = Query.searchFrom(
        $interface,
        Method,
        $method.name
    ).getFirst()!;

    return {
        $interface,
        $defaultMethod,
        $function,
        targetMethodName: targetMethod,
    };
}

const DEFAULT_PACKAGE = "pt.up.fe.specs.lara.kadabra.utils";

/* Generate class for functional mapping*/
export function NewMappingClass(
    $interface: InterfaceType,
    methodName: string,
    getterType: string,
    $target: Class | App | FileJp = Query.root() as App
) {
    const targetMethodFirstCap =
        methodName.charAt(0).toUpperCase() + methodName.slice(1);
    const mapClass = `${DEFAULT_PACKAGE}.${targetMethodFirstCap}Caller`;

    console.log("[LOG] Creating new functional mapping class: " + mapClass);

    const $mapClass = $target.mapVersions(
        mapClass,
        getterType,
        $interface,
        methodName
    );

    return {
        $mapClass,
        put: (key: string, value: string) =>
            `${$mapClass.qualifiedName}.put(${key},${value})`,
        contains: (key: string) =>
            `${$mapClass.qualifiedName}.contains(${key})`,
        get: (param: string, defaultMethod?: string) => {
            if (defaultMethod === undefined) {
                return `${$mapClass.qualifiedName}.get(${param})`;
            }
            return `${$mapClass.qualifiedName}.get(${param},${defaultMethod})`;
        },
    };
}

/* Generate class for functional mapping*/
export function NewFunctionalMethodCaller2(
    $interface: InterfaceType | null = null,
    methodName: string | null = null,
    getterType: string | null = null,
    defaultMethodStr: string | null = null
) {
    if (
        $interface === null ||
        methodName === null ||
        getterType === null ||
        defaultMethodStr === null
    ) {
        return {
            $mapClass: undefined,
            put: "put",
            contains: "contains",
            get: undefined,
        };
    }

    const targetMethodFirstCap =
        methodName.charAt(0).toUpperCase() + methodName.slice(1);
    const mapClass = `${DEFAULT_PACKAGE}.${targetMethodFirstCap}Caller`;

    console.log("[LOG] Creating new functional mapping class: " + mapClass);

    const app = Query.root() as App;
    const $mapClass = app.mapVersions(
        mapClass,
        getterType,
        $interface,
        methodName
    );

    return {
        $mapClass,
        put: "put",
        contains: "contains",
        get: (param: string) =>
            `${$mapClass.qualifiedName}.get(${param},${defaultMethodStr})`,
    };
}
