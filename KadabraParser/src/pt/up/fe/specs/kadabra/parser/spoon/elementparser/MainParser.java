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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import pt.up.fe.specs.kadabra.KadabraNodeFactory;
import pt.up.fe.specs.kadabra.ast.CompilationUnit;
import pt.up.fe.specs.kadabra.ast.KadabraContext;
import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.kadabra.parser.spoon.datafiller.DataFillers;
import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.declaration.CtCompilationUnit;
import spoon.reflect.declaration.CtElement;

public class MainParser {

    private final KadabraContext context;
    private final KadabraNodeFactory factory;
    private final FunctionClassMap<CtElement, KadabraNode> nodeBuilders;
    private final Map<CtElement, KadabraNode> nodeMap;
    // Represents nodes that are currently being parsed
    // Needed to detect cyclic dependencies
    private final Set<CtElement> parsing;
    private final DataFillers dataFillers;

    public MainParser(KadabraContext context) {
        this.context = context;
        this.factory = new KadabraNodeFactory(context);
        this.nodeBuilders = getBaseNodeBuilders();
        this.nodeMap = new HashMap<>();
        this.parsing = new HashSet<>();
        this.dataFillers = new DataFillers(this);

        // Add parsers for elements
        DeclParsers.registerParsers(this);

        // Add parsers for decls
        DeclParsers.registerParsers(this);
    }

    FunctionClassMap<CtElement, KadabraNode> getNodeBuilders() {
        return nodeBuilders;
    }

    public KadabraNodeFactory getFactory() {
        return factory;
    }

    private FunctionClassMap<CtElement, KadabraNode> getBaseNodeBuilders() {
        // Jenkins complained about call to constructor being ambiguous
        Function<CtElement, KadabraNode> defaultFunction = this::defaultBuilder;
        var nodeBuilders = new FunctionClassMap<CtElement, KadabraNode>(defaultFunction);

        // Add parsers for miscellaneous nodes
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
        // Should separate parsing the node from the children?
        // If we were doing a final step to connect declarations, it could be done
        // Since we are using Spoon connections, and establish them during parsing, probably not

        // Check if element has already been parsed
        var node = nodeMap.get(element);
        if (node != null) {
            System.out.println("REUSING NODE FOR : " + element);
            return node;
        }

        if (!parsing.add(element)) {
            // TODO: Return special node that is later replaced? But references to it would need to be updated also
            // Can be detected in fields by overloading KadabraNode.add()
            throw new RuntimeException("Element already being parsed: " + element);
        }

        if (!element.getAllMetadata().isEmpty()) {
            System.out.println("Metadata present in element '" + element + "': " + element.getAllMetadata());
        }

        // Apply parser to given element
        node = nodeBuilders.apply(element);

        // Store mapping between element and node
        nodeMap.put(element, node);

        // TODO: Comments
        // Queue action to convert comments and insert before the node?

        // var fieldReplacer = new NodeFieldReplacer<>(CtElementParser::replacementProvider);
        //
        // fieldReplacer.replaceFields(factory.app(Collections.emptyList()));
        // fieldReplacer.replaceFields(factory.compilationUnit(null, null, null));

        parsing.remove(element);

        return node;
    }

    // private static Optional<KadabraNode> replacementProvider(KadabraNode node) {
    // return Optional.of(node);
    // }

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

        // TODO

        // Package

        // Imports

        // Declared types
        compilationUnit.getDeclaredTypes().stream()
                .map(this::parse)
                .forEach(children::add);

        // var name = compilationUnit.getMainType().getSimpleName();
        // var file = compilationUnit.getFile();
        // var unit = factory.compilationUnit(name, file, children);
        var unit = factory.newNode(CompilationUnit.class, children);
        dataFillers.element().ctCompilationUnit(unit, compilationUnit);
        // var unit = factory.newNode(CompilationUnit.class, children);
        //
        // // Set attributes
        //
        // unit.set(CompilationUnit.NAME, compilationUnit.getMainType().getSimpleName());
        // unit.set(CompilationUnit.FILE, Optional.ofNullable(compilationUnit.getFile()));

        return unit;
    }

    public DataFillers getDataFillers() {
        return dataFillers;
    }

}
