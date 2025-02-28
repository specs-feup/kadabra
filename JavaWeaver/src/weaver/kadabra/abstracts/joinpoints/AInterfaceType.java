package weaver.kadabra.abstracts.joinpoints;

import java.util.List;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Auto-Generated class for join point AInterfaceType
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class AInterfaceType extends AType {

    protected AType aType;

    /**
     * 
     */
    public AInterfaceType(AType aType){
        this.aType = aType;
    }
    /**
     * Get value on attribute interfacesArrayImpl
     * @return the attribute's value
     */
    @Override
    public String[] getInterfacesArrayImpl() {
        return this.aType.getInterfacesArrayImpl();
    }

    /**
     * Get value on attribute interfacesTypesArrayImpl
     * @return the attribute's value
     */
    @Override
    public AInterfaceType[] getInterfacesTypesArrayImpl() {
        return this.aType.getInterfacesTypesArrayImpl();
    }

    /**
     * Get value on attribute isSubtypeOf
     * @return the attribute's value
     */
    @Override
    public Boolean isSubtypeOfImpl(String type) {
        return this.aType.isSubtypeOfImpl(type);
    }

    /**
     * Get value on attribute javadoc
     * @return the attribute's value
     */
    @Override
    public String getJavadocImpl() {
        return this.aType.getJavadocImpl();
    }

    /**
     * Get value on attribute name
     * @return the attribute's value
     */
    @Override
    public String getNameImpl() {
        return this.aType.getNameImpl();
    }

    /**
     * Get value on attribute packageName
     * @return the attribute's value
     */
    @Override
    public String getPackageNameImpl() {
        return this.aType.getPackageNameImpl();
    }

    /**
     * Get value on attribute qualifiedName
     * @return the attribute's value
     */
    @Override
    public String getQualifiedNameImpl() {
        return this.aType.getQualifiedNameImpl();
    }

    /**
     * Get value on attribute superClass
     * @return the attribute's value
     */
    @Override
    public String getSuperClassImpl() {
        return this.aType.getSuperClassImpl();
    }

    /**
     * Get value on attribute superClassJp
     * @return the attribute's value
     */
    @Override
    public ATypeReference getSuperClassJpImpl() {
        return this.aType.getSuperClassJpImpl();
    }

    /**
     * fields inside a class
     * @return 
     */
    @Override
    public List<? extends AField> selectField() {
        return this.aType.selectField();
    }

    /**
     * methods, constructors or static/instance blocks
     * @return 
     */
    @Override
    public List<? extends AExecutable> selectExecutable() {
        return this.aType.selectExecutable();
    }

    /**
     * methods inside a class
     * @return 
     */
    @Override
    public List<? extends AMethod> selectMethod() {
        return this.aType.selectMethod();
    }

    /**
     * comments that start with @ followed by the pragma name
     * @return 
     */
    @Override
    public List<? extends APragma> selectPragma() {
        return this.aType.selectPragma();
    }

    /**
     * Method used by the lara interpreter to select functions
     * @return 
     */
    @Override
    public List<? extends AMethod> selectFunction() {
        return this.aType.selectFunction();
    }

    /**
     * Method used by the lara interpreter to select comments
     * @return 
     */
    @Override
    public List<? extends AComment> selectComment() {
        return this.aType.selectComment();
    }

    /**
     * Get value on attribute annotationsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AAnnotation[] getAnnotationsArrayImpl() {
        return this.aType.getAnnotationsArrayImpl();
    }

    /**
     * Get value on attribute ast
     * @return the attribute's value
     */
    @Override
    public String getAstImpl() {
        return this.aType.getAstImpl();
    }

    /**
     * Get value on attribute astParent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAstParentImpl() {
        return this.aType.getAstParentImpl();
    }

    /**
     * Get value on attribute child
     * @return the attribute's value
     */
    @Override
    public AJoinPoint childImpl(Integer index) {
        return this.aType.childImpl(index);
    }

    /**
     * Get value on attribute childrenArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        return this.aType.getChildrenArrayImpl();
    }

    /**
     * Get value on attribute code
     * @return the attribute's value
     */
    @Override
    public String getCodeImpl() {
        return this.aType.getCodeImpl();
    }

    /**
     * Get value on attribute descendantsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getDescendantsArrayImpl() {
        return this.aType.getDescendantsArrayImpl();
    }

    /**
     * Get value on attribute getAncestor
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAncestorImpl(String type) {
        return this.aType.getAncestorImpl(type);
    }

    /**
     * Get value on attribute hasModifier
     * @return the attribute's value
     */
    @Override
    public Boolean hasModifierImpl(String modifier) {
        return this.aType.hasModifierImpl(modifier);
    }

    /**
     * Get value on attribute id
     * @return the attribute's value
     */
    @Override
    public String getIdImpl() {
        return this.aType.getIdImpl();
    }

    /**
     * Get value on attribute isBlock
     * @return the attribute's value
     */
    @Override
    public Boolean getIsBlockImpl() {
        return this.aType.getIsBlockImpl();
    }

    /**
     * Get value on attribute isFinal
     * @return the attribute's value
     */
    @Override
    public Boolean getIsFinalImpl() {
        return this.aType.getIsFinalImpl();
    }

    /**
     * Get value on attribute isInsideLoopHeader
     * @return the attribute's value
     */
    @Override
    public Boolean getIsInsideLoopHeaderImpl() {
        return this.aType.getIsInsideLoopHeaderImpl();
    }

    /**
     * Get value on attribute isStatement
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStatementImpl() {
        return this.aType.getIsStatementImpl();
    }

    /**
     * Get value on attribute isStatic
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStaticImpl() {
        return this.aType.getIsStaticImpl();
    }

    /**
     * Get value on attribute leftArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getLeftArrayImpl() {
        return this.aType.getLeftArrayImpl();
    }

    /**
     * Get value on attribute line
     * @return the attribute's value
     */
    @Override
    public Integer getLineImpl() {
        return this.aType.getLineImpl();
    }

    /**
     * Get value on attribute modifiersArrayImpl
     * @return the attribute's value
     */
    @Override
    public String[] getModifiersArrayImpl() {
        return this.aType.getModifiersArrayImpl();
    }

    /**
     * Get value on attribute numChildren
     * @return the attribute's value
     */
    @Override
    public Integer getNumChildrenImpl() {
        return this.aType.getNumChildrenImpl();
    }

    /**
     * Get value on attribute parent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getParentImpl() {
        return this.aType.getParentImpl();
    }

    /**
     * Get value on attribute rightArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getRightArrayImpl() {
        return this.aType.getRightArrayImpl();
    }

    /**
     * Get value on attribute srcCode
     * @return the attribute's value
     */
    @Override
    public String getSrcCodeImpl() {
        return this.aType.getSrcCodeImpl();
    }

    /**
     * insert a given class inside the target
     * @param newClass 
     */
    @Override
    public void addClassImpl(AClass newClass) {
        this.aType.addClassImpl(newClass);
    }

    /**
     * 
     * @param interfaceType 
     */
    @Override
    public void addImplementImpl(AInterfaceType interfaceType) {
        this.aType.addImplementImpl(interfaceType);
    }

    /**
     * 
     * @param newInterface 
     */
    @Override
    public void addInterfaceImpl(AInterfaceType newInterface) {
        this.aType.addInterfaceImpl(newInterface);
    }

    /**
     * 
     */
    @Override
    public AJoinPoint copyImpl() {
        return this.aType.copyImpl();
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return this.aType.insertImpl(position, code);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return this.aType.insertImpl(position, code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        return this.aType.insertAfterImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return this.aType.insertAfterImpl(code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertBeforeImpl(AJoinPoint node) {
        return this.aType.insertBeforeImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return this.aType.insertBeforeImpl(code);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public void insertCodeImpl(String code) {
        this.aType.insertCodeImpl(code);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public void insertMethodImpl(String code) {
        this.aType.insertMethodImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return this.aType.insertReplaceImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return this.aType.insertReplaceImpl(code);
    }

    /**
     * 
     * @param modifiers 
     * @param type 
     * @param name 
     * @param defaultValue 
     */
    @Override
    public AField newFieldImpl(String[] modifiers, String type, String name, String defaultValue) {
        return this.aType.newFieldImpl(modifiers, type, name, defaultValue);
    }

    /**
     * 
     * @param modifiers 
     * @param type 
     * @param name 
     */
    @Override
    public AField newFieldImpl(String[] modifiers, String type, String name) {
        return this.aType.newFieldImpl(modifiers, type, name);
    }

    /**
     * add a new method inside the class
     * @param modifiers 
     * @param returnType 
     * @param name 
     * @param paramLeft 
     * @param paramRight 
     * @param code 
     */
    @Override
    public AMethod newMethodImpl(String[] modifiers, String returnType, String name, String[] paramLeft, String[] paramRight, String code) {
        return this.aType.newMethodImpl(modifiers, returnType, name, paramLeft, paramRight, code);
    }

    /**
     * overload which accepts 4 parameters (code is empty string)
     * @param modifiers 
     * @param returnType 
     * @param name 
     * @param paramLeft 
     * @param paramRight 
     */
    @Override
    public AMethod newMethodImpl(String[] modifiers, String returnType, String name, String[] paramLeft, String[] paramRight) {
        return this.aType.newMethodImpl(modifiers, returnType, name, paramLeft, paramRight);
    }

    /**
     * 
     */
    @Override
    public void removeImpl() {
        this.aType.removeImpl();
    }

    /**
     * 
     * @param annotation 
     */
    @Override
    public void removeAnnotationImpl(AAnnotation annotation) {
        this.aType.removeAnnotationImpl(annotation);
    }

    /**
     * 
     * @param interfaceName 
     */
    @Override
    public AInterfaceType removeInterfaceImpl(String interfaceName) {
        return this.aType.removeInterfaceImpl(interfaceName);
    }

    /**
     * 
     * @param modifier 
     */
    @Override
    public void removeModifierImpl(String modifier) {
        this.aType.removeModifierImpl(modifier);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint replaceWithImpl(AJoinPoint jp) {
        return this.aType.replaceWithImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint replaceWithImpl(String code) {
        return this.aType.replaceWithImpl(code);
    }

    /**
     * 
     */
    @Override
    public Optional<? extends AType> getSuper() {
        return Optional.of(this.aType);
    }

    /**
     * 
     */
    @Override
    public final List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
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
        		joinPointList = this.aType.select(selectName);
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
        this.aType.fillWithAttributes(attributes);
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        this.aType.fillWithSelects(selects);
    }

    /**
     * 
     */
    @Override
    protected final void fillWithActions(List<String> actions) {
        this.aType.fillWithActions(actions);
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "interfaceType";
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
        return this.aType.instanceOf(joinpointClass);
    }
    /**
     * 
     */
    protected enum InterfaceTypeAttributes {
        INTERFACES("interfaces"),
        INTERFACESTYPES("interfacesTypes"),
        ISSUBTYPEOF("isSubtypeOf"),
        JAVADOC("javadoc"),
        NAME("name"),
        PACKAGENAME("packageName"),
        QUALIFIEDNAME("qualifiedName"),
        SUPERCLASS("superClass"),
        SUPERCLASSJP("superClassJp"),
        ANNOTATIONS("annotations"),
        AST("ast"),
        ASTPARENT("astParent"),
        CHILD("child"),
        CHILDREN("children"),
        CODE("code"),
        DESCENDANTS("descendants"),
        GETANCESTOR("getAncestor"),
        HASMODIFIER("hasModifier"),
        ID("id"),
        ISBLOCK("isBlock"),
        ISFINAL("isFinal"),
        ISINSIDELOOPHEADER("isInsideLoopHeader"),
        ISSTATEMENT("isStatement"),
        ISSTATIC("isStatic"),
        LEFT("left"),
        LINE("line"),
        MODIFIERS("modifiers"),
        NUMCHILDREN("numChildren"),
        PARENT("parent"),
        RIGHT("right"),
        SRCCODE("srcCode");
        private String name;

        /**
         * 
         */
        private InterfaceTypeAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<InterfaceTypeAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(InterfaceTypeAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
