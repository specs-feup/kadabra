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

import java.util.Arrays;

import com.google.common.reflect.ClassPath.ClassInfo;

import spoon.reflect.declaration.CtElement;
import weaver.kadabra.abstracts.joinpoints.ANamedType;

public class JNamedType extends ANamedType {

    private ClassInfo info;
    private Class<?> libClass;

    private JNamedType(ClassInfo info) {
        this.info = info;
    }

    public static JNamedType newInstance(ClassInfo info) {
        return new JNamedType(info);
    }

    @Override
    public String getNameImpl() {
        return info.getSimpleName();
    }

    @Override
    public String getQualifiedNameImpl() {
        return info.getName();
    }

    @Override
    public String getSuperClassImpl() {
        return getLibClass().getSuperclass().getName();
    }

    @Override
    public String getPackageNameImpl() {
        return info.getPackageName();
    }

    @Override
    public String[] getInterfacesArrayImpl() {
        Class<?>[] interfaces = getLibClass().getInterfaces();
        return Arrays.stream(interfaces).map(i -> i.getName()).toArray(String[]::new);
    }

    // @Override
    // public String[] getModifiersArrayImpl() {
    // return null;
    // }

    @Override
    public String getJavadocImpl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean isSubtypeOfImpl(String type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CtElement getNode() {
        // TODO Auto-generated method stub
        return null;
    }

    private Class<?> getLibClass() {
        if (libClass == null) {
            libClass = info.load();
        }
        return libClass;
    }
}
