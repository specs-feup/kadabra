package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.weaver.interf.events.Stage;
import java.util.Optional;
import org.lara.interpreter.exception.AttributeException;
import java.util.List;
import org.lara.interpreter.weaver.interf.SelectOp;
import org.lara.interpreter.exception.ActionException;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Auto-Generated class for join point AFile
 * This class is overwritten by the Weaver Generator.
 * 
 * Represents a source-code file
 * @author Lara Weaver Generator
 */
public abstract class AFile extends AJavaWeaverJoinPoint {

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
     * Get value on attribute path
     * @return the attribute's value
     */
    public abstract String getPathImpl();

    /**
     * Get value on attribute path
     * @return the attribute's value
     */
    public final Object getPath() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "path", Optional.empty());
        	}
        	String result = this.getPathImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "path", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "path", e);
        }
    }

    /**
     * Get value on attribute dir
     * @return the attribute's value
     */
    public abstract String getDirImpl();

    /**
     * Get value on attribute dir
     * @return the attribute's value
     */
    public final Object getDir() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "dir", Optional.empty());
        	}
        	String result = this.getDirImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "dir", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "dir", e);
        }
    }

    /**
     * Get value on attribute _package
     * @return the attribute's value
     */
    public abstract String getPackageImpl();

    /**
     * Get value on attribute _package
     * @return the attribute's value
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
     * Get value on attribute numClasses
     * @return the attribute's value
     */
    public abstract Integer getNumClassesImpl();

    /**
     * Get value on attribute numClasses
     * @return the attribute's value
     */
    public final Object getNumClasses() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "numClasses", Optional.empty());
        	}
        	Integer result = this.getNumClassesImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "numClasses", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "numClasses", e);
        }
    }

    /**
     * Get value on attribute numInterfaces
     * @return the attribute's value
     */
    public abstract Integer getNumInterfacesImpl();

    /**
     * Get value on attribute numInterfaces
     * @return the attribute's value
     */
    public final Object getNumInterfaces() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "numInterfaces", Optional.empty());
        	}
        	Integer result = this.getNumInterfacesImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "numInterfaces", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "numInterfaces", e);
        }
    }

    /**
     * Represents classes, interfaces and enums
     * @return 
     */
    public List<? extends AType> selectType() {
        return select(weaver.kadabra.abstracts.joinpoints.AType.class, SelectOp.DESCENDANTS);
    }

    /**
     * Represents Java classes
     * @return 
     */
    public List<? extends AClass> selectClass() {
        return select(weaver.kadabra.abstracts.joinpoints.AClass.class, SelectOp.DESCENDANTS);
    }

    /**
     * Represents Java interfaces
     * @return 
     */
    public List<? extends AInterface> selectInterface() {
        return select(weaver.kadabra.abstracts.joinpoints.AInterface.class, SelectOp.DESCENDANTS);
    }

    /**
     * Comments that start with @ followed by the pragma name
     * @return 
     */
    public List<? extends APragma> selectPragma() {
        return select(weaver.kadabra.abstracts.joinpoints.APragma.class, SelectOp.DESCENDANTS);
    }

    /**
     * Java comments
     * @return 
     */
    public List<? extends AComment> selectComment() {
        return select(weaver.kadabra.abstracts.joinpoints.AComment.class, SelectOp.DESCENDANTS);
    }

    /**
     * 
     * @param name 
     * @param extend 
     * @param implement 
     */
    public AClass newClassImpl(String name, String extend, String[] implement) {
        throw new UnsupportedOperationException(get_class()+": Action newClass not implemented ");
    }

    /**
     * 
     * @param name 
     * @param extend 
     * @param implement 
     */
    public final AClass newClass(String name, String extend, String[] implement) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "newClass", this, Optional.empty(), name, extend, implement);
        	}
        	AClass result = this.newClassImpl(name, extend, implement);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "newClass", this, Optional.ofNullable(result), name, extend, implement);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newClass", e);
        }
    }

    /**
     * 
     * @param name 
     */
    public AClass newClassImpl(String name) {
        throw new UnsupportedOperationException(get_class()+": Action newClass not implemented ");
    }

    /**
     * 
     * @param name 
     */
    public final AClass newClass(String name) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "newClass", this, Optional.empty(), name);
        	}
        	AClass result = this.newClassImpl(name);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "newClass", this, Optional.ofNullable(result), name);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newClass", e);
        }
    }

    /**
     * 
     * @param name 
     * @param extend 
     */
    public AInterface newInterfaceImpl(String name, String[] extend) {
        throw new UnsupportedOperationException(get_class()+": Action newInterface not implemented ");
    }

    /**
     * 
     * @param name 
     * @param extend 
     */
    public final AInterface newInterface(String name, String[] extend) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "newInterface", this, Optional.empty(), name, extend);
        	}
        	AInterface result = this.newInterfaceImpl(name, extend);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "newInterface", this, Optional.ofNullable(result), name, extend);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newInterface", e);
        }
    }

    /**
     * 
     * @param name 
     */
    public AInterface newInterfaceImpl(String name) {
        throw new UnsupportedOperationException(get_class()+": Action newInterface not implemented ");
    }

    /**
     * 
     * @param name 
     */
    public final AInterface newInterface(String name) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "newInterface", this, Optional.empty(), name);
        	}
        	AInterface result = this.newInterfaceImpl(name);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "newInterface", this, Optional.ofNullable(result), name);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newInterface", e);
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
     * 
     * @param name 
     * @param keyType 
     * @param _interface 
     * @param methodName 
     */
    public AClass mapVersionsImpl(String name, String keyType, AInterface _interface, String methodName) {
        throw new UnsupportedOperationException(get_class()+": Action mapVersions not implemented ");
    }

    /**
     * 
     * @param name 
     * @param keyType 
     * @param _interface 
     * @param methodName 
     */
    public final AClass mapVersions(String name, String keyType, AInterface _interface, String methodName) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "mapVersions", this, Optional.empty(), name, keyType, _interface, methodName);
        	}
        	AClass result = this.mapVersionsImpl(name, keyType, _interface, methodName);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "mapVersions", this, Optional.ofNullable(result), name, keyType, _interface, methodName);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "mapVersions", e);
        }
    }

    /**
     * 
     */
    @Override
    public final List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
        	case "type": 
        		joinPointList = selectType();
        		break;
        	case "class": 
        		joinPointList = selectClass();
        		break;
        	case "interface": 
        		joinPointList = selectInterface();
        		break;
        	case "pragma": 
        		joinPointList = selectPragma();
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
    public final void defImpl(String attribute, Object value) {
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
    protected final void fillWithAttributes(List<String> attributes) {
        super.fillWithAttributes(attributes);
        attributes.add("name");
        attributes.add("path");
        attributes.add("dir");
        attributes.add("package");
        attributes.add("numClasses");
        attributes.add("numInterfaces");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        super.fillWithSelects(selects);
        selects.add("type");
        selects.add("class");
        selects.add("interface");
        selects.add("pragma");
        selects.add("comment");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithActions(List<String> actions) {
        super.fillWithActions(actions);
        actions.add("class newClass(String, String, String[])");
        actions.add("class newClass(String)");
        actions.add("interface newInterface(String, String[])");
        actions.add("interface newInterface(String)");
        actions.add("void addClass(class)");
        actions.add("void addInterface(interface)");
        actions.add("class mapVersions(String, String, interface, String)");
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "file";
    }
    /**
     * 
     */
    protected enum FileAttributes {
        NAME("name"),
        PATH("path"),
        DIR("dir"),
        PACKAGE("package"),
        NUMCLASSES("numClasses"),
        NUMINTERFACES("numInterfaces"),
        PARENT("parent"),
        ISSTATIC("isStatic"),
        CODE("code"),
        AST("ast"),
        ISBLOCK("isBlock"),
        LINE("line"),
        ANCESTOR("ancestor"),
        MODIFIERS("modifiers"),
        DESCENDANTS("descendants"),
        ISSTATEMENT("isStatement"),
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
        private FileAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<FileAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(FileAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
