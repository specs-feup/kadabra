import Timer from "../monitor/Timer.js";
export class Measurer {
    type;
    beginMeasure;
    endMeasure;
    getMeasure;
    newMeasurerCode;
    static PACKAGE = "autotuner.measurer.";
    constructor(type, beginMeasure, endMeasure, getMeasure, newMeasurerCode) {
        this.beginMeasure = beginMeasure;
        this.endMeasure = endMeasure;
        this.getMeasure = getMeasure;
        this.newMeasurerCode = newMeasurerCode;
        this.type = type;
    }
    static classOf(generic) {
        if (generic === undefined) {
            return Measurer.PACKAGE + "Measurer";
        }
        return Measurer.PACKAGE + "Measurer<" + generic + ">";
    }
    qualifiedType() {
        return Measurer.PACKAGE + "Measurer<" + this.type + ">";
    }
    getProvider(name) {
        const init = name === undefined
            ? ""
            : "java.util.function.Supplier<" +
                this.type +
                "> " +
                name +
                " = ";
        const fini = name === undefined ? "" : ";";
        return init + "() -> " + this.newMeasurerCode + fini;
    }
    static avgTime(timer = Timer.nanoTimer()) {
        return new Measurer("java.lang.Long", timer.start(), timer.stop(), timer.getTime(), "new " +
            Measurer.PACKAGE +
            'AvgLongMeasurer("' +
            timer.getUnit().getUnitsString() +
            '")');
    }
}
//# sourceMappingURL=Measurers.js.map