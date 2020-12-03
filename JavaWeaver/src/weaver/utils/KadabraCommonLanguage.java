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


import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtBreak;
import spoon.reflect.code.CtCase;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtContinue;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLambda;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtSwitch;
import spoon.reflect.code.CtThrow;
import spoon.reflect.code.CtTry;
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

	private static final FunctionClassMap<CtElement, String> JOINPOINT_MAPPER;
	static {
		JOINPOINT_MAPPER = new FunctionClassMap<>();
		
		JOINPOINT_MAPPER.put(CtLambda.class, node -> "LambdaJp");
		JOINPOINT_MAPPER.put(CtContinue.class, node -> "ContinueJp");
		JOINPOINT_MAPPER.put(CtBreak.class, node -> "BreakJp");
		JOINPOINT_MAPPER.put(CtThrow.class, node -> "ThrowJp");
		JOINPOINT_MAPPER.put(CtCatch.class, node -> "CatchJp");
		JOINPOINT_MAPPER.put(CtTry.class, node -> "TryJp");
//		JOINPOINT_MAPPER.put(CtFor.class,node -> "ForJp");
//		JOINPOINT_MAPPER.put(CtDo.class,node -> "DoJp");
//		JOINPOINT_MAPPER.put(CtWhile.class,node -> "WhileJp");
		JOINPOINT_MAPPER.put(CtBinaryOperator.class, node -> "BinaryJp");
		JOINPOINT_MAPPER.put(CtConditional.class, node -> "TernaryJp");
		JOINPOINT_MAPPER.put(CtLoop.class, node -> "LoopJp");
		JOINPOINT_MAPPER.put(CtCase.class, KadabraCommonLanguage::ctCase);
		JOINPOINT_MAPPER.put(CtSwitch.class, node -> "SwitchJp");
		JOINPOINT_MAPPER.put(CtIf.class, node -> "IfJp");
		JOINPOINT_MAPPER.put(CtStatement.class, KadabraCommonLanguage::ctStatement);
		JOINPOINT_MAPPER.put(CtConstructorCall.class, node -> "ConstructorCallJp");
		JOINPOINT_MAPPER.put(CtConstructor.class, node -> "ConstructorJp");
		JOINPOINT_MAPPER.put(CtLocalVariableReference.class, node -> "VarRefJp");
		JOINPOINT_MAPPER.put(CtLocalVariable.class, node -> "VarDeclJp");
		JOINPOINT_MAPPER.put(CtParameter.class, node -> "ParamJp");
		JOINPOINT_MAPPER.put(CtTypeReference.class, node -> "TypeJp");
		JOINPOINT_MAPPER.put(CtFieldReference.class, node -> "FieldRefJp");
		JOINPOINT_MAPPER.put(CtField.class, node -> "FieldJp");
		JOINPOINT_MAPPER.put(CtInvocation.class, node -> "MemberCallJp");
		JOINPOINT_MAPPER.put(CtExpression.class, node -> "ExprJp");
		JOINPOINT_MAPPER.put(CtMethod.class, node -> "MethodJp");
		JOINPOINT_MAPPER.put(CtClass.class, KadabraCommonLanguage::ctClass);
		JOINPOINT_MAPPER.put(CtNamedElement.class, node -> "DeclJp");
		JOINPOINT_MAPPER.put(CtCompilationUnit.class, node -> "FileJp");
		JOINPOINT_MAPPER.put(CtApp.class, node -> "ProgramJp");
		JOINPOINT_MAPPER.put(CtElement.class, node -> "JoinPoint");
	}

	public static String getJoinPointName(CtElement node) {
		return JOINPOINT_MAPPER.apply(node);
	}

	@SuppressWarnings("rawtypes")
	private static String ctCase(CtCase node) {

		if (node.getCaseExpressions().isEmpty())
			return "DefaultJp";

		return "CaseJp";

	}
	
	private static String ctStatement(CtStatement node) {

		if (node.getParent() instanceof CtIf) {
			CtIf ifStmt = (CtIf) node.getParent();

			if (ifStmt.getElseStatement() != null && ifStmt.getElseStatement() == node)
				return "ElseJp";

			if (ifStmt.getThenStatement() != null && ifStmt.getThenStatement() == node)
				return "ThenJp";

		}
			return "StmtJp";

	}
	
	@SuppressWarnings("rawtypes")
	private static String ctClass(CtClass node) {
		
		if(node.isAnonymous())
			return "JoinPoint";
		
		return "ClassJp";	
		
	}


}
