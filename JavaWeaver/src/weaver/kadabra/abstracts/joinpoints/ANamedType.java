package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.weaver.interf.events.Stage;
import java.util.Optional;
import org.lara.interpreter.exception.AttributeException;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import java.util.List;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.stream.Collectors;
import java.util.Arrays;

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
        Object nativeArray0 = getWeaverEngine().getScriptEngine().toNativeArray(stringArrayImpl0);
        return nativeArray0;
    }

    /**
     * list of names of interfaces that this type implements
     */
    public final Object getInterfaces() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "interfaces", Optional.empty());
        	}
        	Object result = this.getInterfacesImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "interfaces", Optional.ofNullable(result));
        	}
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
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "isSubtypeOf", Optional.empty(), type);
        	}
        	Boolean result = this.isSubtypeOfImpl(type);
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "isSubtypeOf", Optional.ofNullable(result), type);
        	}
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
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "javadoc", Optional.empty());
        	}
        	String result = this.getJavadocImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "javadoc", Optional.ofNullable(result));
        	}
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
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "name", Optional.empty());
        	}
        	String result = this.getNameImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "name", Optional.ofNullable(result));
        	}
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
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "packageName", Optional.empty());
        	}
        	String result = this.getPackageNameImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "packageName", Optional.ofNullable(result));
        	}
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
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "qualifiedName", Optional.empty());
        	}
        	String result = this.getQualifiedNameImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "qualifiedName", Optional.ofNullable(result));
        	}
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
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "superClass", Optional.empty());
        	}
        	String result = this.getSuperClassImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "superClass", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "superClass", e);
        }
    }

    /**
     * 
     */
    @Override
    public List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
        	default:
        		joinPointList = super.select(selectName);
        		break;
        }
        return joinPointList;
    }

    /**
     * 
     */
    @Override
    public void defImpl(String attribute, Object value) {
        switch(attribute){
        case "line": {
        	if(value instanceof Integer){
        		this.defLineImpl((Integer)value);
        		return;
        	}
        	if(value instanceof String){
        		this.defLineImpl((String)value);
        		return;
        	}
        	this.unsupportedTypeForDef(attribute, value);
        }
        default: throw new UnsupportedOperationException("Join point "+get_class()+": attribute '"+attribute+"' cannot be defined");
        }
    }

    /**
     * 
     */
    @Override
    protected void fillWithAttributes(List<String> attributes) {
        super.fillWithAttributes(attributes);
        attributes.add("interfaces");
        attributes.add("isSubtypeOf");
        attributes.add("javadoc");
        attributes.add("name");
        attributes.add("packageName");
        attributes.add("qualifiedName");
        attributes.add("superClass");
    }

    /**
     * 
     */
    @Override
    protected void fillWithSelects(List<String> selects) {
        super.fillWithSelects(selects);
    }

    /**
     * 
     */
    @Override
    protected void fillWithActions(List<String> actions) {
        super.fillWithActions(actions);
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
