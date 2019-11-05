/**
 * Copyright 2016 SPeCS.
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

package weaver.kadabra.monitor;

import java.util.Arrays;
import java.util.List;

public class VersionTester<T> {

    private int warmup;
    private int currentWarmup;

    private int numRuns = 0;
    private int currentRuns = 0;

    private T bestVersion;
    private T testVersion;

    private List<T> tests;
    // private Iterator<T> testsIt;
    private int testPos = -1;
    private int bestPos = -1;

    private long bestTime = Long.MAX_VALUE;

    private long testTime = 0;
    private boolean jumpIfWorse = false;

    private boolean initialized = false;
    private boolean finished = false;

    private CodeTimer timer;
    private Runnable onTestFinish;

    private Runnable onInitialize;
    private boolean adapt;

    public static <T> VersionTester<T> newInstance(CodeTimer timer, int numRuns, int warmup, boolean jumpIfWorse) {
	return new VersionTester<>(timer, numRuns, warmup, null, null, jumpIfWorse);
    }

    public static <T> VersionTester<T> newInstance(String timeUnit, int numRuns, int warmup, boolean jumpIfWorse) {
	return newInstance(timeUnit, numRuns, warmup, null, null, jumpIfWorse);
    }

    public static <T> VersionTester<T> newInstance(String timeUnit, int numRuns, int warmup,
	    Runnable onInitialize,
	    Runnable onFinalize,
	    boolean jumpIfWorse) {
	CodeTimer timer = CodeTimer.NewTimer(timeUnit);
	return new VersionTester<T>(timer, numRuns, warmup, onInitialize, onFinalize, jumpIfWorse);
    }

    private VersionTester(CodeTimer timer, int numRuns, int warmup, Runnable onInitialize, Runnable onFinalize,
	    boolean jumpIfWorse) {

	this.timer = timer;
	this.numRuns = numRuns;
	this.warmup = warmup;
	this.onInitialize = onInitialize;
	this.onTestFinish = onFinalize;
	this.jumpIfWorse = jumpIfWorse;
    }

    public void setTests(List<T> tests) {
	this.tests = tests;
    }

    public void setTests(@SuppressWarnings("unchecked") T... tests) {
	this.tests = Arrays.asList(tests);
    }

    public T startOrElse(T defaultValue) {
	if (adapt) {
	    return start();
	}
	return defaultValue;
    }

    public boolean hasFinished() {
	return finished;
    }

    public T start() {
	if (!initialized) { // Reset all variables so the test starts at the first version and without best time!
	    initTest();
	}

	if (currentRuns == 0) {
	    compareBest();
	    resetTestValues();
	    if (hasNext()) {
		getNextTest();
	    } else {
		return finishTest();
	    }
	}

	return testVersion;
    }

    private boolean hasNext() {
	// testsIt.hasNext();
	return getTestPos() + 1 < tests.size();
    }

    private void getNextTest() {
	// testVersion = testsIt.next();
	testPos++;
	testVersion = tests.get(getTestPos());
    }

    private T finishTest() {
	if (onTestFinish != null) {
	    onTestFinish.run();
	}
	finished = true;
	return stop();
    }

    public T stop() {
	initialized = false;
	testPos = -1;
	return bestVersion;
    }

    private T initTest() {
	timer.stop();
	bestTime = Long.MAX_VALUE;
	bestVersion = null;
	testVersion = null;
	finished = false;

	resetTestValues();
	if (onInitialize != null) {
	    onInitialize.run();
	}
	if (tests == null || tests.isEmpty()) {
	    throw new RuntimeException("Could not execute test of versions: no version to test!");
	}
	setTestPos(-1);
	setBestPos(-1);
	// testsIt = tests.iterator();

	initialized = true;
	setAdapt(true);

	getNextTest();
	return testVersion;
    }

    private void resetTestValues() {
	testTime = 0;
	currentRuns = numRuns;
	currentWarmup = warmup;
    }

    public void startTimer() {
	timer.start();
    }

    public long getTime() {
	return timer.getTime();
    }

    public void stopTimer() {
	timer.stop();
    }

    public void pauseTimer() {
	timer.pause();
    }

    public void stopAndUpdate() {
	stopTimer();
	update();
    }

    public void update() {
	if (isAdapt()) {
	    if (currentWarmup == 0) {
		testTime += timer.getTime();
		if (jumpIfWorse && testTime > bestTime) {
		    currentRuns = 0;
		} else {
		    currentRuns--;
		}
	    } else {
		currentWarmup--;
	    }
	}
    }

    public boolean compareBest() {
	if (testTime < bestTime) {
	    bestVersion = testVersion;
	    bestTime = testTime;
	    setBestPos(getTestPos());
	    return true;
	}
	return false;

    }

    public long getBestTime() {
	return bestTime;
    }

    public T getBestVersion() {
	return bestVersion;
    }

    public long getTestTime() {
	return testTime;
    }

    public boolean isAdapt() {
	return adapt;
    }

    public void setAdapt(boolean adapt) {
	this.adapt = adapt;
    }

    public VersionTester<T> setOnTestFinish(Runnable onTestFinish) {
	this.onTestFinish = onTestFinish;
	return this;
    }

    public VersionTester<T> setOnInitialize(Runnable onInitialize) {
	this.onInitialize = onInitialize;
	return this;
    }

    public int getBestPos() {
	return bestPos;
    }

    public void setBestPos(int bestPos) {
	this.bestPos = bestPos;
    }

    public int getTestPos() {
	return testPos;
    }

    public void setTestPos(int testPos) {
	this.testPos = testPos;
    }

}
