package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.exception.AttributeException;
import org.lara.interpreter.exception.ActionException;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

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
        Object nativeArray0 = aXmlElementArrayImpl0;
        return nativeArray0;
    }

    /**
     * Get value on attribute elements
     * @return the attribute's value
     */
    public final Object getElements() {
        try {
        	Object result = this.getElementsImpl();
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
    public abstract AXmlElement[] elementsByNameArrayImpl(String name);

    /**
     * 
     * @param name
     * @return 
     */
    public Object elementsByNameImpl(String name) {
        AXmlElement[] aXmlElementArrayImpl0 = elementsByNameArrayImpl(name);
        Object nativeArray0 = aXmlElementArrayImpl0;
        return nativeArray0;
    }

    /**
     * 
     * @param name
     * @return 
     */
    public final Object elementsByName(String name) {
        try {
        	Object result = this.elementsByNameImpl(name);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "elementsByName", e);
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
        	String result = this.getTextImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "text", e);
        }
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
    public final Object setText(String text) {
        try {
        	String result = this.setTextImpl(text);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setText", e);
        }
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
        ELEMENTSBYNAME("elementsByName"),
        TEXT("text"),
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
