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

package autotuner.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import autotuner.algorithm.provider.AlgorithmListProvider;
import autotuner.algorithm.provider.AlgorithmProvider;
import autotuner.algorithm.provider.KnobsProvider;
import autotuner.configs.Configuration;
import autotuner.measurer.Measurer;

/**
 * Supervises an ExplorationTable. Requires:
 * <ul>
 * <li>a {@link AlgorithmListProvider} to provide a list of algorithms to the {@link ExplorationManager}s</li>
 * <li>a {@link Supplier} of {@link Measurer} to provide a measurer for each exploration</li>
 * <li>a {@link Supplier} of {@link Configuration} for {@link AlgorithmProvider}s</li> *
 * </ul>
 * 
 * 
 * @author tiago
 *
 */
public abstract class KnobExplorationSupervisor2<D extends Comparable<D>, M> extends ExplorationSupervisor<D, Void, M> {

    public KnobExplorationSupervisor2(int warmup, int samples) {
        super(warmup, samples);
    }

    protected abstract List<KnobsProvider<?>> getKnobs();

    @Override
    protected final List<AlgorithmProvider<Void>> getAlgorithms() {
        List<? extends AlgorithmProvider<Void>> knobs = getKnobs();
        List<AlgorithmProvider<Void>> algs = new ArrayList<>(knobs);
        return algs;
    }
}