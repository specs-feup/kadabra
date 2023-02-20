package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.weaver.interf.events.Stage;
import java.util.Optional;
import org.lara.interpreter.exception.ActionException;
import java.util.List;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Auto-Generated class for join point AOpAssignment
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class AOpAssignment extends AAssignment {

    protected AAssignment aAssignment;

    /**
     * 
     */
    public AOpAssignment(AAssignment aAssignment){
        super(aAssignment);
        this.aAssignment = aAssignment;
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
     * Get value on attribute operator
     * @return the attribute's value
     */
    @Override
    public String getOperatorImpl() {
        return this.aAssignment.getOperatorImpl();
    }

    /**
     * Get value on attribute lhs
     * @return the attribute's value
     */
    @Override
    public AExpression getLhsImpl() {
        return this.aAssignment.getLhsImpl();
    }

    /**
     * Get value on attribute rhs
     * @return the attribute's value
     */
    @Override
    public AExpression getRhsImpl() {
        return this.aAssignment.getRhsImpl();
    }

    /**
     * Method used by the lara interpreter to select lhss
     * @return 
     */
    @Override
    public List<? extends AExpression> selectLhs() {
        return this.aAssignment.selectLhs();
    }

    /**
     * Method used by the lara interpreter to select rhss
     * @return 
     */
    @Override
    public List<? extends AExpression> selectRhs() {
        return this.aAssignment.selectRhs();
    }

    /**
     * Get value on attribute kind
     * @return the attribute's value
     */
    @Override
    public String getKindImpl() {
        return this.aAssignment.getKindImpl();
    }

    /**
     * Get value on attribute endLine
     * @return the attribute's value
     */
    @Override
    public Integer getEndLineImpl() {
        return this.aAssignment.getEndLineImpl();
    }

    /**
     * Method used by the lara interpreter to select vars
     * @return 
     */
    @Override
    public List<? extends AVar> selectVar() {
        return this.aAssignment.selectVar();
    }

    /**
     * Method used by the lara interpreter to select calls
     * @return 
     */
    @Override
    public List<? extends ACall> selectCall() {
        return this.aAssignment.selectCall();
    }

    /**
     * 
     */
    public void defOperatorImpl(String value) {
        this.aAssignment.defOperatorImpl(value);
    }

    /**
     * 
     */
    public void defLhsImpl(AExpression value) {
        this.aAssignment.defLhsImpl(value);
    }

    /**
     * 
     */
    public void defRhsImpl(AExpression value) {
        this.aAssignment.defRhsImpl(value);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertBeforeImpl(AJoinPoint node) {
        return this.aAssignment.insertBeforeImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return this.aAssignment.insertBeforeImpl(code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        return this.aAssignment.insertAfterImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return this.aAssignment.insertAfterImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return this.aAssignment.insertReplaceImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return this.aAssignment.insertReplaceImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint replaceWithImpl(AJoinPoint jp) {
        return this.aAssignment.replaceWithImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint replaceWithImpl(String code) {
        return this.aAssignment.replaceWithImpl(code);
    }

    /**
     * 
     */
    @Override
    public AJoinPoint copyImpl() {
        return this.aAssignment.copyImpl();
    }

    /**
     * 
     */
    @Override
    public void removeImpl() {
        this.aAssignment.removeImpl();
    }

    /**
     * 
     * @param annotation 
     */
    @Override
    public void removeAnnotationImpl(AAnnotation annotation) {
        this.aAssignment.removeAnnotationImpl(annotation);
    }

    /**
     * 
     * @param lhs 
     */
    @Override
    public void setLhsImpl(AExpression lhs) {
        this.aAssignment.setLhsImpl(lhs);
    }

    /**
     * 
     * @param rhs 
     */
    @Override
    public void setRhsImpl(AExpression rhs) {
        this.aAssignment.setRhsImpl(rhs);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return this.aAssignment.insertImpl(position, code);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return this.aAssignment.insertImpl(position, code);
    }

    /**
     * 
     */
    @Override
    public Optional<? extends AAssignment> getSuper() {
        return Optional.of(this.aAssignment);
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
        	case "var": 
        		joinPointList = selectVar();
        		break;
        	case "call": 
        		joinPointList = selectCall();
        		break;
        	default:
        		joinPointList = this.aAssignment.select(selectName);
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
        case "lhs": {
        	if(value instanceof AExpression){
        		this.defLhsImpl((AExpression)value);
        		return;
        	}
        	this.unsupportedTypeForDef(attribute, value);
        }
        case "rhs": {
        	if(value instanceof AExpression){
        		this.defRhsImpl((AExpression)value);
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
        this.aAssignment.fillWithAttributes(attributes);
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        this.aAssignment.fillWithSelects(selects);
    }

    /**
     * 
     */
    @Override
    protected final void fillWithActions(List<String> actions) {
        this.aAssignment.fillWithActions(actions);
        actions.add("void setOperator(String)");
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "opAssignment";
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
        return this.aAssignment.instanceOf(joinpointClass);
    }
    /**
     * 
     */
    protected enum OpAssignmentAttributes {
        OPERATOR("operator"),
        LHS("lhs"),
        RHS("rhs"),
        KIND("kind"),
        ENDLINE("endLine"),
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
        private OpAssignmentAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<OpAssignmentAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(OpAssignmentAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
