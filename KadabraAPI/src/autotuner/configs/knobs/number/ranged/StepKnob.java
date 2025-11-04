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

/**
 * A knob with a specific step to climb/descend
 * 
 * @author tdrc
 *
 * @param <T>
 */
public abstract class StepKnob<T extends Number> implements RangedKnob<T> {

    protected T upperLimit;
    protected T lowerLimit;
    protected T value;
    private T step;

    public StepKnob(T lowerLimit, T upperLimit, T step) {
        this(lowerLimit, upperLimit, lowerLimit, step);
    }

    public StepKnob(T lowerLimit, T upperLimit, T value, T step) {
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.value = value;
        this.setStep(step);
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

    /**
     * @param value
     *            the value to set
     */
    @Override
    public void setValue(T value) {
        this.value = value;
    }

    public T getStep() {
        return step;
    }

    public void setStep(T step) {
        this.step = step;
    }

}
