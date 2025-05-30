import BinaryExpressionMutation from "@specs-feup/kadabra/api/kadabra/mutation/BinaryExpressionMutation.js";
import Query from "@specs-feup/lara/api/weaver/Query.js";

const mutation = new BinaryExpressionMutation(">", "==");

// Select binary operators in each file
for (const $op of Query.search("binaryExpression").get()) {
    if ($op.operator === "<") {
        const originalOp = $op;

        for (const mutationResult of mutation.mutate($op)) {
            const $mutatedOp = mutationResult.getMutation();

            // Mutate code
            $op.insertReplace($mutatedOp);

            // Print
            console.log($mutatedOp.getAncestor("method").srcCode);

            // Restore
            $mutatedOp.insertReplace(originalOp);
        }
    }
}
