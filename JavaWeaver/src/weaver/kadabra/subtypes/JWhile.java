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

package weaver.kadabra.subtypes;

import spoon.reflect.code.CtWhile;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.enums.LoopType;
import weaver.kadabra.joinpoints.JLoop;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

/**
 * Join point of a 'while' loop.
 * <p>
 * This class lives outside the 'joinpoints' package because it is not a
 * declared join point in the specification: the LARA-visible join point is
 * 'loop' (represented by JLoop).
 */
public class JWhile<Self extends JWhile<Self>> extends JLoop<Self> {

    public JWhile(CtWhile node, JWeaver weaver) {
        super(node, LoopType.WHILE, weaver);
    }

    @Override
    public AExpression<?> getCondImpl() {
        return (AExpression<?>) CtElement2JoinPoint.convert(getNodeImpl().getLoopingExpression(), getWeaverEngine());
    }

    @Override
    public CtWhile getNodeImpl() {
        return (CtWhile) super.getNodeImpl();
    }
}
