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

import org.javatuples.Pair;

import autotuner.configs.knobs.number.ranged.RangedKnob;

public class Linear2D<L extends Number, R extends Number> extends LinearInc<Pair<L, R>> {

    private RangedKnob<L> leftRange;
    private RangedKnob<R> rightRange;

    public Linear2D(RangedKnob<L> leftRange, RangedKnob<R> rightRange) {
        this.leftRange = leftRange;
        this.rightRange = rightRange;

        add(leftRange);
        add(rightRange);
    }

    @Override
    public Pair<L, R> next() {
        return Pair.with(leftRange.getValue(), rightRange.getValue());
    }

    @Override
    public Pair<L, R> getFirst() {
        return next();
    }

    @Override
    public void setFirst(Pair<L, R> t) {
        // prepare all knobs
        leftRange.setValue(t.getValue0());
        rightRange.setValue(t.getValue1());
    }

    // @Override
    // public boolean hasNext(Pair<L, R> reference) {
    //
    // // L left;
    // // R right;
    // //
    // // if (!leftRange.inc()) {
    // // if (!rightRange.inc()) {
    // // return false;
    // // }
    // // left = reference.getValue0();
    // // right = rightRange.getValue();
    // // } else {
    // // left = leftRange.getValue();
    // // right = reference.getValue1();
    // // }
    // // this.next = Pair.with(left, right);
    // return true;
    // }

}
