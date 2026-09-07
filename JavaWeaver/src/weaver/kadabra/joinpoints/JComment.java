/**
 * Copyright 2017 SPeCS.
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

import spoon.reflect.code.CtComment;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AComment;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.enums.CommentType;
import weaver.utils.weaving.ActionUtils;

public class JComment<Self extends JComment<Self>> extends AComment<Self> {

    public JComment(CtComment comment, JWeaver weaver) {
        super(comment, weaver);
    }

    @Override
    public CommentType getTypeImpl() {
        return CommentType.valueOf(getNodeImpl().getCommentType().name());
    }

    @Override
    public String getContentImpl() {
        return getNodeImpl().getContent();
    }

    @Override
    public CtComment getNodeImpl() {
        return (CtComment) super.getNodeImpl();
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, String code) {
        return new AJoinpoint<?>[] { insertImplJComment(position, code) };
    }

    public AJoinpoint<?> insertImplJComment(InsertPosition position, String code) {
        return ActionUtils.insertInTable(getNodeImpl(), code, position.name().toLowerCase(), getWeaverEngine());
    }

    @Override
    public AJoinpoint<?> insertBeforeImpl(String code) {
        return insertImplJComment(InsertPosition.BEFORE, code);
    }

    @Override
    public AJoinpoint<?> insertAfterImpl(String code) {
        return insertImplJComment(InsertPosition.AFTER, code);
    }

    @Override
    public AJoinpoint<?> insertReplaceImpl(String code) {
        return insertImplJComment(InsertPosition.REPLACE, code);
    }
}
