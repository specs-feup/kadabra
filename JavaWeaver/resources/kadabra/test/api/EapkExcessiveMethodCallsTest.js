laraImport("kadabra.analysis.energy.EnergyAwareAndroidPatterns");

let r = new ExcessiveMethodCallsDetector()
	.analyse()
	.save();

console.log(JSON.stringify(r, null, ' '));