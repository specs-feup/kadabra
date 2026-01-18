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

public abstract class Measurer<M> implements Comparable<Measurer<M>> {

    private int numTests;
    private int numWarmup;
    private String unit = "";

    /**
     * Returns the value of the measurement
     * 
     * @return
     */
    public abstract M getValue();

    /**
     * Updates the measurement with a new value
     * 
     * @param newValue
     * @return
     */
    protected abstract void updateImpl(M newValue);

    /**
     * A measurement that was considered as warmup and usually accumulated separately. By default it does nothing
     * 
     * @param newValue
     * @return
     */
    protected void warmupImpl(M newValue) {

    }

    /**
     * Updates the measurement with a new value
     * 
     * @param newValue
     * @return the total number of tests
     */
    public final int update(M newValue) {
        numTests++;
        updateImpl(newValue);
        return numTests;
    }

    /**
     * A measurement that was considered as warmup and usually accumulated separately. By default it increments the
     * numWarmup field and does nothing more.
     * 
     * @param newValue
     * @return the total number of warmups
     */
    public final int warmup(M newValue) {
        numWarmup++;
        warmupImpl(newValue);
        return numWarmup;
    }

    /**
     * Sets the measurement with the given value
     * 
     * @param newValue
     * @return
     */
    public abstract void set(M newValue);

    /**
     * Resets the measurement
     */
    public final void reset() {
        numTests = 0;
        numWarmup = 0;
        resetImpl();
    }

    /**
     * Resets the measurement
     */
    public void resetImpl() {

    }

    public int getNumTests() {
        return numTests;
    }

    public void setNumTests(int numTests) {
        this.numTests = numTests;
    }

    public int getNumWarmup() {
        return numWarmup;
    }

    public void setNumWarmup(int numWarmup) {
        this.numWarmup = numWarmup;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
