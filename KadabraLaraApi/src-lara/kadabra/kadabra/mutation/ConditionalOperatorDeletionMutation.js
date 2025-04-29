import IterativeMutation from "@specs-feup/lara/api/lara/mutation/IterativeMutation.js";
import MutationResult from "@specs-feup/lara/api/lara/mutation/MutationResult.js";
import { debug } from "@specs-feup/lara/api/lara/core/LaraCore.js";
import { Loop, Ternary, If, UnaryExpression, } from "../../Joinpoints.js";
export default class ConditionalOperatorDeletionMutation extends IterativeMutation {
    constructor() {
        super("ConditionalOperatorDeletionMutation");
    }
    isMutationPoint(jp) {
        if (this.isOfCorrectType(jp) &&
            jp.cond instanceof UnaryExpression &&
            jp.cond.operator === "!") {
            return true;
        }
        return false;
    }
    isOfCorrectType(jp) {
        return jp instanceof If || jp instanceof Ternary || jp instanceof Loop;
    }
    *mutate(jp) {
        //Joinpoint
        if (!this.isMutationPoint(jp)) {
            yield new MutationResult(jp);
        }
        const mutation = jp.copy();
        const tempCond = mutation.cond.copy();
        mutation.cond.insertReplace(tempCond.operand.copy());
        debug("/*--------------------------------------*/");
        debug("Mutating operator: " + jp + " to " + mutation);
        debug("/*--------------------------------------*/");
        yield new MutationResult(mutation);
    }
}
//# sourceMappingURL=ConditionalOperatorDeletionMutation.js.map