/**
 * Copyright 2021 SPeCS.
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

package pt.up.fe.specs.kadabra.parser.spoon.elementparser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.kadabra.ast.decl.ClassDecl;
import pt.up.fe.specs.kadabra.ast.decl.LocalVarDecl;
import pt.up.fe.specs.kadabra.ast.decl.MethodDecl;
import pt.up.fe.specs.kadabra.ast.decl.TypeDecl;
import pt.up.fe.specs.kadabra.parser.spoon.datafiller.DeclDataFiller;
import pt.up.fe.specs.util.SpecsCollections;
import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtTypeReference;

public class DeclParsers extends SpoonParsers {

    private DeclParsers(MainParser mainParser) {
        super(mainParser);
    }

    @Override
    protected void registerParsers(FunctionClassMap<CtElement, KadabraNode> parsers) {
        parsers.put(CtClass.class, this::ctClass);
        parsers.put(CtTypeReference.class, this::ctTypeReference);
        parsers.put(CtMethod.class, this::ctMethod);
        parsers.put(CtLocalVariable.class, this::ctLocalVariable);
    }

    public static void registerParsers(MainParser mainParser) {
        new DeclParsers(mainParser);
    }

    private DeclDataFiller decl() {
        return dataFillers().decl();
    }

    public ClassDecl ctClass(CtClass<?> ctClass) {
        return newParser(ClassDecl.class, decl()::ctClass, this::getCtClassChildren).parse(ctClass);
    }

    /**
     * A reference to a type declaration. If the type exists, parse it. Otherwise, create an incomplete type.
     * 
     * @param ctTypeReference
     * @return
     */
    public TypeDecl ctTypeReference(CtTypeReference<?> ctTypeReference) {

        var ctType = ctTypeReference.getTypeDeclaration();

        // If declaration present, parse as declaration
        if (ctType != null) {
            return parser().toTypeDecl(parser().parse(ctType));
        }

        // Otherwise, parse as an incomplete type reference
        // Since parsed as an incomplete TypeDecl, do not add children
        return newParser(TypeDecl.class, decl()::ctTypeReference, element -> Collections.emptyList())
                .parse(ctTypeReference);
    }

    public MethodDecl ctMethod(CtMethod<?> ctMethod) {
        return newParser(MethodDecl.class, decl()::ctMethod, this::getCtMethodChildren).parse(ctMethod);
    }

    public LocalVarDecl ctLocalVariable(CtLocalVariable<?> ctLocalVariable) {
        return newParser(LocalVarDecl.class, decl()::ctLocalVariable, this::getCtLocalVariableChildren)
                .parse(ctLocalVariable);
    }

    public Collection<CtElement> getCtMethodChildren(CtMethod<?> ctMethod) {
        var children = new ArrayList<CtElement>();

        children.addAll(ctMethod.getParameters());
        children.add(ctMethod.getBody());

        return children;
    }

    public Collection<? extends CtElement> getCtTypeChildren(CtType<?> ctType) {
        return ctType.getTypeMembers();
    }

    public Collection<? extends CtElement> getCtClassChildren(CtClass<?> ctClass) {
        return getCtTypeChildren(ctClass);
    }

    public Collection<? extends CtElement> getCtLocalVariableChildren(CtLocalVariable<?> element) {
        // Has one optional child, the assignment
        return SpecsCollections.asListT(CtElement.class, element.getAssignment());
    }

}
