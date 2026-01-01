import JavaTypes from "@specs-feup/lara/api/lara/util/JavaTypes.js";
/**
 * Static variables with class names of Java classes used in the Clava API.
 */
export default class KadabraJavaTypes {
    static get ArgumentsParser() {
        return JavaTypes.getType("pt.up.fe.specs.util.parsing.arguments.ArgumentsParser");
    }
    static get KadabraLauncher() {
        return JavaTypes.getType("weaver.gui.KadabraLauncher");
    }
    static get KadabraJoinPoints() {
        return JavaTypes.getType("weaver.kadabra.importable.KadabraJoinPoints");
    }
    static get OperatorUtils() {
        return JavaTypes.getType("weaver.utils.element.OperatorUtils");
    }
    static get AndroidResources() {
        return JavaTypes.getType("weaver.utils.android.AndroidResources");
    }
}
//# sourceMappingURL=KadabraJavaTypes.js.map