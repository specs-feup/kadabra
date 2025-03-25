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

    isMutationPoint(jp: Joinpoint) {
        if (jp instanceof If || jp instanceof Ternary || jp instanceof Loop) {
            if (
                jp.cond instanceof UnaryExpression &&
                jp.cond.operator === "!"
            ) {
                return true;
            }
        }

        return false;
    }

    *mutate(jp: Joinpoint) {
        //Joinpoint
        const mutation = jp.copy() as If | Ternary | Loop;

        if (this.isMutationPoint(jp)) {
            const tempCond = mutation.cond.copy() as UnaryExpression;
            mutation.cond.insertReplace(tempCond.operand.copy());
        }

        debug("/*--------------------------------------*/");
        debug("Mutating operator: " + jp + " to " + mutation);
        debug("/*--------------------------------------*/");

        yield new MutationResult(mutation);
    }
}
