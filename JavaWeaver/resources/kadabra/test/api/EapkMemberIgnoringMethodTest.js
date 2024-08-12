laraImport("kadabra.analysis.energy.EnergyAwareAndroidPatterns");

let r = new MemberIgnoringMethodDetector()
	.analyse()
	.save();

console.log(JSON.stringify(r, null, ' '));