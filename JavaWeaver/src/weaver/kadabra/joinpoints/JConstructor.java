/**
 * Copyright 2016 SPeCS.
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

import spoon.reflect.declaration.CtConstructor;
import weaver.kadabra.abstracts.joinpoints.AConstructor;

public class JConstructor<T> extends AConstructor {

    private final CtConstructor<T> node;

    private JConstructor(CtConstructor<T> node) {
        super(JExecutable.newInstance(node));
        this.node = node;
    }

    public static <T> JConstructor<T> newInstance(CtConstructor<T> node) {
        return new JConstructor<>(node);
    }

    // @Override
    // public List<? extends ABody> selectBody() {
    //
    // CtBlock<T> body = node.getBody();
    // List<JBody<?>> node2JoinPointList = SelectUtils.node2JoinPointList(body, JBody::newInstance);
    // return node2JoinPointList;
    // }
    //
    // @Override
    // public List<? extends ADeclaration> selectParam() {
    // List<CtParameter<?>> parameters = node.getParameters();
    // NodeConverter<CtParameter<?>, JDeclaration<?>> converter = JDeclaration::newInstance;
    // return SelectUtils.nodeList2JoinPointList(parameters, converter);
    // }

    @Override
    public CtConstructor<?> getNode() {
        return node;
    }

    @Override
    public String getDeclaratorImpl() {

        return node.getDeclaringType().getQualifiedName();
    }

    // @Override
    // public AJoinPoint[] insertImpl(String position, String code) {
    // return new AJoinPoint[] { insertImplExecutable(position, code) };
    // }
    //
    // @Override
    // public AJoinPoint insertBeforeImpl(String code) {
    // return insertImplExecutable("before", code);
    // }

    // @Override
    // public AJoinPoint[] insertImpl(String position, JoinPoint code) {
    // return new AJoinPoint[] { insertImplExecutable(position, (AJoinPoint) code) };
    // }

    // public AJavaWeaverJoinPoint insertImplExecutable(String position, String code) {
    // Factory factory = getNode().getFactory();
    //
    // var clonedConstructor = node.clone();
    // var constructorClass = getAncestor(CtClass.class);
    // constructorClass.addConstructor(clonedConstructor);
    //
    // var snippetNode = factory.Code()
    //
    // var params = clonedConstructor.getParameters();
    // var newParams = new ArrayList<>(params);
    // newParams.clear();
    // clonedConstructor.setParameters(newParams);
    //
    // // System.out.println("CODE:'" + code + "'");
    // // var snippetNode = factory.Code()
    // // .createCodeSnippetStatement(code).compile();
    // //
    // // System.out.println("SNIPPET CLASS: " + snippetNode.getClass());
    // return null;
    // // CtKadabraSnippetElement snippet = SnippetFactory.createSnippetElement(factory, code);
    // //
    // // return ActionUtils.insertMember(node, snippet, position, getWeaverEngine().getWeaverProfiler());
    // }

    // @Override
    // public void insertImpl(String position, String code) {
    //
    // ActionUtils.insertMember(node, code, position, getWeaverProfiler());
    // }

    @Override
    public String toString() {
        return node.getSignature();
    }
}
