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

package autotuner.configs.factory;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import autotuner.configs.Configuration;
import autotuner.configs.knobs.generic.ConfigFromSupplier;
import autotuner.configs.knobs.generic.DefaultConfig;
import autotuner.configs.knobs.generic.RandomConfig;
import autotuner.configs.knobs.generic.SingleConfig;
import autotuner.configs.knobs.generic.TuplesDefaultConfig;
import autotuner.configs.knobs.number.AroundRangedConfig;
import autotuner.configs.knobs.number.AroundRangedConfigTuple;
import autotuner.configs.knobs.number.DefaultRangedConfig;
import autotuner.configs.knobs.number.DefaultRangedConfigTuple;
import autotuner.configs.knobs.number.LinearSliderConfigTuple;
import autotuner.configs.knobs.number.ranged.CustomStepIntegerKnob;
import autotuner.configs.knobs.number.ranged.IntegerStep;
import autotuner.configs.knobs.number.ranged.RangedKnob;
import autotuner.configs.knobs.tuples.around.Around2D;
import autotuner.configs.knobs.tuples.around.Around3D;
import autotuner.configs.knobs.tuples.full.Full2D;
import autotuner.configs.knobs.tuples.full.Full3D;
import autotuner.configs.knobs.tuples.linear.Linear2D;
import autotuner.configs.knobs.tuples.linear.Linear3D;
import autotuner.configs.knobs.tuples.utils.TupleUtils;
import pt.up.fe.specs.util.SpecsCollections;
import tdrc.tuple.Tuple;

public class ConfigFactory {

    // TODO - local minimum (freeze knobs and move just one)
    // TODO - random ranged ()
    ///////////////////////////////////////////////////////////////
    /////////////////////// INTEGER CONFIGS ///////////////////////
    ///////////////////////////////////////////////////////////////

    /**
     * Simple range configuration to go from the lowerBound to the upperBound with a given step
     * 
     * @param lowerBound
     * @param upperBound
     * @param step
     * @return
     */
    public static Configuration<Integer> range(int lowerBound, int upperBound, int step) {

        return new DefaultRangedConfig<>(new IntegerStep(lowerBound, upperBound, step));
    }

    /**
     * Simple range configuration to go from a given value to the upperBound with a given step
     * 
     * @param lowerBound
     * @param upperBound
     * @param step
     * @return
     */
    public static Configuration<Integer> range(int lowerBound, int upperBound, int value, int step) {

        return new DefaultRangedConfig<>(new IntegerStep(lowerBound, upperBound, value, step));
    }

    public static Configuration<Tuple<Integer>> range(List<RangedKnob<Integer>> rangedKnobs) {

        return new DefaultRangedConfigTuple<>(rangedKnobs);

    }

    public static Configuration<Integer> range(RangedKnob<Integer> rangedKnobs) {

        return new DefaultRangedConfig<>(rangedKnobs);

    }

    /**
     * Full 2D knob exploration
     * 
     * @param leftRange
     * @param rightRange
     * @return
     */
    public static <L extends Number, R extends Number> Configuration<Pair<L, R>> range(RangedKnob<L> leftRange,
            RangedKnob<R> rightRange) {

        return new Full2D<>(leftRange, rightRange);

    }

    /**
     * Full 3D knob exploration
     * 
     * @param leftRange
     * @param middleRange
     * @param rightRange
     * @return
     */
    public static <L extends Number, M extends Number, R extends Number> Configuration<Triplet<L, M, R>> range(
            RangedKnob<L> leftRange,
            RangedKnob<M> middleRange,
            RangedKnob<R> rightRange) {

        return new Full3D<>(leftRange, middleRange, rightRange);

    }

    public static Configuration<Integer> range(int lowerBound, int upperBound,
            Function<Integer, Integer> stepProvider) {

        return new DefaultRangedConfig<>(new CustomStepIntegerKnob(lowerBound, upperBound, stepProvider, stepProvider));

    }

    public static Configuration<Integer> around(RangedKnob<Integer> rangedKnob) {

        return new AroundRangedConfig<>(rangedKnob);
    }

    public static Configuration<Tuple<Integer>> around(List<RangedKnob<Integer>> rangedKnobs) {

        return new AroundRangedConfigTuple<>(rangedKnobs);

    }

    /**
     * Search around 2D knobs
     * 
     * @param leftRange
     * @param rightRange
     * @return
     */
    public static <L extends Number, R extends Number> Configuration<Pair<L, R>> around(RangedKnob<L> leftRange,
            RangedKnob<R> rightRange) {

        return new Around2D<>(leftRange, rightRange);

    }

    /**
     * Search around 3D knobs
     * 
     * @param leftRange
     * @param middleRange
     * @param rightRange
     * @return
     */
    public static <L extends Number, M extends Number, R extends Number> Configuration<Triplet<L, M, R>> around(
            RangedKnob<L> leftRange,
            RangedKnob<M> middleRange,
            RangedKnob<R> rightRange) {

        return new Around3D<>(leftRange, middleRange, rightRange);

    }

    public static Configuration<Tuple<Integer>> linear(List<RangedKnob<Integer>> rangedKnobs) {

        return new LinearSliderConfigTuple<>(rangedKnobs);
    }

    /**
     * Search 3D knobs linearly
     * 
     * @param leftRange
     * @param middleRange
     * @param rightRange
     * @return
     */
    public static <L extends Number, M extends Number, R extends Number> Configuration<Triplet<L, M, R>> linear(
            RangedKnob<L> leftRange,
            RangedKnob<M> middleRange,
            RangedKnob<R> rightRange) {

        return new Linear3D<>(leftRange, middleRange, rightRange);

    }

    public static <L extends Number, R extends Number> Configuration<Pair<L, R>> linear(RangedKnob<L> leftRange,
            RangedKnob<R> rightRange) {

        return new Linear2D<>(leftRange, rightRange);
    }

    public static Configuration<Integer> around(int lowerBound, int upperBound, int step) {

        return around(new IntegerStep(lowerBound, upperBound, step));

    }

    public static Configuration<Integer> around(int lowerBound, int upperBound, int defaulValue,
            int step) {

        return around(new IntegerStep(lowerBound, upperBound, defaulValue, step));

    }

    public static Configuration<Integer> around(int lowerBound, int upperBound, int defaultValue,
            Function<Integer, Integer> descend, Function<Integer, Integer> ascend) {

        return around(new CustomStepIntegerKnob(lowerBound, upperBound, defaultValue, descend, ascend));
    }

    public static Configuration<Integer> around(int lowerBound, int upperBound,
            Function<Integer, Integer> descend, Function<Integer, Integer> ascend) {

        return around(new CustomStepIntegerKnob(lowerBound, upperBound, descend, ascend));
    }

    ///////////////////////////////////////////////////////////////
    /////////////////////// GENERIC CONFIGS ///////////////////////
    ///////////////////////////////////////////////////////////////

    public static <T> DefaultConfig<T> normal(List<T> values) {
        return new DefaultConfig<>(values);
    }

    // @SafeVarargs
    // public static <T> DefaultConfig<T> normal(T... values) {
    // return new DefaultConfig<>(Arrays.asList(values));
    // }

    // public static <T> DefaultConfig<Tuple<T>> normal(TupleList<T> values) {
    // return new TuplesDefaultConfig<>(values);
    // }

    public static <T> DefaultConfig<Tuple<T>> combine(List<T[]> parameters) {
        return new TuplesDefaultConfig<>(parameters);
    }

    public static <L, R> DefaultConfig<Pair<L, R>> combine(Configuration<L> params1, Configuration<R> params2) {

        List<Pair<L, R>> values = TupleUtils.combine(getAllValues(params1), getAllValues(params2));
        return new DefaultConfig<>(values);
    }

    public static <L, M, R> DefaultConfig<Triplet<L, M, R>> combine(Configuration<L> params1, Configuration<M> params2,
            Configuration<R> params3) {

        List<Triplet<L, M, R>> values = TupleUtils.combine(getAllValues(params1), getAllValues(params2),
                getAllValues(params3));
        return new DefaultConfig<>(values);
    }

    public static <T> DefaultConfig<T> random(List<T> values) {
        return new RandomConfig<>(values);
    }

    public static <T> DefaultConfig<T> random(Configuration<T> values) {
        return RandomConfig.newInstance(values);
    }

    public static <N extends Number> DefaultConfig<N> random(RangedKnob<N> values) {
        return RandomConfig.newInstance(values);
    }

    public static <N extends Number, M extends Number> DefaultConfig<Pair<N, M>> random(RangedKnob<N> values,
            RangedKnob<M> values2) {
        return combine(RandomConfig.newInstance(values), RandomConfig.newInstance(values2));
    }
    /*
    public static IntegerConfig> defaultConfig(int lowerBound, int upperBound, int step) {
        new IntegerDefaultConfig(lowerBound, upperBound, step);
    }
    
    public static IntegerConfig> defaultConfig(int lowerBound, int upperBound,
            Function<Integer, Integer> stepProvider) {
        new IntegerDefaultConfigCustomStep(lowerBound, upperBound, stepProvider);
    }
    
    public static IntegerConfig> neighbors(int lowerBound, int upperBound, int step) {
        new IntegerAroundConfig(lowerBound, upperBound, step);
    }
    
    public static IntegerConfig> neighbors(int lowerBound, int upperBound,
            Function<Integer, Integer> decreaseStep,
            Function<Integer, Integer> increaseStep) {
        new IntegerAroundConfigCustomStep(lowerBound, upperBound, decreaseStep, increaseStep);
    }
    
    
    */

    public static <T> Configuration<T> fromSupplier(Supplier<T> supplier) {
        // TODO Auto-generated method stub
        return new ConfigFromSupplier<>(supplier);
    }

    public static <K> Configuration<K> single(K args) {
        return new SingleConfig<>(args);
    }

    public static <T extends Number> List<T> getAllValues(RangedKnob<T> range) {
        List<T> values = SpecsCollections.newArrayList();
        T lower = range.getLowerLimit();
        values.add(lower);
        while (range.canClimb(lower)) {
            lower = range.ascend(lower);
            values.add(lower);
        }
        return values;
    }

    public static <T> List<T> getAllValues(Configuration<T> config) {
        List<T> values = SpecsCollections.newArrayList();
        T first2 = config.getFirst();
        values.add(first2);
        while (config.hasNext(first2)) {
            values.add(config.next());
        }
        return values;
    }
}
