///////////////////////////////////////////////////
//////////////// USEFUL CONSTANTS /////////////////

import { Field } from "../../Joinpoints.js";
import { primitive2Class } from "../Utils.js";

///////////////////////////////////////////////////
export class Configs {
    public static readonly PACKAGE = "autotuner.configs.";
    public static readonly FACTORY = Configs.PACKAGE + "autotuner.configs.";
    public static readonly RANGED = Configs.PACKAGE + "knobs.number.ranged.";
    public static readonly Tuple = "tdrc.tuple.Tuple";
    public static readonly order = {
        normal: Configs.FACTORY + "::normal",
        random: Configs.FACTORY + "::random",
    };

    static default(knobs: Field | Field[], values: string[], type: string) {
        return list2Config(knobs, "normal", values, false, type);
    }

    static random(knobs: Field | Field[], values: string[], type: string) {
        return list2Config(knobs, "random", values, false, type);
    }

    static combine(knobs: Field | Field[], values: string[][], type: string) {
        if (!Array.isArray(values) || values.length < 2) {
            throw "At least two values must be provided to combine values";
        }
        const args = values.map((arg) => {
            return "java.util.Arrays.asList(" + arg.join(",") + ")";
        });
        return new Configuration(
            knobs,
            Configs.FACTORY +
                ".normal(tdrc.utils.ListUtils.createTuplesFromList(" +
                args.join(",") +
                "))",
            type
        );
    }

    static randomOf(
        knobs: Field | Field[],
        ranges: PrimitiveRange | PrimitiveRange[]
    ) {
        return rangedConfig(knobs, ranges, "random", true);
    }

    static range(
        knobs: Field | Field[],
        ranges: PrimitiveRange | PrimitiveRange[]
    ) {
        return rangedConfig(knobs, ranges, "range", true);
    }

    static around(
        knobs: Field | Field[],
        ranges: PrimitiveRange | PrimitiveRange[]
    ) {
        return rangedConfig(knobs, ranges, "around", true);
    }

    static linear(
        knobs: Field | Field[],
        ranges: PrimitiveRange | PrimitiveRange[]
    ) {
        return rangedConfig(knobs, ranges, "linear", true);
    }

    static custom(knobs: Field | Field[], configCode: string, type: string) {
        return new Configuration(knobs, configCode, type);
    }
}

export class Configuration {
    applier: string;
    configCode: string;
    type: string;
    constructor(knobs: Field | Field[], configCode: string, type: string) {
        if (Array.isArray(knobs)) {
            let applierCode = "(knobs) -> {\n";
            for (let i = 0; i < knobs.length; i++) {
                const k = knobs[i];
                //validateKnob(k);
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
        } else {
            this.applier = `(knob)-> ${knobs.staticAccess} = knob`;
        }
        this.type = type;
        this.configCode = configCode;
    }
    declare(name: string) {
        return (
            Configs.PACKAGE +
            "Configuration<" +
            this.type +
            "> " +
            name +
            " = " +
            this.get()
        );
    }
    declareProvider(name: string) {
        return (
            "java.util.function.Supplier< " +
            Configs.PACKAGE +
            "Configuration<" +
            this.type +
            ">> " +
            name +
            " = " +
            this.provider()
        );
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

/*function validateKnob(knob: Joinpoint) {
    if (!(knob instanceof Joinpoint))
        throw new Error(
            "The knobs used in the configuration must be join points"
        );
    if (!(knob instanceof Field))
        throw new Error(
            "A join point of type " +
                knob.joinPointType +
                " was given. The current supported knobs are: field."
        );
}*/

//////////////////////////////////////////////////////////////
//////////////// GENERIC CONFIGS USING LISTS /////////////////
//////////////////////////////////////////////////////////////

function list2Config(
    knobs: Field | Field[],
    constructor: string,
    values: string[],
    acceptsSingle: boolean,
    type: string
) {
    if (values.length == 0) {
        throw (
            "At least one value must be provided to create a " +
            constructor +
            " configuration"
        );
    }

    if (values.length == 1 && acceptsSingle) {
        return new Configuration(
            knobs,
            Configs.FACTORY + "." + constructor + "(" + values.join(",") + ")",
            type
        );
    }
    //	return  new Configuration(knobs, Configs.FACTORY+'.'+constructor+'(java.util.Arrays.asList('+values.join(',')+'))',type);
    return new Configuration(
        knobs,
        Configs.FACTORY + "." + constructor + "(" + values.join(",") + ")",
        type
    );
}

///////////////////////////////////////////////////////
//////////////// CONFIGS USING RANGES /////////////////
///////////////////////////////////////////////////////
/*
	This configuration uses Ranged knobs, which are defined in the lower part of this file (e.g. IntegerRange)
*/

export function rangedConfig(
    knobs: Field | Field[],
    ranges: PrimitiveRange | PrimitiveRange[],
    type: string,
    acceptsSingle: boolean
) {
    let args;
    if (Array.isArray(ranges)) {
        if (ranges.length == 0) {
            throw (
                "At least one value must be provided to create a(n) " +
                type +
                " configuration"
            );
        }

        args = ranges.map(function (range) {
            if (range.instance !== undefined) {
                return range.instance();
            }
            throw (
                "when defining a ranged configuration: one of the given arguments is not a RangedKnob: " +
                range
            );
        });
    } else {
        if (ranges.instance === undefined) {
            throw new Error(
                "when defining a ranged configuration: one of the given arguments is not a RangedKnob: " +
                    ranges
            );
        }
        args = [ranges.instance()];
    }
    return list2Config(knobs, "", args, acceptsSingle, type);
}

/*
	Ranged knob is used in the configurations, use this if you intend to create knobs containing multiple parameters (one RangedKnob per knob) and combine them in a configuration
*/
export class PrimitiveRange {
    type: string;
    lowerLimit: number;
    upperLimit: number;
    step: number | undefined;
    value: number;
    descend: number | undefined;
    ascend: number | undefined;
    constructor(
        type: string,
        lowerLimit: number,
        upperLimit: number,
        step: number,
        value: number
    ) {
        this.type = primitive2Class(type);
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.step = step || 1;
        this.value = value;
        this.descend = undefined;
        this.ascend = undefined;
    }
    setClimbers(descend: number, ascend: number) {
        this.descend = descend;
        this.ascend = ascend;
        this.step = undefined;
        return this;
    }
    initValue(value: number) {
        this.value = value;
        return this;
    }
    toConfig() {
        const instanceCode = this.instance();
        return Configs.FACTORY + ".range(" + instanceCode + ")";
    }
    instance() {
        if (this.ascend != undefined) {
            let newKnob =
                "new " +
                Configs.RANGED +
                "CustomStep" +
                this.type +
                "Knob(" +
                this.lowerLimit +
                "," +
                this.upperLimit +
                ",";
            if (this.value != undefined) {
                newKnob += this.value + ",";
            }
            return newKnob + this.descend + "," + this.ascend + ")";
        }

        let newKnob =
            "new " +
            Configs.RANGED +
            this.type +
            "Step(" +
            this.lowerLimit +
            "," +
            this.upperLimit +
            ",";
        if (this.value != undefined) {
            newKnob += this.value + ",";
        }
        return newKnob + this.step + ")";
    }
    declare(name: string) {
        return (
            Configs.RANGED +
            "RangedKnob<" +
            this.type +
            ">" +
            name +
            " = " +
            this.instance()
        );
    }
}
export class IntegerRange extends PrimitiveRange {
    constructor(
        lowerLimit: number,
        upperLimit: number,
        step: number,
        value: number
    ) {
        super("Integer", lowerLimit, upperLimit, step, value);
    }
}

export class FloatRange extends PrimitiveRange {
    constructor(
        lowerLimit: number,
        upperLimit: number,
        step: number,
        value: number
    ) {
        super("Float", lowerLimit, upperLimit, step, value);
    }
}

/*
IntegerRange.prototype.setClimbers= function(descend, ascend){
	this.descend = descend;
	this.ascend = ascend;
	this.step = undefined;
	return this;
}

IntegerRange.prototype.initValue = function(value){
	this.value = value;
	return this;
}

IntegerRange.prototype.instance = function(){
	
	if(this.ascend != undefined){
		var newKnob = "new "+ Configs.RANGED +"CustomStepIntegerKnob("+this.lowerLimit+","+this.upperLimit+",";
		if(this.value != undefined){
			newKnob+= this.value+",";
		}
		return newKnob+this.descend+","+this.ascend+")";
	}
	
	var newKnob = "new "+ Configs.RANGED +"IntegerStep("+this.lowerLimit+","+this.upperLimit+",";
	if(this.value != undefined){
		newKnob+= this.value+",";
	}
	return newKnob+this.step+")";
	
}

IntegerRange.prototype.declare = function(name){
	return Configs.RANGED+'RangedKnob<'+this.type+'>'+name+' = '+this.instance();
}
/**/
