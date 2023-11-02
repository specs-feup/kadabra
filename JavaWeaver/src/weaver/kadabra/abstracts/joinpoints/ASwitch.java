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
 * Auto-Generated class for join point ASwitch
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class ASwitch extends AStatement {

    protected AStatement aStatement;

    /**
     * 
     */
    public ASwitch(AStatement aStatement){
        this.aStatement = aStatement;
    }
    /**
     * Get value on attribute cases
     * @return the attribute's value
     */
    public abstract ACase[] getCasesArrayImpl();

    /**
     * Get value on attribute cases
     * @return the attribute's value
     */
    public Object getCasesImpl() {
        ACase[] aCaseArrayImpl0 = getCasesArrayImpl();
        Object nativeArray0 = getWeaverEngine().getScriptEngine().toNativeArray(aCaseArrayImpl0);
        return nativeArray0;
    }

    /**
     * Get value on attribute cases
     * @return the attribute's value
     */
    public final Object getCases() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "cases", Optional.empty());
        	}
        	Object result = this.getCasesImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "cases", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "cases", e);
        }
    }

    /**
     * Default implementation of the method used by the lara interpreter to select cases
     * @return 
     */
    public List<? extends ACase> selectCase() {
        return select(weaver.kadabra.abstracts.joinpoints.ACase.class, SelectOp.DESCENDANTS);
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
     * Get value on attribute parent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getParentImpl() {
        return this.aStatement.getParentImpl();
    }

    /**
     * Get value on attribute isStatic
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStaticImpl() {
        return this.aStatement.getIsStaticImpl();
    }

    /**
     * Get value on attribute code
     * @return the attribute's value
     */
    @Override
    public String getCodeImpl() {
        return this.aStatement.getCodeImpl();
    }

    /**
     * Get value on attribute ast
     * @return the attribute's value
     */
    @Override
    public String getAstImpl() {
        return this.aStatement.getAstImpl();
    }

    /**
     * Get value on attribute isBlock
     * @return the attribute's value
     */
    @Override
    public Boolean getIsBlockImpl() {
        return this.aStatement.getIsBlockImpl();
    }

    /**
     * Get value on attribute line
     * @return the attribute's value
     */
    @Override
    public Integer getLineImpl() {
        return this.aStatement.getLineImpl();
    }

    /**
     * Get value on attribute ancestor
     * @return the attribute's value
     */
    @Override
    public AJoinPoint ancestorImpl(String type) {
        return this.aStatement.ancestorImpl(type);
    }

    /**
     * Get value on attribute annotationsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AAnnotation[] getAnnotationsArrayImpl() {
        return this.aStatement.getAnnotationsArrayImpl();
    }

    /**
     * Get value on attribute rightArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getRightArrayImpl() {
        return this.aStatement.getRightArrayImpl();
    }

    /**
     * Get value on attribute modifiersArrayImpl
     * @return the attribute's value
     */
    @Override
    public String[] getModifiersArrayImpl() {
        return this.aStatement.getModifiersArrayImpl();
    }

    /**
     * Get value on attribute descendantsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getDescendantsArrayImpl() {
        return this.aStatement.getDescendantsArrayImpl();
    }

    /**
     * Get value on attribute isStatement
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStatementImpl() {
        return this.aStatement.getIsStatementImpl();
    }

    /**
     * Get value on attribute astParent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAstParentImpl() {
        return this.aStatement.getAstParentImpl();
    }

    /**
     * Get value on attribute childrenArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        return this.aStatement.getChildrenArrayImpl();
    }

    /**
     * Get value on attribute leftArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getLeftArrayImpl() {
        return this.aStatement.getLeftArrayImpl();
    }

    /**
     * Get value on attribute hasModifier
     * @return the attribute's value
     */
    @Override
    public Boolean hasModifierImpl(String modifier) {
        return this.aStatement.hasModifierImpl(modifier);
    }

    /**
     * Get value on attribute numChildren
     * @return the attribute's value
     */
    @Override
    public Integer getNumChildrenImpl() {
        return this.aStatement.getNumChildrenImpl();
    }

    /**
     * Get value on attribute srcCode
     * @return the attribute's value
     */
    @Override
    public String getSrcCodeImpl() {
        return this.aStatement.getSrcCodeImpl();
    }

    /**
     * Get value on attribute isFinal
     * @return the attribute's value
     */
    @Override
    public Boolean getIsFinalImpl() {
        return this.aStatement.getIsFinalImpl();
    }

    /**
     * Get value on attribute id
     * @return the attribute's value
     */
    @Override
    public String getIdImpl() {
        return this.aStatement.getIdImpl();
    }

    /**
     * Get value on attribute child
     * @return the attribute's value
     */
    @Override
    public AJoinPoint childImpl(Integer index) {
        return this.aStatement.childImpl(index);
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
     * @param jp 
     */
    @Override
    public AJoinPoint replaceWithImpl(AJoinPoint jp) {
        return this.aStatement.replaceWithImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint replaceWithImpl(String code) {
        return this.aStatement.replaceWithImpl(code);
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
     * @param modifier 
     */
    @Override
    public void removeModifierImpl(String modifier) {
        this.aStatement.removeModifierImpl(modifier);
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
        	case "case": 
        		joinPointList = selectCase();
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
        attributes.add("cases");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        this.aStatement.fillWithSelects(selects);
        selects.add("case");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithActions(List<String> actions) {
        this.aStatement.fillWithActions(actions);
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "switch";
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
    protected enum SwitchAttributes {
        CASES("cases"),
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
        private SwitchAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<SwitchAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(SwitchAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
