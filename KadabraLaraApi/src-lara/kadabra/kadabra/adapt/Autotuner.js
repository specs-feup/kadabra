import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Class, Field, Method, } from "../../Joinpoints.js";
import { newClass } from "../Factory.js";
import { AdaptiveAlgorithm, Algorithm, AlgorithmWithKnob, GenerativeAlgorithm, SimpleAlgorithm, } from "./Algorithm.js";
import { primitive2Class } from "../Utils.js";
import { Configs } from "./Configs.js";
/******
 * Algorithms Autotuner
 *******/
/**
 * Class defining the instance of an autotuner
 */
export class Autotuner {
    name;
    $targetField;
    $classContainer;
    autotunerClass;
    autotunerType;
    $tuner;
    /**
     * Static variables
     */
    static PACKAGE = "autotuner.";
    static MANAGER_PACKAGE = Autotuner.PACKAGE + "manager.";
    static KNOB_MANAGER_PACKAGE = Autotuner.PACKAGE + "knob.manager.";
    constructor(autotunerClass, $targetField, $classContainer, numWarmup, numRuns) {
        this.name = "tuner";
        this.$targetField = $targetField;
        this.$classContainer =
            $classContainer ??
                newClass(autotunerClass.$class.packageName + ".Autotuners");
        this.autotunerClass = autotunerClass;
        this.autotunerType = this.autotunerClass.$class.qualifiedName;
        this.$tuner = this.$classContainer.newField(["public", "static"], this.autotunerType, "tuner", `new ${this.autotunerType}(${numWarmup},${numRuns})`);
    }
    init(numWarmup, numRuns) {
        this.newField(this.$classContainer, numWarmup, numRuns);
    }
    newField($targetClass, numWarmup, numRuns, modifiers = ["public", "static"]) {
        this.$tuner = $targetClass.newField(modifiers, this.autotunerType, "tuner", `new ${this.autotunerType}(${numWarmup},${numRuns})`);
    }
    getAlgorithmType() {
        return `${Autotuner.MANAGER_PACKAGE}AlgorithmSampling<${this.autotunerClass.builder.algorithmType},${this.autotunerClass.builder.measurementType}>`;
    }
    getAlgorithm(key) {
        return `${this.$tuner}.getAlgorithm(${key})`;
    }
    getBest(key) {
        return `${this.$tuner}.getBest(${key})`;
    }
    updateBefore(key, $stmt) {
        if (this.$targetField === undefined) {
            throw new Error("Expected $targetField to be defined.");
        }
        $stmt.insertBefore(`
            ${this.getAlgorithmType()} algorithm = ${this.getAlgorithm(key)};
            ${this.$targetField} = algorithm.applyAndGet();
        `);
        return measurerProvider(this, "algorithm");
    }
    updateAfter(key, $stmt) {
        if (this.$targetField === undefined) {
            throw new Error("Expected $targetField to be defined.");
        }
        $stmt.insertAfter(`
            ${Autotuner.MANAGER_PACKAGE}AlgorithmSampling<${this.autotunerClass.builder.algorithmType},${this.autotunerClass.builder.measurementType}> algorithm =
                ${this.$tuner}.getAlgorithm(${key});
            ${this.$targetField} = algorithm.applyAndGet();
        `);
        return measurerProvider(this, "algorithm");
    }
    measure(key, $stmt, $stmtEnd = $stmt) {
        if (this.autotunerClass.measurer === undefined) {
            throw new Error("Expected autotunerClass.measurer to be defined.");
        }
        $stmt.insert("before", `
            ${this.autotunerClass.measurer.beginMeasure}
        `);
        $stmtEnd.insert("after", `
            ${this.autotunerClass.measurer.endMeasure}
            ${this.$tuner}.update(${key}, ${this.autotunerClass.measurer.getMeasure});
        `);
    }
    measureWithVar(varName, $stmt, $stmtEnd = $stmt) {
        if (this.autotunerClass.measurer === undefined) {
            throw new Error("Expected autotunerClass.measurer to be defined.");
        }
        $stmt.insert("before", `
            ${this.autotunerClass.measurer.beginMeasure}
        `);
        $stmtEnd.insert("after", `
            ${this.autotunerClass.measurer.endMeasure}
            ${varName}.update(${this.autotunerClass.measurer.getMeasure});
        `);
    }
    updateAndMeasure(key, $stmt, $stmtEnd) {
        const measurer = this.updateBefore(key, $stmt);
        measurer($stmt, $stmtEnd);
        return this;
    }
    inBestMode(key) {
        return `${this.$tuner}.inBestMode(${key})`;
    }
    isSampling(key) {
        return `${this.$tuner}.isSampling(${key})`;
    }
}
export function measurerProvider(autotuner, ref) {
    return ($stmt, $stmtEnd) => autotuner.measureWithVar(ref, $stmt, $stmtEnd);
}
/*
 * Class defining the class of an autotuner
 */
export class AutotunerClass {
    $class;
    builder;
    measurer;
    constructor($class, builder) {
        this.$class = $class;
        this.builder = builder;
        this.measurer = builder.measurer;
    }
    newInstance($targetField, numWarmup, numRuns) {
        if (!($targetField instanceof Field)) {
            throw new Error("[AutotunerClass - newInstance] $targetField not type Field");
        }
        const $targetClass = $targetField.getAncestor("class");
        if ($targetClass === undefined) {
            throw new Error("No class found for the given field.");
        }
        const autotuner = new Autotuner(this, $targetField, $targetClass, numWarmup, numRuns);
        return autotuner;
    }
}
/**
 * Class defining the builder of an autotuner
 */
export class AutotunerBuilder {
    name;
    datasetType;
    algorithmType;
    measurementType;
    algorithms;
    default;
    package;
    distanceMethod;
    measurer;
    configuration;
    constructor(name, datasetType, algorithmType, measurementType) {
        this.name = name;
        this.datasetType = datasetType;
        this.algorithmType = algorithmType;
        this.measurementType = measurementType;
        this.algorithms = [];
        this.default = null;
        this.normalSampling();
        this.package = undefined;
        this.distanceMethod = null;
    }
    generate(packageName = "kadabra.autotuner") {
        this.package = packageName;
        const $autotuner = GenerateTuner(this);
        return new AutotunerClass($autotuner, this);
    }
    /**
     * Adds a simple algorithm
     */
    addAlgorithm(algorithm, id) {
        if (id === undefined) {
            if (algorithm instanceof SimpleAlgorithm ||
                algorithm instanceof AlgorithmWithKnob) {
                return this.pushAlgorithm(algorithm);
            }
            else if (typeof algorithm == "string") {
                //Will assume native code "lambda"
                return this.pushAlgorithm(new SimpleAlgorithm(algorithm));
            }
            else {
                throw new Error("adding an algorithm without id assumes the first argument as a SimpleAlgorithm or AlgorithmWithKnob, however, " +
                    typeof algorithm +
                    " was given");
            }
        }
        if (typeof algorithm != "string" && !(algorithm instanceof Method)) {
            throw new Error(`Expected algorithm to be of type string or Method but was ${typeof algorithm}.`);
        }
        return this.pushAlgorithm(new SimpleAlgorithm(algorithm, id));
    }
    /**
     * Adds an algorithm containing a knob
     */
    addAlgorithmWithKnob(algorithm, id, configuration) {
        return this.pushAlgorithm(new AlgorithmWithKnob(algorithm, id, configuration.applier, configuration.get()));
    }
    addAdaptiveAlg(id, targetMethod, templateName, provider) {
        return this.pushAlgorithm(new AdaptiveAlgorithm(id, targetMethod, templateName, provider));
    }
    addGenerativeAlg(id, $interface, templateName, provider, providerType, extraArg) {
        return this.pushAlgorithm(new GenerativeAlgorithm(id, $interface, templateName, provider, providerType, extraArg));
    }
    pushAlgorithm(algorithm) {
        this.algorithms.push(algorithm);
        return this;
    }
    /**
     * Set the algorithms sampling in a random order
     */
    randomSampling() {
        this.configuration = Configs.order.random;
        return this;
    }
    /**
     * Set the algorithms sampling in the order they are defined
     */
    normalSampling() {
        this.configuration = Configs.order.normal;
        return this;
    }
    /**
     * Define the code that provides a new algorithm measurer (e.g. weaver.kadabra.control...measurers.AvgLongMeasurer )
     */
    setMeasurer(measurer) {
        this.measurer = measurer;
        return this;
    }
}
export function GenerateTuner(tuner) {
    const $app = Query.root();
    let className = tuner.name;
    if (tuner.package !== undefined) {
        className = tuner.package + "." + className;
    }
    console.log("[LOG] Generating Autotuner with the qualified name: " + className);
    const expType = `${Autotuner.MANAGER_PACKAGE}ExplorationSupervisor<${tuner.datasetType},${tuner.algorithmType},${tuner.measurementType}>`;
    const $autotuner = $app.newClass(className, expType, []);
    const algListProviderType = `${Algorithm.PROVIDER_PACKAGE}AlgorithmListProvider<${tuner.algorithmType}>`;
    $autotuner.newField(["private"], algListProviderType, "algListProvider");
    const $constr = $autotuner.newConstructor(["public"], ["int", "int"], ["warmup", "samples"]);
    ReplaceMethodCode($constr, ` 
        super(warmup,samples);
        initAlgProvider();
    `);
    let algProviderCode = "";
    if (tuner.algorithms.length > 0) {
        algProviderCode = "algListProvider";
        for (const alg of tuner.algorithms) {
            algProviderCode += `\n\t.algorithm(${alg.getSupplier()})`;
        }
        algProviderCode += ";";
    }
    $autotuner.newMethod(["private"], "void", "initAlgProvider", [], [], InitCode(algProviderCode));
    if (!(tuner.default instanceof SimpleAlgorithm)) {
        throw new Error("Expected AutotunerBuilder.default to be of type SimpleAlgorithm.");
    }
    $autotuner.newMethod(["protected"], `${Algorithm.PACKAGE}Algorithm<${tuner.algorithmType}>`, "defaultAlgorithm", [], [], `return ${tuner.default.instance()};`);
    if (tuner.measurer === undefined) {
        throw new Error("Expected AutotunerBuilder.measurer to be defined.");
    }
    $autotuner.newMethod(["protected"], `java.util.function.Supplier<${tuner.measurer.qualifiedType()}>`, "measurerProvider", [], [], `return ${tuner.measurer.getProvider()};`);
    if (tuner.configuration === undefined) {
        throw new Error("Expected AutotunerBuilder.configuration to be defined.");
    }
    $autotuner.newMethod(["protected"], `${Autotuner.MANAGER_PACKAGE}ConfigProvider<${tuner.algorithmType}>`, "configurationProvider", [], [], `return ${tuner.configuration};`);
    if (tuner.distanceMethod === null) {
        throw new Error("Expected AutotunerBuilder.distanceMethod to be defined.");
    }
    $autotuner.newMethod(["protected"], `java.util.function.BiFunction<${tuner.datasetType},${tuner.datasetType},java.lang.Double>`, "distanceProvider", [], [], `{return ${tuner.distanceMethod};`);
    $autotuner.newMethod(["protected"], `java.util.List<${Algorithm.PROVIDER_PACKAGE}AlgorithmProvider<${tuner.algorithmType}>>`, "getAlgorithms", [], [], `return algListProvider.build();`);
    return $autotuner;
}
export function InitCode(algProvidersCode) {
    return `
     algListProvider = new AlgorithmListProvider<>();
     ${algProvidersCode}
     `;
}
export function ReplaceMethodCode($method, code) {
    $method.body.replaceWith(code);
}
/******
 * Knobs Autotuner
 *******/
/**
 * Class defining the builder of an autotuner
 */
export class ControlPointBuilder extends AutotunerBuilder {
    knobs;
    knobType;
    config;
    configId;
    default;
    applyKnob;
    concurrent = false;
    constructor(name, datasetType, knobs, measurementType) {
        super(name, datasetType, "", measurementType);
        this.knobs = knobs;
        this.knobType = getKnobType(knobs);
        this.config = undefined;
        this.configId = undefined;
        this.default = null;
        this.applyKnob = undefined;
    }
    generate(packageName = "kadabra.autotuner") {
        this.package = packageName;
        const $autotuner = GenerateKnobTuner(this);
        //TODO
        return new ControlPointClass($autotuner, this);
    }
    setDefault(code) {
        this.default = code;
        return this;
    }
    setConcurrent(conc) {
        this.concurrent = conc;
        return this;
    }
    /**
     * Adds a simple algorithm
     */
    setConfig(config, id) {
        this.config = config;
        this.configId = id ?? this.configId;
        return this;
    }
    withConfig(configFunction, ranges, id = "user_config") {
        this.setConfig(configFunction(this.knobs, ranges), id);
        return this;
    }
    around(ranges, id = "around") {
        this.setConfig(Configs.around(this.knobs, ranges), id);
        return this;
    }
    range(ranges, id = "range") {
        this.setConfig(Configs.range(this.knobs, ranges), id);
        return this;
    }
    linear(ranges, id = "linear") {
        this.setConfig(Configs.linear(this.knobs, ranges), id);
        return this;
    }
    random(ranges, id = "random") {
        this.setConfig(Configs.randomOf(this.knobs, ranges), id);
        return this;
    }
}
const TUPLES_PACKAGE = "org.javatuples.";
const TUPLES_NAMES = [
    undefined,
    "Unit",
    "Pair",
    "Triplet",
    "Quartet",
    "Quintet",
    "Sextet",
    "Septet",
    "Octet",
    "Ennead",
    "Decade",
];
export function getKnobType(knobs) {
    if (!Array.isArray(knobs)) {
        return knobs.type;
    }
    else {
        let tupleName = `${TUPLES_PACKAGE}${TUPLES_NAMES[knobs.length]}<${primitive2Class(knobs[0].type)}`;
        for (let i = 1; i < knobs.length; i++) {
            tupleName += ", " + primitive2Class(knobs[i].type);
        }
        return tupleName + ">";
    }
}
export function GenerateKnobTuner(tuner) {
    const app = Query.root();
    let className = tuner.name;
    if (tuner.package !== undefined) {
        className = tuner.package + "." + className;
    }
    console.log("[LOG] Generating Autotuner with the qualified name: " + className);
    const conc = tuner.concurrent ? "Concurrent" : "";
    const expType = `${Autotuner.KNOB_MANAGER_PACKAGE}KnobExploration${conc}Supervisor<${tuner.datasetType},${tuner.knobType},${tuner.measurementType}>`;
    const $autotuner = app.newClass(className, expType, []);
    const $constr = $autotuner.newConstructor(["public"], ["int", "int"], ["warmup", "samples"]);
    ReplaceMethodCode($constr, `
            super(warmup,samples);
        `);
    if (tuner.default === null) {
        throw new Error("Expected ControlPointBuilder.default to be of type string.");
    }
    $autotuner.newMethod(["protected"], tuner.knobType, "defaultKnobValue", [], [], `return ${tuner.default};`);
    if (tuner.measurer === undefined) {
        throw new Error("Expected ControlPointBuilder.measurer to be defined.");
    }
    $autotuner.newMethod(["protected"], `java.util.function.Supplier<${tuner.measurer.qualifiedType()}>`, "measurerProvider", [], [], `return ${tuner.measurer.getProvider()};`);
    if (tuner.distanceMethod === null) {
        throw new Error("Expected ControlPointBuilder.distanceMethod to be defined.");
    }
    $autotuner.newMethod(["protected"], `java.util.function.BiFunction<${tuner.datasetType},${tuner.datasetType},java.lang.Double>`, "distanceProvider", [], [], `return ${tuner.distanceMethod};`);
    const knobProviderType = `KnobProvider<${tuner.knobType}>`;
    const id = tuner.configId ?? '"config"';
    let code = "(tuple) -> {";
    if (tuner.applyKnob === undefined) {
        const knobs = tuner.knobs;
        if (!Array.isArray(knobs))
            throw new Error("[GenerateKnobTuner] knobs is not an array");
        for (let pos = 0; pos < knobs.length; pos++) {
            code +=
                knobs[pos].staticAccess + " = tuple.getValue" + pos + "(); ";
        }
        code += "}";
    }
    else {
        code = tuner.applyKnob;
    }
    if (tuner.config === undefined) {
        throw new Error("Expected ControlPointBuilder.config to be defined.");
    }
    $autotuner.newMethod(["protected"], Autotuner.KNOB_MANAGER_PACKAGE + knobProviderType, "getKnobProvider", [], [], ` 
            java.util.function.Consumer<${tuner.knobType}> applier = ${code};
            ${Configs.PACKAGE}Configuration<${tuner.knobType}> config = ${tuner.config.get()};
            ${knobProviderType} provider = new KnobProvider<>(${id}, applier, config);
            return provider;
        `);
    return $autotuner;
}
/******
 * Algorithms Autotuner
 *******/
/**
 * Class defining the instance of an autotuner
 */
export class ControlPoint extends Autotuner {
    autotunerClass;
    constructor(autotunerClass, $classContainer, numWarmup, numRuns) {
        super(autotunerClass, undefined, $classContainer, numWarmup, numRuns);
        this.autotunerClass = autotunerClass;
    }
    getKnobType() {
        return `${Autotuner.KNOB_MANAGER_PACKAGE}KnobSampling<${this.autotunerClass.builder.knobType},${this.autotunerClass.builder.measurementType}>`;
    }
    getKnob(key) {
        return `${this.$tuner}.getKnob(${key})`;
    }
    updateBefore(key, $stmt) {
        $stmt.insert("before", ` 
        ${this.getKnobType()} knob = ${this.getKnob(key)};
        knob.apply();
    `);
        return measurerProvider(this, "knob");
    }
    updateAfter(key, $stmt) {
        $stmt.insert("after", `
        ${this.getKnobType()} knob = ${this.getKnob(key)};
        knob.apply();
    `);
        return measurerProvider(this, "knob");
    }
}
/**
 * Class defining the class of an autotuner
 */
export class ControlPointClass extends AutotunerClass {
    builder;
    constructor($class, builder) {
        super($class, builder);
        this.builder = builder;
    }
    newInstance($targetClass, numWarmup, numRuns) {
        if (!($targetClass instanceof Class)) {
            throw new Error("[ControlPointClass - newInstance] $targetClass not of type Class");
        }
        return new ControlPoint(this, $targetClass, numWarmup, numRuns);
    }
}
//# sourceMappingURL=Autotuner.js.map