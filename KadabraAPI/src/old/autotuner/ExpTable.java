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

import java.util.Map.Entry;
import java.util.TreeMap;

import tdrc.utils.StringUtils;

/**
 * Table representing the exploration of knobs and algorithms based on a given measurement
 * 
 * @author tiago
 *
 * @param <D>
 *            The values of the input set that will be used as key
 * @param <K>
 *            The parameters/algorithms that will be used as knobs for exploration
 * @param <V>
 *            The measurement type (e.g. time and/or energy)
 */
public class ExpTable<D extends Comparable<D>, K, V> {

    TreeMap<D, OLDSEManager<K, V>> table;

    public ExpTable() {
        reset();
    }

    public OLDSEManager<K, V> add(D dataSet, OLDSEManager<K, V> exploration) {
        return table.put(dataSet, exploration);
    }

    public Entry<D, OLDSEManager<K, V>> getClosest(D key) {
        Entry<D, OLDSEManager<K, V>> low = table.floorEntry(key);
        Entry<D, OLDSEManager<K, V>> high = table.ceilingEntry(key);

        if (low != null && high != null) {
            int dLow = key.compareTo(low.getKey());
            int dHigh = high.getKey().compareTo(key);
            return dLow <= dHigh ? low : high;
        } else if (low != null || high != null) {

            return low != null ? low : high;
        }
        return null;
    }

    public void reset() {
        table = new TreeMap<>();
    }

    @Override
    public String toString() {
        return StringUtils.join(table.entrySet(), "\n\n");
    }
}
