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

package weaver.kadabra.joinpoints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.lara.interpreter.weaver.interf.enums.InsertPosition;

import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtDo;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AField;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.ALoop;
import weaver.kadabra.enums.LoopType;
import weaver.kadabra.subtypes.JDoWhile;
import weaver.kadabra.subtypes.JFor;
import weaver.kadabra.subtypes.JForEach;
import weaver.kadabra.subtypes.JWhile;
import weaver.utils.SpoonUtils;
import weaver.utils.element.RankCalculator;
import weaver.utils.scanners.NodeSearcher;
import weaver.utils.weaving.ActionUtils;

public class JLoop<Self extends JLoop<Self>> extends ALoop<Self> {

    private final LoopType type;
    private Integer nestedLevel;
    protected String rank;

    public JLoop(CtLoop node, LoopType type, JWeaver weaver) {
        super(node, weaver);
        this.type = type;
        nestedLevel = null;
        rank = null;
    }

    public static JLoop<?> newInstance(CtLoop node, JWeaver weaver) {
        SpoonUtils.sanitizeBody(node);
        if (node instanceof CtFor) {

            return new JFor<>((CtFor) node, weaver);
        } else if (node instanceof CtWhile) {

            return new JWhile<>((CtWhile) node, weaver);
        } else if (node instanceof CtDo) {

            return new JDoWhile<>((CtDo) node, weaver);

        } else if (node instanceof CtForEach) {

            return new JForEach<>((CtForEach) node, weaver);
        }
        throw new RuntimeException("Type of for loop not yet implemented: " + node.getClass());

    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, String code) {
        return new AJoinpoint<?>[] { insertImplJLoop(position, code) };
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, AJoinpoint<?> code) {
        return new AJoinpoint<?>[] {
                ActionUtils.insert(position.name().toLowerCase(), (CtElement) code.getNodeImpl(), getNodeImpl(),
                        getWeaverEngine()) };
    }

    public AJoinpoint<?> insertImplJLoop(InsertPosition position, String code) {
        return ActionUtils.insert(position.name().toLowerCase(), code, getNodeImpl(), getWeaverEngine());
    }

    @Override
    public AJoinpoint<?> insertBeforeImpl(String code) {
        return insertImplJLoop(InsertPosition.BEFORE, code);
    }

    @Override
    public AJoinpoint<?> insertAfterImpl(String code) {
        return insertImplJLoop(InsertPosition.AFTER, code);
    }

    @Override
    public AJoinpoint<?> insertReplaceImpl(String code) {
        return insertImplJLoop(InsertPosition.REPLACE, code);
    }

    @Override
    public LoopType getTypeImpl() {
        return type;
    }

    @Override
    public CtLoop getNodeImpl() {
        return (CtLoop) super.getNodeImpl();
    }

    @Override
    public boolean getIsInnermostImpl() {
        List<CtLoop> innerLoops = new ArrayList<>();
        Class<CtLoop> searchClass = CtLoop.class;
        List<Class<? extends CtElement>> cuts = Arrays.asList(searchClass);

        new NodeSearcher(searchClass, innerLoops::add, Collections.emptyList(), cuts)
                .scan(getNodeImpl().getElements(e -> e != getNodeImpl()));
        return innerLoops.isEmpty();
    }

    @Override
    public boolean getIsOutermostImpl() {
        return getNestedLevelImpl() == 0;
    }

    @Override
    public String getRankImpl() {
        if (rank != null) {
            return rank;
        }
        rank = RankCalculator.calculateString(getNodeImpl(), CtLoop.class);
        return rank;
    }

    @Override
    public int getNestedLevelImpl() {
        if (nestedLevel != null) {
            return nestedLevel;
        }
        CtElement parent = getNodeImpl().getParent();
        int level = 0;
        while (parent != null && !(parent instanceof CtAnonymousExecutable)
                && !(parent instanceof CtExecutable)) {
            if (parent instanceof CtLoop) {
                level++;
            }
            parent = parent.getParent();
        }
        nestedLevel = level;
        return level;
    }

    @Override
    public String getControlVarImpl() {
        weaver.kadabra.util.KadabraLog.warning("Control variable can only be retrieved from 'for' loops");
        return null;
    }

    @Override
    public AExpression<?> getCondImpl() {
        // Only concrete loop subtypes (e.g., 'for', 'while') have a condition
        return null;
    }

    @Override
    public AField<?> tileImpl(String tileName, String block, boolean unique, AJoinpoint<?> around) {
        throw new RuntimeException("The tile action can only be applied to 'for' loops");
    }

    @Override
    public void tileImpl(int block) {
        throw new RuntimeException("The tile action can only be applied to 'for' loops");
    }

    @Override
    public void interchangeImpl(ALoop<?> loop2) {
        throw new RuntimeException("The interchange action can only be applied to 'for' loops");
    }
}
