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

import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.declaration.CtInterface;
import weaver.kadabra.abstracts.joinpoints.AInterface;

public class JInterface<T> extends AInterface {

    CtInterface<T> node;

    private JInterface(CtInterface<T> node, CompilationUnit parent) {
        super(JType.newInstance(node, parent));
        this.node = node;
    }

    public static <T> JInterface<T> newInstance(CtInterface<T> node, CompilationUnit parent) {
        return new JInterface<>(node, parent);
    }

    public static <T> JInterface<T> newInstance(CtInterface<T> node) {
        return new JInterface<>(node, node.getPosition().getCompilationUnit());
    }

    @Override
    public String getSuperClassImpl() {
        return null; // interface does not have a super type (spoon comments: // unsettable property)
    }

    @Override
    public CtInterface<T> getNode() {

        return node;
    }

    @Override
    public String toString() {
        return node.getQualifiedName();
    }
}
