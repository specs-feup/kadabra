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

import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetStatement;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ABody;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.utils.weaving.ActionUtils.Location;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JBody<Self extends JBody<Self>> extends ABody<Self> {

    public JBody(CtBlock node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, String code) {
        return new AJoinpoint<?>[] { insertImplJBody(position, code) };
    }

    public AJoinpoint<?> insertImplJBody(InsertPosition position, String code) {
        final CtCodeSnippetStatement snippet = SnippetFactory.createSnippetStatement(code,
                getNodeImpl().getFactory());

        switch (position) {
            case REPLACE:
                getNodeImpl().getStatements().clear();
                getNodeImpl().addStatement(snippet);
                snippet.setParent(getNodeImpl());
                break;
            default:
                throw new RuntimeException(
                        "Code insertion on a block can only be done around (i.e., complete code replacement)");
        }
        snippet.setParent(getNodeImpl());

        return CtElement2JoinPoint.convert(snippet, getWeaverEngine());
    }

    @Override
    public void insertBeginImpl(String code) {
        final CtCodeSnippetStatement snippet = SnippetFactory.createSnippetStatement(code,
                getNodeImpl().getFactory());
        getNodeImpl().insertBegin(snippet);
    }

    @Override
    public void insertBeginImpl(AStatement<?> statement) {
        getNodeImpl().insertBegin((spoon.reflect.code.CtStatement) statement.getNodeImpl());
    }

    @Override
    public AJoinpoint<?> insertBeforeImpl(String code) {
        return insertImplJBody(InsertPosition.BEFORE, code);
    }

    @Override
    public AJoinpoint<?> insertAfterImpl(String code) {
        return insertImplJBody(InsertPosition.AFTER, code);
    }

    @Override
    public AJoinpoint<?> insertReplaceImpl(String code) {
        return insertImplJBody(InsertPosition.REPLACE, code);
    }

    @Override
    public AStatement<?> getLastStmtImpl() {
        if (!hasStatements()) {
            return null;
        }

        return CtElement2JoinPoint.convert(getNodeImpl().getStatement(getNodeImpl().getStatements().size() - 1),
                getWeaverEngine(),
                AStatement.class);
    }

    private boolean hasStatements() {
        return !(getNodeImpl() == null) && !getNodeImpl().getStatements().isEmpty();
    }

    @Override
    public CtBlock<?> getNodeImpl() {
        return (CtBlock<?>) super.getNodeImpl();
    }

    @Override
    public String getToStringImpl() {
        return "";
    }

}
