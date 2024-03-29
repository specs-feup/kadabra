/**
 * Copyright 2015 SPeCS Research Group.
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

import org.lara.interpreter.weaver.interf.JoinPoint;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AField;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.utils.weaving.ActionUtils;

public class JField<T> extends AField {

    private final CtField<T> node;

    JField(CtField<T> field) {
        super(JDeclaration.newInstance(field));
        node = field;

    }

    public static <T> JField<T> newInstance(CtField<T> field) {
        return new JField<>(field);
    }

    @Override
    public CtField<?> getNode() {
        return node;
    }

    @Override
    public String getDeclaratorImpl() {
        return node.getDeclaringType().getQualifiedName();
    }

    // @Override
    // public void initImpl(String value) {
    // CtCodeSnippetExpression<T> snippetExpression = SnippetFactory.snippetExpression(value, node.getFactory());
    // node.setDefaultExpression(snippetExpression);
    // }

    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return new AJoinPoint[] { insertImplJField(position, (CtElement) code.getNode()) };
    }

    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return new AJoinPoint[] { insertImplJField(position, code) };
    }

    public AJavaWeaverJoinPoint insertImplJField(String position, CtElement code) {
        return ActionUtils.insertMember(node, code, position, getWeaverProfiler());
    }

    public AJavaWeaverJoinPoint insertImplJField(String position, String code) {
        return ActionUtils.insertMember(node, code, position, getWeaverProfiler());
    }

    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return insertImplJField("before", code);
    }

    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return insertImplJField("after", code);
    }

    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return insertImplJField("replace", code);
    }

    @Override
    public String getStaticAccessImpl() {
        return getDeclaratorImpl() + "." + getNameImpl();
    }

    @Override
    public String toString() {
        return getNameImpl();
    }
}
