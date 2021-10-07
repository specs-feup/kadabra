package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.weaver.interf.events.Stage;
import java.util.Optional;
import org.lara.interpreter.exception.AttributeException;
import org.lara.interpreter.exception.ActionException;
import java.util.List;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Auto-Generated class for join point AXmlElement
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class AXmlElement extends AXmlNode {

    protected AXmlNode aXmlNode;

    /**
     * 
     */
    public AXmlElement(AXmlNode aXmlNode){
        this.aXmlNode = aXmlNode;
    }
    /**
     * the name (i.e., tag) of this element
     */
    public abstract String getNameImpl();

    /**
     * the name (i.e., tag) of this element
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
     * 
     * @param name
     * @return 
     */
    public abstract String attributeImpl(String name);

    /**
     * 
     * @param name
     * @return 
     */
    public final Object attribute(String name) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "attribute", Optional.empty(), name);
        	}
        	String result = this.attributeImpl(name);
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "attribute", Optional.ofNullable(result), name);
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "attribute", e);
        }
    }

    /**
     * Get value on attribute attributeNames
     * @return the attribute's value
     */
    public abstract String[] getAttributeNamesArrayImpl();

    /**
     * a list of available attributes in this element
     */
    public Object getAttributeNamesImpl() {
        String[] stringArrayImpl0 = getAttributeNamesArrayImpl();
        Object nativeArray0 = getWeaverEngine().getScriptEngine().toNativeArray(stringArrayImpl0);
        return nativeArray0;
    }

    /**
     * a list of available attributes in this element
     */
    public final Object getAttributeNames() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "attributeNames", Optional.empty());
        	}
        	Object result = this.getAttributeNamesImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "attributeNames", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "attributeNames", e);
        }
    }

    /**
     * 
     * @param name 
     * @param value 
     */
    public String setAttributeImpl(String name, String value) {
        throw new UnsupportedOperationException(get_class()+": Action setAttribute not implemented ");
    }

    /**
     * 
     * @param name 
     * @param value 
     */
    public final String setAttribute(String name, String value) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "setAttribute", this, Optional.empty(), name, value);
        	}
        	String result = this.setAttributeImpl(name, value);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "setAttribute", this, Optional.ofNullable(result), name, value);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setAttribute", e);
        }
    }

    /**
     * Get value on attribute elementsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AXmlElement[] getElementsArrayImpl() {
        return this.aXmlNode.getElementsArrayImpl();
    }

    /**
     * Get value on attribute elementsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AXmlElement[] elementsArrayImpl(String name) {
        return this.aXmlNode.elementsArrayImpl(name);
    }

    /**
     * Get value on attribute text
     * @return the attribute's value
     */
    @Override
    public String getTextImpl() {
        return this.aXmlNode.getTextImpl();
    }

    /**
     * elements inside the Android manifest
     * @return 
     */
    @Override
    public List<? extends AXmlElement> selectElement() {
        return this.aXmlNode.selectElement();
    }

    /**
     * 
     */
    public void defTextImpl(String value) {
        this.aXmlNode.defTextImpl(value);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertBeforeImpl(AJoinPoint node) {
        return this.aXmlNode.insertBeforeImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return this.aXmlNode.insertBeforeImpl(code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        return this.aXmlNode.insertAfterImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return this.aXmlNode.insertAfterImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return this.aXmlNode.insertReplaceImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return this.aXmlNode.insertReplaceImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint replaceWithImpl(AJoinPoint jp) {
        return this.aXmlNode.replaceWithImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint replaceWithImpl(String code) {
        return this.aXmlNode.replaceWithImpl(code);
    }

    /**
     * 
     */
    @Override
    public AJoinPoint copyImpl() {
        return this.aXmlNode.copyImpl();
    }

    /**
     * 
     */
    @Override
    public void removeImpl() {
        this.aXmlNode.removeImpl();
    }

    /**
     * 
     * @param annotation 
     */
    @Override
    public void removeAnnotationImpl(AAnnotation annotation) {
        this.aXmlNode.removeAnnotationImpl(annotation);
    }

    /**
     * 
     * @param text 
     */
    @Override
    public String setTextImpl(String text) {
        return this.aXmlNode.setTextImpl(text);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return this.aXmlNode.insertImpl(position, code);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return this.aXmlNode.insertImpl(position, code);
    }

    /**
     * 
     */
    @Override
    public Optional<? extends AXmlNode> getSuper() {
        return Optional.of(this.aXmlNode);
    }

    /**
     * 
     */
    @Override
    public final List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
        	case "element": 
        		joinPointList = selectElement();
        		break;
        	default:
        		joinPointList = this.aXmlNode.select(selectName);
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
    protected final void fillWithAttributes(List<String> attributes) {
        this.aXmlNode.fillWithAttributes(attributes);
        attributes.add("name");
        attributes.add("attribute");
        attributes.add("attributeNames");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        this.aXmlNode.fillWithSelects(selects);
    }

    /**
     * 
     */
    @Override
    protected final void fillWithActions(List<String> actions) {
        this.aXmlNode.fillWithActions(actions);
        actions.add("String setAttribute(String, String)");
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "xmlElement";
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
        return this.aXmlNode.instanceOf(joinpointClass);
    }
    /**
     * 
     */
    protected enum XmlElementAttributes {
        NAME("name"),
        ATTRIBUTE("attribute"),
        ATTRIBUTENAMES("attributeNames"),
        ELEMENTS("elements"),
        TEXT("text"),
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
        private XmlElementAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<XmlElementAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(XmlElementAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
