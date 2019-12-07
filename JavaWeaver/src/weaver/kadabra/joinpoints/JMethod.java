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

import java.util.Set;

import org.lara.interpreter.weaver.interf.JoinPoint;

import spoon.refactoring.Refactoring;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.Factory;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AClass;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AMethod;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetElement;
import weaver.utils.SpoonUtils;
import weaver.utils.generators.AdapterGenerator;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SnippetFactory;
import weaver.utils.weaving.TypeUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JMethod<T> extends AMethod {

    private final CtMethod<T> node;

    private JMethod(CtMethod<T> node) {
        super(JExecutable.newInstance(node));

        this.node = node;
    }

    public static <T> JMethod<T> newInstance(CtMethod<T> node) {
        return new JMethod<>(node);
    }

    @Override
    public AJoinPoint copyImpl() {
        return CtElement2JoinPoint.convert(Refactoring.copyMethod(node));
    }

    @Override
    public String toString() {
        return getNameImpl();
    }

    @Override
    public CtMethod<T> getNode() {
        return node;
    }

    @Override
    public Set<ModifierKind> getModifiersInternal() {
        return node.getModifiers();
    }
    // @Override
    // public Boolean getIsStaticImpl() {
    // return node.getModifiers().contains(ModifierKind.STATIC);
    // }

    @Override
    public String getToReferenceImpl() {

        return node.getDeclaringType().getSimpleName() + "::" + node.getSimpleName();
    }

    @Override
    public String getToQualifiedReferenceImpl() {
        return node.getDeclaringType().getQualifiedName() + "::" + node.getSimpleName();
    }

    @Override
    public JMethod<T> cloneImpl(String newName) {

        CtMethod<T> clone = ActionUtils.cloneElement(node);
        CtType<?> ancestor = SpoonUtils.getAncestor(node, CtType.class);
        int inc = 0;
        while (!ancestor.getMethodsByName(newName).isEmpty()) {
            newName = newName + (inc++);
        }
        clone.setSimpleName(newName);
        ancestor.addMethod(clone);
        JMethod<T> newInstance = JMethod.newInstance(clone);
        return newInstance;
    }

    @Override
    public void addCommentImpl(String comment) {
        String docComment = node.getDocComment();
        if (docComment == null || docComment.isEmpty()) {
            docComment = comment;
        } else {
            docComment += "\n" + comment;
        }
        node.setDocComment(docComment);
    }

    @Override
    public void addParameterImpl(String type, String name) {
        Factory factory = node.getFactory();
        CtParameter<Object> parameter = factory.Core().createParameter();
        parameter.setType(TypeUtils.typeOf(type, factory));
        parameter.setSimpleName(name);
        node.addParameter(parameter);
    }

    @Override
    public AClass createAdapterImpl(AMethod adaptMethod, String name) {
        return createAdapterImpl(adaptMethod, name, false);
    }

    public AClass createAdapterImpl(AMethod adaptMethod, String name, boolean reuseIfExists) {
        JMethod<?> jMethod = (JMethod<?>) adaptMethod;
        CtMethod<?> adaptMethodNode = jMethod.getNode();
        JClass<?> jClass = AdapterGenerator.generate(name, adaptMethodNode, node, getWeaverProfiler(), reuseIfExists);
        return jClass;
    }

    @Override
    public String getDeclaratorImpl() {
        return node.getDeclaringType().getQualifiedName();
    }

    // @Override
    // public void defImpl(String attribute, Object value) {
    // if (attribute.equals("privacy")) {
    // node.addModifier(ModifierKind.valueOf(value.toString()));
    // }
    // throw new RuntimeException("The attribute '" + attribute + "' is not available for def action");
    // }
    @Override
    public String getPrivacyImpl() {
        Set<ModifierKind> modifiers = node.getModifiers();
        if (modifiers.contains(ModifierKind.PRIVATE)) {
            return "private";
        }
        if (modifiers.contains(ModifierKind.PUBLIC)) {
            return "public";
        }
        if (modifiers.contains(ModifierKind.PROTECTED)) {
            return "protected";
        }
        return "";
    }

    @Override
    public void defPrivacyImpl(String value) {
        node.addModifier(ModifierKind.valueOf(value.toString()));
    }

    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return new AJoinPoint[] { insertImplJMethod(position, code) };
    }

    @Override
    public AJoinPoint[] insertImpl(String position, JoinPoint code) {
        return new AJoinPoint[] { insertImplJMethod(position, (AJoinPoint) code) };
    }

    public AJavaWeaverJoinPoint insertImplJMethod(String position, String code) {
        Factory factory = getNode().getFactory();
        // CtCodeSnippetStatement snippet = SnippetFactory.createSnippetStatement(code, factory);
        CtKadabraSnippetElement snippet = SnippetFactory.createSnippetElement(factory, code);

        return ActionUtils.insertMember(node, snippet, position, getWeaverEngine().getWeaverProfiler());
    }

    public AJavaWeaverJoinPoint insertImplJMethod(String position, AJoinPoint code) {
        return ActionUtils.insertMember(node, code.getNode(), position, getWeaverEngine().getWeaverProfiler());
    }

    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return insertImplJMethod("after", code);
    }

    @Override
    public AJoinPoint insertBeforeImpl(String code) {
        return insertImplJMethod("before", code);
    }

    @Override
    public AJoinPoint insertReplaceImpl(String code) {
        return insertImplJMethod("replace", code);
    }

    @Override
    public AJoinPoint insertReplaceImpl(AJoinPoint jp) {
        return insertImplJMethod("replace", jp);
    }

    /*// Old insertImpl
    CtKadabraSnippetElement snippet = SnippetFactory.createSnippetElement(factory, code);
    SourcePosition pos2 = node.getPosition();
    
    // snippet.setPosition(position2);
    if (pos2 != null) {
       KadabraSourcePosition newPosition = new KadabraSourcePosition(pos2);
       if (position.equals("AFTER")) {
           newPosition.setLine(newPosition.getLine() + 1);
           newPosition.setColumn(newPosition.getColumn() - (counter++)); // FIXME
       }
       snippet.setPosition(newPosition);
    }
    ancestor.addNestedType(snippet);
    if (position.equals("REPLACE") || position.equals("AROUND")) {
    
       ancestor.removeMethod(node);
    }
    */

    // private static int counter = 0;
}
