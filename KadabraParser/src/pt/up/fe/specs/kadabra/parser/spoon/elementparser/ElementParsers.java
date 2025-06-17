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

import pt.up.fe.specs.kadabra.ast.CompilationUnit;
import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.kadabra.parser.spoon.datafiller.ElementDataFiller;
import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.declaration.CtCompilationUnit;
import spoon.reflect.declaration.CtElement;

public class ElementParsers extends SpoonParsers {

    protected ElementParsers(MainParser mainParser) {
        super(mainParser);
    }

    @Override
    protected void registerParsers(FunctionClassMap<CtElement, KadabraNode> parsers) {
        parsers.put(CtCompilationUnit.class, this::compilationUnit);
    }

    public static void registerParsers(MainParser mainParser) {
        new ElementParsers(mainParser);
    }

    private ElementDataFiller element() {
        return dataFillers().element();
    }

    public CompilationUnit compilationUnit(CtCompilationUnit ctCompilationUnit) {

        // Create node
        var compilationUnit = factory().newNode(CompilationUnit.class);
        element().ctCompilationUnit(compilationUnit, ctCompilationUnit);

        // Add children

        // TODO

        // Package

        // Imports

        // Declared types
        ctCompilationUnit.getDeclaredTypes().stream()
                .map(parser()::parse)
                .forEach(compilationUnit::addChild);

        return compilationUnit;
    }

}
