import WeaverOptions from "@specs-feup/lara/api/weaver/WeaverOptions.js";
import { JSONtoFile } from "@specs-feup/lara/api/core/output.js";
import MemberIgnoringMethodDetector from "./detectors/MemberIgnoringMethodDetector.js";
import InternalGetterDetector from "./detectors/InternalGetterDetector.js";
import HashMapUsageDetector from "./detectors/HashMapUsageDetector.js";
import ExcessiveMethodCallsDetector from "./detectors/ExcessiveMethodCallsDetector.js";
export default class EnergyAwareAndroidPatterns {
    debugEnabled;
    detectors;
    constructor(debugEnabled = false) {
        this.debugEnabled = debugEnabled;
        this.detectors = [];
    }
    analyse() {
        this.detectors = [
            new ExcessiveMethodCallsDetector(this.debugEnabled),
            new HashMapUsageDetector(),
            new InternalGetterDetector(),
            new MemberIgnoringMethodDetector(),
        ];
        for (const d of this.detectors) {
            d.analyse();
        }
        return this;
    }
    print() {
        for (const d of this.detectors) {
            d.print();
        }
        return this;
    }
    toReport() {
        const files = WeaverOptions.getData().get("workspace").getFiles();
        const sources = [];
        for (const f of files) {
            sources.push(f.toString());
        }
        const results = {
            sources: sources,
            total: 0,
            detectors: new Map(),
        };
        for (const d of this.detectors) {
            const res = d.save();
            results.detectors.set(`${d.name}`, res);
            results.total += res.length;
        }
        return results;
    }
    toJson(path) {
        JSONtoFile(path, this.toReport());
    }
}
//# sourceMappingURL=EnergyAwareAndroidPatterns.js.map