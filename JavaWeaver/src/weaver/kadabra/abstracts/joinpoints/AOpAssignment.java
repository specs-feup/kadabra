package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.exception.ActionException;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

/**
 * Auto-Generated class for join point AOpAssignment
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class AOpAssignment extends AAssignment {

    protected AAssignment aAssignment;

    /**
     * 
     */
    public AOpAssignment(AAssignment aAssignment){
        super(aAssignment);
        this.aAssignment = aAssignment;
    }
    /**
     * 
     * @param operator 
     */
    public void setOperatorImpl(String operator) {
        throw new UnsupportedOperationException(get_class()+": Action setOperator not implemented ");
    }

    /**
     * 
     * @param operator 
     */
    public final void setOperator(String operator) {
        try {
        	this.setOperatorImpl(operator);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setOperator", e);
        }
    }

    /**
     * Get value on attribute lhs
     * @return the attribute's value
     */
    @Override
    public AExpression getLhsImpl() {
        return this.aAssignment.getLhsImpl();
    }

    /**
     * Get value on attribute operator
     * @return the attribute's value
     */
    @Override
    public String getOperatorImpl() {
        return this.aAssignment.getOperatorImpl();
    }

    /**
     * Get value on attribute rhs
     * @return the attribute's value
     */
    @Override
    public AExpression getRhsImpl() {
        return this.aAssignment.getRhsImpl();
    }

    /**
     * Get value on attribute endLine
     * @return the attribute's value
     */
    @Override
    public Integer getEndLineImpl() {
        return this.aAssignment.getEndLineImpl();
    }

    /**
     * Get value on attribute kind
     * @return the attribute's value
     */
    @Override
    public String getKindImpl() {
        return this.aAssignment.getKindImpl();
    }

    /**
     * Get value on attribute annotationsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AAnnotation[] getAnnotationsArrayImpl() {
        return this.aAssignment.getAnnotationsArrayImpl();
    }

    /**
     * Get value on attribute ast
     * @return the attribute's value
     */
    @Override
    public String getAstImpl() {
        return this.aAssignment.getAstImpl();
    }

    /**
     * Get value on attribute astParent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAstParentImpl() {
        return this.aAssignment.getAstParentImpl();
    }

    /**
     * Get value on attribute child
     * @return the attribute's value
     */
    @Override
    public AJoinPoint childImpl(Integer index) {
        return this.aAssignment.childImpl(index);
    }

    /**
     * Get value on attribute childrenArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        return this.aAssignment.getChildrenArrayImpl();
    }

    /**
     * Get value on attribute code
     * @return the attribute's value
     */
    @Override
    public String getCodeImpl() {
        return this.aAssignment.getCodeImpl();
    }

    /**
     * Get value on attribute descendantsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getDescendantsArrayImpl() {
        return this.aAssignment.getDescendantsArrayImpl();
    }

    /**
     * Get value on attribute getAncestor
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAncestorImpl(String type) {
        return this.aAssignment.getAncestorImpl(type);
    }

    /**
     * Get value on attribute hasModifier
     * @return the attribute's value
     */
    @Override
    public Boolean hasModifierImpl(String modifier) {
        return this.aAssignment.hasModifierImpl(modifier);
    }

    /**
     * Get value on attribute id
     * @return the attribute's value
     */
    @Override
    public String getIdImpl() {
        return this.aAssignment.getIdImpl();
    }

    /**
     * Get value on attribute isBlock
     * @return the attribute's value
     */
    @Override
    public Boolean getIsBlockImpl() {
        return this.aAssignment.getIsBlockImpl();
    }

    /**
     * Get value on attribute isFinal
     * @return the attribute's value
     */
    @Override
    public Boolean getIsFinalImpl() {
        return this.aAssignment.getIsFinalImpl();
    }

    /**
     * Get value on attribute isInsideLoopHeader
     * @return the attribute's value
     */
    @Override
    public Boolean getIsInsideLoopHeaderImpl() {
        return this.aAssignment.getIsInsideLoopHeaderImpl();
    }

    /**
     * Get value on attribute isStatement
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStatementImpl() {
        return this.aAssignment.getIsStatementImpl();
    }

    /**
     * Get value on attribute isStatic
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStaticImpl() {
        return this.aAssignment.getIsStaticImpl();
    }

    /**
     * Get value on attribute leftArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getLeftArrayImpl() {
        return this.aAssignment.getLeftArrayImpl();
    }

    /**
     * Get value on attribute line
     * @return the attribute's value
     */
    @Override
    public Integer getLineImpl() {
        return this.aAssignment.getLineImpl();
    }

    /**
     * Get value on attribute modifiersArrayImpl
     * @return the attribute's value
     */
    @Override
    public String[] getModifiersArrayImpl() {
        return this.aAssignment.getModifiersArrayImpl();
    }

    /**
     * Get value on attribute numChildren
     * @return the attribute's value
     */
    @Override
    public Integer getNumChildrenImpl() {
        return this.aAssignment.getNumChildrenImpl();
    }

    /**
     * Get value on attribute parent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getParentImpl() {
        return this.aAssignment.getParentImpl();
    }

    /**
     * Get value on attribute rightArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getRightArrayImpl() {
        return this.aAssignment.getRightArrayImpl();
    }

    /**
     * Get value on attribute srcCode
     * @return the attribute's value
     */
    @Override
    public String getSrcCodeImpl() {
        return this.aAssignment.getSrcCodeImpl();
    }

    /**
     * 
     */
    @Override
    public AJoinPoint copyImpl() {
        return this.aAssignment.copyImpl();
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return this.aAssignment.insertImpl(position, code);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return this.aAssignment.insertImpl(position, code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        return this.aAssignment.insertAfterImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return this.aAssignment.insertAfterImpl(code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertBeforeImpl(AJoinPoint node) {
        return this.aAssignment.insertBeforeImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return this.aAssignment.insertBeforeImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return this.aAssignment.insertReplaceImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return this.aAssignment.insertReplaceImpl(code);
    }

    /**
     * 
     */
    @Override
    public void removeImpl() {
        this.aAssignment.removeImpl();
    }

    /**
     * 
     * @param annotation 
     */
    @Override
    public void removeAnnotationImpl(AAnnotation annotation) {
        this.aAssignment.removeAnnotationImpl(annotation);
    }

    /**
     * 
     * @param modifier 
     */
    @Override
    public void removeModifierImpl(String modifier) {
        this.aAssignment.removeModifierImpl(modifier);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint replaceWithImpl(AJoinPoint jp) {
        return this.aAssignment.replaceWithImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint replaceWithImpl(String code) {
        return this.aAssignment.replaceWithImpl(code);
    }

    /**
     * 
     * @param lhs 
     */
    @Override
    public void setLhsImpl(AExpression lhs) {
        this.aAssignment.setLhsImpl(lhs);
    }

    /**
     * 
     * @param value 
     */
    @Override
    public void setLineImpl(int value) {
        this.aAssignment.setLineImpl(value);
    }

    /**
     * 
     * @param value 
     */
    @Override
    public void setLineImpl(String value) {
        this.aAssignment.setLineImpl(value);
    }

    /**
     * 
     * @param modifiers 
     */
    @Override
    public void setModifiersImpl(String[] modifiers) {
        this.aAssignment.setModifiersImpl(modifiers);
    }

    /**
     * 
     * @param rhs 
     */
    @Override
    public void setRhsImpl(AExpression rhs) {
        this.aAssignment.setRhsImpl(rhs);
    }

    /**
     * 
     */
    @Override
    public Optional<? extends AAssignment> getSuper() {
        return Optional.of(this.aAssignment);
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "opAssignment";
    }

    /**
     * Defines if this joinpoint is an instanceof a given joinpoint class
     * @return True if this join point is an instanceof the given class
     */
    @Override
    public final boolean instanceOf(String joinpointClass) {
        boolean isInstance = get_class().equals(joinpointClass);
        if(isInstance) {
        	return true;
        }
        return this.aAssignment.instanceOf(joinpointClass);
    }
    /**
     * 
     */
    protected enum OpAssignmentAttributes {
        LHS("lhs"),
        OPERATOR("operator"),
        RHS("rhs"),
        ENDLINE("endLine"),
        KIND("kind"),
        ANNOTATIONS("annotations"),
        AST("ast"),
        ASTPARENT("astParent"),
        CHILD("child"),
        CHILDREN("children"),
        CODE("code"),
        DESCENDANTS("descendants"),
        GETANCESTOR("getAncestor"),
        HASMODIFIER("hasModifier"),
        ID("id"),
        ISBLOCK("isBlock"),
        ISFINAL("isFinal"),
        ISINSIDELOOPHEADER("isInsideLoopHeader"),
        ISSTATEMENT("isStatement"),
        ISSTATIC("isStatic"),
        LEFT("left"),
        LINE("line"),
        MODIFIERS("modifiers"),
        NUMCHILDREN("numChildren"),
        PARENT("parent"),
        RIGHT("right"),
        SRCCODE("srcCode");
        private String name;

        /**
         * 
         */
        private OpAssignmentAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<OpAssignmentAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(OpAssignmentAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
