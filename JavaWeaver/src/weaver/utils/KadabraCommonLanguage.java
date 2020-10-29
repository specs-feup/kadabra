/**
 * Copyright 2020 SPeCS.
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

package weaver.utils;

import pt.up.fe.specs.util.classmap.ClassMap;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtCase;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtSwitch;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtCompilationUnit;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtNamedElement;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.spoon.extensions.nodes.CtApp;

public class KadabraCommonLanguage {

	private static final ClassMap<CtElement, String> JOINPOINT_MAPPER;
	static {
		JOINPOINT_MAPPER = new ClassMap<>();
//		JOINPOINT_MAPPER.put(CtFor.class, "ForJp");
//		JOINPOINT_MAPPER.put(CtDo.class, "DoJp");
//		JOINPOINT_MAPPER.put(CtWhile.class, "WhileJp");
		JOINPOINT_MAPPER.put(CtBinaryOperator.class, "BinaryJp");
		JOINPOINT_MAPPER.put(CtConditional.class, "TernaryJp");
		JOINPOINT_MAPPER.put(CtLoop.class, "LoopJp");
		JOINPOINT_MAPPER.put(CtCase.class, "CaseJp");
		JOINPOINT_MAPPER.put(CtSwitch.class, "SwitchJp");
		JOINPOINT_MAPPER.put(CtIf.class, "IfJp");
		JOINPOINT_MAPPER.put(CtStatement.class, "StmtJp");
		JOINPOINT_MAPPER.put(CtConstructorCall.class, "ConstructorCallJp");
		JOINPOINT_MAPPER.put(CtConstructor.class, "ConstructorJp");
		JOINPOINT_MAPPER.put(CtLocalVariableReference.class, "VarRefJp");
		JOINPOINT_MAPPER.put(CtLocalVariable.class, "VarDeclJp");
		JOINPOINT_MAPPER.put(CtParameter.class, "ParamJp");
		JOINPOINT_MAPPER.put(CtTypeReference.class, "TypeJp");
		JOINPOINT_MAPPER.put(CtFieldReference.class, "FieldRefJp");
		JOINPOINT_MAPPER.put(CtField.class, "FieldJp");
		JOINPOINT_MAPPER.put(CtInvocation.class, "MemberCallJp");
		JOINPOINT_MAPPER.put(CtExpression.class, "ExprJp");
		JOINPOINT_MAPPER.put(CtMethod.class, "MethodJp");
		JOINPOINT_MAPPER.put(CtClass.class, "ClassJp");
		JOINPOINT_MAPPER.put(CtNamedElement.class, "DeclJp");
		JOINPOINT_MAPPER.put(CtCompilationUnit.class, "FileJp");
		JOINPOINT_MAPPER.put(CtApp.class, "ProgramJp");
		JOINPOINT_MAPPER.put(CtElement.class, "JoinPoint");
	}

	public static String getJoinPointName(CtElement node) {
		return JOINPOINT_MAPPER.get(node.getClass());
	}

}
