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

import java.util.List;
import java.util.Optional;

import org.lara.interpreter.weaver.interf.JoinPoint;

import spoon.reflect.code.CtArrayAccess;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtVariableAccess;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AArrayAccess;
import weaver.kadabra.abstracts.joinpoints.ABinaryExpression;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.kadabra.abstracts.joinpoints.AVar;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.SpoonUtils;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.converters.CtElement2JoinPoint;
import weaver.utils.weaving.converters.CtExpression2AExpression;

public class JExpression<T> extends AExpression {

    CtExpression<T> node;
    Integer test = 10;

    public JExpression(CtExpression<T> expr) {
        node = expr;
    }

    @Override
    public Integer getTestImpl() {
        return test;
    }

    public static <K> AExpression newInstanceDefault(CtExpression<K> expr) {
        return new JExpression<>(expr);
    }

    public static <K> AExpression newInstance(CtExpression<K> expr) {
        return CtExpression2AExpression.convert(expr);
        // if (expr instanceof CtBinaryOperator) {
        // return JBinaryExpression.newInstance((CtBinaryOperator<T>) expr);
        // }
        //
        // if (expr instanceof CtInvocation) {
        // return JCall.newInstance((CtInvocation<T>) expr);
        // }

        // return new JExpression<>(expr);
    }

    @Override
    public void defTestImpl(AExpression value) {
        defTestImpl(value.getLineImpl());
    }

    @Override
    public void defTestImpl(Integer value) {
        test = value;
    }

    @Override
    public String getKindImpl() {
        return get_class();
    }

    @Override
    public String getTypeImpl() {

        return node.getType().toString();
    }

    @Override
    public List<? extends AExpression> selectExpr() {

        List<AExpression> select = SelectUtils.select(node, CtExpression.class,
                this::toAExpression);
        return select;
    }

    @Override
    public void extractImpl(String varName, AStatement location, String position) {

        Optional<CtStatement> targetO = getTarget(location);

        if (!targetO.isPresent()) {
            throw new JavaWeaverException("Could not get the target location");
        }

        targetO.ifPresent(t -> SpoonUtils.extract(node, varName, t, position, getWeaverProfiler()));
    }

    private Optional<CtStatement> getTarget(AStatement location) {
        if (location == null) {
            return SpoonUtils.getAncestorIncludeSelf(node, CtStatement.class);
        }

        Object stmt = location.getNode();
        if (stmt instanceof CtStatement) {
            return Optional.of((CtStatement) stmt);
        }

        return Optional.empty();
    }

    @Override
    public CtExpression<T> getNode() {
        return node;
    }

    @Override
    public List<? extends AVar> selectVar() {

        @SuppressWarnings("unchecked")
        List<? extends AVar> select = SelectUtils.select(node, CtVariableAccess.class, JVar::newInstance);
        return select;
    }

    @Override
    public List<? extends AArrayAccess> selectArrayAccess() {
        @SuppressWarnings("unchecked")
        List<? extends AArrayAccess> select = SelectUtils.select(node, CtArrayAccess.class, JArrayAccess::newInstance);
        return select; // TODO Auto-generated method stub
    }

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
    public AJoinPoint[] insertImpl(String position, String code) {
        return new AJoinPoint[] { insertImplJExpression(position, code) };
    }

    public AJavaWeaverJoinPoint insertImplJExpression(String position, String code) {
        if (position.equals("replace") || position.equals("around")) {
            return ActionUtils.replaceExpression(position, code, node, getWeaverProfiler());
        } else {
            return ActionUtils.insert(position, code, node, getWeaverProfiler());
        }
    }

    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint JoinPoint) {
        return new AJoinPoint[] { insertImplJExpression(position, (AJavaWeaverJoinPoint) JoinPoint) };
    }

    // @Override
    // public <U extends JoinPoint> AJoinPoint[] insertImpl(String position, U JoinPoint) {
    // return new AJoinPoint[] { insertImplJExpression(position, (AJavaWeaverJoinPoint) JoinPoint) };
    // };

    // @Override
    // public <U extends JoinPoint> JoinPoint[] insertImpl(
    // String position, U JoinPoint) {
    // System.out.println("HELLOO!!!");
    // return null;
    // };

    public AJavaWeaverJoinPoint insertImplJExpression(String position, AJavaWeaverJoinPoint joinPoint) {
        var ctElement = joinPoint.getNode();

        if (position.equals("replace") || position.equals("around")) {
            if (!(ctElement instanceof CtExpression<?>)) {
                KadabraLog.info("Cannot replace a join point of type " + joinPoint.getJoinPointType()
                        + " inside an expression, it has to be another expression");
                return null;
            }

            CtExpression<?> expression = (CtExpression<?>) ctElement;

            return ActionUtils.replaceExpression(position, expression, node, getWeaverProfiler());
        } else {
            return ActionUtils.insert(position, ctElement, node, getWeaverProfiler());
        }
    }

    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return insertImplJExpression("after", code);
    }

    @Override
    public AJoinPoint copyImpl() {
        return CtElement2JoinPoint.convert(SnippetFactory.createSnippetExpression(node.getFactory(), getSrcCodeImpl()));
    }

}
