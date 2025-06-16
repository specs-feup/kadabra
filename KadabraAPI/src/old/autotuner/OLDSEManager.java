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

package old.autotuner;

import java.util.Iterator;
import java.util.function.Supplier;

import autotuner.configs.Configuration;
import autotuner.manager.ExpMode;
import autotuner.measurer.Measurer;

public class OLDSEManager<K, V> {

    private SpaceExp<K, V> exploration;
    private Supplier<Measurer<V>> measurementProvider;

    private ExpMode mode;
    private Configuration<K> configuration;
    private ExpPoint<K, V> current;
    private int numWarmup;

    public OLDSEManager(Configuration<K> configuration) {
        this(0, 1, configuration);
    }

    public OLDSEManager(int numWarmup, int numTests, Configuration<K> configuration) {
        reset();
        this.numWarmup = numWarmup;
        this.configuration = configuration;
    }

    private void reset() {
        exploration = new SpaceExp<>();
        mode = ExpMode.STOPPED;
    }

    public Supplier<Measurer<V>> getMeasurementProvider() {
        return measurementProvider;
    }

    public void setMeasurementProvider(Supplier<Measurer<V>> measurementProvider) {
        this.measurementProvider = measurementProvider;
    }

    // public ExpPoint<K, V> getVersion() {
    // switch (mode) {
    // case STOPPED:
    // case BEST:
    // return getBest();
    // case SAMPLING:
    // case RESAMPLING:
    // return getTest();
    // default:
    // throw new RuntimeException("Unknown mode: " + mode);
    // }
    //
    // }
    //
    // private ExpPoint<K, V> getTest() {
    // if (current == null) {
    // if (first) {
    // current = new ExpPoint<>(configuration.getFirst(), measurementProvider.get());
    // first = false;
    // return current;
    // }
    // ExpPoint<K, V> best = getBest();
    // if (best == null) {
    // throw new RuntimeException(
    // "This must not happen! Exploration with both current and best as null and it is not the first execution???");
    // }
    // if (configuration.hasNext(null, best.getAlgorithm())) {
    // current = new ExpPoint<>(configuration.next(), measurementProvider.get());
    // }
    // }
    // if (current.getNumTests() >= numTests) {
    // exploration.add(current);
    // ExpPoint<K, V> best = getBest();
    // if (configuration.hasNext(current.getAlgorithm(), best.getAlgorithm())) {
    // current = new ExpPoint<>(configuration.next(), measurementProvider.get());
    // } else {
    // mode = ExpMode.BEST;
    // }
    //
    // }
    // return current;
    // }

    public ExpPoint<K, V> getBest() {
        return exploration.getBest();
    }

    public void update(V measurement) {
        ExpPoint<K, V> toUpdate = current;
        switch (mode) {
        case STOPPED:
        case BEST:
            toUpdate = getBest();
        }

        if (toUpdate.getNumWarmup() >= numWarmup) {
            toUpdate.warmup(measurement);
        } else {
            toUpdate.update(measurement);
        }
    }

    /**
     * @return the configuration
     */
    public Configuration<K> getConfiguration() {
        return configuration;
    }

    /**
     * @param configuration
     *            the configuration to set
     */
    public void setConfiguration(Configuration<K> configuration) {
        this.configuration = configuration;
    }

    /**
     * @return the mode
     */
    public ExpMode getMode() {
        return mode;
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
        String string = "{\n    MODE: " + mode;
        ExpPoint<K, V> best = exploration.getBestOrElse(null);
        if (best != null) {
            string += "\n    BEST: " + best;
        }
        if (current != null && mode.equals(ExpMode.SAMPLING)) {
            string += "\n    TEST: " + current;
        }
        Iterator<ExpPoint<K, V>> iterator = exploration.iterator();

        if (iterator.hasNext()) {

            ExpPoint<K, V> next = iterator.next();
            String versions = "[ " + next;
            int i = 1;
            while (iterator.hasNext() && i < 5) {
                next = iterator.next();
                versions += " | " + next;
                i++;
            }
            if (i == 5) {
                string += "\n    Explored(Top 5): " + versions + " ]";
            } else {
                string += "\n    Explored: " + versions + " ]";
            }
        }

        string += "\n  }";
        return string;
    }
}
