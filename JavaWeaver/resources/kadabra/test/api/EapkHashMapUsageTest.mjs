import HashMapUsageDetector from "@specs-feup/kadabra/api/kadabra/analysis/energy/detectors/HashMapUsageDetector.js";

const r = new HashMapUsageDetector().analyse().save();

console.log(JSON.stringify(r, null, " "));
