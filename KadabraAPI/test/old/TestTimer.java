package old;
import java.util.function.Supplier;

import weaver.kadabra.monitor.TaskTimer;

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

public class TestTimer {

    private static Integer tiling = 2;

    public static void main(String[] args) {

	Supplier<Integer> task = () -> tiling * 2;

	TaskTimer<Integer> tilingValues = new TaskTimer<>(task, 10);

	tilingValues.execute();
	System.out.println("START");
	int numWaits = 0;
	while (tiling < 2048) {
	    if (tilingValues.ready()) {
		// System.out.println("READY");
		System.out.println("num waits: " + numWaits);
		numWaits = 0;
		tiling = tilingValues.getAndExecute();
		// tilingValues.cancel();
		System.out.println(tiling);
	    } else {
		numWaits++;
	    }
	}
	System.out.println("END");
	tilingValues.cancel();
    }
}
