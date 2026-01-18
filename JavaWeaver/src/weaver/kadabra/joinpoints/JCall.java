/**
 * Copyright 2015 SPeCS.
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
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.abstracts.joinpoints.*;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.utils.SpoonUtils;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.converters.CtElement2JoinPoint;
import weaver.utils.weaving.converters.CtType2AType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JCall<T> extends ACall {

    private final CtInvocation<T> node;

    private JCall(CtInvocation<T> call) {
        super(new JExpression<>(call));
        node = call;
    }

    public static <T> JCall<T> newInstance(CtInvocation<T> call) {
        return new JCall<>(call);
    }

    /**
     * If this Call is the call of a CallStatement, return CallStatement.
     */
    @Override
    public AJoinPoint getParentImpl() {
        if (SpoonUtils.isStatementInBlock(node)) {
            return new JCallStatement<>(node);
        }

        return super.getParentImpl();
    }

    // @Override
    // public List<? extends ABinaryExpression> selectBinaryExpr() {
    // List<JBinaryExpression<?>> select = SelectUtils.select(node, CtBinaryOperator.class,
    // JBinaryExpression::newInstance);
    // return select;
    // }

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
    public String getSimpleDeclImpl() {
        var qualifiedDecl = getQualifiedDeclImpl();
        var index = qualifiedDecl.lastIndexOf(".");
        return index == -1 ? qualifiedDecl : qualifiedDecl.substring(index + 1);
    }

    @Override
    public String getExecutableImpl() {
        return node.getExecutable().getSimpleName();
    }

    @Override
    public String getTargetImpl() {
        var target = node.getTarget();
        return target != null ? String.valueOf(target) : null;
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
        var returnType = getReturnTypeJpImpl();
        return returnType != null ? returnType.toString() : null;
        // return getReturnTypeJpImpl().getCodeImpl();
        // return node.getType().toString();
    }

    @Override
    public ATypeReference getReturnTypeJpImpl() {
        var executable = node.getExecutable();
        Objects.requireNonNull(executable, () -> "Call should have an executable");

        var declaringType = executable.getDeclaringType();

        if (declaringType == null) {
            return null;
        }

        return (ATypeReference) CtElement2JoinPoint.convert(declaringType);
    }

    @Override
    public void setArgumentsImpl(AExpression[] newArguments) {
        var newArgs = new ArrayList<CtExpression<?>>();
        for (var arg : newArguments) {
            newArgs.add((CtExpression<?>) arg.getNode());
        }

        node.setArguments(newArgs);
    }

    @Override
    public void setArgumentImpl(AExpression newArgument, Integer index) {
        var previousArgs = new ArrayList<>(getNode().getArguments());

        if (index < 0 || index >= previousArgs.size()) {
            SpecsLogs.info("Trying to set argument with index '" + index + "', but call only has " + previousArgs.size()
                    + " arguments");
            return;
        }

        var newArgs = new ArrayList<CtExpression<?>>(previousArgs);
        newArgs.set(index, (CtExpression) newArgument.getNode());
        node.setArguments(newArgs);
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
    public ACall setTargetImpl(String value) {
        CtCodeSnippetExpression<Object> newTarget = SnippetFactory.snippetExpression(value.toString(),
                node.getFactory());
        node.setTarget(newTarget);
        return this;
    }

    @Override
    public ACall setTargetImpl(AExpression value) {
        return setTargetImpl(value.getSrcCodeImpl());
    }

    @Override
    public ACall setExecutableImpl(AMethod executable) {
        @SuppressWarnings("unchecked")
        JMethod<T> method = JMethod.class.cast(executable);
        CtExecutableReference<T> reference = method.getNode().getReference();
        node.setExecutable(reference);
        return this;
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
        ActionUtils.insert(position, cloned, stat.getNode());
        return JCall.newInstance(cloned);
    }

    @Override
    public AExpression[] getArgumentsArrayImpl() {
        final List<AExpression> exprs = SelectUtils.nodeList2JoinPointList(node.getArguments(),
                arg -> JExpression.newInstance(arg));
        return exprs.toArray(size -> new AExpression[size]);
    }

    @Override
    public AMethod getDeclImpl() {
        var decl = node.getExecutable().getExecutableDeclaration();
        if (decl == null || node.getExecutable().isConstructor()) {
            return null;
        }
        return CtElement2JoinPoint.convert(decl, AMethod.class);
    }
}
