package old;
import old.autotuner.ExpTable;

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

public class TestExplorationTable {
    static ExpTable<Integer, Integer, Long> table = new ExpTable<>();

    // public static void main(String[] args) throws InterruptedException {
    // firstTest();
    // }

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
