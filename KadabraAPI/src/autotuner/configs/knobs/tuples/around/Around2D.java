/**
 * Copyright 2017 SPeCS.
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

package autotuner.configs.knobs.tuples.around;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.javatuples.Pair;

import autotuner.configs.Configuration;
import autotuner.configs.knobs.number.ranged.RangedKnob;
import autotuner.configs.knobs.tuples.utils.TupleUtils;

/**
 * The around configuration searches the space around a given reference point.
 * 
 * @author tiago
 *
 *
 */
public class Around2D<L extends Number, R extends Number> implements Configuration<Pair<L, R>> {
    private Deque<Pair<L, R>> queue = new ArrayDeque<>();
    private List<Pair<L, R>> visited = new ArrayList<>();
    private RangedKnob<L> leftRange;
    private RangedKnob<R> rightRange;
    private Pair<L, R> first;
    private Pair<L, R> next;

    public Around2D(RangedKnob<L> leftRange, RangedKnob<R> rightRange) {
        this.leftRange = leftRange;
        this.rightRange = rightRange;
        first = Pair.with(leftRange.getValue(), rightRange.getValue());
    }

    @Override
    public void setFirst(Pair<L, R> pair) {
        first = pair;
        leftRange.setValue(pair.getValue0());
        rightRange.setValue(pair.getValue1());
    }

    @Override
    public Pair<L, R> getFirst() {
        visited.add(first);
        return first;
    }

    @Override
    public boolean hasNext(Pair<L, R> reference) {
        if (queue.isEmpty()) {

            List<L> lefts = leftRange.getSurrounding(reference.getValue0(), true);
            List<R> rights = rightRange.getSurrounding(reference.getValue1(), true);
            List<Pair<L, R>> pairs = TupleUtils.combine(lefts, rights);

            for (Pair<L, R> pair : pairs) {
                if (!visited.contains(pair)) {
                    queue.push(pair);
                }
            }

            if (queue.isEmpty()) {
                return false;
            }
        }

        next = queue.pop();
        return true;
    }

    @Override
    public Pair<L, R> next() {
        visited.add(next);
        return next;
    }

    @Override
    public int estimateVersions() {
        int total = 1;
        total = leftRange.estimateVersions() * rightRange.estimateVersions();
        return total / 3; // consider that only a third is tested!
    }
}
