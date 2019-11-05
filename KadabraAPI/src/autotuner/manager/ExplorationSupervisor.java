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

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import autotuner.algorithm.Algorithm;
import autotuner.algorithm.provider.AlgorithmListProvider;
import autotuner.algorithm.provider.AlgorithmProvider;
import autotuner.configs.Configuration;
import autotuner.configs.factory.ConfigFactory;
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
public abstract class ExplorationSupervisor<D extends Comparable<D>, A, M> {

    private ExplorationTable<D, A, M> table;
    protected int warmup;
    protected int samples;

    // public ExplorationSupervisor() {
    // this(0, 1);
    // }

    public ExplorationSupervisor(int warmup, int samples) {
        setTable(new ExplorationTable<>());
        this.warmup = warmup;
        this.samples = samples;
        table.setDistanceProvider(distanceProvider());
    }

    /**
     * The list of algorithms to test
     * 
     * @return
     */
    protected abstract List<AlgorithmProvider<A>> getAlgorithms();

    /**
     * The measurer to use for each algorithm version
     * 
     * @return
     */
    protected abstract Supplier<? extends Measurer<M>> measurerProvider();

    /**
     * The configuration to use to select the algorithms
     * 
     * @return
     */
    protected ConfigProvider<A> configurationProvider() {
        return ConfigFactory::normal;
    }

    /**
     * The configuration to use to select the algorithms
     * 
     * @return
     */
    protected abstract Algorithm<A> defaultAlgorithm();

    /**
     * The configuration to use to select the algorithms
     * 
     * @return
     */
    protected abstract BiFunction<D, D, Double> distanceProvider();

    // protected ConfigProvider<A> configurationProvider() {
    // return ConfigFactory::defaultConfig;
    // }

    public ExplorationManager<A, M> getOrNew(D key, ExpMode mode) {
        if (table.contains(key)) {
            ExplorationManager<A, M> explorationManager = table.get(key);
            return explorationManager;
        }
        // ExplorationManager<A, M> explorationManager = table.get(key);
        // if (explorationManager != null) {
        // return explorationManager;
        // }
        ExplorationManager<A, M> em = newExploration(key, mode);

        return em;
    }

    /**
     * Returns an algorithm based on the following default options:
     * <li>if exploration does not exist create one in {@link ExpMode#SAMPLING} mode
     * <li>else return the algorithm with {@link ExplorationManager#getVersion()}
     * 
     * @param key
     * @return
     */
    public AlgorithmSampling<A, M> getAlgorithm(D key) {
        ExplorationManager<A, M> manager = getOrNew(key, ExpMode.SAMPLING);
        return manager.getVersion();
    }

    public AlgorithmSampling<A, M> getBest(D key) {
        ExplorationManager<A, M> manager = get(key);
        if (manager == null) {
            return null;
        }
        return manager.getBest();
    }

    public void update(D key, M measurement) {
        ExplorationManager<A, M> manager = get(key);
        manager.update(measurement);
    }

    public ExplorationManager<A, M> newExploration(D key, ExpMode mode) {
        return newExploration(key, mode, configurationProvider());
    }

    public ExplorationManager<A, M> newExploration(D key, ExpMode mode, Algorithm<A> defaultAlgorithm) {
        ExplorationManager<A, M> newExploration = newExploration(key, mode);
        newExploration.setDefaultValue(defaultAlgorithm);
        return newExploration;
    }

    public ExplorationManager<A, M> setBest(D key, Algorithm<A> bestAlgorithm) {
        ExplorationManager<A, M> newExploration = newExploration(key, ExpMode.BEST);
        newExploration.setDefaultValue(bestAlgorithm);
        return newExploration;
    }

    public ExplorationManager<A, M> newExploration(D key, ExpMode mode, ConfigProvider<A> config) {
        List<AlgorithmProvider<A>> algProviders = getAlgorithms();
        Configuration<AlgorithmProvider<A>> algsConfig = config.apply(algProviders);
        Supplier<? extends Measurer<M>> measurerP = measurerProvider();
        ExplorationManager<A, M> explorationManager = new ExplorationManager<>(warmup, samples, algsConfig, measurerP);
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
    public ExplorationTable<D, A, M> getTable() {
        return table;
    }

    private void setTable(ExplorationTable<D, A, M> table) {
        this.table = table;
    }

    public ExplorationManager<A, M> add(D dataSet, ExplorationManager<A, M> exploration) {
        return table.add(dataSet, exploration);
    }

    public ExplorationManager<A, M> newExploration(D dataSet, ExplorationManager<A, M> exploration) {
        return table.newExploration(dataSet, exploration);
    }

    public Entry<D, ExplorationManager<A, M>> getClosest(D key) {
        return table.getClosest(key);
    }

    public boolean contains(D key) {
        return table.contains(key);
    }

    public ExplorationManager<A, M> get(D key) {
        return table.get(key);
    }

    public boolean inBestMode(D key) {
        ExplorationManager<A, M> explorationManager = table.get(key);
        if (explorationManager == null) {
            return false;
        }
        return explorationManager.inBestMode();
    }

    public boolean isStopped(D key) {
        ExplorationManager<A, M> explorationManager = table.get(key);
        if (explorationManager == null) {
            return false;
        }
        return explorationManager.isStopped();
    }

    public boolean isSampling(D key) {
        ExplorationManager<A, M> explorationManager = table.get(key);
        if (explorationManager == null) {
            return false;
        }
        return explorationManager.isSampling();
    }

    public ExplorationManager<A, M> getOrCreateByClosest(D key) {
        ExplorationManager<A, M> explorationManager = table.get(key);
        if (explorationManager != null) {
            return explorationManager;
        }
        explorationManager = getClosest(key).getValue();
        ExplorationManager<A, M> newExploration = newExploration(key, ExpMode.STOPPED);
        newExploration.setDefaultValue(explorationManager.cloneBestOrDefault());
        return newExploration;
    }

    public ExplorationManager<A, M> getValueOrClosest(D key) {
        return table.getValueOrClosest(key);
    }

    @Override
    public String toString() {
        return table.toString();
    }

    public int estimateVersions() {
        List<AlgorithmProvider<A>> build = getAlgorithms();
        int total = 0;
        for (AlgorithmProvider<A> algorithmProvider : build) {
            total += algorithmProvider.estimateVersions();
        }
        return total;
    }

    public Measurer<M> totalMeasurements() {
        Measurer<M> measurer = measurerProvider().get();
        int numWarmups = 0;
        int numRuns = 0;
        Set<D> keySet = table.keySet();
        for (D d : keySet) {
            Measurer<M> dMeasurer = totalMeasurements(d);
            measurer.update(dMeasurer.getValue());
            numWarmups += dMeasurer.getNumWarmup();
            numRuns += dMeasurer.getNumTests();
        }
        measurer.setNumTests(numRuns);
        measurer.setNumWarmup(numWarmups);
        return measurer;
    }

    public Measurer<M> totalMeasurements(D d) {

        return table.get(d).totalMeasurements();
    }
}
