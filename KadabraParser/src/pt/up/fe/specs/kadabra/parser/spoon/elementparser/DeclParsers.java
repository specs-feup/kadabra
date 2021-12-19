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

import pt.up.fe.specs.kadabra.KadabraNodeFactory;
import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.kadabra.ast.decl.ClassDecl;
import pt.up.fe.specs.kadabra.ast.decl.TypeDecl;
import pt.up.fe.specs.kadabra.parser.spoon.datafiller.DeclDataFiller;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.reference.CtTypeReference;

public class DeclParsers {

    private final MainParser generalParser;
    // private final DeclDataFiller dataFiller;

    private DeclParsers(MainParser generalParser) {
        this.generalParser = generalParser;
        // this.dataFiller = new DeclDataFiller(generalParser);
    }

    public static void registerParsers(MainParser mainParser) {
        // Create parser instance
        var parsers = new DeclParsers(mainParser);

        // Obtain and fill node builders
        var nodeBuilders = mainParser.getNodeBuilders();
        // nodeBuilders.put(CtClass.class, parsers::ctClass);
        // nodeBuilders.put(CtTypeReference.class, parsers::ctTypeReference);

    }

    private KadabraNodeFactory factory() {
        return generalParser.getFactory();
    }

    private DeclDataFiller decl() {
        return generalParser.getDataFillers().decl();
    }

    public ClassDecl ctClass(CtClass<?> ctClass) {
        // TODO: Parse children
        var children = new ArrayList<KadabraNode>();

        // Create node
        var classDecl = factory().newNode(ClassDecl.class, children);

        // Fill data
        decl().ctClass(classDecl, ctClass);

        return classDecl;
    }

    /**
     * A reference to a type declaration. If the type exists, parse it. Otherwise, create an incomplete type.
     * 
     * @param ctTypeReference
     * @return
     */
    public TypeDecl ctTypeReference(CtTypeReference<?> ctTypeReference) {
        return null;
    }

}
