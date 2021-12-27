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
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;

import pt.up.fe.specs.kadabra.ast.KadabraNode;
import spoon.reflect.declaration.CtElement;

public class GenericSpoonParser<N extends KadabraNode, E extends CtElement> {

    private final Class<N> kadabraNodeClass;
    private final BiConsumer<N, E> dataFiller;
    private final Function<E, Collection<? extends CtElement>> childrenGetter;
    private final MainParser parser;

    public GenericSpoonParser(Class<N> kadabraNodeClass, BiConsumer<N, E> dataFiller,
            Function<E, Collection<? extends CtElement>> childrenGetter,
            MainParser parser) {
        this.kadabraNodeClass = kadabraNodeClass;
        this.dataFiller = dataFiller;
        this.childrenGetter = childrenGetter;
        this.parser = parser;
    }

    public N parse(E element) {
        // Create node
        var node = parser.getFactory().newNode(kadabraNodeClass);

        // Fill data
        dataFiller.accept(node, element);

        // Only add children if there is source code
        if (node.get(KadabraNode.HAS_SOURCE)) {
            // Get children
            // Create new array list in order to be able to process it
            var children = new ArrayList<>(childrenGetter.apply(element));

            parser.processChildren(children);

            children.stream()
                    .map(child -> parser.parse(child))
                    .forEach(node::addChild);
        }

        return node;
    }

}
