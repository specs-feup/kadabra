/**
 * Copyright 2016 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package weaver.utils.generators;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.objectweb.asm.MethodVisitor;

import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtSuperAccess;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.agent.MethodAdapter;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.kadabra.joinpoints.JClass;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.TypeUtils;

public class AdapterGenerator {

    private static final String ADAPT_NAME = "adapt";
    private static final String TRANSFORM_NAME = "transform";
    private static final Set<ModifierKind> publicMod = Collections.singleton(ModifierKind.PUBLIC);
    private static final Set<ModifierKind> protectedMod = Collections.singleton(ModifierKind.PROTECTED);
    private static final Set<ModifierKind> privateMod = Collections.singleton(ModifierKind.PRIVATE);

    /**
     * Generate an adapter for a target class based on a given method signature.<br>
     * <b>NOTE:</B> the method signature has to be: byte[] <anyName>(String,byte[]
     * [,<anyType>]);
     * 
     * @param name
     * @param adapterMethod
     * @param reuseIfExists
     * @param targetClass
     * @return
     */
    public static JClass<?> generate(JavaWeaver weaver, String name, CtMethod<?> adapterMethod,
            CtMethod<?> targetMethod,
            boolean reuseIfExists) {
        validateTransformerMethod(adapterMethod);

        List<CtParameter<?>> parameters = adapterMethod.getParameters();
        CtType<?> targetClass = targetMethod.getDeclaringType();

        Factory factory = targetClass.getFactory();
        File targetDir = targetClass.getPosition().getFile().getParentFile();
        // Create Compilation Unit with Class extending Adapt
        String newAdapterName = targetClass.getPackage().getQualifiedName() + "." + name;
        String abstractAdapterName = MethodAdapter.class.getName();
        var cu = ActionUtils.compilationUnitWithClass(newAdapterName, abstractAdapterName, null, targetDir,
                factory);
        CtClass<?> mainType = (CtClass<?>) cu.getMainType();

        generateConstructor(targetMethod, factory, mainType);

        CtMethod<Void> adaptMethod = generateAdaptMethod(factory, mainType);
        CtInvocation<?> transformMethod = generateTransformMethod(factory, mainType, adapterMethod);

        // Capture all the other required arguments
        for (int i = 2; i < parameters.size(); i++) {
            CtParameter<?> param = parameters.get(i);
            processParameter(param, mainType, adaptMethod, transformMethod);

        }

        addSuperAdaptInvocation(factory, adaptMethod);
        JClass<?> jClass = SelectUtils.node2JoinPoint(mainType, c -> JClass.newInstance(c, cu, weaver));
        return jClass;
    }

    private static CtMethod<Void> generateAdaptMethod(Factory factory, CtClass<?> mainType) {
        return factory.Method().create(mainType, publicMod, factory.Type().voidPrimitiveType(),
                ADAPT_NAME, null, null, factory.Core().createBlock());
    }

    private static CtInvocation<?> generateTransformMethod(Factory factory, CtClass<?> mainType,
            CtMethod<?> adapterMethod) {
        CtTypeReference<MethodVisitor> mVType = factory.Type().createReference(MethodVisitor.class);
        CtTypeReference<Integer> intType = factory.Type().integerPrimitiveType();
        CtTypeReference<Void> voidType = factory.Type().voidPrimitiveType();

        CtMethod<Void> method = factory.Method().create(mainType, protectedMod, voidType,
                TRANSFORM_NAME, null, null, factory.Core().createBlock());
        factory.Annotation().annotate(method, Override.class);
        CtParameter<MethodVisitor> mvParam = factory.Method().createParameter(method, mVType, "mv");
        CtParameter<Integer> accessParam = factory.Method().createParameter(method, intType, "access");
        CtInvocation<?> invoke = generateInvokeAdaptStmt(factory, method, mvParam, accessParam,
                adapterMethod);
        return invoke;
    }

    private static <T> CtInvocation<T> generateInvokeAdaptStmt(Factory factory, CtMethod<Void> method,
            CtParameter<MethodVisitor> mvParam, CtParameter<Integer> accessParam,
            CtMethod<T> adapterMethod) {
        CtExecutableReference<T> adapterRef = factory.Executable().createReference(adapterMethod);
        CtTypeReference<?> reference = adapterMethod.getDeclaringType().getReference();
        CtTypeAccess<?> adapterClassAccess = factory.Code().createTypeAccess(reference);
        CtInvocation<T> invoke = factory.Code().createInvocation(adapterClassAccess, adapterRef);
        invoke.addArgument(factory.Code().createVariableRead(mvParam.getReference(), false));
        invoke.addArgument(factory.Code().createVariableRead(accessParam.getReference(), false));

        method.getBody().addStatement(invoke);
        return invoke;
    }

    private static void addSuperAdaptInvocation(Factory factory, CtMethod<Void> adaptMethod) {
        CtSuperAccess<?> superAccess = factory.Core().createSuperAccess();
        CtExecutableReference<Void> superConstrRef;
        try {
            superConstrRef = factory.Method().createReference(MethodAdapter.class.getDeclaredMethod(ADAPT_NAME));
        } catch (Exception e) {
            throw new JavaWeaverException("When accessing Adapter class", e);
        }
        CtInvocation<Void> adaptInvoke = factory.Code().createInvocation(superAccess, superConstrRef);
        adaptMethod.getBody().addStatement(adaptInvoke);
    }

    /**
     * Process a parameter by:<br>
     * * adding it as a field<br>
     * * add as a parameter of method "adapt"<br>
     * * add assignment field = parameter<br>
     * * add as argument of targetMethod invocation
     * 
     * @param mainType
     * @param adaptMethod
     * @param privateMod
     * @param param
     */
    private static <T> void processParameter(CtParameter<T> param, CtClass<?> mainType, CtMethod<Void> adaptMethod,
            CtInvocation<?> adaptInvocation) {
        Factory factory = mainType.getFactory();
        // Add a new field
        CtField<T> paramField = factory.Field().create(mainType, privateMod, param.getType(),
                param.getSimpleName());

        CtParameter<T> paramCloned = param.clone();
        adaptMethod.addParameter(paramCloned);

        CtVariableAccess<T> paramRead = factory.Code().createVariableRead(paramCloned.getReference(), false);
        CtFieldReference<T> fieldRef = paramField.getReference();
        CtAssignment<T, T> varAssignment = factory.Code().createVariableAssignment(fieldRef, false, paramRead);
        adaptMethod.getBody().addStatement(varAssignment);

        adaptInvocation.addArgument(factory.Code().createVariableRead(fieldRef, false));
    }

    private static void generateConstructor(CtMethod<?> targetMethod, Factory factory, CtClass<?> mainType) {
        // Create the constructor

        CtExecutableReference<MethodAdapter> superConstrRef;
        try {
            superConstrRef = factory.Constructor()
                    .createReference(MethodAdapter.class.getConstructor(Class.class, String.class));
        } catch (Exception e) {
            throw new JavaWeaverException("When accessing Adapter class", e);
        }

        CtFieldReference<?> classFieldRef = factory.Field().createReference(
                targetMethod.getDeclaringType().getReference(),
                factory.Type().createReference(Class.class), "class");

        CtVariableAccess<?> targetClassAccess = factory.Code().createVariableRead(classFieldRef, true);
        CtLiteral<String> literalMethodName = factory.Code().createLiteral(targetMethod.getSimpleName());
        CtInvocation<?> superInvoke = factory.Code().createInvocation(null, superConstrRef, targetClassAccess,
                literalMethodName);
        CtConstructor<?> adaptConstr = factory.Constructor().create(mainType, publicMod, Collections.emptyList(),
                Collections.emptySet(), factory.Core().createBlock());

        adaptConstr.getBody().addStatement(superInvoke);
    }

    private static void validateTransformerMethodAux(CtMethod<?> adaptMethodNode) {
        Factory factory = adaptMethodNode.getFactory();
        CtTypeReference<MethodVisitor> mVType = factory.Type().createReference(MethodVisitor.class);
        CtTypeReference<Integer> intType = factory.Type().integerPrimitiveType();

        List<CtParameter<?>> parameters = adaptMethodNode.getParameters();
        // Verify if num args >= 2
        if (parameters.size() < 2) {
            throw new JavaWeaverException(
                    "The adapt method must have at least two arguments: MethodVisitor mv, int access");
        }

        CtTypeReference<?> actualMVType = parameters.get(0).getType();
        CtTypeReference<?> accessType = parameters.get(1).getType();

        // First argument should be of type String
        if (!actualMVType.isSubtypeOf(mVType)) {
            throw new JavaWeaverException(
                    "The first argument of adapt method is not of type " + MethodVisitor.class.getName());// + ".\n" +
        }

        // Second argument should be of type byte[]
        if (!accessType.isSubtypeOf(intType)) {
            throw new JavaWeaverException(
                    "The second argument of adapt method is not of type int.");// \n" +
        }
    }

    private static void validateTransformerMethod(CtMethod<?> adaptMethodNode) {
        try {
            validateTransformerMethodAux(adaptMethodNode);
        } catch (Exception e) {
            String returnType = TypeUtils.getTypeString(adaptMethodNode.getType());
            String args = adaptMethodNode.getParameters().stream().map(p -> TypeUtils.getTypeString(p.getType()))
                    .collect(Collectors.joining(", "));
            throw new RuntimeException(e.getMessage()
                    + "\nMethod Signature: " + returnType + " " + adaptMethodNode.getSimpleName() + "(" + args + ")"
                    + "\nExcepted signature: void <anyName>(" + MethodVisitor.class.getName()
                    + " mv, int access [,<otherArgs>])");
        }
    }

}
