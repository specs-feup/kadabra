laraImport("lara.mutation.Mutator");

/**
 *  @param {$binaryExpression} $binaryExpression - A join point of type binaryExpression.
 *  @param {String[] | String...} newOperators - Operators that will be used to mutate the given binaryExpression.
 */
class BinaryExpressionMutator extends Mutator {
    constructor($binaryExpression, ...newOperators) {
        super();
        // Instance variables
        this.$binaryExpression = $binaryExpression;
        this.newOperators = arrayFromArgs(newOperators);
        this.currentIndex = 0;
        this.previousOp = undefined;

        // Checks

        // Check it is a binaryExpression
        if (!$binaryExpression.instanceOf("binaryExpression")) {
            throw new Error(
                "Expected a binaryExpression, received a " +
                    $binaryExpression.joinPointType
            );
        }

        // TODO: Check if operators are valid
    }

    hasMutations() {
        return this.currentIndex < this.newOperators.length;
    }

    getMutationPoint() {
        return this.$binaryExpression;
    }

    mutatePrivate() {
        // Obtain new operator, increment index
        const newOp = this.newOperators[this.currentIndex];
        this.currentIndex++;

        // Store current operator
        this.previousOp = this.$binaryExpression.operator;

        // Set new operator
        this.$binaryExpression.operator = newOp;
    }

    restorePrivate() {
        // Restore operator
        this.$binaryExpression.operator = this.previousOp;
        this.previousOp = undefined;
    }
}
