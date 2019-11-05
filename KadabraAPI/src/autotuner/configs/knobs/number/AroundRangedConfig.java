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

package autotuner.configs.knobs.number;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import autotuner.configs.Configuration;
import autotuner.configs.knobs.number.ranged.RangedKnob;

/**
 * The default configuration is a simple exhaustive search that tries all possible values/combinations with a provided
 * step
 * 
 * @author tiago
 *
 */
public class AroundRangedConfig<T extends Number> implements Configuration<T> {
    private Deque<T> queue = new ArrayDeque<>();
    private List<T> visited = new ArrayList<>();
    private RangedKnob<T> knob;

    public AroundRangedConfig(RangedKnob<T> rangedKnob) {
        knob = rangedKnob;
    }

    @Override
    public void setFirst(T value) {
        knob.setValue(value);
    }

    @Override
    public T getFirst() {

        return next();
    }

    @Override
    public boolean hasNext(T reference) {
        if (queue.isEmpty()) {

            T left = knob.descend(reference);
            T right = knob.ascend(reference);

            if (knob.inBounds(left) && !visited.contains(left)) {

                queue.push(left);
            }
            if (knob.inBounds(right) && !visited.contains(right)) {

                queue.push(right);
            }

            if (!visited.contains(reference)) {
                queue.push(reference);
            }
            if (queue.isEmpty()) {
                return false;
            }
        }

        knob.setValue(queue.pop());
        return true;
    }

    @Override
    public T next() {
        T value = knob.getValue();
        visited.add(value);
        return value;
    }

    @Override
    public int estimateVersions() {
        int worstCase = knob.estimateVersions(); // considering that all are tested
        return worstCase / 3; // going to consider that only a third is tested
    }

}
