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

import org.lara.interpreter.weaver.interf.enums.InsertPosition;

import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import tdrc.utils.StringUtils;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.converters.CtStatement2AStatement;

public class JStatement<Self extends JStatement<Self>> extends AStatement<Self> {

    public JStatement(CtStatement node, JWeaver weaver) {
        super(node, weaver);
    }

    public static AJoinpoint<?> newInstance(CtStatement node, JWeaver weaver) {
        return CtStatement2AStatement.convert(node, weaver);
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, AJoinpoint<?> code) {
        return new AJoinpoint<?>[] { insertImplJStatement(position, (CtElement) code.getNodeImpl()) };
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, String code) {
        return new AJoinpoint<?>[] { insertImplJStatement(position, code) };
    }

    public AJoinpoint<?> insertImplJStatement(InsertPosition position, CtElement code) {
        return ActionUtils.insert(position.name().toLowerCase(), code, getNodeImpl(), getWeaverEngine());
    }

    public AJoinpoint<?> insertImplJStatement(InsertPosition position, String code) {
        return ActionUtils.insert(position.name().toLowerCase(), code, getNodeImpl(), getWeaverEngine());
    }

    @Override
    public AJoinpoint<?> insertBeforeImpl(String code) {
        return insertImplJStatement(InsertPosition.BEFORE, code);
    }

    @Override
    public AJoinpoint<?> insertAfterImpl(String code) {
        return insertImplJStatement(InsertPosition.AFTER, code);
    }

    @Override
    public AJoinpoint<?> insertReplaceImpl(String code) {
        return insertImplJStatement(InsertPosition.REPLACE, code);
    }

    @Override
    public CtStatement getNodeImpl() {
        return (CtStatement) super.getNodeImpl();
    }

    @Override
    public int getEndLineImpl() {
        return getNodeImpl().getPosition().getEndLine();
    }

    @Override
    public String getKindImpl() {
        String tempType = getNodeImpl().getClass().getSimpleName().replace("Ct", "").replace("Impl", "");
        return StringUtils.firstCharToLower(tempType);
    }
}
