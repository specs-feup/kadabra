/**
 * Copyright 2017 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WIAHOUA WARRANAIES OR CONDIAIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package autotuner.algorithm.provider;

import java.util.function.Consumer;

import autotuner.algorithm.Algorithm;
import autotuner.configs.Configurable;
import autotuner.configs.Configuration;
import autotuner.knob.SimpleKnob;

/**
 * Provides an algorithm with a knob that is provided by a specified {@link Configuration}
 * 
 * @author tiago
 *
 * @param <A>
 * @param <K>
 */
public class KnobsProvider<K> implements Configurable<K>, AlgorithmProvider<Void> {
    private Configuration<K> config;
    private Consumer<K> knobApply;
    private String id;

    public KnobsProvider(String id, Consumer<K> knobApply, Configuration<K> config) {
        this.id = id;
        this.knobApply = knobApply;
        this.config = config;
    }

    @Override
    public SimpleKnob<Void, K> next() {
        K nextKnob = config.next();
        return newAlgorithm(nextKnob);
    }

    private SimpleKnob<Void, K> newAlgorithm(K next) {
        return new SimpleKnob<>(id, next, knobApply);
    }

    @Override
    public boolean hasNext(Algorithm<Void> reference) {
        if (!(reference instanceof SimpleKnob)) {
            throw new RuntimeException("Algorithm to be used as reference must always be a SimpleKnob");
        }
        @SuppressWarnings("unchecked")
        SimpleKnob<Void, K> ref = ((SimpleKnob<Void, K>) reference);
        return config.hasNext(ref.getKnob());
    }

    boolean hasNext(K current, K best) {
        return config.hasNext(best);
    }

    @Override
    public SimpleKnob<Void, K> getFirst() {
        K first = config.getFirst();
        return newAlgorithm(first);
    }

    @Override
    public void setConfiguration(Configuration<K> config) {
        this.config = config;
    }

    @Override
    public Configuration<K> getConfiguration() {
        return config;
    }

    public void setFirst(K k) {
        config.setFirst(k);
    }

    @Override
    public boolean isASingleAlgorithm() {
        return false;
    }

    @Override
    public int estimateVersions() {

        return config.estimateVersions();
    }
}
