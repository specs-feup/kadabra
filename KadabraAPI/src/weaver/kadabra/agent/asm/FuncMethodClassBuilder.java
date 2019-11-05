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

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Generates a class with
 * 
 * @author Tiago
 *
 */
public class FuncMethodClassBuilder implements Opcodes {
    private final String className;
    private final String interfaceName;
    private ClassWriter cw;
    private final String methodName;
    private final String methodDesc;
    private MethodVisitor functionWritter;

    private FuncMethodClassBuilder(String className, String interfaceName, String methodName, String methodDesc) {
        this.className = className;
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.methodDesc = methodDesc;
        setClassWriter();
    }

    /**
     * 
     * @param className
     * @param classBuilder
     */
    public static FuncMethodClassBuilder newInstance(String className, String interfaceName, String methodName,
            String methodDesc) {
        return new FuncMethodClassBuilder(className, interfaceName, methodName, methodDesc);
    }

    /**
     * 
     * @param className
     * @param classBuilder
     */
    public static FuncMethodClassBuilder newInstance(String className, String interfaceName, String methodName,
            Class<?> returnType, Class<?>... arguments) {
        String methodSignature = buildDesc(returnType, arguments);
        return new FuncMethodClassBuilder(className, interfaceName, methodName, methodSignature);
    }

    public byte[] build() {
        cw.visitEnd();
        return cw.toByteArray();

    }

    private void setClassWriter() {
        cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        String classAsPath = className.replace(".", "/");
        String interfAsPath = interfaceName.replace(".", "/");
        String[] interfs = { interfAsPath };
        cw.visit(52, ACC_PUBLIC + ACC_SUPER, classAsPath, null, "java/lang/Object", interfs);
        addDefaultInit(classAsPath);
        addMethodWriter();

    }

    private void addMethodWriter() {
        functionWritter = cw.visitMethod(ACC_PUBLIC, methodName, methodDesc, null, null);
    }

    private void addDefaultInit(String className) {
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

    public MethodVisitor getFunctionWritter() {
        return functionWritter;
    }

    private static String buildDesc(Class<?> returnType, Class<?>[] arguments) {
        Type retType = Type.getType(returnType);
        Type[] argsTypes = new Type[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            argsTypes[i] = Type.getType(arguments[i]);
        }
        return Type.getMethodDescriptor(retType, argsTypes);
    }
}
