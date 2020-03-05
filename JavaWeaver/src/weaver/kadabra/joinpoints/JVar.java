/**
 * Copyright 2015 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

package weaver.kadabra.joinpoints;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import spoon.reflect.code.CtArrayAccess;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.ModifierKind;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.ATypeReference;
import weaver.kadabra.abstracts.joinpoints.AVar;
import weaver.kadabra.enums.RefType;
import weaver.utils.SpoonUtils;
import weaver.utils.element.CtTypeReferenceUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JVar<T> extends AVar {

    private final CtVariableAccess<T> node;

    protected JVar(CtVariableAccess<T> var) {
        super(new JExpression<>(var));
        node = var;
    }

    public static <T> JVar<T> newInstance(CtVariableAccess<T> var) {

        if (var instanceof CtFieldAccess) {
            return JFieldAccess.newInstance((CtFieldAccess<T>) var);
        }

        return new JVar<>(var);
    }

    @Override
    public RefType getReferenceImpl() {
        // TODO - possibly move this to a visit approach
        final CtElement parent = node.getParent();
        if (parent instanceof CtAssignment<?, ?>) {
            CtAssignment<?, ?> par = (CtAssignment<?, ?>) parent;
            if (par.getAssigned().equals(node)) {
                return RefType.WRITE;
            }
            return RefType.READ;
        }
        if (parent instanceof CtArrayAccess<?, ?>) {
            if (parent.getParent() instanceof CtAssignment<?, ?>) {
                final CtArrayAccess<?, ?> arrAccParent = (CtArrayAccess<?, ?>) parent;
                final CtExpression<?> target = arrAccParent.getTarget();
                if (target == node) {
                    return RefType.WRITE;
                }
            }
        }
        if (parent instanceof CtUnaryOperator<?>) {
            final CtUnaryOperator<?> unOp = (CtUnaryOperator<?>) parent;
            switch (unOp.getKind()) {
            case POSTINC:
            case POSTDEC:
            case PREINC:
            case PREDEC:
                return RefType.WRITE;
            default:
                break;
            }

        }
        return RefType.READ;
    }

    @Override
    public ATypeReference getTypeReferenceImpl() {
        return new JTypeReference<>(node.getType());
        // return CtTypeReferenceUtils.getType(node.getType());
    }

    @Override
    public String getTypeImpl() {
        return getTypeReferenceImpl().toString();
    }

    @Override
    public Boolean getIsArrayImpl() {
        return CtTypeReferenceUtils.getIsArray(node.getType());
    }

    @Override
    public Boolean getIsPrimitiveImpl() {
        return CtTypeReferenceUtils.getIsPrimitive(node.getType());
    }

    @Override
    public String toString() {
        // final String isArray = getIsArray() ? "[]" : "";
        // return node.getVariable().toString() + " (" + getType() + isArray + ", " + getReference() + ")";
        return node.toString();
    }

    @Override
    public Boolean getIsFieldImpl() {
        return node instanceof CtFieldAccess<?>;
    }

    @Override
    public Boolean getInLoopHeaderImpl() {

        Optional<CtLoop> ancestor = SpoonUtils.getAncestorTry(node, CtLoop.class);
        if (!ancestor.isPresent()) {
            return false;
        }
        CtLoop loop = ancestor.get();

        return SpoonUtils.insideHeader(loop, node);
    }

    // @Override
    // public void insertImpl(String position, String code) {
    // // final CtStatement parentStatement = node.getParent(CtStatement.class);
    // // final CtElement parent = node.getParent();
    //
    // // System.out.println("PARENT: " + parent.getClass() + "");
    // // System.out.println(parent);
    // ActionUtils.insert(position, code, node, getWeaverProfiler());
    // }

    @Override
    public CtVariableAccess<T> getNode() {
        return node;
    }

    @Override
    public String getNameImpl() {
        return node.getVariable().getSimpleName();
    }

    @Override
    public AJoinPoint[] getReferenceChainArrayImpl() {
        return getChildrenArrayImpl();
    }

    @Override
    public AJavaWeaverJoinPoint getDeclarationImpl() {
        var decl = node.getVariable().getDeclaration();
        if (decl == null) {
            return null;
        }

        return CtElement2JoinPoint.convert(decl);
    }

    @Override
    public Set<ModifierKind> getModifiersInternal() {
        var decl = getDeclarationImpl();
        if (decl == null) {
            return Collections.emptySet();
        }

        return decl.getModifiersInternal();
    }

    // @Override
    // public String[] getModifiersArrayImpl() {
    // return node.getVariable().getModifiers().stream()
    // .map(ModifierKind::name)
    // .toArray(length -> new String[length]);
    // }

    // @Override
    // public Boolean getIsFinalImpl() {
    // return node.getVariable().getModifiers().contains(ModifierKind.FINAL);
    // }
    //
    // @Override
    // public Boolean getIsStaticImpl() {
    // return node.getVariable().getModifiers().contains(ModifierKind.STATIC);
    // }

}
