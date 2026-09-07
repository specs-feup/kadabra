/**
 * Copyright 2017 SPeCS.
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

import org.lara.interpreter.weaver.interf.enums.InsertPosition;

import spoon.reflect.code.CtBlock;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.factory.Factory;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ABody;
import weaver.kadabra.abstracts.joinpoints.ADeclaration;
import weaver.kadabra.abstracts.joinpoints.AExecutable;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.ATypeReference;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetElement;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JExecutable<Self extends JExecutable<Self>> extends AExecutable<Self> {

    public JExecutable(CtExecutable node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public String getNameImpl() {
        return getNodeImpl().getSimpleName().toString();
    }

    @Override
    public String setNameImpl(String name) {
        var currentName = getNodeImpl().getSimpleName();
        getNodeImpl().setSimpleName(name);
        return currentName;
    }

    @Override
    public String getReturnTypeImpl() {
        return getNodeImpl().getType().getSimpleName();
    }

    @Override
    public ABody<?> getBodyImpl() {
        final CtBlock<?> body = getNodeImpl().getBody();
        if (body == null) {
            return null;
        }

        return CtElement2JoinPoint.convert(body, getWeaverEngine(), ABody.class);
    }

    @Override
    public CtExecutable<?> getNodeImpl() {
        return (CtExecutable<?>) super.getNodeImpl();
    }

    @Override
    public String getToStringImpl() {
        return getNodeImpl().getSignature();
    }

    @Override
    public ADeclaration<?>[] getParamsImpl() {
        return SelectUtils
                .nodeList2JoinPointList(getNodeImpl().getParameters(),
                        (node -> new JDeclaration<>(node, getWeaverEngine())))
                .toArray(length -> new ADeclaration[0]);
    }

    @Override
    public ATypeReference<?> getReturnRefImpl() {
        return (ATypeReference<?>) CtElement2JoinPoint.convert(getNodeImpl().getType(), getWeaverEngine());
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, String code) {
        return new AJoinpoint<?>[] { insertImplExecutable(position, code) };
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, AJoinpoint<?> code) {
        return new AJoinpoint<?>[] { insertImplExecutable(position, code) };
    }

    public AJoinpoint<?> insertImplExecutable(InsertPosition position, String code) {
        Factory factory = getNodeImpl().getFactory();

        CtKadabraSnippetElement snippet = SnippetFactory.createSnippetElement(factory, code);

        return ActionUtils.insertMember(getNodeImpl(), snippet, position.name().toLowerCase(), getWeaverEngine());
    }

    public AJoinpoint<?> insertImplExecutable(InsertPosition position, AJoinpoint<?> code) {
        return ActionUtils.insertMember(getNodeImpl(), code.getNodeImpl(), position.name().toLowerCase(),
                getWeaverEngine());
    }

}
