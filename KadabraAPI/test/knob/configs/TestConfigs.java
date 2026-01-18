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
 * specific language governing permissions and limitations under the License. under the License.
 */

package knob.configs;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import autotuner.configs.Configuration;
import autotuner.configs.factory.ConfigFactory;
import autotuner.configs.knobs.number.ranged.FloatStep;
import autotuner.configs.knobs.number.ranged.IntegerStep;
import autotuner.configs.knobs.number.ranged.RangedKnob;
import pt.up.fe.specs.util.SpecsCollections;

public class TestConfigs {

    @Test
    public void full1DExploration() {
        RangedKnob<Integer> anInt = new IntegerStep(-10, 10, 2);

        Configuration<Integer> fullConfig = ConfigFactory.range(anInt);
        int value = fullConfig.getFirst();
        int correct = -10;
        while (fullConfig.hasNext(value)) {
            value = fullConfig.next();
            correct += 2;
            assertEquals(correct, value);
        }
    }

    @Test
    public void around1DExploration() {
        RangedKnob<Integer> anInt = new IntegerStep(-10, 10, 0, 2);

        Configuration<Integer> aroundConfig = ConfigFactory.around(anInt);
        int value = aroundConfig.getFirst();
        assertEquals(0, value);
        int pos = 0;
        int[] expected = new int[] { 2, -2, -4, -6, -8, -10 };
        while (aroundConfig.hasNext(value)) {
            value = aroundConfig.next();
            assertEquals(expected[pos++], value);
        }
    }

    @Test
    public void full2DExploration() {

        RangedKnob<Integer> anInt = new IntegerStep(-10, 10, 2);
        RangedKnob<Float> aFloat = new FloatStep(-10f, 10f, 0.5f);

        Configuration<Pair<Integer, Float>> fullConfig = ConfigFactory.range(anInt, aFloat);
        Pair<Integer, Float> value = fullConfig.getFirst();
        int correctI = -10;
        float correctF = -10;
        assertEquals(correctI, value.getValue0().intValue());
        assertEquals(correctF, value.getValue1().floatValue(), 0);
        while (fullConfig.hasNext(value)) {
            value = fullConfig.next();

            correctF += 0.5f;
            if (correctF > 10f) {
                correctI += 2;
                correctF = -10;
            }
            assertEquals(correctI, value.getValue0().intValue());
            assertEquals(correctF, value.getValue1().floatValue(), 0);
        }

    }

    @Test
    public void around2DExploration() {

        RangedKnob<Integer> anInt = new IntegerStep(-10, 10, 0, 2);
        RangedKnob<Float> aFloat = new FloatStep(-10f, 10f, 0f, 0.5f);

        Configuration<Pair<Integer, Float>> around = ConfigFactory.around(anInt, aFloat);
        Pair<Integer, Float> value = around.getFirst();
        int correctI = 0;
        float correctF = 0;
        assertEquals(correctI, value.getValue0().intValue());
        assertEquals(correctF, value.getValue1().floatValue(), 0);
        int pos = 0;
        while (around.hasNext(value)) {
            value = around.next();
            Pair<Integer, Float> expected = EXPECTED_ARRAY.get(pos++);
            assertEquals(expected, value);
            // System.out.println("Expected vs Actual: " + expected + " <-> " + value);
            // System.out.println("EXPECTED_ARRAY.add(Pair.with(" + value.getValue0() + ", " + value.getValue1() +
            // "f));");

        }
    }

    @Test
    public void linear2DExploration() {

        RangedKnob<Integer> anInt = new IntegerStep(-10, 10, 2);
        RangedKnob<Float> aFloat = new FloatStep(-10f, 10f, 0.5f);

        Configuration<Pair<Integer, Float>> linear = ConfigFactory.linear(anInt, aFloat);

        Pair<Integer, Float> target = Pair.with(-1, 8f);
        Pair<Integer, Float> value = linear.getFirst();
        System.out.println(value);

        boolean hasNext = linear.hasNext(value);
        while (hasNext) {
            value = linear.next();
            System.out.println(value);

            // Pair<Integer, Float> expected = EXPECTED_ARRAY.get(pos++);
            // assertEquals(expected, value);
            hasNext = linear.hasNext(target);
        }
    }

    public static List<Pair<Integer, Float>> EXPECTED_ARRAY;
    {
        EXPECTED_ARRAY = SpecsCollections.newArrayList();
        EXPECTED_ARRAY.add(Pair.with(0, 0.5f));
        EXPECTED_ARRAY.add(Pair.with(0, -0.5f));
        EXPECTED_ARRAY.add(Pair.with(2, 0.0f));
        EXPECTED_ARRAY.add(Pair.with(2, 0.5f));
        EXPECTED_ARRAY.add(Pair.with(2, -0.5f));
        EXPECTED_ARRAY.add(Pair.with(-2, 0.0f));
        EXPECTED_ARRAY.add(Pair.with(-2, 0.5f));
        EXPECTED_ARRAY.add(Pair.with(-2, -0.5f));
        EXPECTED_ARRAY.add(Pair.with(-2, -1.0f));
        EXPECTED_ARRAY.add(Pair.with(0, -1.0f));
        EXPECTED_ARRAY.add(Pair.with(-4, -0.5f));
        EXPECTED_ARRAY.add(Pair.with(-4, 0.0f));
        EXPECTED_ARRAY.add(Pair.with(-4, -1.0f));
        EXPECTED_ARRAY.add(Pair.with(-4, -1.5f));
        EXPECTED_ARRAY.add(Pair.with(-2, -1.5f));
        EXPECTED_ARRAY.add(Pair.with(-6, -1.0f));
        EXPECTED_ARRAY.add(Pair.with(-6, -0.5f));
        EXPECTED_ARRAY.add(Pair.with(-6, -1.5f));
        EXPECTED_ARRAY.add(Pair.with(-6, -2.0f));
        EXPECTED_ARRAY.add(Pair.with(-4, -2.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -1.5f));
        EXPECTED_ARRAY.add(Pair.with(-8, -1.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -2.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -2.5f));
        EXPECTED_ARRAY.add(Pair.with(-6, -2.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -2.0f));
        EXPECTED_ARRAY.add(Pair.with(-10, -1.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -2.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -3.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -3.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -3.5f));
        EXPECTED_ARRAY.add(Pair.with(-6, -3.0f));
        EXPECTED_ARRAY.add(Pair.with(-6, -3.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -3.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -4.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -4.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -4.5f));
        EXPECTED_ARRAY.add(Pair.with(-6, -4.0f));
        EXPECTED_ARRAY.add(Pair.with(-6, -4.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -4.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -5.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -5.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -5.5f));
        EXPECTED_ARRAY.add(Pair.with(-6, -5.0f));
        EXPECTED_ARRAY.add(Pair.with(-6, -5.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -5.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -6.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -6.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -6.5f));
        EXPECTED_ARRAY.add(Pair.with(-6, -6.0f));
        EXPECTED_ARRAY.add(Pair.with(-6, -6.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -6.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -7.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -7.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -7.5f));
        EXPECTED_ARRAY.add(Pair.with(-6, -7.0f));
        EXPECTED_ARRAY.add(Pair.with(-6, -7.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -7.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -8.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -8.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -8.5f));
        EXPECTED_ARRAY.add(Pair.with(-6, -8.0f));
        EXPECTED_ARRAY.add(Pair.with(-6, -8.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -8.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -9.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -9.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -9.5f));
        EXPECTED_ARRAY.add(Pair.with(-6, -9.0f));
        EXPECTED_ARRAY.add(Pair.with(-6, -9.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -9.5f));
        EXPECTED_ARRAY.add(Pair.with(-10, -10.0f));
        EXPECTED_ARRAY.add(Pair.with(-8, -10.0f));
        EXPECTED_ARRAY.add(Pair.with(-6, -10.0f));
        EXPECTED_ARRAY.add(Pair.with(-4, -10.0f));
        EXPECTED_ARRAY.add(Pair.with(-4, -9.5f));
        EXPECTED_ARRAY.add(Pair.with(-4, -9.0f));
        EXPECTED_ARRAY.add(Pair.with(-2, -9.5f));
        EXPECTED_ARRAY.add(Pair.with(-2, -9.0f));
        EXPECTED_ARRAY.add(Pair.with(-2, -10.0f));
        EXPECTED_ARRAY.add(Pair.with(0, -10.0f));
        EXPECTED_ARRAY.add(Pair.with(0, -9.5f));
        EXPECTED_ARRAY.add(Pair.with(0, -9.0f));
        EXPECTED_ARRAY.add(Pair.with(2, -9.5f));
        EXPECTED_ARRAY.add(Pair.with(2, -9.0f));
        EXPECTED_ARRAY.add(Pair.with(2, -10.0f));
        EXPECTED_ARRAY.add(Pair.with(4, -10.0f));
        EXPECTED_ARRAY.add(Pair.with(4, -9.5f));
        EXPECTED_ARRAY.add(Pair.with(4, -9.0f));
        EXPECTED_ARRAY.add(Pair.with(6, -9.5f));
        EXPECTED_ARRAY.add(Pair.with(6, -9.0f));
        EXPECTED_ARRAY.add(Pair.with(6, -10.0f));
        EXPECTED_ARRAY.add(Pair.with(8, -10.0f));
        EXPECTED_ARRAY.add(Pair.with(8, -9.5f));
        EXPECTED_ARRAY.add(Pair.with(8, -9.0f));
        EXPECTED_ARRAY.add(Pair.with(10, -9.5f));
        EXPECTED_ARRAY.add(Pair.with(10, -9.0f));
        EXPECTED_ARRAY.add(Pair.with(10, -10.0f));
    }
}
