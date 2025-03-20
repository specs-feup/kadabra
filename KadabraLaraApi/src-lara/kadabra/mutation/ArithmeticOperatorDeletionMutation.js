import IterativeMutation from "@specs-feup/lara/api/lara/mutation/IterativeMutation.js";
import MutationResult from "@specs-feup/lara/api/lara/mutation/MutationResult.js";
import debug from 'debug';
class ArithmeticOperatorDeletionMutation extends IterativeMutation {
    constructor() {
        super("ArithmeticOperatorDeletionMutation");
    }
    isMutationPoint($jp) {
        return $jp.instanceOf("binaryExpression");
    }
    *mutate($jp) {
        const leftOperand = $jp.lhs.copy();
        debug("/*--------------------------------------*/");
        debug("Mutating operator: " + $jp + " to " + leftOperand);
        debug("/*--------------------------------------*/");
        yield new MutationResult(leftOperand);
        const rightOperand = $jp.rhs.copy();
        debug("/*--------------------------------------*/");
        debug("Mutating operator: " + $jp + " to " + rightOperand);
        debug("/*--------------------------------------*/");
        yield new MutationResult(rightOperand);
    }
}
//# sourceMappingURL=ArithmeticOperatorDeletionMutation.js.map