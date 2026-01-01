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
 * specific language governing permissions and limitations under the License. under the License.
 */

package weaver.kadabra.joinpoints;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.abstracts.joinpoints.ATypeReference;

import java.util.Set;

public class JTypeReference<T> extends ATypeReference {

    private static final Set<String> NUMERIC_PRIMITIVES = Set.of("byte", "char", "short", "int", "long", "float",
            "double");

    private final CtTypeReference<T> typeReference;

    public JTypeReference(CtTypeReference<T> typeReference) {
        super(new JReference(typeReference));

        this.typeReference = typeReference;
    }

    public static <T> JTypeReference<T> newInstance(CtTypeReference<T> node) {
        return new JTypeReference<>(node);
    }

    @Override
    public CtElement getNode() {
        return typeReference;
    }

    @Override
    public Boolean getIsPrimitiveImpl() {
        return typeReference.isPrimitive();
    }

    @Override
    public Boolean getIsArrayImpl() {

        try {
            return typeReference.getActualClass().isArray();
        } catch (Exception e) {
            // Do nothing, sometimes it can launch exception, such as when the type of the class is not on the classpath
            return false;
        }
    }

    // @Override
    // public String getReferenceTypeImpl() {
    // return getIsArrayImpl() ? typeReference.getActualClass().getComponentType().toString()
    // : super.getReferenceTypeImpl();
    // }

    @Override
    public String toString() {
        return getNameImpl();
    }

    @Override
    public String getPackageNameImpl() {
        var ctPackage = typeReference.getPackage();

        return ctPackage != null ? ctPackage.getQualifiedName() : null;
    }

    @Override
    public String[] getPackageNamesArrayImpl() {
        var packageName = getPackageNameImpl();
        return packageName != null ? getPackageNameImpl().split(".") : new String[0];
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
    public Boolean getIsBooleanImpl() {
        var qualifiedName = getQualifiedNameImpl();

        if (getIsPrimitiveImpl()) {
            return qualifiedName.equals("boolean");
        }

        return qualifiedName.equals(Boolean.class.getName());
    }

    @Override
    public String getQualifiedNameImpl() {
        var packageName = getPackageNameImpl();

        return packageName != null ? packageName + "." + typeReference.getSimpleName() : typeReference.getSimpleName();
    }

    // @Override
    // public String toString() {
    // var type = getIsArrayImpl() ? typeReference.getActualClass().getComponentType().toString()
    // : getReferenceTypeImpl();
    //
    // return getNameImpl() + " - " + type;
    //
    // // if (getIsArrayImpl()) {
    // // return typeReference.getActualClass().getComponentType().toString();
    // // }
    // //
    // // return getReferenceTypeImpl();
    // }

}
