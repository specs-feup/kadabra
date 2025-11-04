/**
 * Copyright 2018 SPeCS.
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

package autotuner.configs.knobs.tuples.linear;

import java.util.List;

import org.javatuples.Tuple;

import autotuner.configs.Configuration;
import autotuner.configs.knobs.number.ranged.RangedKnob;
import pt.up.fe.specs.util.SpecsCollections;

public abstract class LinearInc<T extends Tuple> implements Configuration<T> {
    private List<RangedKnob<? extends Number>> knobs;
    private RangedKnob<? extends Number> current;
    private int pos;

    public LinearInc() {

        this.knobs = SpecsCollections.newArrayList();
        reset();
    }

    @Override
    public boolean hasNext(T reference) {
        if (current == null) {
            if (pos >= knobs.size()) {
                return false;
            }
            pos = 0;
            current = knobs.get(pos);
        }
        if (current.inc()) {
            return true;
        }
        // else
        current.setValueFromNumber((Number) reference.getValue(pos)); // assumes tuple has the same length as the
                                                                      // number of ranges
        pos++;
        if (pos >= knobs.size()) {
            current = null;
            return false;
        }
        current = knobs.get(pos);
        return hasNext(reference);
    }

    protected void add(RangedKnob<? extends Number> range) {
        knobs.add(range);
    }

    private void reset() {
        current = null;
        pos = 0;
    }

    @Override
    public int estimateVersions() {
        return knobs.stream().map(RangedKnob::estimateVersions).reduce(0, (a, v) -> a + v) - knobs.size() - 1;
    }
}
