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

package autotuner.configs.knobs.tuples.linear;

import org.javatuples.Quartet;

import autotuner.configs.knobs.number.ranged.RangedKnob;

public class Linear4D<A extends Number, B extends Number, C extends Number, D extends Number>
        extends LinearInc<Quartet<A, B, C, D>> {

    private RangedKnob<A> aRange;
    private RangedKnob<B> bRange;
    private RangedKnob<C> cRange;
    private RangedKnob<D> dRange;

    public Linear4D(RangedKnob<A> aRange, RangedKnob<B> bRange, RangedKnob<C> cRange, RangedKnob<D> dRange) {
        this.aRange = aRange;
        this.bRange = bRange;
        this.cRange = cRange;
        this.dRange = dRange;
        add(aRange);
        add(bRange);
        add(cRange);
        add(dRange);
    }

    @Override
    public Quartet<A, B, C, D> next() {
        return Quartet.with(aRange.getValue(), bRange.getValue(), cRange.getValue(), dRange.getValue());
    }

    @Override
    public Quartet<A, B, C, D> getFirst() {
        return next();
    }

    @Override
    public void setFirst(Quartet<A, B, C, D> t) {
        // prepare all knobs
        aRange.setValue(t.getValue0());
        bRange.setValue(t.getValue1());
        cRange.setValue(t.getValue2());
        dRange.setValue(t.getValue3());
    }
    // @Override
    // public boolean hasNext(Quartet<A, B, C, D> reference) {
    //
    // A a;
    // B b;
    // C c;
    // D d;
    // if (!aRange.inc()) {
    // if (!bRange.inc()) {
    // if (!cRange.inc()) {
    // if (!dRange.inc()) {
    // return false;
    // } else {
    // a = reference.getValue0();
    // b = reference.getValue1();
    // c = reference.getValue2();
    // d = dRange.getValue();
    // }
    // } else {
    // a = reference.getValue0();
    // b = reference.getValue1();
    // c = cRange.getValue();
    // d = reference.getValue3();
    // }
    //
    // } else {
    // a = reference.getValue0();
    // b = bRange.getValue();
    // c = reference.getValue2();
    // d = reference.getValue3();
    // }
    // } else {
    // a = aRange.getValue();
    // b = reference.getValue1();
    // c = reference.getValue2();
    // d = reference.getValue3();
    // }
    // this.next = Quartet.with(a, b, c, d);
    // return true;
    // }
}
