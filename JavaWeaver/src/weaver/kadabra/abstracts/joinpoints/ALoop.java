package weaver.kadabra.abstracts.joinpoints;

import weaver.kadabra.enums.LoopType;
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
 * Auto-Generated class for join point ALoop
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class ALoop extends AStatement {

    protected AStatement aStatement;

    /**
     * 
     */
    public ALoop(AStatement aStatement){
        this.aStatement = aStatement;
    }
    /**
     * Get value on attribute type
     * @return the attribute's value
     */
    public abstract LoopType getTypeImpl();

    /**
     * Get value on attribute type
     * @return the attribute's value
     */
    public final Object getType() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "type", Optional.empty());
        	}
        	LoopType result = this.getTypeImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "type", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "type", e);
        }
    }

    /**
     * Get value on attribute rank
     * @return the attribute's value
     */
    public abstract String getRankImpl();

    /**
     * Get value on attribute rank
     * @return the attribute's value
     */
    public final Object getRank() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "rank", Optional.empty());
        	}
        	String result = this.getRankImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "rank", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "rank", e);
        }
    }

    /**
     * Get value on attribute nestedLevel
     * @return the attribute's value
     */
    public abstract Integer getNestedLevelImpl();

    /**
     * Get value on attribute nestedLevel
     * @return the attribute's value
     */
    public final Object getNestedLevel() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "nestedLevel", Optional.empty());
        	}
        	Integer result = this.getNestedLevelImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "nestedLevel", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "nestedLevel", e);
        }
    }

    /**
     * Get value on attribute isInnermost
     * @return the attribute's value
     */
    public abstract Boolean getIsInnermostImpl();

    /**
     * Get value on attribute isInnermost
     * @return the attribute's value
     */
    public final Object getIsInnermost() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "isInnermost", Optional.empty());
        	}
        	Boolean result = this.getIsInnermostImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "isInnermost", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isInnermost", e);
        }
    }

    /**
     * Get value on attribute isOutermost
     * @return the attribute's value
     */
    public abstract Boolean getIsOutermostImpl();

    /**
     * Get value on attribute isOutermost
     * @return the attribute's value
     */
    public final Object getIsOutermost() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "isOutermost", Optional.empty());
        	}
        	Boolean result = this.getIsOutermostImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "isOutermost", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isOutermost", e);
        }
    }

    /**
     * Get value on attribute controlVar
     * @return the attribute's value
     */
    public abstract String getControlVarImpl();

    /**
     * Get value on attribute controlVar
     * @return the attribute's value
     */
    public final Object getControlVar() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "controlVar", Optional.empty());
        	}
        	String result = this.getControlVarImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "controlVar", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "controlVar", e);
        }
    }

    /**
     * Default implementation of the method used by the lara interpreter to select inits
     * @return 
     */
    public List<? extends AStatement> selectInit() {
        return select(weaver.kadabra.abstracts.joinpoints.AStatement.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select conds
     * @return 
     */
    public List<? extends AExpression> selectCond() {
        return select(weaver.kadabra.abstracts.joinpoints.AExpression.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select steps
     * @return 
     */
    public List<? extends AStatement> selectStep() {
        return select(weaver.kadabra.abstracts.joinpoints.AStatement.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select exprs
     * @return 
     */
    public List<? extends AExpression> selectExpr() {
        return select(weaver.kadabra.abstracts.joinpoints.AExpression.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select bodys
     * @return 
     */
    public List<? extends ABody> selectBody() {
        return select(weaver.kadabra.abstracts.joinpoints.ABody.class, SelectOp.DESCENDANTS);
    }

    /**
     * 
     * @param tileName 
     * @param block 
     * @param unique 
     * @param around 
     */
    public AField tileImpl(String tileName, String block, boolean unique, AJoinPoint around) {
        throw new UnsupportedOperationException(get_class()+": Action tile not implemented ");
    }

    /**
     * 
     * @param tileName 
     * @param block 
     * @param unique 
     * @param around 
     */
    public final AField tile(String tileName, String block, boolean unique, AJoinPoint around) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "tile", this, Optional.empty(), tileName, block, unique, around);
        	}
        	AField result = this.tileImpl(tileName, block, unique, around);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "tile", this, Optional.ofNullable(result), tileName, block, unique, around);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "tile", e);
        }
    }

    /**
     * 
     * @param block 
     */
    public void tileImpl(int block) {
        throw new UnsupportedOperationException(get_class()+": Action tile not implemented ");
    }

    /**
     * 
     * @param block 
     */
    public final void tile(int block) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "tile", this, Optional.empty(), block);
        	}
        	this.tileImpl(block);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "tile", this, Optional.empty(), block);
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "tile", e);
        }
    }

    /**
     * 
     * @param loop2 
     */
    public void interchangeImpl(ALoop loop2) {
        throw new UnsupportedOperationException(get_class()+": Action interchange not implemented ");
    }

    /**
     * 
     * @param loop2 
     */
    public final void interchange(ALoop loop2) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "interchange", this, Optional.empty(), loop2);
        	}
        	this.interchangeImpl(loop2);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "interchange", this, Optional.empty(), loop2);
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "interchange", e);
        }
    }

    /**
     * Get value on attribute kind
     * @return the attribute's value
     */
    @Override
    public String getKindImpl() {
        return this.aStatement.getKindImpl();
    }

    /**
     * Get value on attribute endLine
     * @return the attribute's value
     */
    @Override
    public Integer getEndLineImpl() {
        return this.aStatement.getEndLineImpl();
    }

    /**
     * Method used by the lara interpreter to select vars
     * @return 
     */
    @Override
    public List<? extends AVar> selectVar() {
        return this.aStatement.selectVar();
    }

    /**
     * Method used by the lara interpreter to select calls
     * @return 
     */
    @Override
    public List<? extends ACall> selectCall() {
        return this.aStatement.selectCall();
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertBeforeImpl(AJoinPoint node) {
        return this.aStatement.insertBeforeImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return this.aStatement.insertBeforeImpl(code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        return this.aStatement.insertAfterImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return this.aStatement.insertAfterImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return this.aStatement.insertReplaceImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return this.aStatement.insertReplaceImpl(code);
    }

    /**
     * 
     */
    @Override
    public AJoinPoint copyImpl() {
        return this.aStatement.copyImpl();
    }

    /**
     * 
     */
    @Override
    public void removeImpl() {
        this.aStatement.removeImpl();
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return this.aStatement.insertImpl(position, code);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return this.aStatement.insertImpl(position, code);
    }

    /**
     * 
     */
    @Override
    public String toString() {
        return this.aStatement.toString();
    }

    /**
     * 
     */
    @Override
    public Optional<? extends AStatement> getSuper() {
        return Optional.of(this.aStatement);
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
        	case "cond": 
        		joinPointList = selectCond();
        		break;
        	case "step": 
        		joinPointList = selectStep();
        		break;
        	case "expr": 
        		joinPointList = selectExpr();
        		break;
        	case "body": 
        		joinPointList = selectBody();
        		break;
        	case "var": 
        		joinPointList = selectVar();
        		break;
        	case "call": 
        		joinPointList = selectCall();
        		break;
        	default:
        		joinPointList = this.aStatement.select(selectName);
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
        this.aStatement.fillWithAttributes(attributes);
        attributes.add("type");
        attributes.add("rank");
        attributes.add("nestedLevel");
        attributes.add("isInnermost");
        attributes.add("isOutermost");
        attributes.add("controlVar");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        this.aStatement.fillWithSelects(selects);
        selects.add("init");
        selects.add("cond");
        selects.add("step");
        selects.add("expr");
        selects.add("body");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithActions(List<String> actions) {
        this.aStatement.fillWithActions(actions);
        actions.add("field tile(string, template, boolean, joinpoint)");
        actions.add("void tile(int)");
        actions.add("void interchange(loop)");
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "loop";
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
        return this.aStatement.instanceOf(joinpointClass);
    }
    /**
     * 
     */
    protected enum LoopAttributes {
        TYPE("type"),
        RANK("rank"),
        NESTEDLEVEL("nestedLevel"),
        ISINNERMOST("isInnermost"),
        ISOUTERMOST("isOutermost"),
        CONTROLVAR("controlVar"),
        KIND("kind"),
        ENDLINE("endLine"),
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
        private LoopAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<LoopAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(LoopAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
