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
    console.log(`${this.name}:`);
    let data = this.results.map((r) => [
      r.line.toString(),
      r.getAncestor("file").path,
    ]);
    Collections.printTable(["Line", "File"], data, [40, 100]);
    console.log();
  }

  save() {
    return this.results.map((r) => {
      let loc = ":" + r.line.toString();

      // Initialized inside method
      let node = r.getAncestor("method");
      if(node != null) {
        loc = node.name + loc;
      }
      node = r.getAncestor("class");
      loc = node.name + "/" + loc;
      node = node.getAncestor("file");
      loc = node.name.toString() + "/" + loc;
      return loc;
    });
  }
}
