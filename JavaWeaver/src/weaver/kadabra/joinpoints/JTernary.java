/**
 * Copyright 2019 SPeCS.
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

import spoon.reflect.code.CtConditional;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.ATernary;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JTernary<Self extends JTernary<Self>> extends ATernary<Self> {

    public JTernary(CtConditional node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public CtConditional<?> getNodeImpl() {
        return (CtConditional<?>) super.getNodeImpl();
    }

    @Override
    public AExpression<?> getConditionImpl() {
        return (AExpression<?>) CtElement2JoinPoint.convert(getNodeImpl().getCondition(), getWeaverEngine());
    }

    @Override
    public AExpression<?> getCondImpl() {
        return getConditionImpl();
    }

    @Override
    public AExpression<?> getThenImpl() {
        return (AExpression<?>) CtElement2JoinPoint.convert(getNodeImpl().getThenExpression(), getWeaverEngine());
    }

    @Override
    public AExpression<?> getElseImpl() {
        return (AExpression<?>) CtElement2JoinPoint.convert(getNodeImpl().getElseExpression(), getWeaverEngine());
    }
}
