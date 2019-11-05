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

package autotuner.configs.knobs.number;

import autotuner.configs.Configuration;
import autotuner.configs.knobs.number.ranged.RangedKnob;

public class DefaultRangedConfig<T extends Number> implements Configuration<T> {

    RangedKnob<T> ranged;

    public DefaultRangedConfig(RangedKnob<T> rangedKnob) {
        this.ranged = rangedKnob;
    }

    @Override
    public T next() {
        return ranged.getValue();
    }

    @Override
    public boolean hasNext(T reference) {
        return ranged.inc();
    }

    @Override
    public T getFirst() {
        return ranged.getValue();
    }

    @Override
    public void setFirst(T t) {
        ranged.setValue(t);
    }

    @Override
    public int estimateVersions() {
        return ranged.estimateVersions();
    }

}
