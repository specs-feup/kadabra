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

import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtStatement;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.ABody;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.utils.weaving.ActionUtils.Location;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JBody<T> extends ABody {

    private final CtBlock<T> node;

    private JBody(CtBlock<T> block, JavaWeaver weaver) {
        super(new JStatement(block, weaver), weaver);
        node = block;
    }

    public static <T> JBody<T> newInstance(CtBlock<T> block, JavaWeaver weaver) {
        return new JBody<>(block, weaver);
    }

    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return new AJoinPoint[] { insertImplJBody(position, code) };
    }

    public AJavaWeaverJoinPoint insertImplJBody(String position, String code) {
        final CtCodeSnippetStatement snippet = SnippetFactory.createSnippetStatement(code, node.getFactory());

        switch (Location.valueOf(position.toUpperCase())) {
            case AROUND:
            case REPLACE:
                node.getStatements().clear();
                node.addStatement(snippet);
                snippet.setParent(node);
                break;
            default:
                throw new RuntimeException(
                        "Code insertion on a block can only be done around (i.e., complete code replacement)");
        }
        snippet.setParent(node);

        return CtElement2JoinPoint.convert(snippet, getWeaverEngine());
    }

    @Override
    public void insertBeginImpl(String code) {
        final CtCodeSnippetStatement snippet = SnippetFactory.createSnippetStatement(code, node.getFactory());
        node.insertBegin(snippet);
    }

    @Override
    public void insertBeginImpl(AStatement statement) {
        node.insertBegin((CtStatement) statement.getNode());
    }

    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return insertImplJBody("before", code);
    }

    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return insertImplJBody("after", code);
    }

    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return insertImplJBody("replace", code);
    }

    @Override
    public AStatement getLastStmtImpl() {
        if (!hasStatements()) {
            return null;
        }

        return CtElement2JoinPoint.convert(node.getStatement(node.getStatements().size() - 1), getWeaverEngine(),
                AStatement.class);
    }

    private boolean hasStatements() {
        return !(node == null) && !node.getStatements().isEmpty();
    }

    @Override
    public CtBlock<T> getNode() {
        return node;
    }

    @Override
    public String toString() {
        return "";
    }

}
