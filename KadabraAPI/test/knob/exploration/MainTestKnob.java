/**
 * Copyright 2018 SPeCS.
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

package knob.exploration;

import org.javatuples.Triplet;

import autotuner.knob.manager.KnobExplorationManager;
import autotuner.manager.ExpMode;

public class MainTestKnob {
    private static int A = 1000;
    private static float B = 1000;
    private static int C = 100;

    static void applyKnob(Triplet<Integer, Integer, Float> t) {
        A = t.getValue0();
        C = t.getValue1();
        B = t.getValue2();
    }

    private static final KnobSimulAutotuner autotuner = new KnobSimulAutotuner(1, 2);

    public static void main(String[] args) {
        int[] tests = new int[] { 0, 1, 2 };
        for (int t : tests) {
            KnobExplorationManager<Triplet<Integer, Integer, Float>, Double> exp = autotuner.getOrNew(t,
                    ExpMode.SAMPLING);
            while (exp.isSampling()) {
                exp.getVersion().apply();
                System.out.println("[ A=" + A + ", B=" + B + ", C=" + C + " ]");
                exp.update(Math.sqrt(A * A + B * B + C * C));
            }
        }
        System.out.println(autotuner.toString());
    }
}
