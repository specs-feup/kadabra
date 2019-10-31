/**
 * Copyright 2015 SPeCS.
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

import java.util.List;

import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.abstracts.joinpoints.AArgument;
import weaver.kadabra.abstracts.joinpoints.ABinaryExpression;
import weaver.kadabra.abstracts.joinpoints.ACall;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AMethod;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.kadabra.abstracts.joinpoints.AType;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.converters.CtType2AType;

public class JCall<T> extends ACall {

    private final CtInvocation<T> node;

    private JCall(CtInvocation<T> call) {
        super(new JExpression<>(call));
        node = call;
    }

    public static <T> JCall<T> newInstance(CtInvocation<T> call) {
        return new JCall<>(call);
    }

    @Override
    public List<? extends ABinaryExpression> selectBinaryExpr() {
        List<JBinaryExpression<?>> select = SelectUtils.select(node, CtBinaryOperator.class,
                JBinaryExpression::newInstance);
        return select;
    }

    @Override
    public String getNameImpl() {
        return node.getExecutable().getSimpleName();
    }

    @Override
    public String getDeclaratorImpl() {
        CtExecutableReference<T> executable = node.getExecutable();
        return executable.getDeclaringType().getSimpleName();
    }

    @Override
    public String getQualifiedDeclImpl() {
        String qualifiedName = node.getExecutable().getDeclaringType().getQualifiedName();
        return qualifiedName;
    }

    @Override
    public String getExecutableImpl() {
        return node.getExecutable().getSimpleName();
    }

    @Override
    public String getTargetImpl() {
        return String.valueOf(node.getTarget());
    }

    @Override
    public AType getTargetTypeImpl() {
        // return String.valueOf(node.getTarget().getType().getQualifiedName());
        CtTypeReference<?> type = node.getTarget().getType();
        CtType<?> typeDeclaration = type.getTypeDeclaration();
        return CtType2AType.convert(typeDeclaration);
    }

    @Override
    public String getReturnTypeImpl() {
        return node.getType().toString();
    }

    @Override
    public List<? extends AArgument> selectArg() {
        final List<AArgument> exprs = SelectUtils.nodeList2JoinPointList(node.getArguments(),
                arg -> JArgument.newInstance(arg));
        return exprs;
    }

    @Override
    public CtInvocation<T> getNode() {
        return node;
    }

    // @Override
    // public void insertImpl(String position, String code) {
    // ActionUtils.insert(position, code, node, getWeaverProfiler());
    // }

    @Override
    public void defTargetImpl(String value) {
        CtCodeSnippetExpression<Object> newTarget = SnippetFactory.snippetExpression(value.toString(),
                node.getFactory());
        node.setTarget(newTarget);
    }

    @Override
    public void defTargetImpl(AExpression value) {
        defTargetImpl(value.getSrcCodeImpl());
    }

    @Override
    public void defExecutableImpl(AMethod executable) {
        @SuppressWarnings("unchecked")
        JMethod<T> method = JMethod.class.cast(executable);
        CtExecutableReference<T> reference = method.getNode().getReference();
        node.setExecutable(reference);
    }

    //
    // public void defImpl2(String attributeStr, Object value) {
    //
    // Optional<CallAttributes> attribute = getTargetAttribute(attributeStr);
    //
    // switch (attribute.get()) {
    // case TARGET:
    // // System.out.println("Defining target with " + value);
    // CtCodeSnippetExpression<Object> newTarget = SnippetFactory.snippetExpression(value.toString(),
    // node.getFactory());
    // node.setTarget(newTarget);
    // break;
    // case EXECUTABLE:
    // if (!(value instanceof JMethod)) {
    // throw new JavaWeaverException(
    // "Definition of attribute " + attributeStr + " expects a join point method");
    // }
    //
    // @SuppressWarnings("unchecked")
    // JMethod<T> method = JMethod.class.cast(value);
    // CtExecutableReference<T> reference = method.getNode().getReference();
    // node.setExecutable(reference);
    // break;
    // default:
    // throw new JavaWeaverException("Cannot define attribute " + attributeStr + " of join point call");
    // }
    //
    // }
    //
    // private static Optional<CallAttributes> getTargetAttribute(String attributeStr) {
    // Optional<CallAttributes> attribute = CallAttributes.fromString(attributeStr);
    // if (!attribute.isPresent()) {
    // throw new JavaWeaverException("The join point call does not contain any attribute named " + attributeStr);
    // }
    // return attribute;
    // }

    @Override
    public ACall cloneImpl(AStatement location, String position) {
        if (!(location instanceof JStatement)) {
            throw new JavaWeaverException("During method call cloning",
                    new RuntimeException("Class " + location.getClass() + " is not acceptable here"));
        }

        JStatement stat = (JStatement) location;

        CtInvocation<T> cloned = ActionUtils.cloneElement(node);
        ActionUtils.insert(position, cloned, stat.getNode(), getWeaverProfiler());
        return JCall.newInstance(cloned);
    }
}
