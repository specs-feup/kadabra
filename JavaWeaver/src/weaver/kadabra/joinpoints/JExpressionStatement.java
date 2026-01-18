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

package weaver.kadabra.joinpoints;

import spoon.reflect.code.CtStatement;

/**
 * This class encapsulates expressions that can also be a statement, such as Invocations (JCall/CtInvocation) and
 * Assignemnt (JAssignment/CtAssignment).
 *
 * @author tiago
 *
 */
public class JExpressionStatement extends JStatement {

    public JExpressionStatement(CtStatement node) {
        super(node);
    }

    public static JExpressionStatement newInstance(CtStatement node) {
        return new JExpressionStatement(node);
    }

    @Override
    public String getKindImpl() {
        return "expressionStatement";
    }
}
