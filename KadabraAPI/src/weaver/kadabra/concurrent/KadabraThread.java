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

import java.util.concurrent.atomic.AtomicBoolean;

public class KadabraThread {

    private AtomicBoolean running = new AtomicBoolean(false);
    private Runnable run;

    public static KadabraThread newInstance() {
        return new KadabraThread();
    }

    public static KadabraThread newInstance(Runnable run) {
        KadabraThread kadabraThread = new KadabraThread();
        kadabraThread.setMethod(run);
        return kadabraThread;
    }

    public void start() {
        if (isRunning()) {
            throw new RuntimeException("The thread is already running");
        }
        if (run == null) {
            throw new RuntimeException("No runnable method was setted");
        }
        setRunning(true);
        new Thread(run).start();
    }

    public void start(Runnable arbitraryRun) {
        if (isRunning()) {
            throw new RuntimeException("The thread is already running");
        }
        setRunning(true);
        new Thread(arbitraryRun).start();
    }

    public void terminate() {
        setRunning(false);
    }

    public boolean isRunning() {
        return running.get();
    }

    private void setRunning(boolean running) {
        this.running.set(running);
    }

    public void setMethod(Runnable run) {
        this.run = run;
    }

}
