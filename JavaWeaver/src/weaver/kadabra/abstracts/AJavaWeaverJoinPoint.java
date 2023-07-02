package weaver.kadabra.abstracts;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.lara.interpreter.weaver.interf.JoinPoint;
import org.lara.interpreter.weaver.interf.SelectOp;

import com.google.common.base.Preconditions;

import pt.up.fe.specs.util.exceptions.NotImplementedException;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtStatement;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtModifiable;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.joinpoints.AAnnotation;
import weaver.kadabra.abstracts.joinpoints.ACallStatement;
import weaver.kadabra.abstracts.joinpoints.AExecutable;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.kadabra.abstracts.joinpoints.AType;
import weaver.kadabra.spoon.extensions.launcher.JWEnvironment;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.JoinPoints;
import weaver.utils.SpoonUtils;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;
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

    // public static final Map<CtElement, CtElement> CLONED_NODES = new HashMap<>();

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
        // return getNode().getFactory().getEnvironment().createPrettyPrinter().printElement(getNode());

        return JavaWeaver.getJavaWeaver().getSourceCodePrinter().getSourceCode(getNode());

        // return new DefaultJavaPrettyPrinter(getNode().getFactory().getEnvironment()).printElement(getNode());

        // return new SourceCodePrinter(getNode().getFactory().getEnvironment()).printElement(getNode());

        // DefaultJavaPrettyPrinter printer = JavaWeaver.getJavaWeaver().getPrinter();
        // var node = getNode();
        // String errorMessage = "";
        // try {
        // // we do not want to compute imports of for CtImport and CtReference
        // // as it may change the print of a reference
        // if (!(node instanceof CtImport) && !(node instanceof CtReference)) {
        // printer.getImportsContext().computeImports(this);
        // }
        // printer.scan(node);
        // } catch (ParentNotInitializedException ignore) {
        // SpecsLogs.debug("Could not get code for " + this + ": " + ignore);
        // // throw new RuntimeException("Could not get code for node " + this, ignore);
        // // LOGGER.error(ERROR_MESSAGE_TO_STRING, ignore);
        // // errorMessage = ERROR_MESSAGE_TO_STRING;
        // }
        // // in line-preservation mode, newlines are added at the beginning to matches the lines
        // // removing them from the toString() representation
        // return printer.toString().replaceFirst("^\\s+", "") + errorMessage;

        // return getNode().toString(); // temporary
        // return getNode().toString() + "\n"; // temporary
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

        // System.out.println("JP: " + getClass());
        // System.out.println("NODE: " + getNode().getClass());
        // System.out.println("PARENT J: " + parent.getClass());

        var parentJp = CtElement2JoinPoint.convert(parent);
        // System.out.println("PARENT JP: " + parentJp.getClass());
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
        // System.out.println("ANCESTOR OF " + this + " that is a " + type);
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
        return CtExpression2AExpression.convertToExpression(expression);
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
        if (getNode() == null) {
            return null;
        }
        SourcePosition position = getNode().getPosition();
        if (position == null || !position.isValidPosition()) {
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

    // @Override
    // public AJoinPoint[] insertImpl(String position, String code) {
    // return insertImpl("after", code);
    // }

    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        // return insertImpl("replace", jp)[0];
        return replaceWithImpl(jp);
    }

    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        // return insertImpl("replace", code)[0];
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

        return getJpDescendantsStream().toArray(size -> new AJoinPoint[size]);
        /*
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
        */
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

    // @Override
    // public Boolean getIsBlockStatementImpl() {
    // return SpoonUtils.isStatementInBlock(getNode());
    // }
    @Override
    public AJoinPoint copyImpl() {
        // Clone the node
        var copy = getNode().clone();
        // Set the parent
        copy.setParent(getNode().getParent());

        // CLONED_NODES.put(copy, getNode());

        return CtElement2JoinPoint.convert(copy);
        // throw new RuntimeException(".copy not implemented yet for join point " + getJoinPointType());
    }

    private List<? extends CtElement> getChildrenNodes() {
        return SpoonUtils.getChildren(getNode());
    }

    @Override
    public Stream<JoinPoint> getJpChildrenStream() {
        // return getChildrenNodes().stream()
        // .map(CtElement2JoinPoint::convert);
        return Arrays.stream(getChildrenArrayImpl());
    }

    @Override
    public JoinPoint getJpParent() {
        return getParentImpl();
    }

    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        return getChildrenNodes().stream()
                .map(CtElement2JoinPoint::convert)
                .toArray(size -> new AJoinPoint[size]);
    }

    @Override
    public AJoinPoint childImpl(Integer index) {
        return getChildrenArrayImpl()[index];
        // return CtElement2JoinPoint.convert(getChildrenNodes().get(index));
    }

    @Override
    public Integer getNumChildrenImpl() {
        return getChildrenArrayImpl().length;
        // return getChildrenNodes().size();
    }

    @Override
    public String getAstImpl() {
        return JoinPoints.toAst(this, "");
    }

    @Override
    public String toString() {
        // return getJoinPointType();
        return getNode().toString();
    }

    @Override
    public void removeImpl() {
        // Delete from annotations
        JWEnvironment env = ActionUtils.getKadabraEnvironment(JavaWeaver.getFactory().getSpoonFactory());
        // TODO: This remove can be optimized
        // System.out.println("NODE HAS PARENT? " + getNode().isParentInitialized());
        // System.out.println("NODE PARENT? " + getNode().getParent());
        env.getTable().remove(getNode());

        // getNode().replace(Collections.emptyList()); // Spoon 6
        getNode().delete(); // Spoon 8

    }

    /**
     * If node implements CtModifiable, returns the modifiers. Otherwise, returns empty set.
     * 
     * @return
     */
    // @SuppressWarnings("unchecked")
    public Set<ModifierKind> getModifiersInternal() {
        var node = getNode();

        if (node instanceof CtModifiable) {
            return ((CtModifiable) node).getModifiers();
        }

        return Collections.emptySet();
        //
        // // Choose best method
        // Method invokingMethod = SpecsSystem.getMethod(getNode().getClass(), "getModifiers");
        //
        // // Could not find method, return empty set
        // if (invokingMethod == null) {
        // return Collections.emptySet();
        //
        // }
        //
        // // Invoke method
        // try {
        // return (Set<ModifierKind>) invokingMethod.invoke(getNode());
        // } catch (Exception e) {
        // throw new RuntimeException("Exception while calling getModifiers(): ", e);
        // }
    }

    @Override
    public String[] getModifiersArrayImpl() {
        return getModifiersInternal().stream()
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

        return getModifiersInternal().contains(modifierKind);
        // var modifierLowerCase = modifier.toLowerCase();
        //
        // return Arrays.stream(getModifiersArrayImpl())
        // .map(String::toLowerCase)
        // .filter(currentModifier -> currentModifier.equals(modifierLowerCase))
        // .findFirst()
        // .isPresent();
    }

    @Override
    public Boolean getIsFinalImpl() {
        return getModifiersInternal().contains(ModifierKind.FINAL);
    }

    @Override
    public Boolean getIsStaticImpl() {
        return getModifiersInternal().contains(ModifierKind.STATIC);
    };

    @Override
    public AAnnotation[] getAnnotationsArrayImpl() {

        return getNode().getAnnotations().stream()
                .map(annotation -> (AAnnotation) SelectUtils.expression2JoinPoint(annotation))
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
}
