import lara.mutation.IterativeMutation;
import lara.mutation.MutationResult;

var ArithmeticOperatorDeletionMutation = function() {
	//Parent constructor
     IterativeMutation.call(this, "ArithmeticOperatorDeletionMutation");
};

// Inheritance
ArithmeticOperatorDeletionMutation.prototype = Object.create(IterativeMutation.prototype);



/*** IMPLEMENTATION OF INSTANCE METHODS ***/

ArithmeticOperatorDeletionMutation.prototype.isMutationPoint = function($jp) {
	return $jp.instanceOf("binaryExpression");
}


ArithmeticOperatorDeletionMutation.prototype.mutate = function* ($jp) {
	
	
	var leftOperand = $jp.lhs.copy();

	debug("/*--------------------------------------*/");
	debug("Mutating operator: "+ $jp +" to "+ leftOperand); 
	debug("/*--------------------------------------*/");

	yield new MutationResult(leftOperand);
	
	
	var rightOperand = $jp.rhs.copy();
	
	debug("/*--------------------------------------*/");
	debug("Mutating operator: "+ $jp +" to "+ rightOperand); 
	debug("/*--------------------------------------*/");	
	
	yield new MutationResult(rightOperand);
}