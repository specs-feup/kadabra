import lara.mutation.IterativeMutation;
import lara.mutation.MutationResult;

/**
 *  @param {String[] | String...} newOperators - Operators that will be used to mutate the binaryExpression.
 */
var BinaryExpressionMutation = function() {
	// Parent constructor
    IterativeMutation.call(this, "BinaryExpressionMutation");

	// Instance variables
	this.newOperators = arrayFromArgs(arguments);

	// TODO: Check if operators are valid
};

// Inheritance
BinaryExpressionMutation.prototype = Object.create(IterativeMutation.prototype);


/// IMPLEMENTATION OF INSTANCE METHODS 

BinaryExpressionMutation.prototype.isMutationPoint = function($jp) {
	return $jp.instanceOf("binaryExpression");
}


BinaryExpressionMutation.prototype.mutate = function* ($jp) {
	for(var newOp of this.newOperators) {
	
		// Skip when operators are the same
		if($jp.operator === newOp) {
			continue;
		}
		
		var mutation = $jp.copy();
		mutation.operator = newOp;
		yield new MutationResult(mutation);
	}	
}
