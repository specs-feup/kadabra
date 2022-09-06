laraImport("kadabra.analysis.energy.EnergyAwareAndroidPatterns");

let r = new ExcessiveMethodCallsDetector()
	.analyse()
	.save();

println(JSON.stringify(r, null, ' '));