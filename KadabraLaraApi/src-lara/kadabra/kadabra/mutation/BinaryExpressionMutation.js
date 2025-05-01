import IterativeMutation from "@specs-feup/lara/api/lara/mutation/IterativeMutation.js";
import MutationResult from "@specs-feup/lara/api/lara/mutation/MutationResult.js";
import { BinaryExpression } from "../../Joinpoints.js";
export default class BinaryExpressionMutation extends IterativeMutation {
    newOperators;
    // Parent constructor
    constructor(...newOperators) {
        super("BinaryExpressionMutation");
        // TODO: Check if operators are valid
        this.newOperators = newOperators;
    }
    isMutationPoint(jp) {
        return jp instanceof BinaryExpression;
    }
    *mutate(jp) {
        for (const newOp of this.newOperators) {
            const mutation = jp.copy();
            // Skip when operators are the same
            if (mutation.operator === newOp)
                continue;
            mutation.operator = newOp;
            yield new MutationResult(mutation);
        }
    }
}
//# sourceMappingURL=BinaryExpressionMutation.js.map