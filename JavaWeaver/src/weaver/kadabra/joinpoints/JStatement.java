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

import org.lara.interpreter.weaver.interf.JoinPoint;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import tdrc.utils.StringUtils;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.converters.CtStatement2AStatement;

public class JStatement extends AStatement {

    private final CtStatement node;

    public JStatement(CtStatement node) {

        this.node = node;
    }

    public static AJavaWeaverJoinPoint newInstance(CtStatement node) {
        return CtStatement2AStatement.convert(node);
    }

    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return new AJoinPoint[]{insertImplJStatement(position, (CtElement) code.getNode())};
    }

    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return new AJoinPoint[]{insertImplJStatement(position, code)};
    }

    public AJavaWeaverJoinPoint insertImplJStatement(String position, CtElement code) {
        return ActionUtils.insert(position, code, node);
    }

    public AJavaWeaverJoinPoint insertImplJStatement(String position, String code) {
        return ActionUtils.insert(position, code, node);
    }

    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return insertImplJStatement("before", code);
    }

    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return insertImplJStatement("after", code);
    }

    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return insertImplJStatement("replace", code);
    }

    @Override
    public CtStatement getNode() {
        return node;
    }

    @Override
    public Integer getEndLineImpl() {
        return node.getPosition().getEndLine();
    }

    @Override
    public String getKindImpl() {
        String tempType = node.getClass().getSimpleName().replace("Ct", "").replace("Impl", "");
        return StringUtils.firstCharToLower(tempType);
    }

    /*
    @Override
    public List<? extends ABinaryExpression> selectBinaryExpr() {
        return selectBinaryExpression();
    }
    
    @Override
    public List<? extends ABinaryExpression> selectBinaryExpression() {
        @SuppressWarnings("unchecked")
        List<? extends ABinaryExpression> select = SelectUtils.select(node, CtBinaryOperator.class,
                JBinaryExpression::newInstance);
        return select;
    }
    
    @Override
    public List<? extends AArrayAccess> selectArrayAccess() {
        @SuppressWarnings("unchecked")
        List<? extends AArrayAccess> select = SelectUtils.select(node, CtArrayAccess.class, JArrayAccess::newInstance);
        return select; // TODO Auto-generated method stub
    }
    */
}
