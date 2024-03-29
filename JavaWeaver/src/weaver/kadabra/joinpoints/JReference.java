/**
 * Copyright 2019 SPeCS.
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

import spoon.reflect.declaration.CtElement;
import spoon.reflect.reference.CtReference;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AReference;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JReference extends AReference {

    public static JReference newInstance(CtReference node) {
        return new JReference(node);
    }

    private final CtReference node;

    public JReference(CtReference node) {
        this.node = node;
    }

    @Override
    public CtElement getNode() {
        return node;
    }

    @Override
    public String getNameImpl() {
        if (node == null) {
            return null;
        }

        return node.getSimpleName();
    }

    @Override
    public AJoinPoint getDeclarationImpl() {
        var decl = node.getDeclaration();
        if (decl == null) {
            return null;
        }

        return CtElement2JoinPoint.convert(decl);
    }

    @Override
    public String getTypeImpl() {
        String type = node.getClass().getSimpleName();
        if (type.startsWith("Ct")) {
            type = type.substring(2);
        }
        if (type.endsWith("ReferenceImpl")) {
            type = type.substring(0, type.length() - "ReferenceImpl".length());
        }

        return type;
    }

    // @Override
    // public ATypeReference getTypeImpl() {
    // return new JTypeReference<>(node);
    // String type = node.getClass().getSimpleName();
    // if (type.startsWith("Ct")) {
    // type = type.substring(2);
    // }
    // if (type.endsWith("ReferenceImpl")) {
    // type = type.substring(0, type.length() - "ReferenceImpl".length());
    // }
    //
    // return type;
    // }

    @Override
    public String toString() {
        return getNameImpl() + " - " + getTypeImpl();
    }

}
