laraImport("kadabra.mutation.BinaryExpressionMutation");
laraImport("weaver.Query");

BinaryExpressionMutatorTest();

function BinaryExpressionMutatorTest(){

	const mutation = new BinaryExpressionMutation(">", "==");

	// Select binary operators in each file
	for(const $op of Query.search('binaryExpression').get()) {
	
		if($op.operator === '<') {
			const originalOp = $op;
			
			//var mutatedOps = mutation.mutate($op);
			//for(var $mutatedOp of mutatedOps) {
			for(const mutationResult of mutation.mutate($op)) {			
			//for(var $mutatedOp of mutation.getMutants($op)) {					
			//for(var $mutatedOp of mutation._generator($op)) {			
				const $mutatedOp = mutationResult.getMutation();
				
				// Mutate code
				$op.insertReplace($mutatedOp);
				
				// Print
				console.log($mutatedOp.getAncestor('method').srcCode);
				
				// Restore
				$mutatedOp.insertReplace(originalOp);
			}
		}
	}
}
