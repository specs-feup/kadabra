import kadabra.mutation.BinaryExpressionMutation;
import weaver.Query;

aspectdef BinaryExpressionMutatorTest

	var mutation = new BinaryExpressionMutation(">", "==");

	// Select binary operators in each file
	for($op of Query.search('binaryExpression').get()) {
	
		if($op.operator === '<') {
			var originalOp = $op;
			
			//var mutatedOps = mutation.mutate($op);
			//for(var $mutatedOp of mutatedOps) {
			for(var mutationResult of mutation.mutate($op)) {			
			//for(var $mutatedOp of mutation.getMutants($op)) {					
			//for(var $mutatedOp of mutation._generator($op)) {			
				var $mutatedOp = mutationResult.getMutation();
				
				// Mutate code
				$op.insertReplace($mutatedOp);
				
				// Print
				console.log($mutatedOp.getAncestor('method').srcCode);
				
				// Restore
				$mutatedOp.insertReplace(originalOp);
			}
		}
	}

end
