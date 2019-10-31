/**
 * Copyright 2018 SPeCS.
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

package weaver.kadabra.joinpoints;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.reflect.ClassPath.ClassInfo;

import spoon.reflect.declaration.CtElement;
import weaver.kadabra.abstracts.joinpoints.ALibClass;
import weaver.kadabra.abstracts.joinpoints.ALibMethod;
import weaver.kadabra.util.KadabraLog;

public class JLibClass extends ALibClass {

    private ClassInfo info;
    private Class<?> libClass;

    private JLibClass(ClassInfo info) {
        super(JNamedType.newInstance(info));
        this.info = info;
    }

    public static JLibClass newInstance(ClassInfo info) {
        return new JLibClass(info);
    }

    @Override
    public CtElement getNode() {

        return null;
    }

    @Override
    public List<? extends ALibMethod> selectLibMethod() {
        Class<?> libClass = getLibClass();
        if (libClass != null) {
            try {
                Method[] methods = libClass.getDeclaredMethods();
                List<JLibMethod> jMethods = Arrays.stream(methods).map(JLibMethod::newInstance)
                        .collect(Collectors.toList());
                return jMethods;
            } catch (Throwable e) {
                KadabraLog.warning("Failed to load methods from class " + info.getName() + ": " + e.getMessage());
            }
        }
        return Collections.emptyList();
    }

    private Class<?> getLibClass() {
        if (libClass == null) {
            try {
                libClass = info.load();
            } catch (Throwable e) {
                KadabraLog.warning("Failed to load class " + info.getName() + ": " + e.getMessage());
            }
        }
        return libClass;
    }

}
