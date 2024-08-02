laraImport("lara.mutation.IterativeMutation");
laraImport("lara.mutation.MutationResult");

class ArithmeticOperatorDeletionMutation extends IterativeMutation {
    constructor() {
        super("ArithmeticOperatorDeletionMutation");
    }

    isMutationPoint($jp) {
        return $jp.instanceOf("binaryExpression");
    }

    *mutate($jp) {
        const leftOperand = $jp.lhs.copy();

        debug("/*--------------------------------------*/");
        debug("Mutating operator: " + $jp + " to " + leftOperand);
        debug("/*--------------------------------------*/");

        yield new MutationResult(leftOperand);

        const rightOperand = $jp.rhs.copy();

        debug("/*--------------------------------------*/");
        debug("Mutating operator: " + $jp + " to " + rightOperand);
        debug("/*--------------------------------------*/");

        yield new MutationResult(rightOperand);
    }
}
