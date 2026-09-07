/**
 * Copyright 2017 SPeCS.
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

import weaver.kadabra.JWeaver;
import weaver.kadabra.joinpoints.JStatement;

/**
 * This class encapsulates expressions that can also be a statement, such as
 * Invocations (JCall/CtInvocation) and Assignment (JAssignment/CtAssignment).
 * <p>
 * This class lives outside the 'joinpoints' package because it is not a
 * declared join point in the specification.
 *
 * @author tiago
 */
public class JExpressionStatement<Self extends JExpressionStatement<Self>> extends JStatement<Self> {

    public JExpressionStatement(spoon.reflect.code.CtStatement node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public String getKindImpl() {
        return "expressionStatement";
    }
}
