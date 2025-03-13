laraImport("kadabra.analysis.energy.EnergyAwareAndroidPatterns");

const r = new ExcessiveMethodCallsDetector()
	.analyse()
	.save();

console.log(JSON.stringify(r, null, ' '));