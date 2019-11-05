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

import java.util.Iterator;
import java.util.function.Supplier;

import autotuner.configs.Configuration;
import autotuner.knob.KnobApplier;
import autotuner.manager.ExpMode;
import autotuner.measurer.Measurer;

/**
 * Manage the exploration of a set of algorithms. The exploration can use a {@link Configuration} to decide the
 * execution order of the algorithm (e.g. ordered or randomly)
 * 
 * @author tiago
 *
 * @param <K>
 * @param <M>
 */
public class KnobExplorationManager<K, M> {

    private static final int DEFAULT_LIMIT = 5;
    private KnobExploration<K, M> fullExploration;
    private KnobExploration<K, M> currentExploration;
    private Supplier<? extends Measurer<M>> measurementProvider;

    private ExpMode mode;
    private KnobProvider<K> configuration;

    private KnobSampling<K, M> defaultValue;
    private KnobSampling<K, M> current;
    private KnobProvider<K> currentProvider;
    private int numTests;
    private int numWarmup;
    // private boolean first;
    private int limit;
    private boolean first;

    public KnobExplorationManager(KnobProvider<K> configuration) {
        this(0, 1, configuration, null);
    }

    public KnobExplorationManager(int numWarmup, int numTests) {
        this(numWarmup, numTests, null, null);
    }

    public KnobExplorationManager(int numWarmup, int numTests,
            KnobProvider<K> configuration,
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
        fullExploration = new KnobExploration<>();
        currentExploration = new KnobExploration<>();
        mode = ExpMode.STOPPED;
        first = true;
    }

    public Supplier<? extends Measurer<M>> getMeasurementProvider() {
        return measurementProvider;
    }

    public void setMeasurementProvider(Supplier<Measurer<M>> measurementProvider) {
        this.measurementProvider = measurementProvider;
    }

    public KnobSampling<K, M> getVersion() {
        switch (mode) {
        case STOPPED:
        case BEST:
            KnobSampling<K, M> best = getBest();
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

    private KnobSampling<K, M> getTest() {
        if (first) {//
            currentProvider = configuration;
            first = false;
            current = getFirstFromProvider();
            return current;
        }

        if (current.getNumTests() >= getNumTests()) {
            fullExploration.add(current);

            currentExploration.add(current);
            KnobSampling<K, M> best = currentExploration.getBest();

            if (currentProvider.hasNext(best.getKnob())) {
                // Next knob configuration
                current = newSampling(currentProvider.next());
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

    private KnobSampling<K, M> getFirstFromProvider() {
        return newSampling(currentProvider.getFirst());
    }

    private KnobSampling<K, M> newSampling(KnobApplier<K> a) {
        return new KnobSampling<>(a, measurementProvider.get(), this);
    }

    public KnobSampling<K, M> getBest() {
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

    public void update(KnobSampling<K, M> toUpdate, M measurement) {

        if (toUpdate.getNumWarmup() < getNumWarmup()) {
            toUpdate.warmup(measurement);
        } else {
            toUpdate.update(measurement);
        }
    }

    /**
     * @return the configuration
     */
    public KnobProvider<K> getConfiguration() {
        return configuration;
    }

    /**
     * @param configuration
     *            the configuration to set
     */
    public void setConfiguration(KnobProvider<K> configuration) {
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
        KnobSampling<K, M> best = fullExploration.getBestOrElse(null);
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
                string += "(Top " + printingLimit + "): " + printKnobSampling(limit);
            } else {
                string += ": " + printKnobSampling(expSize);
            }
        }
        string += "\n }";
        return string;

    }

    private String printKnobSampling(int printingLimit) {
        Iterator<KnobSampling<K, M>> iterator = fullExploration.iterator();
        String versions = "[";
        if (printingLimit != 0 && iterator.hasNext()) {
            KnobSampling<K, M> next = iterator.next();
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

    public KnobSampling<K, M> getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(KnobSampling<K, M> defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setDefaultValue(KnobApplier<K> defaultValue) {
        this.defaultValue = new KnobSampling<>(defaultValue, getMeasurementProvider().get());
    }

    public KnobSampling<K, M> getBestOrDefault() {

        KnobSampling<K, M> best = getBest();
        return best != null ? best : defaultValue;
    }

    public KnobSampling<K, M> clone(KnobSampling<K, M> version) {

        KnobApplier<K> algorithm = version.getKnob();
        KnobSampling<K, M> best = new KnobSampling<>(algorithm, measurementProvider.get());
        return best;
    }

    public KnobSampling<K, M> cloneBestOrDefault() {
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

}
