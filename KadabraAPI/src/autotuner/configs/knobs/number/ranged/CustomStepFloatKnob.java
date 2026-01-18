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

public class CustomStepFloatKnob extends CustomStepKnob<Float> {

    public CustomStepFloatKnob(Float lowerLimit, Float upperLimit, Function<Float, Float> descend,
            Function<Float, Float> ascend) {
        super(lowerLimit, upperLimit, descend, ascend);
    }

    public CustomStepFloatKnob(Float lowerLimit, Float upperLimit, Float value, Function<Float, Float> descend,
            Function<Float, Float> ascend) {
        super(lowerLimit, upperLimit, value, descend, ascend);
    }

    @Override
    public boolean inc() {
        float newValue = ascend.apply(value);
        if (newValue <= upperLimit) {
            value = newValue;
            return true;
        }
        return false;
    }

    @Override
    public boolean dec() {
        float newValue = descend.apply(value);
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
    public void setValue(Float value) {
        if (value < lowerLimit || value > upperLimit) {
            throw new RuntimeException("Out of bounds for ranged knob! Expected value between [" + lowerLimit + ","
                    + upperLimit + "] but " + value + " was given.");
        }
        this.value = value;
    }

    @Override
    public boolean inBounds(Float value) {
        return value >= lowerLimit && value <= upperLimit;
    }

    @Override
    public Float ascend(Float newValue) {
        return ascend.apply(newValue);
    }

    @Override
    public Float descend(Float newValue) {
        return descend.apply(newValue);
    }

    @Override
    public int estimateVersions() {
        int total = 1;
        float newValue = ascend.apply(value);
        while (newValue <= upperLimit) {
            total++;
            newValue = ascend(newValue);
        }
        return total;
    }

    @Override
    public void setValueFromNumber(Number value) {

        if (value instanceof Double) { // FIXME
            throw new RuntimeException("Cannot set a Float knob with a type higher than Integer, a "
                    + value.getClass().getSimpleName() + " was given.");
        }
        this.value = value.floatValue();
    }
}
