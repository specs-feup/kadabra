import IterativeMutation from "@specs-feup/lara/api/lara/mutation/IterativeMutation.js";
import MutationResult from "@specs-feup/lara/api/lara/mutation/MutationResult.js";
import { arrayFromArgs } from "@specs-feup/lara/api/lara/core/LaraCore.js";
import { BinaryExpression } from "../../Joinpoints.js";
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
        return jp instanceof BinaryExpression;
    }
    *mutate(jp) {
        for (const newOp of this.newOperators) {
            // Skip when operators are the same
            if (jp instanceof BinaryExpression) {
                if (jp.operator === newOp) {
                    continue;
                }
            }
            const mutation = jp.copy();
            if (mutation instanceof BinaryExpression) {
                mutation.operator = newOp;
            }
            yield new MutationResult(mutation);
        }
    }
}
//# sourceMappingURL=BinaryExpressionMutation.js.map