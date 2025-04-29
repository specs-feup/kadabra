import IterativeMutation from "@specs-feup/lara/api/lara/mutation/IterativeMutation.js";
import MutationResult from "@specs-feup/lara/api/lara/mutation/MutationResult.js";
import { Joinpoint, BinaryExpression } from "../../Joinpoints.js";

/**
 *  @param {string[] | string...} newOperators - Operators that will be used to mutate the binaryExpression.
 */

export default class BinaryExpressionMutation extends IterativeMutation {
    newOperators: string[];

    // Parent constructor
    constructor(...newOperators: string[]) {
        super("BinaryExpressionMutation");
        // TODO: Check if operators are valid
        this.newOperators = newOperators;
    }

    isMutationPoint(jp: Joinpoint): jp is BinaryExpression {
        return jp instanceof BinaryExpression;
    }

    *mutate(jp: BinaryExpression) {
        for (const newOp of this.newOperators) {
            const mutation = jp.copy() as BinaryExpression;

            // Skip when operators are the same
            if (mutation.operator === newOp) continue;

            mutation.operator = newOp;

            yield new MutationResult(mutation);
        }
    }
}
