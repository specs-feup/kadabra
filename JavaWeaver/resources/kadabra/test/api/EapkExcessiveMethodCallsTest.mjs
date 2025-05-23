laraImport("kadabra.analysis.energy.detectors.ExcessiveMethodCallsDetector");

const r = new ExcessiveMethodCallsDetector()
    .analyse()
    .save();

console.log(JSON.stringify(r, null, ' '));
