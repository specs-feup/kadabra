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
 * Auto-Generated class for join point ABinaryExpression
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class ABinaryExpression extends AExpression {

    protected AExpression aExpression;

    /**
     * 
     */
    public ABinaryExpression(AExpression aExpression){
        this.aExpression = aExpression;
    }
    /**
     * Get value on attribute operator
     * @return the attribute's value
     */
    public abstract String getOperatorImpl();

    /**
     * Get value on attribute operator
     * @return the attribute's value
     */
    public final Object getOperator() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "operator", Optional.empty());
        	}
        	String result = this.getOperatorImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "operator", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "operator", e);
        }
    }

    /**
     * 
     */
    public void defOperatorImpl(String value) {
        throw new UnsupportedOperationException("Join point "+get_class()+": Action def operator with type String not implemented ");
    }

    /**
     * Get value on attribute operands
     * @return the attribute's value
     */
    public abstract AExpression[] getOperandsArrayImpl();

    /**
     * Get value on attribute operands
     * @return the attribute's value
     */
    public Object getOperandsImpl() {
        AExpression[] aExpressionArrayImpl0 = getOperandsArrayImpl();
        Object nativeArray0 = getWeaverEngine().getScriptEngine().toNativeArray(aExpressionArrayImpl0);
        return nativeArray0;
    }

    /**
     * Get value on attribute operands
     * @return the attribute's value
     */
    public final Object getOperands() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "operands", Optional.empty());
        	}
        	Object result = this.getOperandsImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "operands", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "operands", e);
        }
    }

    /**
     * Get value on attribute lhs
     * @return the attribute's value
     */
    public abstract AExpression getLhsImpl();

    /**
     * Get value on attribute lhs
     * @return the attribute's value
     */
    public final Object getLhs() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "lhs", Optional.empty());
        	}
        	AExpression result = this.getLhsImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "lhs", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "lhs", e);
        }
    }

    /**
     * Get value on attribute rhs
     * @return the attribute's value
     */
    public abstract AExpression getRhsImpl();

    /**
     * Get value on attribute rhs
     * @return the attribute's value
     */
    public final Object getRhs() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "rhs", Optional.empty());
        	}
        	AExpression result = this.getRhsImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "rhs", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "rhs", e);
        }
    }

    /**
     * Default implementation of the method used by the lara interpreter to select lhss
     * @return 
     */
    public List<? extends AExpression> selectLhs() {
        return select(weaver.kadabra.abstracts.joinpoints.AExpression.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select rhss
     * @return 
     */
    public List<? extends AExpression> selectRhs() {
        return select(weaver.kadabra.abstracts.joinpoints.AExpression.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select operandss
     * @return 
     */
    public List<? extends AExpression> selectOperands() {
        return select(weaver.kadabra.abstracts.joinpoints.AExpression.class, SelectOp.DESCENDANTS);
    }

    /**
     * 
     * @param operator 
     */
    public void setOperatorImpl(String operator) {
        throw new UnsupportedOperationException(get_class()+": Action setOperator not implemented ");
    }

    /**
     * 
     * @param operator 
     */
    public final void setOperator(String operator) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "setOperator", this, Optional.empty(), operator);
        	}
        	this.setOperatorImpl(operator);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "setOperator", this, Optional.empty(), operator);
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setOperator", e);
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
     * Get value on attribute parent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getParentImpl() {
        return this.aExpression.getParentImpl();
    }

    /**
     * Get value on attribute isStatic
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStaticImpl() {
        return this.aExpression.getIsStaticImpl();
    }

    /**
     * Get value on attribute code
     * @return the attribute's value
     */
    @Override
    public String getCodeImpl() {
        return this.aExpression.getCodeImpl();
    }

    /**
     * Get value on attribute ast
     * @return the attribute's value
     */
    @Override
    public String getAstImpl() {
        return this.aExpression.getAstImpl();
    }

    /**
     * Get value on attribute isBlock
     * @return the attribute's value
     */
    @Override
    public Boolean getIsBlockImpl() {
        return this.aExpression.getIsBlockImpl();
    }

    /**
     * Get value on attribute line
     * @return the attribute's value
     */
    @Override
    public Integer getLineImpl() {
        return this.aExpression.getLineImpl();
    }

    /**
     * Get value on attribute ancestor
     * @return the attribute's value
     */
    @Override
    public AJoinPoint ancestorImpl(String type) {
        return this.aExpression.ancestorImpl(type);
    }

    /**
     * Get value on attribute annotationsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AAnnotation[] getAnnotationsArrayImpl() {
        return this.aExpression.getAnnotationsArrayImpl();
    }

    /**
     * Get value on attribute rightArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getRightArrayImpl() {
        return this.aExpression.getRightArrayImpl();
    }

    /**
     * Get value on attribute modifiersArrayImpl
     * @return the attribute's value
     */
    @Override
    public String[] getModifiersArrayImpl() {
        return this.aExpression.getModifiersArrayImpl();
    }

    /**
     * Get value on attribute descendantsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getDescendantsArrayImpl() {
        return this.aExpression.getDescendantsArrayImpl();
    }

    /**
     * Get value on attribute isStatement
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStatementImpl() {
        return this.aExpression.getIsStatementImpl();
    }

    /**
     * Get value on attribute astParent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAstParentImpl() {
        return this.aExpression.getAstParentImpl();
    }

    /**
     * Get value on attribute childrenArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        return this.aExpression.getChildrenArrayImpl();
    }

    /**
     * Get value on attribute leftArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getLeftArrayImpl() {
        return this.aExpression.getLeftArrayImpl();
    }

    /**
     * Get value on attribute hasModifier
     * @return the attribute's value
     */
    @Override
    public Boolean hasModifierImpl(String modifier) {
        return this.aExpression.hasModifierImpl(modifier);
    }

    /**
     * Get value on attribute numChildren
     * @return the attribute's value
     */
    @Override
    public Integer getNumChildrenImpl() {
        return this.aExpression.getNumChildrenImpl();
    }

    /**
     * Get value on attribute srcCode
     * @return the attribute's value
     */
    @Override
    public String getSrcCodeImpl() {
        return this.aExpression.getSrcCodeImpl();
    }

    /**
     * Get value on attribute isFinal
     * @return the attribute's value
     */
    @Override
    public Boolean getIsFinalImpl() {
        return this.aExpression.getIsFinalImpl();
    }

    /**
     * Get value on attribute id
     * @return the attribute's value
     */
    @Override
    public String getIdImpl() {
        return this.aExpression.getIdImpl();
    }

    /**
     * Get value on attribute child
     * @return the attribute's value
     */
    @Override
    public AJoinPoint childImpl(Integer index) {
        return this.aExpression.childImpl(index);
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
        	case "lhs": 
        		joinPointList = selectLhs();
        		break;
        	case "rhs": 
        		joinPointList = selectRhs();
        		break;
        	case "operands": 
        		joinPointList = selectOperands();
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
        case "operator": {
        	if(value instanceof String){
        		this.defOperatorImpl((String)value);
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
        attributes.add("operator");
        attributes.add("operands");
        attributes.add("lhs");
        attributes.add("rhs");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        this.aExpression.fillWithSelects(selects);
        selects.add("lhs");
        selects.add("rhs");
        selects.add("operands");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithActions(List<String> actions) {
        this.aExpression.fillWithActions(actions);
        actions.add("void setOperator(String)");
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "binaryExpression";
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
    protected enum BinaryExpressionAttributes {
        OPERATOR("operator"),
        OPERANDS("operands"),
        LHS("lhs"),
        RHS("rhs"),
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
        RIGHT("right"),
        MODIFIERS("modifiers"),
        DESCENDANTS("descendants"),
        ISSTATEMENT("isStatement"),
        ASTPARENT("astParent"),
        CHILDREN("children"),
        LEFT("left"),
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
        private BinaryExpressionAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<BinaryExpressionAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(BinaryExpressionAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
