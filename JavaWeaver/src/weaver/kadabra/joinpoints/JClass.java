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

import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.declaration.*;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.joinpoints.*;
import weaver.utils.generators.FunctionalClassGenerator;
import weaver.utils.generators.MapGenerator;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

import java.util.*;

public class JClass<T> extends AClass {

    private final CtClass<T> originClass;
    private final CtCompilationUnit parent;

    private JClass(CtClass<T> node, CtCompilationUnit parent, JavaWeaver weaver) {
        super(JType.newInstance(node, parent, weaver), weaver);
        this.originClass = node;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return originClass.getQualifiedName();
    }

    public static <T> JClass<T> newInstance(CtClass<T> node, CtCompilationUnit parent, JavaWeaver weaver) {
        return new JClass<>(node, parent, weaver);
    }

    public static <T> JClass<T> newInstance(CtClass<T> node, JavaWeaver weaver) {
        return new JClass<>(node, node.getPosition().getCompilationUnit(), weaver);
    }

    @Override
    public CtClass<T> getNode() {
        return originClass;
    }

    @Override
    public void insertStaticImpl(String code) {

        Factory factory = originClass.getFactory();
        CtCodeSnippetStatement snippetStmt = SnippetFactory.createSnippetStatement(code, factory);
        CtBlock<Void> newBlock = factory.Core().createBlock();
        newBlock.addStatement(snippetStmt);
        CtAnonymousExecutable createAnonymous = factory.Method().createAnonymous(originClass, newBlock);
        createAnonymous.addModifier(ModifierKind.STATIC);
    }

    @Override
    public AClass mapVersionsImpl(String name, String keyType, AInterfaceType _interface, String methodName) {

        CtClass<?> generate = MapGenerator.generate(originClass.getFactory(), name, keyType, _interface, methodName);
        originClass.addNestedType(generate);
        JClass<?> jClass = new JClass<>(generate, parent, getWeaverEngine());
        return jClass;
    }

    @Override
    public AInterfaceType extractInterfaceImpl(String name, String _package, AMethod method, boolean associate,
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
                    parent.getFile().getParentFile(), factory);
        } else {
            newInterface = ActionUtils.newInterface(name, null, originClass.getFactory());
        }

        if (associate) {
            originClass.addSuperInterface(newInterface.getReference());
        }

        // Then add the method signature
        final JMethod<?> jMethod = (JMethod<?>) method;
        final CtMethod<?> ctMethod = jMethod.getNode();
        // To be replaced in Spoon 5.0 with: create(newInterface, ctMethod, true);
        // newMethod.setBody(null);
        final CtMethod<?> newMethod = copyMethod(newInterface, ctMethod, factory);
        newMethod.removeModifier(ModifierKind.STATIC); // methods in interfaces are non-static!
        newMethod.removeModifier(ModifierKind.PRIVATE); // we want this method to be public
        newMethod.addModifier(ModifierKind.PUBLIC);
        newMethod.setParent(newInterface);
        JInterfaceType<?> newInstance = JInterfaceType.newInstance(newInterface, getWeaverEngine());
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

        JMethod<?> jMethod = FunctionalClassGenerator.generate(getWeaverEngine(), iMethodNode, gMethodNode,
                this.originClass);
        return jMethod;
    }

    @Override
    public AConstructor newConstructorImpl(String[] modifiers, String[] paramLeft, String[] paramRight) {
        CtConstructor<?> newConstructor = ActionUtils.newConstructor(originClass, paramLeft, paramRight, modifiers);
        JConstructor<?> newInstance = SelectUtils.node2JoinPoint(newConstructor,
                (node -> JConstructor.newInstance(node, getWeaverEngine())));
        return newInstance;
    }

    @Override
    public AJoinPoint getParentImpl() {
        var spoonParent = getNode().getParent();
        if (spoonParent != null && !(spoonParent instanceof CtPackage)) {
            return CtElement2JoinPoint.convert(spoonParent, getWeaverEngine());
        }

        return new JFile(parent, getWeaverEngine());
    }

    @Override
    public Boolean getIsTopLevelImpl() {
        return originClass.isTopLevel();
    }
}
