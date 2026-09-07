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

import java.util.Collections;

import org.lara.interpreter.weaver.interf.enums.InsertPosition;

import pt.up.fe.specs.util.SpecsLogs;
import spoon.refactoring.Refactoring;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtCompilationUnit;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AClass;
import weaver.kadabra.abstracts.joinpoints.AField;
import weaver.kadabra.abstracts.joinpoints.AInterfaceType;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.AMethod;
import weaver.kadabra.abstracts.joinpoints.AType;
import weaver.kadabra.abstracts.joinpoints.ATypeReference;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetElement;
import weaver.utils.SpoonUtils;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JType<Self extends JType<Self>> extends AType<Self> {

    private CtCompilationUnit parent;

    public JType(CtType node, JWeaver weaver) {
        this(node, node.getPosition().getCompilationUnit(), weaver);
    }

    public JType(CtType node, CtCompilationUnit parent, JWeaver weaver) {
        super(node, weaver);
        this.parent = parent;
    }

    @Override
    public AJoinpoint<?> copyImpl() {
        return CtElement2JoinPoint.convert(Refactoring.copyType((CtType) getNodeImpl()), getWeaverEngine());
    }

    @Override
    public String getSuperClassImpl() {

        final spoon.reflect.reference.CtTypeReference<?> superclass = getNodeImpl().getSuperclass();
        if (superclass == null) {
            return Object.class.getCanonicalName();
        }

        final String qualifiedName = superclass.getQualifiedName();
        return qualifiedName;
    }

    @Override
    public ATypeReference<?> getSuperClassJpImpl() {
        final spoon.reflect.reference.CtTypeReference<?> superclass = getNodeImpl().getSuperclass();
        if (superclass == null) {
            return null;
        }

        return (ATypeReference<?>) CtElement2JoinPoint.convert(superclass, getWeaverEngine());

    }

    @Override
    public String[] getInterfacesImpl() {

        final var els = getNodeImpl().getSuperInterfaces().stream()
                .map(el -> el.getQualifiedName())
                .collect(java.util.stream.Collectors.toList());
        return els.toArray(new String[0]);
    }

    @Override
    public AInterfaceType<?>[] getInterfacesTypesImpl() {
        var els = getNodeImpl().getSuperInterfaces().stream()
                .map(el -> CtElement2JoinPoint.convert(el,
                        getWeaverEngine(), AInterfaceType.class))
                .collect(java.util.stream.Collectors.toList());

        return els.toArray(new AInterfaceType[0]);
    }

    @Override
    public String getNameImpl() {
        return getNodeImpl().getSimpleName();
    }

    @Override
    public String getQualifiedNameImpl() {
        return getNodeImpl().getQualifiedName();
    }

    @Override
    public String getPackageNameImpl() {

        CtPackage package1 = getNodeImpl().getPackage();
        if (package1 == null) {
            return "";
        }
        return package1.getQualifiedName();
    }

    @Override
    public void addInterfaceImpl(AInterfaceType<?> newInterface) {
        getNodeImpl().addNestedType((CtType<?>) newInterface.getNodeImpl());
    }

    @Override
    public AInterfaceType<?> removeInterfaceImpl(String interfaceName) {

        var interfaceNode = getNodeImpl().getSuperInterfaces().stream()
                .filter(si -> si.getQualifiedName().equals(interfaceName))
                .findFirst()
                .orElse(null);

        if (interfaceNode == null) {
            SpecsLogs.info("removeInterface: could not find interface with name '" + interfaceName + "'");
            return null;
        }

        var success = getNodeImpl().removeSuperInterface(interfaceNode);

        if (!success) {
            SpecsLogs.info("removeInterface: could not remove interface " + interfaceName);
            return null;
        }

        var interfaceType = interfaceNode.getTypeDeclaration();
        if (interfaceType == null) {
            SpecsLogs.debug("removeInterface: could not convert CtTypeReference to CtType for class "
                    + interfaceNode.getQualifiedName()
                    + " (most likely reason is class not being present in the classpath)");
            return null;
        }

        return CtElement2JoinPoint.convert(interfaceNode.getTypeDeclaration(), getWeaverEngine(), AInterfaceType.class);
    }

    @Override
    public void addClassImpl(AClass<?> newClass) {
        getNodeImpl().addNestedType((CtType<?>) newClass.getNodeImpl());
    }

    @Override
    public void addImplementImpl(AInterfaceType<?> _interface) {

        CtType<?> type = (CtType<?>) _interface.getNodeImpl();
        getNodeImpl().addSuperInterface(type.getReference());
    }

    @Override
    public AMethod<?> newMethodImpl(String[] modifiers, String returnType, String name, String[] paramLeft,
            String[] paramRight, String code) {
        spoon.reflect.declaration.CtMethod<?> newMethod = ActionUtils.newMethod(getNodeImpl(), name, returnType,
                paramLeft, paramRight, modifiers, code);
        return new JMethod<>(newMethod, getWeaverEngine());
    }

    @Override
    public AMethod<?> newMethodImpl(String[] modifiers, String returnType, String name, String[] paramLeft,
            String[] paramRight) {
        return newMethodImpl(modifiers, returnType, name, paramLeft, paramRight, "");
    }

    @Override
    public void insertMethodImpl(String code) {
        insertCodeImpl(code);
    }

    @Override
    public void insertCodeImpl(String code) {
        CtKadabraSnippetElement method = SnippetFactory.createSnippetElement(getNodeImpl().getFactory(), code);
        getNodeImpl().addNestedType(method);
    }

    @Override
    public AField<?> newFieldImpl(String[] modifiers, String fieldType, String baseName, String initialValue) {
        spoon.reflect.declaration.CtField<Object> newField = ActionUtils.newField(getNodeImpl(), baseName, fieldType,
                initialValue, modifiers);
        return new JField<>(newField, getWeaverEngine());
    }

    @Override
    public AField<?> newFieldImpl(String[] modifiers, String fieldType, String baseName) {
        return newFieldImpl(modifiers, fieldType, baseName, null);
    }

    @Override
    public String getJavadocImpl() {
        String docComment = getNodeImpl().getDocComment();

        return docComment != null ? docComment : "";
    }

    public static ModifierKind getModifier(String value) {

        try {
            return ModifierKind.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new JavaWeaverException("There is no modifier named " + value);
        }
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, AJoinpoint<?> code) {
        return new AJoinpoint<?>[] { insertImplJType(position, (CtElement) code.getNodeImpl()) };
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, String code) {
        return new AJoinpoint<?>[] { insertImplJType(position, code) };
    }

    public AJoinpoint<?> insertImplJType(InsertPosition position, CtElement code) {
        return ActionUtils.insertMember(getNodeImpl(), code, position.name().toLowerCase(), getWeaverEngine());
    }

    public AJoinpoint<?> insertImplJType(InsertPosition position, String code) {
        return ActionUtils.insertMember(getNodeImpl(), code, position.name().toLowerCase(), getWeaverEngine());
    }

    @Override
    public AJoinpoint<?> insertBeforeImpl(String code) {
        return insertImplJType(InsertPosition.BEFORE, code);
    }

    @Override
    public AJoinpoint<?> insertAfterImpl(String code) {
        return insertImplJType(InsertPosition.AFTER, code);
    }

    @Override
    public AJoinpoint<?> insertReplaceImpl(String code) {
        return insertImplJType(InsertPosition.REPLACE, code);
    }

    @Override
    public CtType<?> getNodeImpl() {
        return (CtType<?>) super.getNodeImpl();
    }

    @Override
    public Boolean getIsSubtypeOfImpl(String type) {

        return getNodeImpl().isSubtypeOf(SpoonUtils.newCtTypeReference(type, getNodeImpl().getFactory(),
                Collections.emptyList()));

    }

    @Override
    public AJoinpoint<?> getParentImpl() {
        var spoonParent = getNodeImpl().getParent();

        if (spoonParent != null && !(spoonParent instanceof CtPackage)) {
            return CtElement2JoinPoint.convert(spoonParent, getWeaverEngine());
        }

        return CtElement2JoinPoint.convert(parent, getWeaverEngine());
    }

}
