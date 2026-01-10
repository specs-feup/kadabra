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

import org.lara.interpreter.weaver.interf.JoinPoint;
import pt.up.fe.specs.util.SpecsLogs;
import spoon.refactoring.Refactoring;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.*;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetElement;
import weaver.utils.SpoonUtils;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.converters.CtElement2JoinPoint;
import java.util.*;
import java.util.stream.Collectors;

public class JType<T> extends AType {

    private CtType<T> node;
    private CtCompilationUnit parent;

    private JType(CtType<T> node, CtCompilationUnit parent, JavaWeaver weaver) {
        super(weaver);
        this.node = node;
        this.parent = parent;
    }

    /**
     * @param <T>
     * @param node
     * @param parent
     * @return
     */
    public static <T> JType<T> newInstance(CtType<T> node, CtCompilationUnit parent, JavaWeaver weaver) {
        return new JType<>(node, parent, weaver);
    }

    @Override
    public AJoinPoint copyImpl() {
        return CtElement2JoinPoint.convert(Refactoring.copyType(node), getWeaverEngine());
    }

    @Override
    public String getSuperClassImpl() {

        final CtTypeReference<?> superclass = node.getSuperclass();
        if (superclass == null) {
            return Object.class.getCanonicalName();
        }

        final String qualifiedName = superclass.getQualifiedName();
        return qualifiedName;
    }

    @Override
    public ATypeReference getSuperClassJpImpl() {
        final CtTypeReference<?> superclass = node.getSuperclass();
        if (superclass == null) {
            return null;
        }

        return (ATypeReference) CtElement2JoinPoint.convert(superclass, getWeaverEngine());

    }

    @Override
    public String[] getInterfacesArrayImpl() {

        final List<String> els = node.getSuperInterfaces().stream()
                .map(el -> el.getQualifiedName())
                .collect(Collectors.toList());
        return els.toArray(new String[0]);
    }

    @Override
    public AInterfaceType[] getInterfacesTypesArrayImpl() {
        var els = node.getSuperInterfaces().stream()
                .map(el -> CtElement2JoinPoint.convert(el,
                        getWeaverEngine(), AInterfaceType.class))
                .collect(Collectors.toList());

        return els.toArray(new AInterfaceType[0]);
    }

    @Override
    public String getNameImpl() {
        return node.getSimpleName();
    }

    @Override
    public String getQualifiedNameImpl() {
        return node.getQualifiedName();
    }

    @Override
    public String getPackageNameImpl() {

        CtPackage package1 = node.getPackage();
        if (package1 == null) {
            return "";
        }
        return package1.getQualifiedName();
    }

    @Override
    public void addInterfaceImpl(AInterfaceType newInterface) {
        node.addNestedType((CtType<?>) newInterface.getNode());
    }

    @Override
    public AInterfaceType removeInterfaceImpl(String interfaceName) {

        var interfaceNode = node.getSuperInterfaces().stream()
                .filter(si -> si.getQualifiedName().equals(interfaceName))
                .findFirst()
                .orElse(null);

        if (interfaceNode == null) {
            SpecsLogs.info("removeInterface: could not find interface with name '" + interfaceName + "'");
            return null;
        }

        var success = node.removeSuperInterface(interfaceNode);

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
    public void addClassImpl(AClass newClass) {
        node.addNestedType((CtType<?>) newClass.getNode());
    }

    @Override
    public void addImplementImpl(AInterfaceType _interface) {

        CtType<?> type = (CtType<?>) _interface.getNode();
        node.addSuperInterface(type.getReference());
    }

    @Override
    public AMethod newMethodImpl(String[] modifiers, String returnType, String name, String[] paramLeft,
            String[] paramRight, String code) {
        CtMethod<?> newMethod = ActionUtils.newMethod(node, name, returnType, paramLeft, paramRight, modifiers, code);
        JMethod<?> newInstance = JMethod.newInstance(newMethod, getWeaverEngine());
        return newInstance;
    }

    @Override
    public AMethod newMethodImpl(String[] modifiers, String returnType, String name, String[] paramLeft,
            String[] paramRight) {
        return newMethodImpl(modifiers, returnType, name, paramLeft, paramRight, "");
    }

    @Override
    public void insertMethodImpl(String code) {
        insertCodeImpl(code);
    }

    @Override
    public void insertCodeImpl(String code) {
        CtKadabraSnippetElement method = SnippetFactory.createSnippetElement(node.getFactory(), code);
        node.addNestedType(method);
    }

    @Override
    public AField newFieldImpl(String[] modifiers, String fieldType, String baseName, String initialValue) {
        CtField<Object> newField = ActionUtils.newField(node, baseName, fieldType, initialValue, modifiers);
        JField<Object> newInstance = JField.newInstance(newField, getWeaverEngine());
        return newInstance;
    }

    @Override
    public AField newFieldImpl(String[] modifiers, String fieldType, String baseName) {
        return newFieldImpl(modifiers, fieldType, baseName, null);
    }

    @Override
    public String getJavadocImpl() {
        String docComment = node.getDocComment();

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
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return new AJoinPoint[] { insertImplJType(position, (CtElement) code.getNode()) };
    }

    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return new AJoinPoint[] { insertImplJType(position, code) };
    }

    public AJavaWeaverJoinPoint insertImplJType(String position, CtElement code) {
        return ActionUtils.insertMember(node, code, position, getWeaverEngine());
    }

    public AJavaWeaverJoinPoint insertImplJType(String position, String code) {
        return ActionUtils.insertMember(node, code, position, getWeaverEngine());
    }

    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return insertImplJType("before", code);
    }

    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return insertImplJType("after", code);
    }

    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return insertImplJType("replace", code);
    }

    @Override
    public CtType<T> getNode() {
        return node;
    }

    @Override
    public Boolean isSubtypeOfImpl(String type) {

        return node.isSubtypeOf(SpoonUtils.newCtTypeReference(type, node.getFactory(), Collections.emptyList()));

    }

    @Override
    public AJoinPoint getParentImpl() {
        var spoonParent = getNode().getParent();

        if (spoonParent != null && !(spoonParent instanceof CtPackage)) {
            return CtElement2JoinPoint.convert(spoonParent, getWeaverEngine());
        }

        return CtElement2JoinPoint.convert(parent, getWeaverEngine());
    }

}
