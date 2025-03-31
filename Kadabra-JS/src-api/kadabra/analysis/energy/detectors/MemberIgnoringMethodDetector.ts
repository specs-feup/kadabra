import Query from "@specs-feup/lara/api/weaver/Query.js";
import Collections from "@specs-feup/lara/api/lara/Collections.js";
import BaseDetector from "./BaseDetector.js";
import {
    Annotation,
    Call,
    Class,
    FileJp,
    Joinpoint,
    Method,
    Reference,
    Var,
} from "../../../../Joinpoints.js";

export default class MemberIgnoringMethodDetector extends BaseDetector {
    dups: Map<string, Method[]>;

    constructor() {
        super("Member Ignoring Method Detector");

        this.dups = new Map();
        this.computeSameNameMethods();
    }

    static noOverrideAnnotationFilter(annos: Annotation[]) {
        return annos.filter((a) => a.type === "Override").length === 0;
    }

    computeSameNameMethods() {
        const methods = Query.search(Method, {
            isStatic: false,
            isFinal: false,
            privacy: (p) => p !== "private",
            annotations:
                MemberIgnoringMethodDetector.noOverrideAnnotationFilter,
        });

        for (const m of methods) {
            const dup = this.dups.get(m.name);
            if (dup !== undefined) {
                dup.push(m);
            } else {
                this.dups.set(m.name, [m]);
            }
        }
    }

    analyseClass(jpClass: Class) {
        super.analyseClass(jpClass);

        const checkOverride = !(
            jpClass.interfaces.length == 0 && jpClass.superClassJp == undefined
        );

        const mightBeStatic = Query.childrenFrom(jpClass, Method, {
            isStatic: false,
            isFinal: false,
            privacy: (p) => p !== "private",
            annotations:
                MemberIgnoringMethodDetector.noOverrideAnnotationFilter,
        });

        for (const m of mightBeStatic) {
            if (m.body === undefined || m.body.children.length === 0) continue;

            if (!this.#methodCanBeStatic(m)) continue;

            if (checkOverride && this.#isOverride(m)) continue;

            this.results.push(m);
        }
    }

    print() {
        console.log(`${this.name}:`);
        const data = this.results.map((r) => [
            r.line.toString(),
            r.name,
            (r.getAncestor("file") as FileJp).path,
        ]);
        Collections.printTable(["Line", "Method", "File"], data, [10, 30, 100]);
        console.log();
    }

    save() {
        return this.results.map((r) => {
            const node = r;
            let loc = node.name;
            const jpClass = node.getAncestor("class") as Class;
            loc = jpClass.name + "/" + loc;
            const jpFile = jpClass.getAncestor("file") as FileJp;
            loc = jpFile.name + "/" + loc;
            return loc + ":" + r.line.toString();
        });
    }

    #isOverride(jpMethod: Method) {
        const dups = this.dups.get(jpMethod.name);
        if (dups === undefined || dups.length < 2) return false;

        for (const dup of dups) {
            if (dup.declarator === jpMethod.declarator) continue;

            if (dup.isOverriding(jpMethod) || jpMethod.isOverriding(dup)) {
                return true;
            }
        }
        return false;
    }

    #methodCanBeStatic(jp: Joinpoint) {
        if (jp instanceof Call && !this.#callIsStatic(jp)) {
            return false;
        }

        if (jp instanceof Var && !this.#varIsStatic(jp)) {
            return false;
        }

        for (const child of jp.children) {
            if (!this.#methodCanBeStatic(child)) return false;
        }

        return true;
    }

    #callIsStatic(jpCall: Call) {
        return jpCall.decl?.isStatic;
    }

    #varIsStatic(jpVar: Var) {
        const isParameter =
            Query.childrenFrom(jpVar, Reference, { type: "Parameter" }).get()
                .length > 0;
        return isParameter ? true : jpVar.isStatic;
    }
}
