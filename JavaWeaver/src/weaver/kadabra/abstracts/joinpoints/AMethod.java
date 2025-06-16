package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.exception.AttributeException;
import org.lara.interpreter.exception.ActionException;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

/**
 * Auto-Generated class for join point AMethod
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class AMethod extends AExecutable {

    protected AExecutable aExecutable;

    /**
     * 
     */
    public AMethod(AExecutable aExecutable){
        this.aExecutable = aExecutable;
    }
    /**
     * Get value on attribute declarator
     * @return the attribute's value
     */
    public abstract String getDeclaratorImpl();

    /**
     * Get value on attribute declarator
     * @return the attribute's value
     */
    public final Object getDeclarator() {
        try {
        	String result = this.getDeclaratorImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "declarator", e);
        }
    }

    /**
     * 
     * @param method
     * @return 
     */
    public abstract Boolean isOverridingImpl(AMethod method);

    /**
     * 
     * @param method
     * @return 
     */
    public final Object isOverriding(AMethod method) {
        try {
        	Boolean result = this.isOverridingImpl(method);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isOverriding", e);
        }
    }

    /**
     * Get value on attribute privacy
     * @return the attribute's value
     */
    public abstract String getPrivacyImpl();

    /**
     * Get value on attribute privacy
     * @return the attribute's value
     */
    public final Object getPrivacy() {
        try {
        	String result = this.getPrivacyImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "privacy", e);
        }
    }

    /**
     * Get value on attribute toQualifiedReference
     * @return the attribute's value
     */
    public abstract String getToQualifiedReferenceImpl();

    /**
     * Get value on attribute toQualifiedReference
     * @return the attribute's value
     */
    public final Object getToQualifiedReference() {
        try {
        	String result = this.getToQualifiedReferenceImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "toQualifiedReference", e);
        }
    }

    /**
     * Get value on attribute toReference
     * @return the attribute's value
     */
    public abstract String getToReferenceImpl();

    /**
     * Get value on attribute toReference
     * @return the attribute's value
     */
    public final Object getToReference() {
        try {
        	String result = this.getToReferenceImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "toReference", e);
        }
    }

    /**
     * 
     * @param comment 
     */
    public void addCommentImpl(String comment) {
        throw new UnsupportedOperationException(get_class()+": Action addComment not implemented ");
    }

    /**
     * 
     * @param comment 
     */
    public final void addComment(String comment) {
        try {
        	this.addCommentImpl(comment);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "addComment", e);
        }
    }

    /**
     * 
     * @param type 
     * @param name 
     */
    public void addParameterImpl(String type, String name) {
        throw new UnsupportedOperationException(get_class()+": Action addParameter not implemented ");
    }

    /**
     * 
     * @param type 
     * @param name 
     */
    public final void addParameter(String type, String name) {
        try {
        	this.addParameterImpl(type, name);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "addParameter", e);
        }
    }

    /**
     * 
     * @param newName 
     */
    public AMethod cloneImpl(String newName) {
        throw new UnsupportedOperationException(get_class()+": Action clone not implemented ");
    }

    /**
     * 
     * @param newName 
     */
    public final Object clone(String newName) {
        try {
        	AMethod result = this.cloneImpl(newName);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "clone", e);
        }
    }

    /**
     * 
     * @param adaptMethod 
     * @param name 
     */
    public AClass createAdapterImpl(AMethod adaptMethod, String name) {
        throw new UnsupportedOperationException(get_class()+": Action createAdapter not implemented ");
    }

    /**
     * 
     * @param adaptMethod 
     * @param name 
     */
    public final Object createAdapter(AMethod adaptMethod, String name) {
        try {
        	AClass result = this.createAdapterImpl(adaptMethod, name);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "createAdapter", e);
        }
    }

    /**
     * 
     * @param privacy 
     */
    public void setPrivacyImpl(String privacy) {
        throw new UnsupportedOperationException(get_class()+": Action setPrivacy not implemented ");
    }

    /**
     * 
     * @param privacy 
     */
    public final void setPrivacy(String privacy) {
        try {
        	this.setPrivacyImpl(privacy);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setPrivacy", e);
        }
    }

    /**
     * Get value on attribute body
     * @return the attribute's value
     */
    @Override
    public ABody getBodyImpl() {
        return this.aExecutable.getBodyImpl();
    }

    /**
     * Get value on attribute name
     * @return the attribute's value
     */
    @Override
    public String getNameImpl() {
        return this.aExecutable.getNameImpl();
    }

    /**
     * Get value on attribute paramsArrayImpl
     * @return the attribute's value
     */
    @Override
    public ADeclaration[] getParamsArrayImpl() {
        return this.aExecutable.getParamsArrayImpl();
    }

    /**
     * Get value on attribute returnRef
     * @return the attribute's value
     */
    @Override
    public ATypeReference getReturnRefImpl() {
        return this.aExecutable.getReturnRefImpl();
    }

    /**
     * Get value on attribute returnType
     * @return the attribute's value
     */
    @Override
    public String getReturnTypeImpl() {
        return this.aExecutable.getReturnTypeImpl();
    }

    /**
     * Get value on attribute annotationsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AAnnotation[] getAnnotationsArrayImpl() {
        return this.aExecutable.getAnnotationsArrayImpl();
    }

    /**
     * Get value on attribute ast
     * @return the attribute's value
     */
    @Override
    public String getAstImpl() {
        return this.aExecutable.getAstImpl();
    }

    /**
     * Get value on attribute astParent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAstParentImpl() {
        return this.aExecutable.getAstParentImpl();
    }

    /**
     * Get value on attribute child
     * @return the attribute's value
     */
    @Override
    public AJoinPoint childImpl(Integer index) {
        return this.aExecutable.childImpl(index);
    }

    /**
     * Get value on attribute childrenArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        return this.aExecutable.getChildrenArrayImpl();
    }

    /**
     * Get value on attribute code
     * @return the attribute's value
     */
    @Override
    public String getCodeImpl() {
        return this.aExecutable.getCodeImpl();
    }

    /**
     * Get value on attribute descendantsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getDescendantsArrayImpl() {
        return this.aExecutable.getDescendantsArrayImpl();
    }

    /**
     * Get value on attribute getAncestor
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAncestorImpl(String type) {
        return this.aExecutable.getAncestorImpl(type);
    }

    /**
     * Get value on attribute hasModifier
     * @return the attribute's value
     */
    @Override
    public Boolean hasModifierImpl(String modifier) {
        return this.aExecutable.hasModifierImpl(modifier);
    }

    /**
     * Get value on attribute id
     * @return the attribute's value
     */
    @Override
    public String getIdImpl() {
        return this.aExecutable.getIdImpl();
    }

    /**
     * Get value on attribute isBlock
     * @return the attribute's value
     */
    @Override
    public Boolean getIsBlockImpl() {
        return this.aExecutable.getIsBlockImpl();
    }

    /**
     * Get value on attribute isFinal
     * @return the attribute's value
     */
    @Override
    public Boolean getIsFinalImpl() {
        return this.aExecutable.getIsFinalImpl();
    }

    /**
     * Get value on attribute isInsideLoopHeader
     * @return the attribute's value
     */
    @Override
    public Boolean getIsInsideLoopHeaderImpl() {
        return this.aExecutable.getIsInsideLoopHeaderImpl();
    }

    /**
     * Get value on attribute isStatement
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStatementImpl() {
        return this.aExecutable.getIsStatementImpl();
    }

    /**
     * Get value on attribute isStatic
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStaticImpl() {
        return this.aExecutable.getIsStaticImpl();
    }

    /**
     * Get value on attribute leftArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getLeftArrayImpl() {
        return this.aExecutable.getLeftArrayImpl();
    }

    /**
     * Get value on attribute line
     * @return the attribute's value
     */
    @Override
    public Integer getLineImpl() {
        return this.aExecutable.getLineImpl();
    }

    /**
     * Get value on attribute modifiersArrayImpl
     * @return the attribute's value
     */
    @Override
    public String[] getModifiersArrayImpl() {
        return this.aExecutable.getModifiersArrayImpl();
    }

    /**
     * Get value on attribute numChildren
     * @return the attribute's value
     */
    @Override
    public Integer getNumChildrenImpl() {
        return this.aExecutable.getNumChildrenImpl();
    }

    /**
     * Get value on attribute parent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getParentImpl() {
        return this.aExecutable.getParentImpl();
    }

    /**
     * Get value on attribute rightArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getRightArrayImpl() {
        return this.aExecutable.getRightArrayImpl();
    }

    /**
     * Get value on attribute srcCode
     * @return the attribute's value
     */
    @Override
    public String getSrcCodeImpl() {
        return this.aExecutable.getSrcCodeImpl();
    }

    /**
     * 
     */
    @Override
    public AJoinPoint copyImpl() {
        return this.aExecutable.copyImpl();
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return this.aExecutable.insertImpl(position, code);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return this.aExecutable.insertImpl(position, code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        return this.aExecutable.insertAfterImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return this.aExecutable.insertAfterImpl(code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertBeforeImpl(AJoinPoint node) {
        return this.aExecutable.insertBeforeImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return this.aExecutable.insertBeforeImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return this.aExecutable.insertReplaceImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return this.aExecutable.insertReplaceImpl(code);
    }

    /**
     * 
     */
    @Override
    public void removeImpl() {
        this.aExecutable.removeImpl();
    }

    /**
     * 
     * @param annotation 
     */
    @Override
    public void removeAnnotationImpl(AAnnotation annotation) {
        this.aExecutable.removeAnnotationImpl(annotation);
    }

    /**
     * 
     * @param modifier 
     */
    @Override
    public void removeModifierImpl(String modifier) {
        this.aExecutable.removeModifierImpl(modifier);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint replaceWithImpl(AJoinPoint jp) {
        return this.aExecutable.replaceWithImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint replaceWithImpl(String code) {
        return this.aExecutable.replaceWithImpl(code);
    }

    /**
     * 
     * @param value 
     */
    @Override
    public void setLineImpl(int value) {
        this.aExecutable.setLineImpl(value);
    }

    /**
     * 
     * @param value 
     */
    @Override
    public void setLineImpl(String value) {
        this.aExecutable.setLineImpl(value);
    }

    /**
     * 
     * @param modifiers 
     */
    @Override
    public void setModifiersImpl(String[] modifiers) {
        this.aExecutable.setModifiersImpl(modifiers);
    }

    /**
     * Sets the name of this executable, returns the previous name
     * @param name 
     */
    @Override
    public String setNameImpl(String name) {
        return this.aExecutable.setNameImpl(name);
    }

    /**
     * 
     */
    @Override
    public Optional<? extends AExecutable> getSuper() {
        return Optional.of(this.aExecutable);
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "method";
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
        return this.aExecutable.instanceOf(joinpointClass);
    }
    /**
     * 
     */
    protected enum MethodAttributes {
        DECLARATOR("declarator"),
        ISOVERRIDING("isOverriding"),
        PRIVACY("privacy"),
        TOQUALIFIEDREFERENCE("toQualifiedReference"),
        TOREFERENCE("toReference"),
        BODY("body"),
        NAME("name"),
        PARAMS("params"),
        RETURNREF("returnRef"),
        RETURNTYPE("returnType"),
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
        private MethodAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<MethodAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(MethodAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
