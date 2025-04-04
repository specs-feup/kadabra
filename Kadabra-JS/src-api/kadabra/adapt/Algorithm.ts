import { TransformMethod, FunctionGenerator } from "./Adapter.js";
import { GetMethod } from "../Utils.js";

import { Method } from "../../Joinpoints.js";

export abstract class Algorithm {
    public static readonly PACKAGE = "autotuner.algorithm.";
    public static readonly PROVIDER_PACKAGE = this.PACKAGE + ".provider.";

    lambda: string;
    id: string;

    abstract getAlgorithm(): string;

    getSupplier(): string {
        return "()->" + this.getAlgorithm();
    }
}

export class SimpleAlgorithm extends Algorithm {
    constructor(lambda: string | Method, id?: string) {
        super();
        if (lambda instanceof Method) {
            this.lambda = lambda.toQualifiedReference;
        } else {
            //assume native lambda code
            this.lambda = lambda;
        }
        this.id = id ?? this.lambda;
        //	this.isAlgorithm = true; //helper
    }

    getAlgorithm(): string {
        return (
            "new " +
            Algorithm.PROVIDER_PACKAGE +
            "SingleAlgorithmProvider<>(" +
            this.lambda +
            ', "' +
            this.id +
            '")'
        );
    }

    instance(): string {
        return (
            "new " +
            Algorithm.PACKAGE +
            "SimpleAlgorithm<>(" +
            this.lambda +
            ', "' +
            this.id +
            '")'
        );
    }
}

export class AlgorithmWithKnob extends Algorithm {
    knobConsumer: any;
    configSupplier: any;

    constructor(lambda: any, id: any, knobConsumer: any, configSupplier: any) {
        super();
        this.lambda = lambda;
        this.id = id;
        this.knobConsumer = knobConsumer;
        this.configSupplier = configSupplier;
        //	this.isAlgorithm = true; //helper
    }

    getAlgorithm(): string {
        return (
            "new " +
            Algorithm.PROVIDER_PACKAGE +
            "AlgorithmWithKnobProvider<>(" +
            this.lambda +
            ', "' +
            this.id +
            '",' +
            this.knobConsumer +
            "," +
            this.configSupplier +
            ")"
        );
    }
}

export class AdaptiveAlgorithm extends Algorithm {
    provider: string;

    constructor(id, $targetMethod, templateName, provider) {
        super();
        const getter = GetMethod(templateName);
        const $templateMethod = getter.methods;
        //	console.log($templateMethod);
        const adapter = TransformMethod($targetMethod, $templateMethod);
        const field = adapter.addField(undefined, id, true);

        this.lambda = `k-> ${field.adapt("k")} return ${
            $targetMethod.toQualifiedReference
        };}`;

        this.id = id;
        this.provider = provider;
    }
    getAlgorithm(): string {
        return (
            "new " +
            Algorithm.PROVIDER_PACKAGE +
            "AdaptiveAlgorithmProvider<>(" +
            this.lambda +
            ', "' +
            this.id +
            '",' +
            this.provider +
            ")"
        );
    }
}

export class GenerativeAlgorithm extends Algorithm {
    provider: string;
    extraArg: string[];
    providerType: any;
    $interface: any;
    constructor(
        id: string,
        $interface: Method,
        templateName: string,
        provider: string,
        providerType: any,
        extraArg?: string[]
    ) {
        super();
        const getter = GetMethod(templateName);

        const $templateMethod = getter.methods;
        if (Array.isArray($templateMethod)) {
            let getters = "";
            for (const g of $templateMethod) {
                getters += g.toQualifiedReference + ",";
            }
            throw (
                "Too much methods with template name: " +
                templateName +
                ". Origins: " +
                getters
            );
        }
        const adapter = FunctionGenerator($templateMethod, $interface);
        //field = adapter.addField(undefined,id,true);
        //TODO: check this
        //before: this.lambda = %{k-> [[adapter.generateQualified("k", extraArg)]]}%;
        //this.lambda = %{k-> [[adapter.generateQualified("k")]]}%;

        if (extraArg) {
            this.lambda = `k-> ${adapter.generateQualified("k", extraArg)}`;
            adapter.generateQualified([["k"], extraArg]);
        } else {
            this.lambda = adapter.generateQualified([["k"]]);
        }
        this.id = id;
        this.provider = provider;
        this.extraArg = extraArg;
        this.providerType = providerType;
        this.$interface = $interface;
    }
    getAlgorithm(): string {
        let genericType = "";
        if (this.providerType != undefined) {
            const interfType =
                this.$interface.getAncestor("interface").qualifiedName;
            genericType = interfType + "," + this.providerType;
        }
        return (
            "new " +
            Algorithm.PROVIDER_PACKAGE +
            "GenerativeAlgorithmProvider<" +
            genericType +
            ">(" +
            this.lambda +
            ',"' +
            this.id +
            '",' +
            this.provider +
            ")"
        );
    }
}
