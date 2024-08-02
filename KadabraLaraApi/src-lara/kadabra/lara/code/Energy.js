import lara.code.EnergyBase;
import lara.code.Logger;
import lara.util.IdGenerator;
import lara.util.PrintOnce;

// TODO: Detect if Odroid or Ubuntu?
var EnergyCheckClass = "weaver.kadabra.monitor.pc.ubuntu.UbuntuEnergyCheck";

Energy.prototype.measure = function($start, prefix, $end){
	//Check for valid joinpoints and additional conditions

	if(!this._measureValidate($start, $end, 'body')){
		return;
	}
	
	//if($start.instanceOf("call") && $start.name==="<init>"){
	//	return;
	//}	
	
	$end = $end === undefined ? $start : $end;

	// Message about dependency
	PrintOnce.message("Weaved code has dependency to project jRAPL, which can be found at https://github.com/kliu20/jRAPL");

	var logger = new Logger(false, this.filename);

	// Build prefix
	if(prefix === undefined) {
		prefix = "";
	}

	
	var energyVar = IdGenerator.next("kadabra_energy_output_");



	var codeBefore = _energy_rapl_start(energyVar,EnergyCheckClass);
	var codeAfter = _energy_rapl_end(energyVar,EnergyCheckClass);
	$start.insert before codeBefore;
		
	logger.append(prefix).appendDouble(energyVar);
	if (this.printUnit) {
        logger.append(this.getPrintUnit());
    }
	logger.log($end);
	$end.insert after codeAfter;

}

//System.out.println("Power consumption of dram: " + (after[0] - before[0]) / 10.0 + " power consumption of cpu: " + (after[1] - before[1]) / 10.0 + " power consumption of package: " + (after[2] - before[2]) / 10.0);
//Will only consider the CPU consumption
codedef _energy_rapl_start(energyVar, energyClass)%{
double[] [[energyVar]]Before = [[energyClass]].getEnergyStats();
}%end

codedef _energy_rapl_end(energyVar, energyClass)%{
double[] [[energyVar]]After = [[energyClass]].getEnergyStats();
double [[energyVar]] = 0;
for(int [[energyVar]]Counter = 0; [[energyVar]]Counter < [[energyVar]]Before.length; [[energyVar]]Counter++){
	[[energyVar]] += [[energyVar]]After[ [[energyVar]]Counter ] - [[energyVar]]Before[ [[energyVar]]Counter ]; // /10?
}
//[[energyClass]].ProfileDealloc();
}%end