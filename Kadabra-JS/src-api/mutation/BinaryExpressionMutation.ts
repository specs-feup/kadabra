import IterativeMutation from  "@specs-feup/lara/api/lara/mutation/IterativeMutation.js";
import MutationResult from  "@specs-feup/lara/api/lara/mutation/MutationResult.js";
import { arrayFromArgs } from "@specs-feup/lara/api/lara/core/LaraCore.js"

/**
 *  @param {String[] | String...} newOperators - Operators that will be used to mutate the binaryExpression.
 */
class BinaryExpressionMutation extends IterativeMutation {
    
    newOperators: any[];

    // Parent constructor
    constructor(...newOperators: any[]) {
        super("BinaryExpressionMutation");
        // TODO: Check if operators are valid
        this.newOperators = arrayFromArgs(newOperators);
    }

    isMutationPoint($jp: any) {
        return $jp.instanceOf("binaryExpression");
    }

    *mutate($jp: any) {
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
