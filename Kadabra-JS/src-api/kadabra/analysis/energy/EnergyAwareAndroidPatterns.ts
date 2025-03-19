laraImport("weaver.WeaverOptions");
laraImport("kadabra.analysis.energy.detectors.MemberIgnoringMethodDetector");
laraImport("kadabra.analysis.energy.detectors.InternalGetterDetector");
laraImport("kadabra.analysis.energy.detectors.HashMapUsageDetector");
laraImport("kadabra.analysis.energy.detectors.ExcessiveMethodCallsDetector");

class EnergyAwareAndroidPatterns {
  constructor(debugEnabled = false) {
    this.debugEnabled = debugEnabled;
    this.detectors = [];
  }

  analyse(packageFilter = (_) => true) {
    this.detectors = [
      new ExcessiveMethodCallsDetector(this.debugEnabled),
      new HashMapUsageDetector(this.debugEnabled),
      new InternalGetterDetector(this.debugEnabled),
      new MemberIgnoringMethodDetector(this.debugEnabled),
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

  toJson(path) {
    Io.writeJson(path, this.toReport());
  }
}
