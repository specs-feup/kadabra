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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pt.up.fe.specs.util.SpecsLogs;

import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ACall;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.AMethod;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.kadabra.abstracts.joinpoints.AType;
import weaver.kadabra.abstracts.joinpoints.ATypeReference;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.utils.SpoonUtils;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.converters.CtElement2JoinPoint;
import weaver.utils.weaving.converters.CtType2AType;

public class JCall<Self extends JCall<Self>> extends ACall<Self> {

    public JCall(CtInvocation node, JWeaver weaver) {
        super(node, weaver);
    }

    /**
     * If this Call is the call of a CallStatement, return CallStatement.
     */
    @Override
    public AJoinpoint<?> getParentImpl() {
        if (SpoonUtils.isStatementInBlock(getNodeImpl())) {
            return new JCallStatement<>(getNodeImpl(), getWeaverEngine());
        }

        return super.getParentImpl();
    }

    @Override
    public String getNameImpl() {
        return getNodeImpl().getExecutable().getSimpleName();
    }

    @Override
    public String getDeclaratorImpl() {
        CtExecutableReference<?> executable = getNodeImpl().getExecutable();
        return executable.getDeclaringType().getSimpleName();
    }

    @Override
    public String getQualifiedDeclImpl() {
        String qualifiedName = getNodeImpl().getExecutable().getDeclaringType().getQualifiedName();
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
        return getNodeImpl().getExecutable().getSimpleName();
    }

    @Override
    public String getTargetImpl() {
        var target = getNodeImpl().getTarget();
        return target != null ? String.valueOf(target) : null;
    }

    @Override
    public AType<?> getTargetTypeImpl() {
        CtTypeReference<?> type = getNodeImpl().getTarget().getType();
        CtType<?> typeDeclaration = type.getTypeDeclaration();
        return CtType2AType.convert(typeDeclaration, getWeaverEngine());
    }

    @Override
    public String getReturnTypeImpl() {
        var returnType = getReturnTypeJpImpl();
        return returnType != null ? returnType.toString() : null;
    }

    @Override
    public ATypeReference<?> getReturnTypeJpImpl() {
        var executable = getNodeImpl().getExecutable();
        Objects.requireNonNull(executable, () -> "Call should have an executable");

        var declaringType = executable.getDeclaringType();

        if (declaringType == null) {
            return null;
        }

        return (ATypeReference<?>) CtElement2JoinPoint.convert(declaringType, getWeaverEngine());
    }

    @Override
    public void setArgumentsImpl(AExpression<?>[] newArguments) {
        var newArgs = new ArrayList<CtExpression<?>>();
        for (var arg : newArguments) {
            newArgs.add((CtExpression<?>) arg.getNodeImpl());
        }

        getNodeImpl().setArguments(newArgs);
    }

    @Override
    public void setArgumentImpl(AExpression<?> newArgument, int index) {
        var previousArgs = new ArrayList<>(getNodeImpl().getArguments());

        if (index < 0 || index >= previousArgs.size()) {
            SpecsLogs.info("Trying to set argument with index '" + index + "', but call only has " + previousArgs.size()
                    + " arguments");
            return;
        }

        var newArgs = new ArrayList<CtExpression<?>>(previousArgs);
        newArgs.set(index, (CtExpression) newArgument.getNodeImpl());
        getNodeImpl().setArguments(newArgs);
    }

    @Override
    public CtInvocation<?> getNodeImpl() {
        return (CtInvocation<?>) super.getNodeImpl();
    }

    @Override
    public ACall<?> setTargetImpl(String value) {
        CtCodeSnippetExpression<?> newTarget = SnippetFactory.snippetExpression(value.toString(),
                getNodeImpl().getFactory());
        getNodeImpl().setTarget(newTarget);
        return this;
    }

    @Override
    public ACall<?> setTargetImpl(AExpression<?> value) {
        return setTargetImpl(value.getSrcCodeImpl());
    }

    @Override
    public ACall<?> setExecutableImpl(AMethod<?> executable) {
        var method = (JMethod<?>) executable;
        CtExecutableReference<?> reference = method.getNodeImpl().getReference();
        getNodeImpl().setExecutable((CtExecutableReference) reference);
        return this;
    }

    @Override
    public ACall<?> cloneImpl(AStatement<?> location, String position) {
        if (!(location instanceof JStatement<?>)) {
            throw new JavaWeaverException("During method call cloning",
                    new RuntimeException("Class " + location.getClass() + " is not acceptable here"));
        }

        JStatement<?> stat = (JStatement<?>) location;

        CtInvocation<?> cloned = ActionUtils.cloneElement(getNodeImpl());
        ActionUtils.insert(position, cloned, stat.getNodeImpl(), getWeaverEngine());
        return new JCall<>(cloned, getWeaverEngine());
    }

    @Override
    public AExpression<?>[] getArgumentsImpl() {
        final List<AExpression<?>> exprs = SelectUtils.nodeList2JoinPointList(getNodeImpl().getArguments(),
                arg -> JExpression.newInstance(arg, getWeaverEngine()));
        return exprs.toArray(size -> new AExpression[size]);
    }

    @Override
    public AMethod<?> getDeclImpl() {
        var decl = getNodeImpl().getExecutable().getExecutableDeclaration();
        if (decl == null || getNodeImpl().getExecutable().isConstructor()) {
            return null;
        }
        return CtElement2JoinPoint.convert(decl, getWeaverEngine(), AMethod.class);
    }
}
