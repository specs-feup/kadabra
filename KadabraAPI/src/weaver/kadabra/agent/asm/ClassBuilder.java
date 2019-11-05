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

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Generates a class with
 * 
 * @author Tiago
 *
 */
public class ClassBuilder implements Opcodes {
    private final String className;
    private ClassWriter cw;

    private ClassBuilder(String className) {
	this.className = className;
	setClassWriter();
    }

    /**
     * 
     * @param className
     * @param classBuilder
     */
    public static ClassBuilder newInstance(String className) {
	return new ClassBuilder(className);
    }

    public byte[] build() {
	cw.visitEnd();
	return cw.toByteArray();

    }

    public ClassBuilder process(Consumer<ClassWriter> processor) {
	processor.accept(cw);
	return this;
    }

    private void setClassWriter() {
	cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
	String classAsPath = className.replace(".", "/");
	cw.visit(52, ACC_PUBLIC + ACC_SUPER, classAsPath, null, "java/lang/Object", null);
	addDefaultInit(cw, classAsPath);
    }

    private static void addDefaultInit(ClassWriter cw, String className) {
	MethodVisitor mv;
	{
	    mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
	    mv.visitCode();
	    Label l0 = new Label();
	    mv.visitLabel(l0);
	    mv.visitLineNumber(10, l0);
	    mv.visitVarInsn(ALOAD, 0);
	    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
	    mv.visitInsn(RETURN);
	    Label l1 = new Label();
	    mv.visitLabel(l1);
	    mv.visitLocalVariable("this", "L" + className + ";", null, l0, l1, 0);
	    mv.visitMaxs(1, 1);
	    mv.visitEnd();
	}
    }
}
