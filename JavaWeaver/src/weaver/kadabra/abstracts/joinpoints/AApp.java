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
 * Auto-Generated class for join point AApp
 * This class is overwritten by the Weaver Generator.
 * 
 * Root node that represents the application
 * @author Lara Weaver Generator
 */
public abstract class AApp extends AJavaWeaverJoinPoint {

    /**
     * Get value on attribute folder
     * @return the attribute's value
     */
    public abstract String getFolderImpl();

    /**
     * Get value on attribute folder
     * @return the attribute's value
     */
    public final Object getFolder() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "folder", Optional.empty());
        	}
        	String result = this.getFolderImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "folder", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "folder", e);
        }
    }

    /**
     * 
     * @param Title
     * @return 
     */
    public abstract String showASTImpl(String Title);

    /**
     * 
     * @param Title
     * @return 
     */
    public final Object showAST(String Title) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "showAST", Optional.empty(), Title);
        	}
        	String result = this.showASTImpl(Title);
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "showAST", Optional.ofNullable(result), Title);
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "showAST", e);
        }
    }

    /**
     * files of the application
     * @return 
     */
    public List<? extends AFile> selectFile() {
        return select(weaver.kadabra.abstracts.joinpoints.AFile.class, SelectOp.DESCENDANTS);
    }

    /**
     * class that is part of a library included in the classpath. This select may be slow, depending on the number of included types
     * @return 
     */
    public List<? extends ALibClass> selectLibClass() {
        return select(weaver.kadabra.abstracts.joinpoints.ALibClass.class, SelectOp.DESCENDANTS);
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
        	case "file": 
        		joinPointList = selectFile();
        		break;
        	case "libClass": 
        		joinPointList = selectLibClass();
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
        attributes.add("folder");
        attributes.add("showAST");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        super.fillWithSelects(selects);
        selects.add("file");
        selects.add("libClass");
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
        actions.add("class mapVersions(String, String, interface, String)");
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "app";
    }
    /**
     * 
     */
    protected enum AppAttributes {
        FOLDER("folder"),
        SHOWAST("showAST"),
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
        private AppAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<AppAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(AppAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
