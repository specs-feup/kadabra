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

package weaver.kadabra.control;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import weaver.kadabra.control.utils.MeasureComparator;
import weaver.kadabra.control.utils.ProviderUtils;
import weaver.kadabra.control.utils.VersionProvider;

public class ControlPoint<T> implements Updater<T> {

    private static final int DEFAULT_NUM_WARMUPS = 0;
    private static final int DEFAULT_NUM_RUNS = 1;
    /*
     * User-defined properties
     */
    private String name;
    private int numTests;
    private int numWarmup;
    private VersionProvider<T> provider = r -> {
        throw new RuntimeException("VersionProvider must be... provided");
    };
    private MeasureComparator comparator = (b, t) -> t < b; // by default the best is the smallest
    /*
     * Events-related fields
     */
    private Consumer<T> onBestChange = ProviderUtils::doNothing;
    private Consumer<T> onTestChange = ProviderUtils::doNothing;
    private Consumer<T> onStop = ProviderUtils::doNothing;
    private Consumer<T> versionConsumer = ProviderUtils::doNothing;

    /*
     * Test-related fields
     */
    private AtomicReference<T> best;
    private AtomicReference<T> test;
    private Map<T, Long> avgs;
    private Map<T, Long> measurements;
    private AtomicBoolean stopped;

    private long bestMeasure;
    private long testMeasure;
    private int currentNumTests = 0;
    private int currentNumWarmups = 0;

    /**
     * Create a control point with a name, no warmup and one measurement per test
     * 
     * @param name
     */
    public ControlPoint(String name) {
        this(name, DEFAULT_NUM_RUNS, DEFAULT_NUM_WARMUPS, null);
    }

    /**
     * Create a control point with a name, no warmup and one measurement per test
     * 
     * @param name
     */
    ControlPoint(String name, VersionProvider<T> versionProvider) {
        this(name, DEFAULT_NUM_RUNS, DEFAULT_NUM_WARMUPS, versionProvider);
    }

    /**
     * Create a control point with a name, numWarmup warmups and numTests measurement per test
     * 
     * @param name
     * @param numTests
     * @param numWarmup
     * 
     */
    ControlPoint(String name, int numTests, int numWarmup) {
        this(name, numTests, numWarmup, null);
    }

    /**
     * Create a control point with a name, numWarmup warmups, numTests measurement per test and the new version provider
     * 
     * @param name
     * @param numTests
     * @param numWarmup
     * @param versionProvider
     * 
     */
    ControlPoint(String name, int numTests, int numWarmup, VersionProvider<T> versionProvider) {
        this.name = name;
        setNumWarmup(numWarmup);
        setNumTests(numTests);
        setProvider(versionProvider);
        reset();
    }

    /**
     * Provide a new measurement for a test.<br>
     * <b>NOTE:</b> Does not guarantee that the measurement is for the test.
     * 
     * @param measurement
     */
    public void measurement(long measurement) {
        if (stopped.get()) {
            return;
        }
        if (currentNumWarmups >= numWarmup) {
            testMeasure += measurement;
        }
        updateNextAndBest();
    }

    /**
     * Provide a new measurement for the given test, if the current test is not equal to that test, then the measurement
     * is discarded. Useful when considering concurrency
     * 
     * @param measurement
     */
    public void measurement(T testedVersion, long measurement) {
        if (!testedVersion.equals(test.get())) {
            return;
        }
        measurement(measurement);
    }

    private void updateNextAndBest() {
        if (currentNumWarmups < numWarmup) {
            currentNumWarmups++;
            return;
        }
        currentNumTests++;
        if (currentNumTests >= numTests) {

            boolean improved = updateMeasurementsAndBest();
            nextTest(improved);
            if (test.get() == null) {
                stopTest();
            }
        }

    }

    private void stopTest() {
        stopped.set(true);
        onStop.accept(best.get());
    }

    private void nextTest(boolean improved) {
        VersionReport<T> report = VersionReport.newInstance(test.get(), improved, testMeasure);
        test.set(provider.apply(report));
        currentNumTests = 0;
        currentNumWarmups = 0;
        testMeasure = 0;
        onTestChange.accept(test.get());
    }

    private boolean updateMeasurementsAndBest() {
        measurements.put(test.get(), testMeasure); // update map containing all metrics
        avgs.put(test.get(), testMeasure / numTests); // update map containing all metrics
        if (comparator.isTestBetter(bestMeasure, testMeasure)) {
            best.set(test.get());
            bestMeasure = testMeasure;
            onBestChange.accept(best.get());
            return true;
        }
        return false;
    }

    /**
     * Start the test with the existing configuration
     * 
     * @throws NullPointerException
     *             if the version provider was not defined previously
     */
    public ControlPoint<T> startTest() throws NullPointerException {
        this.stopped.set(false);
        nextTest(true);
        return this;
    }

    /**
     * starts a test with the given function, which accepts the current test and provides the next test
     * 
     * @param versionProvider
     * @return
     */
    public ControlPoint<T> startTest(VersionProvider<T> versionProvider) {
        setProvider(versionProvider);
        return startTest();
    }

    /**
     * starts a test with a list of versions (FIFO approach)
     * 
     * @param versionProvider
     * @return
     */
    public ControlPoint<T> startTest(T[] testingVersions) {
        setTests(testingVersions);
        return startTest(provider);
    }

    /**
     * starts a test with a list of versions (FIFO approach)
     * 
     * @param versionProvider
     * @return
     */
    public ControlPoint<T> startTest(List<T> testingVersions) {
        setTests(testingVersions);
        return startTest(provider);
    }

    @Override
    public T update() {
        T version = getVersion();
        versionConsumer.accept(version);
        return version;
    }

    public void reset() {
        avgs = new HashMap<>();
        measurements = new HashMap<>();
        this.test = new AtomicReference<>();
        this.best = new AtomicReference<>();
        this.stopped = new AtomicBoolean(true);
        this.bestMeasure = Long.MAX_VALUE;
    }

    ///////////////////////////////////////////////////
    ///////////// GETTERS AND SETTERS /////////////////
    ///////////////////////////////////////////////////

    /**
     * Returns a version to execute: test version if testing, best otherwise
     * 
     * @return
     */
    public T getVersion() {
        return stopped.get() ? best.get() : test.get();
    }

    public T getBest() {
        return best.get();
    }

    /**
     * The name of the Control Point
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    public ControlPoint<T> setOnBestChange(java.util.function.Consumer<T> onBestChange) {
        this.onBestChange = onBestChange;
        return this;
    }

    public ControlPoint<T> setOnTestChange(java.util.function.Consumer<T> onTestChange) {
        this.onTestChange = onTestChange;
        return this;
    }

    /**
     * Defines what to do when stopping test. Recieves as input the best version
     * 
     * @param onStop
     */
    public ControlPoint<T> setOnStop(java.util.function.Consumer<T> onStop) {
        this.onStop = onStop;
        return this;
    }

    public ControlPoint<T> setNumTests(int numTests) {
        this.numTests = numTests;
        return this;
    }

    public ControlPoint<T> setNumWarmup(int numWarmup) {
        this.numWarmup = numWarmup;
        return this;
    }

    /**
     * starts a test with a list of versions (FIFO approach)
     * 
     * @param versionProvider
     * @return
     */
    public ControlPoint<T> setProvider(VersionProvider<T> provider) {
        this.provider = provider;
        return this;
    }

    public ControlPoint<T> setTests(T[] versions) {
        setTests(Arrays.asList(versions));
        return this;
    }

    public ControlPoint<T> setTests(List<T> versions) {
        LinkedList<T> tests = new LinkedList<>(versions);
        VersionProvider<T> provider = report -> tests.pollFirst();

        setProvider(provider);
        return this;
    }

    /**
     * Sets the code that consumes the current test, or the best of test is not running
     */
    public ControlPoint<T> onUpdate(java.util.function.Consumer<T> versionConsumer) {
        this.versionConsumer = versionConsumer;
        return this;
    }

    public boolean hasFinished() {
        return stopped.get();
    }

    public long getBestMeasure() {
        return bestMeasure;
    }

    /**
     * Returns a map containing the accumulated measurements for each T tested
     * 
     * @return
     */
    public Map<T, Long> getMeasurementsAcc() {
        return Collections.unmodifiableMap(measurements);
    }

    /**
     * Returns a map containing the average measurement for each T tested
     * 
     * @return
     */
    public Map<T, Long> getMeasurementsAvg() {
        return Collections.unmodifiableMap(avgs);
    }

    public long removeMeasurement(T element) {
        avgs.remove(element);
        return measurements.remove(element);
    }

    public void setComparator(MeasureComparator comparator) {
        this.comparator = comparator;
    }

    public MeasureComparator getComparator() {
        return comparator;
    }

    public boolean wasTested(T test) {
        return measurements.containsKey(test);
        // return measurements.keySet().stream().filter(test::equals).findAny().isPresent();
    }
}
