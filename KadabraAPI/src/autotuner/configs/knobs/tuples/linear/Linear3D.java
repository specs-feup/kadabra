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

package autotuner.configs.knobs.tuples.linear;

import org.javatuples.Triplet;

import autotuner.configs.knobs.number.ranged.RangedKnob;

public class Linear3D<L extends Number, M extends Number, R extends Number> extends LinearInc<Triplet<L, M, R>> {

    private RangedKnob<L> leftRange;
    private RangedKnob<M> middleRange;
    private RangedKnob<R> rightRange;

    public Linear3D(RangedKnob<L> leftRange, RangedKnob<M> middleRange, RangedKnob<R> rightRange) {
        this.leftRange = leftRange;
        this.middleRange = middleRange;
        this.rightRange = rightRange;
        add(leftRange);
        add(middleRange);
        add(rightRange);
    }

    @Override
    public Triplet<L, M, R> next() {
        return Triplet.with(leftRange.getValue(), middleRange.getValue(), rightRange.getValue());
    }

    //

    @Override
    public Triplet<L, M, R> getFirst() {
        return next();
    }

    @Override
    public void setFirst(Triplet<L, M, R> t) {
        // prepare all knobs
        leftRange.setValue(t.getValue0());
        middleRange.setValue(t.getValue1());
        rightRange.setValue(t.getValue2());
    }

    // @Override
    // public boolean hasNext(Triplet<L, M, R> reference) {
    // L left;
    // M middle;
    // R right;
    // if (!leftRange.inc()) {
    // if (!middleRange.inc()) {
    // if (!rightRange.inc()) {
    // return false;
    // } else {
    // left = reference.getValue0();
    // middle = reference.getValue1();
    // right = rightRange.getValue();
    // }
    //
    // } else {
    // left = reference.getValue0();
    // middle = middleRange.getValue();
    // right = reference.getValue2();
    // }
    // } else {
    // left = leftRange.getValue();
    // middle = reference.getValue1();
    // right = reference.getValue2();
    // }
    // this.next = Triplet.with(left, middle, right);
    // return true;
    // }
}
