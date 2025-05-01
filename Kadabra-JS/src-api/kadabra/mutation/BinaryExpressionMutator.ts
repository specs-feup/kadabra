import Mutator from "@specs-feup/lara/api/lara/mutation/Mutator.js";
import { BinaryExpression } from "../../Joinpoints.js";

export default abstract class BinaryExpressionMutator extends Mutator {
    binaryExpression: BinaryExpression;
    newOperators: string[];
    currentIndex: number;
    previousOp?: string;

    constructor(binaryExpression: BinaryExpression, ...newOperators: string[]) {
        super();
        // Instance variables
        this.binaryExpression = binaryExpression;
        this.newOperators = newOperators;
        this.currentIndex = 0;
        this.previousOp = undefined;

        // Checks
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

        // Store current operator
        this.previousOp = this.binaryExpression.operator;

        // Set new operator
        this.binaryExpression.operator = newOp;
    }

    restorePrivate() {
        // Restore operator
        if (this.previousOp !== undefined)
            this.binaryExpression.operator = this.previousOp;
        this.previousOp = undefined;
    }
}
