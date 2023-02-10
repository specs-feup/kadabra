package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.weaver.interf.events.Stage;
import java.util.Optional;
import org.lara.interpreter.exception.AttributeException;
import java.util.List;
import org.lara.interpreter.weaver.interf.SelectOp;
import org.lara.interpreter.exception.ActionException;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Auto-Generated class for join point ACall
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class ACall extends AExpression {

    protected AExpression aExpression;

    /**
     * 
     */
    public ACall(AExpression aExpression){
        this.aExpression = aExpression;
    }
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
     * Get value on attribute decl
     * @return the attribute's value
     */
    public abstract AMethod getDeclImpl();

    /**
     * Get value on attribute decl
     * @return the attribute's value
     */
    public final Object getDecl() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "decl", Optional.empty());
        	}
        	AMethod result = this.getDeclImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "decl", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "decl", e);
        }
    }

    /**
     * Get value on attribute simpleDecl
     * @return the attribute's value
     */
    public abstract String getSimpleDeclImpl();

    /**
     * Get value on attribute simpleDecl
     * @return the attribute's value
     */
    public final Object getSimpleDecl() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "simpleDecl", Optional.empty());
        	}
        	String result = this.getSimpleDeclImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "simpleDecl", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "simpleDecl", e);
        }
    }

    /**
     * Get value on attribute qualifiedDecl
     * @return the attribute's value
     */
    public abstract String getQualifiedDeclImpl();

    /**
     * Get value on attribute qualifiedDecl
     * @return the attribute's value
     */
    public final Object getQualifiedDecl() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "qualifiedDecl", Optional.empty());
        	}
        	String result = this.getQualifiedDeclImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "qualifiedDecl", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "qualifiedDecl", e);
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
     * Get value on attribute executable
     * @return the attribute's value
     */
    public abstract String getExecutableImpl();

    /**
     * Get value on attribute executable
     * @return the attribute's value
     */
    public final Object getExecutable() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "executable", Optional.empty());
        	}
        	String result = this.getExecutableImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "executable", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "executable", e);
        }
    }

    /**
     * 
     */
    public void defExecutableImpl(String value) {
        throw new UnsupportedOperationException("Join point "+get_class()+": Action def executable with type String not implemented ");
    }

    /**
     * 
     */
    public void defExecutableImpl(AMethod value) {
        throw new UnsupportedOperationException("Join point "+get_class()+": Action def executable with type AMethod not implemented ");
    }

    /**
     * Get value on attribute target
     * @return the attribute's value
     */
    public abstract String getTargetImpl();

    /**
     * Get value on attribute target
     * @return the attribute's value
     */
    public final Object getTarget() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "target", Optional.empty());
        	}
        	String result = this.getTargetImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "target", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "target", e);
        }
    }

    /**
     * 
     */
    public void defTargetImpl(String value) {
        throw new UnsupportedOperationException("Join point "+get_class()+": Action def target with type String not implemented ");
    }

    /**
     * 
     */
    public void defTargetImpl(AExpression value) {
        throw new UnsupportedOperationException("Join point "+get_class()+": Action def target with type AExpression not implemented ");
    }

    /**
     * Get value on attribute targetType
     * @return the attribute's value
     */
    public abstract AType getTargetTypeImpl();

    /**
     * Get value on attribute targetType
     * @return the attribute's value
     */
    public final Object getTargetType() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "targetType", Optional.empty());
        	}
        	AType result = this.getTargetTypeImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "targetType", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "targetType", e);
        }
    }

    /**
     * Get value on attribute returnType
     * @return the attribute's value
     */
    public abstract String getReturnTypeImpl();

    /**
     * Get value on attribute returnType
     * @return the attribute's value
     */
    public final Object getReturnType() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "returnType", Optional.empty());
        	}
        	String result = this.getReturnTypeImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "returnType", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "returnType", e);
        }
    }

    /**
     * Get value on attribute returnTypeJp
     * @return the attribute's value
     */
    public abstract ATypeReference getReturnTypeJpImpl();

    /**
     * Get value on attribute returnTypeJp
     * @return the attribute's value
     */
    public final Object getReturnTypeJp() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "returnTypeJp", Optional.empty());
        	}
        	ATypeReference result = this.getReturnTypeJpImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "returnTypeJp", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "returnTypeJp", e);
        }
    }

    /**
     * Get value on attribute arguments
     * @return the attribute's value
     */
    public abstract AExpression[] getArgumentsArrayImpl();

    /**
     * Get value on attribute arguments
     * @return the attribute's value
     */
    public Object getArgumentsImpl() {
        AExpression[] aExpressionArrayImpl0 = getArgumentsArrayImpl();
        Object nativeArray0 = getWeaverEngine().getScriptEngine().toNativeArray(aExpressionArrayImpl0);
        return nativeArray0;
    }

    /**
     * Get value on attribute arguments
     * @return the attribute's value
     */
    public final Object getArguments() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "arguments", Optional.empty());
        	}
        	Object result = this.getArgumentsImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "arguments", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "arguments", e);
        }
    }

    /**
     * 
     */
    public void defArgumentsImpl(AExpression[] value) {
        throw new UnsupportedOperationException("Join point "+get_class()+": Action def arguments with type AExpression not implemented ");
    }

    /**
     * Default implementation of the method used by the lara interpreter to select args
     * @return 
     */
    public List<? extends AExpression> selectArg() {
        return select(weaver.kadabra.abstracts.joinpoints.AExpression.class, SelectOp.DESCENDANTS);
    }

    /**
     * 
     * @param location 
     * @param position 
     */
    public ACall cloneImpl(AStatement location, String position) {
        throw new UnsupportedOperationException(get_class()+": Action clone not implemented ");
    }

    /**
     * 
     * @param location 
     * @param position 
     */
    public final ACall clone(AStatement location, String position) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "clone", this, Optional.empty(), location, position);
        	}
        	ACall result = this.cloneImpl(location, position);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "clone", this, Optional.ofNullable(result), location, position);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "clone", e);
        }
    }

    /**
     * 
     * @param newArguments 
     */
    public void setArgumentsImpl(AExpression[] newArguments) {
        throw new UnsupportedOperationException(get_class()+": Action setArguments not implemented ");
    }

    /**
     * 
     * @param newArguments 
     */
    public final void setArguments(AExpression[] newArguments) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "setArguments", this, Optional.empty(), new Object[] { newArguments});
        	}
        	this.setArgumentsImpl(newArguments);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "setArguments", this, Optional.empty(), new Object[] { newArguments});
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setArguments", e);
        }
    }

    /**
     * Get value on attribute kind
     * @return the attribute's value
     */
    @Override
    public String getKindImpl() {
        return this.aExpression.getKindImpl();
    }

    /**
     * Get value on attribute type
     * @return the attribute's value
     */
    @Override
    public String getTypeImpl() {
        return this.aExpression.getTypeImpl();
    }

    /**
     * Get value on attribute qualifiedType
     * @return the attribute's value
     */
    @Override
    public String getQualifiedTypeImpl() {
        return this.aExpression.getQualifiedTypeImpl();
    }

    /**
     * Get value on attribute typeReference
     * @return the attribute's value
     */
    @Override
    public ATypeReference getTypeReferenceImpl() {
        return this.aExpression.getTypeReferenceImpl();
    }

    /**
     * Get value on attribute test
     * @return the attribute's value
     */
    @Override
    public Integer getTestImpl() {
        return this.aExpression.getTestImpl();
    }

    /**
     * Method used by the lara interpreter to select exprs
     * @return 
     */
    @Override
    public List<? extends AExpression> selectExpr() {
        return this.aExpression.selectExpr();
    }

    /**
     * Method used by the lara interpreter to select vars
     * @return 
     */
    @Override
    public List<? extends AVar> selectVar() {
        return this.aExpression.selectVar();
    }

    /**
     * Method used by the lara interpreter to select arrayAccesss
     * @return 
     */
    @Override
    public List<? extends AArrayAccess> selectArrayAccess() {
        return this.aExpression.selectArrayAccess();
    }

    /**
     * Method used by the lara interpreter to select binaryExpressions
     * @return 
     */
    @Override
    public List<? extends ABinaryExpression> selectBinaryExpression() {
        return this.aExpression.selectBinaryExpression();
    }

    /**
     * Method used by the lara interpreter to select binaryExprs
     * @return 
     */
    @Override
    public List<? extends ABinaryExpression> selectBinaryExpr() {
        return this.aExpression.selectBinaryExpr();
    }

    /**
     * 
     */
    public void defTestImpl(Integer value) {
        this.aExpression.defTestImpl(value);
    }

    /**
     * 
     */
    public void defTestImpl(AExpression value) {
        this.aExpression.defTestImpl(value);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertBeforeImpl(AJoinPoint node) {
        return this.aExpression.insertBeforeImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return this.aExpression.insertBeforeImpl(code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        return this.aExpression.insertAfterImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return this.aExpression.insertAfterImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return this.aExpression.insertReplaceImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return this.aExpression.insertReplaceImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint replaceWithImpl(AJoinPoint jp) {
        return this.aExpression.replaceWithImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint replaceWithImpl(String code) {
        return this.aExpression.replaceWithImpl(code);
    }

    /**
     * 
     */
    @Override
    public AJoinPoint copyImpl() {
        return this.aExpression.copyImpl();
    }

    /**
     * 
     */
    @Override
    public void removeImpl() {
        this.aExpression.removeImpl();
    }

    /**
     * 
     * @param annotation 
     */
    @Override
    public void removeAnnotationImpl(AAnnotation annotation) {
        this.aExpression.removeAnnotationImpl(annotation);
    }

    /**
     * 
     * @param varName 
     * @param location 
     * @param position 
     */
    @Override
    public void extractImpl(String varName, AStatement location, String position) {
        this.aExpression.extractImpl(varName, location, position);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return this.aExpression.insertImpl(position, code);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return this.aExpression.insertImpl(position, code);
    }

    /**
     * 
     */
    @Override
    public Optional<? extends AExpression> getSuper() {
        return Optional.of(this.aExpression);
    }

    /**
     * 
     */
    @Override
    public final List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
        	case "arg": 
        		joinPointList = selectArg();
        		break;
        	case "expr": 
        		joinPointList = selectExpr();
        		break;
        	case "var": 
        		joinPointList = selectVar();
        		break;
        	case "arrayAccess": 
        		joinPointList = selectArrayAccess();
        		break;
        	case "binaryExpression": 
        		joinPointList = selectBinaryExpression();
        		break;
        	case "binaryExpr": 
        		joinPointList = selectBinaryExpr();
        		break;
        	default:
        		joinPointList = this.aExpression.select(selectName);
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
        case "executable": {
        	if(value instanceof String){
        		this.defExecutableImpl((String)value);
        		return;
        	}
        	if(value instanceof AMethod){
        		this.defExecutableImpl((AMethod)value);
        		return;
        	}
        	this.unsupportedTypeForDef(attribute, value);
        }
        case "target": {
        	if(value instanceof String){
        		this.defTargetImpl((String)value);
        		return;
        	}
        	if(value instanceof AExpression){
        		this.defTargetImpl((AExpression)value);
        		return;
        	}
        	this.unsupportedTypeForDef(attribute, value);
        }
        case "arguments": {
        	if(value instanceof AExpression[]){
        		this.defArgumentsImpl((AExpression[])value);
        		return;
        	}
        	this.unsupportedTypeForDef(attribute, value);
        }
        case "test": {
        	if(value instanceof Integer){
        		this.defTestImpl((Integer)value);
        		return;
        	}
        	if(value instanceof AExpression){
        		this.defTestImpl((AExpression)value);
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
        this.aExpression.fillWithAttributes(attributes);
        attributes.add("name");
        attributes.add("decl");
        attributes.add("simpleDecl");
        attributes.add("qualifiedDecl");
        attributes.add("declarator");
        attributes.add("executable");
        attributes.add("target");
        attributes.add("targetType");
        attributes.add("returnType");
        attributes.add("returnTypeJp");
        attributes.add("arguments");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        this.aExpression.fillWithSelects(selects);
        selects.add("arg");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithActions(List<String> actions) {
        this.aExpression.fillWithActions(actions);
        actions.add("call clone(statement, String)");
        actions.add("void setArguments(expression[])");
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "call";
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
        return this.aExpression.instanceOf(joinpointClass);
    }
    /**
     * 
     */
    protected enum CallAttributes {
        NAME("name"),
        DECL("decl"),
        SIMPLEDECL("simpleDecl"),
        QUALIFIEDDECL("qualifiedDecl"),
        DECLARATOR("declarator"),
        EXECUTABLE("executable"),
        TARGET("target"),
        TARGETTYPE("targetType"),
        RETURNTYPE("returnType"),
        RETURNTYPEJP("returnTypeJp"),
        ARGUMENTS("arguments"),
        KIND("kind"),
        TYPE("type"),
        QUALIFIEDTYPE("qualifiedType"),
        TYPEREFERENCE("typeReference"),
        TEST("test"),
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
        private CallAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<CallAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(CallAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
