laraImport("kadabra.analysis.energy.detectors.BaseDetector");

class InternalGetterDetector extends BaseDetector {
  constructor() {
    super("Internal Getter Detector");
  }

  analyseClass(jpClass) {
    super.analyseClass(jpClass);

    const notVoidFilter = (rr) => rr !== "void";

    let simpleGetters = Query.childrenFrom(jpClass, "method", {
      returnType: notVoidFilter,
      isStatic: false,
    })
      .children("body", { numChildren: 1 })
      .children("return")
      .children("var", { isField: true })
      .chain()
      .map((m) => m["method"]);

    let internalCalls = Query.searchFrom(jpClass, "call", {
      decl: (d) => d !== undefined && simpleGetters.some((sg) => sg.same(d)),
    }).get();

    this.results.push(...internalCalls);
  }

  print() {
    println(`${this.name}:`);
    let data = this.results.map((r) => [
      r.line.toString(),
      r.name,
      r.ancestor("file").path,
    ]);
    printTable(["Line", "Call", "File"], data, [10, 30, 100]);
    println();
  }

  save() {
    return this.results.map((r) => {
      let loc = r.name + "(" + r.arguments + "):" + r.line.toString();

      // Initialized inside method
      let node = r.ancestor("method");
      if(node !== undefined) {
        loc = node.name + "/" + loc;
      }
      else {
        node = r.ancestor("constructor");
        loc = node.name + "/" + loc;
      }
      node = r.ancestor("class");
      loc = node.name + "/" + loc;
      node = node.ancestor("file");
      loc = node.name.toString() + "/" + loc;
      return loc;
    });
  }
}
