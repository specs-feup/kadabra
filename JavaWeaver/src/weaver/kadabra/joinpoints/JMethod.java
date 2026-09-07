/**
 * Copyright 2015 SPeCS Research Group.
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

import java.util.Set;

import org.lara.interpreter.weaver.interf.enums.InsertPosition;

import spoon.refactoring.Refactoring;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.Factory;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AClass;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.AMethod;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetElement;
import weaver.utils.SpoonUtils;
import weaver.utils.generators.AdapterGenerator;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.TypeUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JMethod<Self extends JMethod<Self>> extends AMethod<Self> {

    public JMethod(CtMethod node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public AJoinpoint<?> copyImpl() {
        var methodName = getNameImpl();
        var copy = Refactoring.copyMethod((CtMethod) getNodeImpl());
        // Refactor method changes the name of the method, appends copy at the end.
        // Restore original name
        copy.setSimpleName(methodName);
        return CtElement2JoinPoint.convert(copy, getWeaverEngine());
    }

    @Override
    public String getToStringImpl() {
        return getNameImpl();
    }

    @Override
    public CtMethod<?> getNodeImpl() {
        return (CtMethod<?>) super.getNodeImpl();
    }

    @Override
    public String getToReferenceImpl() {

        return getNodeImpl().getDeclaringType().getSimpleName() + "::" + getNodeImpl().getSimpleName();
    }

    @Override
    public String getToQualifiedReferenceImpl() {
        return getNodeImpl().getDeclaringType().getQualifiedName() + "::" + getNodeImpl().getSimpleName();
    }

    @Override
    public AMethod<?> cloneImpl(String newName) {

        CtMethod<?> clone = ActionUtils.cloneElement(getNodeImpl());
        CtType<?> ancestor = SpoonUtils.getAncestor(getNodeImpl(), CtType.class);
        int inc = 0;
        while (!ancestor.getMethodsByName(newName).isEmpty()) {
            newName = newName + (inc++);
        }
        clone.setSimpleName(newName);
        ancestor.addMethod(clone);
        return new JMethod<>(clone, getWeaverEngine());
    }

    @Override
    public void addCommentImpl(String comment) {
        String docComment = getNodeImpl().getDocComment();
        if (docComment == null || docComment.isEmpty()) {
            docComment = comment;
        } else {
            docComment += "\n" + comment;
        }
        getNodeImpl().setDocComment(docComment);
    }

    @Override
    public void addParameterImpl(String type, String name) {
        Factory factory = getNodeImpl().getFactory();
        CtParameter<Object> parameter = factory.Core().createParameter();
        parameter.setType(TypeUtils.typeOf(type, factory));
        parameter.setSimpleName(name);
        getNodeImpl().addParameter(parameter);
    }

    @Override
    public AClass<?> createAdapterImpl(AMethod<?> adaptMethod, String name) {
        return createAdapterImpl(adaptMethod, name, false);
    }

    public AClass<?> createAdapterImpl(AMethod<?> adaptMethod, String name, boolean reuseIfExists) {
        JMethod<?> jMethod = (JMethod<?>) adaptMethod;
        CtMethod<?> adaptMethodNode = jMethod.getNodeImpl();
        JClass<?> jClass = AdapterGenerator.generate(getWeaverEngine(), name, adaptMethodNode, getNodeImpl(),
                reuseIfExists);
        return jClass;
    }

    @Override
    public String getDeclaratorImpl() {
        return getNodeImpl().getDeclaringType().getQualifiedName();
    }

    @Override
    public String getPrivacyImpl() {
        Set<ModifierKind> modifiers = getNodeImpl().getModifiers();
        if (modifiers.contains(ModifierKind.PRIVATE)) {
            return "private";
        }
        if (modifiers.contains(ModifierKind.PUBLIC)) {
            return "public";
        }
        if (modifiers.contains(ModifierKind.PROTECTED)) {
            return "protected";
        }
        return "";
    }

    @Override
    public void setPrivacyImpl(String value) {
        getNodeImpl().addModifier(ModifierKind.valueOf(value.toString()));
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, String code) {
        return new AJoinpoint<?>[] { insertImplJMethod(position, code) };
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, AJoinpoint<?> code) {
        return new AJoinpoint<?>[] { insertImplJMethod(position, code) };
    }

    public AJoinpoint<?> insertImplJMethod(InsertPosition position, String code) {
        Factory factory = getNodeImpl().getFactory();
        CtKadabraSnippetElement snippet = SnippetFactory.createSnippetElement(factory, code);

        return ActionUtils.insertMember(getNodeImpl(), snippet, position.name().toLowerCase(), getWeaverEngine());
    }

    public AJoinpoint<?> insertImplJMethod(InsertPosition position, AJoinpoint<?> code) {
        return ActionUtils.insertMember(getNodeImpl(), code.getNodeImpl(), position.name().toLowerCase(),
                getWeaverEngine());
    }

    @Override
    public AJoinpoint<?> insertAfterImpl(String code) {
        return insertImplJMethod(InsertPosition.AFTER, code);
    }

    @Override
    public AJoinpoint<?> insertBeforeImpl(String code) {
        return insertImplJMethod(InsertPosition.BEFORE, code);
    }

    @Override
    public AJoinpoint<?> insertReplaceImpl(String code) {
        return insertImplJMethod(InsertPosition.REPLACE, code);
    }

    @Override
    public AJoinpoint<?> insertReplaceImpl(AJoinpoint<?> jp) {
        return insertImplJMethod(InsertPosition.REPLACE, jp);
    }

    @Override
    public boolean getIsOverridingImpl(AMethod<?> method) {
        return getNodeImpl().isOverriding((CtMethod<?>) method.getNodeImpl());
    }
}
