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

package weaver.kadabra.agent;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.Method;

public class AgentUtils {

    /**
     * Add a new {@link MethodAdapter} (extends {@link ClassFileTransformer}) to instrument a specific class. <br>
     * <b>Note:</b> The use of {@link MethodAdapter} requires the JVM argument -javaagent:&lt;path_to_kadabraAgent.jar&gt;
     * 
     * @param adapter
     */
    public static void addAdapter(MethodAdapter/*<?>*/ adapter) {
	PremainAgent.addAdapter(adapter);
    }

    /**
     * Loads a new class with the given bytecodes
     * 
     * @param qualifiedName
     * @param bytecodes
     * @return
     */
    public static Class<?> newClass(String qualifiedName, byte[] bytecodes) {
	// override classDefine (as it is protected) and define the class.
	Class<?> clazz = null;
	try {
	    ClassLoader loader = ClassLoader.getSystemClassLoader();
	    Class<?>[] parameterTypes = { String.class, byte[].class, int.class, int.class };
	    Method method = ClassLoader.class.getDeclaredMethod("defineClass", parameterTypes);
	    method.setAccessible(true);
	    try {

		Object[] args = { qualifiedName, bytecodes, new Integer(0), new Integer(bytecodes.length) };
		clazz = (Class<?>) method.invoke(loader, args);
		// saveClass(qualifiedName, bytecodes);
	    } finally {
		method.setAccessible(false);
	    }
	} catch (Exception e) {
	    throw new RuntimeException("Could not load new class " + qualifiedName, e);
	}
	return clazz;
    }

    public static void saveClass(String qualifiedName, byte[] bytecodes) {
	String classAsPath = qualifiedName.replace(".", "/");
	int pos = classAsPath.lastIndexOf('/');
	if (pos != -1) {
	    String path = classAsPath.substring(0, pos);
	    new File(path).mkdirs();
	}

	try (FileOutputStream fos = new FileOutputStream(new File(classAsPath) + ".class")) {
	    fos.write(bytecodes);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
}
