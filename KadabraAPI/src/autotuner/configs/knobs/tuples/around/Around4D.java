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

import org.javatuples.Quartet;

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
public class Around4D<A extends Number, B extends Number, C extends Number, D extends Number>
        implements Configuration<Quartet<A, B, C, D>> {
    private Deque<Quartet<A, B, C, D>> queue = new ArrayDeque<>();
    private List<Quartet<A, B, C, D>> visited = new ArrayList<>();
    private RangedKnob<A> aRange;
    private RangedKnob<B> bRange;
    private RangedKnob<C> cRange;
    private RangedKnob<D> dRange;
    private Quartet<A, B, C, D> first;
    private Quartet<A, B, C, D> next;

    public Around4D(RangedKnob<A> aRange, RangedKnob<B> bRange, RangedKnob<C> cRange, RangedKnob<D> dRange) {
        this.aRange = aRange;
        this.bRange = bRange;
        this.cRange = cRange;
        this.dRange = dRange;
        first = Quartet.with(aRange.getValue(), bRange.getValue(), cRange.getValue(), dRange.getValue());
    }

    @Override
    public void setFirst(Quartet<A, B, C, D> pair) {
        first = pair;
        aRange.setValue(pair.getValue0());
        bRange.setValue(pair.getValue1());
        cRange.setValue(pair.getValue2());
        dRange.setValue(pair.getValue3());
    }

    @Override
    public Quartet<A, B, C, D> getFirst() {
        visited.add(first);
        return first;
    }

    @Override
    public boolean hasNext(Quartet<A, B, C, D> reference) {
        if (queue.isEmpty()) {

            List<A> as = aRange.getSurrounding(reference.getValue0());
            List<B> bs = bRange.getSurrounding(reference.getValue1());
            List<C> cs = cRange.getSurrounding(reference.getValue2());
            List<D> ds = dRange.getSurrounding(reference.getValue3());
            List<Quartet<A, B, C, D>> pairs = TupleUtils.combine(as, bs, cs, ds);

            for (Quartet<A, B, C, D> pair : pairs) {
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
    public Quartet<A, B, C, D> next() {
        visited.add(next);
        return next;
    }

    @Override
    public int estimateVersions() {
        int total = 1;
        total = aRange.estimateVersions() * bRange.estimateVersions() * cRange.estimateVersions()
                * dRange.estimateVersions();
        return total / 3; // consider that only a third is tested!
    }
}
