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

import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.kadabra.ast.decl.ClassDecl;
import pt.up.fe.specs.kadabra.ast.decl.ReflectedClassDecl;
import pt.up.fe.specs.kadabra.ast.decl.TypeDecl;
import pt.up.fe.specs.kadabra.parser.spoon.datafiller.DeclDataFiller;
import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
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
    }

    public static void registerParsers(MainParser mainParser) {
        new DeclParsers(mainParser);
    }

    // public static void registerParsers(MainParser mainParser) {
    // // Create parser instance
    // var parsers = new DeclParsers(mainParser);
    //
    // // Obtain and fill node builders
    // var nodeBuilders = mainParser.getNodeBuilders();
    // // nodeBuilders.put(CtClass.class, parsers::ctClass);
    // // nodeBuilders.put(CtTypeReference.class, parsers::ctTypeReference);
    //
    // }
    //
    // @Override
    // private KadabraNodeFactory factory() {
    // return generalParser.getFactory();
    // }

    private DeclDataFiller decl() {
        return dataFillers().decl();
    }

    public ClassDecl ctClass(CtClass<?> ctClass) {

        // var children = new ArrayList<KadabraNode>();

        // Create node
        // If this is a class that there is no source code available, instantiate as a ReflectedClassDecl
        var hasSourceCode = ctClass.getPosition().isValidPosition();
        var classDecl = hasSourceCode ? factory().newNode(ClassDecl.class)
                : factory().newNode(ReflectedClassDecl.class);

        // Fill data
        decl().ctClass(classDecl, ctClass);

        // Only add children if there is source code

        if (hasSourceCode) {
            addCtClassChildren(classDecl, ctClass);
        }

        return classDecl;
    }

    public void addCtTypeChildren(ClassDecl classDecl, CtType<?> ctType) {

        // Get type members
        var members = new ArrayList<>(ctType.getTypeMembers());

        parser().processChildren(members);

        members.stream()
                .map(member -> parser().parse(member))
                .forEach(classDecl::addChild);

    }

    public void addCtClassChildren(ClassDecl classDecl, CtClass<?> ctClass) {
        addCtTypeChildren(classDecl, ctClass);
        // scan(CtRole.ANNOTATION, ctClass.getAnnotations());
        // scan(CtRole.SUPER_TYPE, ctClass.getSuperclass());
        // scan(CtRole.INTERFACE, ctClass.getSuperInterfaces());
        // scan(CtRole.TYPE_PARAMETER, ctClass.getFormalCtTypeParameters());
        // scan(CtRole.TYPE_MEMBER, ctClass.getTypeMembers());
        // scan(CtRole.COMMENT, ctClass.getComments());

        /*
        var children = ctClass.getDirectChildren();
        
        // If class has super, first child is the super type
        int startingIndex = 0;
        if (ctClass.getSuperclass() != null) {
            SpecsCheck.checkArgument(children.get(0).equals(ctClass.getSuperclass()),
                    () -> "Expected child to be super class. Child: " + children.get(0) + "; Super: "
                            + ctClass.getSuperclass());
            startingIndex++;
        }
        
        // Add children
        for (int i = startingIndex; i < children.size(); i++) {
            classDecl.addChild(parser().parse(children.get(i)));
        }
        
        // parser().parseChildren(ctClass).stream()
        // .forEach(classDecl::addChild);
         
         */
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

        // System.out.println("Super: " + ctClass.getSuperclass());
        // System.out.println("Super package: " + ctClass.getSuperclass().getQualifiedName());
        // System.out.println("Super decl: " + ctClass.getSuperclass().getDeclaration());
        // System.out.println("Super decl v2: " + ctClass.getSuperclass().getTypeDeclaration());

        // Type references can be incomplete, if it is the case parse as an incomplete type reference

        // Otherwise, parse declaration directly

        // Create node
        var typeDecl = factory().newNode(TypeDecl.class);

        // Fill data
        decl().ctTypeReference(typeDecl, ctTypeReference);

        // Since parsed as an incomplete TypeDecl, do not add children

        return typeDecl;
    }

}
