laraImport("kadabra.analysis.energy.EnergyAwareAndroidPatterns");

const r = new InternalGetterDetector()
	.analyse()
	.save();

console.log(JSON.stringify(r, null, ' '));