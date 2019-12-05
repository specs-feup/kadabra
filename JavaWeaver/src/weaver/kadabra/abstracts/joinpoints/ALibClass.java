package weaver.kadabra.abstracts.joinpoints;

import java.util.List;
import org.lara.interpreter.weaver.interf.SelectOp;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Auto-Generated class for join point ALibClass
 * This class is overwritten by the Weaver Generator.
 * 
 * class that is part of a library, included in the classpath
 * @author Lara Weaver Generator
 */
public abstract class ALibClass extends ANamedType {

    protected ANamedType aNamedType;

    /**
     * 
     */
    public ALibClass(ANamedType aNamedType){
        this.aNamedType = aNamedType;
    }
    /**
     * methods that belong to this class
     * @return 
     */
    public List<? extends ALibMethod> selectLibMethod() {
        return select(weaver.kadabra.abstracts.joinpoints.ALibMethod.class, SelectOp.DESCENDANTS);
    }

    /**
     * Get value on attribute name
     * @return the attribute's value
     */
    @Override
    public String getNameImpl() {
        return this.aNamedType.getNameImpl();
    }

    /**
     * Get value on attribute qualifiedName
     * @return the attribute's value
     */
    @Override
    public String getQualifiedNameImpl() {
        return this.aNamedType.getQualifiedNameImpl();
    }

    /**
     * Get value on attribute superClass
     * @return the attribute's value
     */
    @Override
    public String getSuperClassImpl() {
        return this.aNamedType.getSuperClassImpl();
    }

    /**
     * Get value on attribute _package
     * @return the attribute's value
     */
    @Override
    public String getPackageImpl() {
        return this.aNamedType.getPackageImpl();
    }

    /**
     * Get value on attribute interfacesArrayImpl
     * @return the attribute's value
     */
    @Override
    public String[] getInterfacesArrayImpl() {
        return this.aNamedType.getInterfacesArrayImpl();
    }

    /**
     * Get value on attribute modifiersArrayImpl
     * @return the attribute's value
     */
    @Override
    public String[] getModifiersArrayImpl() {
        return this.aNamedType.getModifiersArrayImpl();
    }

    /**
     * Get value on attribute javadoc
     * @return the attribute's value
     */
    @Override
    public String getJavadocImpl() {
        return this.aNamedType.getJavadocImpl();
    }

    /**
     * Get value on attribute isSubtypeOf
     * @return the attribute's value
     */
    @Override
    public Boolean isSubtypeOfImpl(String type) {
        return this.aNamedType.isSubtypeOfImpl(type);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertBeforeImpl(AJoinPoint node) {
        return this.aNamedType.insertBeforeImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return this.aNamedType.insertBeforeImpl(code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        return this.aNamedType.insertAfterImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return this.aNamedType.insertAfterImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return this.aNamedType.insertReplaceImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return this.aNamedType.insertReplaceImpl(code);
    }

    /**
     * 
     */
    @Override
    public AJoinPoint copyImpl() {
        return this.aNamedType.copyImpl();
    }

    /**
     * 
     */
    @Override
    public void removeImpl() {
        this.aNamedType.removeImpl();
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return this.aNamedType.insertImpl(position, code);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return this.aNamedType.insertImpl(position, code);
    }

    /**
     * 
     */
    @Override
    public String toString() {
        return this.aNamedType.toString();
    }

    /**
     * 
     */
    @Override
    public Optional<? extends ANamedType> getSuper() {
        return Optional.of(this.aNamedType);
    }

    /**
     * 
     */
    @Override
    public final List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
        	case "libMethod": 
        		joinPointList = selectLibMethod();
        		break;
        	default:
        		joinPointList = this.aNamedType.select(selectName);
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
        this.aNamedType.fillWithAttributes(attributes);
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        this.aNamedType.fillWithSelects(selects);
        selects.add("libMethod");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithActions(List<String> actions) {
        this.aNamedType.fillWithActions(actions);
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "libClass";
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
        return this.aNamedType.instanceOf(joinpointClass);
    }
    /**
     * 
     */
    protected enum LibClassAttributes {
        NAME("name"),
        QUALIFIEDNAME("qualifiedName"),
        SUPERCLASS("superClass"),
        PACKAGE("package"),
        INTERFACES("interfaces"),
        MODIFIERS("modifiers"),
        JAVADOC("javadoc"),
        ISSUBTYPEOF("isSubtypeOf"),
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
        private LibClassAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<LibClassAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(LibClassAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
