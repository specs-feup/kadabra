laraImport("kadabra.mutation.ArithmeticOperatorDeletionMutation");
laraImport("kadabra.mutation.ConditionalOperatorDeletionMutation");
laraImport("lara.mutation.IterativeMutator");
laraImport("weaver.Query");


console.log("ArithmeticOperatorDeletion");

var mutator = new IterativeMutator(new ArithmeticOperatorDeletionMutation());
mutator.addJps(Query.search("method", "arithmeticOperatorDeletion").first().descendants);

// Generate all mutations
while (mutator.mutateSource()) {
    // Print
    console.log(mutator.getMutatedPoint().getAncestor('statement').srcCode);
}


console.log("ConditionalOperatorDeletionMutation");

var mutator = new IterativeMutator(new ConditionalOperatorDeletionMutation());
mutator.addJps(Query.search("method", "conditionalOperatorDeletion").first().descendants);

// Generate all mutations
while (mutator.mutateSource()) {
    // Print
    console.log(mutator.getMutatedPoint().srcCode);
}
