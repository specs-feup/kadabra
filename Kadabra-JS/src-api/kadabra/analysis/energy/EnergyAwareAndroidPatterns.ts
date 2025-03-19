import WeaverOptions from "@specs-feup/lara/api/weaver/WeaverOptions.js";
import { JSONtoFile } from "@specs-feup/lara/api/core/output.js";
import MemberIgnoringMethodDetector from "./detectors/MemberIgnoringMethodDetector.js";
import InternalGetterDetector from "./detectors/InternalGetterDetector.js";
import HashMapUsageDetector from "./detectors/HashMapUsageDetector.js";
import ExcessiveMethodCallsDetector from "./detectors/ExcessiveMethodCallsDetector.js";

export default class EnergyAwareAndroidPatterns {
  debugEnabled: boolean;
  detectors: any[];

  constructor(debugEnabled = false) {
    this.debugEnabled = debugEnabled;
    this.detectors = [];
  }

  analyse(packageFilter = (_: any) => true) {
    this.detectors = [
      new ExcessiveMethodCallsDetector(this.debugEnabled),
      new HashMapUsageDetector(),
      new InternalGetterDetector(),
      new MemberIgnoringMethodDetector(),
    ];

    this.detectors.forEach((d) => d.analyse(packageFilter));

    return this;
  }

  print() {
    this.detectors.forEach((d) => d.print());
    return this;
  }

  toReport() {
    const files = WeaverOptions.getData().get("workspace").getFiles();
    let sources = [];
    for (let f of files) {
      sources.push(f.toString());
    }

    let results = {};
    results["sources"] = sources;
    results["total"] = 0;
    let data = {};

    this.detectors.forEach((d) => {
      let res = d.save();
      data[`${d.name}`] = res;
      results["total"] += res.length;
    });

    results["detectors"] = data;

    return results;
  }

  toJson(path: any) {
    JSONtoFile(path, this.toReport());
  }
}
