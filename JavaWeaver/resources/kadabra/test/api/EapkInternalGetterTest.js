laraImport("kadabra.analysis.energy.EnergyAwareAndroidPatterns");

let r = new InternalGetterDetector()
	.analyse()
	.save();

println(JSON.stringify(r, null, ' '));