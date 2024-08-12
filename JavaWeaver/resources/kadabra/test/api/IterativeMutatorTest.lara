import kadabra.mutation.BinaryExpressionMutation;
import lara.mutation.IterativeMutator;
import weaver.Query;

aspectdef IterativeMutatorTest

	var mutation1 = new BinaryExpressionMutation(">", "==");
	var mutation2 = new BinaryExpressionMutation("!=");	
	
	var mutator = new IterativeMutator(mutation1, mutation2);
	mutator.addJps(Query.root().descendants);

	// Generate all mutations
	while(mutator.mutateSource()) {
		
		// Print
		console.log(mutator.getMutatedPoint().getAncestor('statement').srcCode);

		var mutatedPoint = mutator.getMutatedPoint();
		//console.log("Original parent: " + mutator.getMutationPoint().parent);		
		//console.log("Mutated parent: " + mutator.getMutatedPoint().parent);		
		
		//console.log("Mutation: " + mutator.getMutatedPoint() + "\n" + Query.root().srcCode);
		
		// Restore operator
		mutator.restoreSource();
	}

end
