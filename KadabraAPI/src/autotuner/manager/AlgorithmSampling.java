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

import autotuner.algorithm.Algorithm;
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
public class AlgorithmSampling<A, M> implements Comparable<AlgorithmSampling<A, M>> {

    private Algorithm<A> algorithm;
    private Measurer<M> measurer;
    private ExplorationManager<A, M> manager;
    // private int numTests;
    // private int numWarmup;

    public AlgorithmSampling(Algorithm<A> algorithm, Measurer<M> m) {
        this(algorithm, m, null);
    }

    public AlgorithmSampling(Algorithm<A> algorithm, Measurer<M> m, ExplorationManager<A, M> manager) {
        this.algorithm = algorithm;
        this.measurer = m;
        this.manager = manager;
    }

    public static <A, V> AlgorithmSampling<A, V> newInstance(Algorithm<A> algorithm, Measurer<V> m) {
        return new AlgorithmSampling<>(algorithm, m);
    }

    public static <A, M> AlgorithmSampling<A, M> newInstance(Algorithm<A> algorithm, Measurer<M> m,
            ExplorationManager<A, M> manager) {
        return new AlgorithmSampling<>(algorithm, m, manager);
    }

    @Override
    public int compareTo(AlgorithmSampling<A, M> o) {
        return measurer.compareTo(o.measurer);
    }

    /**
     * @return the {@link Algorithm} instance
     */
    public Algorithm<A> getAlgorithm() {
        return algorithm;
    }

    public String getID() {
        return algorithm.getID();
    }

    /**
     * Applies the changes the algorithm needs and returns the algorithm
     * 
     * @return the algorithm itself
     */
    public A applyAndGet() {
        return algorithm.applyAndGet();
    }

    /**
     * Applies the changes the algorithm needs
     * 
     */
    public void apply() {
        algorithm.apply();
    }

    /**
     * Returns the algorithm
     * 
     * @return the algorithm itself
     */
    public A get() {
        return algorithm.get();
    }

    /**
     * @param params
     *            the params to set
     */
    public void setAlgorithm(Algorithm<A> algorithm) {
        this.algorithm = algorithm;
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
        return algorithm + " @ " + measurer;
    }

    public ExplorationManager<A, M> getManager() {
        return manager;
    }

    public void setManager(ExplorationManager<A, M> manager) {
        this.manager = manager;
    }
}
