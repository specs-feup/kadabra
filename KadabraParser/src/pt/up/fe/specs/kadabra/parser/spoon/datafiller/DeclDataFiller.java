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
import java.util.stream.Collectors;

import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.kadabra.ast.decl.ClassDecl;
import pt.up.fe.specs.kadabra.ast.decl.Decl;
import pt.up.fe.specs.kadabra.ast.decl.TypeDecl;
import pt.up.fe.specs.kadabra.parser.spoon.elementparser.MainParser;
import pt.up.fe.specs.util.SpecsCheck;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtNamedElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtTypeInformation;
import spoon.reflect.reference.CtTypeReference;

public class DeclDataFiller {

    private final MainParser mainParser;

    public DeclDataFiller(MainParser generalParser) {
        this.mainParser = generalParser;
    }

    private ElementDataFiller element() {
        return mainParser.getDataFillers().element();
    }

    private void ctTypeInformation(KadabraNode node, CtTypeInformation element) {
        var qualifiedPrefix = DataFillers.extractQualifiedPrefix(element.getQualifiedName(), node.get(Decl.NAME));
        node.set(TypeDecl.QUALIFIED_PREFIX, qualifiedPrefix);

        var superClass = element.getSuperclass();

        // If not present, do nothing
        if (superClass == null) {
            return;
        }

        // Parse reference
        var superType = mainParser.toTypeDecl(mainParser.parse(superClass));
        node.set(TypeDecl.SUPER, Optional.of(superType));

        var interfaces = element.getSuperInterfaces();

        // Parse reference
        var interfaceDecls = interfaces.stream()
                .map(ref -> mainParser.toTypeDecl(mainParser.parse(ref)))
                .collect(Collectors.toList());

        node.set(TypeDecl.INTERFACES, interfaceDecls);
    }

    // private void addInterfaces(KadabraNode node, Collection<CtTypeReference<?>> interfaces) {
    //
    // }

    /// Element parsers

    public void ctNamedElement(Decl node, CtNamedElement ctNamedElement) {
        element().ctElement(node, ctNamedElement);

        // Name
        node.set(Decl.NAME, ctNamedElement.getSimpleName());
    }

    public void ctType(TypeDecl node, CtType<?> element) {
        // Hierarchy
        ctNamedElement(node, element);

        ctTypeInformation(node, element);
    }

    public void ctClass(ClassDecl node, CtClass<?> ctClass) {
        // Hierarchy
        ctType(node, ctClass);

    }

    public void ctTypeReference(TypeDecl node, CtTypeReference<?> element) {
        SpecsCheck.checkArgument(element.getTypeDeclaration() == null,
                () -> "Expected type declaration to be null: " + element);

        // Hierarchy
        element().ctElement(node, element);

        // Decl
        node.set(Decl.NAME, element.getSimpleName());

        // TypeDecl
        ctTypeInformation(node, element);

        // Assume that if this function is being called, then type is incomplete
        node.set(TypeDecl.IS_INCOMPLETE, true);

    }
}
