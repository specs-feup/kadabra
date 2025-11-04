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

package autotuner.measurer;

import java.util.function.Supplier;

public class AvgDoubleMeasurer extends Measurer<Double> {

    private double total;
    private int count;

    // Supplier<AvgLongMeasurer> =/= Supplier<? extends Measurer<Long>>

    public static Supplier<AvgDoubleMeasurer> asSupplier() {
        return () -> new AvgDoubleMeasurer();
    }

    public static Supplier<AvgDoubleMeasurer> asSupplier(String unit) {
        return () -> new AvgDoubleMeasurer(unit);
    }

    public AvgDoubleMeasurer() {
    }

    public AvgDoubleMeasurer(String unit) {
        this.setUnit(unit);
    }

    @Override
    public Double getValue() {
        return total / count;
    }

    @Override
    public void updateImpl(Double newValue) {
        total += newValue;
        count++;
    }

    @Override
    public void set(Double newValue) {
        total = newValue;
        count = 1;
    }

    @Override
    public int compareTo(Measurer<Double> o) {

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
