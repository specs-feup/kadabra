laraImport("kadabra.analysis.energy.EnergyAwareAndroidPatterns");

const r = new MemberIgnoringMethodDetector()
	.analyse()
	.save();

console.log(JSON.stringify(r, null, ' '));