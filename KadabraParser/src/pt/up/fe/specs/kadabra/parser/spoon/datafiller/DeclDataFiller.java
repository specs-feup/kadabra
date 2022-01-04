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
import pt.up.fe.specs.kadabra.ast.decl.LocalVarDecl;
import pt.up.fe.specs.kadabra.ast.decl.MethodDecl;
import pt.up.fe.specs.kadabra.ast.decl.TypeDecl;
import pt.up.fe.specs.kadabra.ast.decl.VarDecl;
import pt.up.fe.specs.kadabra.parser.spoon.elementparser.MainParser;
import pt.up.fe.specs.util.SpecsCheck;
import pt.up.fe.specs.util.SpecsCollections;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtFormalTypeDeclarer;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtNamedElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtTypeInformation;
import spoon.reflect.declaration.CtTypeMember;
import spoon.reflect.declaration.CtTypedElement;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.reference.CtTypeReference;

public class DeclDataFiller extends DataFiller {

    public DeclDataFiller(MainParser generalParser) {
        super(generalParser);
    }
    //
    // @Override
    // private ElementDataFiller element() {
    // return mainParser.getDataFillers().element();
    // }
    //
    // @Override
    // private TypeDecl toTypeDecl(KadabraNode node) {
    // return mainParser.toTypeDecl(node);
    // }

    public void ctTypeInformation(KadabraNode node, CtTypeInformation element) {
        var qualifiedPrefix = DataFillers.extractQualifiedPrefix(element.getQualifiedName(), node.get(Decl.NAME));
        node.set(TypeDecl.QUALIFIED_PREFIX, qualifiedPrefix);

        var superClass = element.getSuperclass();

        // If not present, do nothing
        if (superClass == null) {
            return;
        }

        // Parse reference
        var superType = parser().toTypeDecl(parser().parse(superClass));
        node.set(TypeDecl.SUPER, Optional.of(superType));

        var interfaces = element.getSuperInterfaces();

        // Parse reference
        var interfaceDecls = interfaces.stream()
                .map(ref -> parser().toTypeDecl(parser().parse(ref)))
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

    public void ctExecutable(Decl node, CtExecutable<?> element) {
        ctNamedElement(node, element);

        var thrownTypes = SpecsCollections.map(element.getThrownTypes(),
                thrownType -> toTypeDecl(parser().parse(thrownType)));

        node.set(Decl.THROWN_TYPES, thrownTypes);
    }

    public void ctType(TypeDecl node, CtType<?> element) {
        // Hierarchy
        ctNamedElement(node, element);

        ctTypeInformation(node, element);
        ctFormalTypeDeclarer(node, element);
    }

    public void ctFormalTypeDeclarer(Decl node, CtFormalTypeDeclarer element) {
        var typeParams = SpecsCollections.map(element.getFormalCtTypeParameters(),
                typeParam -> toTypeDecl(parser().parse(typeParam)));

        // var typeParams = element.getFormalCtTypeParameters().stream()
        // .map(typeParam -> (TypeDecl) parser().parse(typeParam))
        // .collect(Collectors.toList());

        node.set(Decl.GENERIC_PARAMS, typeParams);
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

        // Assume that if this function is being called, then decl is incomplete
        node.set(Decl.IS_INCOMPLETE, true);

        // TypeDecl
        ctTypeInformation(node, element);

    }

    public void ctMethod(MethodDecl node, CtMethod<?> element) {
        // Hierarchy
        ctNamedElement(node, element);

        ctFormalTypeDeclarer(node, element);

        ctTypedElement(node, element);

        ctTypeMember(node, element);

        node.set(MethodDecl.IS_DEFAULT, element.isDefaultMethod());
    }

    public void ctTypeMember(Decl node, CtTypeMember element) {
        node.set(Decl.DECLARING_TYPE, toTypeDecl(parser().parse(element.getDeclaringType())));

        var topLevelType = element.getTopLevelType();

        // Set top level type if present
        if (topLevelType != element) {
            node.set(Decl.TOP_LEVEL_TYPE, toTypeDecl(parser().parse(topLevelType)));
        }

    }

    public void ctTypedElement(KadabraNode node, CtTypedElement<?> element) {
        var declType = toTypeDecl(parser().parse(element.getType()));

        node.set(KadabraNode.TYPE, declType);
    }

    public void ctVariable(VarDecl node, CtVariable<?> element) {
        // Hierarchy
        ctNamedElement(node, element);

        ctTypedElement(node, element);

        // Get modifiers
        node.set(VarDecl.MODIFIERS, getModifiers(element));
    }

    public void ctLocalVariable(LocalVarDecl node, CtLocalVariable<?> element) {
        ctVariable(node, element);
    }

}
