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

    isMutationPoint(jp: Joinpoint): boolean {
        if (
            this.isOfCorrectType(jp) &&
            jp.cond instanceof UnaryExpression &&
            jp.cond.operator === "!"
        ) {
            return true;
        }

        return false;
    }

    isOfCorrectType(jp: Joinpoint) {
        return jp instanceof If || jp instanceof Ternary || jp instanceof Loop;
    }

    *mutate(jp: If | Ternary | Loop) {
        if (!this.isMutationPoint(jp)) {
            throw new Error("Argument is not a mutation point.");
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
