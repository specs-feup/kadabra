laraImport("lara.mutation.IterativeMutation");
laraImport("lara.mutation.MutationResult");

class ConditionalOperatorDeletionMutation extends IterativeMutation {
    constructor() {
        super("ConditionalOperatorDeletionMutation");
    }

    isMutationPoint($jp) {
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

    *mutate($jp) {
        const mutation = $jp.copy();

        mutation.cond.insertReplace(mutation.cond.operand.copy());

        debug("/*--------------------------------------*/");
        debug("Mutating operator: " + $jp + " to " + mutation);
        debug("/*--------------------------------------*/");

        yield new MutationResult(mutation);
    }
}
