import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Class, Field, FileJp, Method } from "../../Joinpoints.js";
import { getOrNewClass } from "../Factory.js";

export function CreateClassGenerator(
    adapterMethod: string,
    adapterClass: string,
    $interfaceMethod: Method,
    $storingClass: Class
) {
    let $adaptMethod: Method | undefined;
    let generate: ((args: string[][]) => string) | undefined;
    let generateQualified: ((args: string[][]) => string) | undefined;

    for (const $method of Query.search(FileJp)
        .search(Class, (c: Class) => RegExp(adapterClass).exec(c.name) !== null)
        .search(Method, adapterMethod)) {
        if ($adaptMethod != undefined) {
            throw new Error(
                "More than one target method generator was found, please define a finer selection"
            );
        }
        const adapter = FunctionGenerator(
            $method,
            $interfaceMethod,
            $storingClass
        );
        $adaptMethod = adapter.$adaptMethod;
        generate = adapter.generate;
        generateQualified = adapter.generateQualified;
    }

    if ($adaptMethod == undefined) {
        throw new Error(
            "Could not find given method generator: " +
                adapterMethod +
                " in " +
                adapterClass
        );
    }

    return {
        $adaptMethod: $adaptMethod,
        generate: generate,
        generateQualified: generateQualified,
    };
}
export function FunctionGenerator(
    $adapterMethod: Method,
    $interfaceMethod: Method,
    $storingClass?: Class
): {
    $adaptMethod: Method;
    generate: (args: string[][]) => string;
    generateQualified: (args: string[][]) => string;
} {
    if ($storingClass == undefined) {
        const $class = getOrNewClass("kadabra.utils.Adapters");
        $storingClass = $class;
    }

    if ($storingClass !== undefined) {
        console.log(
            "[FunctionGenerator] Creating new functional class with " +
                $interfaceMethod +
                " and " +
                $adapterMethod
        );

        const $adaptMethod = $storingClass.newFunctionalClass(
            $interfaceMethod,
            $adapterMethod
        );
        const generate = function (args: string[][]) {
            let invoke = $adaptMethod + "(";
            const _args = args.slice();
            invoke += _args.join(", ");
            invoke += ")";
            return invoke;
        };

        const generateQualified = function (args: string[][]) {
            let invoke = $storingClass.qualifiedName + "." + $adaptMethod + "(";
            const _args = args.slice();
            invoke += _args.join(", ");
            invoke += ")";
            return invoke;
        };

        return {
            $adaptMethod: $adapterMethod,
            generate: generate,
            generateQualified: generateQualified,
        };
    } else {
        throw new Error(
            "[FunctionGenerator] Cant create new functional class with " +
                $interfaceMethod +
                " and " +
                $adapterMethod
        );
    }
}
/**
 *
 */
export function CreateAdapter(
    target: string,
    adapter: string,
    targetClass: string,
    adapterClass: string,
    name: string
) {
    let $adaptClass: Class | undefined;
    let addField;
    const $methods = Query.search(FileJp)
        .search(Class, (c: Class) => RegExp(targetClass).exec(c.name) !== null)
        .search(Method, { name: target })
        .get();

    for (const $adaptMethod of Query.search(FileJp)
        .search(Class, (c: Class) => RegExp(adapterClass).exec(c.name) != null)
        .search(Method, { name: adapter })) {
        for (const $method of $methods) {
            if ($method.equals($adaptMethod)) {
                if ($adaptClass != undefined) {
                    throw new Error(
                        "More than one target class/adapter method was found, please define a finer selection"
                    );
                }

                name ??=
                    $method.name.charAt(0).toUpperCase() +
                    $method.name.substring(1) +
                    "_" +
                    adapter +
                    "_" +
                    $adaptMethod.name.charAt(0).toUpperCase() +
                    $adaptMethod.name.substring(1) +
                    "_Adapter";

                const _adapter = TransformMethod($method, $adaptMethod, name); //, $target.name.firstCharToUpper()+"Adapter");
                $adaptClass = _adapter.$adaptClass;
                addField = _adapter.addField;
            }
        }
    }
    return { $adaptClass: $adaptClass, addField: addField };
}
/**
 * Create an adapter based on the target class and the method that transforms the class bytecodes.
 *
 */
export function TransformMethod(
    $target: Method,
    $adaptMethod: Method,
    name?: string
): {
    $adaptClass: Class;
    addField: (
        $class: Class | undefined,
        name: string,
        init?: boolean
    ) => {
        name: string;
        $field: Field;
        addAdapter: string;
        adapt: (...args: string[]) => string;
    };
} {
    name ??=
        $target.name.charAt(0).toUpperCase() +
        $target.name.substring(1) +
        $adaptMethod.name.charAt(0).toUpperCase +
        $adaptMethod.name.substring(1) +
        "Adapter";

    let $adaptClass: Class | undefined;
    for (const $class of Query.search(FileJp).search(Class, { name: name })) {
        $adaptClass = $class;
    }

    if ($adaptClass == undefined) {
        try {
            $adaptClass = $target.createAdapter($adaptMethod, name);
        } catch (e) {
            if (e instanceof Error) console.log(e.message);
        }
    }

    if ($adaptClass != undefined) {
        //this method returns information regarding the field and class, as well as the methods that can be invoked in the field
        const addField = (
            $class: Class | undefined,
            name: string,
            init?: boolean
        ) => {
            $class ??= $adaptClass;
            const fieldName =
                name ||
                $adaptClass.name.charAt(0).toLowerCase() +
                    $adaptClass.name.substring(1);
            init ??= false;
            const $newField = $class.newField(
                ["public", "static"],
                $adaptClass.qualifiedName,
                fieldName,
                "new " + $adaptClass.name + "()"
            );

            const field = {
                name: fieldName,
                $field: $newField,
                addAdapter:
                    "weaver.kadabra.agent.AgentUtils.addAdapter(" +
                    fieldName +
                    ");",
                adapt: (...args: string[]) => {
                    let invoke = $newField.staticAccess + ".adapt(";
                    const _args = args.slice();
                    invoke += _args.join(", ");
                    invoke += ");";
                    return invoke;
                },
            };

            if (init) {
                $class.insertStatic(`${field.addAdapter}`);
            }

            return field;
        };

        return { $adaptClass: $adaptClass, addField: addField };
    } else {
        throw new Error("[TransformMethod] cant create $adaptClass");
    }
}
