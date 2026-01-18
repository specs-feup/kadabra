/**
 * Copyright 2017 SPeCS.
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

import spoon.reflect.code.CtComment;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AComment;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.enums.CommentType;
import weaver.utils.weaving.ActionUtils;

public class JComment extends AComment {

    private final CtComment comment;

    private JComment(CtComment comment) {
        super(new JStatement(comment));
        this.comment = comment;
    }

    public static JComment newInstance(CtComment comment) {
        return new JComment(comment);
    }

    @Override
    public String getTypeImpl() {
        return CommentType.valueOf(comment.getCommentType().name()).getName();
    }

    @Override
    public String getContentImpl() {
        return comment.getContent();
    }

    @Override
    public CtComment getNode() {
        return comment;
    }

    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return new AJoinPoint[] { insertImplJComment(position, code) };
    }

    public AJavaWeaverJoinPoint insertImplJComment(String position, String code) {
        return ActionUtils.insertInTable(comment, code, position);
    }

    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return insertImplJComment("before", code);
    }

    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return insertImplJComment("after", code);
    }

    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return insertImplJComment("replace", code);
    }
}
