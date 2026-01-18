import ArithmeticOperatorDeletionMutation from "@specs-feup/kadabra/api/kadabra/mutation/ArithmeticOperatorDeletionMutation.js";
import ConditionalOperatorDeletionMutation from "@specs-feup/kadabra/api/kadabra/mutation/ConditionalOperatorDeletionMutation.js";
import IterativeMutator from "@specs-feup/lara/api/lara/mutation/IterativeMutator.js";
import Query from "@specs-feup/lara/api/weaver/Query.js";

console.log("ArithmeticOperatorDeletion");

var mutator = new IterativeMutator(new ArithmeticOperatorDeletionMutation());
mutator.addJps(
    Query.search("method", "arithmeticOperatorDeletion").first().descendants
);

// Generate all mutations
while (mutator.mutateSource()) {
    // Print
    console.log(mutator.getMutatedPoint().getAncestor("statement").srcCode);
}

console.log("ConditionalOperatorDeletionMutation");

var mutator = new IterativeMutator(new ConditionalOperatorDeletionMutation());
mutator.addJps(
    Query.search("method", "conditionalOperatorDeletion").first().descendants
);

// Generate all mutations
while (mutator.mutateSource()) {
    // Print
    console.log(mutator.getMutatedPoint().srcCode);
}
