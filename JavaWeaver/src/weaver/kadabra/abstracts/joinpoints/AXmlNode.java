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
 * Auto-Generated class for join point AXmlNode
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class AXmlNode extends AJavaWeaverJoinPoint {

    /**
     * Get value on attribute elements
     * @return the attribute's value
     */
    public abstract AXmlElement[] getElementsArrayImpl();

    /**
     * Get value on attribute elements
     * @return the attribute's value
     */
    public Object getElementsImpl() {
        AXmlElement[] aXmlElementArrayImpl0 = getElementsArrayImpl();
        Object nativeArray0 = getWeaverEngine().getScriptEngine().toNativeArray(aXmlElementArrayImpl0);
        return nativeArray0;
    }

    /**
     * Get value on attribute elements
     * @return the attribute's value
     */
    public final Object getElements() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "elements", Optional.empty());
        	}
        	Object result = this.getElementsImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "elements", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "elements", e);
        }
    }

    /**
     * 
     * @param name
     * @return 
     */
    public abstract AXmlElement[] elementsArrayImpl(String name);

    /**
     * 
     * @param name
     * @return 
     */
    public Object elementsImpl(String name) {
        AXmlElement[] aXmlElementArrayImpl0 = elementsArrayImpl(name);
        Object nativeArray0 = getWeaverEngine().getScriptEngine().toNativeArray(aXmlElementArrayImpl0);
        return nativeArray0;
    }

    /**
     * 
     * @param name
     * @return 
     */
    public final Object elements(String name) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "elements", Optional.empty(), name);
        	}
        	Object result = this.elementsImpl(name);
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "elements", Optional.ofNullable(result), name);
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "elements", e);
        }
    }

    /**
     * Get value on attribute text
     * @return the attribute's value
     */
    public abstract String getTextImpl();

    /**
     * Get value on attribute text
     * @return the attribute's value
     */
    public final Object getText() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "text", Optional.empty());
        	}
        	String result = this.getTextImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "text", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "text", e);
        }
    }

    /**
     * 
     */
    public void defTextImpl(String value) {
        throw new UnsupportedOperationException("Join point "+get_class()+": Action def text with type String not implemented ");
    }

    /**
     * elements inside the Android manifest
     * @return 
     */
    public List<? extends AXmlElement> selectElement() {
        return select(weaver.kadabra.abstracts.joinpoints.AXmlElement.class, SelectOp.DESCENDANTS);
    }

    /**
     * 
     * @param text 
     */
    public String setTextImpl(String text) {
        throw new UnsupportedOperationException(get_class()+": Action setText not implemented ");
    }

    /**
     * 
     * @param text 
     */
    public final String setText(String text) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "setText", this, Optional.empty(), text);
        	}
        	String result = this.setTextImpl(text);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "setText", this, Optional.ofNullable(result), text);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setText", e);
        }
    }

    /**
     * 
     */
    @Override
    public List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
        	case "element": 
        		joinPointList = selectElement();
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
        case "text": {
        	if(value instanceof String){
        		this.defTextImpl((String)value);
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
        attributes.add("elements");
        attributes.add("elements");
        attributes.add("text");
    }

    /**
     * 
     */
    @Override
    protected void fillWithSelects(List<String> selects) {
        super.fillWithSelects(selects);
        selects.add("element");
    }

    /**
     * 
     */
    @Override
    protected void fillWithActions(List<String> actions) {
        super.fillWithActions(actions);
        actions.add("String setText(String)");
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public String get_class() {
        return "xmlNode";
    }
    /**
     * 
     */
    protected enum XmlNodeAttributes {
        ELEMENTS("elements"),
        TEXT("text"),
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
        private XmlNodeAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<XmlNodeAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(XmlNodeAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
