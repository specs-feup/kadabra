import Query from "@specs-feup/lara/api/weaver/Query.js";
import Collections from "@specs-feup/lara/api/lara/Collections.js";
import BaseDetector from "./BaseDetector.js";
import { New } from "../../../../Joinpoints.js";
export default class HashMapUsageDetector extends BaseDetector {
    constructor() {
        super("HashMap Usage Detector");
    }
    analyseClass(jpClass) {
        super.analyseClass(jpClass);
        // TODO: refactor to use chain()
        const hashMapRefs = Query.searchFrom(jpClass, New, { type: "HashMap" })
            .get()
            .filter((jp) => jp.typeReference !== undefined &&
            jp.typeReference.packageName === "java.util");
        this.results.push(...hashMapRefs);
    }
    print() {
        console.log(`${this.name}:`);
        const data = this.results.map((r) => [
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
            const jpMethod = r.getAncestor("method");
            if (jpMethod != null) {
                loc = jpMethod.name + loc;
            }
            const jpClass = r.getAncestor("class");
            loc = jpClass.name + "/" + loc;
            const jpFile = jpClass.getAncestor("file");
            loc = jpFile.name + "/" + loc;
            return loc;
        });
    }
}
//# sourceMappingURL=HashMapUsageDetector.js.map