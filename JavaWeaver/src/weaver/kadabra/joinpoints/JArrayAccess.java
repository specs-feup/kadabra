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
 * specific language governing permissions and limitations under the License. under the License.
 */

package weaver.kadabra.joinpoints;

import java.util.List;

import spoon.reflect.code.CtArrayAccess;
import spoon.reflect.code.CtArrayWrite;
import spoon.reflect.code.CtExpression;
import weaver.kadabra.abstracts.joinpoints.AArrayAccess;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.enums.RefType;
import weaver.utils.element.CtTypeReferenceUtils;
import weaver.utils.weaving.SelectUtils;

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
    public RefType getReferenceImpl() {
        return node instanceof CtArrayWrite ? RefType.WRITE : RefType.READ;
    }

    @Override
    public String getTypeImpl() {

        return CtTypeReferenceUtils.getType(node.getType());
    }

    @Override
    public String toString() {
        return node.toString();
    }

    @Override
    public CtArrayAccess<T, E> getNode() {
        return node;
    }

    @Override
    public List<? extends AExpression> selectTarget() {
        List<? extends AExpression> select = SelectUtils.expression2JoinPointList(node.getTarget());
        return select;
    }

    @Override
    public List<? extends AExpression> selectIndex() {
        List<? extends AExpression> select = SelectUtils.expression2JoinPointList(node.getIndexExpression());
        return select;
    }

}
