/**
 * Copyright 2015 SPeCS.
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

package weaver.kadabra.joinpoints;

import java.util.Collections;
import java.util.List;

import pt.up.fe.specs.util.SpecsLogs;
import spoon.reflect.code.CtWhile;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.kadabra.enums.LoopType;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JWhile extends JLoop {

    CtWhile node;

    private JWhile(CtWhile node) {
        super(node, LoopType.WHILE);
        this.node = node;
    }

    public static JWhile newInstance(CtWhile node) {
        return new JWhile(node);
    }

    @Override
    public List<? extends AStatement> selectInit() {

        SpecsLogs.msgWarn(
                "The while loop does not contain an init. The select 'init' should only be used in 'for' loops");
        return Collections.emptyList();
    }

    // @Override
    // public List<? extends AExpression> selectCond() {
    // return SelectUtils.expression2JoinPointList(node.getLoopingExpression());
    //
    // }

    @Override
    public AExpression getCondImpl() {
        return (AExpression) CtElement2JoinPoint.convert(node.getLoopingExpression());
    }

    @Override
    public List<? extends AStatement> selectStep() {
        SpecsLogs.msgWarn(
                "The while loop does not contain a step. The select 'step' should only be used in 'for' loops");
        return Collections.emptyList();
    }

    @Override
    public List<? extends AExpression> selectExpr() {
        return SelectUtils.expression2JoinPointList(node.getLoopingExpression());
    }

    @Override
    public CtWhile getNode() {
        return node;
    }
}
