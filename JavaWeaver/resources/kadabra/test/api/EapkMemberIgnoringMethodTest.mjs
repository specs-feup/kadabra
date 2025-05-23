import MemberIgnoringMethodDetector from "KADABRA/api/kadabra/analysis/energy/detectors/MemberIgnoringMethodDetector.js";

const r = new MemberIgnoringMethodDetector().analyse().save();

console.log(JSON.stringify(r, null, " "));
