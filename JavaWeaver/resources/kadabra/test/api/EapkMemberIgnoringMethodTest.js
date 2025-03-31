laraImport("kadabra.analysis.energy.detectors.MemberIgnoringMethodDetector");

const r = new MemberIgnoringMethodDetector()
    .analyse()
    .save();

console.log(JSON.stringify(r, null, ' '));
