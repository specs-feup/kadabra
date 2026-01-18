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

package autotuner.configs.knobs.number.ranged;

import java.util.function.Function;

public abstract class CustomStepKnob<T extends Number> implements RangedKnob<T> {

    protected T upperLimit;
    protected T lowerLimit;
    protected T value;
    protected Function<T, T> descend;
    protected Function<T, T> ascend;

    public CustomStepKnob(T lowerLimit, T upperLimit, Function<T, T> descend, Function<T, T> ascend) {
        this(lowerLimit, upperLimit, lowerLimit, descend, ascend);
    }

    public CustomStepKnob(T lowerLimit, T upperLimit, T value, Function<T, T> descend, Function<T, T> ascend) {
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.value = value;
        this.ascend = ascend;
        this.descend = descend;
    }

    @Override
    public T getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(T upperLimit) {
        this.upperLimit = upperLimit;
    }

    @Override
    public T getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(T lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    /**
     * @return the value
     */
    @Override
    public T getValue() {
        return value;
    }

}
