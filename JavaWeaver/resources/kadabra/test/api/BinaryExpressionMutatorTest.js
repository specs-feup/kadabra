laraImport("kadabra.mutation.BinaryExpressionMutator");
laraImport("weaver.Query");

// Select binary operators in each file
for(const $op of Query.search('binaryExpression').get()) {

	if($op.operator === '<') {
		const mutator = new BinaryExpressionMutator($op, ">", "=="); // [">", "=="] is also supported

		while(mutator.hasMutations()) {
			// Mutate
			mutator.mutate();
			
			// Print
			if(mutator.getMutationPoint() !== undefined) {
				console.log(mutator.getMutationPoint().getAncestor('method').srcCode);
			}


			// Restore operator
			mutator.restore();
		}
	}
}
