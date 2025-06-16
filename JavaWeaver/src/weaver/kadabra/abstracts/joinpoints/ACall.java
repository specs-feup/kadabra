package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.exception.AttributeException;
import org.lara.interpreter.exception.ActionException;
import org.lara.interpreter.weaver.interf.JoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

/**
 * Auto-Generated class for join point ACall
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class ACall extends AExpression {

    protected AExpression aExpression;

    /**
     * 
     */
    public ACall(AExpression aExpression){
        this.aExpression = aExpression;
    }
    /**
     * Get value on attribute arguments
     * @return the attribute's value
     */
    public abstract AExpression[] getArgumentsArrayImpl();

    /**
     * Get value on attribute arguments
     * @return the attribute's value
     */
    public Object getArgumentsImpl() {
        AExpression[] aExpressionArrayImpl0 = getArgumentsArrayImpl();
        Object nativeArray0 = aExpressionArrayImpl0;
        return nativeArray0;
    }

    /**
     * Get value on attribute arguments
     * @return the attribute's value
     */
    public final Object getArguments() {
        try {
        	Object result = this.getArgumentsImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "arguments", e);
        }
    }

    /**
     * Get value on attribute decl
     * @return the attribute's value
     */
    public abstract AMethod getDeclImpl();

    /**
     * Get value on attribute decl
     * @return the attribute's value
     */
    public final Object getDecl() {
        try {
        	AMethod result = this.getDeclImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "decl", e);
        }
    }

    /**
     * Get value on attribute declarator
     * @return the attribute's value
     */
    public abstract String getDeclaratorImpl();

    /**
     * Get value on attribute declarator
     * @return the attribute's value
     */
    public final Object getDeclarator() {
        try {
        	String result = this.getDeclaratorImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "declarator", e);
        }
    }

    /**
     * Get value on attribute executable
     * @return the attribute's value
     */
    public abstract String getExecutableImpl();

    /**
     * Get value on attribute executable
     * @return the attribute's value
     */
    public final Object getExecutable() {
        try {
        	String result = this.getExecutableImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "executable", e);
        }
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
        	String result = this.getNameImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "name", e);
        }
    }

    /**
     * Get value on attribute qualifiedDecl
     * @return the attribute's value
     */
    public abstract String getQualifiedDeclImpl();

    /**
     * Get value on attribute qualifiedDecl
     * @return the attribute's value
     */
    public final Object getQualifiedDecl() {
        try {
        	String result = this.getQualifiedDeclImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "qualifiedDecl", e);
        }
    }

    /**
     * Get value on attribute returnType
     * @return the attribute's value
     */
    public abstract String getReturnTypeImpl();

    /**
     * Get value on attribute returnType
     * @return the attribute's value
     */
    public final Object getReturnType() {
        try {
        	String result = this.getReturnTypeImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "returnType", e);
        }
    }

    /**
     * Get value on attribute returnTypeJp
     * @return the attribute's value
     */
    public abstract ATypeReference getReturnTypeJpImpl();

    /**
     * Get value on attribute returnTypeJp
     * @return the attribute's value
     */
    public final Object getReturnTypeJp() {
        try {
        	ATypeReference result = this.getReturnTypeJpImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "returnTypeJp", e);
        }
    }

    /**
     * Get value on attribute simpleDecl
     * @return the attribute's value
     */
    public abstract String getSimpleDeclImpl();

    /**
     * Get value on attribute simpleDecl
     * @return the attribute's value
     */
    public final Object getSimpleDecl() {
        try {
        	String result = this.getSimpleDeclImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "simpleDecl", e);
        }
    }

    /**
     * Get value on attribute target
     * @return the attribute's value
     */
    public abstract String getTargetImpl();

    /**
     * Get value on attribute target
     * @return the attribute's value
     */
    public final Object getTarget() {
        try {
        	String result = this.getTargetImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "target", e);
        }
    }

    /**
     * Get value on attribute targetType
     * @return the attribute's value
     */
    public abstract AType getTargetTypeImpl();

    /**
     * Get value on attribute targetType
     * @return the attribute's value
     */
    public final Object getTargetType() {
        try {
        	AType result = this.getTargetTypeImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "targetType", e);
        }
    }

    /**
     * 
     * @param location 
     * @param position 
     */
    public ACall cloneImpl(AStatement location, String position) {
        throw new UnsupportedOperationException(get_class()+": Action clone not implemented ");
    }

    /**
     * 
     * @param location 
     * @param position 
     */
    public final Object clone(AStatement location, String position) {
        try {
        	ACall result = this.cloneImpl(location, position);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "clone", e);
        }
    }

    /**
     * 
     * @param newArgument 
     * @param index 
     */
    public void setArgumentImpl(AExpression newArgument, Integer index) {
        throw new UnsupportedOperationException(get_class()+": Action setArgument not implemented ");
    }

    /**
     * 
     * @param newArgument 
     * @param index 
     */
    public final void setArgument(AExpression newArgument, Integer index) {
        try {
        	this.setArgumentImpl(newArgument, index);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setArgument", e);
        }
    }

    /**
     * 
     * @param newArguments 
     */
    public void setArgumentsImpl(AExpression[] newArguments) {
        throw new UnsupportedOperationException(get_class()+": Action setArguments not implemented ");
    }

    /**
     * 
     * @param newArguments 
     */
    public final void setArguments(Object[] newArguments) {
        try {
        	this.setArgumentsImpl(pt.up.fe.specs.util.SpecsCollections.cast(newArguments, AExpression.class));
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setArguments", e);
        }
    }

    /**
     * 
     * @param executable 
     */
    public ACall setExecutableImpl(AMethod executable) {
        throw new UnsupportedOperationException(get_class()+": Action setExecutable not implemented ");
    }

    /**
     * 
     * @param executable 
     */
    public final Object setExecutable(AMethod executable) {
        try {
        	ACall result = this.setExecutableImpl(executable);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setExecutable", e);
        }
    }

    /**
     * 
     * @param target 
     */
    public ACall setTargetImpl(AExpression target) {
        throw new UnsupportedOperationException(get_class()+": Action setTarget not implemented ");
    }

    /**
     * 
     * @param target 
     */
    public final Object setTarget(AExpression target) {
        try {
        	ACall result = this.setTargetImpl(target);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setTarget", e);
        }
    }

    /**
     * 
     * @param target 
     */
    public ACall setTargetImpl(String target) {
        throw new UnsupportedOperationException(get_class()+": Action setTarget not implemented ");
    }

    /**
     * 
     * @param target 
     */
    public final Object setTarget(String target) {
        try {
        	ACall result = this.setTargetImpl(target);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setTarget", e);
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
     * Get value on attribute qualifiedType
     * @return the attribute's value
     */
    @Override
    public String getQualifiedTypeImpl() {
        return this.aExpression.getQualifiedTypeImpl();
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
     * Get value on attribute type
     * @return the attribute's value
     */
    @Override
    public String getTypeImpl() {
        return this.aExpression.getTypeImpl();
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
     * Get value on attribute annotationsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AAnnotation[] getAnnotationsArrayImpl() {
        return this.aExpression.getAnnotationsArrayImpl();
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
     * Get value on attribute astParent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAstParentImpl() {
        return this.aExpression.getAstParentImpl();
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
     * Get value on attribute childrenArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        return this.aExpression.getChildrenArrayImpl();
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
     * Get value on attribute descendantsArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getDescendantsArrayImpl() {
        return this.aExpression.getDescendantsArrayImpl();
    }

    /**
     * Get value on attribute getAncestor
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getAncestorImpl(String type) {
        return this.aExpression.getAncestorImpl(type);
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
     * Get value on attribute id
     * @return the attribute's value
     */
    @Override
    public String getIdImpl() {
        return this.aExpression.getIdImpl();
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
     * Get value on attribute isFinal
     * @return the attribute's value
     */
    @Override
    public Boolean getIsFinalImpl() {
        return this.aExpression.getIsFinalImpl();
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
     * Get value on attribute isStatement
     * @return the attribute's value
     */
    @Override
    public Boolean getIsStatementImpl() {
        return this.aExpression.getIsStatementImpl();
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
     * Get value on attribute leftArrayImpl
     * @return the attribute's value
     */
    @Override
    public AJoinPoint[] getLeftArrayImpl() {
        return this.aExpression.getLeftArrayImpl();
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
     * Get value on attribute modifiersArrayImpl
     * @return the attribute's value
     */
    @Override
    public String[] getModifiersArrayImpl() {
        return this.aExpression.getModifiersArrayImpl();
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
     * Get value on attribute parent
     * @return the attribute's value
     */
    @Override
    public AJoinPoint getParentImpl() {
        return this.aExpression.getParentImpl();
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
     * Get value on attribute srcCode
     * @return the attribute's value
     */
    @Override
    public String getSrcCodeImpl() {
        return this.aExpression.getSrcCodeImpl();
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
     * @param value 
     */
    @Override
    public void setLineImpl(int value) {
        this.aExpression.setLineImpl(value);
    }

    /**
     * 
     * @param value 
     */
    @Override
    public void setLineImpl(String value) {
        this.aExpression.setLineImpl(value);
    }

    /**
     * 
     * @param modifiers 
     */
    @Override
    public void setModifiersImpl(String[] modifiers) {
        this.aExpression.setModifiersImpl(modifiers);
    }

    /**
     * 
     * @param test 
     */
    @Override
    public void setTestImpl(AExpression test) {
        this.aExpression.setTestImpl(test);
    }

    /**
     * 
     * @param test 
     */
    @Override
    public void setTestImpl(int test) {
        this.aExpression.setTestImpl(test);
    }

    /**
     * 
     */
    @Override
    public Optional<? extends AExpression> getSuper() {
        return Optional.of(this.aExpression);
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "call";
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
        return this.aExpression.instanceOf(joinpointClass);
    }
    /**
     * 
     */
    protected enum CallAttributes {
        ARGUMENTS("arguments"),
        DECL("decl"),
        DECLARATOR("declarator"),
        EXECUTABLE("executable"),
        NAME("name"),
        QUALIFIEDDECL("qualifiedDecl"),
        RETURNTYPE("returnType"),
        RETURNTYPEJP("returnTypeJp"),
        SIMPLEDECL("simpleDecl"),
        TARGET("target"),
        TARGETTYPE("targetType"),
        KIND("kind"),
        QUALIFIEDTYPE("qualifiedType"),
        TEST("test"),
        TYPE("type"),
        TYPEREFERENCE("typeReference"),
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
        private CallAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<CallAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(CallAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
