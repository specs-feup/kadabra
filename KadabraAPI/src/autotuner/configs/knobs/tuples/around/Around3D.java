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
 * specific language governing permissions and limitations under the License.
 */

package autotuner.configs.knobs.tuples.around;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.javatuples.Triplet;

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
public class Around3D<L extends Number, M extends Number, R extends Number>
        implements Configuration<Triplet<L, M, R>> {
    private Deque<Triplet<L, M, R>> queue = new ArrayDeque<>();
    private List<Triplet<L, M, R>> visited = new ArrayList<>();
    private RangedKnob<L> leftRange;
    private RangedKnob<M> middleRange;
    private RangedKnob<R> rightRange;
    private Triplet<L, M, R> first;
    private Triplet<L, M, R> next;

    public Around3D(RangedKnob<L> leftRange, RangedKnob<M> middleRange, RangedKnob<R> rightRange) {
        this.leftRange = leftRange;
        this.middleRange = middleRange;
        this.rightRange = rightRange;
        first = Triplet.with(leftRange.getValue(), middleRange.getValue(), rightRange.getValue());
    }

    @Override
    public void setFirst(Triplet<L, M, R> pair) {
        first = pair;
        leftRange.setValue(pair.getValue0());
        middleRange.setValue(pair.getValue1());
        rightRange.setValue(pair.getValue2());
    }

    @Override
    public Triplet<L, M, R> getFirst() {
        visited.add(first);
        return first;
    }

    @Override
    public boolean hasNext(Triplet<L, M, R> reference) {
        if (queue.isEmpty()) {

            List<L> lefts = leftRange.getSurrounding(reference.getValue0(), true);
            List<M> middles = middleRange.getSurrounding(reference.getValue1(), true);
            List<R> rights = rightRange.getSurrounding(reference.getValue2(), true);
            List<Triplet<L, M, R>> pairs = TupleUtils.combine(lefts, middles, rights);

            for (Triplet<L, M, R> pair : pairs) {
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
    public Triplet<L, M, R> next() {
        visited.add(next);
        return next;
    }

    @Override
    public int estimateVersions() {
        int total = 1;
        total = leftRange.estimateVersions() * middleRange.estimateVersions() * rightRange.estimateVersions();
        return total / 3; // consider that only a third is tested!
    }
}
