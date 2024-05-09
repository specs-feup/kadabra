package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.weaver.interf.events.Stage;
import java.util.Optional;
import org.lara.interpreter.exception.AttributeException;
import java.util.List;
import org.lara.interpreter.weaver.interf.SelectOp;
import org.lara.interpreter.exception.ActionException;
import weaver.kadabra.entities.Pair;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Auto-Generated class for join point AClass
 * This class is overwritten by the Weaver Generator.
 * 
 * join point representation of a class
 * @author Lara Weaver Generator
 */
public abstract class AClass extends AType {

    protected AType aType;

    /**
     * 
     */
    public AClass(AType aType){
        this.aType = aType;
    }
    /**
     * Get value on attribute isTopLevel
     * @return the attribute's value
     */
    public abstract Boolean getIsTopLevelImpl();

    /**
     * Get value on attribute isTopLevel
     * @return the attribute's value
     */
    public final Object getIsTopLevel() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "isTopLevel", Optional.empty());
        	}
        	Boolean result = this.getIsTopLevelImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "isTopLevel", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isTopLevel", e);
        }
    }

    /**
     * the class constructors
     * @return 
     */
    public List<? extends AConstructor> selectConstructor() {
        return select(weaver.kadabra.abstracts.joinpoints.AConstructor.class, SelectOp.DESCENDANTS);
    }

    /**
     * anonymous code blocks defined in a class
     * @return 
     */
    public List<? extends AAnonymousExec> selectAnonymousExec() {
        return select(weaver.kadabra.abstracts.joinpoints.AAnonymousExec.class, SelectOp.DESCENDANTS);
    }

    /**
     * 
     * @param name 
     * @param keyType 
     * @param _interface 
     * @param methodName 
     */
    public AClass mapVersionsImpl(String name, String keyType, AInterface _interface, String methodName) {
        throw new UnsupportedOperationException(get_class()+": Action mapVersions not implemented ");
    }

    /**
     * 
     * @param name 
     * @param keyType 
     * @param _interface 
     * @param methodName 
     */
    public final AClass mapVersions(String name, String keyType, AInterface _interface, String methodName) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "mapVersions", this, Optional.empty(), name, keyType, _interface, methodName);
        	}
        	AClass result = this.mapVersionsImpl(name, keyType, _interface, methodName);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "mapVersions", this, Optional.ofNullable(result), name, keyType, _interface, methodName);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "mapVersions", e);
        }
    }

    /**
     * 
     * @param modifiers 
     * @param param 
     */
    public AConstructor newConstructorImpl(String[] modifiers, Pair[] param) {
        throw new UnsupportedOperationException(get_class()+": Action newConstructor not implemented ");
    }

    /**
     * 
     * @param modifiers 
     * @param param 
     */
    public final AConstructor newConstructor(String[] modifiers, Pair[] param) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "newConstructor", this, Optional.empty(), modifiers, param);
        	}
        	AConstructor result = this.newConstructorImpl(modifiers, param);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "newConstructor", this, Optional.ofNullable(result), modifiers, param);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newConstructor", e);
        }
    }

    /**
     * 
     * @param interfaceMethod 
     * @param generatorMethod 
     */
    public AMethod newFunctionalClassImpl(AMethod interfaceMethod, AMethod generatorMethod) {
        throw new UnsupportedOperationException(get_class()+": Action newFunctionalClass not implemented ");
    }

    /**
     * 
     * @param interfaceMethod 
     * @param generatorMethod 
     */
    public final AMethod newFunctionalClass(AMethod interfaceMethod, AMethod generatorMethod) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "newFunctionalClass", this, Optional.empty(), interfaceMethod, generatorMethod);
        	}
        	AMethod result = this.newFunctionalClassImpl(interfaceMethod, generatorMethod);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "newFunctionalClass", this, Optional.ofNullable(result), interfaceMethod, generatorMethod);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newFunctionalClass", e);
        }
    }

    /**
     * 
     * @param code 
     */
    public void insertStaticImpl(String code) {
        throw new UnsupportedOperationException(get_class()+": Action insertStatic not implemented ");
    }

    /**
     * 
     * @param code 
     */
    public final void insertStatic(String code) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "insertStatic", this, Optional.empty(), code);
        	}
        	this.insertStaticImpl(code);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "insertStatic", this, Optional.empty(), code);
        	}
        } catch(Exception e) {
        	throw new ActionException(get_class(), "insertStatic", e);
        }
    }

    /**
     * 
     * @param name 
     * @param _package 
     * @param method 
     * @param associate 
     * @param newFile 
     */
    public AInterface extractInterfaceImpl(String name, String _package, AMethod method, boolean associate, boolean newFile) {
        throw new UnsupportedOperationException(get_class()+": Action extractInterface not implemented ");
    }

    /**
     * 
     * @param name 
     * @param _package 
     * @param method 
     * @param associate 
     * @param newFile 
     */
    public final AInterface extractInterface(String name, String _package, AMethod method, boolean associate, boolean newFile) {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.BEGIN, "extractInterface", this, Optional.empty(), name, _package, method, associate, newFile);
        	}
        	AInterface result = this.extractInterfaceImpl(name, _package, method, associate, newFile);
        	if(hasListeners()) {
        		eventTrigger().triggerAction(Stage.END, "extractInterface", this, Optional.ofNullable(result), name, _package, method, associate, newFile);
        	}
        	return result;
        } catch(Exception e) {
        	throw new ActionException(get_class(), "extractInterface", e);
        }
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
     * Get value on attribute _package
     * @return the attribute's value
     */
    @Override
    public String getPackageImpl() {
        return this.aType.getPackageImpl();
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
    public AInterface[] getInterfacesTypesArrayImpl() {
        return this.aType.getInterfacesTypesArrayImpl();
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
     * Get value on attribute isSubtypeOf
     * @return the attribute's value
     */
    @Override
    public Boolean isSubtypeOfImpl(String type) {
        return this.aType.isSubtypeOfImpl(type);
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
     * Get value on attribute parent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getParentImpl() {
        return this.aType.getParentImpl();
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
     * Get value on attribute code
     * @return the attribute's value
     */
    @Override
    public String getCodeImpl() {
        return this.aType.getCodeImpl();
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
     * Get value on attribute isBlock
     * @return the attribute's value
     */
    @Override
    public Boolean getIsBlockImpl() {
        return this.aType.getIsBlockImpl();
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
     * Get value on attribute line
     * @return the attribute's value
     */
    @Override
    public Integer getLineImpl() {
        return this.aType.getLineImpl();
    }

    /**
     * Get value on attribute ancestor
     * @return the attribute's value
     */
    @Override
    public AJoinPoint ancestorImpl(String type) {
        return this.aType.ancestorImpl(type);
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
     * Get value on attribute rightArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getRightArrayImpl() {
        return this.aType.getRightArrayImpl();
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
     * Get value on attribute descendantsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getDescendantsArrayImpl() {
        return this.aType.getDescendantsArrayImpl();
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
     * Get value on attribute astParent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAstParentImpl() {
        return this.aType.getAstParentImpl();
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
     * Get value on attribute leftArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getLeftArrayImpl() {
        return this.aType.getLeftArrayImpl();
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
     * Get value on attribute numChildren
     * @return the attribute's value
     */
    @Override
    public Integer getNumChildrenImpl() {
        return this.aType.getNumChildrenImpl();
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
     * Get value on attribute isFinal
     * @return the attribute's value
     */
    @Override
    public Boolean getIsFinalImpl() {
        return this.aType.getIsFinalImpl();
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
     * Get value on attribute child
     * @return the attribute's value
     */
    @Override
    public AJoinPoint childImpl(Integer index) {
        return this.aType.childImpl(index);
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
    public AJoinPoint copyImpl() {
        return this.aType.copyImpl();
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
     * @param modifier 
     */
    @Override
    public void removeModifierImpl(String modifier) {
        this.aType.removeModifierImpl(modifier);
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
     * @param newInterface 
     */
    @Override
    public void addInterfaceImpl(AInterface newInterface) {
        this.aType.addInterfaceImpl(newInterface);
    }

    /**
     * 
     * @param interfaceName 
     */
    @Override
    public AInterface removeInterfaceImpl(String interfaceName) {
        return this.aType.removeInterfaceImpl(interfaceName);
    }

    /**
     * add a new method inside the class
     * @param modifiers 
     * @param returnType 
     * @param name 
     * @param param 
     * @param code 
     */
    @Override
    public AMethod newMethodImpl(String[] modifiers, String returnType, String name, Pair[] param, String code) {
        return this.aType.newMethodImpl(modifiers, returnType, name, param, code);
    }

    /**
     * overload which accepts 4 parameters (code is empty string)
     * @param modifiers 
     * @param returnType 
     * @param name 
     * @param param 
     */
    @Override
    public AMethod newMethodImpl(String[] modifiers, String returnType, String name, Pair[] param) {
        return this.aType.newMethodImpl(modifiers, returnType, name, param);
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
     * @param code 
     */
    @Override
    public void insertCodeImpl(String code) {
        this.aType.insertCodeImpl(code);
    }

    /**
     * 
     * @param _interface 
     */
    @Override
    public void addImplementImpl(AInterface _interface) {
        this.aType.addImplementImpl(_interface);
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
     */
    @Override
    public Optional<? extends AType> getSuper() {
        return Optional.of(this.aType);
    }

    /**
     * 
     */
    @Override
    public List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
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
        		joinPointList = this.aType.select(selectName);
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
        this.aType.fillWithAttributes(attributes);
        attributes.add("isTopLevel");
    }

    /**
     * 
     */
    @Override
    protected void fillWithSelects(List<String> selects) {
        this.aType.fillWithSelects(selects);
        selects.add("constructor");
        selects.add("anonymousExec");
    }

    /**
     * 
     */
    @Override
    protected void fillWithActions(List<String> actions) {
        this.aType.fillWithActions(actions);
        actions.add("class mapVersions(String, String, interface, String)");
        actions.add("constructor newConstructor(String[], Pair[])");
        actions.add("method newFunctionalClass(method, method)");
        actions.add("void insertStatic(template)");
        actions.add("interface extractInterface(String, String, method, boolean, boolean)");
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public String get_class() {
        return "class";
    }

    /**
     * Defines if this joinpoint is an instanceof a given joinpoint class
     * @return True if this join point is an instanceof the given class
     */
    @Override
    public boolean instanceOf(String joinpointClass) {
        boolean isInstance = get_class().equals(joinpointClass);
        if(isInstance) {
        	return true;
        }
        return this.aType.instanceOf(joinpointClass);
    }
    /**
     * 
     */
    protected enum ClassAttributes {
        ISTOPLEVEL("isTopLevel"),
        NAME("name"),
        QUALIFIEDNAME("qualifiedName"),
        SUPERCLASS("superClass"),
        SUPERCLASSJP("superClassJp"),
        PACKAGE("package"),
        INTERFACES("interfaces"),
        INTERFACESTYPES("interfacesTypes"),
        JAVADOC("javadoc"),
        ISSUBTYPEOF("isSubtypeOf"),
        PARENT("parent"),
        ISSTATIC("isStatic"),
        CODE("code"),
        AST("ast"),
        ISBLOCK("isBlock"),
        ISINSIDELOOPHEADER("isInsideLoopHeader"),
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
        private ClassAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<ClassAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(ClassAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
