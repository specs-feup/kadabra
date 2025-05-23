import InternalGetterDetector from "KADABRA/api/kadabra/analysis/energy/detectors/InternalGetterDetector.js";

const r = new InternalGetterDetector().analyse().save();

console.log(JSON.stringify(r, null, " "));
