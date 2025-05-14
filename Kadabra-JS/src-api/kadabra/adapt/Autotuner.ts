import Query from "@specs-feup/lara/api/weaver/Query.js";
import {
    App,
    Class,
    Constructor,
    Field,
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
import { Measurer } from "./Measurers.js";
import { primitive2Class } from "../Utils.js";
import { Configs, Configuration, PrimitiveRange } from "./Configs.js";

/******
 * Algorithms Autotuner
 *******/

/**
 * Class defining the instance of an autotuner
 */
export class Autotuner {
    name: string;
    $targetField: Field;
    $classContainer: Class;
    autotunerClass: AutotunerClass;
    autotunerType: string;
    $tuner: Field;

    /**
     * Static variables
     */
    public static readonly PACKAGE = "autotuner.";
    public static readonly MANAGER_PACKAGE = Autotuner.PACKAGE + "manager.";
    public static readonly KNOB_MANAGER_PACKAGE =
        Autotuner.PACKAGE + "knob.manager.";

    constructor(
        autotunerClass: AutotunerClass,
        $targetField: Field,
        $classContainer: Class | undefined,
        numWarmup: number,
        numRuns: number
    ) {
        this.name = "tuner";
        this.$targetField = $targetField;
        this.$classContainer =
            $classContainer ??
            newClass(autotunerClass.$class.packageName + ".Autotuners");
        this.autotunerClass = autotunerClass;
        this.autotunerType = this.autotunerClass.$class.qualifiedName;

        this.$tuner = this.$classContainer.newField(
            ["public", "static"],
            this.autotunerType,
            "tuner",
            `new ${this.autotunerType}(${numWarmup},${numRuns})`
        );
    }

    init(numWarmup: number, numRuns: number): void {
        this.newField(this.$classContainer, numWarmup, numRuns);
    }

    newField(
        $targetClass: Class,
        numWarmup: number,
        numRuns: number,
        modifiers = ["public", "static"]
    ): void {
        this.$tuner = $targetClass.newField(
            modifiers,
            this.autotunerType,
            "tuner",
            `new ${this.autotunerType}(${numWarmup},${numRuns})`
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

    updateBefore(key: string, $stmt: Statement) {
        $stmt.insertBefore(`
            ${this.getAlgorithmType()} algorithm = ${this.getAlgorithm(key)};
            ${this.$targetField} = algorithm.applyAndGet();
        `);

        return measurerProvider(this, "algorithm");
    }

    updateAfter(key: string, $stmt: Statement) {
        $stmt.insertAfter(`
            ${Autotuner.MANAGER_PACKAGE}AlgorithmSampling<${this.autotunerClass.builder.algorithmType},${this.autotunerClass.builder.measurementType}> algorithm =
                ${this.$tuner}.getAlgorithm(${key});
            ${this.$targetField} = algorithm.applyAndGet();
        `);

        return measurerProvider(this, "algorithm");
    }

    measure(key: string, $stmt: Statement, $stmtEnd: Statement = $stmt): void {
        if (this.autotunerClass.measurer === undefined) {
            throw new Error("Expected autotunerClass.measurer to be defined.");
        }

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
        $stmtEnd: Statement = $stmt
    ): void {
        if (this.autotunerClass.measurer === undefined) {
            throw new Error("Expected autotunerClass.measurer to be defined.");
        }

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

export function measurerProvider(autotuner: Autotuner, ref: string) {
    return ($stmt: Statement, $stmtEnd: Statement) =>
        autotuner.measureWithVar(ref, $stmt, $stmtEnd);
}

/*
 * Class defining the class of an autotuner
 */
export class AutotunerClass {
    $class: Class;
    builder: AutotunerBuilder;
    measurer: Measurer | undefined;

    constructor($class: Class, builder: AutotunerBuilder) {
        this.$class = $class;
        this.builder = builder;
        this.measurer = builder.measurer;
    }

    newInstance($targetField: Field, numWarmup: number, numRuns: number) {
        const $targetClass = $targetField.getAncestor("class") as
            | Class
            | undefined;
        if ($targetClass === undefined) {
            throw new Error("No class found for the given field.");
        }

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
    datasetType: string;
    algorithmType: string;
    measurementType: string;
    algorithms: Algorithm[];
    default: SimpleAlgorithm | null;
    package: string | undefined;
    distanceMethod: string | null;
    measurer: Measurer | undefined;
    configuration: string | undefined;

    constructor(
        name: string,
        datasetType: string,
        algorithmType: string,
        measurementType: string
    ) {
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

    generate(packageName = "kadabra.autotuner"): AutotunerClass {
        this.package = packageName;
        const $autotuner = GenerateTuner(this);

        return new AutotunerClass($autotuner, this);
    }

    /**
     * Adds a simple algorithm
     */
    addAlgorithm(
        algorithm: string | Method | SimpleAlgorithm | AlgorithmWithKnob,
        id?: string
    ) {
        if (id === undefined) {
            if (
                algorithm instanceof SimpleAlgorithm ||
                algorithm instanceof AlgorithmWithKnob
            ) {
                return this.pushAlgorithm(algorithm);
            } else if (typeof algorithm == "string") {
                //Will assume native code "lambda"
                return this.pushAlgorithm(new SimpleAlgorithm(algorithm));
            } else {
                throw new Error(
                    "adding an algorithm without id assumes the first argument as a SimpleAlgorithm or AlgorithmWithKnob, however, " +
                        typeof algorithm +
                        " was given"
                );
            }
        }

        if (typeof algorithm != "string" && !(algorithm instanceof Method)) {
            throw new Error(
                `Expected algorithm to be of type string or Method but was ${typeof algorithm}.`
            );
        }

        return this.pushAlgorithm(new SimpleAlgorithm(algorithm, id));
    }

    /**
     * Adds an algorithm containing a knob
     */
    addAlgorithmWithKnob(
        algorithm: string,
        id: string,
        configuration: Configuration
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
        providerType: string,
        extraArg: string
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

    pushAlgorithm(algorithm: Algorithm) {
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
    setMeasurer(measurer: Measurer) {
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
    $autotuner.newField(["private"], algListProviderType, "algListProvider");
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

    if (tuner.default !== null) {
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

    if (tuner.measurer === undefined) {
        throw new Error("Expected AutotunerBuilder.measurer");
    }

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
     ${algProvidersCode}
     `;
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
    config: Configuration | undefined;
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
        super(name, datasetType, undefined, measurementType);
        this.knobs = knobs;
        this.knobType = getKnobType(knobs);
    }
    generate(packageName: string = "kadabra.autotuner"): AutotunerClass {
        this.package = packageName;
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
    setConfig(config: Configuration, id: string) {
        this.config = config;
        this.configId = id || this.configId;
        return this;
    }

    withConfig(
        configFunction: (
            knobs: Field | Field[],
            ranges: PrimitiveRange | PrimitiveRange[]
        ) => Configuration,
        ranges: PrimitiveRange | PrimitiveRange[],
        id = "user_config"
    ) {
        this.setConfig(configFunction(this.knobs, ranges), id);
        return this;
    }

    around(ranges: PrimitiveRange | PrimitiveRange[], id = "around") {
        this.setConfig(Configs.around(this.knobs, ranges), id);
        return this;
    }

    range(ranges: PrimitiveRange | PrimitiveRange[], id = "range") {
        this.setConfig(Configs.range(this.knobs, ranges), id);
        return this;
    }

    linear(ranges: PrimitiveRange | PrimitiveRange[], id = "linear") {
        this.setConfig(Configs.linear(this.knobs, ranges), id);
        return this;
    }

    random(ranges: PrimitiveRange | PrimitiveRange[], id = "random") {
        this.setConfig(Configs.randomOf(this.knobs, ranges), id);
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
        ["int", "int"],
        ["warmup", "samples"]
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

    if (tuner.measurer === undefined) {
        throw new Error("Expected AutotunerBuilder.measurer");
    }

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
    const id = tuner.configId ?? '"config"';

    let code = "(tuple) -> {";
    if (tuner.applyKnob == undefined) {
        const knobs = tuner.knobs;
        if (!Array.isArray(knobs))
            throw new Error("[GenerateKnobTuner] knobs is not an array");
        for (let pos = 0; pos < knobs.length; pos++) {
            code +=
                knobs[pos].staticAccess + " = tuple.getValue" + pos + "(); ";
        }
        code += "}";
    } else {
        code = tuner.applyKnob;
    }

    if (tuner.config === undefined) {
        throw new Error("Expected AutotunerBuilder.measurer");
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
    autotunerClass: ControlPointClass;

    constructor(
        autotunerClass: ControlPointClass,
        $classContainer: Class | undefined,
        numWarmup: number,
        numRuns: number
    ) {
        super(autotunerClass, undefined, $classContainer, numWarmup, numRuns);
        this.autotunerClass = autotunerClass;
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
    builder: ControlPointBuilder;

    constructor($class: Class, builder: ControlPointBuilder) {
        super($class, builder);
        this.builder = builder;
    }

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
