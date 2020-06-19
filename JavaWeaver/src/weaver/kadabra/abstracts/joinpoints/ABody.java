package weaver.kadabra.abstracts.joinpoints;

import java.util.List;
import org.lara.interpreter.weaver.interf.SelectOp;
import org.lara.interpreter.weaver.interf.events.Stage;
import java.util.Optional;
import org.lara.interpreter.exception.ActionException;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Auto-Generated class for join point ABody
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class ABody extends AStatement {

    protected AStatement aStatement;

    /**
     * 
     */
    public ABody(AStatement aStatement){
        this.aStatement = aStatement;
    }
    /**
     * Default implementation of the method used by the lara interpreter to select statements
     * @return 
     */
    public List<? extends AStatement> selectStatement() {
        return select(weaver.kadabra.abstracts.joinpoints.AStatement.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select stmts
     * @return 
     */
    public List<? extends AStatement> selectStmt() {
        return select(weaver.kadabra.abstracts.joinpoints.AStatement.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select firstStmts
     * @return 
     */
    public List<? extends AStatement> selectFirstStmt() {
        return select(weaver.kadabra.abstracts.joinpoints.AStatement.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select lastStmts
     * @return 
     */
    public List<? extends AStatement> selectLastStmt() {
        return select(weaver.kadabra.abstracts.joinpoints.AStatement.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select declarations
     * @return 
     */
    public List<? extends ALocalVariable> selectDeclaration() {
        return select(weaver.kadabra.abstracts.joinpoints.ALocalVariable.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select loops
     * @return 
     */
    public List<? extends ALoop> selectLoop() {
        return select(weaver.kadabra.abstracts.joinpoints.ALoop.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select ifs
     * @return 
     */
    public List<? extends AIf> selectIf() {
        return select(weaver.kadabra.abstracts.joinpoints.AIf.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select returns
     * @return 
     */
    public List<? extends AReturn> selectReturn() {
        return select(weaver.kadabra.abstracts.joinpoints.AReturn.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select assignments
     * @return 
     */
    public List<? extends AAssignment> selectAssignment() {
        return select(weaver.kadabra.abstracts.joinpoints.AAssignment.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select pragmas
     * @return 
     */
    public List<? extends APragma> selectPragma() {
        return select(weaver.kadabra.abstracts.joinpoints.APragma.class, SelectOp.DESCENDANTS);
    }

    /**
     * Default implementation of the method used by the lara interpreter to select comments
     * @return 
     */
    public List<? extends AComment> selectComment() {
        return select(weaver.kadabra.abstracts.joinpoints.AComment.class, SelectOp.DESCENDANTS);
    }

    /**
     * 
     * @param code 
     */
    public void insertBeginImpl(String code) {
        throw new UnsupportedOperationException(get_class()+": Action insertBegin not implemented ");
    }

    /**
     * 
     * @param code 
     */
    public final void insertBegin(String code) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "insertBegin", this, Optional.empty(), code);
        	}
        	this.insertBeginImpl(code);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "insertBegin", this, Optional.empty(), code);
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "insertBegin", e);
        }
    }

    /**
     * 
     * @param statement 
     */
    public void insertBeginImpl(AStatement statement) {
        throw new UnsupportedOperationException(get_class()+": Action insertBegin not implemented ");
    }

    /**
     * 
     * @param statement 
     */
    public final void insertBegin(AStatement statement) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "insertBegin", this, Optional.empty(), statement);
        	}
        	this.insertBeginImpl(statement);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "insertBegin", this, Optional.empty(), statement);
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "insertBegin", e);
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
     * @param annotation 
     */
    @Override
    public void removeAnnotationImpl(AAnnotation annotation) {
        this.aStatement.removeAnnotationImpl(annotation);
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
        	case "statement": 
        		joinPointList = selectStatement();
        		break;
        	case "stmt": 
        		joinPointList = selectStmt();
        		break;
        	case "firstStmt": 
        		joinPointList = selectFirstStmt();
        		break;
        	case "lastStmt": 
        		joinPointList = selectLastStmt();
        		break;
        	case "declaration": 
        		joinPointList = selectDeclaration();
        		break;
        	case "loop": 
        		joinPointList = selectLoop();
        		break;
        	case "if": 
        		joinPointList = selectIf();
        		break;
        	case "return": 
        		joinPointList = selectReturn();
        		break;
        	case "assignment": 
        		joinPointList = selectAssignment();
        		break;
        	case "pragma": 
        		joinPointList = selectPragma();
        		break;
        	case "comment": 
        		joinPointList = selectComment();
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
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        this.aStatement.fillWithSelects(selects);
        selects.add("statement");
        selects.add("stmt");
        selects.add("firstStmt");
        selects.add("lastStmt");
        selects.add("declaration");
        selects.add("loop");
        selects.add("if");
        selects.add("return");
        selects.add("assignment");
        selects.add("pragma");
        selects.add("comment");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithActions(List<String> actions) {
        this.aStatement.fillWithActions(actions);
        actions.add("void insertBegin(String)");
        actions.add("void insertBegin(statement)");
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "body";
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
    protected enum BodyAttributes {
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
        private BodyAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<BodyAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(BodyAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
