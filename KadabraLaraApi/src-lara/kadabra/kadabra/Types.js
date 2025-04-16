export class KTypes {
    static Thread = "weaver.kadabra.concurrent.KadabraThread";
    static Channel = "weaver.kadabra.concurrent.KadabraChannel";
    static Product = "weaver.kadabra.concurrent.Product";
    static ControlPoint = "weaver.kadabra.control.ControlPoint";
    static ControlPointFactory = "weaver.kadabra.control.ControlPointFactory";
    static VersionProviderUtils = "weaver.kadabra.control.utils.ProviderUtils";
    static VersionProvider = "weaver.kadabra.control.utils.VersionProvider";
    static Tuple = "tdrc.tuple.Tuple";
    static Pair = "tdrc.utils.Pair";
    static Atomic = "java.util.concurrent.atomic.Atomic";
    /**
     * Generates a channel type with the specified key and value types.
     */
    static channelOf(key = "Object", value = "Object") {
        return `${this.Channel}<${key},${value}>`;
    }
    /**
     * Generates a product type with the specified key and value types.
     */
    static productOf(key, value) {
        if (key === undefined || value === undefined) {
            return undefined;
        }
        return `${this.Product}<${key},${value}>`;
    }
    /**
     * Generates a control point type with the specified type.
     */
    static controlPointOf(type = "Object") {
        return `${this.ControlPoint}<${type}>`;
    }
    /**
     * Generates a provider type with the specified type.
     */
    static providerOf(type = "Object") {
        return `${this.VersionProvider}<${type}>`;
    }
    /**
     * Generates a tuple type with the specified type.
     */
    static tupleOf(type) {
        return `${this.Tuple}<${type}>`;
    }
    /**
     * Generates a list type with the specified type.
     */
    static listOf(type) {
        return `java.util.List<${type}>`;
    }
    /**
     * Generates a pair type with the specified types.
     */
    static pairOf(type1, type2) {
        return `${this.Pair}<${type1},${type2}>`;
    }
    /**
     * Generates an atomic type with the specified type.
     */
    static atomicOf(type) {
        if (this.isPrimitive(type)) {
            return `${this.Atomic}${this.toWrapper(type)}`;
        }
        return `${this.Atomic}Reference<${type}>`;
    }
    /**
     * Converts a primitive type to its wrapper class.
     */
    static toWrapper(type) {
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
     * Checks if a type is a primitive type.
     */
    static isPrimitive(type) {
        return this.PRIMITIVE_TYPES.includes(type);
    }
    // List of primitive types
    static PRIMITIVE_TYPES = [
        "bool",
        "int",
        "char",
        "void",
        "byte",
        "short",
        "long",
        "float",
        "double",
    ];
}
export var Types;
(function (Types) {
    Types[Types["bool"] = 0] = "bool";
    Types[Types["int"] = 1] = "int";
    Types[Types["char"] = 2] = "char";
    Types[Types["void"] = 3] = "void";
    Types[Types["byte"] = 4] = "byte";
    Types[Types["short"] = 5] = "short";
    Types[Types["long"] = 6] = "long";
    Types[Types["float"] = 7] = "float";
    Types[Types["double"] = 8] = "double";
})(Types || (Types = {}));
//# sourceMappingURL=Types.js.map