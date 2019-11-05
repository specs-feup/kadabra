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

package weaver.kadabra.control;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import weaver.kadabra.concurrent.KadabraChannel;
import weaver.kadabra.concurrent.KadabraThread;
import weaver.kadabra.concurrent.Product;

/**
 * 
 * @author tiago
 *
 * @param <T>
 *            Testing type
 */
public class ConcurrentControlPoint<T> {

    private AtomicReference<ControlPoint<T>> cp;
    private KadabraChannel<T, Long> measurementsChannel;
    private KadabraChannel<T, T> versionsChannel;
    private KadabraThread thread;
    private long timeout;
    private TimeUnit unit;

    public static <T> ConcurrentControlPoint<T> newInstance(String cpName, int measurementsCapacity,
            int versionsCapacity) {
        return newInstance(new ControlPoint<>(cpName), measurementsCapacity, versionsCapacity);
    }

    public static <T> ConcurrentControlPoint<T> newInstance(ControlPoint<T> cp, int measurementsCapacity) {
        return newInstance(cp, measurementsCapacity, 1);
    }

    public static <T> ConcurrentControlPoint<T> newInstance(ControlPoint<T> cp, int measurementsCapacity,
            int versionsCapacity) {
        return new ConcurrentControlPoint<>(cp, measurementsCapacity, versionsCapacity);
    }

    private ConcurrentControlPoint(ControlPoint<T> cp, int measurementsCapacity, int versionsCapacity) {
        measurementsChannel = KadabraChannel.newInstance(measurementsCapacity);
        versionsChannel = KadabraChannel.newInstance(versionsCapacity);
        this.cp = new AtomicReference<>(cp);
        thread = KadabraThread.newInstance(this::run);
        timeout = 1000;
        unit = TimeUnit.MILLISECONDS;
    }

    /**
     * Starts the thread that expects measurements
     */
    public void startThread() {
        thread.start();
    }

    /**
     * Terminate the thread execution
     */
    public void stopThread() {
        thread.terminate();
    }

    private void run() {
        System.out.println("STARTED");
        while (thread.isRunning()) {
            Product<T, Long> measurement = measurementsChannel.poll(timeout, unit);
            if (measurement == null) {
                continue;
            }
            System.out.println(measurement);
            cp.get().measurement(measurement.getKey(), measurement.getValue());
        }
        System.out.println("Done");

    }

    public boolean offer(T id, long measurement) {

        return measurementsChannel.offer(id, measurement);
    }

    public T update() {
        return cp.get().update();
    }

    /**
     * Gets a version to test or the best version so far, if no version to test exists<br>
     * <b>NOTE:</b>best may be null if no tests were done so far
     * 
     * @return
     */
    public T pollOrBest() {
        T poll = poll();
        return poll != null ? poll : cp.get().getBest();
    }

    /**
     * Gets a version to test, or null if no version to test exists
     * 
     * @return
     */
    public T poll() {
        Product<T, T> poll = versionsChannel.poll();
        if (poll == null) {
            return null;
        }
        return poll.getValue();
    }

    public ControlPoint<T> getControlPoint() {
        return cp.get();
    }
}
