laraImport("lara.mutation.IterativeMutation");
laraImport("lara.mutation.MutationResult");

/**
 *  @param {String[] | String...} newOperators - Operators that will be used to mutate the binaryExpression.
 */
class BinaryExpressionMutation extends IterativeMutation {
    // Parent constructor
    constructor(...newOperators) {
        super("BinaryExpressionMutation");
        // TODO: Check if operators are valid
        this.newOperators = arrayFromArgs(newOperators);
    }

    isMutationPoint($jp) {
        return $jp.instanceOf("binaryExpression");
    }

    *mutate($jp) {
        for (const newOp of this.newOperators) {
            // Skip when operators are the same
            if ($jp.operator === newOp) {
                continue;
            }

            const mutation = $jp.copy();
            mutation.operator = newOp;
            yield new MutationResult(mutation);
        }
    }
}
