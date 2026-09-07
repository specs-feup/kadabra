/**
 * Copyright 2015 SPeCS.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.subtypes;

import java.util.ArrayList;
import java.util.List;

import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtLocalVariable;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AField;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.ALoop;
import weaver.kadabra.enums.LoopType;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.kadabra.joinpoints.JField;
import weaver.kadabra.joinpoints.JLoop;
import weaver.utils.SpoonUtils;
import weaver.utils.transformations.LoopTiling;
import weaver.utils.weaving.AttributeUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

/**
 * Join point of a 'for' loop.
 * <p>
 * This class lives outside the 'joinpoints' package because it is not a
 * declared join point in the specification: the LARA-visible join point is
 * 'loop' (represented by JLoop).
 */
public class JFor<Self extends JFor<Self>> extends JLoop<Self> {

    public JFor(CtFor node, JWeaver weaver) {
        super(node, LoopType.FOR, weaver);
    }

    @Override
    public String getControlVarImpl() {

        CtLocalVariable<?> controlVar = AttributeUtils.getControlVar(getNodeImpl());
        if (controlVar == null) {
            return null;
        }
        return controlVar.getSimpleName();
    }

    /**
     * Implementation of loop tiling
     */
    @Override
    public AField<?> tileImpl(String tileName, String block, boolean unique, AJoinpoint<?> around) {
        spoon.reflect.code.CtStatement aroundStatement;
        if (around == null) {
            aroundStatement = getNodeImpl();
        } else {
            Object nodeRef = around.getNodeImpl();
            if (!(nodeRef instanceof spoon.reflect.declaration.CtElement)) {
                String ref = nodeRef == null ? null : nodeRef.getClass().toString();
                throw new JavaWeaverException("When applying tile action",
                        new RuntimeException("unknown reference for insertion: " + ref));
            }
            aroundStatement = SpoonUtils.getInsertableParent((spoon.reflect.declaration.CtElement) nodeRef);
        }
        spoon.reflect.declaration.CtField<Integer> tileField = LoopTiling.tile(this, tileName, block, unique,
                aroundStatement);
        if (tileField == null) {
            return null;
        }
        // invalidate current rank
        rank = null;
        return new JField<>(tileField, getWeaverEngine());
    }

    @Override
    public void tileImpl(int block) {
        LoopTiling.tile(getNodeImpl(), block);

    }

    @Override
    public AExpression<?> getCondImpl() {
        return (AExpression<?>) CtElement2JoinPoint.convert(getNodeImpl().getExpression(), getWeaverEngine());
    }

    /**
     * A simple implementation of loop interchange
     */
    @Override
    public void interchangeImpl(ALoop<?> jFor) {

        if (!(jFor instanceof JFor<?>)) {
            throw new JavaWeaverException("The second Loop for interchange has to be a 'for' loop");
        }
        JFor<?> jFor2 = (JFor<?>) jFor;
        CtFor secondFor = jFor2.getNodeImpl();
        List<spoon.reflect.code.CtStatement> inits = new ArrayList<>(secondFor.getForInit());
        CtExpression<Boolean> cond = secondFor.getExpression();
        List<spoon.reflect.code.CtStatement> updates = new ArrayList<>(secondFor.getForUpdate());

        secondFor.setForInit(getNodeImpl().getForInit());
        secondFor.setExpression(getNodeImpl().getExpression());
        secondFor.setForUpdate(getNodeImpl().getForUpdate());

        getNodeImpl().setForInit(inits);
        getNodeImpl().setExpression(cond);
        getNodeImpl().setForUpdate(updates);

        // invalidate ranks
        jFor2.rank = null;
        rank = null;
    }

    @Override
    public CtFor getNodeImpl() {
        return (CtFor) super.getNodeImpl();
    }
}
