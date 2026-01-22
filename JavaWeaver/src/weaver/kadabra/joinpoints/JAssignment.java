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
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.joinpoints;

import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtOperatorAssignment;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.joinpoints.AAssignment;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JAssignment<T, V extends T> extends AAssignment {

    private final CtAssignment<T, V> node;

    protected JAssignment(CtAssignment<T, V> node, JavaWeaver weaver) {
        super(new JStatement(node, weaver), weaver);
        this.node = node;
    }

    public static <T, V extends T> JAssignment<T, V> newInstance(CtAssignment<T, V> node, JavaWeaver weaver) {
        if (node instanceof CtOperatorAssignment) {
            return JOpAssignmentAux.newInstance((CtOperatorAssignment<T, V>) node, weaver);
        }
        return new JAssignment<>(node, weaver);
    }

    @Override
    public String getOperatorImpl() {
        return "=";
    }

    @Override
    public CtAssignment<T, V> getNode() {
        return node;
    }

    @Override
    public AExpression getLhsImpl() {
        return (AExpression) CtElement2JoinPoint.convert(node.getAssigned(), getWeaverEngine());
    }

    @Override
    public AExpression getRhsImpl() {
        return (AExpression) CtElement2JoinPoint.convert(node.getAssignment(), getWeaverEngine());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setLhsImpl(AExpression lhs) {
        node.setAssigned((CtExpression<T>) lhs.getNode());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setRhsImpl(AExpression rhs) {
        node.setAssignment((CtExpression<V>) rhs.getNode());
    }
}
