import { newClass } from "../Factory.js";
export default class DataFeature {
    static PACKAGE = "kadabra.adapt.features";
    name;
    qualifiedName;
    features;
    nc;
    constructor(name) {
        this.name = name;
        this.qualifiedName = name;
        this.features = [];
        this.nc = undefined;
    }
    generate(packageName) {
        packageName ??= DataFeature.PACKAGE;
        this.qualifiedName = packageName + "." + this.name;
        this.nc = newClass(this.qualifiedName, `Comparable<${this.name}>`);
        return this.nc;
    }
    addFeature() {
        return this.nc;
    }
}
//# sourceMappingURL=DataFeature.js.map