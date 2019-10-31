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
 * specific language governing permissions and limitations under the License. under the License.
 */

package weaver.utils.generators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.objectweb.asm.MethodVisitor;

import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLambda;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtParameterReference;
import spoon.reflect.reference.CtTypeReference;
import tdrc.utils.StringUtils;
import weaver.kadabra.agent.asm.ASMUtils;
import weaver.kadabra.agent.asm.MethodBuilder;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.kadabra.joinpoints.JMethod;
import weaver.utils.weaving.TypeUtils;

public class FunctionalClassGenerator {

    /**
     * Generates a method that invokes the methodbuilder inside a {@link MethodBuilder} lambda and then returns an
     * invokation to ASMUtils.
     * 
     * @param funcInterfaceMethod
     * @param methodBuilder
     * @param newMethodOwner
     * @return
     */
    public static JMethod<?> generate(CtMethod<?> funcInterfaceMethod, CtMethod<?> methodBuilder,
            CtClass<?> newMethodOwner) {
        validateTransformerMethod(methodBuilder);

        String name = buildName(funcInterfaceMethod, methodBuilder);

        CtTypeReference<?> interfaceReference = funcInterfaceMethod.getDeclaringType().getReference();
        CtMethod<?> newMethod = generateNewVersionGenerator(name, interfaceReference, newMethodOwner);

        List<CtParameter<?>> parameters = methodBuilder.getParameters();
        List<CtExpression<?>> arguments = new ArrayList<>(parameters.size());

        for (int i = 1; i < parameters.size(); i++) {
            CtParameter<?> ctParameter = parameters.get(i).clone();

            newMethod.addParameter(ctParameter);
            arguments.add(paramRead(ctParameter));
        }

        CtLocalVariable<MethodBuilder> builderVar = generateBuilderLambda(methodBuilder, arguments, name);
        CtReturn<?> returnStmt = generateReturnStmt(methodBuilder, funcInterfaceMethod, builderVar);

        newMethod.getBody().addStatement(builderVar);
        newMethod.getBody().addStatement(returnStmt);
        return JMethod.newInstance(newMethod);
    }

    public static <T> CtReturn<?> generateReturnStmt(CtMethod<?> methodBuilder, CtMethod<?> funcInterfaceMethod,
            CtLocalVariable<MethodBuilder> builderVar) {
        Factory factory = methodBuilder.getFactory();
        CtReturn<T> returnStmt = factory.Core().createReturn();

        CtTypeReference<ASMUtils> asmUtilsClass = factory.Type().createReference(ASMUtils.class);
        CtTypeAccess<?> target = factory.Code().createTypeAccess(asmUtilsClass);

        CtTypeReference<T> interfaceTypeRef = funcInterfaceMethod.getFactory().Type()
                .createReference(funcInterfaceMethod.getDeclaringType().getQualifiedName());

        CtExecutableReference<T> execRef = factory.Executable().createReference(asmUtilsClass, true, interfaceTypeRef,
                "newFuncMethod");

        List<CtExpression<?>> arguments = new ArrayList<>();

        // builder
        arguments.add(factory.Code().createVariableRead(builderVar.getReference(), false));

        // <interface>.class
        arguments.add(generateClassFieldAccess(interfaceTypeRef));

        // method name
        arguments.add(factory.Code().createLiteral(funcInterfaceMethod.getSimpleName()));

        // return type
        // CtTypeReference<?> typeRef = funcInterfaceMethod.getType();
        // CtFieldReference<Class<?>> classRef = factory.Field().createReference(typeRef, classRef,
        // classFieldName);
        arguments.add(generateClassFieldAccess(funcInterfaceMethod.getType()));
        for (CtParameter<?> param : funcInterfaceMethod.getParameters()) {
            arguments.add(generateClassFieldAccess(param.getType()));
        }

        CtInvocation<T> invocation = factory.Code().createInvocation(target, execRef, arguments);
        returnStmt.setReturnedExpression(invocation);
        return returnStmt;
    }
    //
    // public static <T> CtTypeReference<T> getDeclaratorTypeRef(CtMethod<?> funcInterfaceMethod) {
    // CtTypeReference<T> ref;
    // ref = funcInterfaceMethod.getFactory().Type()
    // .createReference(funcInterfaceMethod.getDeclaringType().getQualifiedName());
    // return ref;
    // }

    public static <T> CtVariableAccess<Class<?>> generateClassFieldAccess(CtTypeReference<T> typeRef) {

        Factory factory = typeRef.getFactory();
        CtTypeReference<Class<?>> classRef = factory.Type()
                .createReference(Class.class.getName());
        CtFieldReference<Class<?>> interfClassRef = factory.Field().createReference(typeRef, classRef,
                "class");
        CtVariableAccess<Class<?>> createVariableRead = factory.Code().createVariableRead(interfClassRef, true);
        return createVariableRead;
    }

    public static CtLocalVariable<MethodBuilder> generateBuilderLambda(CtMethod<?> methodBuilder,
            List<CtExpression<?>> arguments, String name) {
        Factory factory = methodBuilder.getFactory();
        CtTypeReference<MethodBuilder> consumerRef = factory.Type().createReference(MethodBuilder.class);
        CtTypeReference<MethodVisitor> mvType = factory.Type().createReference(MethodVisitor.class);

        CtLambda<MethodBuilder> lambda = factory.Core().createLambda();
        lambda.setType(consumerRef);
        lambda.setSimpleName("lambda$" + name);

        CtLocalVariable<MethodBuilder> builderVar = factory.Code().createLocalVariable(consumerRef, "builder",
                lambda);

        CtParameter<MethodVisitor> mvParam = factory.Method().createParameter(lambda, mvType, "mv");
        arguments.add(0, paramReadNew(mvParam));

        CtTypeAccess<?> target = factory.Code().createTypeAccess(methodBuilder.getDeclaringType().getReference());
        CtInvocation<?> invocation = factory.Code().createInvocation(target, methodBuilder.getReference(),
                arguments);
        CtBlock<MethodBuilder> block = factory.Core().createBlock();
        block.addStatement(invocation);
        lambda.setBody(block);

        return builderVar;
    }

    public static <T> CtExpression<T> paramReadNew(CtParameter<T> parameter) {
        CtParameterReference<T> paramRef = parameter.getFactory().Core().createParameterReference();//
        paramRef.setSimpleName(parameter.getSimpleName());
        paramRef.setType(parameter.getType());
        CtExpression<T> variableRead = parameter.getFactory().Code().createVariableRead(paramRef, false);
        return variableRead;
    }

    public static <T> CtExpression<T> paramRead(CtParameter<T> parameter) {
        CtParameterReference<T> paramRef = parameter.getFactory().Method().createParameterReference(parameter);//

        CtExpression<T> variableRead = parameter.getFactory().Code().createVariableRead(paramRef, false);
        return variableRead;
    }

    public static String buildName(CtMethod<?> funcInterfaceMethod, CtMethod<?> methodBuilder) {
        String simpleName = funcInterfaceMethod.getSimpleName();
        return "new" + StringUtils.firstCharToUpper(simpleName) + "By"
                + methodBuilder.getDeclaringType().getSimpleName() + "$" + methodBuilder.getSimpleName();
    }

    private static <T> CtMethod<T> generateNewVersionGenerator(String name, CtTypeReference<T> returnType,
            CtClass<?> container) {

        CtBlock<T> createBlock = container.getFactory().Core().createBlock();

        Set<ModifierKind> kinds = new HashSet<>();
        kinds.add(ModifierKind.PUBLIC);
        kinds.add(ModifierKind.STATIC);
        return container.getFactory().Method().create(container,
                kinds,
                returnType,
                name, null, null, createBlock);
    }

    private static void validateTransformerMethod(CtMethod<?> methodBuilder) {
        try {
            validateTransformerMethodAux(methodBuilder);
        } catch (Exception e) {
            String returnType = TypeUtils.getTypeString(methodBuilder.getType());
            String args = methodBuilder.getParameters().stream().map(p -> TypeUtils.getTypeString(p.getType()))
                    .collect(Collectors.joining(", "));
            throw new RuntimeException(e.getMessage()
                    + "\nMethod Signature: " + returnType + " " + methodBuilder.getSimpleName() + "(" + args + ")"
                    + "\nExcepted signature: void <anyName>(org.objectweb.asm.MethodVisitor [, anyType+])");
        }
    }

    private static void validateTransformerMethodAux(CtMethod<?> methodBuilder) {
        Factory factory = methodBuilder.getFactory();
        // CtTypeReference<Void> voidType = factory.Type().voidPrimitiveType();
        CtTypeReference<MethodVisitor> methodVisitorType = factory.Type().createReference(MethodVisitor.class);

        // First verify if return type is void - this is not a problem since we ignore the return type
        // if (!methodBuilder.getType().isAssignableFrom(voidType)) {
        // throw new JavaWeaverException(
        // "The method builder must have void return");
        // }

        List<CtParameter<?>> parameters = methodBuilder.getParameters();
        // Verify if num args >= 2
        if (parameters.size() < 1) {
            throw new JavaWeaverException(
                    "The method builder must have at least one argument: org.objectweb.asm.MethodVisitor mv");
        }

        CtTypeReference<?> mvType = parameters.get(0).getType();
        // First argument should be of type org.objectweb.asm.MethodVisitor
        if (!mvType.isSubtypeOf(methodVisitorType)) {
            throw new JavaWeaverException(
                    "The first argument of adapt method is not of type org.objectweb.asm.MethodVisitor\n" +
                            "The method builder must have at least one argument: org.objectweb.asm.MethodVisitor mv");
        }
    }
}
