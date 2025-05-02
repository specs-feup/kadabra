import Timer from "../monitor/Timer.js";

export class Measurer {
    type: string;
    beginMeasure: string;
    endMeasure: string;
    getMeasure: string;
    newMeasurerCode: string;
    public static readonly PACKAGE = "autotuner.measurer.";

    constructor(
        type: string,
        beginMeasure: string,
        endMeasure: string,
        getMeasure: string,
        newMeasurerCode: string
    ) {
        this.beginMeasure = beginMeasure;
        this.endMeasure = endMeasure;
        this.getMeasure = getMeasure;
        this.newMeasurerCode = newMeasurerCode;
        this.type = type;
    }
    static classOf(generic: string) {
        if (generic === undefined) {
            return Measurer.PACKAGE + "Measurer";
        }
        return Measurer.PACKAGE + "Measurer<" + generic + ">";
    }
    qualifiedType() {
        return Measurer.PACKAGE + "Measurer<" + this.type + ">";
    }
    getProvider(name?: string) {
        const init =
            name === undefined
                ? ""
                : "java.util.function.Supplier<" +
                  this.type +
                  "> " +
                  name +
                  " = ";
        const fini = name === undefined ? "" : ";";
        return init + "() -> " + this.newMeasurerCode + fini;
    }
    static avgTime(timer: Timer = Timer.nanoTimer()) {
        return new Measurer(
            "java.lang.Long",
            timer.start(),
            timer.stop(),
            timer.getTime(),
            "new " +
                Measurer.PACKAGE +
                'AvgLongMeasurer("' +
                timer.getUnit().getUnitsString() +
                '")'
        );
    }
}
