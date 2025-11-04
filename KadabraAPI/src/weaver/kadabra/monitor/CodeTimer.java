package weaver.kadabra.monitor;

import java.util.function.Supplier;

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

public class CodeTimer {

    private long currentTime;
    private long totalTime;
    private int count;
    private State state;

    private final Supplier<Long> timeProvider;

    private CodeTimer(Supplier<Long> timeProvider) {
        this.timeProvider = timeProvider;
        reset();
        state = State.STOP;
    }

    public static CodeTimer NewTimer(String timeUnit) {
        switch (timeUnit.toUpperCase()) {
        case "MILLIS":
        case "MILLISECONDS":
        case "MS":
            return MillisTimer();
        case "NANO":
        case "NANOSECONDS":
        case "NS":
            return NanoTimer();
        default:
            throw new RuntimeException("The given time unit cannot be used: " + timeUnit
                    + ". Please use 'nanoseconds'/'nano'/'ns' or 'milliseconds'/'millis'/'ms'");
        }
    }

    public static CodeTimer NewTimer(Supplier<Long> timeProvider) {

        return new CodeTimer(timeProvider);
    }

    public static CodeTimer NanoTimer() {

        return NewTimer(System::nanoTime);
    }

    public static CodeTimer MillisTimer() {

        return NewTimer(System::currentTimeMillis);
    }

    private enum State {
        START,
        PAUSE,
        STOP;
    }

    public void stop() {
        updateTime();

        state = State.STOP;
    }

    public void pause() {
        updateTime();

        state = State.PAUSE;
    }

    public void start() {
        currentTime = getCurrentTime();

        switch (state) {
        case START:
        case PAUSE:
            break;
        case STOP:
            totalTime = 0;
            count = 0;
            break;
        default:
            break;
        }
        state = State.START;
        count++;
    }

    public long getTime() {
        long result = totalTime;

        if (state == State.START) {

            result += getDelta();
        }
        return result;
    }

    public int getCount() {
        return count;
    }

    public double getAverage() {
        long result = totalTime;

        if (state == State.START) {

            result += getDelta();
        }

        if (count == 0) {
            // throw new NumberFormatException("Number of occurrences is zero!");
            return -1;
        }

        return result / (double) count;
    }

    private void updateTime() {

        switch (state) {

        case STOP:
        case PAUSE:
            break;

        case START:
            totalTime += getDelta();
            break;

        default:
            break;
        }
    }

    private void reset() {
        currentTime = totalTime = 0;
        count = 0;
    }

    private long getDelta() {

        return getCurrentTime() - currentTime;
    }

    private long getCurrentTime() {
        return timeProvider.get();
    }

}
