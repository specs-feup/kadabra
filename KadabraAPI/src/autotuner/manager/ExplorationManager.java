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

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import autotuner.algorithm.Algorithm;
import autotuner.algorithm.provider.AlgorithmProvider;
import autotuner.configs.Configuration;
import autotuner.configs.factory.ConfigFactory;
import autotuner.measurer.Measurer;

/**
 * Manage the exploration of a set of algorithms. The exploration can use a {@link Configuration} to decide the
 * execution order of the algorithm (e.g. ordered or randomly)
 * 
 * @author tiago
 *
 * @param <A>
 * @param <M>
 */
public class ExplorationManager<A, M> {

    private static final int DEFAULT_LIMIT = 5;
    private AlgorithmsExploration<A, M> fullExploration;
    private AlgorithmsExploration<A, M> currentExploration;
    private Supplier<? extends Measurer<M>> measurementProvider;

    private ExpMode mode;
    private Configuration<AlgorithmProvider<A>> configuration;

    private AlgorithmSampling<A, M> defaultValue;
    private AlgorithmSampling<A, M> current;
    private AlgorithmProvider<A> currentProvider;
    private int numTests;
    private int numWarmup;
    // private boolean first;
    private int limit;

    public ExplorationManager(Configuration<AlgorithmProvider<A>> configuration) {
        this(0, 1, configuration, null);
    }

    /**
     * The exploration Manager tests all algorithms of the list in the same order as in that list
     * 
     * @param numWarmup
     * @param numTests
     * @param algorithms
     */
    public static <A, V> ExplorationManager<A, V> ordered(int numWarmup, int numTests,
            List<AlgorithmProvider<A>> algorithms, Supplier<? extends Measurer<V>> measurementProvider) {
        return new ExplorationManager<>(numWarmup, numTests, ConfigFactory.normal(algorithms),
                measurementProvider);
    }

    /**
     * The exploration Manager tests all algorithms of the list randomly
     * 
     * @param numWarmup
     * @param numTests
     * @param algorithms
     */
    public static <A, V> ExplorationManager<A, V> random(int numWarmup, int numTests,
            List<AlgorithmProvider<A>> algorithms, Supplier<? extends Measurer<V>> measurementProvider) {
        return new ExplorationManager<>(numWarmup, numTests, ConfigFactory.normal(algorithms),
                measurementProvider);
    }

    public ExplorationManager(int numWarmup, int numTests) {
        this(numWarmup, numTests, null, null);
    }

    public ExplorationManager(int numWarmup, int numTests,
            Configuration<AlgorithmProvider<A>> configuration,
            Supplier<? extends Measurer<M>> measurementProvider) {
        reset();
        this.setNumTests(numTests);
        this.setNumWarmup(numWarmup);
        this.configuration = configuration;
        // this.first = true;
        this.measurementProvider = measurementProvider;
        this.limit = DEFAULT_LIMIT;
    }

    private void reset() {
        fullExploration = new AlgorithmsExploration<>();
        currentExploration = new AlgorithmsExploration<>();
        mode = ExpMode.STOPPED;
    }

    public Supplier<? extends Measurer<M>> getMeasurementProvider() {
        return measurementProvider;
    }

    public void setMeasurementProvider(Supplier<Measurer<M>> measurementProvider) {
        this.measurementProvider = measurementProvider;
    }

    public AlgorithmSampling<A, M> getVersion() {
        switch (mode) {
        case STOPPED:
        case BEST:
            AlgorithmSampling<A, M> best = getBest();
            if (best == null) {
                return defaultValue;
            }
            return best;
        case SAMPLING:
        case RESAMPLING:
            return getTest();
        default:
            throw new RuntimeException("Unknown mode: " + mode);
        }
    }

    private AlgorithmSampling<A, M> getTest() {
        if (current == null && currentProvider == null) { // re-think this!
            currentProvider = configuration.getFirst();
            // first = false;
            current = getFirstFromProvider();
            return current;
        }

        if (current.getNumTests() >= getNumTests()) {
            fullExploration.add(current);

            if (!currentProvider.isASingleAlgorithm()) {
                currentExploration.add(current);
                AlgorithmSampling<A, M> best = currentExploration.getBest();

                if (currentProvider.hasNext(best.getAlgorithm())) {
                    current = newSampling(currentProvider.next());
                    return current;
                }
            }

            if (configuration.hasNext(currentProvider)) {
                currentProvider = configuration.next();
                if (!currentProvider.isASingleAlgorithm()) {
                    currentExploration.reset();
                }
                current = getFirstFromProvider();
                return current;
            }
            // Nothing more to explore
            currentProvider = null;
            current = null;
            mode = ExpMode.BEST;
            return getBest();
        }
        return current;
    }

    private AlgorithmSampling<A, M> getFirstFromProvider() {
        return newSampling(currentProvider.getFirst());
    }

    private AlgorithmSampling<A, M> newSampling(Algorithm<A> a) {
        return AlgorithmSampling.newInstance(a, measurementProvider.get(), this);
    }

    public AlgorithmSampling<A, M> getBest() {
        return fullExploration.getBest();
    }

    /**
     * Force an update as the current best may no longer be the best
     */
    public void updateBest() {
        fullExploration.updateBest();
    }

    public void update(M measurement) {
        if (mode == ExpMode.STOPPED || mode == ExpMode.BEST) {
            update(getBest(), measurement);
        } else {
            update(current, measurement);
        }

    }

    public void update(AlgorithmSampling<A, M> toUpdate, M measurement) {

        if (toUpdate.getNumWarmup() < getNumWarmup()) {
            toUpdate.warmup(measurement);
        } else {
            toUpdate.update(measurement);
        }
    }

    /**
     * @return the configuration
     */
    public Configuration<AlgorithmProvider<A>> getConfiguration() {
        return configuration;
    }

    /**
     * @param configuration
     *            the configuration to set
     */
    public void setConfiguration(Configuration<AlgorithmProvider<A>> configuration) {
        this.configuration = configuration;
    }

    /**
     * @return the mode
     */
    public ExpMode getMode() {
        return mode;
    }

    public boolean isStopped() {
        return mode == ExpMode.STOPPED;
    }

    public boolean isSampling() {
        return mode == ExpMode.SAMPLING || mode == ExpMode.RESAMPLING;
    }

    public boolean inBestMode() {
        return mode == ExpMode.BEST;
    }

    /**
     * @param mode
     *            the mode to set
     */
    public void setMode(ExpMode mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        String string = "{\n MODE: " + mode;
        AlgorithmSampling<A, M> best = fullExploration.getBestOrElse(null);
        if (best != null) {
            string += "\n BEST: " + best;
        }
        if (current != null) {
            string += "\n TEST: " + current;
        }

        if (defaultValue != null) {
            string += "\n DEFAULT: " + defaultValue;
        }
        int printingLimit = getPrintingLimit();
        if (printingLimit != 0) {
            string += "\n Explored";
            int expSize = fullExploration.getSize();
            if (limit > -1 && limit < expSize) {
                string += "(Top " + printingLimit + "): " + printAlgSampling(limit);
            } else {
                string += ": " + printAlgSampling(expSize);
            }
        }
        string += "\n }";
        return string;

    }

    private String printAlgSampling(int printingLimit) {
        Iterator<AlgorithmSampling<A, M>> iterator = fullExploration.iterator();
        String versions = "[";
        if (printingLimit != 0 && iterator.hasNext()) {
            AlgorithmSampling<A, M> next = iterator.next();
            versions += next;
            int i = 1;
            while (iterator.hasNext() && i < printingLimit) {
                next = iterator.next();
                versions += " | " + next;
                i++;
            }
        }
        return versions + "]";
    }

    public int getPrintingLimit() {
        return limit;
    }

    /**
     * Sets the limit of explorations to print. -1 for all
     * 
     * @param limit
     */
    public void setPrintingLimit(int limit) {
        this.limit = limit;
    }

    public void pause() {
        setMode(ExpMode.STOPPED);
        if (current != null) {
            fullExploration.add(current);
        }
    }

    public AlgorithmSampling<A, M> getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(AlgorithmSampling<A, M> defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setDefaultValue(Algorithm<A> defaultValue) {
        this.defaultValue = new AlgorithmSampling<>(defaultValue, getMeasurementProvider().get());
    }

    public AlgorithmSampling<A, M> getBestOrDefault() {

        AlgorithmSampling<A, M> best = getBest();
        return best != null ? best : defaultValue;
    }

    public AlgorithmSampling<A, M> clone(AlgorithmSampling<A, M> version) {

        Algorithm<A> algorithm = version.getAlgorithm();
        AlgorithmSampling<A, M> best = new AlgorithmSampling<>(algorithm, measurementProvider.get());
        return best;
    }

    public AlgorithmSampling<A, M> cloneBestOrDefault() {
        return clone(getBestOrDefault());
    }

    public int getNumTests() {
        return numTests;
    }

    public void setNumTests(int numTests) {
        this.numTests = numTests;
    }

    public int getNumWarmup() {
        return numWarmup;
    }

    public void setNumWarmup(int numWarmup) {
        this.numWarmup = numWarmup;
    }

    public Measurer<M> totalMeasurements() {
        Measurer<M> measurer = getMeasurementProvider().get();
        Measurer<M> fullExpMeasurer = getMeasurementProvider().get();
        Measurer<M> currentExpMeasurer = getMeasurementProvider().get();
        fullExploration.totalMeasurements(fullExpMeasurer);
        currentExploration.totalMeasurements(currentExpMeasurer);

        measurer.update(fullExpMeasurer.getValue());
        measurer.update(currentExpMeasurer.getValue());
        measurer.setNumTests(fullExpMeasurer.getNumTests() + currentExpMeasurer.getNumTests());
        measurer.setNumWarmup(fullExpMeasurer.getNumWarmup() + currentExpMeasurer.getNumWarmup());
        return measurer;
    }

}
