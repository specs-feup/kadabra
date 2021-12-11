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

package pt.up.fe.specs.kadabra.parser.spoon;

import java.util.ArrayList;
import java.util.function.Function;

import pt.up.fe.specs.kadabra.KadabraNodeFactory;
import pt.up.fe.specs.kadabra.ast.CompilationUnit;
import pt.up.fe.specs.kadabra.ast.KadabraContext;
import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.declaration.CtCompilationUnit;
import spoon.reflect.declaration.CtElement;

public class CtElementParser {

    private final KadabraContext context;
    private final KadabraNodeFactory factory;
    private final FunctionClassMap<CtElement, KadabraNode> nodeBuilders;

    public CtElementParser(KadabraContext context) {
        this.context = context;
        this.factory = new KadabraNodeFactory(context);
        this.nodeBuilders = getNodeBuilders();
    }

    private FunctionClassMap<CtElement, KadabraNode> getNodeBuilders() {
        // Jenkins complained about call to constructor being ambiguous
        Function<CtElement, KadabraNode> defaultFunction = this::defaultBuilder;
        var nodeBuilders = new FunctionClassMap<CtElement, KadabraNode>(defaultFunction);

        nodeBuilders.put(CtCompilationUnit.class, this::compilationUnit);

        return nodeBuilders;
    }

    private KadabraNode defaultBuilder(CtElement element) {
        var node = factory.genericKadabraNode(element.getClass().getSimpleName());

        var children = parseChildren(element);

        // Set children
        node.setChildren(children);

        return node;
    }

    public KadabraNode parse(CtElement element) {
        if (!element.getAllMetadata().isEmpty()) {
            System.out.println("Metadata present in element '" + element + "': " + element.getAllMetadata());
        }

        // Apply parser to given element
        var node = nodeBuilders.apply(element);

        // Add data common to all nodes

        // TODO: Comments

        return node;
    }

    private ArrayList<KadabraNode> parseChildren(CtElement element) {
        // Apply parser to children
        var children = new ArrayList<KadabraNode>();

        for (var childElem : element.getDirectChildren()) {
            var child = parse(childElem);

            // ... do any processing that should be done to children (comments?)

            // Add child
            children.add(child);
        }
        return children;
    }

    public CompilationUnit compilationUnit(CtCompilationUnit compilationUnit) {

        var children = new ArrayList<KadabraNode>();

        // Package

        // Imports

        // Declared types
        compilationUnit.getDeclaredTypes().stream()
                .map(this::parse)
                .forEach(children::add);

        var unit = factory.newNode(CompilationUnit.class, children);

        return unit;
    }
}
