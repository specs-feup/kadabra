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

package autotuner.manager;

import java.util.Iterator;
import java.util.Optional;
import java.util.TreeSet;

import autotuner.algorithm.Algorithm;
import autotuner.measurer.Measurer;

/**
 * The exploration of a set of algorithms, ordered from the best to the worst
 * 
 * @author tiago
 *
 * @param <A>
 * @param <M>
 */
public class AlgorithmsExploration<A, M> {

    /**
     * Orders the explorations from the best to the worst
     */
    private TreeSet<AlgorithmSampling<A, M>> set;

    public AlgorithmsExploration() {
        reset();
    }

    public void reset() {
        this.set = new TreeSet<>();
    }

    /**
     * @see java.util.TreeSet#add(Object)
     * @param value
     * @return
     */
    public boolean add(AlgorithmSampling<A, M> value) {
        return set.add(value);
    }

    public boolean add(Algorithm<A> param, Measurer<M> measurer) {
        return set.add(new AlgorithmSampling<>(param, measurer));
    }

    public void update(Algorithm<A> param, M measurement) {

        get(param).ifPresent(ep -> update(measurement, ep));
    }

    private void update(M newMeasurement, AlgorithmSampling<A, M> ep) {
        ep.update(newMeasurement);
        // Reordering the set
        set.remove(ep);
        set.add(ep);
    }

    public Optional<AlgorithmSampling<A, M>> get(Algorithm<A> param) {
        return set.stream().filter(ep -> ep.getAlgorithm().equals(param)).findAny();
    }

    public AlgorithmSampling<A, M> getBest() {
        return set.isEmpty() ? null : set.first();
    }

    public int getSize() {
        return set.size();

    }

    // public <B extends AlgorithmSampling<A, V>> B getBest(Class<B> toSearch) {
    //
    // for (AlgorithmSampling<A, V> algorithmSampling : set) {
    // if (toSearch.isInstance(algorithmSampling)) {
    // return toSearch.cast(algorithmSampling);
    // }
    // }
    // return null;
    // }

    /**
     * Get the best version for a given type of algorithm
     * 
     * @param toSearch
     * @return
     */
    public AlgorithmSampling<A, M> getBest(Class<A> toSearch) {

        for (AlgorithmSampling<A, M> algorithmSampling : set) {
            if (toSearch.isInstance(algorithmSampling.getAlgorithm())) {
                return algorithmSampling;
            }
        }
        return null;
    }

    public AlgorithmSampling<A, M> getBestOrElse(AlgorithmSampling<A, M> defaultVersion) {
        if (set.isEmpty()) {
            return defaultVersion;
        }
        return set.first();
    }

    public Iterator<AlgorithmSampling<A, M>> iterator() {
        return set.iterator();
    }

    /**
     * Force an reinsertion of the best as the measurements may be different
     * 
     * @param best
     */
    public void updateBest() {
        AlgorithmSampling<A, M> best = set.pollFirst();
        // set.remove(best);
        if (best != null) {
            set.add(best);
        }
    }

    /**
     * Populate the given measurer with all the experiences done
     * 
     * @param measurer
     */
    public void totalMeasurements(Measurer<M> measurer) {
        int runs = 0;
        int warmups = 0;
        for (AlgorithmSampling<A, M> algorithmSampling : set) {
            Measurer<M> algMeasurer = algorithmSampling.getMeasurer();
            measurer.update(algMeasurer.getValue());
            runs += algMeasurer.getNumTests();
            warmups += algMeasurer.getNumWarmup();
        }
        measurer.setNumTests(runs);
        measurer.setNumWarmup(warmups);
    }
}
