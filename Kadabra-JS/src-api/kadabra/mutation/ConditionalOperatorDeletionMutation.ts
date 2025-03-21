import IterativeMutation from  "@specs-feup/lara/api/lara/mutation/IterativeMutation.js";
import MutationResult from  "@specs-feup/lara/api/lara/mutation/MutationResult.js";
import debug from 'debug';

class ConditionalOperatorDeletionMutation extends IterativeMutation {
    constructor() {
        super("ConditionalOperatorDeletionMutation");
    }

    isMutationPoint($jp: any) {
        if (
            $jp.instanceOf("if") ||
            $jp.instanceOf("ternary") ||
            $jp.instanceOf("loop")
        ) {
            if (
                $jp.cond.instanceOf("unaryExpression") &&
                $jp.cond.operator === "!"
            ) {
                return true;
            }
        }

        return false;
    }

    *mutate($jp: any) {
        const mutation = $jp.copy();

        mutation.cond.insertReplace(mutation.cond.operand.copy());

        debug("/*--------------------------------------*/");
        debug("Mutating operator: " + $jp + " to " + mutation);
        debug("/*--------------------------------------*/");

        yield new MutationResult(mutation);
    }
}
