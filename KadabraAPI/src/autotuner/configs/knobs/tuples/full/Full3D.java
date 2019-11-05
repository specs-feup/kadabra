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

import org.javatuples.Triplet;

import autotuner.configs.Configuration;
import autotuner.configs.knobs.number.ranged.RangedKnob;

public class Full3D<L extends Number, M extends Number, R extends Number> implements Configuration<Triplet<L, M, R>> {

    private RangedKnob<L> leftRange;
    private RangedKnob<M> middleRange;
    private RangedKnob<R> rightRange;
    int lastPos;
    private Triplet<L, M, R> next;

    // public Full2D(List<RangedKnob<T>> rangedKnobList) {
    // rangedList = rangedKnobList;
    // lastPos = rangedList.size() - 1;
    // }

    public Full3D(RangedKnob<L> leftRange, RangedKnob<M> middleRange, RangedKnob<R> rightRange) {
        this.leftRange = leftRange;
        this.middleRange = middleRange;
        this.rightRange = rightRange;
    }

    @Override
    public Triplet<L, M, R> next() {
        return next;
    }

    @Override
    public boolean hasNext(Triplet<L, M, R> reference) {

        if (!rightRange.inc()) {
            if (!middleRange.inc()) {
                if (!leftRange.inc()) {
                    return false;
                }
                middleRange.setValue(middleRange.getLowerLimit());
            }
            rightRange.setValue(rightRange.getLowerLimit());
        }
        this.next = Triplet.with(leftRange.getValue(), middleRange.getValue(), rightRange.getValue());
        return true;
    }

    @Override
    public Triplet<L, M, R> getFirst() {
        if (next == null) {
            setFirst(Triplet.with(leftRange.getValue(), middleRange.getValue(), rightRange.getValue()));
        }
        return next;
    }

    @Override
    public void setFirst(Triplet<L, M, R> t) {
        next = t;
        // prepare all knobs
        leftRange.setValue(t.getValue0());
        middleRange.setValue(t.getValue1());
        rightRange.setValue(t.getValue2());
    }

    @Override
    public int estimateVersions() {
        return leftRange.estimateVersions() * middleRange.estimateVersions() * rightRange.estimateVersions();
    }
}
