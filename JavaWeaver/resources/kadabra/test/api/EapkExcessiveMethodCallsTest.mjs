import ExcessiveMethodCallsDetector from "@specs-feup/kadabra/api/kadabra/analysis/energy/detectors/ExcessiveMethodCallsDetector.js";
const r = new ExcessiveMethodCallsDetector().analyse().save();

console.log(JSON.stringify(r, null, " "));
