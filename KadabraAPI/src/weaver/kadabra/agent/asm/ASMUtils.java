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

package weaver.kadabra.agent.asm;

import java.util.function.Consumer;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import weaver.kadabra.agent.AgentUtils;

public class ASMUtils {

    private static int counter = 0;

    public static void visitConstantInstr(int i, MethodVisitor mv) {
	if (i >= 0 && i <= 5) {
	    mv.visitInsn(Opcodes.ICONST_0 + i);
	} else if (i >= Byte.MIN_VALUE && i <= Byte.MAX_VALUE) {
	    mv.visitIntInsn(Opcodes.BIPUSH, i);
	} else if (i >= Short.MIN_VALUE && i <= Short.MAX_VALUE) {
	    mv.visitIntInsn(Opcodes.SIPUSH, i);
	} else {
	    mv.visitLdcInsn(i);
	}
    }

    /**
     * Generates a new class with a single method, which reflects into the interfaceType, and returns an instance of
     * that class casted to the interfaceType.
     * 
     * @param methodBuilder
     *            The builder that generates the method body
     * @param interfaceType
     *            The functional interface type of the class
     * @param methodName
     *            The name of the method. <b>NOTE</b> the name of the method should be the same as the functional method
     *            of the interface.
     * @param returnType
     *            The type of return of the method
     * @param args
     *            The arguments types
     * @return
     */
    public static <T> T newFuncMethod(Consumer<MethodVisitor> methodBuilder, Class<T> interfaceType,
	    String methodName, Class<?> returnType,
	    Class<?>... args) {

	String newClassName = "org.kadabra.Kadabra" + methodName + "Version" + counter++;
	FuncMethodClassBuilder cb = FuncMethodClassBuilder.newInstance(newClassName, interfaceType.getName(),
		methodName, returnType, args);
	methodBuilder.accept(cb.getFunctionWritter());

	byte[] build = cb.build();
	// AgentUtils.saveClass(newClassName, build);
	Class<?> newClass = AgentUtils.newClass(newClassName, build);

	try {
	    return interfaceType.cast(newClass.newInstance());
	} catch (Exception e) {
	    throw new RuntimeException("Could not instantiate new Functional Class", e);
	}
    }

}
