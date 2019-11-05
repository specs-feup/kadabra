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

package autotuner.configs.knobs.tuples.full;

import org.javatuples.Pair;

import autotuner.configs.Configuration;
import autotuner.configs.knobs.number.ranged.RangedKnob;

public class Full2D<L extends Number, R extends Number> implements Configuration<Pair<L, R>> {

    private RangedKnob<L> leftRange;
    private RangedKnob<R> rightRange;
    int lastPos;
    private Pair<L, R> next;

    // public Full2D(List<RangedKnob<T>> rangedKnobList) {
    // rangedList = rangedKnobList;
    // lastPos = rangedList.size() - 1;
    // }

    public Full2D(RangedKnob<L> leftRange, RangedKnob<R> rightRange) {
        this.leftRange = leftRange;
        this.rightRange = rightRange;
    }

    @Override
    public Pair<L, R> next() {
        return next;
    }

    @Override
    public boolean hasNext(Pair<L, R> reference) {

        if (!rightRange.inc()) {
            if (!leftRange.inc()) {
                return false;
            }
            rightRange.setValue(rightRange.getLowerLimit());
        }
        this.next = Pair.with(leftRange.getValue(), rightRange.getValue());
        return true;
    }

    @Override
    public Pair<L, R> getFirst() {
        if (next == null) {
            setFirst(Pair.with(leftRange.getValue(), rightRange.getValue()));
        }
        return next;
    }

    @Override
    public void setFirst(Pair<L, R> t) {
        next = t;
        // prepare all knobs
        leftRange.setValue(t.getValue0());
        rightRange.setValue(t.getValue1());
    }

    @Override
    public int estimateVersions() {
        return leftRange.estimateVersions() * rightRange.estimateVersions();
    }
}
