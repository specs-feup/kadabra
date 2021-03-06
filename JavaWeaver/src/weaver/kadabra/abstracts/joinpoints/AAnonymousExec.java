package weaver.kadabra.abstracts.joinpoints;

import java.util.List;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Auto-Generated class for join point AAnonymousExec
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class AAnonymousExec extends AExecutable {

    protected AExecutable aExecutable;

    /**
     * 
     */
    public AAnonymousExec(AExecutable aExecutable){
        this.aExecutable = aExecutable;
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
     * Get value on attribute body
     * @return the attribute's value
     */
    @Override
    public ABody getBodyImpl() {
        return this.aExecutable.getBodyImpl();
    }

    /**
     * Get value on attribute paramsArrayImpl
     * @return the attribute's value
     */
    @Override
    public ADeclaration[] getParamsArrayImpl() {
        return this.aExecutable.getParamsArrayImpl();
    }

    /**
     * Get value on attribute returnRef
     * @return the attribute's value
     */
    @Override
    public ATypeReference getReturnRefImpl() {
        return this.aExecutable.getReturnRefImpl();
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
     * @param jp 
     */
    @Override
    public AJoinPoint replaceWithImpl(AJoinPoint jp) {
        return this.aExecutable.replaceWithImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint replaceWithImpl(String code) {
        return this.aExecutable.replaceWithImpl(code);
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
     * 
     * @param annotation 
     */
    @Override
    public void removeAnnotationImpl(AAnnotation annotation) {
        this.aExecutable.removeAnnotationImpl(annotation);
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
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "anonymousExec";
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
    protected enum AnonymousExecAttributes {
        NAME("name"),
        RETURNTYPE("returnType"),
        BODY("body"),
        PARAMS("params"),
        RETURNREF("returnRef"),
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
        private AnonymousExecAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<AnonymousExecAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(AnonymousExecAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
