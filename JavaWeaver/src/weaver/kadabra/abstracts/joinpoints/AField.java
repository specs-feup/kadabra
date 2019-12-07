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
 * Auto-Generated class for join point AField
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class AField extends ADeclaration {

    protected ADeclaration aDeclaration;

    /**
     * 
     */
    public AField(ADeclaration aDeclaration){
        this.aDeclaration = aDeclaration;
    }
    /**
     * Get value on attribute declarator
     * @return the attribute's value
     */
    public abstract String getDeclaratorImpl();

    /**
     * Get value on attribute declarator
     * @return the attribute's value
     */
    public final Object getDeclarator() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "declarator", Optional.empty());
        	}
        	String result = this.getDeclaratorImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "declarator", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "declarator", e);
        }
    }

    /**
     * Get value on attribute staticAccess
     * @return the attribute's value
     */
    public abstract String getStaticAccessImpl();

    /**
     * Get value on attribute staticAccess
     * @return the attribute's value
     */
    public final Object getStaticAccess() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "staticAccess", Optional.empty());
        	}
        	String result = this.getStaticAccessImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "staticAccess", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "staticAccess", e);
        }
    }

    /**
     * 
     * @param value 
     */
    public void initImpl(String value) {
        throw new UnsupportedOperationException(get_class()+": Action init not implemented ");
    }

    /**
     * 
     * @param value 
     */
    public final void init(String value) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "init", this, Optional.empty(), value);
        	}
        	this.initImpl(value);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "init", this, Optional.empty(), value);
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "init", e);
        }
    }

    /**
     * Get value on attribute name
     * @return the attribute's value
     */
    @Override
    public String getNameImpl() {
        return this.aDeclaration.getNameImpl();
    }

    /**
     * Get value on attribute type
     * @return the attribute's value
     */
    @Override
    public String getTypeImpl() {
        return this.aDeclaration.getTypeImpl();
    }

    /**
     * Get value on attribute isArray
     * @return the attribute's value
     */
    @Override
    public Boolean getIsArrayImpl() {
        return this.aDeclaration.getIsArrayImpl();
    }

    /**
     * Get value on attribute isPrimitive
     * @return the attribute's value
     */
    @Override
    public Boolean getIsPrimitiveImpl() {
        return this.aDeclaration.getIsPrimitiveImpl();
    }

    /**
     * Get value on attribute completeType
     * @return the attribute's value
     */
    @Override
    public String getCompleteTypeImpl() {
        return this.aDeclaration.getCompleteTypeImpl();
    }

    /**
     * Method used by the lara interpreter to select inits
     * @return 
     */
    @Override
    public List<? extends AExpression> selectInit() {
        return this.aDeclaration.selectInit();
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertBeforeImpl(AJoinPoint node) {
        return this.aDeclaration.insertBeforeImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return this.aDeclaration.insertBeforeImpl(code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        return this.aDeclaration.insertAfterImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return this.aDeclaration.insertAfterImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return this.aDeclaration.insertReplaceImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return this.aDeclaration.insertReplaceImpl(code);
    }

    /**
     * 
     */
    @Override
    public AJoinPoint copyImpl() {
        return this.aDeclaration.copyImpl();
    }

    /**
     * 
     */
    @Override
    public void removeImpl() {
        this.aDeclaration.removeImpl();
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return this.aDeclaration.insertImpl(position, code);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return this.aDeclaration.insertImpl(position, code);
    }

    /**
     * 
     */
    @Override
    public String toString() {
        return this.aDeclaration.toString();
    }

    /**
     * 
     */
    @Override
    public Optional<? extends ADeclaration> getSuper() {
        return Optional.of(this.aDeclaration);
    }

    /**
     * 
     */
    @Override
    public final List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
        	case "init": 
        		joinPointList = selectInit();
        		break;
        	default:
        		joinPointList = this.aDeclaration.select(selectName);
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
        this.aDeclaration.fillWithAttributes(attributes);
        attributes.add("declarator");
        attributes.add("staticAccess");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        this.aDeclaration.fillWithSelects(selects);
    }

    /**
     * 
     */
    @Override
    protected final void fillWithActions(List<String> actions) {
        this.aDeclaration.fillWithActions(actions);
        actions.add("void init(String)");
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "field";
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
        return this.aDeclaration.instanceOf(joinpointClass);
    }
    /**
     * 
     */
    protected enum FieldAttributes {
        DECLARATOR("declarator"),
        STATICACCESS("staticAccess"),
        NAME("name"),
        TYPE("type"),
        ISARRAY("isArray"),
        ISPRIMITIVE("isPrimitive"),
        COMPLETETYPE("completeType"),
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
        private FieldAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<FieldAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(FieldAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
