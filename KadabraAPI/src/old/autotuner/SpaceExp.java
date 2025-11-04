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

import java.util.Iterator;
import java.util.Optional;
import java.util.TreeSet;

public class SpaceExp<K, V> {

    TreeSet<ExpPoint<K, V>> set;

    public SpaceExp() {
        reset();
    }

    private void reset() {
        this.set = new TreeSet<>();
    }

    /**
     * @see java.util.TreeSet#add(Object)
     * @param value
     * @return
     */
    public boolean add(ExpPoint<K, V> value) {
        return set.add(value);
    }
    //
    // public boolean add(K param, Measurement<V> measurement) {
    // ExpPoint<K, V> e = ExpPoint.newInstance(param, measurement);
    // return set.add(e);
    // }

    public void update(K param, V measurement) {

        get(param).ifPresent(ep -> update(measurement, ep));
    }

    private void update(V newMeasurement, ExpPoint<K, V> ep) {
        ep.update(newMeasurement);
        // Reordering the set
        set.remove(ep);
        set.add(ep);
    }

    public Optional<ExpPoint<K, V>> get(K param) {
        return set.stream().filter(ep -> ep.getAlgorithm().equals(param)).findAny();
    }

    public ExpPoint<K, V> getBest() {
        if (set.isEmpty()) {
            return null;
        }
        return set.first();
    }

    public ExpPoint<K, V> getBestOrElse(ExpPoint<K, V> defaultVersion) {
        if (set.isEmpty()) {
            return defaultVersion;
        }
        return set.first();
    }

    public Iterator<ExpPoint<K, V>> iterator() {
        return set.iterator();
    }
}
