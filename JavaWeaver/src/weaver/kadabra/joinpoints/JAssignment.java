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

import java.util.List;

import pt.up.fe.specs.util.SpecsLogs;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtOperatorAssignment;
import weaver.kadabra.abstracts.joinpoints.AAssignment;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JAssignment<T, V extends T> extends AAssignment {

    private final CtAssignment<T, V> node;

    protected JAssignment(CtAssignment<T, V> node) {
        super(new JStatement(node));
        this.node = node;
    }

    public static <T, V extends T> JAssignment<T, V> newInstance(CtAssignment<T, V> node) {
        if (node instanceof CtOperatorAssignment) {
            return JOpAssignmentAux.newInstance((CtOperatorAssignment<T, V>) node);
        }
        return new JAssignment<>(node);
    }

    @Override
    public String getOperatorImpl() {
        return "=";
    }

    @Override
    public void defOperatorImpl(String value) {
        SpecsLogs.msgInfo("Def of attribute operator not supported for join point 'assignment'");
    }

    @Override
    public List<? extends AExpression> selectLhs() {
        return SelectUtils.expression2JoinPointList(node.getAssigned());
    }

    @Override
    public List<? extends AExpression> selectRhs() {
        return SelectUtils.expression2JoinPointList(node.getAssignment());
    }

    @Override
    public CtAssignment<T, V> getNode() {
        return node;
    }

    @Override
    public AExpression getLhsImpl() {
        return (AExpression) CtElement2JoinPoint.convert(node.getAssigned());
    }

    @Override
    public AExpression getRhsImpl() {
        return (AExpression) CtElement2JoinPoint.convert(node.getAssignment());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void defLhsImpl(AExpression value) {
        node.setAssigned((CtExpression<T>) value.getNode());
    }

    @Override
    public void setLhsImpl(AExpression lhs) {
        defLhsImpl(lhs);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void defRhsImpl(AExpression value) {
        node.setAssignment((CtExpression<V>) value.getNode());
    }

    @Override
    public void setRhsImpl(AExpression rhs) {
        defRhsImpl(rhs);
    }
}
