laraImport("kadabra.analysis.energy.detectors.HashMapUsageDetector");

const r = new HashMapUsageDetector()
    .analyse()
    .save();

console.log(JSON.stringify(r, null, ' '));
