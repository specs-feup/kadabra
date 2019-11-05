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

package weaver.kadabra.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AConcurrentTester<T, K> {

    private final List<T> versions = new ArrayList<>();
    private final List<Callable<Long>> callables = new ArrayList<>();
    private Callable<K> originalRunnable;
    private long originalTime;
    private final T original;
    private long[] times;
    private boolean testing = false;
    private final Supplier<Long> timeProvider = System::currentTimeMillis;
    private final int warmupIt;
    private int currentIt;

    /**
     * 
     * 
     * @param original
     *            The original version to which the versions are contesting
     * @param warmupIt
     *            number of initial iterations to ignore before timing the versions
     */
    public AConcurrentTester(T original, int warmupIt) {
	this.original = original;
	this.warmupIt = warmupIt;
    }

    protected K run() {
	if (!isTesting()) {
	    init();
	}
	ExecutorService pool = Executors.newFixedThreadPool(versions.size());
	try {
	    boolean acc = currentIt >= warmupIt;
	    List<Future<Long>> timers = new ArrayList<>();
	    for (Callable<Long> callable : callables) {
		Future<Long> future = pool.submit(callable);
		timers.add(future);
	    }
	    // Execute the original version first in this thread
	    K out = measureOriginal(acc);

	    // Then execute and get the time of each callable (only accumulate if acc == true)
	    for (int k = 0; k < timers.size(); k++) {
		Future<Long> future = timers.get(k);
		try {
		    long time = future.get();
		    if (acc) {
			times[k] += time;
		    }
		    // System.out.println(k + " = " + time + "ms");
		} catch (Exception e) {
		    System.err.println("Problems when testing version " + k + ": " + e.getMessage());
		}
	    }
	    if (!acc) {
		currentIt++;
	    }
	    return out;
	} finally {
	    pool.shutdown();
	    callables.clear();
	}
    }

    private K measureOriginal(boolean acc) {

	try {

	    long start = timeProvider.get();
	    K out = originalRunnable.call();
	    long time = timeProvider.get() - start;
	    if (acc) {
		originalTime += time;
	    }
	    // System.out.println("-1 = " + time + "ms");
	    return out;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    public T getBest() {
	T best = original;
	long bestTime = originalTime;
	// int pos = -1;
	for (int i = 0; i < times.length; i++) {
	    long time = times[i];
	    if (time < bestTime) {
		bestTime = time;
		best = versions.get(i);
		// pos = i;

	    }
	}

	// System.out.println("timers: ");
	// printTimes();
	// System.out.println("BEST: " + pos);
	// System.out.println("Time: " + bestTime);

	return best;
    }

    public void printTimes() {
	System.out.println(-1 + "->" + originalTime);
	for (int i = 0; i < times.length; i++) {
	    long time = times[i];
	    System.out.println(i + "->" + time);

	}
    }

    protected <V> void createTests(Function<T, V> prepareTest, Function<V, K> testCode) {
	for (T t : versions) {
	    V prep = prepareTest.apply(t);
	    Callable<Long> testCodeCall = () -> {
		long begin = timeProvider.get();
		testCode.apply(prep);
		long end = timeProvider.get() - begin;
		return end;
	    };
	    addCallable(testCodeCall);
	}

	V prep = prepareTest.apply(original);
	setOriginalRun(() -> testCode.apply(prep));
    }

    public void addAll(Collection<T> versions) {
	versions.forEach(this::add);
    }

    public int add(T version) {
	if (isTesting()) {
	    throw new RuntimeException("Cannot had new versions during a test.");
	    // times = Arrays.copyOf(times, times.length + 1); // maybe this should be an exception?
	}
	versions.add(version);
	return versions.size() - 1;
    }

    public void remove(T version) {
	int indexOf = versions.indexOf(version);
	remove(indexOf);
    }

    public void remove(int index) {
	if (index == -1) {
	    return; // does not exist in the list
	}
	versions.remove(index);
	if (isTesting()) {
	    throw new RuntimeException("Cannot remove versions during a test.");
	    // times = ArrayUtils.remove(times, index);
	    // throw new RuntimeException("Unexpected version removal during tests");
	}
    }

    public void init() {
	int size = versions.size();
	times = new long[size];
	currentIt = 0;
	setTesting(true);
    }

    protected void addCallable(Callable<Long> callable) {

	callables.add(callable);
    }

    /**
     * Stops
     */
    public void stop() {
	setTesting(false);
    }

    public boolean isTesting() {
	return testing;
    }

    public void setTesting(boolean testing) {
	this.testing = testing;
    }

    protected void setOriginalRun(Callable<K> testCode) {
	originalRunnable = testCode;
    }

}