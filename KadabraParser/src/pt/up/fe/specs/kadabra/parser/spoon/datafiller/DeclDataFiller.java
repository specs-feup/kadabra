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

import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.kadabra.ast.decl.TypeDecl;
import pt.up.fe.specs.kadabra.parser.spoon.elementparser.MainParser;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtNamedElement;
import spoon.reflect.declaration.CtType;

public class DeclDataFiller {

    private final MainParser generalParser;

    public DeclDataFiller(MainParser generalParser) {
        this.generalParser = generalParser;
    }

    private ElementDataFiller element() {
        return generalParser.getDataFillers().element();
    }

    public void ctNamedElement(KadabraNode node, CtNamedElement element) {
        // TODO Auto-generated method stub

    }

    public void ctType(KadabraNode node, CtType<?> element) {
        // Hierarchy
        ctNamedElement(node, element);

        var superType = (TypeDecl) generalParser.parse(element.getSuperclass());
        System.out.println("Super: " + element.getSuperclass());
        System.out.println("Super package: " + element.getSuperclass().getQualifiedName());
        System.out.println("Super decl: " + element.getSuperclass().getDeclaration());

    }

    public void ctClass(KadabraNode node, CtClass<?> element) {
        // Hierarchy
        ctType(node, element);

    }
}
