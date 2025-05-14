import { InterfaceType, Method } from "../../Joinpoints.js";
import { getMethod } from "../Utils.js";
import { FunctionGenerator, TransformMethod } from "./Adapter.js";

export abstract class Algorithm {
    public static readonly PACKAGE = "autotuner.algorithm.";
    public static readonly PROVIDER_PACKAGE = Algorithm.PACKAGE + "provider.";

    lambda: string | undefined;
    id: string | undefined;

    abstract getAlgorithm(): string;

    getSupplier() {
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
    knobConsumer: string;
    configSupplier: string;

    constructor(
        lambda: string,
        id: string,
        knobConsumer: string,
        configSupplier: string
    ) {
        super();
        this.lambda = lambda;
        this.id = id;
        this.knobConsumer = knobConsumer;
        this.configSupplier = configSupplier;
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

    constructor(
        id: string,
        $targetMethod: Method,
        templateName: string,
        provider: string
    ) {
        super();
        const getter = getMethod(templateName);

        if (!(getter instanceof Method)) {
            throw new Error(
                "[AdaptiveAlgorithm] Multiple methods or undefined method"
            );
        }

        const $templateMethod = getter;
        const adapter = TransformMethod($targetMethod, $templateMethod);
        const field = adapter.addField(undefined, id, true);

        this.lambda = `k-> {${field.adapt("k")} return ${
            $targetMethod.toQualifiedReference
        };}`;

        this.id = id;
        this.provider = provider;
    }

    getAlgorithm() {
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
    extraArg: string;
    providerType: string | undefined = undefined;
    $interface: Method;

    constructor(
        id: string,
        $interface: Method,
        templateName: string,
        provider: string,
        providerType: string,
        extraArg: string
    ) {
        super();
        const getter = getMethod(templateName);

        if (getter instanceof Method) {
            const $templateMethod = getter;
            const adapter = FunctionGenerator($templateMethod, $interface);

            if (extraArg) {
                this.lambda = `k-> ${adapter.generateQualified("k", extraArg)}`;
            } else {
                this.lambda = `k-> ${adapter.generateQualified("k")}`;
            }
            this.id = id;
            this.provider = provider;
            this.extraArg = extraArg;
            this.providerType = providerType;
            this.$interface = $interface;
        } else if (Array.isArray(getter)) {
            let getters = "";
            for (const g of getter) {
                getters += g.toQualifiedReference + ",";
            }
            throw new Error(
                "Too much methods with template name: " +
                    getter +
                    ". Origins: " +
                    getters
            );
        } else {
            throw new Error("[AdaptiveAlgorithm] Undefined method");
        }
    }

    getAlgorithm(): string {
        let genericType = "";
        if (this.providerType !== undefined) {
            const ancestor = this.$interface.getAncestor("interface") as
                | InterfaceType
                | undefined;

            if (ancestor === undefined) {
                throw new Error("No interface found for the given method.");
            }

            genericType = ancestor.qualifiedName + "," + this.providerType;
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
