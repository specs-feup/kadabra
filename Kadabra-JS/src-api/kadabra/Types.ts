export class KTypes {
    static readonly Thread = "weaver.kadabra.concurrent.KadabraThread";
    static readonly Channel = "weaver.kadabra.concurrent.KadabraChannel";
    static readonly Product = "weaver.kadabra.concurrent.Product";
    static readonly ControlPoint = "weaver.kadabra.control.ControlPoint";
    static readonly ControlPointFactory = "weaver.kadabra.control.ControlPointFactory";
    static readonly VersionProviderUtils = "weaver.kadabra.control.utils.ProviderUtils";
    static readonly VersionProvider = "weaver.kadabra.control.utils.VersionProvider";
    static readonly Tuple = "tdrc.tuple.Tuple";
    static readonly Pair = "tdrc.utils.Pair";
    static readonly Atomic = "java.util.concurrent.atomic.Atomic";

    /**
     * Generates a channel type with the specified key and value types.
     */
    static channelOf(key: string = "Object", value: string = "Object"): string {
        return `${this.Channel}<${key},${value}>`;
    }
    /**
     * Generates a product type with the specified key and value types.
     */
    static productOf(key: string, value: string): string | undefined {
        if (!key || !value) {
            return undefined;
        }
        return `${this.Product}<${key},${value}>`;
    }
    /**
     * Generates a control point type with the specified type.
     */
    static controlPointOf(type: string = "Object"): string {
        return `${this.ControlPoint}<${type}>`;
    }
    /**
     * Generates a provider type with the specified type.
     */
    static providerOf(type: string = "Object"): string {
        return `${this.VersionProvider}<${type}>`;
    }
    /**
     * Generates a tuple type with the specified type.
     */
    static tupleOf(type: string): string {
        return `${this.Tuple}<${type}>`;
    }
    /**
     * Generates a list type with the specified type.
     */
    static listOf(type: string): string {
        return `java.util.List<${type}>`;
    }
    /**
     * Generates a pair type with the specified types.
     */
    static pairOf(type1: string, type2: string): string {
        return `${this.Pair}<${type1},${type2}>`;
    }
    /**
     * Generates an atomic type with the specified type.
     */
    static atomicOf(type: string): string {
        if (this.isPrimitive(type)) {
            return `${this.Atomic}${this.toWrapper(type)}`;
        }
        return `${this.Atomic}Reference<${type}>`;
    }
    /**
     * Converts a primitive type to its wrapper class.
     */
    static toWrapper(type: string): string {
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
    static isPrimitive(type: string): boolean {
        return this.PRIMITIVE_TYPES.includes(type);
    }
    // List of primitive types
    private static readonly PRIMITIVE_TYPES = [
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

export enum Types {'bool', 'int', 'char', 'void', 'byte', 'short', 'long', 'float', 'double'};