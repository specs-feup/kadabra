laraImport("kadabra.analysis.energy.detectors.BaseDetector");

class MemberIgnoringMethodDetector extends BaseDetector {
  constructor() {
    super("Member Ignoring Method Detector");

    this.dups = new Map();
    this.computeSameNameMethods();
  }

  static noOverrideAnnotationFilter(annos) {
    return annos.filter((a) => a.type === "Override").length === 0;
  }

  computeSameNameMethods() {
    let methods = Query.search("method", {
      isStatic: false,
      isFinal: false,
      privacy: (p) => p !== "private",
      annotations: MemberIgnoringMethodDetector.noOverrideAnnotationFilter,
    }).get();

    for (const m of methods) {
      let dup = this.dups.get(m.name);
      if (dup !== undefined) {
        dup.push(m);
      } else {
        this.dups.set(m.name, [m]);
      }
    }
  }

  analyseClass(jpClass) {
    super.analyseClass(jpClass);

    let checkOverride = !(
      jpClass.interfaces.length == 0 && jpClass.superClassJp == undefined
    );

    let mightBeStatic = Query.childrenFrom(jpClass, "method", {
      isStatic: false,
      isFinal: false,
      privacy: (p) => p !== "private",
      annotations: MemberIgnoringMethodDetector.noOverrideAnnotationFilter,
    }).get();

    for (let m of mightBeStatic) {
      if (m.body === undefined || m.body.children.length === 0) continue;

      if (!this.#methodCanBeStatic(m)) continue;

      if (checkOverride && this.#isOverride(m)) continue;

      this.results.push(m);
    }
  }

  print() {
    console.log(`${this.name}:`);
    let data = this.results.map((r) => [
      r.line.toString(),
      r.name,
      r.getAncestor("file").path,
    ]);
    Collections.printTable(["Line", "Method", "File"], data, [10, 30, 100]);
    console.log();
  }

  save() {
    return this.results.map((r) => {
      let node = r;
      let loc = node.name;
      node = node.getAncestor("class");
      loc = node.name + "/" + loc;
      node = node.getAncestor("file");
      loc = node.name.toString() + "/" + loc;
      return loc + ":" + r.line.toString();
    });
  }

  #isOverride(jpMethod) {
    let dups = this.dups.get(jpMethod.name);
    if (dups === undefined || dups.length < 2) return false;

    for (const dup of dups) {
      if (dup.declarator === jpMethod.declarator) continue;

      if (dup.isOverriding(jpMethod) || jpMethod.isOverriding(dup)) {
        return true;
      }
    }
    return false;
  }

  #methodCanBeStatic(jp) {
    if (jp.instanceOf("call") && !this.#callIsStatic(jp)) {
      return false;
    }

    if (jp.instanceOf("var") && !this.#varIsStatic(jp)) {
      return false;
    }

    for (let child of jp.children) {
      if (!this.#methodCanBeStatic(child)) return false;
    }

    return true;
  }

  #callIsStatic(jpCall) {
    return jpCall.decl !== undefined && jpCall.decl.isStatic;
  }

  #varIsStatic(jpVar) {
    const isParameter =
      Query.childrenFrom(jpVar, "reference", { type: "Parameter" }).get()
        .length > 0;
    return isParameter ? true : jpVar.isStatic;
  }
}
