/**
 * Copyright 2018 SPeCS.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.joinpoints;

import java.util.Arrays;

import com.google.common.reflect.ClassPath.ClassInfo;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ANamedType;

public class JNamedType<Self extends JNamedType<Self>> extends ANamedType<Self> {

    private final ClassInfo info;
    private Class<?> libClass;

    public JNamedType(ClassInfo info, JWeaver weaver) {
        super((spoon.reflect.declaration.CtElement) null, weaver);
        this.info = info;
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
    public String[] getInterfacesImpl() {
        Class<?>[] interfaces = getLibClass().getInterfaces();
        return Arrays.stream(interfaces).map(i -> i.getName()).toArray(String[]::new);
    }

    @Override
    public String getJavadocImpl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean getIsSubtypeOfImpl(String type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public spoon.reflect.declaration.CtElement getNodeImpl() {
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
