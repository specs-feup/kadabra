import Query from "@specs-feup/lara/api/weaver/Query.js";
import Collections from "@specs-feup/lara/api/lara/Collections.js";
import BaseDetector from "./BaseDetector.js";
import { Loop, Var, Call, RefType, LoopType, LocalVariable, ArrayAccess } from "../../../../Joinpoints.js";
export default class ExcessiveMethodCallsDetector extends BaseDetector {
    currentPackage;
    loopGlobalReads;
    loopGlobalWrites;
    loopLocalWrites;
    methodsInfo;
    variantCalls;
    missingCallDecl;
    constructor(debugEnabled = false) {
        super("Excessive Method Calls Detector", debugEnabled);
    }
    static containsJP(arr, jp) {
        return arr.some((i) => i.same(jp));
    }
    static tryGetMethodInfo(arr, jp) {
        for (const j of arr) {
            if (j.method.same(jp))
                return j;
        }
        return null;
    }
    static addIfNew(arr, jp) {
        if (!ExcessiveMethodCallsDetector.containsJP(arr, jp))
            arr.push(jp);
    }
    analyseClass(jpClass) {
        super.analyseClass(jpClass);
        this.currentPackage = jpClass.packageName;
        const fileName = jpClass.getAncestor("file");
        const loops = Query.searchFrom(jpClass, Loop).get();
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
            console.log("Global Reads: ");
            this.loopGlobalReads.forEach((lgr) => console.log(lgr.name + ", "));
            console.log("\nGlobal Writes: ");
            this.loopGlobalWrites.forEach((lgw) => console.log(lgw.name + ", "));
            console.log("\nLocal Writes: ");
            this.loopLocalWrites.forEach((llw) => console.log(llw.name + ", "));
            console.log("\n");
        }
        // Second pass to analyse loop calls against collected loop info
        this.printDebugInfo("Analyse Loop Calls:");
        const calls = this.getFirstDescendentsOfTypes(jpLoop, ["call"]);
        calls.forEach((c) => this.analyseLoopCall(c));
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
    resetDetector() {
        this.loopGlobalReads = [];
        this.loopLocalWrites = [];
        this.loopGlobalWrites = [];
        this.methodsInfo = [];
        this.variantCalls = [];
        this.missingCallDecl = false;
    }
    printDebugInfo(msg) {
        if (!this.debugEnabled)
            return;
        console.log(msg);
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
    ////////// First pass //////////
    collectLoopInfo(jp) {
        const addIfNew = ExcessiveMethodCallsDetector.addIfNew;
        jp.children.forEach((child) => this.collectLoopInfo(child));
        if (jp instanceof Loop && jp.type !== LoopType.FOREACH) {
            if (jp.cond !== undefined) {
                Query.searchFrom(jp.cond, Var, {
                    declaration: (d) => d !== undefined,
                })
                    .get()
                    .forEach((v) => addIfNew(this.loopLocalWrites, v.declaration));
            }
        }
        else if (jp instanceof LocalVariable) {
            addIfNew(this.loopLocalWrites, jp);
        }
        else if (jp instanceof Var) {
            this.analyseVar(jp);
        }
        else if (jp instanceof Call) {
            if (jp.decl === undefined) {
                this.missingCallDecl = true;
                this.printDebugInfo("Failed to get declaration of call: " + jp);
                return;
            }
            const { writes, reads } = this.analyseMethodRecursive(jp.decl);
            writes.forEach((w) => addIfNew(this.loopGlobalWrites, w.declaration));
            reads.forEach((r) => addIfNew(this.loopGlobalReads, r.declaration));
        }
    }
    analyseVar(jpVar) {
        const addIfNew = ExcessiveMethodCallsDetector.addIfNew;
        if (jpVar.declaration === undefined)
            return;
        const isField = jpVar.isField;
        const access = jpVar.reference;
        if (isField && access === RefType.WRITE)
            addIfNew(this.loopGlobalWrites, jpVar.declaration);
        else if (isField && access === RefType.READ)
            addIfNew(this.loopGlobalReads, jpVar.declaration);
        else if (isField && access === RefType.READWRITE) {
            addIfNew(this.loopGlobalWrites, jpVar.declaration);
            addIfNew(this.loopGlobalReads, jpVar.declaration);
        }
        else if (!isField && access === RefType.WRITE)
            addIfNew(this.loopLocalWrites, jpVar.declaration);
    }
    analyseMethodRecursive(jpMethod) {
        const res = { writes: [], reads: [] };
        if (ExcessiveMethodCallsDetector.tryGetMethodInfo(this.methodsInfo, jpMethod) !== null)
            return res;
        this.printDebugInfo(`Analysing method: ${jpMethod} ${jpMethod.line !== undefined ? "@ L" + jpMethod.line : ""}`);
        const { missing: m, write: w, read: r, } = this.getFieldUsageInsideJP(jpMethod);
        res.writes.push(...w);
        res.reads.push(...r);
        const calls = Query.searchFrom(jpMethod, Call).get();
        const callDeclSplit = Collections.partition(calls, (c) => c.decl !== undefined);
        const missingInfo = m.length > 0 || callDeclSplit[1].length > 0;
        this.methodsInfo.push({
            method: jpMethod,
            missingInfo: missingInfo,
            writes: w,
            reads: r,
        });
        callDeclSplit[0].forEach((c) => {
            const { writes: wi, reads: ri } = this.analyseMethodRecursive(c.decl);
            res.writes.push(...wi);
            res.reads.push(...ri);
        });
        return res;
    }
    getFieldUsageInsideJP(jp) {
        const vars = Query.searchFrom(jp, Var, { isField: true }).get();
        const declSplit = Collections.partition(vars, (v) => v.declaration !== undefined);
        const read = [];
        const write = [];
        for (const v of declSplit[0]) {
            const accessType = v.reference;
            if (accessType === RefType.WRITE)
                write.push(v);
            else if (accessType === RefType.READ)
                read.push(v);
            else if (accessType === RefType.READWRITE) {
                read.push(v);
                write.push(v);
            }
        }
        return {
            missing: declSplit[1],
            write: write,
            read: read,
        };
    }
    ////////// Second pass //////////
    analyseLoopCall(jp) {
        let childCallsInvariant = true;
        jp.children.forEach((child) => (childCallsInvariant &&= this.analyseLoopCall(child)));
        if (jp instanceof Call) {
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
            this.printDebugInfo(`Call: ${jpCall} @ L${jpCall.line} -> VARIANT (repeatVariantCall)`);
            return false;
        }
        if (jpCall.decl === undefined) {
            this.printDebugInfo(`Call: ${jpCall} @ L${jpCall.line} -> VARIANT (noCallDecl)`);
            this.variantCalls.push(jpCall.toString());
            return false;
        }
        if (jpCall.decl.body === undefined ||
            jpCall.decl.body.children.length === 0) {
            this.printDebugInfo(`Call: ${jpCall} @ L${jpCall.line} -> VARIANT (emptyDecl)`);
            this.variantCalls.push(jpCall.toString());
            return false;
        }
        // Overlap check of some arguments. unavoidable until ast can differentiate in "a.foo(bar)" -> argument vars (bar) and all other vars (a)
        const vars = this.getFirstDescendentsOfTypes(jpCall, ["var"]);
        for (const v of vars) {
            if (v.declaration === undefined) {
                this.printDebugInfo(`Call: ${jpCall} @ L${jpCall.line} -> VARIANT (noVarDecl)`);
                this.variantCalls.push(jpCall.toString());
                return false;
            }
            else if (this.loopLocalWrites.some((w) => w.same(v.declaration))) {
                this.printDebugInfo(`Call: ${jpCall} @ L${jpCall.line} -> VARIANT (usesLoopVar)`);
                this.variantCalls.push(jpCall.toString());
                return false;
            }
        }
        if (jpCall.descendants.some((d) => d instanceof ArrayAccess)) {
            this.printDebugInfo(`Call: ${jpCall} @ L${jpCall.line} -> VARIANT (containsArrayAccess)`);
            this.variantCalls.push(jpCall.toString());
            return false;
        }
        const mi = ExcessiveMethodCallsDetector.tryGetMethodInfo(this.methodsInfo, jpCall.decl);
        if (mi !== null && mi.missingInfo) {
            this.printDebugInfo(`Call: ${jpCall} @ L${jpCall.line} -> VARIANT (methodMissingInfo)`);
            this.variantCalls.push(jpCall.toString());
            return false;
        }
        const mir = mi.reads.map((r) => r.declaration);
        const miw = mi.writes.map((w) => w.declaration);
        const cmr2lw = this.compareFieldUsage(mir, this.loopGlobalWrites);
        const cmw2lr = this.compareFieldUsage(miw, this.loopGlobalReads);
        if (cmr2lw || cmw2lr) {
            this.printDebugInfo(`Call: ${jpCall} @ L${jpCall.line} -> VARIANT (variantMethodFields)`);
            this.variantCalls.push(jpCall.toString());
            return false;
        }
        const { isVariant, cause } = this.callUsesVariantArgument(jpCall);
        if (isVariant) {
            this.printDebugInfo(`Call: ${jpCall} @ L${jpCall.line} -> VARIANT (${cause})`);
            this.variantCalls.push(jpCall.toString());
            return false;
        }
        if (this.callInvokesVariantMethod(jpCall.decl)) {
            this.printDebugInfo(`Call: ${jpCall} @ L${jpCall.line} -> VARIANT (variantMethodCalls)`);
            this.variantCalls.push(jpCall.toString());
            return false;
        }
        if (jpCall.name === "next") {
            console.log(jpCall.decl.body);
        }
        this.printDebugInfo(`Call: ${jpCall} @ L${jpCall.line} -> INVARIANT`);
        return true;
    }
    compareFieldUsage(arr1, arr2) {
        return arr1.some((r) => arr2.some((w) => w.same(r)));
    }
    callUsesVariantArgument(jpCall) {
        // Gather arguments
        const args = jpCall.children.slice(1);
        const varArgs = args.filter((arg) => arg instanceof Var);
        const callArgs = args.filter((arg) => arg instanceof Call);
        // If any call argument is already tagged as variant, this call is also variant
        if (callArgs.some((c) => this.variantCalls.includes(c.toString())))
            return { isVariant: true, cause: "variantCallAsArgument" };
        // Check variables
        for (const v of varArgs) {
            const vDecl = v.declaration;
            // Unknown field used as argument
            const unknField = vDecl === undefined;
            if (unknField)
                return { isVariant: true, cause: "noVarDecl" };
            // Read field but unknown call exists in loop
            if (v.isField && this.missingCallDecl)
                return { isVariant: true, cause: "usesField + missingCallDecl" };
            // Reads variable changed by loop
            if (!unknField &&
                (ExcessiveMethodCallsDetector.containsJP(this.loopGlobalWrites, vDecl) ||
                    ExcessiveMethodCallsDetector.containsJP(this.loopLocalWrites, vDecl)))
                return { isVariant: true, cause: "varChangedInLoop" };
        }
        // Check calls recursively
        return { isVariant: false, cause: "" };
    }
    callInvokesVariantMethod(jp) {
        for (const child of jp.children) {
            const isVariant = this.callInvokesVariantMethod(child);
            if (isVariant)
                return isVariant;
        }
        if (jp instanceof Call) {
            return !this.isCallInvariant(jp);
        }
    }
    getFirstDescendentsOfTypes(jp, types) {
        if (types.some((type) => jp.instanceOf(type))) {
            return [jp];
        }
        else {
            const matches = [];
            jp.children.forEach((child) => matches.push(...this.getFirstDescendentsOfTypes(child, types)));
            return matches;
        }
    }
}
//# sourceMappingURL=ExcessiveMethodCallsDetector.js.map