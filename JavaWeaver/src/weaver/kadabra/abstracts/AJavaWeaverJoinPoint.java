package weaver.kadabra.abstracts;

import com.google.common.base.Preconditions;
import org.lara.interpreter.weaver.interf.JoinPoint;
import pt.up.fe.specs.util.SpecsEnums;
import pt.up.fe.specs.util.SpecsLogs;
import pt.up.fe.specs.util.exceptions.NotImplementedException;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtStatement;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.*;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.joinpoints.*;
import weaver.kadabra.spoon.extensions.launcher.JWEnvironment;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.JoinPoints;
import weaver.utils.SpoonUtils;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.converters.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract class which can be edited by the developer. This class will not be
 * overwritten.
 *
 * @author Lara Weaver Generator
 */
public abstract class AJavaWeaverJoinPoint extends AJoinPoint {

    public AJavaWeaverJoinPoint(JavaWeaver weaver) {
        super(weaver);
    }

    @Override
    public JavaWeaver getWeaverEngine() {
        return (JavaWeaver) super.getWeaverEngine();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof JoinPoint jp) {
            return same(jp);
        }

        return false;
    }

    /**
     * Compares the two join points based on their node reference of the used
     * compiler/parsing tool.<br>
     * This is the default implementation for comparing two join points. <br>
     * <b>Note for developers:</b> A weaver may override this implementation in the
     * editable abstract join point, so the changes are made for all join points, or
     * override this method in specific join points.
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
        return getWeaverEngine().getSourceCodePrinter().getSourceCode(getNode());
    }

    @Override
    public abstract CtElement getNode();

    @Override
    public void setLineImpl(int value) {
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
    public void setLineImpl(String value) {
        setLineImpl(Integer.parseInt(value));
    }

    @Override
    public AJoinPoint getParentImpl() {
        // If no node, no parent
        // This condition is a bit suspicious,
        // because if a join point returns a null node
        // (e.g., JApp or JFile), they should have their
        // own implementations of getParentImpl()
        if (getNode() == null) {
            return null;
        }

        CtElement parent = getNode().getParent();
        if (parent == null) {
            return null;
        }

        var parentJp = CtElement2JoinPoint.convert(parent, getWeaverEngine());

        // Special case: if parent is a CallStatement, return the Call instead
        if (parentJp instanceof ACallStatement) {
            return ((ACallStatement) parentJp).getCallImpl();
        }

        return parentJp;
    }

    @Override
    public AJoinPoint getAstParentImpl() {
        return getParentImpl();
    }

    /**
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
    public AJoinPoint getAncestorImpl(String type) {
        Preconditions.checkNotNull(type, "Missing type of ancestor in attribute 'ancestor'");

        AJoinPoint currentNode = getParentImpl();
        while (currentNode != null) {
            if (currentNode.instanceOf(type)) {
                return currentNode;
            }
            currentNode = currentNode.getParentImpl();
        }

        return currentNode;
    }

    protected AExpression toAExpression(CtExpression<?> expression) {
        return CtExpression2AExpression.convertToExpression(expression, getWeaverEngine());
    }

    protected AJavaWeaverJoinPoint toAStatement(CtStatement statement) {
        return CtStatement2AStatement.convert(statement, getWeaverEngine());
    }

    protected AType toAType(CtType<?> type) {
        return CtType2AType.convert(type, getWeaverEngine());
    }

    protected AExecutable toAExecutable(CtExecutable<?> exec) {
        return CtExecutable2AExecutable.convert(exec, getWeaverEngine());
    }

    @Override
    public Integer getLineImpl() {
        if (getNode() == null) {
            return null;
        }
        SourcePosition position = getNode().getPosition();
        if (position == null || !position.isValidPosition()) {
            return null;
        }

        return position.getLine();
    }

    /**
     * Required, because original insertImpl returns JoinPoint, but abstract join
     * points return AJoinPoint.
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
        return insertImpl("before", code)[0];
    }

    @Override
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        insertImpl("after", node);
        return node;
    }

    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return insertImpl("after", code)[0];
    }

    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return replaceWithImpl(jp);
    }

    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return replaceWithImpl(code);
    }

    @Override
    public AJoinPoint replaceWithImpl(AJoinPoint jp) {
        return insertImpl("replace", jp)[0];
    }

    @Override
    public AJoinPoint replaceWithImpl(String code) {
        return insertImpl("replace", code)[0];
    }

    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint JoinPoint) {
        throw new NotImplementedException(this);
    }

    @Override
    public AJoinPoint[] getDescendantsArrayImpl() {
        return getJpDescendantsStream().toArray(size -> new AJoinPoint[size]);
    }

    @Override
    public Boolean getIsStatementImpl() {
        return this instanceof AStatement;
        // return getNode() instanceof CtStatement;
    }

    @Override
    public Boolean getIsBlockImpl() {
        return getNode() instanceof CtBlock;
    }

    @Override
    public AJoinPoint copyImpl() {
        // Clone the node
        var copy = getNode().clone();
        // Set the parent
        copy.setParent(getNode().getParent());

        return CtElement2JoinPoint.convert(copy, getWeaverEngine());
    }

    private List<? extends CtElement> getChildrenNodes() {
        return SpoonUtils.getChildren(getNode());
    }

    @Override
    public Stream<JoinPoint> getJpChildrenStream() {
        return Arrays.stream(getChildrenArrayImpl());
    }

    @Override
    public JoinPoint getJpParent() {
        return getParentImpl();
    }

    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        return getChildrenNodes().stream()
                .map(node -> CtElement2JoinPoint.convert(node, getWeaverEngine()))
                .toArray(size -> new AJoinPoint[size]);
    }

    @Override
    public AJoinPoint childImpl(Integer index) {
        return getChildrenArrayImpl()[index];
    }

    @Override
    public Integer getNumChildrenImpl() {
        return getChildrenArrayImpl().length;
    }

    @Override
    public String getAstImpl() {
        var node = getNode();

        // If no node, start from join point
        if (node == null) {
            return JoinPoints.toAst(this, "");
        }

        // This method is more robust regarding the initial node
        return SpoonUtils.toAst(getNode(), "", getWeaverEngine());
    }

    @Override
    public String toString() {
        return getNode().toString();
    }

    @Override
    public void removeImpl() {
        // Delete from annotations
        JWEnvironment env = ActionUtils.getKadabraEnvironment(getWeaverEngine().getFactory().getSpoonFactory());
        // TODO: This remove can be optimized
        env.getTable().remove(getNode());

        getNode().delete();
    }

    @Override
    public String[] getModifiersArrayImpl() {
        return modifiersToString(SpoonUtils.getModifiers(getNode()));
    }

    public String[] modifiersToString(Collection<ModifierKind> modifiers) {
        return modifiers.stream()
                .map(ModifierKind::name)
                .toArray(length -> new String[length]);
    }

    @Override
    public Boolean hasModifierImpl(String modifier) {
        ModifierKind modifierKind = null;
        try {
            modifierKind = ModifierKind.valueOf(modifier.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Invalid modifier '" + modifier + "', please use one of: "
                    + Arrays.toString(ModifierKind.values()));
        }

        return SpoonUtils.getModifiers(getNode()).contains(modifierKind);
    }

    @Override
    public Boolean getIsFinalImpl() {
        return SpoonUtils.getModifiers(getNode()).contains(ModifierKind.FINAL);
    }

    @Override
    public Boolean getIsStaticImpl() {
        return SpoonUtils.getModifiers(getNode()).contains(ModifierKind.STATIC);
    }

    @Override
    public AAnnotation[] getAnnotationsArrayImpl() {

        return getNode().getAnnotations().stream()
                .map(annotation -> (AAnnotation) SelectUtils.expression2JoinPoint(annotation, getWeaverEngine()))
                .collect(Collectors.toList())
                .toArray(size -> new AAnnotation[size]);
    }

    @Override
    public void removeAnnotationImpl(AAnnotation annotation) {
        getNode().removeAnnotation((CtAnnotation<?>) annotation.getNode());
    }

    @Override
    public String getIdImpl() {
        var node = getNode();
        return node.getClass().getSimpleName() + "_" + node.hashCode();
    }

    @Override
    public AJoinPoint[] getLeftArrayImpl() {
        var parent = getParentImpl();

        if (parent == null) {
            SpecsLogs.info("$jp.left: no parent, could not fetch siblings");
            return new AJoinPoint[0];
        }

        var siblings = parent.getChildrenArrayImpl();

        // Find self index
        int selfIndex = -1;
        for (int i = 0; i < siblings.length; i++) {
            // Using equality on purpose
            if (siblings[i].getNode() == getNode()) {
                selfIndex = i;
                break;
            }
        }

        if (selfIndex == -1) {
            SpecsLogs.info("$jp.left: could not find self in siblings");
            return new AJoinPoint[0];
        }

        return Arrays.copyOfRange(siblings, 0, selfIndex);
    }

    @Override
    public AJoinPoint[] getRightArrayImpl() {
        var parent = getParentImpl();

        if (parent == null) {
            SpecsLogs.info("$jp.right: no parent, could not fetch siblings");
            return new AJoinPoint[0];
        }

        var siblings = parent.getChildrenArrayImpl();

        // Find self index
        int selfIndex = -1;
        for (int i = 0; i < siblings.length; i++) {
            // Using equality on purpose
            if (siblings[i].getNode() == getNode()) {
                selfIndex = i;
                break;
            }
        }

        if (selfIndex == -1) {
            SpecsLogs.info("$jp.right: could not find self in siblings");
            return new AJoinPoint[0];
        }

        return Arrays.copyOfRange(siblings, selfIndex + 1, siblings.length);
    }

    @Override
    public void removeModifierImpl(String modifier) {
        var modifiersSet = SpoonUtils.getModifiers(getNode());

        if (modifiersSet.isEmpty()) {
            SpecsLogs.debug(() -> "No modifiers to remove");
            return;
        }

        // Convert modifier to enum
        var modifierEnum = SpecsEnums.valueOfTry(ModifierKind.class, modifier.toUpperCase());

        if (modifierEnum.isEmpty()) {
            SpecsLogs.info("Could not obtain modifier from string '" + modifier + "'. Available modifiers: "
                    + Arrays.toString(ModifierKind.values()));
            return;
        }

        var newModifiers = new HashSet<>(modifiersSet);

        var hadModifier = newModifiers.remove(modifierEnum.get());
        if (hadModifier) {
            SpoonUtils.setModifiers(getNode(), newModifiers);
        } else {
            SpecsLogs.debug(() -> "Could not remove modifier '" + modifier + "', not present in node");
        }

    }

    @Override
    public void setModifiersImpl(String[] modifiers) {

        Set<ModifierKind> modifiersSet = new HashSet<>();

        for (String modifier : modifiers) {
            // Convert modifier to enum
            var modifierEnum = SpecsEnums.valueOfTry(ModifierKind.class, modifier.toUpperCase());

            if (modifierEnum.isEmpty()) {
                SpecsLogs.info("Could not obtain modifier from string '" + modifier + "'. Available modifiers: "
                        + Arrays.toString(ModifierKind.values()));
                return;
            }

            // Add modifier to modifiersSet
            modifiersSet.add(modifierEnum.get());
        }

        SpoonUtils.setModifiers(getNode(), modifiersSet);
    }

    @Override
    public Boolean getIsInsideLoopHeaderImpl() {
        var node = getNode();

        Optional<CtLoop> ancestor = SpoonUtils.getAncestorTry(node, CtLoop.class);
        if (!ancestor.isPresent()) {
            return false;
        }
        CtLoop loop = ancestor.get();

        return SpoonUtils.insideHeader(loop, node);
    }
}
