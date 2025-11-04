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
 * specific language governing permissions and limitations under the License.
 */

package old.autotuner;

import autotuner.algorithm.Algorithm;
import autotuner.measurer.Measurer;

/**
 * Represents an exploration point and uses the measurement in the compareTo method.
 * 
 * @author tiago
 *
 * @param <K>
 * @param <V>
 */
public class ExpPoint<A, V> implements Comparable<ExpPoint<Algorithm<A>, V>> {

    private Algorithm<A> algorithm;
    private Measurer<V> measurement;

    public ExpPoint(Algorithm<A> p, Measurer<V> m) {
        algorithm = p;
        this.measurement = m;
    }

    public static <A, V> ExpPoint<A, V> newInstance(Algorithm<A> p, Measurer<V> m) {
        return new ExpPoint<>(p, m);
    }

    @Override
    public int compareTo(ExpPoint<Algorithm<A>, V> o) {
        return measurement.compareTo(o.measurement);
    }

    /**
     * @return the params
     */
    public Algorithm<A> getAlgorithm() {
        return algorithm;
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
    public Measurer<V> getMeasurement() {
        return measurement;
    }

    /**
     * @param measurement
     *            the measurement to set
     */
    public void setMeasurement(Measurer<V> measurement) {
        this.measurement = measurement;
    }

    /**
     * Update this point with a new measurement
     * 
     * @param measurement
     * @return total number of tests
     */
    public int update(V measurement) {
        return this.measurement.update(measurement);
    }

    /**
     * Warmup this point with a new measurement
     * 
     * @param measurement
     * @return total number of warmups
     */
    public int warmup(V measurement) {
        return this.measurement.warmup(measurement);
    }

    /**
     * @return the numTests
     */
    public int getNumTests() {
        return this.measurement.getNumTests();
    }

    /**
     * @return the numWarmup
     */
    public int getNumWarmup() {
        return this.measurement.getNumWarmup();
    }

    @Override
    public String toString() {
        return algorithm + " @ " + measurement;
    }
}
