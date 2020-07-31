package weaver.kadabra.abstracts.joinpoints;

import java.util.List;
import org.lara.interpreter.weaver.interf.SelectOp;
import weaver.kadabra.entities.Pair;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Auto-Generated class for join point AEnum
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class AEnum extends AClass {

    protected AClass aClass;

    /**
     * 
     */
    public AEnum(AClass aClass){
        super(aClass);
        this.aClass = aClass;
    }
    /**
     * Default implementation of the method used by the lara interpreter to select valuess
     * @return 
     */
    public List<? extends AField> selectValues() {
        return select(weaver.kadabra.abstracts.joinpoints.AField.class, SelectOp.DESCENDANTS);
    }

    /**
     * the class constructors
     * @return 
     */
    @Override
    public List<? extends AConstructor> selectConstructor() {
        return this.aClass.selectConstructor();
    }

    /**
     * anonymous code blocks defined in a class
     * @return 
     */
    @Override
    public List<? extends AAnonymousExec> selectAnonymousExec() {
        return this.aClass.selectAnonymousExec();
    }

    /**
     * Get value on attribute name
     * @return the attribute's value
     */
    @Override
    public String getNameImpl() {
        return this.aClass.getNameImpl();
    }

    /**
     * Get value on attribute qualifiedName
     * @return the attribute's value
     */
    @Override
    public String getQualifiedNameImpl() {
        return this.aClass.getQualifiedNameImpl();
    }

    /**
     * Get value on attribute superClass
     * @return the attribute's value
     */
    @Override
    public String getSuperClassImpl() {
        return this.aClass.getSuperClassImpl();
    }

    /**
     * Get value on attribute _package
     * @return the attribute's value
     */
    @Override
    public String getPackageImpl() {
        return this.aClass.getPackageImpl();
    }

    /**
     * Get value on attribute interfacesArrayImpl
     * @return the attribute's value
     */
    @Override
    public String[] getInterfacesArrayImpl() {
        return this.aClass.getInterfacesArrayImpl();
    }

    /**
     * Get value on attribute javadoc
     * @return the attribute's value
     */
    @Override
    public String getJavadocImpl() {
        return this.aClass.getJavadocImpl();
    }

    /**
     * Get value on attribute isSubtypeOf
     * @return the attribute's value
     */
    @Override
    public Boolean isSubtypeOfImpl(String type) {
        return this.aClass.isSubtypeOfImpl(type);
    }

    /**
     * fields inside a class
     * @return 
     */
    @Override
    public List<? extends AField> selectField() {
        return this.aClass.selectField();
    }

    /**
     * methods, constructors or static/instance blocks
     * @return 
     */
    @Override
    public List<? extends AExecutable> selectExecutable() {
        return this.aClass.selectExecutable();
    }

    /**
     * methods inside a class
     * @return 
     */
    @Override
    public List<? extends AMethod> selectMethod() {
        return this.aClass.selectMethod();
    }

    /**
     * comments that start with @ followed by the pragma name
     * @return 
     */
    @Override
    public List<? extends APragma> selectPragma() {
        return this.aClass.selectPragma();
    }

    /**
     * Method used by the lara interpreter to select functions
     * @return 
     */
    @Override
    public List<? extends AMethod> selectFunction() {
        return this.aClass.selectFunction();
    }

    /**
     * Method used by the lara interpreter to select comments
     * @return 
     */
    @Override
    public List<? extends AComment> selectComment() {
        return this.aClass.selectComment();
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertBeforeImpl(AJoinPoint node) {
        return this.aClass.insertBeforeImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return this.aClass.insertBeforeImpl(code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        return this.aClass.insertAfterImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return this.aClass.insertAfterImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return this.aClass.insertReplaceImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return this.aClass.insertReplaceImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint replaceWithImpl(AJoinPoint jp) {
        return this.aClass.replaceWithImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint replaceWithImpl(String code) {
        return this.aClass.replaceWithImpl(code);
    }

    /**
     * 
     */
    @Override
    public AJoinPoint copyImpl() {
        return this.aClass.copyImpl();
    }

    /**
     * 
     */
    @Override
    public void removeImpl() {
        this.aClass.removeImpl();
    }

    /**
     * 
     * @param annotation 
     */
    @Override
    public void removeAnnotationImpl(AAnnotation annotation) {
        this.aClass.removeAnnotationImpl(annotation);
    }

    /**
     * 
     * @param name 
     * @param keyType 
     * @param _interface 
     * @param methodName 
     */
    @Override
    public AClass mapVersionsImpl(String name, String keyType, AInterface _interface, String methodName) {
        return this.aClass.mapVersionsImpl(name, keyType, _interface, methodName);
    }

    /**
     * 
     * @param modifiers 
     * @param param 
     */
    @Override
    public AConstructor newConstructorImpl(String[] modifiers, Pair[] param) {
        return this.aClass.newConstructorImpl(modifiers, param);
    }

    /**
     * 
     * @param interfaceMethod 
     * @param generatorMethod 
     */
    @Override
    public AMethod newFunctionalClassImpl(AMethod interfaceMethod, AMethod generatorMethod) {
        return this.aClass.newFunctionalClassImpl(interfaceMethod, generatorMethod);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public void insertStaticImpl(String code) {
        this.aClass.insertStaticImpl(code);
    }

    /**
     * 
     * @param name 
     * @param _package 
     * @param method 
     * @param associate 
     * @param newFile 
     */
    @Override
    public AInterface extractInterfaceImpl(String name, String _package, AMethod method, boolean associate, boolean newFile) {
        return this.aClass.extractInterfaceImpl(name, _package, method, associate, newFile);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return this.aClass.insertImpl(position, code);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return this.aClass.insertImpl(position, code);
    }

    /**
     * 
     */
    @Override
    public String toString() {
        return this.aClass.toString();
    }

    /**
     * 
     */
    @Override
    public Optional<? extends AClass> getSuper() {
        return Optional.of(this.aClass);
    }

    /**
     * 
     */
    @Override
    public final List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
        	case "values": 
        		joinPointList = selectValues();
        		break;
        	case "constructor": 
        		joinPointList = selectConstructor();
        		break;
        	case "anonymousExec": 
        		joinPointList = selectAnonymousExec();
        		break;
        	case "field": 
        		joinPointList = selectField();
        		break;
        	case "executable": 
        		joinPointList = selectExecutable();
        		break;
        	case "method": 
        		joinPointList = selectMethod();
        		break;
        	case "pragma": 
        		joinPointList = selectPragma();
        		break;
        	case "function": 
        		joinPointList = selectFunction();
        		break;
        	case "comment": 
        		joinPointList = selectComment();
        		break;
        	default:
        		joinPointList = this.aClass.select(selectName);
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
        this.aClass.fillWithAttributes(attributes);
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        this.aClass.fillWithSelects(selects);
        selects.add("values");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithActions(List<String> actions) {
        this.aClass.fillWithActions(actions);
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "enum";
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
        return this.aClass.instanceOf(joinpointClass);
    }
    /**
     * 
     */
    protected enum EnumAttributes {
        NAME("name"),
        QUALIFIEDNAME("qualifiedName"),
        SUPERCLASS("superClass"),
        PACKAGE("package"),
        INTERFACES("interfaces"),
        JAVADOC("javadoc"),
        ISSUBTYPEOF("isSubtypeOf"),
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
        private EnumAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<EnumAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(EnumAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
