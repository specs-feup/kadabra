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

import java.util.List;

import autotuner.configs.Configuration;
import autotuner.configs.knobs.number.ranged.RangedKnob;
import tdrc.tuple.Tuple;

public class DefaultRangedConfigTuple<T extends Number> implements Configuration<Tuple<T>> {

    List<RangedKnob<T>> rangedList;
    int lastPos;
    Tuple<T> next;

    public DefaultRangedConfigTuple(List<RangedKnob<T>> rangedKnobList) {
        rangedList = rangedKnobList;
        lastPos = rangedList.size() - 1;
    }

    @Override
    public Tuple<T> next() {
        return next;
    }

    @Override
    public boolean hasNext(Tuple<T> reference) {

        Tuple<T> next = Tuple.newInstance();
        RangedKnob<T> lastKnob = rangedList.get(lastPos);

        if (!lastKnob.inc()) {
            lastKnob.setValue(lastKnob.getLowerLimit());
            int pos = lastPos - 1;
            while (pos >= 0) {
                lastKnob = rangedList.get(pos);
                if (lastKnob.inc()) {
                    break;
                }
                lastKnob.setValue(lastKnob.getLowerLimit());
                pos--;
            }
            if (pos < 0) {
                return false;
            }
        }
        rangedList.forEach(k -> next.add(k.getValue()));
        this.next = next;
        return true;
    }

    @Override
    public Tuple<T> getFirst() {
        return next;
    }

    @Override
    public void setFirst(Tuple<T> t) {
        next = t;
        for (int i = 0; i < t.size(); i++) {
            rangedList.get(i).setValue(t.get(i)); // prepare all knobs
        }
    }

    @Override
    public int estimateVersions() {
        int total = 1;
        for (RangedKnob<T> rangedKnob : rangedList) {
            total *= rangedKnob.estimateVersions();
        }
        return total;
    }
}
