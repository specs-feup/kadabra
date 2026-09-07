/**
 * Copyright 2015 SPeCS.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.joinpoints;

import weaver.kadabra.abstracts.joinpoints.AVar;
import java.util.Optional;

import spoon.reflect.code.CtArrayAccess;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtOperatorAssignment;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtVariableAccess;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.ATypeReference;
import weaver.kadabra.enums.RefType;
import weaver.utils.SpoonUtils;
import weaver.utils.element.CtTypeReferenceUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JVar<Self extends JVar<Self>> extends AVar<Self> {

    public JVar(CtVariableAccess node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public RefType getReferenceImpl() {
        // TODO - possibly move this to a visit approach
        final spoon.reflect.declaration.CtElement parent = getNodeImpl().getParent();
        if (parent instanceof CtOperatorAssignment<?, ?>) {
            return RefType.READWRITE;
        }
        if (parent instanceof CtAssignment<?, ?> par) {
            if (par.getAssigned().equals(getNodeImpl())) {
                return RefType.WRITE;
            }
            return RefType.READ;
        }
        if (parent instanceof CtArrayAccess<?, ?>) {
            if (parent.getParent() instanceof CtAssignment<?, ?>) {
                final CtArrayAccess<?, ?> arrAccParent = (CtArrayAccess<?, ?>) parent;
                final spoon.reflect.code.CtExpression<?> target = arrAccParent.getTarget();
                if (target == getNodeImpl()) {
                    return RefType.WRITE;
                }
            }
        }
        if (parent instanceof CtUnaryOperator<?> unOp) {
            switch (unOp.getKind()) {
                case POSTINC:
                case POSTDEC:
                case PREINC:
                case PREDEC:
                    return RefType.READWRITE;
                default:
                    break;
            }

        }
        return RefType.READ;
    }

    @Override
    public ATypeReference<?> getTypeReferenceImpl() {
        return new JTypeReference<>(getNodeImpl().getType(), getWeaverEngine());
    }

    @Override
    public String getTypeImpl() {
        return getTypeReferenceImpl().toString();
    }

    @Override
    public boolean getIsArrayImpl() {
        return CtTypeReferenceUtils.getIsArray(getNodeImpl().getType());
    }

    @Override
    public boolean getIsPrimitiveImpl() {
        return CtTypeReferenceUtils.getIsPrimitive(getNodeImpl().getType());
    }

    @Override
    public String getToStringImpl() {
        return getNodeImpl().toString();
    }

    @Override
    public boolean getIsFieldImpl() {
        return getNodeImpl() instanceof CtFieldAccess<?>;
    }

    @Override
    public boolean getInLoopHeaderImpl() {

        Optional<CtLoop> ancestor = SpoonUtils.getAncestorTry(getNodeImpl(), CtLoop.class);
        if (!ancestor.isPresent()) {
            return false;
        }
        CtLoop loop = ancestor.get();

        return SpoonUtils.insideHeader(loop, getNodeImpl());
    }

    @Override
    public CtVariableAccess<?> getNodeImpl() {
        return (CtVariableAccess<?>) super.getNodeImpl();
    }

    @Override
    public String getNameImpl() {
        return getNodeImpl().getVariable().getSimpleName();
    }

    @Override
    public AJoinpoint<?>[] getReferenceChainImpl() {
        return getChildrenImpl();
    }

    @Override
    public AJoinpoint<?> getDeclarationImpl() {
        var decl = getNodeImpl().getVariable().getDeclaration();
        if (decl == null) {
            return null;
        }

        return CtElement2JoinPoint.convert(decl, getWeaverEngine());
    }
}
