/**
 * Copyright 2015 SPeCS.
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

import weaver.kadabra.abstracts.joinpoints.AArrayAccess;
import spoon.reflect.code.CtArrayAccess;
import spoon.reflect.code.CtArrayWrite;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ATypeReference;
import weaver.kadabra.enums.RefType;

public class JArrayAccess<Self extends JArrayAccess<Self>> extends AArrayAccess<Self> {

    public JArrayAccess(CtArrayAccess node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public RefType getReferenceImpl() {
        return getNodeImpl() instanceof CtArrayWrite ? RefType.WRITE : RefType.READ;
    }

    @Override
    public ATypeReference<?> getTypeReferenceImpl() {
        return new JTypeReference<>(getNodeImpl().getType(), getWeaverEngine());
    }

    @Override
    public String getTypeImpl() {
        return getTypeReferenceImpl().toString();
    }

    @Override
    public CtArrayAccess<?, ?> getNodeImpl() {
        return (CtArrayAccess<?, ?>) super.getNodeImpl();
    }
}
