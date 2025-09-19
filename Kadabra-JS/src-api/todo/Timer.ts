import TimerBase from "@specs-feup/lara/api/lara/code/TimerBase.js";
import IdGenerator from "@specs-feup/lara/api/lara/util/IdGenerator.js";
import Logger from "./Logger.js";
import { Joinpoint } from "../Joinpoints.js";

export default class Timer extends TimerBase<Joinpoint> {
    time($start: Joinpoint, prefix: string, $end: Joinpoint) {
        if (!this._timeValidate($start, $end, "executable")) {
            return;
        }

        // Build prefix
        if (prefix === undefined) {
            prefix = "";
        }

        if ($end === undefined) {
            $end = $start;
        }

        // Declare variable for time interval, which uses calculation as initialization
        //var timeIntervalVar = IdGenerator.next("kadabra_timing_duration_");
        //var $timingResultDecl;
        // Add includes

        // get variable names
        const startVar = IdGenerator.next("kadabra_timing_start_");
        const intervalVar = IdGenerator.next("kadabra_timing_interval_");

        const codeBefore = _timer_java_now(startVar);
        const codeAfter = _timer_java_calc_interval(
            startVar,
            intervalVar,
            this.timeUnits.getMagnitudeFactorFromNanoseconds()
        );

        // Build message
        let logger;
        if (this.print) {
            logger = new Logger(false, this.filename);

            logger.append(prefix).appendDouble(intervalVar);
            if (this.printUnit) {
                logger.append(this.timeUnits.getUnitsString());
            }
            logger.ln();
        }

        // Insert code
        $start.insertBefore(codeBefore);

        // insert measuring code after $end point
        const $timeMeasure = $end.insertAfter(codeAfter);
        let afterJp: Joinpoint | undefined = $timeMeasure;

        // Print after measure code
        if (this.print) {
            logger!.log($timeMeasure);
            afterJp = logger!.getAfterJp();
        }

        this.setAfterJp(afterJp);

        return intervalVar;
    }
}

//Java codedefs
function _timer_java_now(timeVar: string) {
    return `long ${timeVar} = System.nanoTime();`;
}

function _timer_java_calc_interval(timeVar: string, durationVar: string, factorConversion: number) {
    return `double ${durationVar} = (double)(System.nanoTime() - ${timeVar}) /  (double)${factorConversion};`;
}
