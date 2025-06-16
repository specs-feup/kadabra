package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.exception.AttributeException;
import org.lara.interpreter.exception.ActionException;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

/**
 * Auto-Generated class for join point AType
 * This class is overwritten by the Weaver Generator.
 * 
 * base join point that class, interface and enum extend
 * @author Lara Weaver Generator
 */
public abstract class AType extends AJavaWeaverJoinPoint {

    /**
     * Get value on attribute interfaces
     * @return the attribute's value
     */
    public abstract String[] getInterfacesArrayImpl();

    /**
     * list of names of interfaces that this class implements
     */
    public Object getInterfacesImpl() {
        String[] stringArrayImpl0 = getInterfacesArrayImpl();
        Object nativeArray0 = stringArrayImpl0;
        return nativeArray0;
    }

    /**
     * list of names of interfaces that this class implements
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
     * Get value on attribute interfacesTypes
     * @return the attribute's value
     */
    public abstract AInterfaceType[] getInterfacesTypesArrayImpl();

    /**
     * returns the interface join points that this class implements
     */
    public Object getInterfacesTypesImpl() {
        AInterfaceType[] aInterfaceTypeArrayImpl0 = getInterfacesTypesArrayImpl();
        Object nativeArray0 = aInterfaceTypeArrayImpl0;
        return nativeArray0;
    }

    /**
     * returns the interface join points that this class implements
     */
    public final Object getInterfacesTypes() {
        try {
        	Object result = this.getInterfacesTypesImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "interfacesTypes", e);
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
     * the simple name of the class
     */
    public abstract String getNameImpl();

    /**
     * the simple name of the class
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
     * package name of this class
     */
    public abstract String getPackageNameImpl();

    /**
     * package name of this class
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
     * the qualified name of this class, includes packages
     */
    public abstract String getQualifiedNameImpl();

    /**
     * the qualified name of this class, includes packages
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
     * name of the superclass this class extends
     */
    public abstract String getSuperClassImpl();

    /**
     * name of the superclass this class extends
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
     * the superclass this class extends, or undefined if the class extends java.lang.Object
     */
    public abstract ATypeReference getSuperClassJpImpl();

    /**
     * the superclass this class extends, or undefined if the class extends java.lang.Object
     */
    public final Object getSuperClassJp() {
        try {
        	ATypeReference result = this.getSuperClassJpImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "superClassJp", e);
        }
    }

    /**
     * insert a given class inside the target
     * @param newClass 
     */
    public void addClassImpl(AClass newClass) {
        throw new UnsupportedOperationException(get_class()+": Action addClass not implemented ");
    }

    /**
     * insert a given class inside the target
     * @param newClass 
     */
    public final void addClass(AClass newClass) {
        try {
        	this.addClassImpl(newClass);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "addClass", e);
        }
    }

    /**
     * 
     * @param interfaceType 
     */
    public void addImplementImpl(AInterfaceType interfaceType) {
        throw new UnsupportedOperationException(get_class()+": Action addImplement not implemented ");
    }

    /**
     * 
     * @param interfaceType 
     */
    public final void addImplement(AInterfaceType interfaceType) {
        try {
        	this.addImplementImpl(interfaceType);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "addImplement", e);
        }
    }

    /**
     * 
     * @param newInterface 
     */
    public void addInterfaceImpl(AInterfaceType newInterface) {
        throw new UnsupportedOperationException(get_class()+": Action addInterface not implemented ");
    }

    /**
     * 
     * @param newInterface 
     */
    public final void addInterface(AInterfaceType newInterface) {
        try {
        	this.addInterfaceImpl(newInterface);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "addInterface", e);
        }
    }

    /**
     * 
     * @param code 
     */
    public void insertCodeImpl(String code) {
        throw new UnsupportedOperationException(get_class()+": Action insertCode not implemented ");
    }

    /**
     * 
     * @param code 
     */
    public final void insertCode(String code) {
        try {
        	this.insertCodeImpl(code);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "insertCode", e);
        }
    }

    /**
     * 
     * @param code 
     */
    public void insertMethodImpl(String code) {
        throw new UnsupportedOperationException(get_class()+": Action insertMethod not implemented ");
    }

    /**
     * 
     * @param code 
     */
    public final void insertMethod(String code) {
        try {
        	this.insertMethodImpl(code);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "insertMethod", e);
        }
    }

    /**
     * 
     * @param modifiers 
     * @param type 
     * @param name 
     * @param defaultValue 
     */
    public AField newFieldImpl(String[] modifiers, String type, String name, String defaultValue) {
        throw new UnsupportedOperationException(get_class()+": Action newField not implemented ");
    }

    /**
     * 
     * @param modifiers 
     * @param type 
     * @param name 
     * @param defaultValue 
     */
    public final Object newField(Object[] modifiers, String type, String name, String defaultValue) {
        try {
        	AField result = this.newFieldImpl(pt.up.fe.specs.util.SpecsCollections.cast(modifiers, String.class), type, name, defaultValue);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newField", e);
        }
    }

    /**
     * 
     * @param modifiers 
     * @param type 
     * @param name 
     */
    public AField newFieldImpl(String[] modifiers, String type, String name) {
        throw new UnsupportedOperationException(get_class()+": Action newField not implemented ");
    }

    /**
     * 
     * @param modifiers 
     * @param type 
     * @param name 
     */
    public final Object newField(Object[] modifiers, String type, String name) {
        try {
        	AField result = this.newFieldImpl(pt.up.fe.specs.util.SpecsCollections.cast(modifiers, String.class), type, name);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newField", e);
        }
    }

    /**
     * add a new method inside the class
     * @param modifiers 
     * @param returnType 
     * @param name 
     * @param paramLeft 
     * @param paramRight 
     * @param code 
     */
    public AMethod newMethodImpl(String[] modifiers, String returnType, String name, String[] paramLeft, String[] paramRight, String code) {
        throw new UnsupportedOperationException(get_class()+": Action newMethod not implemented ");
    }

    /**
     * add a new method inside the class
     * @param modifiers 
     * @param returnType 
     * @param name 
     * @param paramLeft 
     * @param paramRight 
     * @param code 
     */
    public final Object newMethod(Object[] modifiers, String returnType, String name, Object[] paramLeft, Object[] paramRight, String code) {
        try {
        	AMethod result = this.newMethodImpl(pt.up.fe.specs.util.SpecsCollections.cast(modifiers, String.class), returnType, name, pt.up.fe.specs.util.SpecsCollections.cast(paramLeft, String.class), pt.up.fe.specs.util.SpecsCollections.cast(paramRight, String.class), code);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newMethod", e);
        }
    }

    /**
     * overload which accepts 4 parameters (code is empty string)
     * @param modifiers 
     * @param returnType 
     * @param name 
     * @param paramLeft 
     * @param paramRight 
     */
    public AMethod newMethodImpl(String[] modifiers, String returnType, String name, String[] paramLeft, String[] paramRight) {
        throw new UnsupportedOperationException(get_class()+": Action newMethod not implemented ");
    }

    /**
     * overload which accepts 4 parameters (code is empty string)
     * @param modifiers 
     * @param returnType 
     * @param name 
     * @param paramLeft 
     * @param paramRight 
     */
    public final Object newMethod(Object[] modifiers, String returnType, String name, Object[] paramLeft, Object[] paramRight) {
        try {
        	AMethod result = this.newMethodImpl(pt.up.fe.specs.util.SpecsCollections.cast(modifiers, String.class), returnType, name, pt.up.fe.specs.util.SpecsCollections.cast(paramLeft, String.class), pt.up.fe.specs.util.SpecsCollections.cast(paramRight, String.class));
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newMethod", e);
        }
    }

    /**
     * 
     * @param interfaceName 
     */
    public AInterfaceType removeInterfaceImpl(String interfaceName) {
        throw new UnsupportedOperationException(get_class()+": Action removeInterface not implemented ");
    }

    /**
     * 
     * @param interfaceName 
     */
    public final Object removeInterface(String interfaceName) {
        try {
        	AInterfaceType result = this.removeInterfaceImpl(interfaceName);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "removeInterface", e);
        }
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public String get_class() {
        return "type";
    }
    /**
     * 
     */
    protected enum TypeAttributes {
        INTERFACES("interfaces"),
        INTERFACESTYPES("interfacesTypes"),
        ISSUBTYPEOF("isSubtypeOf"),
        JAVADOC("javadoc"),
        NAME("name"),
        PACKAGENAME("packageName"),
        QUALIFIEDNAME("qualifiedName"),
        SUPERCLASS("superClass"),
        SUPERCLASSJP("superClassJp"),
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
        private TypeAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<TypeAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(TypeAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
