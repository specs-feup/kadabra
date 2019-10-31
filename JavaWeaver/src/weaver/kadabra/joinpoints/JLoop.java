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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtDo;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.ABody;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.ALoop;
import weaver.kadabra.enums.LoopType;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.SpoonUtils;
import weaver.utils.element.RankCalculator;
import weaver.utils.scanners.NodeSearcher;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;

public abstract class JLoop extends ALoop {

    private final CtLoop node;
    private final LoopType type;
    private Integer nestedLevel;
    protected String rank;

    public static JLoop newInstance(CtLoop node) {
        SpoonUtils.sanitizeBody(node);
        if (node instanceof CtFor) {

            return JFor.newInstance((CtFor) node);
        } else if (node instanceof CtWhile) {

            return JWhile.newInstance((CtWhile) node);
        } else if (node instanceof CtDo) {

            return JDoWhile.newInstance((CtDo) node);

        } else if (node instanceof CtForEach) {

            return JForEach.newInstance((CtForEach) node);
        }
        throw new RuntimeException("Type of for loop not yet implemented: " + node.getClass());

    }

    protected JLoop(CtLoop node, LoopType type) {
        super(new JStatement(node));
        this.node = node;
        this.type = type;
        nestedLevel = null;
        rank = null;
    }

    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return new AJoinPoint[] { insertImplJLoop(position, code) };
    }

    public AJavaWeaverJoinPoint insertImplJLoop(String position, String code) {
        return ActionUtils.insert(position, code, node, getWeaverProfiler());
    }

    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return insertImplJLoop("after", code);
    }

    @Override
    public LoopType getTypeImpl() {
        return type;
    }

    @Override
    public List<? extends ABody> selectBody() {
        final CtStatement body = node.getBody();
        // The SpoonUtils.sanitizeBody ensures that the body of the loop is in
        // fact a CtBlock
        return SelectUtils.node2JoinPointList((CtBlock<?>) body, JBody::newInstance);
        // return Collections.emptyList();
    }

    @Override
    public CtLoop getNode() {
        return node;
    }

    @Override
    public Boolean getIsInnermostImpl() {
        List<CtLoop> innerLoops = new ArrayList<>();
        Class<CtLoop> searchClass = CtLoop.class;
        List<Class<? extends CtElement>> cuts = Arrays.asList(searchClass);

        new NodeSearcher(searchClass, innerLoops::add, Collections.emptyList(), cuts)
                .scan(node.getElements(e -> e != node));
        return innerLoops.isEmpty();
    }

    @Override
    public Boolean getIsOutermostImpl() {
        return getNestedLevelImpl() == 0;
    }

    @Override
    public String getRankImpl() {
        if (rank != null) {
            return rank;
        }
        rank = RankCalculator.calculateString(node, CtLoop.class);
        return rank;
    }

    @Override
    public Integer getNestedLevelImpl() {
        if (nestedLevel != null) {
            return nestedLevel;
        }
        CtElement parent = node.getParent();
        nestedLevel = 0;
        while (parent != null && !(parent instanceof CtAnonymousExecutable)
                && !(parent instanceof CtExecutable)) {
            // System.out.println(parent);
            if (parent instanceof CtLoop) {
                nestedLevel++;
            }
            parent = parent.getParent();
        }
        return nestedLevel;
    }

    @Override
    public String getControlVarImpl() {
        KadabraLog.warning("Control variable can only be retrieved from 'for' loops");
        return null;
    }

    // @Override
    // public int getLine() {
    //
    // return node.getPosition().getLine();
    // }
    //
    // @Override
    // public int getEndLine() {
    // return node.getPosition().getEndLine();
    // }
}
