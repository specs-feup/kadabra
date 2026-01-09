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
 * specific language governing permissions and limitations under the License. under the License.
 */

package weaver.utils.weaving.converters;

import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtAnnotation;
import spoon.support.reflect.code.CtCodeSnippetExpressionImpl;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.joinpoints.*;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetExpression;
import weaver.utils.SpoonUtils;

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
        // CONVERTER.put(CtInvocation.class, JCall::newInstance);
        // CONVERTER.put(CtInvocation.class, CtExpression2AExpression::ctInvokation);
        CONVERTER.put(CtFieldAccess.class, JFieldAccess::newInstance);
        CONVERTER.put(CtVariableAccess.class, JVar::newInstance);
        CONVERTER.put(CtArrayAccess.class, JArrayAccess::newInstance);
        CONVERTER.put(CtLiteral.class, JLiteral::newInstance);
        CONVERTER.put(CtConstructorCall.class, JNew::newInstance);
        CONVERTER.put(CtConditional.class, JTernary::newInstance);
        CONVERTER.put(CtAnnotation.class, JAnnotation::new);
        CONVERTER.put(CtThisAccess.class, JThis::new);
        CONVERTER.put(CtCodeSnippetExpressionImpl.class,
                ctElement -> JSnippetExpr.newInstance(new CtKadabraSnippetExpression<Object>(ctElement)));

        // CONVERTER.put(CtAssignment.class, JAssignment::newInstance);

        // CONVERTER_EXPR.putAll(CONVERTER);
    }

    private static final FunctionClassMap<CtExpression<?>, AExpression> CONVERTER_EXPR = new FunctionClassMap<>(
            CONVERTER);

    static {
        CONVERTER_EXPR.put(CtInvocation.class, JCall::newInstance);
    }

    private static final FunctionClassMap<CtExpression<?>, AJavaWeaverJoinPoint> CONVERTER_GENERAL = new FunctionClassMap<>(
            CONVERTER);

    static {
        CONVERTER_GENERAL.put(CtInvocation.class, CtExpression2AExpression::ctInvokation);
        //CONVERTER_GENERAL.put(CtInvocation.class, JCall::newInstance);
    }

    // Package protected so only CtElement2JoinPoint can use this method
    public static AExpression convertToExpression(CtExpression<?> element) {
        return CONVERTER_EXPR.apply(element);

    }

    /**
     * Converts the element CtExpression a join point, it does not always return an AExpression (e.g., super(); is a
     * AStatement)
     *
     * @param element
     * @return
     */
    public static AJavaWeaverJoinPoint convert(CtExpression<?> element) {
        return CONVERTER_GENERAL.apply(element);

    }

    // public static AExpression convert(CtExpression<?> element) {
    // return CONVERTER.apply(element);
    //
    // }

    public static <T> AJavaWeaverJoinPoint ctInvokation(CtInvocation<T> call) {

        // Special case: if call is also a statement, return JCallStatement
        if (SpoonUtils.isStatementInBlock(call)) {
            return new JCallStatement<>(call);
        }

        return JCall.newInstance(call);
    }
}
