package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.exception.AttributeException;
import org.lara.interpreter.exception.ActionException;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

/**
 * Auto-Generated class for join point AExpression
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class AExpression extends AJavaWeaverJoinPoint {

    /**
     * Get value on attribute kind
     * @return the attribute's value
     */
    public abstract String getKindImpl();

    /**
     * Get value on attribute kind
     * @return the attribute's value
     */
    public final Object getKind() {
        try {
        	String result = this.getKindImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "kind", e);
        }
    }

    /**
     * Get value on attribute qualifiedType
     * @return the attribute's value
     */
    public abstract String getQualifiedTypeImpl();

    /**
     * Get value on attribute qualifiedType
     * @return the attribute's value
     */
    public final Object getQualifiedType() {
        try {
        	String result = this.getQualifiedTypeImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "qualifiedType", e);
        }
    }

    /**
     * Get value on attribute test
     * @return the attribute's value
     */
    public abstract Integer getTestImpl();

    /**
     * Get value on attribute test
     * @return the attribute's value
     */
    public final Object getTest() {
        try {
        	Integer result = this.getTestImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "test", e);
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
     * @param varName 
     * @param location 
     * @param position 
     */
    public void extractImpl(String varName, AStatement location, String position) {
        throw new UnsupportedOperationException(get_class()+": Action extract not implemented ");
    }

    /**
     * 
     * @param varName 
     * @param location 
     * @param position 
     */
    public final void extract(String varName, AStatement location, String position) {
        try {
        	this.extractImpl(varName, location, position);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "extract", e);
        }
    }

    /**
     * 
     * @param test 
     */
    public void setTestImpl(AExpression test) {
        throw new UnsupportedOperationException(get_class()+": Action setTest not implemented ");
    }

    /**
     * 
     * @param test 
     */
    public final void setTest(AExpression test) {
        try {
        	this.setTestImpl(test);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setTest", e);
        }
    }

    /**
     * 
     * @param test 
     */
    public void setTestImpl(int test) {
        throw new UnsupportedOperationException(get_class()+": Action setTest not implemented ");
    }

    /**
     * 
     * @param test 
     */
    public final void setTest(int test) {
        try {
        	this.setTestImpl(test);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setTest", e);
        }
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public String get_class() {
        return "expression";
    }
    /**
     * 
     */
    protected enum ExpressionAttributes {
        KIND("kind"),
        QUALIFIEDTYPE("qualifiedType"),
        TEST("test"),
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
        private ExpressionAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<ExpressionAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(ExpressionAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
