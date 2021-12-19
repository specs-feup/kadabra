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

package pt.up.fe.specs.kadabra.parser.spoon.datafiller;

import java.util.Optional;

import pt.up.fe.specs.kadabra.ast.decl.ClassDecl;
import pt.up.fe.specs.kadabra.ast.decl.Decl;
import pt.up.fe.specs.kadabra.ast.decl.TypeDecl;
import pt.up.fe.specs.kadabra.parser.spoon.elementparser.MainParser;
import pt.up.fe.specs.util.SpecsCheck;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtNamedElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtTypeReference;

public class DeclDataFiller {

    private final MainParser mainParser;

    public DeclDataFiller(MainParser generalParser) {
        this.mainParser = generalParser;
    }

    private ElementDataFiller element() {
        return mainParser.getDataFillers().element();
    }

    public void ctNamedElement(Decl node, CtNamedElement ctNamedElement) {
        element().ctElement(node, ctNamedElement);

        // Name
        node.set(Decl.NAME, ctNamedElement.getSimpleName());
    }

    public void ctType(TypeDecl node, CtType<?> ctType) {
        // Hierarchy
        ctNamedElement(node, ctType);

        var qualifiedPrefix = DataFillers.extractQualifiedPrefix(ctType.getQualifiedName(), node.get(Decl.NAME));
        node.set(TypeDecl.QUALIFIED_PREFIX, qualifiedPrefix);

        var superClass = ctType.getSuperclass();

        // If present, has super
        if (superClass != null) {
            // Parse reference
            var superType = mainParser.toTypeDecl(mainParser.parse(ctType.getSuperclass()));
            node.set(TypeDecl.SUPER, Optional.of(superType));
        }

    }

    public void ctClass(ClassDecl node, CtClass<?> ctClass) {
        // Hierarchy
        ctType(node, ctClass);

    }

    public void ctTypeReference(TypeDecl typeDecl, CtTypeReference<?> ctTypeReference) {
        // Assume that if this function is being called, then type is incomplete

        SpecsCheck.checkArgument(ctTypeReference.getTypeDeclaration() == null,
                () -> "Expected type declaration to be null: " + ctTypeReference);

        typeDecl.set(TypeDecl.IS_INCOMPLETE, true);
        typeDecl.set(Decl.NAME, ctTypeReference.getSimpleName());

        var qualifiedPrefix = DataFillers.extractQualifiedPrefix(ctTypeReference.getQualifiedName(),
                typeDecl.get(Decl.NAME));
        typeDecl.set(TypeDecl.QUALIFIED_PREFIX, qualifiedPrefix);

        // TODO Auto-generated method stub

        // System.out.println("Super: " + element.getSuperclass());
        // System.out.println("Super package: " + element.getSuperclass().getQualifiedName());
        // System.out.println("Super decl: " + element.getSuperclass().getDeclaration());

    }
}
