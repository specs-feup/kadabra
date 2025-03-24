import Mutator from "@specs-feup/lara/api/lara/mutation/Mutator.js";
import { arrayFromArgs } from "@specs-feup/lara/api/lara/core/LaraCore.js";
import { Joinpoint, BinaryExpression } from "../../Joinpoints.js";

/**
 *  @param {BinaryExpression} binaryExpression - A join point of type binaryExpression.
 *  @param {String[] | String...} newOperators - Operators that will be used to mutate the given binaryExpression.
 */
export default abstract class BinaryExpressionMutator extends Mutator {
    binaryExpression: Joinpoint;
    newOperators: string[];
    currentIndex: number;
    previousOp: string;

    constructor(binaryExpression: Joinpoint, ...newOperators: Joinpoint[]) {
        super();
        // Instance variables
        this.binaryExpression = binaryExpression;
        this.newOperators = arrayFromArgs(newOperators);
        this.currentIndex = 0;
        this.previousOp = undefined;

        // Checks

        // Check it is a binaryExpression
        if (!(binaryExpression instanceof BinaryExpression)) {
            throw new Error(
                "Expected a binaryExpression, received a " // +
                //    binaryExpression.type.name
            );
        }

        // TODO: Check if operators are valid
    }

    hasMutations() {
        return this.currentIndex < this.newOperators.length;
    }

    getMutationPoint() {
        return this.binaryExpression;
    }

    mutatePrivate() {
        // Obtain new operator, increment index
        const newOp = this.newOperators[this.currentIndex];
        this.currentIndex++;

        if (this.binaryExpression instanceof BinaryExpression) {
            // Store current operator
            this.previousOp = this.binaryExpression.operator;

            // Set new operator
            this.binaryExpression.operator = newOp;
        }
    }

    restorePrivate() {
        if (this.binaryExpression instanceof BinaryExpression) {
            // Restore operator
            this.binaryExpression.operator = this.previousOp;
            this.previousOp = undefined;
        }
    }
}
