package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.exception.AttributeException;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

/**
 * Auto-Generated class for join point ANamedType
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class ANamedType extends AJavaWeaverJoinPoint {

    /**
     * Get value on attribute interfaces
     * @return the attribute's value
     */
    public abstract String[] getInterfacesArrayImpl();

    /**
     * list of names of interfaces that this type implements
     */
    public Object getInterfacesImpl() {
        String[] stringArrayImpl0 = getInterfacesArrayImpl();
        Object nativeArray0 = stringArrayImpl0;
        return nativeArray0;
    }

    /**
     * list of names of interfaces that this type implements
     */
    public final Object getInterfaces() {
        try {
        	Object result = this.getInterfacesImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "interfaces", e);
        }
    }

    /**
     * 
     * @param type
     * @return 
     */
    public abstract Boolean isSubtypeOfImpl(String type);

    /**
     * 
     * @param type
     * @return 
     */
    public final Object isSubtypeOf(String type) {
        try {
        	Boolean result = this.isSubtypeOfImpl(type);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isSubtypeOf", e);
        }
    }

    /**
     * Get value on attribute javadoc
     * @return the attribute's value
     */
    public abstract String getJavadocImpl();

    /**
     * Get value on attribute javadoc
     * @return the attribute's value
     */
    public final Object getJavadoc() {
        try {
        	String result = this.getJavadocImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "javadoc", e);
        }
    }

    /**
     * the simple name of the type
     */
    public abstract String getNameImpl();

    /**
     * the simple name of the type
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
     * package name of this type
     */
    public abstract String getPackageNameImpl();

    /**
     * package name of this type
     */
    public final Object getPackageName() {
        try {
        	String result = this.getPackageNameImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "packageName", e);
        }
    }

    /**
     * the qualified name of this type, includes packages
     */
    public abstract String getQualifiedNameImpl();

    /**
     * the qualified name of this type, includes packages
     */
    public final Object getQualifiedName() {
        try {
        	String result = this.getQualifiedNameImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "qualifiedName", e);
        }
    }

    /**
     * name of the superclass this type extends
     */
    public abstract String getSuperClassImpl();

    /**
     * name of the superclass this type extends
     */
    public final Object getSuperClass() {
        try {
        	String result = this.getSuperClassImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "superClass", e);
        }
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public String get_class() {
        return "NamedType";
    }
    /**
     * 
     */
    protected enum NamedTypeAttributes {
        INTERFACES("interfaces"),
        ISSUBTYPEOF("isSubtypeOf"),
        JAVADOC("javadoc"),
        NAME("name"),
        PACKAGENAME("packageName"),
        QUALIFIEDNAME("qualifiedName"),
        SUPERCLASS("superClass"),
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
        private NamedTypeAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<NamedTypeAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(NamedTypeAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
