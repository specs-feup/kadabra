/**
 * Copyright 2017 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

package weaver.utils.visitors;

import java.util.List;

import spoon.reflect.code.CtDo;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.CtAbstractVisitor;
import weaver.utils.SpoonUtils;

public class VisitLoopHeaders extends CtAbstractVisitor {

    private final CtElement target;
    private boolean inLoopHeader;

    public VisitLoopHeaders(CtElement target) {
        this.target = target;
        this.inLoopHeader = false;
    }

    @Override
    public void visitCtDo(CtDo doLoop) {
        CtExpression<Boolean> loopingExpression = doLoop.getLoopingExpression();
        inLoopHeader = SpoonUtils.isAncestor(target, loopingExpression, doLoop);
    }

    @Override
    public void visitCtWhile(CtWhile whileLoop) {
        CtExpression<Boolean> loopingExpression = whileLoop.getLoopingExpression();
        inLoopHeader = SpoonUtils.isAncestor(target, loopingExpression, whileLoop);
    }

    @Override
    public void visitCtFor(CtFor forLoop) {
        List<CtStatement> initStmts = forLoop.getForInit();
        for (CtStatement ctStatement : initStmts) {
            inLoopHeader = SpoonUtils.isAncestor(target, ctStatement, forLoop);
            if (inLoopHeader) {
                return;
            }
        }

        CtExpression<Boolean> loopingExpression = forLoop.getExpression();
        inLoopHeader = SpoonUtils.isAncestor(target, loopingExpression, forLoop);
        if (inLoopHeader) {
            return;
        }
        List<CtStatement> updateStmts = forLoop.getForUpdate();
        for (CtStatement ctStatement : updateStmts) {
            inLoopHeader = SpoonUtils.isAncestor(target, ctStatement, forLoop);
            if (inLoopHeader) {
                return;
            }
        }
    }

    @Override
    public void visitCtForEach(CtForEach foreach) {
        CtExpression<?> expression = foreach.getExpression();
        inLoopHeader = SpoonUtils.isAncestor(target, expression, foreach);
    }

    public boolean isInLoopHeader() {
        return inLoopHeader;
    }

    public void setInLoopHeader(boolean inLoopHeader) {
        this.inLoopHeader = inLoopHeader;
    }
}
