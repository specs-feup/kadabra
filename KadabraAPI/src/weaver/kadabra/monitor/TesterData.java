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
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.monitor;

public class TesterData<T> {

    private int warmup;
    private int currentWarmup;

    private int numRuns;
    private int currentRuns;

    private T bestVersion;

    private int testsCount;
    private int bestTest;

    private long bestTime = Long.MAX_VALUE;
    private long testTime = 0;
    private boolean jumpIfWorse;
    private CodeTimer timer;
    private boolean finished;

    private boolean adapt;

    public TesterData(String timer, int numRuns, int warmup, boolean jumpIfWorse) {
	this(CodeTimer.NewTimer(timer), numRuns, warmup, jumpIfWorse);
    }

    public TesterData(CodeTimer timer, int numRuns, int warmup, boolean jumpIfWorse) {
	this.timer = timer;
	this.warmup = warmup;
	this.numRuns = numRuns;
	this.jumpIfWorse = jumpIfWorse;
	resetAllData();
    }

    public void resetAllData() {
	bestTest = -1;
	testsCount = 0;
	bestTime = 0;
	resetTest();
    }

    public void resetTest() {
	testTime = 0;
	currentRuns = 0;
	currentWarmup = 0;
	finished = false;
	timer.stop();
    }

    public boolean swapBest(T testVersion) {
	if (testTime < bestTime) {
	    bestVersion = testVersion;
	    bestTime = testTime;
	    setBestTest(testsCount - 1);
	    return true;
	}
	return false;
    }

    /**
     * If the current test has finished
     * 
     * @return
     */
    public boolean hasFinished() {
	return finished;
    }

    public void update() {
	if (currentWarmup == warmup) {
	    incTestTime(timer.getTime());
	    currentRuns++;
	    if (currentRuns >= numRuns ||
		    (jumpIfWorse && testTime > bestTime)) {
		finished = true;
	    }
	} else {
	    currentWarmup++;
	}
    }

    public void timerStart() {
	timer.start();
    }

    public long getTime() {
	return timer.getTime();
    }

    public void timerPause() {
	timer.pause();
    }

    public void timerStop() {
	timer.stop();
    }

    /* Common getters and setters*/
    public int getWarmup() {
	return warmup;
    }

    public void setWarmup(int warmup) {
	this.warmup = warmup;
    }

    public int getCurrentWarmup() {
	return currentWarmup;
    }

    public int getNumRuns() {
	return numRuns;
    }

    public void setNumRuns(int numRuns) {
	this.numRuns = numRuns;
    }

    public int getCurrentRuns() {
	return currentRuns;
    }

    public T getBestVersion() {
	return bestVersion;
    }

    public int getTestsCount() {
	return testsCount;
    }

    public int getBestTest() {
	return bestTest;
    }

    private void setBestTest(int bestTest) {
	this.bestTest = bestTest;
    }

    public long getBestTime() {
	return bestTime;
    }

    public long getTestTime() {
	return testTime;
    }

    public void incTestsCount() {
	testsCount++;
    }

    private void incTestTime(long testTime) {
	this.testTime += testTime;
    }

    public CodeTimer getTimer() {
	return timer;
    }

    public void setTimer(CodeTimer timer) {
	this.timer = timer;
    }

    public boolean isAdapt() {
	return adapt;
    }

    public void setAdapt(boolean adapt) {
	this.adapt = adapt;
    }
}
