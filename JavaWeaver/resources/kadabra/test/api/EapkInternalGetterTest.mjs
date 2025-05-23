laraImport("kadabra.analysis.energy.detectors.InternalGetterDetector");

const r = new InternalGetterDetector()
    .analyse()
    .save();

console.log(JSON.stringify(r, null, ' '));
