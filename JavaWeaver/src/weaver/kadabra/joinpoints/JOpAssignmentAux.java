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

import spoon.reflect.code.CtOperatorAssignment;
import weaver.kadabra.JavaWeaver;
import weaver.utils.element.OperatorUtils;

public class JOpAssignmentAux<T, V extends T> extends JAssignment<T, V> {

    private final CtOperatorAssignment<T, V> node;

    private JOpAssignmentAux(CtOperatorAssignment<T, V> node, JavaWeaver weaver) {
        super(node, weaver);
        this.node = node;
    }

    public static <T, V extends T> JOpAssignmentAux<T, V> newInstance(CtOperatorAssignment<T, V> node,
            JavaWeaver weaver) {
        return new JOpAssignmentAux<>(node, weaver);
    }

    @Override
    public String getOperatorImpl() {
        return OperatorUtils.convert(node.getKind()) + "=";
    }

}
