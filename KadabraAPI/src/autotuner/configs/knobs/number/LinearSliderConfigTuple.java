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

package autotuner.configs.knobs.number;

import java.util.ArrayList;
import java.util.List;

import autotuner.configs.Configuration;
import autotuner.configs.knobs.number.ranged.RangedKnob;
import tdrc.tuple.Tuple;

/**
 * The default configuration is a simple exhaustive search that tries all possible values/combinations with a provided
 * step
 * 
 * @author tiago
 *
 *
 *         public class DefaultRangedConfigTuple<T extends Number> implements Configuration<Tuple<T>> {
 * 
 *         List<RangedKnob<T>> rangedList; int lastPos; Tuple<T> next;
 * 
 *         public DefaultRangedConfigTuple(List<RangedKnob<T>> rangedKnobList) { rangedList = rangedKnobList; lastPos =
 *         rangedList.size() - 1; }
 *
 */
public class LinearSliderConfigTuple<T extends Number> implements Configuration<Tuple<T>> {
    private List<Tuple<T>> visited = new ArrayList<>();
    private List<RangedKnob<T>> rangedList;
    // private Tuple<T> next;

    private int pos = 0; // position that will slide

    public LinearSliderConfigTuple(List<RangedKnob<T>> rangedKnobList) {
        this.rangedList = rangedKnobList;
        // first = Tuple.newInstance();
        // rangedList.forEach(k -> first.add(k.getValue()));
    }

    @Override
    public void setFirst(Tuple<T> t) {
        for (int i = 0; i < t.size(); i++) {
            rangedList.get(i).setValue(t.get(i));
        }
    }

    @Override
    public Tuple<T> getFirst() {
        Tuple<T> next = createTuple();
        visited.add(next);
        return next;
    }

    private Tuple<T> createTuple() {
        Tuple<T> next = Tuple.newInstance();
        rangedList.forEach(k -> next.add(k.getValue()));
        return next;
    }

    @Override
    public boolean hasNext(Tuple<T> reference) {
        RangedKnob<T> knob = rangedList.get(pos);
        if (knob.inc()) {
            return true;
        }
        if (pos + 1 >= rangedList.size()) {
            return false; // done!
        }
        knob.setValue(reference.get(pos)); // mark current knob with the reference!

        pos++;
        return hasNext(reference);
    }

    @Override
    public Tuple<T> next() {
        Tuple<T> tuple = createTuple();
        visited.add(tuple);
        return tuple;
    }

    @Override
    public int estimateVersions() {
        int total = 0;
        for (RangedKnob<T> rangedKnob : rangedList) {
            total += rangedKnob.estimateVersions() - rangedList.size();
        }
        return total;
    }

}
