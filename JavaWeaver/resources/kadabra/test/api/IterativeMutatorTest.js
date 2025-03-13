laraImport("kadabra.mutation.BinaryExpressionMutation");
laraImport("lara.mutation.IterativeMutator");
laraImport("weaver.Query");

IterativeMutatorTest()
function IterativeMutatorTest(){

	const mutation1 = new BinaryExpressionMutation(">", "==");
	const mutation2 = new BinaryExpressionMutation("!=");	
	
	var mutator = new IterativeMutator(mutation1, mutation2);
	mutator.addJps(Query.root().descendants);

	// Generate all mutations
	while(mutator.mutateSource()) {
		
		// Print
		console.log(mutator.getMutatedPoint().getAncestor('statement').srcCode);

		const mutatedPoint = mutator.getMutatedPoint();
		//console.log("Original parent: " + mutator.getMutationPoint().parent);		
		//console.log("Mutated parent: " + mutator.getMutatedPoint().parent);		
		
		//console.log("Mutation: " + mutator.getMutatedPoint() + "\n" + Query.root().srcCode);
		
		// Restore operator
		mutator.restoreSource();
	}

}
