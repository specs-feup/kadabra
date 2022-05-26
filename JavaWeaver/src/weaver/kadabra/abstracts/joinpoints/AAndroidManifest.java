package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.weaver.interf.events.Stage;
import java.util.Optional;
import org.lara.interpreter.exception.AttributeException;
import java.util.List;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Auto-Generated class for join point AAndroidManifest
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class AAndroidManifest extends AXmlNode {

    protected AXmlNode aXmlNode;

    /**
     * 
     */
    public AAndroidManifest(AXmlNode aXmlNode){
        this.aXmlNode = aXmlNode;
    }
    /**
     * Get value on attribute asJson
     * @return the attribute's value
     */
    public abstract Object getAsJsonImpl();

    /**
     * Get value on attribute asJson
     * @return the attribute's value
     */
    public final Object getAsJson() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "asJson", Optional.empty());
        	}
        	Object result = this.getAsJsonImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "asJson", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "asJson", e);
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
        attributes.add("asJson");
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
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "androidManifest";
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
    protected enum AndroidManifestAttributes {
        ASJSON("asJson"),
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
        private AndroidManifestAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<AndroidManifestAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(AndroidManifestAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
