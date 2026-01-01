import MemberIgnoringMethodDetector from "@specs-feup/kadabra/api/kadabra/analysis/energy/detectors/MemberIgnoringMethodDetector.js";

const r = new MemberIgnoringMethodDetector().analyse().save();

console.log(JSON.stringify(r, null, " "));
