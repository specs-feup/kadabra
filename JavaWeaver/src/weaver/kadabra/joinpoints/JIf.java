/**
 * Copyright 2016 SPeCS.
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

import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import tdrc.utils.StringUtils;
import weaver.kadabra.abstracts.joinpoints.ABody;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AIf;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.utils.SpoonUtils;
import weaver.utils.element.RankCalculator;
import weaver.utils.weaving.SelectUtils;

public class JIf extends AIf {

    private final CtIf node;
    private String rank;

    private JIf(CtIf node) {
        super(new JStatement(node));
        this.node = node;
    }

    public static JIf newInstance(CtIf node) {
        return new JIf(node);
    }

    // @Override
    // public int getLine() {
    // return node.getPosition().getLine();
    // }
    //
    // @Override
    // public int getEndLine() {
    // return node.getPosition().getEndLine();
    // }

    @Override
    public String getRankImpl() {
        if (rank != null) {
            return rank;
        }
        CtElement executableAncestor = SpoonUtils.getExecutableAncestor(node);
        List<Integer> ranks = RankCalculator.calculate(node, CtIf.class, executableAncestor);
        rank = StringUtils.join(ranks, ".");
        return rank;
    }

    @Override
    public List<? extends AExpression> selectCond() {
        return SelectUtils.expression2JoinPointList(node.getCondition());
    }

    @Override
    public List<? extends ABody> selectThen() {
        CtStatement thenStatement = node.getThenStatement();
        if (!(thenStatement instanceof CtBlock)) {
            throw new JavaWeaverException("The then statement must always be a block");
        }

        return SelectUtils.node2JoinPointList((CtBlock<?>) thenStatement, JBody::newInstance);
    }

    @Override
    public List<? extends ABody> selectElse() {
        CtStatement elseStatement = node.getElseStatement();

        if (elseStatement == null) {
            return Collections.emptyList();
        }

        if (!(elseStatement instanceof CtBlock)) {
            throw new JavaWeaverException("The else statement must always be a block");
        }

        return SelectUtils.node2JoinPointList((CtBlock<?>) elseStatement, JBody::newInstance);
    }

    @Override
    public CtIf getNode() {
        return node;
    }

}
