/**
 * Copyright 2019 SPeCS.
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

import java.util.Arrays;
import java.util.List;

import spoon.reflect.code.CtConditional;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.ATernary;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JTernary<T> extends ATernary {

    private final CtConditional<T> conditional;

    public JTernary(CtConditional<T> conditional) {
        super(new JExpression<>(conditional));

        this.conditional = conditional;
    }

    public static <T> JTernary<T> newInstance(CtConditional<T> node) {
        return new JTernary<>(node);
    }

    @Override
    public CtElement getNode() {
        return conditional;
    }

    @Override
    public AExpression getConditionImpl() {
        return (AExpression) CtElement2JoinPoint.convert(conditional.getCondition());
    }

    @Override
    public AExpression getCondImpl() {
        return getConditionImpl();
    }

    @Override
    public AExpression getThenImpl() {
        return (AExpression) CtElement2JoinPoint.convert(conditional.getThenExpression());
    }

    @Override
    public AExpression getElseImpl() {
        return (AExpression) CtElement2JoinPoint.convert(conditional.getElseExpression());
    }

    @Override
    public List<? extends AExpression> selectCondition() {
        return Arrays.asList(getConditionImpl());
    }

    @Override
    public List<? extends AExpression> selectThen() {
        return Arrays.asList(getThenImpl());
    }

    @Override
    public List<? extends AExpression> selectElse() {
        return Arrays.asList(getElseImpl());
    }
}
