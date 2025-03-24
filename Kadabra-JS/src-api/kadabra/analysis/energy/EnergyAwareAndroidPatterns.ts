import WeaverOptions from "@specs-feup/lara/api/weaver/WeaverOptions.js";
import { JSONtoFile } from "@specs-feup/lara/api/core/output.js";
import MemberIgnoringMethodDetector from "./detectors/MemberIgnoringMethodDetector.js";
import InternalGetterDetector from "./detectors/InternalGetterDetector.js";
import HashMapUsageDetector from "./detectors/HashMapUsageDetector.js";
import ExcessiveMethodCallsDetector from "./detectors/ExcessiveMethodCallsDetector.js";
import BaseDetector from "./detectors/BaseDetector.js";

export default class EnergyAwareAndroidPatterns {
    debugEnabled: boolean;
    detectors: BaseDetector[];

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

        const results = {};
        results["sources"] = sources;
        results["total"] = 0;
        const data = {};

        for (const d of this.detectors) {
            const res = d.save();
            data[`${d.name}`] = res;
            results["total"] += res.length;
        }

        results["detectors"] = data;

        return results;
    }

    toJson(path: string) {
        JSONtoFile(path, this.toReport());
    }
}
