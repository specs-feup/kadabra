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

import autotuner.knob.KnobApplier;
import autotuner.measurer.Measurer;

/**
 * Represents an exploration, i.e. measurement, over a given algorithm
 * 
 * @author tiago
 *
 * @param <A>
 *            The algorithm to sample
 * @param <M>
 *            The type of the measurer to use for sampling
 */
public class KnobSampling<K, M> implements Comparable<KnobSampling<K, M>> {

    private KnobApplier<K> knob;
    private Measurer<M> measurer;
    private ExplorationManager<K, M> manager;
    // private int numTests;
    // private int numWarmup;

    public KnobSampling(KnobApplier<K> knob, Measurer<M> m) {
        this(knob, m, null);
    }

    public KnobSampling(KnobApplier<K> knob, Measurer<M> m, ExplorationManager<K, M> manager) {
        this.knob = knob;
        this.measurer = m;
        this.manager = manager;
    }

    public static <K, V> KnobSampling<K, V> newInstance(KnobApplier<K> knob, Measurer<V> m) {
        return new KnobSampling<>(knob, m);
    }

    public static <K, M> KnobSampling<K, M> newInstance(KnobApplier<K> knob, Measurer<M> m,
            ExplorationManager<K, M> manager) {
        return new KnobSampling<>(knob, m, manager);
    }

    @Override
    public int compareTo(KnobSampling<K, M> o) {
        return measurer.compareTo(o.measurer);
    }

    public String getID() {
        return knob.getID();
    }

    /**
     * Applies the values of the knob
     * 
     */
    public void apply() {
        knob.apply();
    }

    /**
     * Returns the value of the knob
     * 
     * @return the knob value(s)
     */
    public K getKnobValue() {
        return knob.getKnob();
    }

    public K applyAndGet() {
        apply();
        return getKnobValue();
    }

    /**
     * @return the measurement
     */
    public Measurer<M> getMeasurer() {
        return measurer;
    }

    /**
     * @param measurer
     *            the measurement to set
     */
    public void setMeasurer(Measurer<M> measurer) {
        this.measurer = measurer;
    }

    public void sample(M measurement) {
        this.measurer.update(measurement);
        // numTests++;
    }

    public void warmup(M measurement) {
        this.measurer.warmup(measurement);
        // numWarmup++;
    }

    /**
     * Decides if this measurement should be a warmup or a measurement
     *
     * @param measurement
     */
    public void update(M measurement) {
        if (manager == null) {
            throw new NullPointerException(
                    "Algorithm sampling requires a manager for the update. Use warmup/sample if you are not using a manager.");
        }
        if (getNumWarmup() < manager.getNumWarmup()) {
            warmup(measurement);
        } else {
            sample(measurement);
        }
    }

    /**
     * @return the numTests
     */
    public int getNumTests() {
        return measurer.getNumTests();
    }

    /**
     * @return the numWarmup
     */
    public int getNumWarmup() {
        return measurer.getNumWarmup();
    }

    @Override
    public String toString() {
        return knob + " @ " + measurer;
    }

    public ExplorationManager<K, M> getManager() {
        return manager;
    }

    public void setManager(ExplorationManager<K, M> manager) {
        this.manager = manager;
    }
}
