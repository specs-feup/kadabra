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

import pt.up.fe.specs.kadabra.ast.CompilationUnit;
import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.kadabra.parser.spoon.elementparser.MainParser;
import spoon.reflect.declaration.CtCompilationUnit;
import spoon.reflect.declaration.CtElement;

public class ElementDataFiller {

    private final MainParser generalParser;

    public ElementDataFiller(MainParser generalParser) {
        this.generalParser = generalParser;
    }

    public void ctElement(KadabraNode node, CtElement element) {
        // Add data common to all nodes

        // TODO: Position

        // Set has source
        node.set(KadabraNode.HAS_SOURCE, element.getPosition().isValidPosition());

    }

    public void ctCompilationUnit(KadabraNode node, CtCompilationUnit element) {
        // Hierarchy
        ctElement(node, element);

        // Set attributes
        node.set(CompilationUnit.NAME, element.getMainType().getSimpleName());
        node.set(CompilationUnit.FILE, Optional.ofNullable(element.getFile()));
    }

}
