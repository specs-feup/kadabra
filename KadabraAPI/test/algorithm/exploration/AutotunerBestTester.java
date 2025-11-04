package algorithm.exploration;

import autotuner.algorithm.Algorithm;
import autotuner.knob.SimpleKnob;
import autotuner.manager.AlgorithmSampling;
import tuner.TestBestTuner;

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

public class AutotunerBestTester {
    static TestBestTuner tuner = new TestBestTuner(5, 10);

    static int knobx = 10;
    static int knoby = 10;
    static int field = 10;

    public static void main(String[] args) throws InterruptedException {
        Algorithm<Void> zero = new SimpleKnob<>(0, AutotunerBestTester::setKnobx);
        Algorithm<Void> one = new SimpleKnob<>(1, AutotunerBestTester::setKnobx);
        Algorithm<Void> two = new SimpleKnob<>(2, AutotunerBestTester::setKnobx);

        tuner.setBest(0, zero);
        tuner.setBest(1, one);
        tuner.setBest(2, two);

        for (int i = 0; i < 10; i++) {
            AlgorithmSampling<Void, Long> algorithm = tuner.getAlgorithm(i % 3);
            algorithm.apply();
            System.out.println(getKnobx());
        }
    }

    public static int getKnobx() {
        return knobx;
    }

    public static int setKnobx(int knobx) {
        AutotunerBestTester.knobx = knobx;
        return knobx;
    }
}
