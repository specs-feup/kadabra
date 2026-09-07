/**
 * Copyright 2019 SPeCS.
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

import java.util.Set;

import spoon.reflect.reference.CtTypeReference;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ATypeReference;

public class JTypeReference<Self extends JTypeReference<Self>> extends ATypeReference<Self> {

    private static final Set<String> NUMERIC_PRIMITIVES = Set.of("byte", "char", "short", "int", "long", "float",
            "double");

    public JTypeReference(CtTypeReference typeReference, JWeaver weaver) {
        super(typeReference, weaver);
    }

    @Override
    public CtTypeReference<?> getNodeImpl() {
        return (CtTypeReference<?>) super.getNodeImpl();
    }

    @Override
    public boolean getIsPrimitiveImpl() {
        return getNodeImpl().isPrimitive();
    }

    @Override
    public boolean getIsArrayImpl() {

        try {
            return getNodeImpl().getActualClass().isArray();
        } catch (Exception e) {
            // Do nothing, sometimes it can launch exception, such as when the type of the
            // class is not on the classpath
            return false;
        }
    }

    @Override
    public String getToStringImpl() {
        return getNameImpl();
    }

    @Override
    public String getPackageNameImpl() {
        var ctPackage = getNodeImpl().getPackage();

        return ctPackage != null ? ctPackage.getQualifiedName() : null;
    }

    @Override
    public String[] getPackageNamesImpl() {
        var packageName = getPackageNameImpl();
        return packageName != null ? packageName.split("\\.") : new String[0];
    }

    @Override
    public Boolean getIsNumericImpl() {
        var qualifiedName = getQualifiedNameImpl();

        if (getIsPrimitiveImpl()) {
            return NUMERIC_PRIMITIVES.contains(qualifiedName);
        }

        try {
            var numericClass = Class.forName(qualifiedName);
            return Number.class.isAssignableFrom(numericClass);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public boolean getIsBooleanImpl() {
        var qualifiedName = getQualifiedNameImpl();

        if (getIsPrimitiveImpl()) {
            return qualifiedName.equals("boolean");
        }

        return qualifiedName.equals(Boolean.class.getName());
    }

    @Override
    public String getQualifiedNameImpl() {
        var packageName = getPackageNameImpl();

        return packageName != null ? packageName + "." + getNodeImpl().getSimpleName() : getNodeImpl().getSimpleName();
    }
}
