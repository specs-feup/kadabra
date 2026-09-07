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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtCompilationUnit;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AClass;
import weaver.kadabra.abstracts.joinpoints.AConstructor;
import weaver.kadabra.abstracts.joinpoints.AInterfaceType;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.AMethod;
import weaver.utils.generators.FunctionalClassGenerator;
import weaver.utils.generators.MapGenerator;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JClass<Self extends JClass<Self>> extends AClass<Self> {

    private final CtCompilationUnit parent;

    public JClass(CtClass node, JWeaver weaver) {
        this(node, node.getPosition().getCompilationUnit(), weaver);
    }

    public JClass(CtClass node, CtCompilationUnit parent, JWeaver weaver) {
        super(node, weaver);
        this.parent = parent;
    }

    @Override
    public String getToStringImpl() {
        return getNodeImpl().getQualifiedName();
    }

    @Override
    public CtClass<?> getNodeImpl() {
        return (CtClass<?>) super.getNodeImpl();
    }

    @Override
    public void insertStaticImpl(String code) {

        Factory factory = getNodeImpl().getFactory();
        CtCodeSnippetStatement snippetStmt = SnippetFactory.createSnippetStatement(code, factory);
        CtBlock<Void> newBlock = factory.Core().createBlock();
        newBlock.addStatement(snippetStmt);
        CtAnonymousExecutable createAnonymous = factory.Method().createAnonymous(getNodeImpl(), newBlock);
        createAnonymous.addModifier(ModifierKind.STATIC);
    }

    @Override
    public AClass<?> mapVersionsImpl(String name, String keyType, AInterfaceType<?> _interface, String methodName) {

        CtClass<?> generate = MapGenerator.generate(getNodeImpl().getFactory(), name, keyType, _interface, methodName);
        getNodeImpl().addNestedType(generate);
        JClass<?> jClass = new JClass<>(generate, parent, getWeaverEngine());
        return jClass;
    }

    @Override
    public AInterfaceType<?> extractInterfaceImpl(String name, String _package, AMethod<?> method, boolean associate,
            boolean newFile) {

        Factory factory = getNodeImpl().getFactory();
        // First create the interface

        Collection<CompilationUnit> compilationUnits = factory.CompilationUnit().getMap().values();
        String className = name;

        boolean anyMatch = compilationUnits.stream()
                .anyMatch(c -> c.getMainType().getSimpleName().equals(className));
        if (anyMatch) {
            int counter = 0;
            do {
                int current = counter++;
                anyMatch = compilationUnits.stream()
                        .anyMatch(c -> c.getMainType().getSimpleName().equals(className + current));
            } while (anyMatch);
            name += counter;
        }

        String qualifiedName = _package.isEmpty() ? name : _package + "." + name;
        final CtInterface<?> newInterface;
        if (newFile) {
            newInterface = ActionUtils.compilationUnitWithInterface(qualifiedName, null,
                    parent.getFile().getParentFile(), factory);
        } else {
            newInterface = ActionUtils.newInterface(name, null, getNodeImpl().getFactory());
        }

        if (associate) {
            getNodeImpl().addSuperInterface(newInterface.getReference());
        }

        // Then add the method signature
        final JMethod<?> jMethod = (JMethod<?>) method;
        final CtMethod<?> ctMethod = jMethod.getNodeImpl();
        final CtMethod<?> newMethod = copyMethod(newInterface, ctMethod, factory);
        newMethod.removeModifier(ModifierKind.STATIC); // methods in interfaces are non-static!
        newMethod.removeModifier(ModifierKind.PRIVATE); // we want this method to be public
        newMethod.addModifier(ModifierKind.PUBLIC);
        newMethod.setParent(newInterface);
        JInterfaceType<?> newInstance = new JInterfaceType<>(newInterface, getWeaverEngine());
        return newInstance;
    }

    private static CtMethod<?> copyMethod(CtInterface<?> newInterface, CtMethod<?> ctMethod, Factory factory) {
        Set<ModifierKind> modifiers = new HashSet<>(ctMethod.getModifiers());
        Set<CtTypeReference<? extends Throwable>> thrownTypes = new HashSet<>(ctMethod.getThrownTypes());
        List<CtParameter<?>> parameters = new ArrayList<>(ctMethod.getParameters());
        final CtMethod<?> newMethod = factory.Method().create(newInterface, modifiers, ctMethod.getType(),
                ctMethod.getSimpleName(), parameters, thrownTypes);
        return newMethod;
    }

    @Override
    public AMethod<?> newFunctionalClassImpl(AMethod<?> interfaceMethod, AMethod<?> generatorMethod) {
        JMethod<?> iMethod = (JMethod<?>) interfaceMethod;
        CtMethod<?> iMethodNode = iMethod.getNodeImpl();
        JMethod<?> gMethod = (JMethod<?>) generatorMethod;
        CtMethod<?> gMethodNode = gMethod.getNodeImpl();

        JMethod<?> jMethod = FunctionalClassGenerator.generate(getWeaverEngine(), iMethodNode, gMethodNode,
                getNodeImpl());
        return jMethod;
    }

    @Override
    public AConstructor<?> newConstructorImpl(String[] modifiers, String[] paramLeft, String[] paramRight) {
        CtConstructor<?> newConstructor = ActionUtils.newConstructor(getNodeImpl(), paramLeft, paramRight, modifiers);
        JConstructor<?> newInstance = SelectUtils.node2JoinPoint(newConstructor,
                (node -> new JConstructor<>(node, getWeaverEngine())));
        return newInstance;
    }

    @Override
    public AJoinpoint<?> getParentImpl() {
        var spoonParent = getNodeImpl().getParent();
        if (spoonParent != null && !(spoonParent instanceof CtPackage)) {
            return CtElement2JoinPoint.convert(spoonParent, getWeaverEngine());
        }

        return new JFile<>(parent, getWeaverEngine());
    }

    @Override
    public boolean getIsTopLevelImpl() {
        return getNodeImpl().isTopLevel();
    }
}
