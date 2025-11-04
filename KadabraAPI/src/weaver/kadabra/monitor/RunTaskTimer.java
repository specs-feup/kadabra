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

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class RunTaskTimer {

    private final Timer timer;
    private final Runnable task;
    private long delay;
    private AtomicBoolean ready;

    public RunTaskTimer(Runnable task, long delay) {

        this.timer = new Timer();
        this.task = task;
        this.setDelay(delay);
        this.ready = new AtomicBoolean(true);
    }

    public synchronized void execute() {
        TimerTask tTask = new TimerTask() {

            @Override
            public void run() {
                ready.set(false);

                task.run();
                ready.set(true);
            }
        };
        timer.schedule(tTask, getDelay());
    }

    public synchronized boolean ready() {

        return ready.get();
    }

    public synchronized void cancel() {

        timer.cancel();
        ready.set(true);
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicBoolean stop = new AtomicBoolean(false);
        RunTaskTimer task = new RunTaskTimer(() -> stop.set(true), 5000);
        task.execute();
        while (!stop.get()) {
            System.out.print("NaN");
            Thread.sleep(300);
        }
        System.out.println("\nBatman!");
        task.cancel();
    }
}
