laraImport("kadabra.analysis.energy.detectors.BaseDetector");

class HashMapUsageDetector extends BaseDetector {
  constructor() {
    super("HashMap Usage Detector");
  }

  analyseClass(jpClass) {
    super.analyseClass(jpClass);

    // TODO: refactor to use chain()
    let hashMapRefs = Query.searchFrom(jpClass, "new", { type: "HashMap" })
      .get()
      .filter(
        (jp) =>
          jp.typeReference !== undefined &&
          jp.typeReference.package === "java.util"
      );

    this.results.push(...hashMapRefs);
  }

  print() {
    println(`${this.name}:`);
    let data = this.results.map((r) => [
      r.line.toString(),
      r.ancestor("file").path,
    ]);
    Collections.printTable(["Line", "File"], data, [40, 100]);
    println();
  }

  save() {
    return this.results.map((r) => {
      let loc = ":" + r.line.toString();

      // Initialized inside method
      let node = r.ancestor("method");
      if(node != null) {
        loc = node.name + loc;
      }
      node = r.ancestor("class");
      loc = node.name + "/" + loc;
      node = node.ancestor("file");
      loc = node.name.toString() + "/" + loc;
      return loc;
    });
  }
}
