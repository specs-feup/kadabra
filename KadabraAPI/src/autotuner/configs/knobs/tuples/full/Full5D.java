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

package autotuner.configs.knobs.tuples.full;

import org.javatuples.Quintet;

import autotuner.configs.Configuration;
import autotuner.configs.knobs.number.ranged.RangedKnob;

public class Full5D<A extends Number, B extends Number, C extends Number, D extends Number, E extends Number>
        implements Configuration<Quintet<A, B, C, D, E>> {

    private RangedKnob<A> aRange;
    private RangedKnob<B> bRange;
    private RangedKnob<C> cRange;
    private RangedKnob<D> dRange;
    private RangedKnob<E> eRange;
    int lastPos;
    private Quintet<A, B, C, D, E> next;

    // public Full2D(List<RangedKnob<T>> rangedKnobList) {
    // rangedList = rangedKnobList;
    // lastPos = rangedList.size() - 1;
    // }

    public Full5D(RangedKnob<A> aRange, RangedKnob<B> bRange, RangedKnob<C> cRange, RangedKnob<D> dRange,
            RangedKnob<E> eRange) {
        this.aRange = aRange;
        this.bRange = bRange;
        this.cRange = cRange;
        this.dRange = dRange;
        this.eRange = eRange;
    }

    @Override
    public Quintet<A, B, C, D, E> next() {
        return next;
    }

    @Override
    public boolean hasNext(Quintet<A, B, C, D, E> reference) {

        if (!eRange.inc()) {
            if (!dRange.inc()) {
                if (!cRange.inc()) {
                    if (!bRange.inc()) {
                        if (!aRange.inc()) {
                            return false;
                        }
                        bRange.setValue(bRange.getLowerLimit());
                    }
                    cRange.setValue(cRange.getLowerLimit());
                }
                dRange.setValue(dRange.getLowerLimit());
            }
            eRange.setValue(eRange.getLowerLimit());
        }
        this.next = Quintet.with(aRange.getValue(), bRange.getValue(), cRange.getValue(), dRange.getValue(),
                eRange.getValue());
        return true;
    }

    @Override
    public Quintet<A, B, C, D, E> getFirst() {
        if (next == null) {
            setFirst(Quintet.with(aRange.getValue(), bRange.getValue(), cRange.getValue(), dRange.getValue(),
                    eRange.getValue()));
        }
        return next;
    }

    @Override
    public void setFirst(Quintet<A, B, C, D, E> t) {
        next = t;
        // prepare all knobs
        aRange.setValue(t.getValue0());
        bRange.setValue(t.getValue1());
        cRange.setValue(t.getValue2());
        dRange.setValue(t.getValue3());
        eRange.setValue(t.getValue4());
    }

    @Override
    public int estimateVersions() {
        return aRange.estimateVersions() * bRange.estimateVersions() * cRange.estimateVersions()
                * dRange.estimateVersions() * eRange.estimateVersions();
    }
}
