import Query from "@specs-feup/lara/api/weaver/Query.js";
import Collections from "@specs-feup/lara/api/lara/Collections.js";
import BaseDetector from "./BaseDetector.js";
import { Class, FileJp, Method, New } from "../../../../Joinpoints.js";

export default class HashMapUsageDetector extends BaseDetector {
    constructor() {
        super("HashMap Usage Detector");
    }

    analyseClass(jpClass: Class) {
        super.analyseClass(jpClass);

        const hashMapRefs = Query.searchFrom(jpClass, New, {
            type: "HashMap",
            typeReference: (jp) => jp.packageName === "java.util",
        }).get();

        this.results.push(...hashMapRefs);
    }

    print() {
        console.log(`${this.name}:`);
        const data = this.results.map((r) => [
            r.line.toString(),
            (r.getAncestor("file") as FileJp).path,
        ]);
        Collections.printTable(["Line", "File"], data, [40, 100]);
        console.log();
    }

    save() {
        return this.results.map((r) => {
            let loc = ":" + r.line.toString();

            // Initialized inside method
            const jpMethod = r.getAncestor("method") as Method;
            if (jpMethod != null) {
                loc = jpMethod.name + loc;
            }
            const jpClass = r.getAncestor("class") as Class;
            loc = jpClass.name + "/" + loc;
            const jpFile = jpClass.getAncestor("file") as FileJp;
            loc = jpFile.name + "/" + loc;
            return loc;
        });
    }
}
