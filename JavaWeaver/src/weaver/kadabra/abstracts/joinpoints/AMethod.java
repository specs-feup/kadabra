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
 * Auto-Generated class for join point AMethod
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class AMethod extends AExecutable {

    protected AExecutable aExecutable;

    /**
     * 
     */
    public AMethod(AExecutable aExecutable){
        this.aExecutable = aExecutable;
    }
    /**
     * Get value on attribute isStatic
     * @return the attribute's value
     */
    public abstract Boolean getIsStaticImpl();

    /**
     * Get value on attribute isStatic
     * @return the attribute's value
     */
    public final Object getIsStatic() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "isStatic", Optional.empty());
        	}
        	Boolean result = this.getIsStaticImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "isStatic", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isStatic", e);
        }
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
     * Get value on attribute privacy
     * @return the attribute's value
     */
    public abstract String getPrivacyImpl();

    /**
     * Get value on attribute privacy
     * @return the attribute's value
     */
    public final Object getPrivacy() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "privacy", Optional.empty());
        	}
        	String result = this.getPrivacyImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "privacy", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "privacy", e);
        }
    }

    /**
     * 
     */
    public void defPrivacyImpl(String value) {
        throw new UnsupportedOperationException("Join point "+get_class()+": Action def privacy with type String not implemented ");
    }

    /**
     * Get value on attribute toReference
     * @return the attribute's value
     */
    public abstract String getToReferenceImpl();

    /**
     * Get value on attribute toReference
     * @return the attribute's value
     */
    public final Object getToReference() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "toReference", Optional.empty());
        	}
        	String result = this.getToReferenceImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "toReference", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "toReference", e);
        }
    }

    /**
     * Get value on attribute toQualifiedReference
     * @return the attribute's value
     */
    public abstract String getToQualifiedReferenceImpl();

    /**
     * Get value on attribute toQualifiedReference
     * @return the attribute's value
     */
    public final Object getToQualifiedReference() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "toQualifiedReference", Optional.empty());
        	}
        	String result = this.getToQualifiedReferenceImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "toQualifiedReference", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "toQualifiedReference", e);
        }
    }

    /**
     * 
     * @param comment 
     */
    public void addCommentImpl(String comment) {
        throw new UnsupportedOperationException(get_class()+": Action addComment not implemented ");
    }

    /**
     * 
     * @param comment 
     */
    public final void addComment(String comment) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "addComment", this, Optional.empty(), comment);
        	}
        	this.addCommentImpl(comment);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "addComment", this, Optional.empty(), comment);
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "addComment", e);
        }
    }

    /**
     * 
     * @param type 
     * @param name 
     */
    public void addParameterImpl(String type, String name) {
        throw new UnsupportedOperationException(get_class()+": Action addParameter not implemented ");
    }

    /**
     * 
     * @param type 
     * @param name 
     */
    public final void addParameter(String type, String name) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "addParameter", this, Optional.empty(), type, name);
        	}
        	this.addParameterImpl(type, name);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "addParameter", this, Optional.empty(), type, name);
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "addParameter", e);
        }
    }

    /**
     * 
     * @param adaptMethod 
     * @param name 
     */
    public AClass createAdapterImpl(AMethod adaptMethod, String name) {
        throw new UnsupportedOperationException(get_class()+": Action createAdapter not implemented ");
    }

    /**
     * 
     * @param adaptMethod 
     * @param name 
     */
    public final AClass createAdapter(AMethod adaptMethod, String name) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "createAdapter", this, Optional.empty(), adaptMethod, name);
        	}
        	AClass result = this.createAdapterImpl(adaptMethod, name);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "createAdapter", this, Optional.ofNullable(result), adaptMethod, name);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "createAdapter", e);
        }
    }

    /**
     * 
     * @param newName 
     */
    public AMethod cloneImpl(String newName) {
        throw new UnsupportedOperationException(get_class()+": Action clone not implemented ");
    }

    /**
     * 
     * @param newName 
     */
    public final AMethod clone(String newName) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "clone", this, Optional.empty(), newName);
        	}
        	AMethod result = this.cloneImpl(newName);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "clone", this, Optional.ofNullable(result), newName);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "clone", e);
        }
    }

    /**
     * Get value on attribute name
     * @return the attribute's value
     */
    @Override
    public String getNameImpl() {
        return this.aExecutable.getNameImpl();
    }

    /**
     * Get value on attribute returnType
     * @return the attribute's value
     */
    @Override
    public String getReturnTypeImpl() {
        return this.aExecutable.getReturnTypeImpl();
    }

    /**
     * Method used by the lara interpreter to select bodys
     * @return 
     */
    @Override
    public List<? extends ABody> selectBody() {
        return this.aExecutable.selectBody();
    }

    /**
     * Method used by the lara interpreter to select params
     * @return 
     */
    @Override
    public List<? extends ADeclaration> selectParam() {
        return this.aExecutable.selectParam();
    }

    /**
     * 
     */
    public void defNameImpl(String value) {
        this.aExecutable.defNameImpl(value);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertBeforeImpl(AJoinPoint node) {
        return this.aExecutable.insertBeforeImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return this.aExecutable.insertBeforeImpl(code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        return this.aExecutable.insertAfterImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return this.aExecutable.insertAfterImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return this.aExecutable.insertReplaceImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return this.aExecutable.insertReplaceImpl(code);
    }

    /**
     * 
     */
    @Override
    public AJoinPoint copyImpl() {
        return this.aExecutable.copyImpl();
    }

    /**
     * 
     */
    @Override
    public void removeImpl() {
        this.aExecutable.removeImpl();
    }

    /**
     * Sets the name of this executable, returns the previous name
     * @param name 
     */
    @Override
    public String setNameImpl(String name) {
        return this.aExecutable.setNameImpl(name);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return this.aExecutable.insertImpl(position, code);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return this.aExecutable.insertImpl(position, code);
    }

    /**
     * 
     */
    @Override
    public String toString() {
        return this.aExecutable.toString();
    }

    /**
     * 
     */
    @Override
    public Optional<? extends AExecutable> getSuper() {
        return Optional.of(this.aExecutable);
    }

    /**
     * 
     */
    @Override
    public final List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
        	case "body": 
        		joinPointList = selectBody();
        		break;
        	case "param": 
        		joinPointList = selectParam();
        		break;
        	default:
        		joinPointList = this.aExecutable.select(selectName);
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
        case "privacy": {
        	if(value instanceof String){
        		this.defPrivacyImpl((String)value);
        		return;
        	}
        	this.unsupportedTypeForDef(attribute, value);
        }
        case "name": {
        	if(value instanceof String){
        		this.defNameImpl((String)value);
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
        this.aExecutable.fillWithAttributes(attributes);
        attributes.add("isStatic");
        attributes.add("declarator");
        attributes.add("privacy");
        attributes.add("toReference");
        attributes.add("toQualifiedReference");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        this.aExecutable.fillWithSelects(selects);
    }

    /**
     * 
     */
    @Override
    protected final void fillWithActions(List<String> actions) {
        this.aExecutable.fillWithActions(actions);
        actions.add("void addComment(string)");
        actions.add("void addParameter(string, string)");
        actions.add("class createAdapter(method, String)");
        actions.add("method clone(string)");
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "method";
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
        return this.aExecutable.instanceOf(joinpointClass);
    }
    /**
     * 
     */
    protected enum MethodAttributes {
        ISSTATIC("isStatic"),
        DECLARATOR("declarator"),
        PRIVACY("privacy"),
        TOREFERENCE("toReference"),
        TOQUALIFIEDREFERENCE("toQualifiedReference"),
        NAME("name"),
        RETURNTYPE("returnType"),
        PARENT("parent"),
        ISSTATEMENT("isStatement"),
        CODE("code"),
        AST("ast"),
        ISBLOCK("isBlock"),
        CHILDREN("children"),
        LINE("line"),
        ANCESTOR("ancestor"),
        NUMCHILDREN("numChildren"),
        SRCCODE("srcCode"),
        DESCENDANTS("descendants"),
        CHILD("child");
        private String name;

        /**
         * 
         */
        private MethodAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<MethodAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(MethodAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
