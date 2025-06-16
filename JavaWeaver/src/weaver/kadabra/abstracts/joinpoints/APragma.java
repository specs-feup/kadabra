package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.exception.AttributeException;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

/**
 * Auto-Generated class for join point APragma
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class APragma extends AComment {

    protected AComment aComment;

    /**
     * 
     */
    public APragma(AComment aComment){
        super(aComment);
        this.aComment = aComment;
    }
    /**
     * Get value on attribute name
     * @return the attribute's value
     */
    public abstract String getNameImpl();

    /**
     * Get value on attribute name
     * @return the attribute's value
     */
    public final Object getName() {
        try {
        	String result = this.getNameImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "name", e);
        }
    }

    /**
     * Get value on attribute content
     * @return the attribute's value
     */
    @Override
    public String getContentImpl() {
        return this.aComment.getContentImpl();
    }

    /**
     * Get value on attribute type
     * @return the attribute's value
     */
    @Override
    public String getTypeImpl() {
        return this.aComment.getTypeImpl();
    }

    /**
     * Get value on attribute endLine
     * @return the attribute's value
     */
    @Override
    public Integer getEndLineImpl() {
        return this.aComment.getEndLineImpl();
    }

    /**
     * Get value on attribute kind
     * @return the attribute's value
     */
    @Override
    public String getKindImpl() {
        return this.aComment.getKindImpl();
    }

    /**
     * Get value on attribute annotationsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AAnnotation[] getAnnotationsArrayImpl() {
        return this.aComment.getAnnotationsArrayImpl();
    }

    /**
     * Get value on attribute ast
     * @return the attribute's value
     */
    @Override
    public String getAstImpl() {
        return this.aComment.getAstImpl();
    }

    /**
     * Get value on attribute astParent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAstParentImpl() {
        return this.aComment.getAstParentImpl();
    }

    /**
     * Get value on attribute child
     * @return the attribute's value
     */
    @Override
    public AJoinPoint childImpl(Integer index) {
        return this.aComment.childImpl(index);
    }

    /**
     * Get value on attribute childrenArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        return this.aComment.getChildrenArrayImpl();
    }

    /**
     * Get value on attribute code
     * @return the attribute's value
     */
    @Override
    public String getCodeImpl() {
        return this.aComment.getCodeImpl();
    }

    /**
     * Get value on attribute descendantsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getDescendantsArrayImpl() {
        return this.aComment.getDescendantsArrayImpl();
    }

    /**
     * Get value on attribute getAncestor
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAncestorImpl(String type) {
        return this.aComment.getAncestorImpl(type);
    }

    /**
     * Get value on attribute hasModifier
     * @return the attribute's value
     */
    @Override
    public Boolean hasModifierImpl(String modifier) {
        return this.aComment.hasModifierImpl(modifier);
    }

    /**
     * Get value on attribute id
     * @return the attribute's value
     */
    @Override
    public String getIdImpl() {
        return this.aComment.getIdImpl();
    }

    /**
     * Get value on attribute isBlock
     * @return the attribute's value
     */
    @Override
    public Boolean getIsBlockImpl() {
        return this.aComment.getIsBlockImpl();
    }

    /**
     * Get value on attribute isFinal
     * @return the attribute's value
     */
    @Override
    public Boolean getIsFinalImpl() {
        return this.aComment.getIsFinalImpl();
    }

    /**
     * Get value on attribute isInsideLoopHeader
     * @return the attribute's value
     */
    @Override
    public Boolean getIsInsideLoopHeaderImpl() {
        return this.aComment.getIsInsideLoopHeaderImpl();
    }

    /**
     * Get value on attribute isStatement
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStatementImpl() {
        return this.aComment.getIsStatementImpl();
    }

    /**
     * Get value on attribute isStatic
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStaticImpl() {
        return this.aComment.getIsStaticImpl();
    }

    /**
     * Get value on attribute leftArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getLeftArrayImpl() {
        return this.aComment.getLeftArrayImpl();
    }

    /**
     * Get value on attribute line
     * @return the attribute's value
     */
    @Override
    public Integer getLineImpl() {
        return this.aComment.getLineImpl();
    }

    /**
     * Get value on attribute modifiersArrayImpl
     * @return the attribute's value
     */
    @Override
    public String[] getModifiersArrayImpl() {
        return this.aComment.getModifiersArrayImpl();
    }

    /**
     * Get value on attribute numChildren
     * @return the attribute's value
     */
    @Override
    public Integer getNumChildrenImpl() {
        return this.aComment.getNumChildrenImpl();
    }

    /**
     * Get value on attribute parent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getParentImpl() {
        return this.aComment.getParentImpl();
    }

    /**
     * Get value on attribute rightArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getRightArrayImpl() {
        return this.aComment.getRightArrayImpl();
    }

    /**
     * Get value on attribute srcCode
     * @return the attribute's value
     */
    @Override
    public String getSrcCodeImpl() {
        return this.aComment.getSrcCodeImpl();
    }

    /**
     * 
     */
    @Override
    public AJoinPoint copyImpl() {
        return this.aComment.copyImpl();
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return this.aComment.insertImpl(position, code);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return this.aComment.insertImpl(position, code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        return this.aComment.insertAfterImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return this.aComment.insertAfterImpl(code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertBeforeImpl(AJoinPoint node) {
        return this.aComment.insertBeforeImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return this.aComment.insertBeforeImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return this.aComment.insertReplaceImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return this.aComment.insertReplaceImpl(code);
    }

    /**
     * 
     */
    @Override
    public void removeImpl() {
        this.aComment.removeImpl();
    }

    /**
     * 
     * @param annotation 
     */
    @Override
    public void removeAnnotationImpl(AAnnotation annotation) {
        this.aComment.removeAnnotationImpl(annotation);
    }

    /**
     * 
     * @param modifier 
     */
    @Override
    public void removeModifierImpl(String modifier) {
        this.aComment.removeModifierImpl(modifier);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint replaceWithImpl(AJoinPoint jp) {
        return this.aComment.replaceWithImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint replaceWithImpl(String code) {
        return this.aComment.replaceWithImpl(code);
    }

    /**
     * 
     * @param value 
     */
    @Override
    public void setLineImpl(int value) {
        this.aComment.setLineImpl(value);
    }

    /**
     * 
     * @param value 
     */
    @Override
    public void setLineImpl(String value) {
        this.aComment.setLineImpl(value);
    }

    /**
     * 
     * @param modifiers 
     */
    @Override
    public void setModifiersImpl(String[] modifiers) {
        this.aComment.setModifiersImpl(modifiers);
    }

    /**
     * 
     */
    @Override
    public Optional<? extends AComment> getSuper() {
        return Optional.of(this.aComment);
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "pragma";
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
        return this.aComment.instanceOf(joinpointClass);
    }
    /**
     * 
     */
    protected enum PragmaAttributes {
        NAME("name"),
        CONTENT("content"),
        TYPE("type"),
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
        private PragmaAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<PragmaAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(PragmaAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
