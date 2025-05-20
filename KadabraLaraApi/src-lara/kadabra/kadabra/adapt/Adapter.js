import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Class, FileJp, Method } from "../../Joinpoints.js";
import { getOrNewClass } from "../Factory.js";
export function CreateClassGenerator(adapterMethod, $interfaceMethod, adapterClass = ".*", $storingClass) {
    let $adaptMethod;
    let generate;
    let generateQualified;
    for (const $method of Query.search(FileJp)
        .search(Class, (c) => RegExp(adapterClass).exec(c.name) !== null)
        .search(Method, adapterMethod)) {
        if ($adaptMethod != undefined) {
            throw new Error("More than one target method generator was found, please define a finer selection");
        }
        const adapter = FunctionGenerator($method, $interfaceMethod, $storingClass);
        $adaptMethod = adapter.$adaptMethod;
        generate = adapter.generate;
        generateQualified = adapter.generateQualified;
    }
    if ($adaptMethod == undefined) {
        throw new Error("Could not find given method generator: " +
            adapterMethod +
            " in " +
            adapterClass);
    }
    return {
        $adaptMethod: $adaptMethod,
        generate: generate,
        generateQualified: generateQualified,
    };
}
export function FunctionGenerator($adapterMethod, $interfaceMethod, $storingClass = getOrNewClass("kadabra.utils.Adapters")) {
    console.log("[FunctionGenerator] Creating new functional class with " +
        $interfaceMethod +
        " and " +
        $adapterMethod);
    const $adaptMethod = $storingClass.newFunctionalClass($interfaceMethod, $adapterMethod);
    const generate = function (...args) {
        let invoke = $adaptMethod + "(";
        const _args = args.slice();
        invoke += _args.join(", ");
        invoke += ")";
        return invoke;
    };
    const generateQualified = function (...args) {
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
}
/**
 *
 */
export function CreateAdapter(target, adapter, name, targetClass = ".*", adapterClass = ".*") {
    let $adaptClass;
    let addField;
    const $methods = Query.search(FileJp)
        .search(Class, (c) => RegExp(targetClass).exec(c.name) !== null)
        .search(Method, { name: target });
    for (const $adaptMethod of Query.search(FileJp)
        .search(Class, (c) => RegExp(adapterClass).exec(c.name) != null)
        .search(Method, { name: adapter })) {
        for (const $method of $methods) {
            if ($method.equals($adaptMethod)) {
                if ($adaptClass != undefined) {
                    throw new Error("More than one target class/adapter method was found, please define a finer selection");
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
export function TransformMethod($target, $adaptMethod, name = defaultTransformMethodName($target, $adaptMethod)) {
    let $adaptClass;
    for (const $class of Query.search(FileJp).search(Class, { name: name })) {
        $adaptClass = $class;
    }
    if ($adaptClass === undefined) {
        try {
            $adaptClass = $target.createAdapter($adaptMethod, name);
        }
        catch (e) {
            if (e instanceof Error)
                console.log(e.message);
        }
    }
    if ($adaptClass === undefined) {
        throw new Error("[TransformMethod] couldn't create $adaptClass");
    }
    //this method returns information regarding the field and class, as well as the methods that can be invoked in the field
    const addField = ($class = $adaptClass, fieldName = $adaptClass.name.charAt(0).toLowerCase() +
        $adaptClass.name.substring(1), init = false) => {
        const $newField = $class.newField(["public", "static"], $adaptClass.qualifiedName, fieldName, "new " + $adaptClass.name + "()");
        const field = {
            name: fieldName,
            $field: $newField,
            addAdapter: "weaver.kadabra.agent.AgentUtils.addAdapter(" +
                fieldName +
                ");",
            adapt: (...args) => {
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
}
function defaultTransformMethodName($target, $adaptMethod) {
    return ($target.name.charAt(0).toUpperCase() +
        $target.name.substring(1) +
        $adaptMethod.name.charAt(0).toUpperCase +
        $adaptMethod.name.substring(1) +
        "Adapter");
}
//# sourceMappingURL=Adapter.js.map