package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.weaver.interf.events.Stage;
import java.util.Optional;
import org.lara.interpreter.exception.AttributeException;
import java.util.List;
import org.lara.interpreter.weaver.interf.SelectOp;
import org.lara.interpreter.exception.ActionException;
import weaver.kadabra.entities.Pair;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Auto-Generated class for join point AType
 * This class is overwritten by the Weaver Generator.
 * 
 * base join point that class, interface and enum extend
 * @author Lara Weaver Generator
 */
public abstract class AType extends AJavaWeaverJoinPoint {

    /**
     * the simple name of the class
     */
    public abstract String getNameImpl();

    /**
     * the simple name of the class
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
     * the qualified name of this class, includes packages
     */
    public abstract String getQualifiedNameImpl();

    /**
     * the qualified name of this class, includes packages
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
     * name of the superclass this class extends
     */
    public abstract String getSuperClassImpl();

    /**
     * name of the superclass this class extends
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
     * the superclass this class extends, or undefined if the class extends java.lang.Object
     */
    public abstract ATypeReference getSuperClassJpImpl();

    /**
     * the superclass this class extends, or undefined if the class extends java.lang.Object
     */
    public final Object getSuperClassJp() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "superClassJp", Optional.empty());
        	}
        	ATypeReference result = this.getSuperClassJpImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "superClassJp", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "superClassJp", e);
        }
    }

    /**
     * package name of this class
     */
    public abstract String getPackageImpl();

    /**
     * package name of this class
     */
    public final Object getPackage() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "package", Optional.empty());
        	}
        	String result = this.getPackageImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "package", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "package", e);
        }
    }

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
        Object nativeArray0 = getWeaverEngine().getScriptEngine().toNativeArray(stringArrayImpl0);
        return nativeArray0;
    }

    /**
     * list of names of interfaces that this class implements
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
     * fields inside a class
     * @return 
     */
    public List<? extends AField> selectField() {
        return select(weaver.kadabra.abstracts.joinpoints.AField.class, SelectOp.DESCENDANTS);
    }

    /**
     * methods, constructors or static/instance blocks
     * @return 
     */
    public List<? extends AExecutable> selectExecutable() {
        return select(weaver.kadabra.abstracts.joinpoints.AExecutable.class, SelectOp.DESCENDANTS);
    }

    /**
     * methods inside a class
     * @return 
     */
    public List<? extends AMethod> selectMethod() {
        return select(weaver.kadabra.abstracts.joinpoints.AMethod.class, SelectOp.DESCENDANTS);
    }

    /**
     * comments that start with @ followed by the pragma name
     * @return 
     */
    public List<? extends APragma> selectPragma() {
        return select(weaver.kadabra.abstracts.joinpoints.APragma.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select functions
     * @return 
     */
    public List<? extends AMethod> selectFunction() {
        return select(weaver.kadabra.abstracts.joinpoints.AMethod.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select comments
     * @return 
     */
    public List<? extends AComment> selectComment() {
        return select(weaver.kadabra.abstracts.joinpoints.AComment.class, SelectOp.DESCENDANTS);
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
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "addClass", this, Optional.empty(), newClass);
        	}
        	this.addClassImpl(newClass);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "addClass", this, Optional.empty(), newClass);
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "addClass", e);
        }
    }

    /**
     * 
     * @param newInterface 
     */
    public void addInterfaceImpl(AInterface newInterface) {
        throw new UnsupportedOperationException(get_class()+": Action addInterface not implemented ");
    }

    /**
     * 
     * @param newInterface 
     */
    public final void addInterface(AInterface newInterface) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "addInterface", this, Optional.empty(), newInterface);
        	}
        	this.addInterfaceImpl(newInterface);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "addInterface", this, Optional.empty(), newInterface);
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "addInterface", e);
        }
    }

    /**
     * add a new method inside the class
     * @param modifiers 
     * @param returnType 
     * @param name 
     * @param param 
     * @param code 
     */
    public AMethod newMethodImpl(String[] modifiers, String returnType, String name, Pair[] param, String code) {
        throw new UnsupportedOperationException(get_class()+": Action newMethod not implemented ");
    }

    /**
     * add a new method inside the class
     * @param modifiers 
     * @param returnType 
     * @param name 
     * @param param 
     * @param code 
     */
    public final AMethod newMethod(String[] modifiers, String returnType, String name, Pair[] param, String code) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "newMethod", this, Optional.empty(), modifiers, returnType, name, param, code);
        	}
        	AMethod result = this.newMethodImpl(modifiers, returnType, name, param, code);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "newMethod", this, Optional.ofNullable(result), modifiers, returnType, name, param, code);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newMethod", e);
        }
    }

    /**
     * overload which accepts 4 parameters (code is empty string)
     * @param modifiers 
     * @param returnType 
     * @param name 
     * @param param 
     */
    public AMethod newMethodImpl(String[] modifiers, String returnType, String name, Pair[] param) {
        throw new UnsupportedOperationException(get_class()+": Action newMethod not implemented ");
    }

    /**
     * overload which accepts 4 parameters (code is empty string)
     * @param modifiers 
     * @param returnType 
     * @param name 
     * @param param 
     */
    public final AMethod newMethod(String[] modifiers, String returnType, String name, Pair[] param) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "newMethod", this, Optional.empty(), modifiers, returnType, name, param);
        	}
        	AMethod result = this.newMethodImpl(modifiers, returnType, name, param);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "newMethod", this, Optional.ofNullable(result), modifiers, returnType, name, param);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newMethod", e);
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
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "insertMethod", this, Optional.empty(), code);
        	}
        	this.insertMethodImpl(code);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "insertMethod", this, Optional.empty(), code);
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "insertMethod", e);
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
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "insertCode", this, Optional.empty(), code);
        	}
        	this.insertCodeImpl(code);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "insertCode", this, Optional.empty(), code);
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "insertCode", e);
        }
    }

    /**
     * 
     * @param _interface 
     */
    public void addImplementImpl(AInterface _interface) {
        throw new UnsupportedOperationException(get_class()+": Action addImplement not implemented ");
    }

    /**
     * 
     * @param _interface 
     */
    public final void addImplement(AInterface _interface) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "addImplement", this, Optional.empty(), _interface);
        	}
        	this.addImplementImpl(_interface);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "addImplement", this, Optional.empty(), _interface);
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "addImplement", e);
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
    public final AField newField(String[] modifiers, String type, String name, String defaultValue) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "newField", this, Optional.empty(), modifiers, type, name, defaultValue);
        	}
        	AField result = this.newFieldImpl(modifiers, type, name, defaultValue);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "newField", this, Optional.ofNullable(result), modifiers, type, name, defaultValue);
        	}
        	return result;
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
    public final AField newField(String[] modifiers, String type, String name) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "newField", this, Optional.empty(), modifiers, type, name);
        	}
        	AField result = this.newFieldImpl(modifiers, type, name);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "newField", this, Optional.ofNullable(result), modifiers, type, name);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newField", e);
        }
    }

    /**
     * 
     */
    @Override
    public List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
        	case "field": 
        		joinPointList = selectField();
        		break;
        	case "executable": 
        		joinPointList = selectExecutable();
        		break;
        	case "method": 
        		joinPointList = selectMethod();
        		break;
        	case "pragma": 
        		joinPointList = selectPragma();
        		break;
        	case "function": 
        		joinPointList = selectFunction();
        		break;
        	case "comment": 
        		joinPointList = selectComment();
        		break;
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
        attributes.add("name");
        attributes.add("qualifiedName");
        attributes.add("superClass");
        attributes.add("superClassJp");
        attributes.add("package");
        attributes.add("interfaces");
        attributes.add("javadoc");
        attributes.add("isSubtypeOf");
    }

    /**
     * 
     */
    @Override
    protected void fillWithSelects(List<String> selects) {
        super.fillWithSelects(selects);
        selects.add("field");
        selects.add("executable");
        selects.add("method");
        selects.add("pragma");
        selects.add("function");
        selects.add("comment");
    }

    /**
     * 
     */
    @Override
    protected void fillWithActions(List<String> actions) {
        super.fillWithActions(actions);
        actions.add("void addClass(class)");
        actions.add("void addInterface(interface)");
        actions.add("method newMethod(String[], String, String, Pair[], String)");
        actions.add("method newMethod(String[], String, String, Pair[])");
        actions.add("void insertMethod(template)");
        actions.add("void insertCode(template)");
        actions.add("void addImplement(interface)");
        actions.add("field newField(String[], String, String, String)");
        actions.add("field newField(String[], String, String)");
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
        NAME("name"),
        QUALIFIEDNAME("qualifiedName"),
        SUPERCLASS("superClass"),
        SUPERCLASSJP("superClassJp"),
        PACKAGE("package"),
        INTERFACES("interfaces"),
        JAVADOC("javadoc"),
        ISSUBTYPEOF("isSubtypeOf"),
        PARENT("parent"),
        ISSTATIC("isStatic"),
        CODE("code"),
        AST("ast"),
        ISBLOCK("isBlock"),
        LINE("line"),
        ANCESTOR("ancestor"),
        ANNOTATIONS("annotations"),
        MODIFIERS("modifiers"),
        DESCENDANTS("descendants"),
        ISSTATEMENT("isStatement"),
        ASTPARENT("astParent"),
        CHILDREN("children"),
        HASMODIFIER("hasModifier"),
        NUMCHILDREN("numChildren"),
        SRCCODE("srcCode"),
        ISFINAL("isFinal"),
        CHILD("child");
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
