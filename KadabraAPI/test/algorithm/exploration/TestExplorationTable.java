package algorithm.exploration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import autotuner.algorithm.Algorithm;
import autotuner.algorithm.provider.AlgorithmProvider;
import autotuner.algorithm.provider.AlgorithmWithKnobProvider;
import autotuner.algorithm.provider.SingleAlgorithmProvider;
import autotuner.configs.Configuration;
import autotuner.configs.factory.ConfigFactory;
import autotuner.configs.knobs.number.ranged.CustomStepIntegerKnob;
import autotuner.configs.knobs.number.ranged.IntegerStep;
import autotuner.configs.knobs.number.ranged.RangedKnob;
import autotuner.manager.AlgorithmSampling;
import autotuner.manager.ExpMode;
import autotuner.manager.ExplorationManager;
import autotuner.manager.ExplorationTable;
import autotuner.measurer.AvgLongMeasurer;
import autotuner.measurer.Measurer;
import tdrc.tuple.Tuple;
import weaver.kadabra.monitor.CodeTimer;

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

public class TestExplorationTable {
    static ExplorationTable<Integer, Integer, Long> table = new ExplorationTable<>();

    static int knobx = 10;
    static int knoby = 10;
    static int field = 10;

    public static void main(String[] args) throws InterruptedException {
        ExplorationManager<IMedian, Long> explorer = newExplorer();
        explorer.setPrintingLimit(10);
        CodeTimer timer = CodeTimer.NanoTimer();
        // System.out.println(table);
        int numForUpdate = 10;
        for (int i = 0; i < 400; i++) {
            ExpMode mode = explorer.getMode();
            AlgorithmSampling<IMedian, Long> version = explorer.getVersion();
            timer.start();
            Algorithm<IMedian> algorithm = version.getAlgorithm();
            algorithm.apply();
            IMedian iMedian = algorithm.get();
            Thread.sleep(iMedian.get());
            timer.stop();
            explorer.update(version, timer.getTime());
            System.out.println(version.toString() + " (" + version.getNumWarmup() + ", " +
                    +version.getNumTests() + ")");
            if (mode != explorer.getMode()) {
                System.out.println(explorer.toString());

            }
            if (explorer.getMode() == ExpMode.BEST) {
                numForUpdate--;
                if (numForUpdate <= 0) {
                    numForUpdate = 10;
                    System.out.println("Forcing update");
                    explorer.updateBest();
                }
            }

        }
        System.out.println(explorer.toString());
    }

    private static ExplorationManager<IMedian, Long> newExplorer() {
        List<AlgorithmProvider<IMedian>> providers = algorithmProviders();
        Supplier<Measurer<Long>> measurementProvider = () -> new AvgLongMeasurer();
        ExplorationManager<IMedian, Long> exploration = ExplorationManager.ordered(5, 10, providers,
                measurementProvider);
        exploration.setMode(ExpMode.SAMPLING);
        return exploration;
    }

    private static List<AlgorithmProvider<IMedian>> algorithmProviders() {
        SingleAlgorithmProvider<IMedian> x = new SingleAlgorithmProvider<>(
                TestExplorationTable::getX, "x");
        SingleAlgorithmProvider<IMedian> y = new SingleAlgorithmProvider<>(
                TestExplorationTable::getY, "y");
        SingleAlgorithmProvider<IMedian> z = new SingleAlgorithmProvider<>(
                TestExplorationTable::getZ, "z");

        AlgorithmWithKnobProvider<IMedian, Integer> xWKnob = new AlgorithmWithKnobProvider<>(
                TestExplorationTable::getXWithKnob,
                "x w/knob",
                knobValue -> knobx = knobValue,
                // ConfigFactory.defaultRangedConfig(0, 10, 2).get());
                ConfigFactory.around(0, 8, 6, 2));

        // Integer[] values = { 5, 2, 8, 1 };
        // List<Integer[]> valuesList = new ArrayList<>();
        // valuesList.add(values);
        // valuesList.add(values);
        List<RangedKnob<Integer>> rangedKnobs = new ArrayList<>();
        // DefaultConfig<Tuple<Integer>> defaultConfig = ConfigFactory.combine(valuesList).get();
        rangedKnobs.add(new IntegerStep(0, 6, 3, 1));
        rangedKnobs.add(new CustomStepIntegerKnob(1, 32, 4, (t) -> t >> 1, (t) -> t << 1));
        Configuration<Tuple<Integer>> config = ConfigFactory.around(rangedKnobs);
        AlgorithmWithKnobProvider<IMedian, Tuple<Integer>> xW2Knobs = new AlgorithmWithKnobProvider<>(
                TestExplorationTable::getXWith2Knobs,
                "x w/2 knobs",
                knobValue -> {
                    knobx = knobValue.get(0);
                    knobx = knobValue.get(1);
                },
                config);
        // defaultConfig);
        List<AlgorithmProvider<IMedian>> providers = Arrays.asList(x, y, z, xWKnob, xW2Knobs);
        return providers;
    }

    public static interface IMedian {
        int get();
    }

    static int getX() {
        return field;
    }

    static int getY() {
        return field + 3;
    }

    static int getZ() {
        return field - 5;
    }

    static int getXWithKnob() {
        return Math.abs(field - knobx);
    }

    static int getXWith2Knobs() {
        return Math.abs(field - knobx - knoby);
    }

    // public static void firstTest() throws InterruptedException {
    // CodeTimer timer = CodeTimer.NanoTimer();
    // int dataSet = 10000;
    //
    // int lowerBound = -10;
    // int upperBound = 10;
    // int step = 2;
    //
    // OLDSEManager<Integer, Long> exp10000 = newFullExplorer(dataSet, lowerBound, upperBound, step);
    // System.out.println(table);
    // for (int i = lowerBound; i < 20; i++) {
    // ExpPoint<Integer, Long> version = exp10000.getVersion();
    // timer.start();
    // codeToTest(version.getAlgorithm());
    // timer.stop();
    // version.update(timer.getTime());
    // }
    // System.out.println("-----");
    // int newData = 7000;
    // OLDSEManager<Integer, Long> exp7000 = newNeighborExplorer(newData, lowerBound, upperBound, step);
    // System.out.println(table);
    // System.out.println("-----");
    // for (int i = lowerBound; i < 3; i++) {
    // ExpPoint<Integer, Long> version = exp7000.getVersion();
    // timer.start();
    // codeToTest(version.getAlgorithm());
    // timer.stop();
    // version.update(timer.getTime());
    // }
    // System.out.println(table);
    // }
    //
    // public static void anotherTest() throws InterruptedException {
    // CodeTimer timer = CodeTimer.NanoTimer();
    // int[] learningDataSets = { 1000, 2000, 3000, 30000, 10000 };
    // int[] testingDataSets = { 2500, 500, 11000 };
    //
    // int lowerBound = -10;
    // int upperBound = 10;
    // int step = 2;
    //
    // OLDSEManager<Integer, Long> exp10000 = newFullExplorer(learningDataSets[0], lowerBound, upperBound, step);
    // System.out.println(table);
    // for (int i = lowerBound; i < 20; i++) {
    // ExpPoint<Integer, Long> version = exp10000.getVersion();
    // timer.start();
    // codeToTest(version.getAlgorithm());
    // timer.stop();
    // version.update(timer.getTime());
    // }
    // System.out.println("-----");
    // int newData = 7000;
    // OLDSEManager<Integer, Long> exp7000 = newNeighborExplorer(newData, lowerBound, upperBound, step);
    // System.out.println(table);
    // System.out.println("-----");
    // for (int i = lowerBound; i < 3; i++) {
    // ExpPoint<Integer, Long> version = exp7000.getVersion();
    // timer.start();
    // codeToTest(version.getAlgorithm());
    // timer.stop();
    // version.update(timer.getTime());
    // }
    // System.out.println(table);
    // }
    //
    // private static void codeToTest(Integer params) throws InterruptedException {
    // Thread.sleep(Math.abs(params * 100));
    // }
    //
    // private static OLDSEManager<Integer, Long> newNeighborExplorer(int newData, int lowerBound, int upperBound, int
    // step) {
    // Entry<Integer, OLDSEManager<Integer, Long>> closest = table.getClosest(newData);
    // OLDSEManager<Integer, Long> explorer;
    // if (closest != null) { // table may be empty!
    // IntegerAroundConfig aroundConfig = new IntegerAroundConfig(lowerBound, upperBound, step);
    // OLDSEManager<Integer, Long> explorerRef = closest.getValue();
    // ExpPoint<Integer, Long> best = explorerRef.getBest();
    // if (best != null) {
    // aroundConfig.setFirst(best.getAlgorithm());
    // explorer = newExploration(aroundConfig);
    // } else { // may not started the exploration so will stick with fullExplorer
    // explorer = newFullExplorer(newData, lowerBound, upperBound, step);
    // }
    // } else {
    // explorer = newFullExplorer(newData, lowerBound, upperBound, step);
    // }
    //
    // table.add(newData, explorer);
    // return explorer;
    // }
    //
    // private static OLDSEManager<Integer, Long> newFullExplorer(int dataSet, int lowerBound, int upperBound, int step)
    // {
    // IntegerDefaultConfig configuration = new IntegerDefaultConfig(lowerBound, upperBound, step);
    // OLDSEManager<Integer, Long> se = newExploration(configuration);
    // table.add(dataSet, se);
    // return se;
    // }
    //
    // private static OLDSEManager<Integer, Long> newExploration(Configuration<Integer> configuration) {
    // OLDSEManager<Integer, Long> se = new OLDSEManager<>(configuration);
    // se.setMode(ExpMode.SAMPLING);
    // Supplier<Measurement<Long>> measurementProvider = () -> new AvgLongMeasurement();
    // se.setMeasurementProvider(measurementProvider);
    // return se;
    // }

}
