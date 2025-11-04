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

package autotuner.configs.knobs.number;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import autotuner.configs.Configuration;
import autotuner.configs.knobs.number.ranged.RangedKnob;
import tdrc.tuple.Tuple;
import tdrc.tuple.TupleList;
import tdrc.utils.ListUtils;

/**
 * The around configuration searches the space around a given reference point. step
 * 
 * @author tiago
 *
 *
 */
public class AroundRangedConfigTuple<T extends Number> implements Configuration<Tuple<T>> {
    private Deque<Tuple<T>> queue = new ArrayDeque<>();
    private List<Tuple<T>> visited = new ArrayList<>();
    private List<RangedKnob<T>> rangedList;
    private Tuple<T> first;
    private Tuple<T> next;

    public AroundRangedConfigTuple(List<RangedKnob<T>> rangedKnobList) {
        this.rangedList = rangedKnobList;
        first = Tuple.newInstance();
        rangedList.forEach(k -> first.add(k.getValue()));
    }

    @Override
    public void setFirst(Tuple<T> t) {
        first = t;
    }

    @Override
    public Tuple<T> getFirst() {
        visited.add(first);
        return first;
    }

    @Override
    public boolean hasNext(Tuple<T> reference) {
        if (queue.isEmpty()) {

            List<List<T>> toCombine = new ArrayList<>();
            for (int i = 0; i < reference.size(); i++) {
                T newValue = reference.get(i);
                RangedKnob<T> knob = rangedList.get(i);
                T left = knob.descend(newValue);
                T right = knob.ascend(newValue);
                List<T> neighbors = new ArrayList<>();
                neighbors.add(newValue);
                if (knob.inBounds(left)) {
                    neighbors.add(left);
                }
                if (knob.inBounds(right)) {

                    neighbors.add(right);
                }
                toCombine.add(neighbors);
            }

            TupleList<T> tuples = ListUtils.createTuplesFromList(toCombine);
            for (Tuple<T> tuple : tuples) {
                if (!visited.contains(tuple)) {
                    queue.push(tuple);
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
    public Tuple<T> next() {
        visited.add(next);
        return next;
    }

    @Override
    public int estimateVersions() {
        int total = 1;
        for (RangedKnob<T> rangedKnob : rangedList) {
            total *= rangedKnob.estimateVersions();
        }
        return total / 3; // consider that only a third is tested!
    }
}
