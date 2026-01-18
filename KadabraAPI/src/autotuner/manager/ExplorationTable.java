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

import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiFunction;

import tdrc.utils.StringUtils;

/**
 * Table representing the exploration of knobs and algorithms based on a given measurement
 * 
 * @author tiago
 *
 * @param <D>
 *            Data feature: The values of the input set that will be used as key
 * @param <A>
 *            The parameters/algorithms that will be used as knobs for exploration
 * @param <M>
 *            The measurement type (e.g. time and/or energy)
 */
public class ExplorationTable<D extends Comparable<D>, A, M> {

    private TreeMap<D, ExplorationManager<A, M>> table;

    private BiFunction<D, D, Double> distanceProvider;
    // private AlgorithmListProvider<A> algorithmListProvider;

    public ExplorationTable() {
        reset();
        // setAlgorithmListProvider(new AlgorithmListProvider<>());
    }

    public ExplorationManager<A, M> add(D dataSet, ExplorationManager<A, M> exploration) {
        return table.put(dataSet, exploration);
    }

    public ExplorationManager<A, M> newExploration(D dataSet, ExplorationManager<A, M> exploration) {
        return table.put(dataSet, exploration);
    }

    public Entry<D, ExplorationManager<A, M>> getClosest(D key) {
        Entry<D, ExplorationManager<A, M>> low = table.floorEntry(key);
        Entry<D, ExplorationManager<A, M>> high = table.ceilingEntry(key);
        if (low != null && high != null) {
            double dLow = distanceProvider.apply(key, low.getKey());
            double dHigh = distanceProvider.apply(high.getKey(), key);
            return dLow <= dHigh ? low : high;
        } else if (low != null || high != null) {

            return low != null ? low : high;
        }
        return null;
    }

    public boolean contains(D key) {
        return table.containsKey(key);
    }

    public ExplorationManager<A, M> get(D key) {
        return table.get(key);
    }

    public ExplorationManager<A, M> getValueOrClosest(D key) {
        if (contains(key)) {
            return table.get(key);
        }
        Entry<D, ExplorationManager<A, M>> closest = getClosest(key);
        return closest != null ? closest.getValue() : null;
    }

    public void reset() {
        table = new TreeMap<>();
    }

    @Override
    public String toString() {
        return StringUtils.join(table.entrySet(), "\n\n");
    }
    //
    // public AlgorithmListProvider<A> getAlgorithmListProvider() {
    // return algorithmListProvider;
    // }
    //
    // public void setAlgorithmListProvider(AlgorithmListProvider<A> algorithmListProvider) {
    // this.algorithmListProvider = algorithmListProvider;
    // }

    public void pause() {
        table.forEach((key, exp) -> {
            if (exp.getMode() != ExpMode.BEST) {
                exp.pause();
            }
        });
    }

    public void resume() {
        table.forEach((key, exp) -> {
            if (exp.getMode() != ExpMode.BEST) {
                exp.setMode(ExpMode.SAMPLING);
            }
        });
    }

    public BiFunction<D, D, Double> getDistanceProvider() {
        return distanceProvider;
    }

    public void setDistanceProvider(BiFunction<D, D, Double> distanceProvider) {
        this.distanceProvider = distanceProvider;
    }

    public Set<D> keySet() {
        return this.table.keySet();
    }
}
