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
 * Auto-Generated class for join point ADeclaration
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class ADeclaration extends AJavaWeaverJoinPoint {

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
     * Get value on attribute type
     * @return the attribute's value
     */
    public abstract String getTypeImpl();

    /**
     * Get value on attribute type
     * @return the attribute's value
     */
    public final Object getType() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "type", Optional.empty());
        	}
        	String result = this.getTypeImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "type", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "type", e);
        }
    }

    /**
     * Get value on attribute typeReference
     * @return the attribute's value
     */
    public abstract ATypeReference getTypeReferenceImpl();

    /**
     * Get value on attribute typeReference
     * @return the attribute's value
     */
    public final Object getTypeReference() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "typeReference", Optional.empty());
        	}
        	ATypeReference result = this.getTypeReferenceImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "typeReference", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "typeReference", e);
        }
    }

    /**
     * Get value on attribute isArray
     * @return the attribute's value
     */
    public abstract Boolean getIsArrayImpl();

    /**
     * Get value on attribute isArray
     * @return the attribute's value
     */
    public final Object getIsArray() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "isArray", Optional.empty());
        	}
        	Boolean result = this.getIsArrayImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "isArray", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isArray", e);
        }
    }

    /**
     * Get value on attribute isPrimitive
     * @return the attribute's value
     */
    public abstract Boolean getIsPrimitiveImpl();

    /**
     * Get value on attribute isPrimitive
     * @return the attribute's value
     */
    public final Object getIsPrimitive() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "isPrimitive", Optional.empty());
        	}
        	Boolean result = this.getIsPrimitiveImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "isPrimitive", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isPrimitive", e);
        }
    }

    /**
     * Get value on attribute completeType
     * @return the attribute's value
     */
    public abstract String getCompleteTypeImpl();

    /**
     * Get value on attribute completeType
     * @return the attribute's value
     */
    public final Object getCompleteType() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "completeType", Optional.empty());
        	}
        	String result = this.getCompleteTypeImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "completeType", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "completeType", e);
        }
    }

    /**
     * Get value on attribute init
     * @return the attribute's value
     */
    public abstract AExpression getInitImpl();

    /**
     * Get value on attribute init
     * @return the attribute's value
     */
    public final Object getInit() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "init", Optional.empty());
        	}
        	AExpression result = this.getInitImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "init", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "init", e);
        }
    }

    /**
     * 
     */
    public void defInitImpl(AExpression value) {
        throw new UnsupportedOperationException("Join point "+get_class()+": Action def init with type AExpression not implemented ");
    }

    /**
     * Default implementation of the method used by the lara interpreter to select inits
     * @return 
     */
    public List<? extends AExpression> selectInit() {
        return select(weaver.kadabra.abstracts.joinpoints.AExpression.class, SelectOp.DESCENDANTS);
    }

    /**
     * 
     * @param value 
     */
    public void setInitImpl(AExpression value) {
        throw new UnsupportedOperationException(get_class()+": Action setInit not implemented ");
    }

    /**
     * 
     * @param value 
     */
    public final void setInit(AExpression value) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "setInit", this, Optional.empty(), value);
        	}
        	this.setInitImpl(value);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "setInit", this, Optional.empty(), value);
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setInit", e);
        }
    }

    /**
     * 
     */
    @Override
    public List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
        	case "init": 
        		joinPointList = selectInit();
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
        case "init": {
        	if(value instanceof AExpression){
        		this.defInitImpl((AExpression)value);
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
        attributes.add("type");
        attributes.add("typeReference");
        attributes.add("isArray");
        attributes.add("isPrimitive");
        attributes.add("completeType");
        attributes.add("init");
    }

    /**
     * 
     */
    @Override
    protected void fillWithSelects(List<String> selects) {
        super.fillWithSelects(selects);
        selects.add("init");
    }

    /**
     * 
     */
    @Override
    protected void fillWithActions(List<String> actions) {
        super.fillWithActions(actions);
        actions.add("void setInit(expression)");
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public String get_class() {
        return "declaration";
    }
    /**
     * 
     */
    protected enum DeclarationAttributes {
        NAME("name"),
        TYPE("type"),
        TYPEREFERENCE("typeReference"),
        ISARRAY("isArray"),
        ISPRIMITIVE("isPrimitive"),
        COMPLETETYPE("completeType"),
        INIT("init"),
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
        ID("id"),
        CHILD("child");
        private String name;

        /**
         * 
         */
        private DeclarationAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<DeclarationAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(DeclarationAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
