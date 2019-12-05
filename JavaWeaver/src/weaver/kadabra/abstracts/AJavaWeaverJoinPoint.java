package weaver.kadabra.abstracts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.lara.interpreter.weaver.interf.JoinPoint;
import org.lara.interpreter.weaver.interf.SelectOp;

import com.google.common.base.Preconditions;

import pt.up.fe.specs.util.exceptions.NotImplementedException;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtStatement;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtType;
import weaver.kadabra.abstracts.joinpoints.AExecutable;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AFile;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.kadabra.abstracts.joinpoints.AType;
import weaver.kadabra.joinpoints.JApp;
import weaver.kadabra.joinpoints.JFile;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.JoinPoints;
import weaver.utils.SpoonUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;
import weaver.utils.weaving.converters.CtExecutable2AExecutable;
import weaver.utils.weaving.converters.CtExpression2AExpression;
import weaver.utils.weaving.converters.CtStatement2AStatement;
import weaver.utils.weaving.converters.CtType2AType;

/**
 * Abstract class which can be edited by the developer. This class will not be overwritten.
 *
 * @author Lara Weaver Generator
 */
public abstract class AJavaWeaverJoinPoint extends AJoinPoint {

    /**
     * Compares the two join points based on their node reference of the used compiler/parsing tool.<br>
     * This is the default implementation for comparing two join points. <br>
     * <b>Note for developers:</b> A weaver may override this implementation in the editable abstract join point, so the
     * changes are made for all join points, or override this method in specific join points.
     */
    @Override
    public boolean compareNodes(AJoinPoint aJoinPoint) {
        return getNode().equals(aJoinPoint.getNode());
    }

    @Override
    public String getSrcCodeImpl() {
        return getCodeImpl();
    }

    @Override
    public String getCodeImpl() {
        return getNode().toString(); // temporary
    }

    @Override
    public abstract CtElement getNode();

    // public CtElement getParent() {
    // return getNode().getParent();
    // }

    @Override
    public void defLineImpl(Integer value) {
        CtElement node = getNode();
        if (node == null) {
            KadabraLog.warning("Cannot change line in this join point: " + getJoinPointType());
        }
        SourcePosition position = node.getPosition();
        if (position == null) {
            KadabraLog.warning("Cannot change line in this join point: " + getJoinPointType());
        }
        int line = position.getLine();
        KadabraLog.info("Changing line number from " + line + " to " + value);
    }

    @Override
    public void defLineImpl(String value) {
        defLineImpl(Integer.parseInt(value));
    }

    @Override
    public AJoinPoint getParentImpl() {
        CtElement parent = getNode().getParent();
        if (parent == null) {
            return null;
        }

        return CtElement2JoinPoint.convert(parent);
    }

    /**
     *
     * @param ancestorType
     * @return
     */
    public <T extends CtElement> T getAncestor(Class<T> ancestorType) {
        return SpoonUtils.getAncestor(getNode(), ancestorType);
    }

    public <T extends CtElement> Optional<T> getAncestorTry(Class<T> ancestorType) {
        return SpoonUtils.getAncestorTry(getNode(), ancestorType);
    }

    @Override
    public AJoinPoint ancestorImpl(String type) {
        Preconditions.checkNotNull(type, "Missing type of ancestor in attribute 'ancestor'");

        // if (type.equals("statement")) { // TODO: need to deal with special cases such as statement and expression
        // return stmtAncestor(type);
        // }

        AJoinPoint currentNode = getParentImpl();
        while (currentNode != null) {
            if (currentNode.instanceOf(type)) {
                return currentNode;
            }

            currentNode = currentNode.getParentImpl();
        }

        return currentNode;

        // CtElement currentNode = getNode().getParent();
        // while (currentNode != null) {
        // System.out.println("CURRENT NODE: " + currentNode.getClass());
        //
        // AJavaWeaverJoinPoint parentJp = CtElement2JoinPoint.convert(currentNode);
        //
        // if (parentJp.instanceOf(type)) {
        // return parentJp;
        // }
        // // String joinPointType = parentJp.getJoinPointType();
        // // if (joinPointType.equals(type)) {
        // // return parentJp;
        // // }
        // currentNode = currentNode.getParent();
        // }
        //
        // return null;
    }

    protected AExpression toAExpression(CtExpression<?> expression) {
        return CtExpression2AExpression.convert(expression);
    }

    protected AStatement toAStatement(CtStatement statement) {
        return CtStatement2AStatement.convert(statement);
    }

    protected AType toAType(CtType<?> type) {
        return CtType2AType.convert(type);
    }

    protected AExecutable toAExecutable(CtExecutable<?> exec) {
        return CtExecutable2AExecutable.convert(exec);
    }

    @Override
    public Integer getLineImpl() {
        SourcePosition position = getNode().getPosition();
        if (position == null) {
            return null;
        }
        return position.getLine();
        // // Check if this joinpoint is a statement
        // if (this instanceof AStatement) {
        // return ((AStatement) this).getLineImpl();
        // }
        //
        // // Try to obtain a statement
        // AJavaWeaverJoinPoint stmtAncestor = ancestorImpl("statement");
        // if (stmtAncestor instanceof AStatement) {
        // return ((AStatement) stmtAncestor).getLineImpl();
        // }
        //
        // SpecsLogs.msgInfo("attribute 'line' not implemented for join point " + getJoinPointType()
        // + " that is not inside a statement");
        //
        // return -1;
    }

    /**
     * Required, because original insertImpl returns JoinPoint, but abstract join points return AJoinPoint.
     */
    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        throw new RuntimeException("Not implemented yet for join point type '" + getJoinPointType() + "'");
    }

    @Override
    public AJoinPoint insertBeforeImpl(AJoinPoint node) {
        insertImpl("after", node);
        return node;
    }

    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return (AJoinPoint) insertImpl("before", code)[0];
    }

    @Override
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        insertImpl("after", node);
        return node;
    }

    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return (AJoinPoint) insertImpl("after", code)[0];
    }

    // @Override
    // public AJoinPoint[] insertImpl(String position, String code) {
    // return insertImpl("after", code);
    // }

    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return insertImpl("replace", jp)[0];
    }

    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return (AJoinPoint) insertImpl("replace", code)[0];
    }

    // @Override
    // public <T extends JoinPoint> AJoinPoint[] insertImpl(String position, T JoinPoint) {
    // throw new NotImplementedException(this);
    // }

    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint JoinPoint) {
        throw new NotImplementedException(this);
    }

    /**
     * Generic select function, used by the default select implementations.
     */
    @Override
    public <T extends AJoinPoint> List<? extends T> select(Class<T> joinPointClass, SelectOp op) {
        throw new RuntimeException(
                "Generic select function not implemented yet. Implement it in order to use the default implementations of select");
    }

    @Override
    public AJoinPoint[] getDescendantsArrayImpl() {

        if (this instanceof JApp) {
            JApp app = (JApp) this;

            List<AJoinPoint> descendants = new ArrayList<>();
            for (AFile file : app.selectFile()) {
                descendants.add(file);
                descendants.addAll(Arrays.asList(file.getDescendantsArrayImpl()));
            }
            // for (ALibClass libClass : app.selectLibClass()) {
            // descendants.addAll(Arrays.asList(libClass.getDescendantsArrayImpl()));
            // }

            return descendants.toArray(new AJoinPoint[0]);
        }

        if (this instanceof JFile) {
            JFile jfile = (JFile) this;

            List<AJoinPoint> descendants = new ArrayList<>();
            for (var file : jfile.getNode().getCu().getDeclaredTypes()) {
                AJavaWeaverJoinPoint type = CtElement2JoinPoint.convertTry(file).orElse(null);
                if (type == null) {
                    continue;
                }

                descendants.add(type);
                descendants.addAll(Arrays.asList(type.getDescendantsArrayImpl()));
            }

            return descendants.toArray(new AJoinPoint[0]);
        }

        return getNode().getElements(element -> true)
                .stream()
                .map(CtElement2JoinPoint::convertTry)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toArray(size -> new AJoinPoint[size]);

    }

    @Override
    public Boolean getIsStatementImpl() {
        return getNode() instanceof CtStatement;
    }

    @Override
    public Boolean getIsBlockImpl() {
        return getNode() instanceof CtBlock;
    }

    // @Override
    // public Boolean getIsBlockStatementImpl() {
    // return SpoonUtils.isStatementInBlock(getNode());
    // }
    @Override
    public AJoinPoint copyImpl() {
        return CtElement2JoinPoint.convert(getNode().clone());
        // throw new RuntimeException(".copy not implemented yet for join point " + getJoinPointType());
    }

    public List<CtElement> getChildrenNodes() {
        return SpoonUtils.getChildren(getNode());
    }

    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        return getChildrenNodes().stream()
                .map(CtElement2JoinPoint::convert)
                .toArray(size -> new AJoinPoint[size]);
    }

    @Override
    public AJoinPoint childImpl(Integer index) {
        return CtElement2JoinPoint.convert(getChildrenNodes().get(0));
    }

    @Override
    public Integer getNumChildrenImpl() {
        return getChildrenNodes().size();
    }

    @Override
    public String getAstImpl() {
        return JoinPoints.toAst(this, "");
    }

    @Override
    public String toString() {
        return getNode().toString();
    }

    @Override
    public void removeImpl() {
        getNode().delete();
    }

}
