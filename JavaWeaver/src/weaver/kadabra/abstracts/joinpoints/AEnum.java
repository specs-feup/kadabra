package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

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
     * Get value on attribute isTopLevel
     * @return the attribute's value
     */
    @Override
    public Boolean getIsTopLevelImpl() {
        return this.aClass.getIsTopLevelImpl();
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
     * Get value on attribute interfacesTypesArrayImpl
     * @return the attribute's value
     */
    @Override
    public AInterfaceType[] getInterfacesTypesArrayImpl() {
        return this.aClass.getInterfacesTypesArrayImpl();
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
     * Get value on attribute javadoc
     * @return the attribute's value
     */
    @Override
    public String getJavadocImpl() {
        return this.aClass.getJavadocImpl();
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
     * Get value on attribute packageName
     * @return the attribute's value
     */
    @Override
    public String getPackageNameImpl() {
        return this.aClass.getPackageNameImpl();
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
     * Get value on attribute superClassJp
     * @return the attribute's value
     */
    @Override
    public ATypeReference getSuperClassJpImpl() {
        return this.aClass.getSuperClassJpImpl();
    }

    /**
     * Get value on attribute annotationsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AAnnotation[] getAnnotationsArrayImpl() {
        return this.aClass.getAnnotationsArrayImpl();
    }

    /**
     * Get value on attribute ast
     * @return the attribute's value
     */
    @Override
    public String getAstImpl() {
        return this.aClass.getAstImpl();
    }

    /**
     * Get value on attribute astParent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAstParentImpl() {
        return this.aClass.getAstParentImpl();
    }

    /**
     * Get value on attribute child
     * @return the attribute's value
     */
    @Override
    public AJoinPoint childImpl(Integer index) {
        return this.aClass.childImpl(index);
    }

    /**
     * Get value on attribute childrenArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        return this.aClass.getChildrenArrayImpl();
    }

    /**
     * Get value on attribute code
     * @return the attribute's value
     */
    @Override
    public String getCodeImpl() {
        return this.aClass.getCodeImpl();
    }

    /**
     * Get value on attribute descendantsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getDescendantsArrayImpl() {
        return this.aClass.getDescendantsArrayImpl();
    }

    /**
     * Get value on attribute getAncestor
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAncestorImpl(String type) {
        return this.aClass.getAncestorImpl(type);
    }

    /**
     * Get value on attribute hasModifier
     * @return the attribute's value
     */
    @Override
    public Boolean hasModifierImpl(String modifier) {
        return this.aClass.hasModifierImpl(modifier);
    }

    /**
     * Get value on attribute id
     * @return the attribute's value
     */
    @Override
    public String getIdImpl() {
        return this.aClass.getIdImpl();
    }

    /**
     * Get value on attribute isBlock
     * @return the attribute's value
     */
    @Override
    public Boolean getIsBlockImpl() {
        return this.aClass.getIsBlockImpl();
    }

    /**
     * Get value on attribute isFinal
     * @return the attribute's value
     */
    @Override
    public Boolean getIsFinalImpl() {
        return this.aClass.getIsFinalImpl();
    }

    /**
     * Get value on attribute isInsideLoopHeader
     * @return the attribute's value
     */
    @Override
    public Boolean getIsInsideLoopHeaderImpl() {
        return this.aClass.getIsInsideLoopHeaderImpl();
    }

    /**
     * Get value on attribute isStatement
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStatementImpl() {
        return this.aClass.getIsStatementImpl();
    }

    /**
     * Get value on attribute isStatic
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStaticImpl() {
        return this.aClass.getIsStaticImpl();
    }

    /**
     * Get value on attribute leftArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getLeftArrayImpl() {
        return this.aClass.getLeftArrayImpl();
    }

    /**
     * Get value on attribute line
     * @return the attribute's value
     */
    @Override
    public Integer getLineImpl() {
        return this.aClass.getLineImpl();
    }

    /**
     * Get value on attribute modifiersArrayImpl
     * @return the attribute's value
     */
    @Override
    public String[] getModifiersArrayImpl() {
        return this.aClass.getModifiersArrayImpl();
    }

    /**
     * Get value on attribute numChildren
     * @return the attribute's value
     */
    @Override
    public Integer getNumChildrenImpl() {
        return this.aClass.getNumChildrenImpl();
    }

    /**
     * Get value on attribute parent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getParentImpl() {
        return this.aClass.getParentImpl();
    }

    /**
     * Get value on attribute rightArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getRightArrayImpl() {
        return this.aClass.getRightArrayImpl();
    }

    /**
     * Get value on attribute srcCode
     * @return the attribute's value
     */
    @Override
    public String getSrcCodeImpl() {
        return this.aClass.getSrcCodeImpl();
    }

    /**
     * insert a given class inside the target
     * @param newClass 
     */
    @Override
    public void addClassImpl(AClass newClass) {
        this.aClass.addClassImpl(newClass);
    }

    /**
     * 
     * @param interfaceType 
     */
    @Override
    public void addImplementImpl(AInterfaceType interfaceType) {
        this.aClass.addImplementImpl(interfaceType);
    }

    /**
     * 
     * @param newInterface 
     */
    @Override
    public void addInterfaceImpl(AInterfaceType newInterface) {
        this.aClass.addInterfaceImpl(newInterface);
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
     * @param name 
     * @param packageName 
     * @param method 
     * @param associate 
     * @param newFile 
     */
    @Override
    public AInterfaceType extractInterfaceImpl(String name, String packageName, AMethod method, boolean associate, boolean newFile) {
        return this.aClass.extractInterfaceImpl(name, packageName, method, associate, newFile);
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
     * @param code 
     */
    @Override
    public void insertCodeImpl(String code) {
        this.aClass.insertCodeImpl(code);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public void insertMethodImpl(String code) {
        this.aClass.insertMethodImpl(code);
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
     * @param code 
     */
    @Override
    public void insertStaticImpl(String code) {
        this.aClass.insertStaticImpl(code);
    }

    /**
     * 
     * @param name 
     * @param keyType 
     * @param interfaceType 
     * @param methodName 
     */
    @Override
    public AClass mapVersionsImpl(String name, String keyType, AInterfaceType interfaceType, String methodName) {
        return this.aClass.mapVersionsImpl(name, keyType, interfaceType, methodName);
    }

    /**
     * 
     * @param modifiers 
     * @param paramLeft 
     * @param paramRight 
     */
    @Override
    public AConstructor newConstructorImpl(String[] modifiers, String[] paramLeft, String[] paramRight) {
        return this.aClass.newConstructorImpl(modifiers, paramLeft, paramRight);
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
        return this.aClass.newFieldImpl(modifiers, type, name, defaultValue);
    }

    /**
     * 
     * @param modifiers 
     * @param type 
     * @param name 
     */
    @Override
    public AField newFieldImpl(String[] modifiers, String type, String name) {
        return this.aClass.newFieldImpl(modifiers, type, name);
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
        return this.aClass.newMethodImpl(modifiers, returnType, name, paramLeft, paramRight, code);
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
        return this.aClass.newMethodImpl(modifiers, returnType, name, paramLeft, paramRight);
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
     * @param interfaceName 
     */
    @Override
    public AInterfaceType removeInterfaceImpl(String interfaceName) {
        return this.aClass.removeInterfaceImpl(interfaceName);
    }

    /**
     * 
     * @param modifier 
     */
    @Override
    public void removeModifierImpl(String modifier) {
        this.aClass.removeModifierImpl(modifier);
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
     * @param value 
     */
    @Override
    public void setLineImpl(int value) {
        this.aClass.setLineImpl(value);
    }

    /**
     * 
     * @param value 
     */
    @Override
    public void setLineImpl(String value) {
        this.aClass.setLineImpl(value);
    }

    /**
     * 
     * @param modifiers 
     */
    @Override
    public void setModifiersImpl(String[] modifiers) {
        this.aClass.setModifiersImpl(modifiers);
    }

    /**
     * 
     */
    @Override
    public Optional<? extends AClass> getSuper() {
        return Optional.of(this.aClass);
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
        ISTOPLEVEL("isTopLevel"),
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
