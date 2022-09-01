laraImport("kadabra.analysis.energy.detectors.BaseDetector");

class ExcessiveMethodCallsDetector extends BaseDetector {
  constructor(debugEnabled = false) {
    super("Excessive Method Calls Detector", debugEnabled);
  }

  static containsJP(arr, jp) {
    return arr.some((i) => i.same(jp));
  }

  static tryGetMethodInfo(arr, jp) {
    for (let j of arr) {
      if (j.method.same(jp)) return j;
    }
    return null;
  }

  static addIfNew(arr, jp) {
    if (!ExcessiveMethodCallsDetector.containsJP(arr, jp)) arr.push(jp);
  }

  analyseClass(jpClass) {
    super.analyseClass(jpClass);

    this.currentPackage = jpClass.package;
    let fileName = jpClass.ancestor("file");

    let loops = Query.searchFrom(jpClass, "loop").get();
    loops.forEach((l) => {
      this.printDebugInfo(`\n${fileName} @ L${l.line}`);
      this.analyseLoop(l);
    });
  }

  analyseLoop(jpLoop) {
    this.resetDetector();

    // First pass to collect information on read/write variables and methods
    this.printDebugInfo("Collect Loop Info:");
    this.collectLoopInfo(jpLoop);

    if (this.debugEnabled) {
      print("Global Reads: ");
      this.loopGlobalReads.forEach((lgr) => print(lgr.name + ", "));
      print("\nGlobal Writes: ");
      this.loopGlobalWrites.forEach((lgw) => print(lgw.name + ", "));
      print("\nLocal Writes: ");
      this.loopLocalWrites.forEach((llw) => print(llw.name + ", "));
      print("\n");
    }

    // Second pass to analyse loop calls against collected loop info
    this.printDebugInfo("Analyse Loop Calls:");
    let calls = this.getFirstDescendentsOfTypes(jpLoop, ["call"]);
    calls.forEach((c) => this.analyseLoopCall(c));
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

  resetDetector() {
    this.loopGlobalReads = [];
    this.loopLocalWrites = [];
    this.loopGlobalWrites = [];
    this.methodsInfo = [];
    this.variantCalls = [];
    this.missingCallDecl = false;
  }

  printDebugInfo(msg) {
    if (!this.debugEnabled) return;

    println(msg);
  }

  save() {
    return this.results.map((r) => {
      let loc = r.name + "(" + r.arguments + "):" + r.line.toString();

      // Initialized inside method
      let node = r.ancestor("method");
      if (node !== undefined) {
        loc = node.name + "/" + loc;
      } else {
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

  ////////// First pass //////////

  collectLoopInfo(jp) {
    const addIfNew = ExcessiveMethodCallsDetector.addIfNew;

    jp.children.forEach((child) => this.collectLoopInfo(child));

    if (jp.instanceOf("loop") && jp.type.name !== "foreach") {
      if (jp.cond !== undefined) {
        Query.searchFrom(jp.cond, "var", {
          declaration: (d) => d !== undefined,
        })
          .get()
          .forEach((v) =>
            ExcessiveMethodCallsDetector.addIfNew(
              this.loopLocalWrites,
              v.declaration
            )
          );
      }
      return;
    }

    if (jp.instanceOf("localVariable")) {
      addIfNew(this.loopLocalWrites, jp);
      return;
    }

    if (jp.instanceOf("var")) {
      this.analyseVar(jp);
      return;
    }

    if (jp.instanceOf("call")) {
      if (jp.decl === undefined) {
        this.missingCallDecl = true;
        this.printDebugInfo("Failed to get declaration of call: " + jp);
        return;
      }

      let { writes, reads } = this.analyseMethodRecursive(jp.decl);
      writes.forEach((w) => addIfNew(this.loopGlobalWrites, w.declaration));
      reads.forEach((r) => addIfNew(this.loopGlobalReads, r.declaration));
    }
  }

  analyseVar(jpVar) {
    const addIfNew = ExcessiveMethodCallsDetector.addIfNew;

    if (jpVar.declaration === undefined) return;
    let isField = jpVar.isField;
    let access = jpVar.reference.name;
    if (isField && access === "write")
      addIfNew(this.loopGlobalWrites, jpVar.declaration);
    else if (isField && access === "read")
      addIfNew(this.loopGlobalReads, jpVar.declaration);
    else if (isField && access === "readwrite") {
      addIfNew(this.loopGlobalWrites, jpVar.declaration);
      addIfNew(this.loopGlobalReads, jpVar.declaration);
    } else if (!isField && access === "write")
      addIfNew(this.loopLocalWrites, jpVar.declaration);
  }

  analyseMethodRecursive(jpMethod) {
    let res = { writes: [], reads: [] };

    if (
      ExcessiveMethodCallsDetector.tryGetMethodInfo(
        this.methodsInfo,
        jpMethod
      ) !== null
    )
      return res;

    this.printDebugInfo(
      `Analysing method: ${jpMethod} ${
        jpMethod.line !== undefined ? "@ L" + jpMethod.line : ""
      }`
    );

    let {
      missing: m,
      write: w,
      read: r,
    } = this.getFieldUsageInsideJP(jpMethod);

    res.writes.push(...w);
    res.reads.push(...r);

    let calls = Query.searchFrom(jpMethod, "call").get();
    let callDeclSplit = Collections.partition(
      calls,
      (c) => c.decl !== undefined
    );

    let missingInfo = m.length > 0 || callDeclSplit[1].length > 0;

    this.methodsInfo.push({
      method: jpMethod,
      missingInfo: missingInfo,
      writes: w,
      reads: r,
    });

    callDeclSplit[0].forEach((c) => {
      let { writes: wi, reads: ri } = this.analyseMethodRecursive(c.decl);
      res.writes.push(...wi);
      res.reads.push(...ri);
    });

    return res;
  }

  getFieldUsageInsideJP(jp) {
    let vars = Query.searchFrom(jp, "var", { isField: true }).get();
    let declSplit = Collections.partition(
      vars,
      (v) => v.declaration !== undefined
    );

    let read = [];
    let write = [];
    for (let v of declSplit[0]) {
      let accessType = v.reference.name;
      if (accessType === "write") write.push(v);
      else if (accessType === "read") read.push(v);
      else if (accessType === "readwrite") {
        read.push(v);
        write.push(v);
      }
    }

    // let accessSplit = partition(
    //   declSplit[0],
    //   (v) => v.reference.name === "write"
    // );
    return {
      missing: declSplit[1],
      write: write,
      read: read,
    };
  }

  ////////// Second pass //////////

  analyseLoopCall(jp) {
    let childCallsInvariant = true;

    jp.children.forEach(
      (child) => (childCallsInvariant &= this.analyseLoopCall(child))
    );

    if (jp.instanceOf("call")) {
      if (childCallsInvariant && this.isCallInvariant(jp)) {
        this.results.push(jp);
        return true;
      }
      return false;
    }
    return true;
  }

  isCallInvariant(jpCall) {
    if (this.variantCalls.includes(jpCall.toString())) {
      this.printDebugInfo(
        `Call: ${jpCall} @ L${jpCall.line} -> VARIANT (repeatVariantCall)`
      );
      return false;
    }

    if (jpCall.decl === undefined) {
      this.printDebugInfo(
        `Call: ${jpCall} @ L${jpCall.line} -> VARIANT (noCallDecl)`
      );
      this.variantCalls.push(jpCall.toString());
      return false;
    }

    if (
      jpCall.decl.body === undefined ||
      jpCall.decl.body.children.length === 0
    ) {
      this.printDebugInfo(
        `Call: ${jpCall} @ L${jpCall.line} -> VARIANT (emptyDecl)`
      );
      this.variantCalls.push(jpCall.toString());
      return false;
    }

    // let nonPureCalls = ["java.", "org.xmlpull"];
    // let qualifiedReference = jpCall.decl.toQualifiedReference;
    // if (nonPureCalls.some(ref => qualifiedReference.startsWith(ref))) {
    //   this.printDebugInfo(
    //     `Call: ${jpCall} @ L${jpCall.line} -> VARIANT (nonPureCall)`
    //   );
    //   this.variantCalls.push(jpCall.toString());
    //   return false;
    // }

    // if (jpCall.decl.declarator.substring(0, jpCall.decl.declarator.lastIndexOf(".")) !== this.currentPackage) {
    //   this.printDebugInfo(`Call: ${jpCall} @ L${jpCall.line} -> VARIANT (fromDifferentPackage)`);
    //   this.variantCalls.push(jpCall.toString());
    //   return false;
    // }

    // Overlap check of some arguments. unavoidable until ast can differentiate in "a.foo(bar)" -> argument vars (bar) and all other vars (a)
    let vars = this.getFirstDescendentsOfTypes(jpCall, ["var"]);
    for (let v of vars) {
      if (v.declaration === undefined) {
        this.printDebugInfo(
          `Call: ${jpCall} @ L${jpCall.line} -> VARIANT (noVarDecl)`
        );
        this.variantCalls.push(jpCall.toString());
        return false;
      } else if (this.loopLocalWrites.some((w) => w.same(v.declaration))) {
        this.printDebugInfo(
          `Call: ${jpCall} @ L${jpCall.line} -> VARIANT (usesLoopVar)`
        );
        this.variantCalls.push(jpCall.toString());
        return false;
      }
    }

    if (jpCall.descendants.some((d) => d.instanceOf("arrayAccess"))) {
      this.printDebugInfo(
        `Call: ${jpCall} @ L${jpCall.line} -> VARIANT (containsArrayAccess)`
      );
      this.variantCalls.push(jpCall.toString());
      return false;
    }

    let mi = ExcessiveMethodCallsDetector.tryGetMethodInfo(
      this.methodsInfo,
      jpCall.decl
    );

    if (mi !== null && mi.missingInfo) {
      this.printDebugInfo(
        `Call: ${jpCall} @ L${jpCall.line} -> VARIANT (methodMissingInfo)`
      );
      this.variantCalls.push(jpCall.toString());
      return false;
    }

    let mir = mi.reads.map((r) => r.declaration);
    let miw = mi.writes.map((w) => w.declaration);
    let cmr2lw = this.compareFieldUsage(mir, this.loopGlobalWrites);
    let cmw2lr = this.compareFieldUsage(miw, this.loopGlobalReads);
    if (cmr2lw || cmw2lr) {
      this.printDebugInfo(
        `Call: ${jpCall} @ L${jpCall.line} -> VARIANT (variantMethodFields)`
      );
      this.variantCalls.push(jpCall.toString());
      return false;
    }

    let { isVariant, cause } = this.callUsesVariantArgument(jpCall);
    if (isVariant) {
      this.printDebugInfo(
        `Call: ${jpCall} @ L${jpCall.line} -> VARIANT (${cause})`
      );
      this.variantCalls.push(jpCall.toString());
      return false;
    }

    if (this.callInvokesVariantMethod(jpCall.decl)) {
      this.printDebugInfo(
        `Call: ${jpCall} @ L${jpCall.line} -> VARIANT (variantMethodCalls)`
      );
      this.variantCalls.push(jpCall.toString());
      return false;
    }

    if (jpCall.name === "next") {
      println(jpCall.decl.body);
    }

    this.printDebugInfo(`Call: ${jpCall} @ L${jpCall.line} -> INVARIANT`);
    return true;
  }

  compareFieldUsage(arr1, arr2) {
    return arr1.some((r) => arr2.some((w) => w.same(r)));
  }

  callUsesVariantArgument(jpCall) {
    // Gather arguments
    let args = jpCall.children.slice(1);

    let varArgs = args.filter((arg) => arg.instanceOf("var"));
    let callArgs = args.filter((arg) => arg.instanceOf("call"));

    // If any call argument is already tagged as variant, this call is also variant
    if (callArgs.some((c) => this.variantCalls.includes(c.toString())))
      return { isVariant: true, cause: "variantCallAsArgument" };

    // Check variables
    for (let v of varArgs) {
      let vDecl = v.declaration;

      // Unknown field used as argument
      let unknField = vDecl === undefined;
      if (unknField) return { isVariant: true, cause: "noVarDecl" };

      // Read field but unknown call exists in loop
      if (v.isField && this.missingCallDecl)
        return { isVariant: true, cause: "usesField + missingCallDecl" };

      // Reads variable changed by loop
      if (
        !unknField &&
        (ExcessiveMethodCallsDetector.containsJP(
          this.loopGlobalWrites,
          vDecl
        ) ||
          ExcessiveMethodCallsDetector.containsJP(this.loopLocalWrites, vDecl))
      )
        return { isVariant: true, cause: "varChangedInLoop" };
    }

    // Check calls recursively

    return { isVariant: false, cause: "" };
  }

  callInvokesVariantMethod(jp) {
    for (let child of jp.children) {
      let isVariant = this.callInvokesVariantMethod(child);
      if (isVariant) return isVariant;
    }

    if (jp.instanceOf("call")) {
      return !this.isCallInvariant(jp);
    }
  }

  getFirstDescendentsOfTypes(jp, types) {
    if (types.some((type) => jp.instanceOf(type))) {
      return [jp];
    } else {
      let matches = [];
      jp.children.forEach((child) =>
        matches.push(...this.getFirstDescendentsOfTypes(child, types))
      );
      return matches;
    }
  }
}
