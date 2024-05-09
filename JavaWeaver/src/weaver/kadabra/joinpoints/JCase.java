/**
 * Copyright 2018 SPeCS.
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

import java.util.List;

import spoon.reflect.code.CtCase;
import weaver.kadabra.abstracts.joinpoints.ACase;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JCase<S> extends ACase {

    private final CtCase<S> node;

    private JCase(CtCase<S> node) {
        super(new JStatement(node));
        this.node = node;
    }

    public static <S> JCase<S> newInstance(CtCase<S> node) {
        return new JCase<>(node);
    }

    @Override
    public CtCase<S> getNode() {
        return node;
    }

    @Override
    public List<? extends AExpression> selectExpression() {
        return SelectUtils.expression2JoinPointList(node.getCaseExpression());
    }

    @Override
    public List<? extends AExpression> selectExpr() {
        return selectExpression();
    }

    @Override
    public List<? extends AStatement> selectStatement() {
        return SelectUtils.statementList2JoinPointList(node.getStatements());
    }

    @Override
    public List<? extends AStatement> selectStmt() {
        return selectStatement();
    }

    @Override
    public Boolean getIsDefaultImpl() {
        return node.getCaseExpression() == null;
    }

    @Override
    public AStatement[] getStmtsArrayImpl() {
        return CtElement2JoinPoint.convertList(node.getStatements(), AStatement.class);
    }

    @Override
    public AExpression getExprImpl() {
        return CtElement2JoinPoint.convert(node.getCaseExpression(), AExpression.class);
    }

}
