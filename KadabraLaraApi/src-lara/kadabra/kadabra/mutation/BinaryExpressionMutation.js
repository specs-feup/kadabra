import IterativeMutation from "@specs-feup/lara/api/lara/mutation/IterativeMutation.js";
import MutationResult from "@specs-feup/lara/api/lara/mutation/MutationResult.js";
import { arrayFromArgs } from "@specs-feup/lara/api/lara/core/LaraCore.js";
/**
 *  @param {string[] | string...} newOperators - Operators that will be used to mutate the binaryExpression.
 */
export default class BinaryExpressionMutation extends IterativeMutation {
    newOperators;
    // Parent constructor
    constructor(...newOperators) {
        super("BinaryExpressionMutation");
        // TODO: Check if operators are valid
        this.newOperators = arrayFromArgs(newOperators);
    }
    isMutationPoint(jp) {
        return jp.operands !== undefined;
    }
    *mutate(jp) {
        for (const newOp of this.newOperators) {
            const mutation = jp.copy();
            // Skip when operators are the same
            if (this.isMutationPoint(mutation)) {
                if (mutation.operator === newOp)
                    continue;
                mutation.operator = newOp;
            }
            yield new MutationResult(mutation);
        }
    }
}
//# sourceMappingURL=BinaryExpressionMutation.js.map