/**
 * Copyright 2016 SPeCS.
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

package weaver.utils.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import spoon.reflect.code.CtLoop;
import spoon.reflect.declaration.CtElement;
import tdrc.utils.StringUtils;
import weaver.utils.SpoonUtils;
import weaver.utils.scanners.NodeProcessor;
import weaver.utils.scanners.NodeSearcher;

/**
 * Calculates the rank of a node based on a searching type. Used, for instance, to calculate the rank of a loop
 * 
 * @author Tiago
 *
 */
public class RankCalculator {
    public static String calculateString(CtElement node, Class<? extends CtElement> searchType) {
        List<Integer> ranks = RankCalculator.calculate(node, CtLoop.class);
        return StringUtils.join(ranks, ".");
    }

    public static List<Integer> calculate(CtElement node, Class<? extends CtElement> searchType) {
        CtElement executableAncestor = SpoonUtils.getExecutableAncestor(node);
        return RankCalculator.calculate(node, CtLoop.class, executableAncestor);
    }

    public static List<Integer> calculate(CtElement node, Class<? extends CtElement> searchType,
            CtElement root) {
        CtElement ancestor = getRankedAncestor(node, searchType, root);
        int rank = calculate(node, ancestor, searchType);
        if (ancestor != root) {
            List<Integer> parentRank = calculate(ancestor, searchType, root);
            parentRank.add(rank);
            return parentRank;
        }
        List<Integer> ranks = new ArrayList<>();
        ranks.add(rank);
        return ranks;
    }

    private static <T extends CtElement> int calculate(CtElement node, CtElement ancestor,
            Class<T> searchType) {
        Accumulator acc = new Accumulator();

        NodeProcessor<T> processor = t -> {

            acc.inc();
            if (t.equals(node)) {
                acc.stop();
            }
        };

        new NodeSearcher(searchType, processor, Collections.emptyList(), Arrays.asList(searchType))
                .scan(ancestor.getElements(e -> !e.equals(ancestor)));

        return acc.value;
    }

    public static CtElement getRankedAncestor(CtElement node, Class<? extends CtElement> searchType,
            CtElement root) {
        Optional<? extends CtElement> ancestor = SpoonUtils.getAncestorTry(node, searchType);

        return ancestor.isPresent() ? ancestor.get() : root;
    }

    static class Accumulator {

        private int value = 0;
        private boolean stop = false;

        public int value() {
            return value;
        }

        public void inc() {
            if (!stop) {
                value++;
            }
        }

        public void stop() {
            stop = true;
        }

        public void reset() {
            value = 0;
            stop = false;
        }
    }
}
