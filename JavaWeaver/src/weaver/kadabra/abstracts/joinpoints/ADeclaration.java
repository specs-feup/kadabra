package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.exception.AttributeException;
import org.lara.interpreter.exception.ActionException;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

/**
 * Auto-Generated class for join point ADeclaration
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class ADeclaration extends AJavaWeaverJoinPoint {

    /**
     * Get value on attribute completeType
     * @return the attribute's value
     */
    public abstract String getCompleteTypeImpl();

    /**
     * Get value on attribute completeType
     * @return the attribute's value
     */
    public final Object getCompleteType() {
        try {
        	String result = this.getCompleteTypeImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "completeType", e);
        }
    }

    /**
     * Get value on attribute init
     * @return the attribute's value
     */
    public abstract AExpression getInitImpl();

    /**
     * Get value on attribute init
     * @return the attribute's value
     */
    public final Object getInit() {
        try {
        	AExpression result = this.getInitImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "init", e);
        }
    }

    /**
     * Get value on attribute isArray
     * @return the attribute's value
     */
    public abstract Boolean getIsArrayImpl();

    /**
     * Get value on attribute isArray
     * @return the attribute's value
     */
    public final Object getIsArray() {
        try {
        	Boolean result = this.getIsArrayImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isArray", e);
        }
    }

    /**
     * Get value on attribute isPrimitive
     * @return the attribute's value
     */
    public abstract Boolean getIsPrimitiveImpl();

    /**
     * Get value on attribute isPrimitive
     * @return the attribute's value
     */
    public final Object getIsPrimitive() {
        try {
        	Boolean result = this.getIsPrimitiveImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isPrimitive", e);
        }
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
     * Get value on attribute type
     * @return the attribute's value
     */
    public abstract String getTypeImpl();

    /**
     * Get value on attribute type
     * @return the attribute's value
     */
    public final Object getType() {
        try {
        	String result = this.getTypeImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "type", e);
        }
    }

    /**
     * Get value on attribute typeReference
     * @return the attribute's value
     */
    public abstract ATypeReference getTypeReferenceImpl();

    /**
     * Get value on attribute typeReference
     * @return the attribute's value
     */
    public final Object getTypeReference() {
        try {
        	ATypeReference result = this.getTypeReferenceImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "typeReference", e);
        }
    }

    /**
     * 
     * @param value 
     */
    public void setInitImpl(AExpression value) {
        throw new UnsupportedOperationException(get_class()+": Action setInit not implemented ");
    }

    /**
     * 
     * @param value 
     */
    public final void setInit(AExpression value) {
        try {
        	this.setInitImpl(value);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setInit", e);
        }
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public String get_class() {
        return "declaration";
    }
    /**
     * 
     */
    protected enum DeclarationAttributes {
        COMPLETETYPE("completeType"),
        INIT("init"),
        ISARRAY("isArray"),
        ISPRIMITIVE("isPrimitive"),
        NAME("name"),
        TYPE("type"),
        TYPEREFERENCE("typeReference"),
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
        private DeclarationAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<DeclarationAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(DeclarationAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
