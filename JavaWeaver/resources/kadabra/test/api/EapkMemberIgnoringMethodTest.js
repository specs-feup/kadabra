laraImport("kadabra.analysis.energy.EnergyAwareAndroidPatterns");

let r = new MemberIgnoringMethodDetector()
	.analyse()
	.save();

println(JSON.stringify(r, null, ' '));