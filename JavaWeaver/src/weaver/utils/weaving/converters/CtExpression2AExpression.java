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
import spoon.reflect.code.CtArrayAccess;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtThisAccess;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtAnnotation;
import spoon.support.reflect.code.CtCodeSnippetExpressionImpl;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.joinpoints.JAnnotation;
import weaver.kadabra.joinpoints.JArrayAccess;
import weaver.kadabra.joinpoints.JBinaryExpression;
import weaver.kadabra.joinpoints.JCall;
import weaver.kadabra.joinpoints.JCallStatement;
import weaver.kadabra.joinpoints.JExpression;
import weaver.kadabra.joinpoints.JFieldAccess;
import weaver.kadabra.joinpoints.JLiteral;
import weaver.kadabra.joinpoints.JNew;
import weaver.kadabra.joinpoints.JSnippetExpr;
import weaver.kadabra.joinpoints.JTernary;
import weaver.kadabra.joinpoints.JThis;
import weaver.kadabra.joinpoints.JUnaryExpression;
import weaver.kadabra.joinpoints.JVar;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetExpression;
import weaver.utils.SpoonUtils;

/**
 * Converts a given expression to the correct Join point type
 *
 * @author tiago
 *
 */
public class CtExpression2AExpression {
    private static final BiFunctionClassMap<CtExpression<?>, JavaWeaver, AExpression> CONVERTER = new BiFunctionClassMap<>();

    static {

        CONVERTER.put(CtBinaryOperator.class, JBinaryExpression::newInstance);
        CONVERTER.put(CtUnaryOperator.class, JUnaryExpression::newInstance);
        CONVERTER.put(CtFieldAccess.class, JFieldAccess::newInstance);
        CONVERTER.put(CtVariableAccess.class, JVar::newInstance);
        CONVERTER.put(CtArrayAccess.class, JArrayAccess::newInstance);
        CONVERTER.put(CtLiteral.class, JLiteral::newInstance);
        CONVERTER.put(CtConstructorCall.class, JNew::newInstance);
        CONVERTER.put(CtConditional.class, JTernary::newInstance);
        CONVERTER.put(CtAnnotation.class, JAnnotation::new);
        CONVERTER.put(CtThisAccess.class, JThis::new);
        CONVERTER.put(CtCodeSnippetExpressionImpl.class,
                (ctElement, weaver) -> JSnippetExpr.newInstance(new CtKadabraSnippetExpression<Object>(ctElement),
                        weaver));
        CONVERTER.put(CtExpression.class, CtExpression2AExpression::defaultFactory);
    }

    public static AExpression defaultFactory(CtExpression<?> element, JavaWeaver weaver) {
        return JExpression.newInstanceDefault(element, weaver);
    }

    private static final BiFunctionClassMap<CtExpression<?>, JavaWeaver, AExpression> CONVERTER_EXPR = new BiFunctionClassMap<>(
            CONVERTER);

    static {
        CONVERTER_EXPR.put(CtInvocation.class, JCall::newInstance);
    }

    private static final BiFunctionClassMap<CtExpression<?>, JavaWeaver, AJavaWeaverJoinPoint> CONVERTER_GENERAL = new BiFunctionClassMap<>(
            CONVERTER);

    static {
        CONVERTER_GENERAL.put(CtInvocation.class, CtExpression2AExpression::ctInvokation);
        // CONVERTER_GENERAL.put(CtInvocation.class, JCall::newInstance);
    }

    // Package protected so only CtElement2JoinPoint can use this method
    public static AExpression convertToExpression(CtExpression<?> element, JavaWeaver weaver) {
        return CONVERTER_EXPR.apply(element, weaver);
    }

    /**
     * Converts the element CtExpression a join point, it does not always return an
     * AExpression (e.g., super(); is a AStatement)
     * 
     * @param element
     * @return
     */
    public static AJavaWeaverJoinPoint convert(CtExpression<?> element, JavaWeaver weaver) {
        return CONVERTER_GENERAL.apply(element, weaver);

    }

    public static <T> AJavaWeaverJoinPoint ctInvokation(CtInvocation<T> call, JavaWeaver weaver) {
        // Special case: if call is also a statement, return JCallStatement
        if (SpoonUtils.isStatementInBlock(call)) {
            return new JCallStatement<>(call, weaver);
        }

        return JCall.newInstance(call, weaver);
    }
}
