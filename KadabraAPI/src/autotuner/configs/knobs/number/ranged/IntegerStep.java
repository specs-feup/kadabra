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

package autotuner.configs.knobs.number.ranged;

public class IntegerStep extends StepKnob<Integer> {

    public IntegerStep(Integer lowerLimit, Integer upperLimit, Integer value, Integer step) {
        super(lowerLimit, upperLimit, value, step);
    }

    public IntegerStep(Integer lowerLimit, Integer upperLimit, Integer step) {
        super(lowerLimit, upperLimit, step);
    }

    @Override
    public boolean inc() {
        if ((value + getStep()) <= upperLimit) {
            value += getStep();
            return true;
        }
        return false;
    }

    @Override
    public boolean dec() {
        if ((value - getStep()) >= lowerLimit) {
            value -= getStep();
            return true;
        }
        return false;
    }

    @Override
    public boolean inBounds(Integer value) {

        return value >= lowerLimit && value <= upperLimit;
    }

    @Override
    public Integer ascend(Integer newValue) {
        return newValue + getStep();
    }

    @Override
    public Integer descend(Integer newValue) {
        return newValue - getStep();
    }

    @Override
    public int estimateVersions() {
        return (upperLimit - value) / getStep() + 1;
    }

    @Override
    public void setValueFromNumber(Number value) {

        if (value instanceof Double || value instanceof Float || value instanceof Long) { // FIXME
            throw new RuntimeException("Cannot set an Integer knob with a type higher than Integer, a "
                    + value.getClass().getSimpleName() + " was given.");
        }
        this.value = value.intValue();
    }
}
