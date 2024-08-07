laraImport("kadabra.analysis.energy.EnergyAwareAndroidPatterns");

let r = new InternalGetterDetector()
	.analyse()
	.save();

console.log(JSON.stringify(r, null, ' '));