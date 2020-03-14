/**
 * Copyright 2015 SPeCS Research Group.
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
import spoon.reflect.declaration.CtCompilationUnit;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.abstracts.joinpoints.AAnonymousExec;
import weaver.kadabra.abstracts.joinpoints.AClass;
import weaver.kadabra.abstracts.joinpoints.AConstructor;
import weaver.kadabra.abstracts.joinpoints.AInterface;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AMethod;
import weaver.kadabra.entities.Pair;
import weaver.utils.generators.FunctionalClassGenerator;
import weaver.utils.generators.MapGenerator;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.SnippetFactory;

public class JClass<T> extends AClass {

    private final CtClass<T> originClass;
    private final CtCompilationUnit parent;

    private JClass(CtClass<T> node, CtCompilationUnit parent) {
        super(JType.newInstance(node, parent));
        this.originClass = node;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return originClass.getQualifiedName();
    }

    public static <T> JClass<T> newInstance(CtClass<T> node, CtCompilationUnit parent) {
        return new JClass<>(node, parent);
    }

    public static <T> JClass<T> newInstance(CtClass<T> node) {

        return new JClass<>(node, node.getPosition().getCompilationUnit());
    }

    @Override
    public CtClass<T> getNode() {
        return originClass;
    }

    @Override
    public void insertStaticImpl(String code) {
        Factory factory = originClass.getFactory();
        CtCodeSnippetStatement snippetStmt = SnippetFactory.createSnippetStatement(code, factory);
        // CtBlock<Void> newBlock = factory.Code().createCtBlock(snippetStmt);
        CtBlock<Void> newBlock = factory.Core().createBlock();
        newBlock.addStatement(snippetStmt);
        CtAnonymousExecutable createAnonymous = factory.Method().createAnonymous(originClass, newBlock);
        createAnonymous.addModifier(ModifierKind.STATIC);
    }

    @Override
    public AClass mapVersionsImpl(String name, String keyType, AInterface _interface, String methodName) {

        CtClass<?> generate = MapGenerator.generate(originClass.getFactory(), name, keyType, _interface, methodName,
                getWeaverProfiler());
        originClass.addNestedType(generate);
        JClass<?> jClass = new JClass<>(generate, parent);
        return jClass;
    }

    @Override
    public AInterface extractInterfaceImpl(String name, String _package, AMethod method, boolean associate,
            boolean newFile) {

        Factory factory = originClass.getFactory();
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
        final CtInterface<Object> newInterface;
        if (newFile) {
            newInterface = ActionUtils.compilationUnitWithInterface(qualifiedName, null,
                    parent.getFile().getParentFile(), factory, getWeaverProfiler());
        } else {
            newInterface = ActionUtils.newInterface(name, null, originClass.getFactory(), getWeaverProfiler());
            // file.addInterface(new JInterface(newInterface));
        }

        if (associate) {
            originClass.addSuperInterface(newInterface.getReference());
        }

        // Then add the method signature
        final JMethod<?> jMethod = (JMethod<?>) method;
        final CtMethod<?> ctMethod = jMethod.getNode();
        // To be replaced in Spoon 5.0 with: create(newInterface, ctMethod, true); newMethod.setBody(null);
        final CtMethod<?> newMethod = copyMethod(newInterface, ctMethod, factory);
        newMethod.removeModifier(ModifierKind.STATIC); // methods in interfaces are non-static!
        newMethod.removeModifier(ModifierKind.PRIVATE); // we want this method to be public
        newMethod.addModifier(ModifierKind.PUBLIC);
        newMethod.setParent(newInterface);
        JInterface<?> newInstance = JInterface.newInstance(newInterface);
        return newInstance;
    }

    private static CtMethod<?> copyMethod(CtInterface<Object> newInterface, CtMethod<?> ctMethod, Factory factory) {
        Set<ModifierKind> modifiers = new HashSet<>(ctMethod.getModifiers());
        Set<CtTypeReference<? extends Throwable>> thrownTypes = new HashSet<>(ctMethod.getThrownTypes());
        List<CtParameter<?>> parameters = new ArrayList<>(ctMethod.getParameters());
        final CtMethod<?> newMethod = factory.Method().create(newInterface, modifiers, ctMethod.getType(),
                ctMethod.getSimpleName(), parameters, thrownTypes);
        return newMethod;
    }

    @Override
    public AMethod newFunctionalClassImpl(AMethod interfaceMethod, AMethod generatorMethod) {
        JMethod<?> iMethod = (JMethod<?>) interfaceMethod;
        CtMethod<?> iMethodNode = iMethod.getNode();
        JMethod<?> gMethod = (JMethod<?>) generatorMethod;
        CtMethod<?> gMethodNode = gMethod.getNode();

        JMethod<?> jMethod = FunctionalClassGenerator.generate(iMethodNode, gMethodNode, this.originClass);
        return jMethod;
    }

    @Override
    public List<? extends AConstructor> selectConstructor() {
        return SelectUtils.nodeList2JoinPointList(originClass.getConstructors(), JConstructor::newInstance);
    }

    @Override
    public List<? extends AAnonymousExec> selectAnonymousExec() {
        return SelectUtils.nodeList2JoinPointList(originClass.getAnonymousExecutables(), JAnonymousExec::newInstance);
    }

    @Override
    public AConstructor newConstructorImpl(String[] modifiers, Pair[] param) {
        CtConstructor<?> newConstructor = ActionUtils.newConstructor(originClass, param, modifiers,
                getWeaverProfiler());
        JConstructor<?> newInstance = SelectUtils.node2JoinPoint(newConstructor, JConstructor::newInstance);
        return newInstance;
    }

    @Override
    public AJoinPoint getParentImpl() {
        return new JFile(parent);
    }
}