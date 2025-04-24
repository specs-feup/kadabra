import IterativeMutation from "@specs-feup/lara/api/lara/mutation/IterativeMutation.js";
import MutationResult from "@specs-feup/lara/api/lara/mutation/MutationResult.js";
import { debug } from "@specs-feup/lara/api/lara/core/LaraCore.js";
import { Joinpoint, BinaryExpression } from "../../Joinpoints.js";

export default class ArithmeticOperatorDeletionMutation extends IterativeMutation {
    constructor() {
        super("ArithmeticOperatorDeletionMutation");
    }

    isMutationPoint(jp: Joinpoint) {
        return jp instanceof BinaryExpression;
    }

    *mutate(jp: BinaryExpression) {
        const leftOperand = jp.lhs.copy();

        debug("/*--------------------------------------*/");
        debug("Mutating operator: " + jp + " to " + leftOperand);
        debug("/*--------------------------------------*/");

        yield new MutationResult(leftOperand);

        const rightOperand = jp.rhs.copy();

        debug("/*--------------------------------------*/");
        debug("Mutating operator: " + jp + " to " + rightOperand);
        debug("/*--------------------------------------*/");

        yield new MutationResult(rightOperand);
    }
}
