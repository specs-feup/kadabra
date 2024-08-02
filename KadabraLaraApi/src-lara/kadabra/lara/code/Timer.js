laraImport("lara.code.TimerBase");
laraImport("lara.code.Logger");

laraImport("lara.util.IdGenerator");
laraImport("lara.Platforms");
laraImport("lara.util.TimeUnits");

class Timer extends TimerBase {
    time($start, prefix, $end) {
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
        if (this.print) {
            const logger = new Logger(false, this.filename);

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
        let afterJp = $timeMeasure;

        // Print after measure code
        if (this.print) {
            logger.log($timeMeasure);
            afterJp = logger.getAfterJp();
        }

        this.setAfterJp(afterJp);

        return intervalVar;
    }
}

//Java codedefs
function _timer_java_now(timeVar) {
    return `long ${timeVar} = System.nanoTime();`;
}

function _timer_java_calc_interval(timeVar, durationVar, factorConversion) {
    return `double ${durationVar} = (double)(System.nanoTime() - ${timeVar}) /  (double)${factorConversion};`;
}
