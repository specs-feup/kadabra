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
 * specific language governing permissions and limitations under the License. under the License.
 */

package weaver.kadabra.spoon.extensions.nodes;

import java.util.ArrayList;
import java.util.List;

import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.ParentNotInitializedException;
import spoon.support.reflect.code.CtCommentImpl;

public class CtCommentWrapper extends CtCommentImpl {

    private List<CtElement> before;
    private List<CtElement> after;
    private List<CtElement> replace;

    /**
     * 
     */
    private static final long serialVersionUID = 5042937303652292619L;

    public CtCommentWrapper(CtComment toWrap) {
        replaceCommentWithThis(toWrap);
        before = new ArrayList<>();
        after = new ArrayList<>();
        replace = new ArrayList<>();
    }

    private void replaceCommentWithThis(CtComment toWrap) {
        this.setPosition(toWrap.getPosition());
        this.setFactory(toWrap.getFactory());
        this.setLabel(toWrap.getLabel());
        this.setContent(toWrap.getContent());
        this.setCommentType(toWrap.getCommentType());
        CtElement toWrapParent = toWrap.getParent();
        // List<CtComment> parentComments = toWrapParent.getComments();
        // int indexOf = parentComments.indexOf(toWrap);
        // parentComments.set(indexOf, this);
        toWrapParent.removeComment(toWrap);
        toWrapParent.addComment(this);
        // this.setParent(toWrapParent);
    }

    @Override
    public <T extends CtStatement> T insertBefore(CtStatement statement) throws ParentNotInitializedException {
        before.add(statement);
        @SuppressWarnings("unchecked") // same as in CtStatementImpl
        T _this = (T) this;
        return _this;
    }

    @Override
    public <T extends CtStatement> T insertAfter(CtStatement statement) throws ParentNotInitializedException {
        after.add(statement);
        @SuppressWarnings("unchecked") // same as in CtStatementImpl
        T _this = (T) this;
        return _this;
    }

    @Override
    public void replace(CtElement element) {
        replace.add(element);
    }

    /**
     * @return the before
     */
    public List<CtElement> getBefore() {
        return before;
    }

    /**
     * @return the after
     */
    public List<CtElement> getAfter() {
        return after;
    }

    /**
     * @return the replace
     */
    public List<CtElement> getReplace() {
        return replace;
    }

    public boolean isReplaced() {
        return getReplace().isEmpty();
    }

    public boolean hasBefores() {

        return getBefore().isEmpty();
    }

    public boolean hasAfters() {

        return getAfter().isEmpty();
    }
}
