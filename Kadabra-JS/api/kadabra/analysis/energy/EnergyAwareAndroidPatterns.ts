import WeaverOptions from "@specs-feup/lara/api/weaver/WeaverOptions.ts";
import { JSONtoFile } from "@specs-feup/lara/api/core/output.ts";
import MemberIgnoringMethodDetector from "./detectors/MemberIgnoringMethodDetector.ts";
import InternalGetterDetector from "./detectors/InternalGetterDetector.ts";
import HashMapUsageDetector from "./detectors/HashMapUsageDetector.ts";
import ExcessiveMethodCallsDetector from "./detectors/ExcessiveMethodCallsDetector.ts";
import BaseDetector from "./detectors/BaseDetector.ts";

export interface Report {
    sources: string[];
    total: number;
    detectors: Map<string, string[]>;
}

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
        const sources: string[] = [];
        for (const f of files) {
            sources.push(f.toString());
        }

        const results: Report = {
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

    toJson(path: string) {
        JSONtoFile(path, this.toReport());
    }
}
