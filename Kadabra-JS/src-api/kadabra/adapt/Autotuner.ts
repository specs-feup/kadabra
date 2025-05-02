/******
 * Algorithms Autotuner
 *******/

import Query from "@specs-feup/lara/api/weaver/Query.js";
import {
    App,
    Body,
    Class,
    Constructor,
    Field,
    FileJp,
    InterfaceType,
    Joinpoint,
    Method,
    Statement,
} from "../../Joinpoints.js";
import { newClass } from "../Factory.js";
import {
    AdaptiveAlgorithm,
    Algorithm,
    AlgorithmWithKnob,
    GenerativeAlgorithm,
    SimpleAlgorithm,
} from "./Algorithm.js";
import JoinPoints from "@specs-feup/lara/api/weaver/JoinPoints.js";
import { Measurer } from "./Measurers.js";
import { primitive2Class } from "../Utils.js";
import { Configs, Configuration, PrimitiveRange } from "./Configs.js";

export function measurerProvider(autotuner: Autotuner, ref: string) {
    const measurer = ($stmt: Statement, $stmtEnd: Statement) =>
        autotuner.measureWithVar(ref, $stmt, $stmtEnd);
    return measurer;
}
/**
 * Class defining the instance of an autotuner
 */
export class Autotuner {
    name: string;
    $targetField: Field | undefined;
    $classContainer: Class | undefined = undefined;
    autotunerClass: AutotunerClass;
    autotunerType: string;
    $tuner: Field | undefined = undefined;

    /**
     * Static variables
     */
    public static readonly PACKAGE = "autotuner.";
    public static readonly MANAGER_PACKAGE = Autotuner.PACKAGE + "manager.";
    public static readonly KNOB_MANAGER_PACKAGE =
        Autotuner.PACKAGE + "knob.manager.";

    constructor(
        autotunerClass: AutotunerClass,
        $targetField: Field | undefined,
        $classContainer: Class | undefined,
        numWarmup: number,
        numRuns: number
    ) {
        this.name = "tuner";
        this.$targetField = $targetField;
        this.$classContainer = $classContainer;
        this.autotunerClass = autotunerClass;
        this.autotunerType = this.autotunerClass.$class.qualifiedName;
        this.init(numWarmup, numRuns);
    }
    init(numWarmup: number, numRuns: number): void {
        if (
            this.$classContainer === undefined ||
            this.$classContainer === null
        ) {
            const nc = newClass(
                this.autotunerClass.$class.packageName + ".Autotuners"
            );
            this.$classContainer = nc;
        }

        this.newField(this.$classContainer, numWarmup, numRuns);
    }
    newField(
        $targetClass: Class,
        numWarmup: number,
        numRuns: number,
        modifiers: string[] = ["public", "static"]
    ): void {
        this.$tuner = $targetClass.newField(
            modifiers,
            this.autotunerType,
            "tuner",
            "new " + this.autotunerType + "(" + numWarmup + "," + numRuns + ")"
        );
    }
    getAlgorithmType(): string {
        return `${Autotuner.MANAGER_PACKAGE}AlgorithmSampling<${this.autotunerClass.builder.algorithmType},${this.autotunerClass.builder.measurementType}>`;
    }
    getAlgorithm(key: string) {
        return `${this.$tuner}.getAlgorithm(${key})`;
    }
    getBest(key: string) {
        return `${this.$tuner}.getBest(${key})`;
    }
    updateBefore(key: string, $stmt: Statement): any {
        $stmt.insert(
            "before",
            `
            ${this.getAlgorithmType()} algorithm = ${this.getAlgorithm(key)};
            ${this.$targetField} = algorithm.applyAndGet();
        `
        );

        return measurerProvider(this, "algorithm");
    }
    updateAfter(key: string, $stmt: Statement) {
        $stmt.insert(
            "after",
            `
            ${Autotuner.MANAGER_PACKAGE}AlgorithmSampling<${this.autotunerClass.builder.algorithmType},${this.autotunerClass.builder.measurementType}> algorithm =
                ${this.$tuner}.getAlgorithm(${key});
            ${this.$targetField} = algorithm.applyAndGet();
        `
        );

        return measurerProvider(this, "algorithm");
    }

    measure(key: string, $stmt: Statement, $stmtEnd?: Statement): void {
        $stmtEnd ??= $stmt;
        $stmt.insert(
            "before",
            `
            ${this.autotunerClass.measurer.beginMeasure}
        `
        );
        $stmtEnd.insert(
            "after",
            `
            ${this.autotunerClass.measurer.endMeasure}
            ${this.$tuner}.update(${key}, ${this.autotunerClass.measurer.getMeasure});
        `
        );
    }

    measureWithVar(
        varName: string,
        $stmt: Statement,
        $stmtEnd: Statement
    ): void {
        $stmtEnd ??= $stmt;
        $stmt.insert(
            "before",
            `
            ${this.autotunerClass.measurer.beginMeasure}
        `
        );
        $stmtEnd.insert(
            "after",
            `
            ${this.autotunerClass.measurer.endMeasure}
            ${varName}.update(${this.autotunerClass.measurer.getMeasure});
        `
        );
    }
    updateAndMeasure(key: string, $stmt: Statement, $stmtEnd: Statement) {
        const measurer = this.updateBefore(key, $stmt);
        measurer($stmt, $stmtEnd);
        return this;
    }
    inBestMode(key: string): string {
        return `${this.$tuner}.inBestMode(${key})`;
    }

    isSampling(key: string): string {
        return `${this.$tuner}.isSampling(${key})`;
    }
}

/*
 * Class defining the class of an autotuner
 */
export class AutotunerClass {
    $class: Class;
    builder: AutotunerBuilder;
    measurer: any;
    constructor($class: Class, builder: AutotunerBuilder) {
        this.$class = $class;
        this.builder = builder;
        this.measurer = builder.measurer;
    }
    newInstance($targetField: Joinpoint, numWarmup: number, numRuns: number) {
        if (!($targetField instanceof Field))
            throw new Error(
                "[AutotunerClass - newInstance] $targetField not type Field"
            );
        const $targetClass = $targetField.getAncestor("class") as Class;
        const autotuner = new Autotuner(
            this,
            $targetField,
            $targetClass,
            numWarmup,
            numRuns
        );
        return autotuner;
    }
}

/**
 * Class defining the builder of an autotuner
 */
export class AutotunerBuilder {
    name: string;
    datasetType: any;
    algorithmType: any;
    measurementType: any;
    algorithms: any;
    default: any;
    package: string | undefined = undefined;
    distanceMethod: any;
    measurer: Measurer | undefined;
    configuration: string | undefined;

    constructor(name: string, datasetType, algorithmType, measurementType) {
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

    generate(package: string = "kadabra.autotuner"): AutotunerClass {
        this.package = package;
        const $autotuner = GenerateTuner(this);
        //Confirm This
        if ($autotuner instanceof Class) {
            const autotunerClass = new AutotunerClass($autotuner, this);
            return autotunerClass;
        } else {
            throw new Error("[generate] generator is undefined");
        }
    }
    addAlgorithm(
        /*Code | SimpleAlgorithm | AlgorithmWithKnob*/ algorithm:
            | string
            | Algorithm,
        id: string | undefined
    ) {
        if (
            algorithm instanceof SimpleAlgorithm ||
            algorithm instanceof AlgorithmWithKnob
        ) {
            return this.pushAlgorithm(algorithm);
        } else if (typeof algorithm == "string") {
            //Will assume native code "lambda"
            return this.pushAlgorithm(new SimpleAlgorithm(algorithm, id));
        } else {
            throw (
                "adding an algorithm without id assumes the first argument as a SimpleAlgorithm or AlgorithmWithKnob, however, " +
                typeof algorithm +
                " was given"
            );
        }
    }
    addAlgorithmWithKnob(
        /*Code */ algorithm: string,
        id: string,
        /* Configuration */ configuration: Configuration
    ) {
        return this.pushAlgorithm(
            new AlgorithmWithKnob(
                algorithm,
                id,
                configuration.applier,
                configuration.get()
            )
        );
    }

    addAdaptiveAlg(
        id: string,
        targetMethod: Method,
        templateName: string,
        provider: string
    ) {
        return this.pushAlgorithm(
            new AdaptiveAlgorithm(id, targetMethod, templateName, provider)
        );
    }
    addGenerativeAlg(
        id: string,
        $interface: Method,
        templateName: string,
        provider: string,
        providerType: any,
        extraArg: string[][]
    ) {
        return this.pushAlgorithm(
            new GenerativeAlgorithm(
                id,
                $interface,
                templateName,
                provider,
                providerType,
                extraArg
            )
        );
    }

    pushAlgorithm(algorithm: any) {
        this.algorithms.push(algorithm);
        return this;
    }
    randomSampling() {
        this.configuration = Configs.order.random;
        return this;
    }
    normalSampling() {
        this.configuration = Configs.order.normal;
        return this;
    }
    setMeasurer(measurer: Measurer) {
        if (!(measurer instanceof Measurer)) {
            throw "The measurer for the autotuner must be of class Measurer from the import: kadabra.adapt.Measurers";
        }
        this.measurer = measurer;
        return this;
    }
}
export function GenerateTuner(tuner: AutotunerBuilder) {
    const $app = Query.root() as App;
    let className = tuner.name;
    if (tuner.package != undefined) {
        className = tuner.package + "." + className;
    }
    console.log(
        "[LOG] Generating Autotuner with the qualified name: " + className
    );
    const expType =
        Autotuner.MANAGER_PACKAGE +
        "ExplorationSupervisor<" +
        tuner.datasetType +
        "," +
        tuner.algorithmType +
        "," +
        tuner.measurementType +
        ">";
    const $autotuner = $app.newClass(className, expType, []);
    const algListProviderType =
        Algorithm.PROVIDER_PACKAGE +
        "AlgorithmListProvider<" +
        tuner.algorithmType +
        ">";
    $autotuner.newField(
        ["private"],
        algListProviderType,
        "algListProvider",
        undefined
    );
    const $constr = $autotuner.newConstructor(
        ["public"],
        ["int", "warmup"],
        ["int", "samples"]
    );
    ReplaceMethodCode(
        $constr,
        ` 
        super(warmup,samples);
        initAlgProvider();
    `
    );
    let algProviderCode = "";
    if (tuner.algorithms.length > 0) {
        algProviderCode = "algListProvider";
        for (const alg of tuner.algorithms) {
            algProviderCode += "\n\t.algorithm(" + alg.getSupplier() + ")";
        }
        algProviderCode += ";";
    }
    $autotuner.newMethod(
        ["private"],
        "void",
        "initAlgProvider",
        [],
        [],
        InitCode(algProviderCode)
    );
    let defaultCode = null;

    if (tuner.default != null) {
        defaultCode = tuner.default.instance();
    }

    $autotuner.newMethod(
        ["protected"],
        Algorithm.PACKAGE + "Algorithm<" + tuner.algorithmType + ">",
        "defaultAlgorithm",
        [],
        [],
        `return ${defaultCode};`
    );

    $autotuner.newMethod(
        ["protected"],
        "java.util.function.Supplier<" + tuner.measurer.qualifiedType() + ">",
        "measurerProvider",
        [],
        [],
        `return ${tuner.measurer.getProvider()};`
    );
    $autotuner.newMethod(
        ["protected"],
        Autotuner.MANAGER_PACKAGE +
            "ConfigProvider<" +
            tuner.algorithmType +
            ">",
        "configurationProvider",
        [],
        [],
        `return ${tuner.configuration};`
    );
    $autotuner.newMethod(
        ["protected"],
        "java.util.function.BiFunction<" +
            tuner.datasetType +
            "," +
            tuner.datasetType +
            ",java.lang.Double>",
        "distanceProvider",
        [],
        [],
        `{return ${tuner.distanceMethod};`
    );

    const algProviderType =
        Algorithm.PROVIDER_PACKAGE +
        "AlgorithmProvider<" +
        tuner.algorithmType +
        ">";
    $autotuner.newMethod(
        ["protected"],
        "java.util.List<" + algProviderType + ">",
        "getAlgorithms",
        [],
        [],
        `return algListProvider.build();`
    );

    return $autotuner;
}

export function InitCode(algProvidersCode: string) {
    return `
     algListProvider = new AlgorithmListProvider<>();
     ${algProvidersCode}`;
}

export function ReplaceMethodCode($method: Constructor, code: string) {
    $method.body.replaceWith(code);
}
/**
 * Define the code that provides a new algorithm measurer (e.g. weaver.kadabra.control...measurers.AvgLongMeasurer )
 */

/******
 * Knobs Autotuner
 *******/

/**
 * Class defining the builder of an autotuner
 */
export class ControlPointBuilder extends AutotunerBuilder {
    knobs: Field | Field[];
    knobType: string;
    config: Configuration | Algorithm | undefined;
    configId: string | undefined;
    default: string | null = null;
    package: string = "kadabra.autotuner";
    distanceMethod = null;
    applyKnob = undefined;
    concurrent = false;
    constructor(
        name: string,
        datasetType: any,
        knobs: Field | Field[],
        measurementType: any
    ) {
        super(name, datasetType, AlgorithmTypeVRR, measurementType);
        this.knobs = knobs;
        this.knobType = getKnobType(knobs);
    }
    generate(package: string = "kadabra.autotuner"): AutotunerClass {
        this.package = package;
        const $autotuner = GenerateKnobTuner(this);
        //TODO
        return new ControlPointClass($autotuner, this);
    }

    setDefault(code: string) {
        this.default = code;
        return this;
    }

    setConcurrent(conc: boolean) {
        this.concurrent = conc;
        return this;
    }

    /**
     * Adds a simple algorithm
     */
    setConfig(config: Configuration | Algorithm, id: string) {
        this.config = config;
        this.configId = id || this.configId;
        return this;
    }

    withConfig(configFunction, ranges, id) {
        this.setConfig(configFunction(this.knobs, ranges), id || "user_config");
        return this;
    }

    around(ranges: PrimitiveRange | PrimitiveRange[], id: string = "around") {
        this.setConfig(Configs.around(this.knobs, ranges), id);
        return this;
    }

    range(ranges, id) {
        this.setConfig(Configs.range(this.knobs, ranges), id || "range");
        return this;
    }

    linear(ranges, id) {
        this.setConfig(Configs.linear(this.knobs, ranges), id || "linear");
        return this;
    }

    random(ranges, id) {
        this.setConfig(Configs.randomOf(this.knobs, ranges), id || "random");
        return this;
    }

    /**
     * Define the code that provides a new algorithm measurer (e.g. weaver.kadabra.control...measurers.AvgLongMeasurer )
     */
    setMeasurer(measurer: Measurer) {
        this.measurer = measurer;
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

function getKnobType(knobs: Field | Field[]) {
    if (!Array.isArray(knobs)) {
        return knobs.type;
    } else {
        let tupleName =
            TUPLES_PACKAGE +
            TUPLES_NAMES[knobs.length] +
            "<" +
            primitive2Class(knobs[0].type);
        for (let i = 1; i < knobs.length; i++) {
            tupleName += ", " + primitive2Class(knobs[i].type);
        }
        tupleName += ">";
        return tupleName;
    }
}

export function GenerateKnobTuner(tuner: ControlPointBuilder) {
    //output $autotuner end
    const app = Query.root() as App;
    let className = tuner.name;
    if (tuner.package != undefined) {
        className = tuner.package + "." + className;
    }
    console.log(
        "[LOG] Generating Autotuner with the qualified name: " + className
    );
    const conc = tuner.concurrent ? "Concurrent" : "";
    const expType =
        Autotuner.KNOB_MANAGER_PACKAGE +
        "KnobExploration" +
        conc +
        "Supervisor<" +
        tuner.datasetType +
        "," +
        tuner.knobType +
        "," +
        tuner.measurementType +
        ">";
    const $autotuner = app.newClass(className, expType, []);
    const $constr = $autotuner.newConstructor(
        ["public"],
        [new Pair("int", "warmup"), new Pair("int", "samples")]
    );
    ReplaceMethodCode(
        $constr,
        `
            super(warmup,samples);
        `
    );

    let defaultCode = null;
    if (tuner.default != null) {
        defaultCode = tuner.default;
    }

    $autotuner.newMethod(
        ["protected"],
        tuner.knobType,
        "defaultKnobValue",
        [],
        [],
        `return ${defaultCode};`
    );

    $autotuner.newMethod(
        ["protected"],
        "java.util.function.Supplier<" + tuner.measurer.qualifiedType() + ">",
        "measurerProvider",
        [],
        [],
        `return ${tuner.measurer.getProvider()};`
    );

    $autotuner.newMethod(
        ["protected"],
        "java.util.function.BiFunction<" +
            tuner.datasetType +
            "," +
            tuner.datasetType +
            ",java.lang.Double>",
        "distanceProvider",
        [],
        [],
        `return ${tuner.distanceMethod};`
    );

    const knobProviderType = "KnobProvider<" + tuner.knobType + ">";
    const id = tuner.configId || '"config"';
    if (tuner.applyKnob == undefined) {
        var code = "(tuple) -> {";
        var pos = 0;
        const knobs = tuner.knobs;
        if (!Array.isArray(knobs))
            throw new Error("[GenerateKnobTuner] knobs is not an array");
        for (var pos = 0; pos < knobs.length; pos++) {
            code +=
                knobs[pos].staticAccess + " = tuple.getValue" + pos + "(); ";
        }
        code += "}";
    } else {
        code = tuner.applyKnob;
    }

    $autotuner.newMethod(
        ["protected"],
        Autotuner.KNOB_MANAGER_PACKAGE + knobProviderType,
        "getKnobProvider",
        [],
        [],
        ` 
            java.util.function.Consumer<${tuner.knobType}> applier = ${code};
            ${Configs.PACKAGE}Configuration<${
            tuner.knobType
        }> config = ${tuner.config.get()};
            ${knobProviderType} provider = new KnobProvider<>(${id}, applier, config);
            return provider;
        `
    );

    return $autotuner;
}

/******
 * Algorithms Autotuner
 *******/
/**
 * Class defining the instance of an autotuner
 */
export class ControlPoint extends Autotuner {
    constructor(
        autotunerClass: ControlPointClass,
        $classContainer?: Class,
        numWarmup: number,
        numRuns: number
    ) {
        super(autotunerClass, undefined, $classContainer, numWarmup, numRuns);
    }

    getKnobType() {
        return `${Autotuner.KNOB_MANAGER_PACKAGE}KnobSampling<${this.autotunerClass.builder.knobType},${this.autotunerClass.builder.measurementType}>`;
    }
    getKnob(key: string) {
        return `${this.$tuner}.getKnob(${key})`;
    }

    updateBefore(key: string, $stmt: Statement) {
        $stmt.insert(
            "before",
            ` 
        ${this.getKnobType()} knob = ${this.getKnob(key)};
        knob.apply();
    `
        );

        //return "algorithm";
        return measurerProvider(this, "knob");
    }

    updateAfter(key: string, $stmt: Statement) {
        $stmt.insert(
            "after",
            `
        ${this.getKnobType()} knob = ${this.getKnob(key)};
        knob.apply();
    `
        );

        return measurerProvider(this, "knob");
    }
}

/**
 * Class defining the class of an autotuner
 */
export class ControlPointClass extends AutotunerClass {
    newInstance(
        $targetClass: Joinpoint,
        numWarmup: number,
        numRuns: number
    ): Autotuner {
        if (!($targetClass instanceof Class))
            throw new Error(
                "[ControlPointClass - newInstance] $targetClass not of type Class"
            );
        return new ControlPoint(this, $targetClass, numWarmup, numRuns);
    }
}
