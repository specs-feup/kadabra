laraImport("kadabra.analysis.energy.EnergyAwareAndroidPatterns");

let r = new HashMapUsageDetector()
	.analyse()
	.save();

println(JSON.stringify(r, null, ' '));