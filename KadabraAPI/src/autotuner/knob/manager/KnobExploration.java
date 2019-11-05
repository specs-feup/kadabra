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
import java.util.Optional;
import java.util.TreeSet;

import autotuner.knob.Knob;
import autotuner.knob.KnobApplier;
import autotuner.measurer.Measurer;

/**
 * The exploration of a set of algorithms, ordered from the best to the worst
 * 
 * @author tiago
 *
 * @param <K>
 * @param <M>
 */
public class KnobExploration<K, M> {

    /**
     * Orders the explorations from the best to the worst
     */
    private TreeSet<KnobSampling<K, M>> set;

    public KnobExploration() {
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
    public boolean add(KnobSampling<K, M> value) {
        return set.add(value);
    }

    public boolean add(KnobApplier<K> param, Measurer<M> measurer) {
        return set.add(new KnobSampling<>(param, measurer));
    }

    public void update(Knob<K> param, M measurement) {

        get(param).ifPresent(ep -> update(measurement, ep));
    }

    private void update(M newMeasurement, KnobSampling<K, M> ep) {
        ep.update(newMeasurement);
        // Reordering the set
        set.remove(ep);
        set.add(ep);
    }

    public Optional<KnobSampling<K, M>> get(Knob<K> param) {
        return set.stream().filter(ep -> ep.getKnobValue().equals(param)).findAny();
    }

    public KnobSampling<K, M> getBest() {
        return set.isEmpty() ? null : set.first();
    }

    public int getSize() {
        return set.size();

    }

    public KnobSampling<K, M> getBestOrElse(KnobSampling<K, M> defaultVersion) {
        if (set.isEmpty()) {
            return defaultVersion;
        }
        return set.first();
    }

    public Iterator<KnobSampling<K, M>> iterator() {
        return set.iterator();
    }

    /**
     * Force an reinsertion of the best as the measurements may be different
     * 
     * @param best
     */
    public void updateBest() {
        KnobSampling<K, M> best = set.pollFirst();
        // set.remove(best);
        if (best != null) {
            set.add(best);
        }
    }
}
