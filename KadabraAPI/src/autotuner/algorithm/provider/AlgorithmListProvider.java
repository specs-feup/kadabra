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

package autotuner.algorithm.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import autotuner.configs.Configuration;

/**
 * Create a list of algorithm providers
 * 
 * @author tiago
 *
 * @param <A>
 */
public class AlgorithmListProvider<A> {

    private List<Supplier<AlgorithmProvider<A>>> providers;

    public AlgorithmListProvider() {
        providers = new ArrayList<>();
    }

    /**
     * Add an algorithm that uses a knob
     * 
     * @param algorithm
     * @param id
     * @param knobApply
     * @param configSupplier
     * @return
     */
    public <K> AlgorithmListProvider<A> algorithmWithKnob(A algorithm, String id,
            Consumer<K> knobApply, Supplier<Configuration<K>> configSupplier) {
        Supplier<AlgorithmProvider<A>> aWKP = () -> new AlgorithmWithKnobProvider<>(algorithm,
                id, knobApply,
                configSupplier.get());
        providers.add(aWKP);
        return this;
    }

    public AlgorithmListProvider<A> algorithm(A algorithm, String id) {
        Supplier<AlgorithmProvider<A>> sAP = () -> new SingleAlgorithmProvider<>(algorithm,
                id);
        providers.add(sAP);
        return this;
    }

    public AlgorithmListProvider<A> algorithm(Supplier<AlgorithmProvider<A>> algorithmProvider) {
        providers.add(algorithmProvider);
        return this;
    }

    public AlgorithmListProvider<A> algorithms(Map<String, A> algorithms) {

        for (Entry<String, A> entry : algorithms.entrySet()) {
            algorithm(entry.getValue(), entry.getKey());
        }
        return this;
    }

    public List<AlgorithmProvider<A>> build() {
        return providers.stream().map(s -> s.get()).collect(Collectors.toList());

    }
}
