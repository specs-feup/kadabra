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

import pt.up.fe.specs.util.classmap.BiFunctionClassMap;
import spoon.reflect.code.CtAssert;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtBreak;
import spoon.reflect.code.CtCase;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtContinue;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtOperatorAssignment;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtSwitch;
import spoon.reflect.code.CtThrow;
import spoon.reflect.code.CtTry;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.kadabra.joinpoints.JAssert;
import weaver.kadabra.joinpoints.JAssignment;
import weaver.kadabra.joinpoints.JBody;
import weaver.kadabra.joinpoints.JBreak;
import weaver.kadabra.joinpoints.JCase;
import weaver.kadabra.joinpoints.JComment;
import weaver.kadabra.joinpoints.JContinue;
import weaver.kadabra.joinpoints.JIf;
import weaver.kadabra.joinpoints.JLocalVariable;
import weaver.kadabra.joinpoints.JLoop;
import weaver.kadabra.joinpoints.JOpAssignment;
import weaver.kadabra.joinpoints.JReturn;
import weaver.kadabra.joinpoints.JSnippetStmt;
import weaver.kadabra.joinpoints.JStatement;
import weaver.kadabra.joinpoints.JSwitch;
import weaver.kadabra.joinpoints.JThrow;
import weaver.kadabra.joinpoints.JTry;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetStatement;

/**
 * Converts a given statement to the correct Join point type
 *
 * @author tiago
 *
 */
public class CtStatement2AStatement {
    private static final BiFunctionClassMap<CtStatement, JWeaver, AJoinpoint<?>> CONVERTER = new BiFunctionClassMap<>();

    static {
        CONVERTER.put(CtInvocation.class, CtExpression2AExpression::ctInvokation);
        CONVERTER.put(CtIf.class, JIf::new);
        CONVERTER.put(CtLoop.class, JLoop::newInstance);
        CONVERTER.put(CtAssignment.class, JAssignment::new);
        CONVERTER.put(CtOperatorAssignment.class, JOpAssignment::new);
        CONVERTER.put(CtReturn.class, JReturn::new);
        CONVERTER.put(CtKadabraSnippetStatement.class, JSnippetStmt::new);
        CONVERTER.put(CtLocalVariable.class, JLocalVariable::new);
        CONVERTER.put(CtTry.class, JTry::new);
        CONVERTER.put(CtAssert.class, JAssert::new);
        CONVERTER.put(CtComment.class, JComment::new);
        CONVERTER.put(CtBlock.class, JBody::new);
        CONVERTER.put(CtThrow.class, JThrow::new);
        CONVERTER.put(CtSwitch.class, JSwitch::new);
        CONVERTER.put(CtCase.class, JCase::new);
        CONVERTER.put(CtBreak.class, JBreak::new);
        CONVERTER.put(CtContinue.class, JContinue::new);
        CONVERTER.put(CtStatement.class, CtStatement2AStatement::defaultFactory);
    }

    public static AStatement<?> defaultFactory(CtStatement element, JWeaver weaver) {
        return new JStatement<>(element, weaver);
    }

    // Package protected so only CtElement2JoinPoint can use this method
    public static AJoinpoint<?> convert(CtStatement element, JWeaver weaver) {
        return CONVERTER.apply(element, weaver);
    }
}
