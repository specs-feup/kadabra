import EnergyBase from "@specs-feup/lara/api/lara/code/EnergyBase.js";
import Logger from "./Logger.js";
import IdGenerator from "@specs-feup/lara/api/lara/util/IdGenerator.js";
import PrintOnce from "@specs-feup/lara/api/lara/util/PrintOnce.js";

// TODO: Detect if Odroid or Ubuntu?
const EnergyCheckClass = "weaver.kadabra.monitor.pc.ubuntu.UbuntuEnergyCheck";

export default class Energy extends EnergyBase {
    measure($start, prefix, $end) {
        //Check for valid joinpoints and additional conditions

        if (!this.measureValidate($start, $end, "body")) {
            return;
        }

        $end = $end === undefined ? $start : $end;

        // Message about dependency
        PrintOnce.message(
            "Weaved code has dependency to project jRAPL, which can be found at https://github.com/kliu20/jRAPL"
        );

        const logger = new Logger(false, this.filename);

        // Build prefix
        if (prefix === undefined) {
            prefix = "";
        }

        const energyVar = IdGenerator.next("kadabra_energy_output_");

        const codeBefore = _energy_rapl_start(energyVar, EnergyCheckClass);
        const codeAfter = _energy_rapl_end(energyVar, EnergyCheckClass);
        $start.insertBefore(codeBefore);

        logger.append(prefix).appendDouble(energyVar);
        if (this.printUnit) {
            logger.append(this.getPrintUnit());
        }
        logger.log($end);
        $end.insertAfter(codeAfter);
    }
}

//System.out.println("Power consumption of dram: " + (after[0] - before[0]) / 10.0 + " power consumption of cpu: " + (after[1] - before[1]) / 10.0 + " power consumption of package: " + (after[2] - before[2]) / 10.0);
//Will only consider the CPU consumption
function _energy_rapl_start(energyVar, energyClass) {
    return `double[] ${energyVar}Before = ${energyClass}.getEnergyStats();`;
}

function _energy_rapl_end(energyVar, energyClass) {
    return `double[] ${energyVar}After = ${energyClass}.getEnergyStats();
double ${energyVar} = 0;
for(int ${energyVar}Counter = 0; ${energyVar}Counter < ${energyVar}Before.length; ${energyVar}Counter++){
    ${energyVar} += ${energyVar}After[ ${energyVar}Counter ] - ${energyVar}Before[ ${energyVar}Counter ]; // /10?
}
//${energyClass}.ProfileDealloc();`;
}
