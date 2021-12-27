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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import pt.up.fe.specs.kadabra.KadabraNodeFactory;
import pt.up.fe.specs.kadabra.ast.KadabraContext;
import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.kadabra.ast.decl.TypeDecl;
import pt.up.fe.specs.kadabra.ast.generic.GenericKadabraNode;
import pt.up.fe.specs.kadabra.ast.generic.GenericTypeDecl;
import pt.up.fe.specs.kadabra.parser.spoon.datafiller.DataFillers;
import pt.up.fe.specs.kadabra.parser.spoon.elements.CtLabel;
import pt.up.fe.specs.kadabra.parser.spoon.nodes.UnresolvedNode;
import pt.up.fe.specs.kadabra.parser.spoon.nodes.UnresolvedTypeDecl;
import pt.up.fe.specs.util.SpecsCollections;
import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;

public class MainParser {

    private final KadabraContext context;
    private final KadabraNodeFactory factory;
    private final FunctionClassMap<CtElement, KadabraNode> nodeParsers;
    private final Map<CtElement, KadabraNode> nodeMap;

    // Represents nodes that are currently being parsed
    // Needed to detect cyclic dependencies
    private final Set<CtElement> parsing;
    private final DataFillers dataFillers;
    // private final List<Runnable> queuedActions;
    private final List<CtElement> textElements;

    public MainParser(KadabraContext context) {
        this.context = context;
        this.factory = new KadabraNodeFactory(context);
        this.nodeParsers = initNodeParsers();
        this.nodeMap = new HashMap<>();
        this.parsing = new HashSet<>();
        this.dataFillers = new DataFillers(this);
        this.textElements = new ArrayList<>();

        // Register parsers
        ElementParsers.registerParsers(this);
        DeclParsers.registerParsers(this);
        StmtParsers.registerParsers(this);

        // Tip: check CtScanner to find out the children of a CtElement
    }

    public FunctionClassMap<CtElement, KadabraNode> getNodeBuilders() {
        return nodeParsers;
    }

    public KadabraNodeFactory getFactory() {
        return factory;
    }

    private FunctionClassMap<CtElement, KadabraNode> initNodeParsers() {
        // Jenkins complained about call to constructor being ambiguous
        Function<CtElement, KadabraNode> defaultFunction = this::defaultNodeParser;

        var nodeParsers = new FunctionClassMap<CtElement, KadabraNode>(defaultFunction);

        // Add parsers for miscellaneous nodes
        // nodeBuilders.put(CtCompilationUnit.class, this::compilationUnit);

        return nodeParsers;
    }

    private KadabraNode defaultNodeParser(CtElement element) {
        var node = factory.genericKadabraNode(element.getClass().getSimpleName());

        var children = parseChildren(element);

        // Set children
        node.setChildren(children);

        return node;
    }

    public KadabraNode parse(CtElement element) {
        // Should separate parsing the node from the children?
        // If we were doing a final step to connect declarations, it could be done
        // Since we are using Spoon connections, we can establish them during parsing and avoid that step

        // Check if element has already been parsed
        var node = nodeMap.get(element);
        if (node != null) {
            // System.out.println("REUSING NODE FOR : " + element);
            return node;
        }

        if (!parsing.add(element)) {
            // TODO: Return special node that is later replaced? But references to it would need to be updated also
            // Can be detected in fields by overloading KadabraNode.add()

            var unresolved = factory.newNode(UnresolvedNode.class);
            unresolved.set(UnresolvedNode.ORIGINAL_ELEMENT, element);

            return unresolved;
            // throw new RuntimeException("Element already being parsed: " + element);
        }

        if (!element.getAllMetadata().isEmpty()) {
            System.out.println("Metadata present in element '" + element + "': " + element.getAllMetadata());
        }

        // Apply parser to given element
        node = nodeParsers.apply(element);

        // Store mapping between element and node
        nodeMap.put(element, node);

        // Queue insertion of comments and annotations
        collectTextElements(element);

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

    private void collectTextElements(CtElement element) {
        // Text elements might be siblings or children
        // On this phase just collect them, to insert later

        textElements.addAll(element.getAnnotations());
        textElements.addAll(element.getComments());

        // Add labels as separate nodes
        if (element instanceof CtStatement) {
            var label = new CtLabel(((CtStatement) element).getLabel());
            label.setPosition(element.getPosition());
            textElements.add(label);
        }

        /*
        var siblings = new ArrayList<CtElement>();
        
        siblings.add(element);
        siblings.addAll(element.getAnnotations());
        siblings.addAll(element.getComments());
        
        for (var sib : siblings) {
            var pos = sib.getPosition();
            System.out.println("Pos: " + pos);
            System.out.println("Line: " + pos.getLine());
            System.out.println("Start: " + pos.getSourceStart());
            System.out.println("End: " + pos.getSourceEnd());
            System.out.println("Type: " + sib.getClass());
            // System.out.println("Node:\n" + sib);
        
        }
        
        System.out.println("Siblings before: "
                + siblings.stream().map(ct -> ct.getClass().getSimpleName()).collect(Collectors.joining(", ")));
        
        // Order nodes by position
        Collections.sort(siblings, SpoonParsers::compare);
        
        System.out.println("Siblings after: "
                + siblings.stream().map(ct -> ct.getClass().getSimpleName()).collect(Collectors.joining(", ")));
        
        // Separate by the position of the base element
        */
    }

    public List<KadabraNode> parseChildren(CtElement element) {
        // Only parse children for nodes from which we have source code
        if (!element.getPosition().isValidPosition()) {
            return Collections.emptyList();
        }

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

    public DataFillers getDataFillers() {
        return dataFillers;
    }

    public TypeDecl toTypeDecl(KadabraNode node) {
        if (node instanceof TypeDecl) {
            return (TypeDecl) node;
        }

        if (node instanceof GenericKadabraNode) {
            return new GenericTypeDecl(node.getData(), node.getChildren());
        }

        if (node instanceof UnresolvedNode) {
            return new UnresolvedTypeDecl(node.getData(), node.getChildren());
        }

        throw new RuntimeException("Not implemented for " + node);
    }

    public void print(String prefix, List<? extends CtElement> elements) {
        var message = elements.stream()
                .map(element -> element.getClass().getSimpleName() + "->" + element.getPosition())
                .collect(Collectors.joining("\n", prefix + "\n", ""));

        System.out.println(message);
    }

    public void removeNoSource(List<? extends CtElement> elements) {
        SpecsCollections.remove(elements, element -> !element.getPosition().isValidPosition());

    }

    public void processChildren(List<? extends CtElement> elements) {
        // Remove elements that have no source
        removeNoSource(elements);

        // print("Before: ", elements);
        // Sort elements
        SpoonParsers.sort(elements);
        // print("After: ", elements);

    }
}
