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

import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.javatuples.Triplet;

import autotuner.configs.Configuration;
import autotuner.configs.factory.ConfigFactory;
import autotuner.configs.knobs.number.ranged.FloatStep;
import autotuner.configs.knobs.number.ranged.IntegerStep;
import autotuner.configs.knobs.number.ranged.RangedKnob;
import autotuner.knob.manager.KnobExplorationSupervisor;
import autotuner.knob.manager.KnobProvider;
import autotuner.measurer.AvgDoubleMeasurer;

public class KnobSimulAutotuner extends KnobExplorationSupervisor<Integer, Triplet<Integer, Integer, Float>, Double> {

    public KnobSimulAutotuner(int warmup, int samples) {
        super(warmup, samples);
    }

    @Override
    protected KnobProvider<Triplet<Integer, Integer, Float>> getKnobProvider() {
        return getProvider();
    }

    private static KnobProvider<Triplet<Integer, Integer, Float>> getProvider() {

        RangedKnob<Integer> kNNRange = new IntegerStep(2, 12, 1, 3);
        RangedKnob<Integer> windowRange = new IntegerStep(800, 13000, 1000, 100);
        RangedKnob<Float> overlapRange = new FloatStep(0f, 0.6f, 0.6f, 0.1f);

        Configuration<Triplet<Integer, Integer, Float>> config = ConfigFactory.linear(kNNRange, windowRange,
                overlapRange);

        KnobProvider<Triplet<Integer, Integer, Float>> provider = new KnobProvider<>("exp", MainTestKnob::applyKnob,
                config);
        return provider;
    }

    @Override
    protected Supplier<AvgDoubleMeasurer> measurerProvider() {

        return AvgDoubleMeasurer.asSupplier();
    }

    @Override
    protected Triplet<Integer, Integer, Float> defaultKnobValue() {
        return Triplet.with(3, 100, 0.1f);
    }

    @Override
    protected BiFunction<Integer, Integer, Double> distanceProvider() {
        return null;
    }

}
