import { getAPINames, convertPrimitive } from "./Utils.js";
/**
 * Create an atomic field in the given class. This aspect provides outputs such as get and set of the field
 */
export function NewAtomic($class, type, fieldName, initValue, isStatic = true) {
    const convert = convertPrimitive(type);
    let atomicType = "java.util.concurrent.atomic.Atomic";
    let atomicTypeSimple = "Atomic";
    if (convert.isPrimitive) {
        atomicType += convert.wrapper;
        atomicTypeSimple += convert.wrapper;
    }
    else {
        atomicType += "Reference<" + type + ">";
        atomicTypeSimple += "Reference<>";
    }
    const init = initValue || "";
    const mods = ["private"];
    if (isStatic) {
        mods.push("static");
    }
    const field = $class.newField(mods, atomicType, fieldName, "new " + atomicTypeSimple + "(" + init + ")");
    const reference = field.declarator + "." + field.name;
    const name = field.name;
    const get = reference + ".get()";
    const set = function (value) {
        return reference + ".set(" + value + ")";
    };
    return { reference, name, field, get, set };
}
export function NewThread($class, threadName = "thread", adaptCode = "") {
    const names = getAPINames();
    //Add thread field
    const $thread = $class.newField(["private", "static"], names.thread, threadName, "new " + names.thread + "()");
    //And the method to execute
    const threadMethod = $class.newMethod(["private", "static"], "void", threadName + "Method", [], [], adaptCode);
    const name = $thread.name;
    const reference = $thread.declarator + "." + name;
    const start = reference +
        ".start(" +
        threadMethod.declarator +
        "::" +
        threadMethod.name +
        ")";
    const stop = reference + ".terminate()";
    const running = reference + ".isRunning()";
    const setCode = (code) => {
        threadMethod.body.replaceWith(code);
    }; //allows adaptation code rewritting
    return { start, stop, running, setCode, name, reference };
}
export function NewChannel(keyTypeI, valueTypeI, $class, capacity, channelName = "channel", isStatic = true) {
    let convert = convertPrimitive(keyTypeI);
    const keyType = convert.wrapper;
    convert = convertPrimitive(valueTypeI);
    const valueType = convert.wrapper;
    const names = getAPINames();
    const genericTuple = "<" + keyType + "," + valueType + ">";
    const channelType = names.channel + genericTuple;
    const productType = names.product + genericTuple;
    const init = "KadabraChannel.newInstance(" + capacity + ")";
    const mods = ["private"];
    if (isStatic) {
        mods.push("static");
    }
    const $channeltemp = $class.newField(mods, channelType, channelName, init);
    const $channel = $channeltemp;
    const reference = $channel.declarator + "." + $channel.name;
    const offer = function (key, value) {
        return reference + ".offer(" + key + "," + value + ")";
    };
    const take = reference + ".take()";
    return {
        $channel,
        reference,
        offer,
        take,
        keyType,
        valueType,
        channelType,
        productType,
    };
}
//# sourceMappingURL=Concurrent.js.map