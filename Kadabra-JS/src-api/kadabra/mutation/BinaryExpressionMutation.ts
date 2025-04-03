import IterativeMutation from "@specs-feup/lara/api/lara/mutation/IterativeMutation.js";
import MutationResult from "@specs-feup/lara/api/lara/mutation/MutationResult.js";
import { arrayFromArgs } from "@specs-feup/lara/api/lara/core/LaraCore.js";
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
        this.newOperators = arrayFromArgs(newOperators);
    }

    isMutationPoint(jp: Joinpoint | BinaryExpression): jp is BinaryExpression {
        return (jp as BinaryExpression).operator !== undefined;
    }

    *mutate(jp: Joinpoint) {
        for (const newOp of this.newOperators) {
            const mutation = jp.copy();

            // Skip when operators are the same
            if (this.isMutationPoint(mutation)) {
                if (mutation.operator === newOp) continue;

                mutation.operator = newOp;
            }

            yield new MutationResult(mutation);
        }
    }
}
