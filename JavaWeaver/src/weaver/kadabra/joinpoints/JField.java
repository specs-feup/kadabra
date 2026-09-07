/**
 * Copyright 2015 SPeCS Research Group.
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

import org.lara.interpreter.weaver.interf.enums.InsertPosition;

import spoon.reflect.declaration.CtField;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AField;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.utils.weaving.ActionUtils;

public class JField<Self extends JField<Self>> extends AField<Self> {

    public JField(CtField node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public CtField<?> getNodeImpl() {
        return (CtField<?>) super.getNodeImpl();
    }

    @Override
    public String getDeclaratorImpl() {
        return getNodeImpl().getDeclaringType().getQualifiedName();
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, AJoinpoint<?> code) {
        return new AJoinpoint<?>[] { insertImplJField(position, code.getNodeImpl()) };
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, String code) {
        return new AJoinpoint<?>[] { insertImplJField(position, code) };
    }

    public AJoinpoint<?> insertImplJField(InsertPosition position, spoon.reflect.declaration.CtElement code) {
        return ActionUtils.insertMember(getNodeImpl(), code, position.name().toLowerCase(), getWeaverEngine());
    }

    public AJoinpoint<?> insertImplJField(InsertPosition position, String code) {
        return ActionUtils.insertMember(getNodeImpl(), code, position.name().toLowerCase(), getWeaverEngine());
    }

    @Override
    public AJoinpoint<?> insertBeforeImpl(String code) {
        return insertImplJField(InsertPosition.BEFORE, code);
    }

    @Override
    public AJoinpoint<?> insertAfterImpl(String code) {
        return insertImplJField(InsertPosition.AFTER, code);
    }

    @Override
    public AJoinpoint<?> insertReplaceImpl(String code) {
        return insertImplJField(InsertPosition.REPLACE, code);
    }

    @Override
    public String getStaticAccessImpl() {
        return getDeclaratorImpl() + "." + getNameImpl();
    }

    @Override
    public String getToStringImpl() {
        return getNameImpl();
    }
}
