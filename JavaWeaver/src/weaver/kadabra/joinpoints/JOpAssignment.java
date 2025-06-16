/**
 * Copyright 2023 SPeCS.
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

import pt.up.fe.specs.util.SpecsLogs;
import spoon.reflect.code.CtOperatorAssignment;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.abstracts.joinpoints.AOpAssignment;
import weaver.utils.element.OperatorUtils;

public class JOpAssignment<T, V extends T> extends AOpAssignment {

    private final CtOperatorAssignment<T, V> node;

    public JOpAssignment(CtOperatorAssignment<T, V> node) {
        super(new JAssignment<>(node));
        this.node = node;
    }

    @Override
    public CtElement getNode() {
        return node;
    }

    @Override
    public String getOperatorImpl() {
        return OperatorUtils.convert(node.getKind()) + "=";
    }

    @Override
    public void setOperatorImpl(String operator) {

        if (!operator.endsWith("=")) {
            SpecsLogs.msgInfo("Invalid assignment operator '" + operator + "', must end with '='");
            return;
        }

        var spoonOp = operator.substring(0, operator.length() - 1);

        // Convert string to kind
        var kind = OperatorUtils.parseBinaryTry(spoonOp).orElse(null);

        if (kind == null) {
            SpecsLogs.msgInfo("Could not parse assignment operator '" + operator + "'");
            return;
        }

        node.setKind(kind);
    }

}
