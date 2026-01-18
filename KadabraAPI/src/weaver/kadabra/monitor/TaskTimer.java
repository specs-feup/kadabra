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
import java.util.function.Supplier;

public class TaskTimer<T> {

    private T result;
    private final Timer timer;
    private final Supplier<T> task;
    private long delay;

    public TaskTimer(Supplier<T> task, long delay) {

	this.result = null;
	this.timer = new Timer();
	this.task = task;
	this.setDelay(delay);
    }

    public synchronized void execute() {
	result = null;
	TimerTask tTask = new TimerTask() {

	    @Override
	    public void run() {

		result = task.get();
	    }
	};
	timer.schedule(tTask, getDelay());
    }

    public synchronized boolean ready() {

	return result != null;
    }

    public synchronized T getAndExecute() {

	T res = get();
	execute();
	return res;
    }

    public synchronized T get() {

	// if (!ready()) {
	//
	// throw new RuntimeException("The result is not ready");
	// }
	T res = result;
	result = null;
	return res;
    }

    public synchronized void cancel() {

	timer.cancel();
    }

    public long getDelay() {
	return delay;
    }

    public void setDelay(long delay) {
	this.delay = delay;
    }

}
