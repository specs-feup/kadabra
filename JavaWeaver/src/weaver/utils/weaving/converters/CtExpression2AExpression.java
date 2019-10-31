/**
 * Copyright 2017 SPeCS.
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

package weaver.utils.weaving.converters;

import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.code.CtArrayAccess;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtVariableAccess;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.joinpoints.JArrayAccess;
import weaver.kadabra.joinpoints.JBinaryExpression;
import weaver.kadabra.joinpoints.JCall;
import weaver.kadabra.joinpoints.JExpression;
import weaver.kadabra.joinpoints.JFieldAccess;
import weaver.kadabra.joinpoints.JLiteral;
import weaver.kadabra.joinpoints.JUnaryExpression;
import weaver.kadabra.joinpoints.JVar;

/**
 * Converts a given expression to the correct Join point type
 * 
 * @author tiago
 *
 */
public class CtExpression2AExpression {
    private static final FunctionClassMap<CtExpression<?>, AExpression> CONVERTER = new FunctionClassMap<>(
            JExpression::newInstanceDefault);

    static {

        CONVERTER.put(CtBinaryOperator.class, JBinaryExpression::newInstance);
        CONVERTER.put(CtUnaryOperator.class, JUnaryExpression::newInstance);
        CONVERTER.put(CtInvocation.class, JCall::newInstance);
        CONVERTER.put(CtVariableAccess.class, JVar::newInstance);
        CONVERTER.put(CtFieldAccess.class, JFieldAccess::newInstance);
        CONVERTER.put(CtArrayAccess.class, JArrayAccess::newInstance);
        CONVERTER.put(CtLiteral.class, JLiteral::newInstance);

        // CONVERTER.put(CtAssignment.class, JAssignment::newInstance);

    }

    // Package protected so only CtElement2JoinPoint can use this method
    public static AExpression convert(CtExpression<?> element) {
        return CONVERTER.apply(element);

    }
}
