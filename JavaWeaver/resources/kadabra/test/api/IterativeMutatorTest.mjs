import BinaryExpressionMutation from "KADABRA/api/kadabra/mutation/BinaryExpressionMutation.js";
import IterativeMutator from "@specs-feup/lara/api/lara/mutation/IterativeMutator.js";
import Query from "@specs-feup/lara/api/weaver/Query.js";

const mutation1 = new BinaryExpressionMutation(">", "==");
const mutation2 = new BinaryExpressionMutation("!=");

var mutator = new IterativeMutator(mutation1, mutation2);
mutator.addJps(Query.root().descendants);

// Generate all mutations
while (mutator.mutateSource()) {
    // Print
    console.log(mutator.getMutatedPoint().getAncestor("statement").srcCode);

    const mutatedPoint = mutator.getMutatedPoint();

    // Restore operator
    mutator.restoreSource();
}
