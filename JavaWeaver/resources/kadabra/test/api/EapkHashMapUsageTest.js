laraImport("kadabra.analysis.energy.EnergyAwareAndroidPatterns");

let r = new HashMapUsageDetector()
	.analyse()
	.save();

console.log(JSON.stringify(r, null, ' '));