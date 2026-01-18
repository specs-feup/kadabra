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

import java.util.List;
import java.util.stream.Collectors;

import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;
import spoon.reflect.visitor.CtVisitable;

/**
 * Utility class that clones a given element, including node-related information and its children. Nothing is done with
 * the parent.
 * 
 * @author Tiago
 *
 */
public class Cloner {

    private static final MapCtElement CLONERS = new MapCtElement();

    static {

	// Expressions
	CLONERS.put(CtInvocation.class, Cloner::invocation);
	CLONERS.put(CtBinaryOperator.class, Cloner::binaryOperator);
	CLONERS.put(CtFieldRead.class, Cloner::fieldRead);
	CLONERS.put(CtVariableRead.class, Cloner::varRead);
	CLONERS.put(CtLiteral.class, Cloner::literal);

	// References
	CLONERS.put(CtExecutableReference.class, Cloner::execRef);
	CLONERS.put(CtFieldReference.class, Cloner::fieldRef);
	CLONERS.put(CtTypeReference.class, Cloner::typeRef);

	// F
	CLONERS.put(CtElement.class, Cloner::defaultClone);
    }

    /**
     * Clones a given element
     * 
     * @param node
     * @return
     */
    public static <T extends CtVisitable> T clone(T node) {
	return CLONERS.applyTo(node);
    }

    private static CtVisitable defaultClone(CtVisitable node) {
	throw new RuntimeException("Not yet implemented for class '" + node.getClass() + "'");
    }

    /**
     * Clone literal expressions
     * 
     * @param node
     * @return
     */
    private static <T> CtLiteral<T> literal(CtLiteral<T> node) {
	return node.getFactory().Code().createLiteral(node.getValue());
    }

    /**
     * Clone binary expressions
     * 
     * @param operator
     * @return
     */
    private static <T> CtExpression<T> binaryOperator(CtBinaryOperator<T> operator) {

	CtBinaryOperator<T> newBin = operator.getFactory().Core().createBinaryOperator();
	newBin.setLeftHandOperand(clone(operator.getLeftHandOperand()));
	newBin.setRightHandOperand(clone(operator.getRightHandOperand()));
	newBin.setKind(operator.getKind());

	return newBin;
    }

    /**
     * Clone a method invocation
     * 
     * @param node
     * @return
     */
    private static <T> CtInvocation<T> invocation(CtInvocation<T> node) {
	List<CtExpression<?>> originalArgs = node.getArguments();

	CtExpression<?> target = clone(node.getTarget());

	CtExecutableReference<T> executable = execRef(node.getExecutable());
	List<CtExpression<?>> clonedArgs = originalArgs.stream()
		.map(Cloner::clone)
		.collect(Collectors.toList());

	return node.getFactory().Code().createInvocation(target, executable, clonedArgs);
    }

    /**
     * Clone variable reads
     * 
     * @param node
     * @return
     */
    private static <T> CtVariableRead<T> varRead(CtVariableRead<T> node) {

	CtVariableReference<T> variable = node.getVariable();
	CtVariableRead<T> varRead = node.getFactory().Core().createVariableRead();
	return varRead.setVariable(variable).setType(variable.getType());
    }

    /**
     * Clone field reads
     * 
     * @param node
     * @return
     */
    private static <T> CtFieldRead<T> fieldRead(CtFieldRead<T> node) {

	CtFieldReference<T> variable = node.getVariable();
	CtFieldRead<T> fieldRead = node.getFactory().Core().createFieldRead();

	fieldRead.setTarget(clone(node.getTarget()));
	return fieldRead.setVariable(variable).setType(variable.getType());

    }

    /**
     * Clone executable references
     * 
     * @param node
     * @return
     */
    private static <T> CtExecutableReference<T> execRef(CtExecutableReference<T> node) {
	Factory factory = node.getFactory();

	CtExecutable<T> declaration = node.getDeclaration();
	if (declaration != null) {

	    return factory.Executable().createReference(declaration);
	}

	// In case of libraries and Java-original classes
	CtTypeReference<?> declaringType = node.getDeclaringType();
	CtTypeReference<T> type = clone(node.getType()); // should it be cloned or use the existent?
	List<CtTypeReference<?>> parameters = node.getParameters().stream()
		.map(Cloner::clone)
		.collect(Collectors.toList());
	return factory.Executable().createReference(declaringType, type, node.getSimpleName(), parameters);
    }

    /**
     * Clone field references
     * 
     * @param node
     * @return
     */
    private static <T> CtFieldReference<T> fieldRef(CtFieldReference<T> node) {
	Factory factory = node.getFactory();

	CtField<T> declaration = node.getDeclaration();
	if (declaration != null) {
	    return factory.Field().createReference(declaration);
	}

	return factory.Field().createReference(node.getDeclaringType(), node.getType(), node.getSimpleName());
    }

    /**
     * Clone type references
     * 
     * @param node
     * @return
     */
    private static <T> CtTypeReference<T> typeRef(CtTypeReference<T> node) {
	Factory factory = node.getFactory();
	CtType<T> declaration = node.getDeclaration();
	if (declaration != null) {
	    return factory.Type().createReference(declaration);
	}

	return factory.Type().createReference(node.getQualifiedName());
    }
    // private static CtLocalVariable<?> localVariable(CtLocalVariable<?> node) {
    // node.
    // return node.getFactory().Code().createLiteral(node.getValue());
    // }

    // private static <T> CtMethod<T> method(CtMethod<T> node) {
    // Factory factory = node.getFactory();
    // CtClass<Object> tempClass = factory.Class().create("TEMP");
    // factory.Method().create(tempClass, node, redirectReferences);
    // }
}
