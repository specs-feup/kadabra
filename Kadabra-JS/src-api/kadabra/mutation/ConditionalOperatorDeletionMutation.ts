import IterativeMutation from "@specs-feup/lara/api/lara/mutation/IterativeMutation.js";
import MutationResult from "@specs-feup/lara/api/lara/mutation/MutationResult.js";
import debug from "debug";
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
        const mutation: Joinpoint = jp.copy();

        if (
            mutation instanceof If ||
            mutation instanceof Ternary ||
            mutation instanceof Loop
        ) {
            if (
                mutation.cond instanceof UnaryExpression &&
                mutation.cond.operator === "!"
            ) {
                mutation.cond.insertReplace(mutation.cond.operand.copy());
            }
        }

        debug("/*--------------------------------------*/");
        debug("Mutating operator: " + jp + " to " + mutation);
        debug("/*--------------------------------------*/");

        yield new MutationResult(mutation);
    }
}
