laraImport("kadabra.analysis.energy.EnergyAwareAndroidPatterns");

const r = new HashMapUsageDetector()
	.analyse()
	.save();

console.log(JSON.stringify(r, null, ' '));