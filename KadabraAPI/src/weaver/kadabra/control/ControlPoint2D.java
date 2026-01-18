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
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

import weaver.kadabra.concurrent.Product;
import weaver.kadabra.control.utils.ProviderUtils;
import weaver.kadabra.control.utils.ProviderUtils.ProductProvider;

public class ControlPoint2D<T, V> {

    private static final int DEFAULT_NUM_WARMUPS = 0;
    private static final int DEFAULT_NUM_RUNS = 1;
    /*
     * User-defined properties
     */
    private String name;
    private int numTests;
    private int numWarmup;
    private ProductProvider<T, V> provider;
    /*
     * Events
     */
    private java.util.function.BiConsumer<T, V> onBestChange = ProviderUtils::doNothing;
    private java.util.function.BiConsumer<T, V> onTestChange = ProviderUtils::doNothing;
    private java.util.function.BiConsumer<T, V> onStop = ProviderUtils::doNothing;
    private java.util.function.BiConsumer<T, V> versionConsumer = ProviderUtils::doNothing;
    /*
     * Test-related fields
     */
    private AtomicReference<T> bestX;
    private AtomicReference<V> bestY;
    private AtomicReference<T> testX;
    private AtomicReference<V> testY;
    private AtomicBoolean stopped;

    private long bestMeasure;
    private long testMeasure;
    private int currentNumTests = 0;
    private int currentNumWarmups = 0;

    /**
     * Creates a control point with only a name
     * 
     * @param name
     * @param begin
     * @param end
     * @return
     */
    public static <T, V> ControlPoint2D<T, V> newInstance(String name) {
        return new ControlPoint2D<>(name);
    }

    /**
     * Creates a control point with the given number of warmups and tests
     * 
     * @param name
     * @param begin
     * @param end
     * @return
     */
    public static <T, V> ControlPoint2D<T, V> newInstance(String name, int warmup, int runs) {
        return new ControlPoint2D<>(name, runs, warmup);
    }

    /**
     * Creates a control point that profiles an array of versions
     * 
     * @param name
     * @param begin
     * @param end
     * @return
     */
    public static <T, V> ControlPoint2D<T, V> testVersions(String name, int warmup, int runs, T[] xVersions,
            V[] yVersions, boolean localMinimum) {
        return new ControlPoint2D<T, V>(name, runs, warmup).setTests(xVersions, yVersions, localMinimum);
    }

    /**
     * Creates a control point that profiles an array of versions
     * 
     * @param name
     * @param begin
     * @param end
     * @return
     */
    public static <T, V> ControlPoint2D<T, V> testVersions(String name, int warmup, int runs, List<T> xVersions,
            List<V> yVersions, boolean localMinimum) {
        return new ControlPoint2D<T, V>(name, runs, warmup).setTests(xVersions, yVersions, localMinimum);
    }

    /**
     * Create a control point with a name, no warmup and one measurement per test
     * 
     * @param name
     */
    public ControlPoint2D(String name) {
        this(name, DEFAULT_NUM_RUNS, DEFAULT_NUM_WARMUPS, null);
    }

    /**
     * Create a control point with a name, numWarmup warmups and numTests measurement per test
     * 
     * @param name
     * @param numTests
     * @param numWarmup
     * 
     */
    private ControlPoint2D(String name, int numTests, int numWarmup) {
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
    private ControlPoint2D(String name, int numTests, int numWarmup, ProductProvider<T, V> provider) {
        this.name = name;
        this.numWarmup = numWarmup;
        this.numTests = numTests;
        this.provider = provider;

        testX = new AtomicReference<>();
        testY = new AtomicReference<>();
        bestX = new AtomicReference<>();
        bestY = new AtomicReference<>();
        this.stopped = new AtomicBoolean(true);
        this.bestMeasure = Long.MAX_VALUE;
    }

    /**
     * Returns a version to execute: test version if testing, best otherwise
     * 
     * @return
     */
    public T getXVersion() {
        return stopped.get() ? bestX.get() : testX.get();
    }

    public V getYVersion() {
        return stopped.get() ? bestY.get() : testY.get();
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
    public void measurement(T xVersion, V yVersion, long measurement) {
        if (!xVersion.equals(testX.get()) && !yVersion.equals(testY.get())) {
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
            updateBest();
            nextTest();
            if (testX.get() == null || testY.get() == null) {
                stopTest();
            }
        }

    }

    private void stopTest() {
        stopped.set(true);
        onStop.accept(bestX.get(), bestY.get());
    }

    private void nextTest() {
        Product<T, V> product = provider.apply(testX.get(), testY.get());
        testX.set(product.getKey());
        testY.set(product.getValue());
        currentNumTests = 0;
        currentNumWarmups = 0;
        testMeasure = 0;
        onTestChange.accept(testX.get(), testY.get());
    }

    private void updateBest() {
        if (testMeasure < bestMeasure) {
            bestX.set(testX.get());
            bestY.set(testY.get());
            bestMeasure = testMeasure;
            onBestChange.accept(bestX.get(), bestY.get());
        }
    }

    public String getName() {
        return name;
    }

    public void setOnBestChange(BiConsumer<T, V> onBestChange) {
        this.onBestChange = onBestChange;
    }

    public void setOnTestChange(BiConsumer<T, V> onTestChange) {
        this.onTestChange = onTestChange;
    }

    /**
     * Defines what to do when stopping test. Receives as input the best version
     * 
     * @param onStop
     */
    public void setOnStop(BiConsumer<T, V> onStop) {
        this.onStop = onStop;
    }

    public ControlPoint2D<T, V> setNumTests(int numTests) {
        this.numTests = numTests;
        return this;
    }

    public ControlPoint2D<T, V> setNumWarmup(int numWarmup) {
        this.numWarmup = numWarmup;
        return this;
    }

    /**
     * Start the test with the existing configuration
     * 
     * @throws NullPointerException
     *             if the version provider was not defined previously
     */
    public ControlPoint2D<T, V> startTest() throws NullPointerException {
        this.stopped.set(false);
        nextTest();
        return this;
    }

    /**
     * starts a test with the given function, which accepts the current test and provides the next test
     * 
     * @param versionProvider
     * @return
     */
    public ControlPoint2D<T, V> startTest(ProductProvider<T, V> versionProvider) {
        setProvider(versionProvider);
        return startTest();
    }

    /**
     * starts a test with a list of versions (FIFO approach)
     * 
     * @param versionProvider
     * @return
     */
    public ControlPoint2D<T, V> startTest(T[] xTestingVersions, V[] yTestingVersions, boolean localMinimum) {
        setTests(xTestingVersions, yTestingVersions, localMinimum);
        return startTest();
    }

    /**
     * starts a test with a list of versions (FIFO approach)
     * 
     * @param versionProvider
     * @return
     */
    public ControlPoint2D<T, V> startTest(List<T> xVersions, List<V> yVersions, boolean localMinimum) {
        setTests(xVersions, yVersions, localMinimum);
        return startTest();
    }

    /**
     * starts a test with a list of versions (FIFO approach)
     * 
     * @param versionProvider
     * @return
     */
    public void setProvider(ProductProvider<T, V> provider) {
        this.provider = provider;
    }

    public ControlPoint2D<T, V> setTests(T[] xVersions, V[] yVersions, boolean localMinimum) {
        setTests(Arrays.asList(xVersions), Arrays.asList(yVersions), localMinimum);
        return this;
    }

    public ControlPoint2D<T, V> setTests(List<T> xVersions, List<V> yVersions, boolean localMinimum) {
        LinkedList<T> xTests = new LinkedList<>(xVersions);

        ProductProvider<T, V> provider;
        if (localMinimum) {
            LinkedList<V> yTests = new LinkedList<>(yVersions);
            provider = (t, v) -> {
                if (!xTests.isEmpty()) { // first test the x versions
                    t = xTests.pollFirst();
                    if (v == null) { // in the first run v is null
                        v = yTests.pollFirst();
                    }
                    return Product.newInstance(t, v);
                }
                if (yTests.isEmpty()) {
                    return Product.newInstance(null, null);
                } // then test the y versions together with the best X
                t = bestX.get();
                v = yTests.pollFirst();
                return Product.newInstance(t, v);
            };
        } else {// then all possible combinations between X and Y
            LinkedList<V> yTests = new LinkedList<>();
            provider = (t, v) -> {

                if (yTests.isEmpty()) {
                    if (xTests.isEmpty()) {
                        return Product.newInstance(null, null);
                    }
                    t = xTests.pollFirst();
                    yTests.addAll(yVersions);
                }
                v = yTests.pollFirst();
                return Product.newInstance(t, v);
            };
        }

        setProvider(provider);
        return this;
    }

    public void update() {
        versionConsumer.accept(getXVersion(), getYVersion());
    }

    /**
     * Sets the code that consumes the current test, or the best of test is not running
     */
    public ControlPoint2D<T, V> setVersionConsumer(java.util.function.BiConsumer<T, V> versionConsumer) {
        this.versionConsumer = versionConsumer;
        return this;
    }

    public boolean hasFinished() {
        return stopped.get();
    }

    public long getBestMeasure() {
        return bestMeasure;
    }
}
