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

package autotuner.knob;

import java.util.function.Consumer;

import autotuner.algorithm.SimpleAlgorithm;

/**
 * A knob with no association to any algorithm.
 * 
 * @author tiago
 *
 * @param <A>
 * @param <K>
 */
public class SimpleKnob<A, K> extends SimpleAlgorithm<A> {
    protected Knob<K> knob;

    public SimpleKnob(String id, K value, Consumer<K> action) {
        this(id, new Knob<>(value, action));
    }

    public SimpleKnob(K value, Consumer<K> action) {
        this(null, new Knob<>(value, action));
    }

    public SimpleKnob(String id, Knob<K> knob) {
        super(null, id);
        this.knob = knob;
    }

    @Override
    public void apply() {
        knob.apply();
    }

    @Override
    public A get() {
        throw new NullPointerException("Cannot access algorithm in a 'knob-only'. See autotuner.algorithm.SimpleKnob");
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SimpleKnob)) {
            return false;
        }
        SimpleKnob<?, ?> alg2 = (SimpleKnob<?, ?>) obj;
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
}
