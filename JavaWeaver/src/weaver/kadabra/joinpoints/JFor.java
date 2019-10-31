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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pt.up.fe.specs.util.SpecsLogs;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AField;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.ALoop;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.kadabra.enums.LoopType;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.utils.SpoonUtils;
import weaver.utils.transformations.LoopTiling;
import weaver.utils.weaving.AttributeUtils;
import weaver.utils.weaving.SelectUtils;

public class JFor extends JLoop {

    CtFor node;

    private JFor(CtFor node) {
        super(node, LoopType.FOR);
        this.node = node;
    }

    public static JFor newInstance(CtFor node) {
        return new JFor(node);
    }

    @Override
    public String getControlVarImpl() {

        CtLocalVariable<?> controlVar = AttributeUtils.getControlVar(node);
        if (controlVar == null) {
            return null;
        }
        return controlVar.getSimpleName();
    }

    /**
     * Implementation of loop tiling
     * 
     * @param block
     * @return
     */
    @Override
    public AField tileImpl(String tileName, String block, boolean unique, AJoinPoint around) {
        CtStatement aroundStatement;
        if (around == null) {
            aroundStatement = node;
        } else {
            Object nodeRef = around.getNode();
            if (!(nodeRef instanceof CtElement)) {
                String ref = nodeRef == null ? null : nodeRef.getClass().toString();
                throw new JavaWeaverException("When applying tile action",
                        new RuntimeException("unknown reference for insertion: " + ref));
            }
            aroundStatement = SpoonUtils.getInsertableParent((CtElement) nodeRef);
        }
        CtField<Integer> tileField = LoopTiling.tile(this, tileName, block, unique, aroundStatement);
        if (tileField == null) {
            return null;
        }
        // invalidate current rank
        rank = null;
        JField<Integer> newInstance = JField.newInstance(tileField);
        return newInstance;
    }

    @Override
    public void tileImpl(int block) {
        LoopTiling.tile(node, block);

    }

    @Override
    public List<? extends AStatement> selectInit() {

        return SelectUtils.statementList2JoinPointList(node.getForInit());
    }

    @Override
    public List<? extends AExpression> selectCond() {
        return SelectUtils.expression2JoinPointList(node.getExpression());

    }

    @Override
    public List<? extends AStatement> selectStep() {
        final List<CtStatement> forUpdate = node.getForUpdate();
        return SelectUtils.statementList2JoinPointList(forUpdate);
    }

    @Override
    public List<? extends AExpression> selectExpr() {
        SpecsLogs.msgWarn(
                "The for loop does not contain a single expression. The select 'expr' should only be used in 'for-each' loops");
        return Collections.emptyList();
    }

    /**
     * A simple implementation of loop interchange
     * 
     * @param jFor
     */
    @Override
    public void interchangeImpl(ALoop jFor) {

        if (!(jFor instanceof JFor)) {
            throw new JavaWeaverException("The second Loop for interchange has to be a 'for' loop");
        }
        JFor jFor2 = (JFor) jFor;
        CtFor secondFor = jFor2.node;
        List<CtStatement> inits = new ArrayList<>(secondFor.getForInit());
        CtExpression<Boolean> cond = secondFor.getExpression();
        List<CtStatement> updates = new ArrayList<>(secondFor.getForUpdate());

        secondFor.setForInit(node.getForInit());
        secondFor.setExpression(node.getExpression());
        secondFor.setForUpdate(node.getForUpdate());

        node.setForInit(inits);
        node.setExpression(cond);
        node.setForUpdate(updates);

        // invalidate ranks
        jFor2.rank = null;
        rank = null;
    }

    @Override
    public CtFor getNode() {
        return node;
    }
}
