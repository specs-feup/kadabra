package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.weaver.interf.events.Stage;
import java.util.Optional;
import org.lara.interpreter.exception.AttributeException;
import weaver.kadabra.enums.RefType;
import java.util.List;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Auto-Generated class for join point AVar
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class AVar extends AExpression {

    protected AExpression aExpression;

    /**
     * 
     */
    public AVar(AExpression aExpression){
        this.aExpression = aExpression;
    }
    /**
     * Get value on attribute name
     * @return the attribute's value
     */
    public abstract String getNameImpl();

    /**
     * Get value on attribute name
     * @return the attribute's value
     */
    public final Object getName() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "name", Optional.empty());
        	}
        	String result = this.getNameImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "name", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "name", e);
        }
    }

    /**
     * Get value on attribute reference
     * @return the attribute's value
     */
    public abstract RefType getReferenceImpl();

    /**
     * Get value on attribute reference
     * @return the attribute's value
     */
    public final Object getReference() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "reference", Optional.empty());
        	}
        	RefType result = this.getReferenceImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "reference", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "reference", e);
        }
    }

    /**
     * Get value on attribute isArray
     * @return the attribute's value
     */
    public abstract Boolean getIsArrayImpl();

    /**
     * Get value on attribute isArray
     * @return the attribute's value
     */
    public final Object getIsArray() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "isArray", Optional.empty());
        	}
        	Boolean result = this.getIsArrayImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "isArray", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isArray", e);
        }
    }

    /**
     * Get value on attribute isPrimitive
     * @return the attribute's value
     */
    public abstract Boolean getIsPrimitiveImpl();

    /**
     * Get value on attribute isPrimitive
     * @return the attribute's value
     */
    public final Object getIsPrimitive() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "isPrimitive", Optional.empty());
        	}
        	Boolean result = this.getIsPrimitiveImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "isPrimitive", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isPrimitive", e);
        }
    }

    /**
     * Get value on attribute isField
     * @return the attribute's value
     */
    public abstract Boolean getIsFieldImpl();

    /**
     * Get value on attribute isField
     * @return the attribute's value
     */
    public final Object getIsField() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "isField", Optional.empty());
        	}
        	Boolean result = this.getIsFieldImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "isField", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isField", e);
        }
    }

    /**
     * Equivalent to the global attribute 'isInsideLoopHeader'
     */
    public abstract Boolean getInLoopHeaderImpl();

    /**
     * Equivalent to the global attribute 'isInsideLoopHeader'
     */
    public final Object getInLoopHeader() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "inLoopHeader", Optional.empty());
        	}
        	Boolean result = this.getInLoopHeaderImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "inLoopHeader", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "inLoopHeader", e);
        }
    }

    /**
     * Get value on attribute referenceChain
     * @return the attribute's value
     */
    public abstract AJoinPoint[] getReferenceChainArrayImpl();

    /**
     * the chain of references of this variable (e.g., this.field)
     */
    public Object getReferenceChainImpl() {
        AJoinPoint[] aJoinPointArrayImpl0 = getReferenceChainArrayImpl();
        Object nativeArray0 = getWeaverEngine().getScriptEngine().toNativeArray(aJoinPointArrayImpl0);
        return nativeArray0;
    }

    /**
     * the chain of references of this variable (e.g., this.field)
     */
    public final Object getReferenceChain() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "referenceChain", Optional.empty());
        	}
        	Object result = this.getReferenceChainImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "referenceChain", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "referenceChain", e);
        }
    }

    /**
     * Get value on attribute declaration
     * @return the attribute's value
     */
    public abstract AJoinPoint getDeclarationImpl();

    /**
     * Get value on attribute declaration
     * @return the attribute's value
     */
    public final Object getDeclaration() {
        try {
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.BEGIN, this, "declaration", Optional.empty());
        	}
        	AJoinPoint result = this.getDeclarationImpl();
        	if(hasListeners()) {
        		eventTrigger().triggerAttribute(Stage.END, this, "declaration", Optional.ofNullable(result));
        	}
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "declaration", e);
        }
    }

    /**
     * Get value on attribute kind
     * @return the attribute's value
     */
    @Override
    public String getKindImpl() {
        return this.aExpression.getKindImpl();
    }

    /**
     * Get value on attribute type
     * @return the attribute's value
     */
    @Override
    public String getTypeImpl() {
        return this.aExpression.getTypeImpl();
    }

    /**
     * Get value on attribute qualifiedType
     * @return the attribute's value
     */
    @Override
    public String getQualifiedTypeImpl() {
        return this.aExpression.getQualifiedTypeImpl();
    }

    /**
     * Get value on attribute typeReference
     * @return the attribute's value
     */
    @Override
    public ATypeReference getTypeReferenceImpl() {
        return this.aExpression.getTypeReferenceImpl();
    }

    /**
     * Get value on attribute test
     * @return the attribute's value
     */
    @Override
    public Integer getTestImpl() {
        return this.aExpression.getTestImpl();
    }

    /**
     * Method used by the lara interpreter to select exprs
     * @return 
     */
    @Override
    public List<? extends AExpression> selectExpr() {
        return this.aExpression.selectExpr();
    }

    /**
     * Method used by the lara interpreter to select vars
     * @return 
     */
    @Override
    public List<? extends AVar> selectVar() {
        return this.aExpression.selectVar();
    }

    /**
     * Method used by the lara interpreter to select arrayAccesss
     * @return 
     */
    @Override
    public List<? extends AArrayAccess> selectArrayAccess() {
        return this.aExpression.selectArrayAccess();
    }

    /**
     * Method used by the lara interpreter to select binaryExpressions
     * @return 
     */
    @Override
    public List<? extends ABinaryExpression> selectBinaryExpression() {
        return this.aExpression.selectBinaryExpression();
    }

    /**
     * Method used by the lara interpreter to select binaryExprs
     * @return 
     */
    @Override
    public List<? extends ABinaryExpression> selectBinaryExpr() {
        return this.aExpression.selectBinaryExpr();
    }

    /**
     * 
     */
    public void defTestImpl(Integer value) {
        this.aExpression.defTestImpl(value);
    }

    /**
     * 
     */
    public void defTestImpl(AExpression value) {
        this.aExpression.defTestImpl(value);
    }

    /**
     * Get value on attribute parent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getParentImpl() {
        return this.aExpression.getParentImpl();
    }

    /**
     * Get value on attribute isStatic
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStaticImpl() {
        return this.aExpression.getIsStaticImpl();
    }

    /**
     * Get value on attribute code
     * @return the attribute's value
     */
    @Override
    public String getCodeImpl() {
        return this.aExpression.getCodeImpl();
    }

    /**
     * Get value on attribute ast
     * @return the attribute's value
     */
    @Override
    public String getAstImpl() {
        return this.aExpression.getAstImpl();
    }

    /**
     * Get value on attribute isBlock
     * @return the attribute's value
     */
    @Override
    public Boolean getIsBlockImpl() {
        return this.aExpression.getIsBlockImpl();
    }

    /**
     * Get value on attribute isInsideLoopHeader
     * @return the attribute's value
     */
    @Override
    public Boolean getIsInsideLoopHeaderImpl() {
        return this.aExpression.getIsInsideLoopHeaderImpl();
    }

    /**
     * Get value on attribute line
     * @return the attribute's value
     */
    @Override
    public Integer getLineImpl() {
        return this.aExpression.getLineImpl();
    }

    /**
     * Get value on attribute ancestor
     * @return the attribute's value
     */
    @Override
    public AJoinPoint ancestorImpl(String type) {
        return this.aExpression.ancestorImpl(type);
    }

    /**
     * Get value on attribute annotationsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AAnnotation[] getAnnotationsArrayImpl() {
        return this.aExpression.getAnnotationsArrayImpl();
    }

    /**
     * Get value on attribute rightArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getRightArrayImpl() {
        return this.aExpression.getRightArrayImpl();
    }

    /**
     * Get value on attribute modifiersArrayImpl
     * @return the attribute's value
     */
    @Override
    public String[] getModifiersArrayImpl() {
        return this.aExpression.getModifiersArrayImpl();
    }

    /**
     * Get value on attribute descendantsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getDescendantsArrayImpl() {
        return this.aExpression.getDescendantsArrayImpl();
    }

    /**
     * Get value on attribute isStatement
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStatementImpl() {
        return this.aExpression.getIsStatementImpl();
    }

    /**
     * Get value on attribute astParent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAstParentImpl() {
        return this.aExpression.getAstParentImpl();
    }

    /**
     * Get value on attribute childrenArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        return this.aExpression.getChildrenArrayImpl();
    }

    /**
     * Get value on attribute leftArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getLeftArrayImpl() {
        return this.aExpression.getLeftArrayImpl();
    }

    /**
     * Get value on attribute hasModifier
     * @return the attribute's value
     */
    @Override
    public Boolean hasModifierImpl(String modifier) {
        return this.aExpression.hasModifierImpl(modifier);
    }

    /**
     * Get value on attribute numChildren
     * @return the attribute's value
     */
    @Override
    public Integer getNumChildrenImpl() {
        return this.aExpression.getNumChildrenImpl();
    }

    /**
     * Get value on attribute srcCode
     * @return the attribute's value
     */
    @Override
    public String getSrcCodeImpl() {
        return this.aExpression.getSrcCodeImpl();
    }

    /**
     * Get value on attribute isFinal
     * @return the attribute's value
     */
    @Override
    public Boolean getIsFinalImpl() {
        return this.aExpression.getIsFinalImpl();
    }

    /**
     * Get value on attribute id
     * @return the attribute's value
     */
    @Override
    public String getIdImpl() {
        return this.aExpression.getIdImpl();
    }

    /**
     * Get value on attribute child
     * @return the attribute's value
     */
    @Override
    public AJoinPoint childImpl(Integer index) {
        return this.aExpression.childImpl(index);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertBeforeImpl(AJoinPoint node) {
        return this.aExpression.insertBeforeImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return this.aExpression.insertBeforeImpl(code);
    }

    /**
     * 
     * @param node 
     */
    @Override
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        return this.aExpression.insertAfterImpl(node);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return this.aExpression.insertAfterImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return this.aExpression.insertReplaceImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return this.aExpression.insertReplaceImpl(code);
    }

    /**
     * 
     * @param jp 
     */
    @Override
    public AJoinPoint replaceWithImpl(AJoinPoint jp) {
        return this.aExpression.replaceWithImpl(jp);
    }

    /**
     * 
     * @param code 
     */
    @Override
    public AJoinPoint replaceWithImpl(String code) {
        return this.aExpression.replaceWithImpl(code);
    }

    /**
     * 
     */
    @Override
    public AJoinPoint copyImpl() {
        return this.aExpression.copyImpl();
    }

    /**
     * 
     */
    @Override
    public void removeImpl() {
        this.aExpression.removeImpl();
    }

    /**
     * 
     * @param annotation 
     */
    @Override
    public void removeAnnotationImpl(AAnnotation annotation) {
        this.aExpression.removeAnnotationImpl(annotation);
    }

    /**
     * 
     * @param modifier 
     */
    @Override
    public void removeModifierImpl(String modifier) {
        this.aExpression.removeModifierImpl(modifier);
    }

    /**
     * 
     * @param varName 
     * @param location 
     * @param position 
     */
    @Override
    public void extractImpl(String varName, AStatement location, String position) {
        this.aExpression.extractImpl(varName, location, position);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return this.aExpression.insertImpl(position, code);
    }

    /**
     * 
     * @param position 
     * @param code 
     */
    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return this.aExpression.insertImpl(position, code);
    }

    /**
     * 
     */
    @Override
    public Optional<? extends AExpression> getSuper() {
        return Optional.of(this.aExpression);
    }

    /**
     * 
     */
    @Override
    public List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
        	case "expr": 
        		joinPointList = selectExpr();
        		break;
        	case "var": 
        		joinPointList = selectVar();
        		break;
        	case "arrayAccess": 
        		joinPointList = selectArrayAccess();
        		break;
        	case "binaryExpression": 
        		joinPointList = selectBinaryExpression();
        		break;
        	case "binaryExpr": 
        		joinPointList = selectBinaryExpr();
        		break;
        	default:
        		joinPointList = this.aExpression.select(selectName);
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
        case "test": {
        	if(value instanceof Integer){
        		this.defTestImpl((Integer)value);
        		return;
        	}
        	if(value instanceof AExpression){
        		this.defTestImpl((AExpression)value);
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
        this.aExpression.fillWithAttributes(attributes);
        attributes.add("name");
        attributes.add("reference");
        attributes.add("isArray");
        attributes.add("isPrimitive");
        attributes.add("isField");
        attributes.add("inLoopHeader");
        attributes.add("referenceChain");
        attributes.add("declaration");
    }

    /**
     * 
     */
    @Override
    protected void fillWithSelects(List<String> selects) {
        this.aExpression.fillWithSelects(selects);
    }

    /**
     * 
     */
    @Override
    protected void fillWithActions(List<String> actions) {
        this.aExpression.fillWithActions(actions);
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public String get_class() {
        return "var";
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
        return this.aExpression.instanceOf(joinpointClass);
    }
    /**
     * 
     */
    protected enum VarAttributes {
        NAME("name"),
        REFERENCE("reference"),
        ISARRAY("isArray"),
        ISPRIMITIVE("isPrimitive"),
        ISFIELD("isField"),
        INLOOPHEADER("inLoopHeader"),
        REFERENCECHAIN("referenceChain"),
        DECLARATION("declaration"),
        KIND("kind"),
        TYPE("type"),
        QUALIFIEDTYPE("qualifiedType"),
        TYPEREFERENCE("typeReference"),
        TEST("test"),
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
        private VarAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<VarAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(VarAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
