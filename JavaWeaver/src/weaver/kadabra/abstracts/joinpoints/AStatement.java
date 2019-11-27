package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.weaver.interf.events.Stage;
import java.util.Optional;
import org.lara.interpreter.exception.AttributeException;
import java.util.List;
import org.lara.interpreter.weaver.interf.SelectOp;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Auto-Generated class for join point AStatement
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class AStatement extends AJavaWeaverJoinPoint {

    /**
     * Get value on attribute kind
     * @return the attribute's value
     */
    public abstract String getKindImpl();

    /**
     * Get value on attribute kind
     * @return the attribute's value
     */
    public final Object getKind() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "kind", Optional.empty());
        	}
        	String result = this.getKindImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "kind", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "kind", e);
        }
    }

    /**
     * Get value on attribute endLine
     * @return the attribute's value
     */
    public abstract Integer getEndLineImpl();

    /**
     * Get value on attribute endLine
     * @return the attribute's value
     */
    public final Object getEndLine() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "endLine", Optional.empty());
        	}
        	Integer result = this.getEndLineImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "endLine", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "endLine", e);
        }
    }

    /**
     * Default implementation of the method used by the lara interpreter to select vars
     * @return 
     */
    public List<? extends AVar> selectVar() {
        return select(weaver.kadabra.abstracts.joinpoints.AVar.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select calls
     * @return 
     */
    public List<? extends ACall> selectCall() {
        return select(weaver.kadabra.abstracts.joinpoints.ACall.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select binaryExpressions
     * @return 
     */
    public List<? extends ABinaryExpression> selectBinaryExpression() {
        return select(weaver.kadabra.abstracts.joinpoints.ABinaryExpression.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select binaryExprs
     * @return 
     */
    public List<? extends ABinaryExpression> selectBinaryExpr() {
        return select(weaver.kadabra.abstracts.joinpoints.ABinaryExpression.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select arrayAccesss
     * @return 
     */
    public List<? extends AArrayAccess> selectArrayAccess() {
        return select(weaver.kadabra.abstracts.joinpoints.AArrayAccess.class, SelectOp.DESCENDANTS);
    }

    /**
     * 
     */
    @Override
    public List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
        	case "var": 
        		joinPointList = selectVar();
        		break;
        	case "call": 
        		joinPointList = selectCall();
        		break;
        	case "binaryExpression": 
        		joinPointList = selectBinaryExpression();
        		break;
        	case "binaryExpr": 
        		joinPointList = selectBinaryExpr();
        		break;
        	case "arrayAccess": 
        		joinPointList = selectArrayAccess();
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
        default: throw new UnsupportedOperationException("Join point "+get_class()+": attribute '"+attribute+"' cannot be defined");
        }
    }

    /**
     * 
     */
    @Override
    protected void fillWithAttributes(List<String> attributes) {
        super.fillWithAttributes(attributes);
        attributes.add("kind");
        attributes.add("endLine");
    }

    /**
     * 
     */
    @Override
    protected void fillWithSelects(List<String> selects) {
        super.fillWithSelects(selects);
        selects.add("var");
        selects.add("call");
        selects.add("binaryExpression");
        selects.add("binaryExpr");
        selects.add("arrayAccess");
    }

    /**
     * 
     */
    @Override
    protected void fillWithActions(List<String> actions) {
        super.fillWithActions(actions);
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public String get_class() {
        return "statement";
    }
    /**
     * 
     */
    protected enum StatementAttributes {
        KIND("kind"),
        ENDLINE("endLine"),
        PARENT("parent"),
        ISSTATEMENT("isStatement"),
        CODE("code"),
        ISBLOCK("isBlock"),
        LINE("line"),
        ANCESTOR("ancestor"),
        SRCCODE("srcCode"),
        DESCENDANTS("descendants");
        private String name;

        /**
         * 
         */
        private StatementAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<StatementAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(StatementAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
