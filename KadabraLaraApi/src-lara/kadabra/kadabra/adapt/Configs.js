import { primitive2Class } from "../Utils.js";
/**
 * Class defining the structure of an autotuner
 */
export class Configs {
    ///////////////////////////////////////////////////
    //////////////// USEFUL CONSTANTS /////////////////
    ///////////////////////////////////////////////////
    static PACKAGE = "autotuner.configs.";
    static FACTORY = Configs.PACKAGE + "autotuner.configs.";
    static RANGED = Configs.PACKAGE + "knobs.number.ranged.";
    static Tuple = "tdrc.tuple.Tuple";
    static order = {
        normal: Configs.FACTORY + "::normal",
        random: Configs.FACTORY + "::random",
    };
    /**
    A list of values to test in the order they are given.
    **/
    static default(knobs, values, type) {
        return list2Config(knobs, "normal", values, false, type);
    }
    /**
    A list of values to test in random order.
    **/
    static random(knobs, values, type) {
        return list2Config(knobs, "random", values, false, type);
    }
    static combine(knobs, values, type) {
        if (!Array.isArray(values) || values.length < 2) {
            throw new Error("At least two values must be provided to combine values");
        }
        const args = values.map((arg) => {
            return "java.util.Arrays.asList(" + arg.join(",") + ")";
        });
        return new Configuration(knobs, Configs.FACTORY +
            ".normal(tdrc.utils.ListUtils.createTuplesFromList(" +
            args.join(",") +
            "))", type);
    }
    /*
    This configuration uses Ranged knobs, which are defined in the lower part of this file (e.g. IntegerRange)
    */
    static randomOf(knobs, ranges) {
        return rangedConfig(knobs, ranges, "random", true);
    }
    static range(knobs, ranges) {
        return rangedConfig(knobs, ranges, "range", true);
    }
    static around(knobs, ranges) {
        return rangedConfig(knobs, ranges, "around", true);
    }
    static linear(knobs, ranges) {
        return rangedConfig(knobs, ranges, "linear", true);
    }
    static custom(knobs, configCode, type) {
        return new Configuration(knobs, configCode, type);
    }
}
export class Configuration {
    applier;
    configCode;
    type;
    constructor(knobs, configCode, type) {
        if (Array.isArray(knobs)) {
            let applierCode = "(knobs) -> {\n";
            for (let i = 0; i < knobs.length; i++) {
                const k = knobs[i];
                applierCode +=
                    "\t" +
                        k.staticAccess +
                        " = (" +
                        k.type +
                        ")knobs.get(" +
                        i +
                        ");\n";
            }
            this.applier = applierCode + "}";
        }
        else {
            this.applier = `(knob)-> ${knobs.staticAccess} = knob`;
        }
        this.type = type;
        this.configCode = configCode;
    }
    declare(name) {
        return (Configs.PACKAGE +
            "Configuration<" +
            this.type +
            "> " +
            name +
            " = " +
            this.get());
    }
    declareProvider(name) {
        return ("java.util.function.Supplier< " +
            Configs.PACKAGE +
            "Configuration<" +
            this.type +
            ">> " +
            name +
            " = " +
            this.provider());
    }
    provider() {
        return "()-> " + this.configCode;
    }
    get() {
        return this.configCode;
    }
    toString() {
        return this.get();
    }
}
//////////////////////////////////////////////////////////////
//////////////// GENERIC CONFIGS USING LISTS /////////////////
//////////////////////////////////////////////////////////////
function list2Config(knobs, constructor, values, acceptsSingle, type) {
    if (values.length == 0) {
        throw new Error("At least one value must be provided to create a " +
            constructor +
            " configuration");
    }
    if (values.length == 1 && acceptsSingle) {
        return new Configuration(knobs, Configs.FACTORY + "." + constructor + "(" + values.join(",") + ")", type);
    }
    return new Configuration(knobs, Configs.FACTORY + "." + constructor + "(" + values.join(",") + ")", type);
}
///////////////////////////////////////////////////////
//////////////// CONFIGS USING RANGES /////////////////
///////////////////////////////////////////////////////
export function rangedConfig(knobs, ranges, type, acceptsSingle) {
    let args;
    if (Array.isArray(ranges)) {
        if (ranges.length == 0) {
            throw new Error("At least one value must be provided to create a(n) " +
                type +
                " configuration");
        }
        args = ranges.map(function (range) {
            return range.instance();
        });
    }
    else {
        args = [ranges.instance()];
    }
    return list2Config(knobs, "", args, acceptsSingle, type);
}
/*
    Ranged knob is used in the configurations, use this if you intend to create knobs containing multiple parameters (one RangedKnob per knob) and combine them in a configuration
*/
export class PrimitiveRange {
    type;
    lowerLimit;
    upperLimit;
    step;
    value;
    descend;
    ascend;
    constructor(type, lowerLimit, upperLimit, step = 1, value) {
        this.type = primitive2Class(type);
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.step = step;
        this.value = value;
        this.descend = undefined;
        this.ascend = undefined;
    }
    setClimbers(descend, ascend) {
        this.descend = descend;
        this.ascend = ascend;
        this.step = undefined;
        return this;
    }
    initValue(value) {
        this.value = value;
        return this;
    }
    toConfig() {
        const instanceCode = this.instance();
        return Configs.FACTORY + ".range(" + instanceCode + ")";
    }
    instance() {
        if (this.ascend != undefined) {
            let newKnob = "new " +
                Configs.RANGED +
                "CustomStep" +
                this.type +
                "Knob(" +
                this.lowerLimit +
                "," +
                this.upperLimit +
                ",";
            if (this.value !== undefined) {
                newKnob += this.value + ",";
            }
            if (this.descend === undefined) {
                throw new Error("Expected descend to be defined.");
            }
            return newKnob + this.descend + "," + this.ascend + ")";
        }
        let newKnob = "new " +
            Configs.RANGED +
            this.type +
            "Step(" +
            this.lowerLimit +
            "," +
            this.upperLimit +
            ",";
        if (this.value !== undefined) {
            newKnob += this.value + ",";
        }
        if (this.step === undefined) {
            throw new Error("Expected step to be defined.");
        }
        return newKnob + this.step + ")";
    }
    declare(name) {
        return (Configs.RANGED +
            "RangedKnob<" +
            this.type +
            ">" +
            name +
            " = " +
            this.instance());
    }
}
export class IntegerRange extends PrimitiveRange {
    constructor(lowerLimit, upperLimit, step, value) {
        super("Integer", lowerLimit, upperLimit, step, value);
    }
}
export class FloatRange extends PrimitiveRange {
    constructor(lowerLimit, upperLimit, step, value) {
        super("Float", lowerLimit, upperLimit, step, value);
    }
}
//# sourceMappingURL=Configs.js.map