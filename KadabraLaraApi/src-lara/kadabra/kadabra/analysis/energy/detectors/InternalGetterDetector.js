import Query from "@specs-feup/lara/api/weaver/Query.js";
import Collections from "@specs-feup/lara/api/lara/Collections.js";
import BaseDetector from "./BaseDetector.js";
import { Body, Call, Method, Return, Var, } from "../../../../Joinpoints.js";
export default class InternalGetterDetector extends BaseDetector {
    constructor() {
        super("Internal Getter Detector");
    }
    analyseClass(jpClass) {
        super.analyseClass(jpClass);
        const notVoidFilter = (rr) => rr !== "void";
        const simpleGetters = Query.childrenFrom(jpClass, Method, {
            returnType: notVoidFilter,
            isStatic: false,
        })
            .children(Body, { numChildren: 1 })
            .children(Return)
            .children(Var, { isField: true })
            .chain()
            .map((m) => m["method"]);
        const internalCalls = Query.searchFrom(jpClass, Call, {
            decl: (d) => d !== undefined && simpleGetters.some((sg) => sg.equals(d)),
        }).get();
        this.results.push(...internalCalls);
    }
    print() {
        console.log(`${this.name}:`);
        const data = this.results.map((r) => [
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
            const node = r.getAncestor("method");
            if (node !== undefined) {
                loc = node.name + "/" + loc;
            }
            else {
                const jpConstruct = r.getAncestor("constructor");
                loc = jpConstruct.name + "/" + loc;
            }
            const jpClass = r.getAncestor("class");
            loc = jpClass.name + "/" + loc;
            const jpFile = jpClass.getAncestor("file");
            loc = jpFile.name + "/" + loc;
            return loc;
        });
    }
}
//# sourceMappingURL=InternalGetterDetector.js.map