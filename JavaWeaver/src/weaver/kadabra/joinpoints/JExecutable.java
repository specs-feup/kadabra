/**
 * Copyright 2017 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.joinpoints;

import org.lara.interpreter.weaver.interf.JoinPoint;

import spoon.reflect.code.CtBlock;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.factory.Factory;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.ABody;
import weaver.kadabra.abstracts.joinpoints.ADeclaration;
import weaver.kadabra.abstracts.joinpoints.AExecutable;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.ATypeReference;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetElement;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JExecutable<R> extends AExecutable {

    private CtExecutable<R> node;

    private JExecutable(CtExecutable<R> node, JavaWeaver weaver) {
        super(weaver);
        this.node = node;
    }

    public static <R> JExecutable<R> newInstance(CtExecutable<R> node, JavaWeaver weaver) {
        return new JExecutable<>(node, weaver);
    }

    @Override
    public String getNameImpl() {
        return node.getSimpleName().toString();
    }

    @Override
    public String setNameImpl(String name) {
        var currentName = node.getSimpleName();
        node.setSimpleName(name);
        return currentName;
    }

    @Override
    public String getReturnTypeImpl() {
        return node.getType().getSimpleName();
    }

    @Override
    public ABody getBodyImpl() {
        final CtBlock<?> body = node.getBody();
        if (body == null) {
            return null;
        }

        return CtElement2JoinPoint.convert(body, getWeaverEngine(), ABody.class);
    }

    @Override
    public CtExecutable<R> getNode() {
        return node;
    }

    @Override
    public String toString() {
        return node.getSignature();
    }

    @Override
    public ADeclaration[] getParamsArrayImpl() {
        return SelectUtils
                .nodeList2JoinPointList(node.getParameters(),
                        (node -> JDeclaration.newInstance(node, getWeaverEngine())))
                .toArray(length -> new ADeclaration[0]);
    }

    @Override
    public ATypeReference getReturnRefImpl() {
        return (ATypeReference) CtElement2JoinPoint.convert(node.getType(), getWeaverEngine());
    }

    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return new AJoinPoint[] { insertImplExecutable(position, code) };
    }

    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return new AJoinPoint[] { insertImplExecutable(position, (AJoinPoint) code) };
    }

    public AJavaWeaverJoinPoint insertImplExecutable(String position, String code) {
        Factory factory = getNode().getFactory();

        CtKadabraSnippetElement snippet = SnippetFactory.createSnippetElement(factory, code);

        return ActionUtils.insertMember(node, snippet, position, getWeaverEngine());
    }

    public AJavaWeaverJoinPoint insertImplExecutable(String position, AJoinPoint code) {
        return ActionUtils.insertMember(node, code.getNode(), position, getWeaverEngine());
    }

}
