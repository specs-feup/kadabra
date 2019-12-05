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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AAssignment;
import weaver.kadabra.abstracts.joinpoints.ABody;
import weaver.kadabra.abstracts.joinpoints.ACall;
import weaver.kadabra.abstracts.joinpoints.AComment;
import weaver.kadabra.abstracts.joinpoints.AIf;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.ALocalVariable;
import weaver.kadabra.abstracts.joinpoints.ALoop;
import weaver.kadabra.abstracts.joinpoints.APragma;
import weaver.kadabra.abstracts.joinpoints.AReturn;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.utils.scanners.NodeSearcher;
import weaver.utils.weaving.ActionUtils.Location;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JBody<T> extends ABody {

    private final CtBlock<T> node;

    private JBody(CtBlock<T> block) {
        super(new JStatement(block));
        node = block;
    }

    public static <T> JBody<T> newInstance(CtBlock<T> block) {
        return new JBody<>(block);
    }
    // public JBody(CtStatement node) {
    // this.node = node;
    // }

    @Override
    public List<? extends ALoop> selectLoop() {
        final List<JLoop> loops = SelectUtils.select(node, CtLoop.class, JLoop::newInstance);
        return loops;
    }

    @Override
    public List<? extends AIf> selectIf() {
        final List<JIf> ifs = SelectUtils.select(node, CtIf.class, JIf::newInstance);
        return ifs;
    }

    // @Override
    // public List<? extends AVar> selectVar() {
    // final List<JVar> vars = WeavingUtils.select(node,
    // CtVariableAccess.class, JVar::new);
    // return vars;
    //
    // }

    @Override
    public List<? extends ACall> selectCall() {
        return SelectUtils.selectCall(node);
        // final List<JCall<?>> calls = SelectUtils.select(node, CtInvocation.class, JCall::newInstance);
        // return calls;
    }

    @Override
    public List<? extends APragma> selectPragma() {
        List<CtComment> comments = NodeSearcher.list(CtComment.class, node, Collections.emptyList())
                .stream()
                .filter(JPragma::isPragma)
                .collect(Collectors.toList());
        final List<JPragma> pragmas = SelectUtils.nodeList2JoinPointList(comments, JPragma::newInstance);
        return pragmas;
    }

    @Override
    public List<? extends AComment> selectComment() {
        final List<JComment> comments = SelectUtils.select(node, CtComment.class, JComment::newInstance);
        return comments;
    }

    @Override
    public List<? extends ALocalVariable> selectDeclaration() {
        final List<ALocalVariable> comments = SelectUtils.select(node, CtLocalVariable.class,
                JLocalVariable::newInstance);
        return comments;

    }

    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return new AJoinPoint[] { insertImplJBody(position, code) };
    }

    public AJavaWeaverJoinPoint insertImplJBody(String position, String code) {
        final CtCodeSnippetStatement snippet = SnippetFactory.createSnippetStatement(code, node.getFactory());

        switch (Location.valueOf(position.toUpperCase())) {
        // case BEFORE:
        // node.insertBegin(snippet);
        // break;
        // case AFTER:
        // node.insertEnd(snippet);
        // break;
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

        return CtElement2JoinPoint.convert(snippet);
        // return SelectUtils.node2JoinPoint(snippet, JSnippet::newInstance);
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
    public List<? extends AStatement> selectStatement() {
        if (node.getStatements() == null || node.getStatements().isEmpty()) {
            return Collections.emptyList();
        }
        return SelectUtils.nodeList2JoinPointList(node.getStatements(), this::toAStatement);
    }

    @Override
    public List<? extends AStatement> selectStmt() {
        return selectStatement();
    }

    @Override
    public List<? extends AStatement> selectFirstStmt() {
        if (!hasStatements()) {
            return new ArrayList<>();
        }

        CtStatement statement = node.getStatements().get(0);
        return SelectUtils.statement2JoinPointList(statement);
    }

    @Override
    public List<? extends AStatement> selectLastStmt() {
        if (!hasStatements()) {
            return new ArrayList<>();
        }

        CtStatement statement = node.getStatement(node.getStatements().size() - 1);
        return SelectUtils.statement2JoinPointList(statement);
    }

    @Override
    public List<? extends AReturn> selectReturn() {
        @SuppressWarnings("unchecked")
        List<? extends AReturn> selected = SelectUtils.select(node, CtReturn.class, JReturn::newInstance);
        return selected;
    }

    private boolean hasStatements() {
        return !(node == null) && !node.getStatements().isEmpty();
    }

    @Override
    public CtBlock<T> getNode() {
        return node;
    }

    @Override
    public List<? extends AAssignment> selectAssignment() {
        @SuppressWarnings("unchecked")
        List<? extends AAssignment> select = SelectUtils.select(node, CtAssignment.class, JAssignment::newInstance);
        return select;
    }

    // @Override
    // public int getLine() {
    //
    // return node.getPosition().getLine();
    // }
    //
    // @Override
    // public int getEndLine() {
    // return node.getPosition().getEndLine();
    // }

    @Override
    public String toString() {
        return "";
    }

}
