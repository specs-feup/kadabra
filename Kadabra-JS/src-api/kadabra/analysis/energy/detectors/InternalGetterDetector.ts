import Query from "@specs-feup/lara/api/weaver/Query.js";
import Collections from "@specs-feup/lara/api/lara/Collections.js";
import BaseDetector from "./BaseDetector.js";
import { Body, Call, Class, Method, Return, Var } from "../../../../Joinpoints.js";

export default class InternalGetterDetector extends BaseDetector {
  constructor() {
    super("Internal Getter Detector");
  }

  analyseClass(jpClass: Class) {
    super.analyseClass(jpClass);

    const notVoidFilter = (rr: string) => rr !== "void";

    let simpleGetters = Query.childrenFrom(jpClass, Method, {
      returnType: notVoidFilter,
      isStatic: false,
    })
      .children(Body, { numChildren: 1 })
      .children(Return)
      .children(Var, { isField: true })
      .chain()
      .map((m) => m["method"]);

    let internalCalls = Query.searchFrom(jpClass, Call, {
      decl: (d) => d !== undefined && simpleGetters.some((sg: any) => sg.same(d)),
    }).get();

    this.results.push(...internalCalls);
  }

  print() {
    console.log(`${this.name}:`);
    let data = this.results.map((r) => [
      r.line.toString(),
      r.name,
      r.getAncestor("file").path,
    ]);
    Collections.printTable(["Line", "Call", "File"], data, [10, 30, 100]);
    console.log();
  }

  save() {
    return this.results.map((r) => {
      let loc = r.name + "(" + r.arguments + "):" + r.line.toString();

      // Initialized inside method
      let node = r.getAncestor("method");
      if (node !== undefined) {
        loc = node.name + "/" + loc;
      }
      else {
        node = r.getAncestor("constructor");
        loc = node.name + "/" + loc;
      }
      node = r.getAncestor("class");
      loc = node.name + "/" + loc;
      node = node.getAncestor("file");
      loc = node.name.toString() + "/" + loc;
      return loc;
    });
  }
}
