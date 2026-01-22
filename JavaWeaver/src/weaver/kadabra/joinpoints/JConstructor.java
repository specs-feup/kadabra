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

import spoon.reflect.declaration.CtConstructor;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.joinpoints.AConstructor;

public class JConstructor<T> extends AConstructor {

    private final CtConstructor<T> node;

    private JConstructor(CtConstructor<T> node, JavaWeaver weaver) {
        super(JExecutable.newInstance(node, weaver), weaver);
        this.node = node;
    }

    public static <T> JConstructor<T> newInstance(CtConstructor<T> node, JavaWeaver weaver) {
        return new JConstructor<>(node, weaver);
    }

    @Override
    public CtConstructor<?> getNode() {
        return node;
    }

    @Override
    public String getDeclaratorImpl() {

        return node.getDeclaringType().getQualifiedName();
    }

    @Override
    public String toString() {
        return node.getSignature();
    }
}
