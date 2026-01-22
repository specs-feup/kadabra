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

import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.joinpoints.AFieldAccess;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JFieldAccess<T> extends AFieldAccess {

    private final CtFieldAccess<T> node;

    protected JFieldAccess(CtFieldAccess<T> var, JavaWeaver weaver) {
        super(new JVar<>(var, weaver), weaver);
        node = var;
    }

    public static <T> JFieldAccess<T> newInstance(CtFieldAccess<T> var, JavaWeaver weaver) {
        return new JFieldAccess<>(var, weaver);
    }

    @Override
    public CtElement getNode() {
        return node;
    }

    @Override
    public AExpression getBaseImpl() {
        return CtElement2JoinPoint.convert(node.getTarget(), getWeaverEngine(), AExpression.class);
    }
}
