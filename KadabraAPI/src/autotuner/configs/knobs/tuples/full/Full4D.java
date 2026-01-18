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

import org.javatuples.Quartet;

import autotuner.configs.Configuration;
import autotuner.configs.knobs.number.ranged.RangedKnob;

public class Full4D<A extends Number, B extends Number, C extends Number, D extends Number>
        implements Configuration<Quartet<A, B, C, D>> {

    private RangedKnob<A> aRange;
    private RangedKnob<B> bRange;
    private RangedKnob<C> cRange;
    private RangedKnob<D> dRange;
    int lastPos;
    private Quartet<A, B, C, D> next;

    // public Full2D(List<RangedKnob<T>> rangedKnobList) {
    // rangedList = rangedKnobList;
    // lastPos = rangedList.size() - 1;
    // }

    public Full4D(RangedKnob<A> aRange, RangedKnob<B> bRange, RangedKnob<C> cRange, RangedKnob<D> dRange) {
        this.aRange = aRange;
        this.bRange = bRange;
        this.cRange = cRange;
        this.dRange = dRange;
    }

    @Override
    public Quartet<A, B, C, D> next() {
        return next;
    }

    @Override
    public boolean hasNext(Quartet<A, B, C, D> reference) {

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
        this.next = Quartet.with(aRange.getValue(), bRange.getValue(), cRange.getValue(), dRange.getValue());
        return true;
    }

    @Override
    public Quartet<A, B, C, D> getFirst() {
        if (next == null) {
            setFirst(Quartet.with(aRange.getValue(), bRange.getValue(), cRange.getValue(), dRange.getValue()));
        }
        return next;
    }

    @Override
    public void setFirst(Quartet<A, B, C, D> t) {
        next = t;
        // prepare all knobs
        aRange.setValue(t.getValue0());
        bRange.setValue(t.getValue1());
        cRange.setValue(t.getValue2());
        dRange.setValue(t.getValue3());
    }

    @Override
    public int estimateVersions() {
        return aRange.estimateVersions() * bRange.estimateVersions() * cRange.estimateVersions()
                * dRange.estimateVersions();
    }
}
