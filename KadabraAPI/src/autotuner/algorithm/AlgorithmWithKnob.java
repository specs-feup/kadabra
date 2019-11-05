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

package autotuner.algorithm;

import java.util.function.Consumer;

import autotuner.knob.Knob;

/**
 * An algorithm associated to a knob. E.g., method with tiled loop in which the block must be defined as a knob. The
 * {@link #apply()} method applies the knob settings
 * 
 * @author tiago
 *
 * @param <A>
 * @param <K>
 */
public class AlgorithmWithKnob<A, K> extends SimpleAlgorithm<A> {
    protected Knob<K> knob;

    public AlgorithmWithKnob(A method, String id, K value, Consumer<K> action) {
        this(method, id, new Knob<>(value, action));
    }

    public AlgorithmWithKnob(A method, String id, Knob<K> knob) {
        super(method, id);
        this.knob = knob;
    }

    @Override
    public void apply() {
        knob.apply();
    }

    @Override
    public A applyAndGet() {
        apply();
        return get();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AlgorithmWithKnob)) {
            return false;
        }
        AlgorithmWithKnob<?, ?> alg2 = (AlgorithmWithKnob<?, ?>) obj;
        return super.method.equals(alg2.method) && knob.equals(alg2.knob);
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
}
