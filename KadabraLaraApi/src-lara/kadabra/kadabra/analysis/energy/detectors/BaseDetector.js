import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Class } from "../../../../Joinpoints.js";
export default class BaseDetector {
    name;
    results;
    debugEnabled;
    constructor(name, debugEnabled = false) {
        this.name = name;
        this.results = [];
        this.debugEnabled = debugEnabled;
    }
    analyse() {
        console.log(`Running ${this.name}...`);
        const classes = Query.search(Class, {
            isTopLevel: true,
        });
        for (const c of classes) {
            this.analyseClass(c);
        }
        return this;
    }
    analyseClass(jpClass) {
        jpClass.insert("before", "foo");
        if (!jpClass ||
            !("instanceOf" in jpClass) ||
            !(jpClass instanceof Class)) {
            console.log("Argument is not a joinpoint of type 'class'");
        }
    }
    print() {
        throw new Error(`Method 'print()' was not implemented for detector '${this.name}'`);
    }
    save() {
        throw new Error(`Method 'save()' was not implemented for detector '${this.name}'`);
    }
}
//# sourceMappingURL=BaseDetector.js.map