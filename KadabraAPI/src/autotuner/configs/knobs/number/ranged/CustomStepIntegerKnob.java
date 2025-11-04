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

public class CustomStepIntegerKnob extends CustomStepKnob<Integer> {

    public CustomStepIntegerKnob(Integer lowerLimit, Integer upperLimit, Function<Integer, Integer> descend,
            Function<Integer, Integer> ascend) {
        super(lowerLimit, upperLimit, descend, ascend);
    }

    public CustomStepIntegerKnob(Integer lowerLimit, Integer upperLimit, Integer value,
            Function<Integer, Integer> descend,
            Function<Integer, Integer> ascend) {
        super(lowerLimit, upperLimit, value, descend, ascend);
    }

    @Override
    public boolean inc() {
        int newValue = ascend.apply(value);
        if (newValue <= upperLimit) {
            value = newValue;
            return true;
        }
        return false;
    }

    @Override
    public boolean dec() {
        int newValue = descend.apply(value);
        if (newValue >= lowerLimit) {
            value = newValue;
            return true;
        }
        return false;
    }

    /**
     * @param value
     *            the value to set
     */
    @Override
    public void setValue(Integer value) {
        if (value < lowerLimit || value > upperLimit) {
            throw new RuntimeException("Out of bounds for ranged knob! Expected value between [" + lowerLimit + ","
                    + upperLimit + "] but " + value + " was given.");
        }
        this.value = value;
    }

    @Override
    public boolean inBounds(Integer value) {
        return value >= lowerLimit && value <= upperLimit;
    }

    @Override
    public Integer ascend(Integer newValue) {
        return ascend.apply(newValue);
    }

    @Override
    public Integer descend(Integer newValue) {
        return descend.apply(newValue);
    }

    @Override
    public int estimateVersions() {
        int total = 1;
        int newValue = ascend.apply(value);
        while (newValue <= upperLimit) {
            total++;
            newValue = ascend(newValue);
        }
        return total;
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
