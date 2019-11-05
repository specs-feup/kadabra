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

package autotuner.measurer;

import java.util.function.Supplier;

public class AvgFloatMeasurer extends Measurer<Float> {

    private float total;
    private int count;

    // Supplier<AvgLongMeasurer> =/= Supplier<? extends Measurer<Long>>

    public static Supplier<AvgFloatMeasurer> asSupplier() {
        return () -> new AvgFloatMeasurer();
    }

    public static Supplier<AvgFloatMeasurer> asSupplier(String unit) {
        return () -> new AvgFloatMeasurer(unit);
    }

    public AvgFloatMeasurer() {
    }

    public AvgFloatMeasurer(String unit) {
        this.setUnit(unit);
    }

    @Override
    public Float getValue() {
        return total / count;
    }

    @Override
    public void updateImpl(Float newValue) {
        total += newValue;
        count++;
    }

    @Override
    public void set(Float newValue) {
        total = newValue;
        count = 1;
    }

    @Override
    public int compareTo(Measurer<Float> o) {

        return (int) (getValue() - o.getValue());
    }

    @Override
    public void resetImpl() {
        total = 0;
        count = 0;
    }

    @Override
    public String toString() {
        String unit = getUnit();
        return getValue() + "" + unit + "(w: " + getNumWarmup() + ", s: " + getNumTests() + ")";
    }

}
