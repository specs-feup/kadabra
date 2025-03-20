import JavaTypes from "@specs-feup/lara/api/lara/util/JavaTypes.js";
/**
 * Static variables with class names of Java classes used in the Clava API.
 *
 */
export default class KadabraJavaTypes {
    static get ArgumentsParser() {
        return JavaTypes.getType("pt.up.fe.specs.util.parsing.arguments.ArgumentsParser");
    }
    static get KadabraLauncher() {
        return JavaTypes.getType("weaver.gui.KadabraLauncher");
    }
}
//# sourceMappingURL=KadabraJavaTypes.js.map