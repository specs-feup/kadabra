/******
 * Algorithms Autotuner
*******/

import Query from "@specs-feup/lara/api/weaver/Query.js";
import { App, Body, Class, Constructor, Field, FileJp, InterfaceType, Joinpoint, Method, Statement } from "../../Joinpoints.js";
import { newClass } from "../Factory.js";
import { Algorithm ,AlgorithmWithKnob,SimpleAlgorithm} from "./Algorithm.js";
import JoinPoints from "@specs-feup/lara/api/weaver/JoinPoints.js";

export static function measurerProvider(autotuner:Autotuner, ref:string){
    var measurer = ($stmt:Statement, $stmtEnd:Statement) =>
        autotuner.measureWithVar(ref, $stmt, $stmtEnd); 
    ;
    return measurer;
}
/**
 * Class defining the instance of an autotuner 
 */
export class Autotuner{
    name:string;
    $targetField:Field;
    $classContainer:Class|undefined = undefined;
    autotunerClass:AutotunerClass;
    autotunerType:string;
    $tuner:Field|undefined = undefined;
    
    /**
     * Static variables
     */
    public static readonly PACKAGE = "autotuner.";
    public static readonly MANAGER_PACKAGE = Autotuner.PACKAGE+"manager.";
    public static readonly KNOB_MANAGER_PACKAGE = Autotuner.PACKAGE+"knob.manager.";

    constructor(autotunerClass:AutotunerClass, $targetField:Field, $classContainer:Class,
            numWarmup:number, numRuns:number){
        this.name = "tuner";
        this.$targetField = $targetField;
        this.$classContainer = $classContainer;
        this.autotunerClass = autotunerClass;
        this.autotunerType = this.autotunerClass.$class.qualifiedName;
        this.init(numWarmup, numRuns);
    }
    init(numWarmup:number, numRuns:number):void{
        if(this.$classContainer === undefined || this.$classContainer === null){
            let nc = newClass(this.autotunerClass.$class.packageName+".Autotuners");
            this.$classContainer = nc;
        }

        this.newField(this.$classContainer, numWarmup, numRuns);
    
    }
    newField($targetClass:Class, numWarmup:number, numRuns:number, modifiers?:string[]):void{
        modifiers ??= ["public", "static"];
        this.$tuner = $targetClass.newField(modifiers, this.autotunerType,
                                    "tuner", "new "+this.autotunerType+"("+numWarmup+","+numRuns+")");
    }
    getAlgorithmType():string{
        return `${Autotuner.MANAGER_PACKAGE}AlgorithmSampling<${this.autotunerClass.builder.algorithmType},${this.autotunerClass.builder.measurementType}>`;
    }
    getAlgorithm (key:string){
        return `${this.$tuner}.getAlgorithm(${key})`;
    }
    getBest(key:string){
        return `${this.$tuner}.getBest(${key})`;
    }
    updateBefore(key:string, $stmt:Statement):any{
        $stmt.insert("before",`
            ${this.getAlgorithmType()} algorithm = ${this.getAlgorithm(key)};
            ${this.$targetField} = algorithm.applyAndGet();
        `);

        return measurerProvider(this,"algorithm");
    }
    updateAfter(key:string, $stmt:Statement){
        $stmt.insert("after", `
            ${Autotuner.MANAGER_PACKAGE}AlgorithmSampling<${this.autotunerClass.builder.algorithmType},${this.autotunerClass.builder.measurementType}> algorithm =
                ${this.$tuner}.getAlgorithm(${key});
            ${this.$targetField} = algorithm.applyAndGet();
        `);

        return measurerProvider(this,"algorithm");
    }

    measure(key:string, $stmt:Statement, $stmtEnd?:Statement):void{
        $stmtEnd ??= $stmt;
        $stmt.insert("before", `
            ${this.autotunerClass.measurer.beginMeasure}
        `);
        $stmtEnd.insert("after", `
            ${this.autotunerClass.measurer.endMeasure}
            ${this.$tuner}.update(${key}, ${this.autotunerClass.measurer.getMeasure});
        `);
    }

    measureWithVar(varName:string, $stmt:Statement, $stmtEnd:Statement):void{
        $stmtEnd ??= $stmt;
        $stmt.insert("before", `
            ${this.autotunerClass.measurer.beginMeasure}
        `);
        $stmtEnd.insert("after", `
            ${this.autotunerClass.measurer.endMeasure}
            ${varName}.update(${this.autotunerClass.measurer.getMeasure});
        `);
    }
    updateAndMeasure(key:string, $stmt:Statement, $stmtEnd:Statement){
        let measurer = this.updateBefore(key, $stmt);
        measurer($stmt, $stmtEnd);
        return this;
    }
    inBestMode(key:string):string{
        return `${this.$tuner}.inBestMode(${key})`;
    }

    isSampling(key:string):string{
        return `${this.$tuner}.isSampling(${key})`;
    }


}

/*
 * Class defining the class of an autotuner 
 */
export class AutotunerClass {
    $class:Class;
    builder:AutotunerBuilder;
    measurer:any;
    constructor($class:Class, builder:AutotunerBuilder){
        this.$class = $class;
        this.builder = builder;
        this.measurer = builder.measurer;
    }
    newInstance($targetField:Field, numWarmup:number, numRuns:number){
        let $targetClass = $targetField.getAncestor("class") as Class;
        let autotuner = new Autotuner(this, $targetField, $targetClass, numWarmup, numRuns);
        return autotuner;
    }

}


/**
 * Class defining the builder of an autotuner 
 */
export class  AutotunerBuilder{
    name:string;
    datasetType:any;
    algorithmType:any;
    measurementType:any;
    algorithms:any;
    default:any;
    package:string |undefined = undefined;
    distanceMethod:any;

    constructor(name:string, datasetType, algorithmType, measurementType){
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

    generate(package:string):AutotunerClass{
        this.package = package ?? 'kadabra.autotuner';
        let generator= GenerateTuner(this);
        //Confirm This
        generator();
        if(generator instanceof Class){
            var autotunerClass = new AutotunerClass(generator.$autotuner, this);
            return autotunerClass;
        }else{
            throw new Error("[generate] generator is undefined");
        }
        
    }
    addAlgorithm(/*Code | SimpleAlgorithm | AlgorithmWithKnob*/ algorithm:string|SimpleAlgorithm|AlgorithmWithKnob, id:string|undefined){
        if(id === undefined){
            if(algorithm instanceof SimpleAlgorithm || algorithm instanceof AlgorithmWithKnob){
                return this.pushAlgorithm(algorithm);
            }else if((typeof algorithm).equals('string')){ //Will assume native code "lambda"
                return this.pushAlgorithm(new SimpleAlgorithm(algorithm));
            }
            else{
                throw 'adding an algorithm without id assumes the first argument as a SimpleAlgorithm or AlgorithmWithKnob, however, '+typeof algorithm+' was given';
            }
        }

        return this.pushAlgorithm(new SimpleAlgorithm(algorithm,id));
    }
    addAlgorithmWithKnob(/*Code */ algorithm:string, id:string, /* Configuration */ configuration:){

        return this.pushAlgorithm(new AlgorithmWithKnob(algorithm,id, configuration.applier, configuration.get()));
    }

    addAdaptiveAlg (id:string,  targetMethod:Method,  
        templateName:string, provider:InputArgsProvider){
        return this.pushAlgorithm(new AdaptiveAlgorithm(id, targetMethod, templateName, provider));
    }
    addGenerativeAlg(id:string, $interface:InterfaceType,templateName:string, provider:InputArgsProvider , providerType:any, extraArg:string[][]){
        return this.pushAlgorithm(new GenerativeAlgorithm(id, $interface, templateName, provider, providerType, extraArg));
    }

    pushAlgorithm(algorithm:any){
        this.algorithms.push(algorithm);
        return this;
    }
    randomSampling(){
        this.configuration = Configs.order.random;
        return this;
    }
    normalSampling(){
        this.configuration = Configs.order.normal;
        return this;
    }
    setMeasurer (measurer:Measurer){
        if(!(measurer instanceof Measurer)){
            throw 'The measurer for the autotuner must be of class Measurer from the import: kadabra.adapt.Measurers';
        }
        this.measurer = measurer;
        return this;
    }


}
export static function GenerateTuner(tuner:AutotunerBuilder){
    for(const $app of Query.search(FileJp).search(App)){
        let className = tuner.name;
        if(tuner.package != undefined){
            className = tuner.package+'.'+className;
        }
        console.log("[LOG] Generating Autotuner with the qualified name: "+className);
        var expType = Autotuner.MANAGER_PACKAGE+'ExplorationSupervisor<'
                    + tuner.datasetType + ','
                    + tuner.algorithmType + ','
                    + tuner.measurementType + '>';
        let $autotuner = $app.newClass(className, expType, []);
        let algListProviderType = Algorithm.PROVIDER_PACKAGE+'AlgorithmListProvider<'+tuner.algorithmType+'>';
        $autotuner.newField(['private'], algListProviderType, 'algListProvider',undefined);
        let $constr = $autotuner.newConstructor(['public'], ['int','warmup'],['int','samples']);
        ReplaceMethodCode($constr, ` 
            super(warmup,samples);
            initAlgProvider();
        `);
        let algProviderCode = '';
        if(tuner.algorithms.length > 0){
            algProviderCode = 'algListProvider';
            for(var alg of tuner.algorithms){
                algProviderCode += '\n\t.algorithm('+alg.getSupplier()+')';
            }
            algProviderCode+=';';
        }
        $autotuner.newMethod(['private'], 'void', 'initAlgProvider', [], 
            InitCode(algProviderCode));
        var defaultCode = null;
        
        if(tuner.default != null){
            defaultCode = tuner.default.instance();
        }
        
        $autotuner.newMethod(['protected'], Algorithm.PACKAGE+'Algorithm<'+tuner.algorithmType+'>', 'defaultAlgorithm', [], 
            [`return ${defaultCode};`]);
        
        $autotuner.newMethod(['protected'], 'java.util.function.Supplier<'+tuner.measurer.qualifiedType()+'>', 'measurerProvider', [], 
            [`return ${tuner.measurer.getProvider()};`]);
        $autotuner.newMethod(['protected'], Autotuner.MANAGER_PACKAGE+'ConfigProvider<'+tuner.algorithmType+'>', 'configurationProvider', [], 
            [`return ${tuner.configuration};`]);
        $autotuner.newMethod(['protected'], 'java.util.function.BiFunction<'+tuner.datasetType+','+tuner.datasetType+',java.lang.Double>', 'distanceProvider', [], 
            [`{return ${tuner.distanceMethod};`]);
        
        var algProviderType = Algorithm.PROVIDER_PACKAGE+'AlgorithmProvider<'+tuner.algorithmType+'>';
        $autotuner.newMethod(['protected'], 'java.util.List<'+algProviderType+'>', 'getAlgorithms', [], 
            [`return algListProvider.build();`]);
        
        return $autotuner;
    }
}


export function InitCode(algProvidersCode:string){
    return `
     algListProvider = new AlgorithmListProvider<>();
     ${algProvidersCode}`;
}

export function ReplaceMethodCode($method :Constructor, code:string){
    $method.body.replaceWith(code)
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
function ControlPointBuilder(name, datasetType, knobs, measurementType){
    this.name = name;
    this.datasetType = datasetType;
    this.knobs = knobs;
    this.knobType = getKnobType(knobs);
    this.measurementType = measurementType;
    this.config = [];
    this.configId = [];
    this.default = null;
    this.package = undefined;
    this.distanceMethod = null;
    this.applyKnob = undefined;
    this.concurrent = false;
}


var TUPLES_PACKAGE = "org.javatuples.";
var TUPLES_NAMES = [undefined, "Unit", "Pair", "Triplet","Quartet", "Quintet","Sextet", "Septet", "Octet","Ennead", "Decade"];
function getKnobType(knobs){
    if(!Array.isArray(knobs)){
        return knobs.type;
    }else{
        var tupleName = TUPLES_PACKAGE+TUPLES_NAMES[knobs.length]+"<"+primitive2Class(knobs[0].type);
        for(var i = 1; i < knobs.length; i++){
            tupleName+= ", "+primitive2Class(knobs[i].type);
        }
        tupleName+=">";
        return tupleName;
    }
}


ControlPointBuilder.prototype.generate = function(package){
    this.package = package || 'kadabra.autotuner';
    var generator = new kadabra$adapt$Autotuner$GenerateKnobTuner(this);
    call generator();
    //TODO
    var autotunerClass = new ControlPointClass(generator.$autotuner, this);
    return autotunerClass;
}

aspectdef GenerateKnobTuner
    input tuner end
    output $autotuner end
    select app end
    apply
        var className = tuner.name;
        if(tuner.package != undefined){
            className = tuner.package+'.'+className;
        }
        console.log("[LOG] Generating Autotuner with the qualified name: "+className);
        var conc = tuner.concurrent?'Concurrent':'';
        var expType = Autotuner.KNOB_MANAGER_PACKAGE+'KnobExploration'+conc+'Supervisor<'
                    + tuner.datasetType + ','
                    + tuner.knobType + ','
                    + tuner.measurementType + '>';
        $autotuner = $app.exec newClass(className, expType, []);
        $constr = $autotuner.exec newConstructor(['public'], [new Pair('int','warmup'), new Pair('int','samples')]);
        call ReplaceMethodCode($constr,  %{
            super(warmup,samples);
        }%);

        
        var defaultCode = null;
        if(tuner.default != null){
            defaultCode = tuner.default;
        }
        
        $autotuner.exec newMethod(['protected'], tuner.knobType, 'defaultKnobValue', [], 
            %{return [[defaultCode]];}%);
        
        $autotuner.exec newMethod(['protected'], 'java.util.function.Supplier<'+tuner.measurer.qualifiedType()+'>', 'measurerProvider', [], 
            %{return [[tuner.measurer.getProvider()]];}%);
                
        $autotuner.exec newMethod(['protected'], 'java.util.function.BiFunction<'+tuner.datasetType+','+tuner.datasetType+',java.lang.Double>', 'distanceProvider', [], 
            %{return [[tuner.distanceMethod]];}%);

        var knobProviderType = 'KnobProvider<'+tuner.knobType+'>';
        var id = tuner.id || '"config"';
        if(tuner.applyKnob == undefined){
            var code = '(tuple) -> {';
            var pos = 0;
            knobs = tuner.knobs;
            for(var pos = 0; pos < knobs.length; pos++){
                code+= knobs[pos].staticAccess+' = tuple.getValue'+pos+'(); ';
            }
            code+='}';
        }else{
            code = tuner.applyKnob;
        }
        
        $autotuner.exec newMethod(['protected'], Autotuner.KNOB_MANAGER_PACKAGE+knobProviderType, 'getKnobProvider', [], %{
            java.util.function.Consumer<[[tuner.knobType]]> applier = [[code]];
            [[Configs.PACKAGE]]Configuration<[[tuner.knobType]]> config = [[tuner.config.get()]];
            [[knobProviderType]] provider = new KnobProvider<>([[id]], applier, config);
            return provider;
        }%);

    end
end

ControlPointBuilder.prototype.setDefault = function(code){
    this.default = code;
    return this;
}

ControlPointBuilder.prototype.setConcurrent = function(conc){
    this.concurrent = conc;
    return this;
}


/**
 * Adds a simple algorithm 
 */ 
ControlPointBuilder.prototype.setConfig = function(/*Code | SimpleAlgorithm | AlgorithmWithKnob*/ config, id){
    this.config = config;
    this.configId = id || this.configId;
    return this;
}

ControlPointBuilder.prototype.withConfig = function(configFunction, ranges,id){
    this.setConfig(configFunction(this.knobs,ranges),id || 'user_config');
    return this;
}


ControlPointBuilder.prototype.around = function(ranges,id){
    this.setConfig(Configs.around(this.knobs,ranges),id || 'around');
    return this;
}



ControlPointBuilder.prototype.range = function(ranges,id){
    this.setConfig(Configs.range(this.knobs,ranges),id || 'range');
    return this;
}

ControlPointBuilder.prototype.linear = function(ranges,id){
    this.setConfig(Configs.linear(this.knobs,ranges),id || 'linear');
    return this;
}

ControlPointBuilder.prototype.random = function(ranges,id){
    this.setConfig(Configs.randomOf(this.knobs,ranges),id || 'random');
    return this;
}

/**
 * Define the code that provides a new algorithm measurer (e.g. weaver.kadabra.control...measurers.AvgLongMeasurer )
 */
ControlPointBuilder.prototype.setMeasurer = function(measurer){
    if(!(measurer instanceof Measurer)){
        throw 'The measurer for the autotuner must be of class Measurer from the import: kadabra.adapt.Measurers';
    }
    this.measurer = measurer;
    return this;
}


/******
 * Algorithms Autotuner
*******/
/**
 * Class defining the instance of an autotuner 
 */
function ControlPoint(/*ControlPointClass*/ autotunerClass, /*Class*/ $classContainer,
            /*int*/ numWarmup, /*int*/ numRuns){
    this.name = "tuner";
    this.$classContainer = $classContainer;
    this.autotunerClass = autotunerClass;
    this.autotunerType = this.autotunerClass.$class.qualifiedName;
    this.init(numWarmup, numRuns);
}

ControlPoint.prototype.init = function(numWarmup, numRuns){
    if(this.$classContainer === undefined || this.$classContainer === null){
        nc = call NewClass(this.autotunerClass.$class.packageName+".Autotuners");
        this.$classContainer = nc.$class;
    }

    this.newField(this.$classContainer, numWarmup, numRuns);
    
}

ControlPoint.prototype.newField = function($targetClass, numWarmup, numRuns, modifiers){
    
    modifiers = modifiers || ["public", "static"];
    this.$tuner = $targetClass.exec newField(modifiers, this.autotunerType,
                                "tuner", "new "+this.autotunerType+"("+numWarmup+","+numRuns+")");
}

ControlPoint.prototype.getKnobType = function(){
    return %{[[Autotuner.KNOB_MANAGER_PACKAGE]]KnobSampling<[[this.autotunerClass.builder.knobType]],[[this.autotunerClass.builder.measurementType]]>}%;
}
ControlPoint.prototype.getKnob = function(key){
    return %{[[this.$tuner]].getKnob([[key]])}%;
}

ControlPoint.prototype.getBest = function(key){
    return %{[[this.$tuner]].getBest([[key]])}%;
}


ControlPoint.prototype.updateBefore = function(key, $stmt){
    $stmt.insert before %{
        [[this.getKnobType()]] knob = [[this.getKnob(key)]];
        knob.apply();
    }%;

    //return "algorithm";
    return measurerProvider(this,"knob");
}



ControlPoint.prototype.updateAfter = function(key, $stmt){
    $stmt.insert after %{
        [[this.getKnobType()]] knob = [[this.getKnob(key)]];
        knob.apply();
    }%;

    return measurerProvider(this,"knob");
}


ControlPoint.prototype.measure = function(key, $stmt, $stmtEnd){
    $stmtEnd = $stmtEnd || $stmt;
    $stmt.insert before %{
        [[this.autotunerClass.measurer.beginMeasure]]
    }%;
    $stmtEnd.insert after %{
        [[this.autotunerClass.measurer.endMeasure]]
        [[this.$tuner]].update([[key]], [[this.autotunerClass.measurer.getMeasure]]);
    }%;
}

ControlPoint.prototype.measureWithVar = function(varName, $stmt, $stmtEnd){
    $stmtEnd = $stmtEnd || $stmt;
    $stmt.insert before %{
        [[this.autotunerClass.measurer.beginMeasure]]
    }%;
    $stmtEnd.insert after %{
        [[this.autotunerClass.measurer.endMeasure]]
        [[varName]].update([[this.autotunerClass.measurer.getMeasure]]);
    }%;
}



ControlPoint.prototype.updateAndMeasure = function(key, $stmt, $stmtEnd){
    measurer = this.updateBefore(key, $stmt);
    measurer($stmt, $stmtEnd);
}

ControlPoint.prototype.inBestMode = function(key){
    return %{[[this.$tuner]].inBestMode([[key]])}%;
}

ControlPoint.prototype.isSampling = function(key){
    return %{[[this.$tuner]].isSampling([[key]])}%;
}


/**
 * Class defining the class of an autotuner 
 */
function ControlPointClass(/*class*/ $class, /*ControlPointBuilder*/ builder){
    this.$class = $class;
    this.builder = builder;
    this.measurer = builder.measurer;
}

ControlPointClass.prototype.newInstance = function($targetClass, numWarmup, numRuns, modifiers){
    var autotuner = new ControlPoint(this, $targetClass, numWarmup, numRuns);
    return autotuner;
}
