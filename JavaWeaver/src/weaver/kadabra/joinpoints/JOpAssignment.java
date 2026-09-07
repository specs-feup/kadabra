/**
 * Copyright 2023 SPeCS.
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

import pt.up.fe.specs.util.SpecsLogs;

import spoon.reflect.code.CtOperatorAssignment;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AOpAssignment;
import weaver.utils.element.OperatorUtils;

public class JOpAssignment<Self extends JOpAssignment<Self>> extends AOpAssignment<Self> {

    public JOpAssignment(CtOperatorAssignment node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public CtOperatorAssignment<?, ?> getNodeImpl() {
        return (CtOperatorAssignment<?, ?>) super.getNodeImpl();
    }

    @Override
    public String getOperatorImpl() {
        return OperatorUtils.convert(getNodeImpl().getKind()) + "=";
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

        getNodeImpl().setKind(kind);
    }

}
