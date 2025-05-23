laraImport("kadabra.mutation.BinaryExpressionMutation");
laraImport("lara.mutation.IterativeMutator");
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
