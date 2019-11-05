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

package autotuner.knob.manager;

import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import autotuner.algorithm.provider.AlgorithmListProvider;
import autotuner.algorithm.provider.AlgorithmProvider;
import autotuner.configs.Configuration;
import autotuner.knob.KnobApplier;
import autotuner.manager.ExpMode;
import autotuner.measurer.Measurer;

/**
 * Supervises an KnobExplorationTable. Requires:
 * <ul>
 * <li>a {@link AlgorithmListProvider} to provide a list of algorithms to the {@link KnobExplorationManager}s</li>
 * <li>a {@link Supplier} of {@link Measurer} to provide a measurer for each exploration</li>
 * <li>a {@link Supplier} of {@link Configuration} for {@link AlgorithmProvider}s</li> *
 * </ul>
 * 
 * 
 * @author tiago
 *
 */
public abstract class KnobExplorationConcurrentSupervisor<D extends Comparable<D>, K, M> {
    private KnobExplorationTable<D, K, M> table;
    protected int warmup;
    protected int samples;

    public KnobExplorationConcurrentSupervisor(int warmup, int samples) {
        setTable(new KnobExplorationTable<>());
        this.warmup = warmup;
        this.samples = samples;
        table.setDistanceProvider(distanceProvider());
    }

    protected abstract KnobProvider<K> getKnobProvider();

    /**
     * The measurer to use for each algorithm version
     * 
     * @return
     */
    protected abstract Supplier<? extends Measurer<M>> measurerProvider();

    /**
     * The default value for the knobs
     * 
     * @return
     */
    protected abstract K defaultKnobValue();

    /**
     * The configuration to use to select the algorithms
     * 
     * @return
     */
    protected abstract BiFunction<D, D, Double> distanceProvider();

    // protected ConfigProvider<K> configurationProvider() {
    // return ConfigFactory::defaultConfig;
    // }

    public KnobExplorationManager<K, M> getOrNew(D key, ExpMode mode) {
        if (table.contains(key)) {
            KnobExplorationManager<K, M> explorationManager = table.get(key);
            return explorationManager;
        }
        // KnobExplorationManager<K, M> explorationManager = table.get(key);
        // if (explorationManager != null) {
        // return explorationManager;
        // }
        KnobExplorationManager<K, M> em = newExploration(key, mode);

        return em;
    }

    /**
     * Returns a knob based on the following default options:
     * <li>if exploration does not exist create one in {@link ExpMode#SAMPLING} mode
     * <li>else return the algorithm with {@link KnobExplorationManager#getVersion()}
     * 
     * @param key
     * @return
     */
    public KnobSampling<K, M> getKnob(D key) {
        KnobExplorationManager<K, M> manager = getOrNew(key, ExpMode.SAMPLING);
        return manager.getVersion();
    }

    public KnobSampling<K, M> getBest(D key) {
        KnobExplorationManager<K, M> manager = get(key);
        if (manager == null) {
            return null;
        }
        return manager.getBest();
    }

    public void update(D key, M measurement) {
        KnobExplorationManager<K, M> manager = get(key);
        manager.update(measurement);
    }

    // public KnobExplorationManager<K, M> newExploration(D key, ExpMode mode) {
    // return newExploration(key, mode);
    // }

    public KnobExplorationManager<K, M> newExploration(D key, ExpMode mode, KnobApplier<K> defaultAlgorithm) {
        KnobExplorationManager<K, M> newExploration = newExploration(key, mode);
        newExploration.setDefaultValue(defaultAlgorithm);
        return newExploration;
    }

    public KnobExplorationManager<K, M> setBest(D key, KnobApplier<K> bestAlgorithm) {
        KnobExplorationManager<K, M> newExploration = newExploration(key, ExpMode.BEST);
        newExploration.setDefaultValue(bestAlgorithm);
        return newExploration;
    }

    public KnobExplorationManager<K, M> newExploration(D key, ExpMode mode) {
        KnobProvider<K> knobsProvider = getKnobProvider();
        Supplier<? extends Measurer<M>> measurerP = measurerProvider();
        KnobExplorationManager<K, M> explorationManager = new KnobExplorationManager<>(warmup, samples, knobsProvider,
                measurerP);
        explorationManager.setMode(mode);
        table.add(key, explorationManager);
        return explorationManager;
    }

    public void reset() {
        table.reset();
    }

    public void pause() {
        table.pause();
    }

    public void resume() {
        table.resume();
    }

    ////// TABLE WRAPPERS/////////
    public KnobExplorationTable<D, K, M> getTable() {
        return table;
    }

    private void setTable(KnobExplorationTable<D, K, M> table) {
        this.table = table;
    }

    public KnobExplorationManager<K, M> add(D dataSet, KnobExplorationManager<K, M> exploration) {
        return table.add(dataSet, exploration);
    }

    public KnobExplorationManager<K, M> newExploration(D dataSet, KnobExplorationManager<K, M> exploration) {
        return table.newExploration(dataSet, exploration);
    }

    public Entry<D, KnobExplorationManager<K, M>> getClosest(D key) {
        return table.getClosest(key);
    }

    public boolean contains(D key) {
        return table.contains(key);
    }

    public KnobExplorationManager<K, M> get(D key) {
        return table.get(key);
    }

    public boolean inBestMode(D key) {
        KnobExplorationManager<K, M> explorationManager = table.get(key);
        if (explorationManager == null) {
            return false;
        }
        return explorationManager.inBestMode();
    }

    public boolean isStopped(D key) {
        KnobExplorationManager<K, M> explorationManager = table.get(key);
        if (explorationManager == null) {
            return false;
        }
        return explorationManager.isStopped();
    }

    public boolean isSampling(D key) {
        KnobExplorationManager<K, M> explorationManager = table.get(key);
        if (explorationManager == null) {
            return false;
        }
        return explorationManager.isSampling();
    }

    public KnobExplorationManager<K, M> getOrCreateByClosest(D key) {
        KnobExplorationManager<K, M> explorationManager = table.get(key);
        if (explorationManager != null) {
            return explorationManager;
        }
        explorationManager = getClosest(key).getValue();
        KnobExplorationManager<K, M> newExploration = newExploration(key, ExpMode.STOPPED);
        newExploration.setDefaultValue(explorationManager.cloneBestOrDefault());
        return newExploration;
    }

    public KnobExplorationManager<K, M> getValueOrClosest(D key) {
        return table.getValueOrClosest(key);
    }

    @Override
    public String toString() {
        return table.toString();
    }

    public int estimateVersions() {
        KnobProvider<K> build = getKnobProvider();
        return build.estimateVersions();
    }

}