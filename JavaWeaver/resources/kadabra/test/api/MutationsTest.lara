import kadabra.mutation.ArithmeticOperatorDeletionMutation;
import kadabra.mutation.ConditionalOperatorDeletionMutation;
import lara.mutation.IterativeMutator;
import weaver.Query;

aspectdef MutationsTest


	testArithmeticOperatorDeletionMutation();
	testConditionalOperatorDeletionMutation();


end


function testArithmeticOperatorDeletionMutation() {

	console.log("ArithmeticOperatorDeletion");
	
	var mutator = new IterativeMutator(new ArithmeticOperatorDeletionMutation());
	mutator.addJps(Query.search("method", "arithmeticOperatorDeletion").first().descendants);

	// Generate all mutations
	while(mutator.mutateSource()) {
		// Print
		console.log(mutator.getMutatedPoint().getAncestor('statement').srcCode);
	}
}


function testConditionalOperatorDeletionMutation() {

	console.log("ConditionalOperatorDeletionMutation");
	
	var mutator = new IterativeMutator(new ConditionalOperatorDeletionMutation());
	mutator.addJps(Query.search("method", "conditionalOperatorDeletion").first().descendants);

	// Generate all mutations
	while(mutator.mutateSource()) {
		// Print
		console.log(mutator.getMutatedPoint().srcCode);
	}
}