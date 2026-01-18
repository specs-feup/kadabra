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

import org.javatuples.Quintet;

import autotuner.configs.knobs.number.ranged.RangedKnob;

public class Linear5D<A extends Number, B extends Number, C extends Number, D extends Number, E extends Number>
        extends LinearInc<Quintet<A, B, C, D, E>> {

    private RangedKnob<A> aRange;
    private RangedKnob<B> bRange;
    private RangedKnob<C> cRange;
    private RangedKnob<D> dRange;
    private RangedKnob<E> eRange;

    public Linear5D(RangedKnob<A> aRange, RangedKnob<B> bRange, RangedKnob<C> cRange, RangedKnob<D> dRange,
            RangedKnob<E> eRange) {
        this.aRange = aRange;
        this.bRange = bRange;
        this.cRange = cRange;
        this.dRange = dRange;
        this.eRange = eRange;
        add(aRange);
        add(bRange);
        add(cRange);
        add(dRange);
        add(eRange);
    }

    @Override
    public Quintet<A, B, C, D, E> next() {
        return Quintet.with(aRange.getValue(), bRange.getValue(), cRange.getValue(), dRange.getValue(),
                eRange.getValue());
    }

    @Override
    public Quintet<A, B, C, D, E> getFirst() {
        return next();
    }

    @Override
    public void setFirst(Quintet<A, B, C, D, E> t) {
        // prepare all knobs
        aRange.setValue(t.getValue0());
        bRange.setValue(t.getValue1());
        cRange.setValue(t.getValue2());
        dRange.setValue(t.getValue3());
        eRange.setValue(t.getValue4());
    }

    // @Override
    // public boolean hasNext(Quintet<A, B, C, D, E> reference) {
    //
    // A a;
    // B b;
    // C c;
    // D d;
    // E e;
    // if (!aRange.inc()) {
    // if (!bRange.inc()) {
    // if (!cRange.inc()) {
    // if (!dRange.inc()) {
    // if (!eRange.inc()) {
    // return false;
    // } else {
    // a = reference.getValue0();
    // b = reference.getValue1();
    // c = reference.getValue2();
    // d = reference.getValue3();
    // e = eRange.getValue();
    // }
    // } else {
    // a = reference.getValue0();
    // b = reference.getValue1();
    // c = reference.getValue2();
    // d = dRange.getValue();
    // e = reference.getValue4();
    // }
    // } else {
    // a = reference.getValue0();
    // b = reference.getValue1();
    // c = cRange.getValue();
    // d = reference.getValue3();
    // e = reference.getValue4();
    // }
    //
    // } else {
    // a = reference.getValue0();
    // b = bRange.getValue();
    // c = reference.getValue2();
    // d = reference.getValue3();
    // e = reference.getValue4();
    // }
    // } else {
    // a = aRange.getValue();
    // b = reference.getValue1();
    // c = reference.getValue2();
    // d = reference.getValue3();
    // e = reference.getValue4();
    // }
    // this.next = Quintet.with(a, b, c, d, e);
    // return true;
    // }
}
