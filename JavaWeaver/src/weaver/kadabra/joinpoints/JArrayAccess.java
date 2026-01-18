/**
 * Copyright 2015 SPeCS.
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

package weaver.kadabra.joinpoints;

import spoon.reflect.code.CtArrayAccess;
import spoon.reflect.code.CtArrayWrite;
import spoon.reflect.code.CtExpression;
import weaver.kadabra.abstracts.joinpoints.AArrayAccess;
import weaver.kadabra.abstracts.joinpoints.ATypeReference;
import weaver.kadabra.enums.RefType;

public class JArrayAccess<T, E extends CtExpression<?>> extends AArrayAccess {

    private final CtArrayAccess<T, E> node;

    protected JArrayAccess(CtArrayAccess<T, E> acess) {
        super(new JExpression<>(acess));
        node = acess;
    }

    public static <T, E extends CtExpression<?>> JArrayAccess<T, E> newInstance(CtArrayAccess<T, E> access) {

        return new JArrayAccess<>(access);
    }

    @Override
    public String getReferenceImpl() {
        return node instanceof CtArrayWrite ? RefType.WRITE.getName() : RefType.READ.getName();
    }

    @Override
    public ATypeReference getTypeReferenceImpl() {
        return new JTypeReference<>(node.getType());
        // return CtTypeReferenceUtils.getType(node.getType());
    }

    @Override
    public String getTypeImpl() {
        return getTypeReferenceImpl().toString();
    }

    @Override
    public CtArrayAccess<T, E> getNode() {
        return node;
    }
}
