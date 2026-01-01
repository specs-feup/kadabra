import { Method } from "../../Joinpoints.js";
import { getMethod } from "../Utils.js";
import { FunctionGenerator, TransformMethod } from "./Adapter.js";
export class Algorithm {
    static PACKAGE = "autotuner.algorithm.";
    static PROVIDER_PACKAGE = Algorithm.PACKAGE + "provider.";
    lambda;
    id;
    getSupplier() {
        return "()->" + this.getAlgorithm();
    }
}
export class SimpleAlgorithm extends Algorithm {
    constructor(lambda, id) {
        super();
        if (lambda instanceof Method) {
            this.lambda = lambda.toQualifiedReference;
        }
        else {
            //assume native lambda code
            this.lambda = lambda;
        }
        this.id = id ?? this.lambda;
    }
    getAlgorithm() {
        return ("new " +
            Algorithm.PROVIDER_PACKAGE +
            "SingleAlgorithmProvider<>(" +
            this.lambda +
            ', "' +
            this.id +
            '")');
    }
    instance() {
        return ("new " +
            Algorithm.PACKAGE +
            "SimpleAlgorithm<>(" +
            this.lambda +
            ', "' +
            this.id +
            '")');
    }
}
export class AlgorithmWithKnob extends Algorithm {
    knobConsumer;
    configSupplier;
    constructor(lambda, id, knobConsumer, configSupplier) {
        super();
        this.lambda = lambda;
        this.id = id;
        this.knobConsumer = knobConsumer;
        this.configSupplier = configSupplier;
    }
    getAlgorithm() {
        return ("new " +
            Algorithm.PROVIDER_PACKAGE +
            "AlgorithmWithKnobProvider<>(" +
            this.lambda +
            ', "' +
            this.id +
            '",' +
            this.knobConsumer +
            "," +
            this.configSupplier +
            ")");
    }
}
export class AdaptiveAlgorithm extends Algorithm {
    provider;
    constructor(id, $targetMethod, templateName, provider) {
        super();
        const getter = getMethod(templateName);
        if (!(getter instanceof Method)) {
            throw new Error("[AdaptiveAlgorithm] Multiple methods or undefined method");
        }
        const $templateMethod = getter;
        const adapter = TransformMethod($targetMethod, $templateMethod);
        const field = adapter.addField(undefined, id, true);
        this.lambda = `k-> {${field.adapt("k")} return ${$targetMethod.toQualifiedReference};}`;
        this.id = id;
        this.provider = provider;
    }
    getAlgorithm() {
        return ("new " +
            Algorithm.PROVIDER_PACKAGE +
            "AdaptiveAlgorithmProvider<>(" +
            this.lambda +
            ', "' +
            this.id +
            '",' +
            this.provider +
            ")");
    }
}
export class GenerativeAlgorithm extends Algorithm {
    provider;
    extraArg;
    providerType = undefined;
    $interface;
    constructor(id, $interface, templateName, provider, providerType, extraArg) {
        super();
        const getter = getMethod(templateName);
        if (getter instanceof Method) {
            const $templateMethod = getter;
            const adapter = FunctionGenerator($templateMethod, $interface);
            if (extraArg) {
                this.lambda = `k-> ${adapter.generateQualified("k", extraArg)}`;
            }
            else {
                this.lambda = `k-> ${adapter.generateQualified("k")}`;
            }
            this.id = id;
            this.provider = provider;
            this.extraArg = extraArg;
            this.providerType = providerType;
            this.$interface = $interface;
        }
        else if (Array.isArray(getter)) {
            let getters = "";
            for (const g of getter) {
                getters += g.toQualifiedReference + ",";
            }
            throw new Error("Too much methods with template name: " +
                getter +
                ". Origins: " +
                getters);
        }
        else {
            throw new Error("[AdaptiveAlgorithm] Undefined method");
        }
    }
    getAlgorithm() {
        let genericType = "";
        if (this.providerType !== undefined) {
            const ancestor = this.$interface.getAncestor("interface");
            if (ancestor === undefined) {
                throw new Error("No interface found for the given method.");
            }
            genericType = ancestor.qualifiedName + "," + this.providerType;
        }
        return ("new " +
            Algorithm.PROVIDER_PACKAGE +
            "GenerativeAlgorithmProvider<" +
            genericType +
            ">(" +
            this.lambda +
            ',"' +
            this.id +
            '",' +
            this.provider +
            ")");
    }
}
//# sourceMappingURL=Algorithm.js.map