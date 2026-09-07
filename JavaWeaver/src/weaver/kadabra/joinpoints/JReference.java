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

import spoon.reflect.declaration.CtElement;
import spoon.reflect.reference.CtReference;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.AReference;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JReference<Self extends JReference<Self>> extends AReference<Self> {

    public JReference(CtReference node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public CtReference getNodeImpl() {
        return (CtReference) super.getNodeImpl();
    }

    @Override
    public String getNameImpl() {
        if (getNodeImpl() == null) {
            return null;
        }

        return getNodeImpl().getSimpleName();
    }

    @Override
    public AJoinpoint<?> getDeclarationImpl() {
        var decl = getNodeImpl().getDeclaration();
        if (decl == null) {
            return null;
        }

        return CtElement2JoinPoint.convert(decl, getWeaverEngine());
    }

    @Override
    public String getTypeImpl() {
        String type = getNodeImpl().getClass().getSimpleName();
        if (type.startsWith("Ct")) {
            type = type.substring(2);
        }
        if (type.endsWith("ReferenceImpl")) {
            type = type.substring(0, type.length() - "ReferenceImpl".length());
        }

        return type;
    }

    @Override
    public String getToStringImpl() {
        return getNameImpl() + " - " + getTypeImpl();
    }

}
