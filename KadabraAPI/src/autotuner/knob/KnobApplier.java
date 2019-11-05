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

package autotuner.knob;

import java.util.function.Consumer;

import autotuner.algorithm.SimpleAlgorithm;

/**
 * An algorithm associated to a knob. E.g., method with tiled loop in which the block must be defined as a knob. The
 * {@link #apply()} method applies the knob settings
 * 
 * @author tiago
 *
 * @param <A>
 * @param <K>
 */
public class KnobApplier<K> extends SimpleAlgorithm<K> {
    protected Knob<K> knob;

    public KnobApplier(String id, K value, Consumer<K> action) {
        this(id, new Knob<>(value, action));
    }

    public KnobApplier(String id, Knob<K> knob) {
        super(null, id);
        this.knob = knob;
    }

    @Override
    public void apply() {
        knob.apply();
    }

    @Override
    public K applyAndGet() {
        apply();
        return knob.getValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof KnobApplier)) {
            return false;
        }
        KnobApplier<?> alg2 = (KnobApplier<?>) obj;
        return knob.equals(alg2.knob);
    }

    public K getKnob() {

        return knob.getValue();
    }

    @Override
    public String toString() {
        return id + " [" + knob.getValue() + "]";
    }

    @Override
    public boolean needsApply() {
        return true;
    }

    public void apply(Consumer<K> applier) {
        knob.apply(applier);
    }
}
