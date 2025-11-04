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

package weaver.kadabra.agent;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import weaver.kadabra.agent.asm.EmptyMethodVisitor;

public abstract class MethodAdapter implements ClassFileTransformer {

    private Instrumentation instr;
    private final Class<?> targetClass;
    private final String targetClassPath;
    private final String targetMethod;
    private boolean isActive;
    // private T data;
    private static final boolean DEBUG = false;

    public MethodAdapter(Class<?> targetClass, String targetMethod) {
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
        targetClassPath = targetClass.getName().replace(".", "/");
        isActive = false;
    }

    final void setInstrumentation(Instrumentation instr) {
        this.instr = instr;
    }

    protected final void adapt(/*T data*/) {
        try {
            // data = data;
            if (instr == null) {
                // throw new RuntimeException("Could not access instrumentation instance");
                System.err.println("[KADABRA] Could not access instrumentation instance");
                return;
            }
            isActive = true;
            instr.retransformClasses(targetClass);
        } catch (UnmodifiableClassException e) {
            System.err.print(
                    "[KADABRA] Could not retransform target class (" + targetClass.getName() + "): " + e.getMessage());
        }
        isActive = false;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!isActive || !className.equals(targetClassPath)) {
            return classfileBuffer;
        }
        try {
            ClassReader classReader = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
            ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                        String[] exceptions) {
                    final MethodVisitor originalMV = cv.visitMethod(access, name, desc, signature, exceptions);
                    if (name.equals(targetMethod)) {

                        return new EmptyMethodVisitor() {
                            @Override
                            public void visitEnd() {
                                transform(originalMV, access);
                            }
                        };

                    }
                    return originalMV;
                }

            };
            classReader.accept(cv, 0);

            byte[] byteArray = cw.toByteArray();

            if (DEBUG) {
                File debugFolder = new File("ADAPTER_DEBUG");

                if (!debugFolder.exists()) {
                    debugFolder.mkdir();
                }

                // System.out.println("---->" + byteArray.length);
                // int pos = className.indexOf("/");
                // if (pos > -1) {

                // String innerDir = className.substring(0, pos);
                // File outDir = new File(debugFolder, innerDir);
                // outDir.mkdirs();
                // }
                File outFile = new File(debugFolder, className + "_Version" + getId() + ".class");
                outFile.getParentFile().mkdirs();
                try (FileOutputStream fos = new FileOutputStream(outFile)) {
                    fos.write(byteArray);
                }
            }
            return byteArray;
        } catch (

        Exception e) {
            e.printStackTrace();
        }

        return classfileBuffer;
    }

    private int getId() {
        return counter++;
    }

    private int counter = 0;

    // protected abstract byte[] transformBytes(String className, byte[] classfileBuffer/*, T data*/);

    protected abstract void transform(MethodVisitor mv, int access);
}
