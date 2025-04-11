import JavaTypes, {
    JavaClasses,
} from "@specs-feup/lara/api/lara/util/JavaTypes.js";

// eslint-disable-next-line @typescript-eslint/no-namespace
export namespace KadabraJavaClasses {
    /* eslint-disable @typescript-eslint/no-empty-object-type */
    export interface ArgumentsParser extends JavaClasses.JavaClass {}
    export interface KadabraLauncher extends JavaClasses.JavaClass {}
    /* eslint-enable @typescript-eslint/no-empty-object-type */
}

/**
 * Static variables with class names of Java classes used in the Clava API.
 *
 */
export default class KadabraJavaTypes {
    static get ArgumentsParser() {
        return JavaTypes.getType(
            "pt.up.fe.specs.util.parsing.arguments.ArgumentsParser"
        ) as KadabraJavaClasses.ArgumentsParser;
    }

    static get KadabraLauncher() {
        return JavaTypes.getType(
            "weaver.gui.KadabraLauncher"
        ) as KadabraJavaClasses.KadabraLauncher;
    }
}
