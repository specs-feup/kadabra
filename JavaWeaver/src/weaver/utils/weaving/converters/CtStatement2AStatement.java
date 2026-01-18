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

package weaver.utils.weaving.converters;

import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.code.*;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.joinpoints.*;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetStatement;

/**
 * Converts a given statement to the correct Join point type
 *
 * @author tiago
 *
 */
public class CtStatement2AStatement {
    private static final FunctionClassMap<CtStatement, AJavaWeaverJoinPoint> CONVERTER = new FunctionClassMap<>(
            JStatement::new);

    static {

        CONVERTER.put(CtInvocation.class, CtExpression2AExpression::ctInvokation);
        CONVERTER.put(CtAssignment.class, JExpressionStatement::newInstance);
        CONVERTER.put(CtIf.class, JIf::newInstance);
        CONVERTER.put(CtLoop.class, JLoop::newInstance);
        // CONVERTER.put(CtOperatorAssignment.class, JOpAssignmentAux::newInstance);
        CONVERTER.put(CtAssignment.class, JAssignment::newInstance);
        CONVERTER.put(CtOperatorAssignment.class, JOpAssignment::new);
        CONVERTER.put(CtReturn.class, JReturn::newInstance);
        CONVERTER.put(CtKadabraSnippetStatement.class, JSnippetStmt::newInstance);
        CONVERTER.put(CtLocalVariable.class, JLocalVariable::newInstance);
        CONVERTER.put(CtTry.class, JTry::newInstance);
        CONVERTER.put(CtAssert.class, JAssert::newInstance);
        CONVERTER.put(CtComment.class, JComment::newInstance);
        CONVERTER.put(CtBlock.class, JBody::newInstance);
        CONVERTER.put(CtThrow.class, JThrow::newInstance);
        CONVERTER.put(CtSwitch.class, JSwitch::newInstance);
        CONVERTER.put(CtCase.class, JCase::newInstance);
        CONVERTER.put(CtBreak.class, JBreak::newInstance);
        CONVERTER.put(CtContinue.class, JContinue::newInstance);
    }

    // Package protected so only CtElement2JoinPoint can use this method
    public static AJavaWeaverJoinPoint convert(CtStatement element) {
        return CONVERTER.apply(element);

    }
}
