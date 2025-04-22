import IterativeMutation from "@specs-feup/lara/api/lara/mutation/IterativeMutation.js";
import MutationResult from "@specs-feup/lara/api/lara/mutation/MutationResult.js";
import { debug } from "@specs-feup/lara/api/lara/core/LaraCore.js";
import {
    Loop,
    Joinpoint,
    Ternary,
    If,
    UnaryExpression,
} from "../../Joinpoints.js";

export default class ConditionalOperatorDeletionMutation extends IterativeMutation {
    constructor() {
        super("ConditionalOperatorDeletionMutation");
    }

    isMutationPoint(jp: If | Ternary | Loop): boolean {
        if (jp.cond instanceof UnaryExpression && jp.cond.operator === "!") {
            return true;
        }

        return false;
    }

    isOfCorrectType(jp: Joinpoint) {
        return jp instanceof If || jp instanceof Ternary || jp instanceof Loop;
    }

    *mutate(jp: Joinpoint) {
        //Joinpoint

        if (!(this.isOfCorrectType(jp) && this.isMutationPoint(jp))) {
            yield new MutationResult(jp);
        }
        const mutation = jp.copy() as If | Ternary | Loop;
        const tempCond = mutation.cond.copy() as UnaryExpression;
        mutation.cond.insertReplace(tempCond.operand.copy());

        debug("/*--------------------------------------*/");
        debug("Mutating operator: " + jp + " to " + mutation);
        debug("/*--------------------------------------*/");

        yield new MutationResult(mutation);
    }
}
