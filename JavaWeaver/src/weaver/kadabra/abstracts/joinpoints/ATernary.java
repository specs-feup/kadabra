package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.weaver.interf.events.Stage;
import java.util.Optional;
import org.lara.interpreter.exception.AttributeException;
import java.util.List;
import org.lara.interpreter.weaver.interf.SelectOp;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Auto-Generated class for join point ATernary
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class ATernary extends AExpression {

    protected AExpression aExpression;

    /**
     * 
     */
    public ATernary(AExpression aExpression){
        this.aExpression = aExpression;
    }
    /**
     * the condition of the ternary expression
     */
    public abstract AExpression getConditionImpl();

    /**
     * the condition of the ternary expression
     */
    public final Object getCondition() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "condition", Optional.empty());
        	}
        	AExpression result = this.getConditionImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "condition", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "condition", e);
        }
    }

    /**
     * alias for attribute 'condition'
     */
    public abstract AExpression getCondImpl();

    /**
     * alias for attribute 'condition'
     */
    public final Object getCond() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "cond", Optional.empty());
        	}
        	AExpression result = this.getCondImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "cond", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "cond", e);
        }
    }

    /**
     * Get value on attribute then
     * @return the attribute's value
     */
    public abstract AExpression getThenImpl();

    /**
     * Get value on attribute then
     * @return the attribute's value
     */
    public final Object getThen() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "then", Optional.empty());
        	}
        	AExpression result = this.getThenImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "then", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "then", e);
        }
    }

    /**
     * Get value on attribute _else
     * @return the attribute's value
     */
    public abstract AExpression getElseImpl();

    /**
     * Get value on attribute _else
     * @return the attribute's value
     */
    public final Object getElse() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "else", Optional.empty());
        	}
        	AExpression result = this.getElseImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "else", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "else", e);
        }
    }

    /**
     * Default implementation of the method used by the lara interpreter to select conditions
     * @return 
     */
    public List<? extends AExpression> selectCondition() {
        return select(weaver.kadabra.abstracts.joinpoints.AExpression.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select thens
     * @return 
     */
    public List<? extends AExpression> selectThen() {
        return select(weaver.kadabra.abstracts.joinpoints.AExpression.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select elses
     * @return 
     */
    public List<? extends AExpression> selectElse() {
        return select(weaver.kadabra.abstracts.joinpoints.AExpression.class, SelectOp.DESCENDANTS);
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
    public String toString() {
        return this.aExpression.toString();
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
        	case "condition": 
        		joinPointList = selectCondition();
        		break;
        	case "then": 
        		joinPointList = selectThen();
        		break;
        	case "else": 
        		joinPointList = selectElse();
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
        attributes.add("condition");
        attributes.add("cond");
        attributes.add("then");
        attributes.add("else");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        this.aExpression.fillWithSelects(selects);
        selects.add("condition");
        selects.add("then");
        selects.add("else");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithActions(List<String> actions) {
        this.aExpression.fillWithActions(actions);
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "ternary";
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
    protected enum TernaryAttributes {
        CONDITION("condition"),
        COND("cond"),
        THEN("then"),
        ELSE("else"),
        KIND("kind"),
        TYPE("type"),
        TYPEREFERENCE("typeReference"),
        TEST("test"),
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
        private TernaryAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<TernaryAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(TernaryAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
