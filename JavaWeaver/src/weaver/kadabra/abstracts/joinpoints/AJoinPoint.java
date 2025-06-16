package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.weaver.interf.JoinPoint;
import spoon.reflect.declaration.CtElement;
import org.lara.interpreter.exception.ActionException;
import org.lara.interpreter.exception.AttributeException;
import weaver.kadabra.JavaWeaver;

/**
 * Abstract class containing the global attributes and default action exception.
 * This class is overwritten when the weaver generator is executed.
 * @author Lara Weaver Generator
 */
public abstract class AJoinPoint extends JoinPoint {

    /**
     * 
     */
    @Override
    public boolean same(JoinPoint iJoinPoint) {
        if (this.get_class().equals(iJoinPoint.get_class())) {
        
                return this.compareNodes((AJoinPoint) iJoinPoint);
            }
            return false;
    }

    /**
     * Compares the two join points based on their node reference of the used compiler/parsing tool.<br>
     * This is the default implementation for comparing two join points. <br>
     * <b>Note for developers:</b> A weaver may override this implementation in the editable abstract join point, so
     * the changes are made for all join points, or override this method in specific join points.
     */
    public boolean compareNodes(AJoinPoint aJoinPoint) {
        return this.getNode().equals(aJoinPoint.getNode());
    }

    /**
     * Returns the tree node reference of this join point.<br><b>NOTE</b>This method is essentially used to compare two join points
     * @return Tree node reference
     */
    public abstract CtElement getNode();

    /**
     * 
     */
    public AJoinPoint copyImpl() {
        throw new UnsupportedOperationException(get_class()+": Action copy not implemented ");
    }

    /**
     * 
     */
    public final Object copy() {
        try {
        	AJoinPoint result = this.copyImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "copy", e);
        }
    }

    /**
     * 
     * @param node 
     */
    public AJoinPoint insertAfterImpl(AJoinPoint node) {
        throw new UnsupportedOperationException(get_class()+": Action insertAfter not implemented ");
    }

    /**
     * 
     * @param node 
     */
    public final Object insertAfter(AJoinPoint node) {
        try {
        	AJoinPoint result = this.insertAfterImpl(node);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "insertAfter", e);
        }
    }

    /**
     * 
     * @param code 
     */
    public AJoinPoint insertAfterImpl(String code) {
        throw new UnsupportedOperationException(get_class()+": Action insertAfter not implemented ");
    }

    /**
     * 
     * @param code 
     */
    public final Object insertAfter(String code) {
        try {
        	AJoinPoint result = this.insertAfterImpl(code);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "insertAfter", e);
        }
    }

    /**
     * 
     * @param node 
     */
    public AJoinPoint insertBeforeImpl(AJoinPoint node) {
        throw new UnsupportedOperationException(get_class()+": Action insertBefore not implemented ");
    }

    /**
     * 
     * @param node 
     */
    public final Object insertBefore(AJoinPoint node) {
        try {
        	AJoinPoint result = this.insertBeforeImpl(node);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "insertBefore", e);
        }
    }

    /**
     * 
     * @param code 
     */
    public AJoinPoint insertBeforeImpl(String code) {
        throw new UnsupportedOperationException(get_class()+": Action insertBefore not implemented ");
    }

    /**
     * 
     * @param code 
     */
    public final Object insertBefore(String code) {
        try {
        	AJoinPoint result = this.insertBeforeImpl(code);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "insertBefore", e);
        }
    }

    /**
     * 
     * @param jp 
     */
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        throw new UnsupportedOperationException(get_class()+": Action insertReplace not implemented ");
    }

    /**
     * 
     * @param jp 
     */
    public final Object insertReplace(AJoinPoint jp) {
        try {
        	AJoinPoint result = this.insertReplaceImpl(jp);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "insertReplace", e);
        }
    }

    /**
     * 
     * @param code 
     */
    public AJoinPoint insertReplaceImpl(String code) {
        throw new UnsupportedOperationException(get_class()+": Action insertReplace not implemented ");
    }

    /**
     * 
     * @param code 
     */
    public final Object insertReplace(String code) {
        try {
        	AJoinPoint result = this.insertReplaceImpl(code);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "insertReplace", e);
        }
    }

    /**
     * 
     */
    public void removeImpl() {
        throw new UnsupportedOperationException(get_class()+": Action remove not implemented ");
    }

    /**
     * 
     */
    public final void remove() {
        try {
        	this.removeImpl();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "remove", e);
        }
    }

    /**
     * 
     * @param annotation 
     */
    public void removeAnnotationImpl(AAnnotation annotation) {
        throw new UnsupportedOperationException(get_class()+": Action removeAnnotation not implemented ");
    }

    /**
     * 
     * @param annotation 
     */
    public final void removeAnnotation(AAnnotation annotation) {
        try {
        	this.removeAnnotationImpl(annotation);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "removeAnnotation", e);
        }
    }

    /**
     * 
     * @param modifier 
     */
    public void removeModifierImpl(String modifier) {
        throw new UnsupportedOperationException(get_class()+": Action removeModifier not implemented ");
    }

    /**
     * 
     * @param modifier 
     */
    public final void removeModifier(String modifier) {
        try {
        	this.removeModifierImpl(modifier);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "removeModifier", e);
        }
    }

    /**
     * 
     * @param jp 
     */
    public AJoinPoint replaceWithImpl(AJoinPoint jp) {
        throw new UnsupportedOperationException(get_class()+": Action replaceWith not implemented ");
    }

    /**
     * 
     * @param jp 
     */
    public final Object replaceWith(AJoinPoint jp) {
        try {
        	AJoinPoint result = this.replaceWithImpl(jp);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "replaceWith", e);
        }
    }

    /**
     * 
     * @param code 
     */
    public AJoinPoint replaceWithImpl(String code) {
        throw new UnsupportedOperationException(get_class()+": Action replaceWith not implemented ");
    }

    /**
     * 
     * @param code 
     */
    public final Object replaceWith(String code) {
        try {
        	AJoinPoint result = this.replaceWithImpl(code);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "replaceWith", e);
        }
    }

    /**
     * 
     * @param modifiers 
     */
    public void setModifiersImpl(String[] modifiers) {
        throw new UnsupportedOperationException(get_class()+": Action setModifiers not implemented ");
    }

    /**
     * 
     * @param modifiers 
     */
    public final void setModifiers(Object[] modifiers) {
        try {
        	this.setModifiersImpl(pt.up.fe.specs.util.SpecsCollections.cast(modifiers, String.class));
        } catch(Exception e) {
        	throw new ActionException(get_class(), "setModifiers", e);
        }
    }

    /**
     * Get value on attribute annotations
     * @return the attribute's value
     */
    public abstract AAnnotation[] getAnnotationsArrayImpl();

    /**
     * an array of the annotations of this node
     */
    public Object getAnnotationsImpl() {
        AAnnotation[] aAnnotationArrayImpl0 = getAnnotationsArrayImpl();
        Object nativeArray0 = aAnnotationArrayImpl0;
        return nativeArray0;
    }

    /**
     * an array of the annotations of this node
     */
    public final Object getAnnotations() {
        try {
        	Object result = this.getAnnotationsImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "annotations", e);
        }
    }

    /**
     * A string representation of the AST corresponding to this node
     */
    public abstract String getAstImpl();

    /**
     * A string representation of the AST corresponding to this node
     */
    public final Object getAst() {
        try {
        	String result = this.getAstImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "ast", e);
        }
    }

    /**
     * Alias of attribute 'parent'
     */
    public abstract AJoinPoint getAstParentImpl();

    /**
     * Alias of attribute 'parent'
     */
    public final Object getAstParent() {
        try {
        	AJoinPoint result = this.getAstParentImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "astParent", e);
        }
    }

    /**
     * 
     * @param index
     * @return 
     */
    public abstract AJoinPoint childImpl(Integer index);

    /**
     * 
     * @param index
     * @return 
     */
    public final Object child(Integer index) {
        try {
        	AJoinPoint result = this.childImpl(index);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "child", e);
        }
    }

    /**
     * Get value on attribute children
     * @return the attribute's value
     */
    public abstract AJoinPoint[] getChildrenArrayImpl();

    /**
     * Returns an array with the children of the node
     */
    public Object getChildrenImpl() {
        AJoinPoint[] aJoinPointArrayImpl0 = getChildrenArrayImpl();
        Object nativeArray0 = aJoinPointArrayImpl0;
        return nativeArray0;
    }

    /**
     * Returns an array with the children of the node
     */
    public final Object getChildren() {
        try {
        	Object result = this.getChildrenImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "children", e);
        }
    }

    /**
     * The source code corresponding to this join point
     */
    public abstract String getCodeImpl();

    /**
     * The source code corresponding to this join point
     */
    public final Object getCode() {
        try {
        	String result = this.getCodeImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "code", e);
        }
    }

    /**
     * Get value on attribute descendants
     * @return the attribute's value
     */
    public abstract AJoinPoint[] getDescendantsArrayImpl();

    /**
     * Get value on attribute descendants
     * @return the attribute's value
     */
    public Object getDescendantsImpl() {
        AJoinPoint[] aJoinPointArrayImpl0 = getDescendantsArrayImpl();
        Object nativeArray0 = aJoinPointArrayImpl0;
        return nativeArray0;
    }

    /**
     * Get value on attribute descendants
     * @return the attribute's value
     */
    public final Object getDescendants() {
        try {
        	Object result = this.getDescendantsImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "descendants", e);
        }
    }

    /**
     * 
     * @param type
     * @return 
     */
    public abstract AJoinPoint getAncestorImpl(String type);

    /**
     * 
     * @param type
     * @return 
     */
    public final Object getAncestor(String type) {
        try {
        	AJoinPoint result = this.getAncestorImpl(type);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "getAncestor", e);
        }
    }

    /**
     * 
     * @param modifier
     * @return 
     */
    public abstract Boolean hasModifierImpl(String modifier);

    /**
     * 
     * @param modifier
     * @return 
     */
    public final Object hasModifier(String modifier) {
        try {
        	Boolean result = this.hasModifierImpl(modifier);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "hasModifier", e);
        }
    }

    /**
     * unique identifier for node
     */
    public abstract String getIdImpl();

    /**
     * unique identifier for node
     */
    public final Object getId() {
        try {
        	String result = this.getIdImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "id", e);
        }
    }

    /**
     * true if this node is considered a block of statements
     */
    public abstract Boolean getIsBlockImpl();

    /**
     * true if this node is considered a block of statements
     */
    public final Object getIsBlock() {
        try {
        	Boolean result = this.getIsBlockImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isBlock", e);
        }
    }

    /**
     * true if this node has the modifier 'final'
     */
    public abstract Boolean getIsFinalImpl();

    /**
     * true if this node has the modifier 'final'
     */
    public final Object getIsFinal() {
        try {
        	Boolean result = this.getIsFinalImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isFinal", e);
        }
    }

    /**
     * true if the node is inside a loop header, false otherwise
     */
    public abstract Boolean getIsInsideLoopHeaderImpl();

    /**
     * true if the node is inside a loop header, false otherwise
     */
    public final Object getIsInsideLoopHeader() {
        try {
        	Boolean result = this.getIsInsideLoopHeaderImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isInsideLoopHeader", e);
        }
    }

    /**
     * true if this node is considered a statement
     */
    public abstract Boolean getIsStatementImpl();

    /**
     * true if this node is considered a statement
     */
    public final Object getIsStatement() {
        try {
        	Boolean result = this.getIsStatementImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isStatement", e);
        }
    }

    /**
     * true if this node has the modifier 'static'
     */
    public abstract Boolean getIsStaticImpl();

    /**
     * true if this node has the modifier 'static'
     */
    public final Object getIsStatic() {
        try {
        	Boolean result = this.getIsStaticImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "isStatic", e);
        }
    }

    /**
     * Get value on attribute left
     * @return the attribute's value
     */
    public abstract AJoinPoint[] getLeftArrayImpl();

    /**
     * Sibling nodes to the left of this node
     */
    public Object getLeftImpl() {
        AJoinPoint[] aJoinPointArrayImpl0 = getLeftArrayImpl();
        Object nativeArray0 = aJoinPointArrayImpl0;
        return nativeArray0;
    }

    /**
     * Sibling nodes to the left of this node
     */
    public final Object getLeft() {
        try {
        	Object result = this.getLeftImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "left", e);
        }
    }

    /**
     * Get value on attribute line
     * @return the attribute's value
     */
    public abstract Integer getLineImpl();

    /**
     * Get value on attribute line
     * @return the attribute's value
     */
    public final Object getLine() {
        try {
        	Integer result = this.getLineImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "line", e);
        }
    }

    /**
     * Get value on attribute modifiers
     * @return the attribute's value
     */
    public abstract String[] getModifiersArrayImpl();

    /**
     * an array of modifiers (e.g., final, static) applied to this node. If no modifiers are applied, or if the node does not support modifiers, returns an empty array
     */
    public Object getModifiersImpl() {
        String[] stringArrayImpl0 = getModifiersArrayImpl();
        Object nativeArray0 = stringArrayImpl0;
        return nativeArray0;
    }

    /**
     * an array of modifiers (e.g., final, static) applied to this node. If no modifiers are applied, or if the node does not support modifiers, returns an empty array
     */
    public final Object getModifiers() {
        try {
        	Object result = this.getModifiersImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "modifiers", e);
        }
    }

    /**
     * Returns the number of children of the node
     */
    public abstract Integer getNumChildrenImpl();

    /**
     * Returns the number of children of the node
     */
    public final Object getNumChildren() {
        try {
        	Integer result = this.getNumChildrenImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "numChildren", e);
        }
    }

    /**
     * Get value on attribute parent
     * @return the attribute's value
     */
    public abstract AJoinPoint getParentImpl();

    /**
     * Get value on attribute parent
     * @return the attribute's value
     */
    public final Object getParent() {
        try {
        	AJoinPoint result = this.getParentImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "parent", e);
        }
    }

    /**
     * Get value on attribute right
     * @return the attribute's value
     */
    public abstract AJoinPoint[] getRightArrayImpl();

    /**
     * Sibling nodes to the right of this node
     */
    public Object getRightImpl() {
        AJoinPoint[] aJoinPointArrayImpl0 = getRightArrayImpl();
        Object nativeArray0 = aJoinPointArrayImpl0;
        return nativeArray0;
    }

    /**
     * Sibling nodes to the right of this node
     */
    public final Object getRight() {
        try {
        	Object result = this.getRightImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "right", e);
        }
    }

    /**
     * Alias for attribute 'code'
     */
    public abstract String getSrcCodeImpl();

    /**
     * Alias for attribute 'code'
     */
    public final Object getSrcCode() {
        try {
        	String result = this.getSrcCodeImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "srcCode", e);
        }
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
        return super.instanceOf(joinpointClass);
    }

    /**
     * Returns the Weaving Engine this join point pertains to.
     */
    @Override
    public JavaWeaver getWeaverEngine() {
        return JavaWeaver.getJavaWeaver();
    }
}
