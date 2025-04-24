import { Class } from "../../Joinpoints.js";
import { newClass } from "../Factory.js";

export default class DataFeature {
    static readonly PACKAGE = "kadabra.adapt.features";

    name: string;
    qualifiedName: string;
    features: never[];
    nc: Class | undefined;

    constructor(name: string) {
        this.name = name;
        this.qualifiedName = name;
        this.features = [];
        this.nc = undefined;
    }

    generate(packageName: string = DataFeature.PACKAGE) {
        this.qualifiedName = packageName + "." + this.name;
        this.nc = newClass(this.qualifiedName, `Comparable<${this.name}>`);
        return this.nc;
    }

    addFeature() {
        return this.nc;
    }
}
