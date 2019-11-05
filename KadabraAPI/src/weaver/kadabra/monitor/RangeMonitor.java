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

public class RangeMonitor {

    double max;
    double min;

    public static RangeMonitor[] newArray(int size) {

	RangeMonitor[] rangeMonitors = new RangeMonitor[size];
	for (int i = 0; i < size; i++) {
	    rangeMonitors[i] = new RangeMonitor();
	}
	return rangeMonitors;
    }

    public RangeMonitor() {
	max = Double.NEGATIVE_INFINITY;
	min = Double.POSITIVE_INFINITY;
    }

    public void update(double value) {
	if (max < value) {
	    max = value;
	}
	if (min > value) {
	    min = value;
	}
    }

    @Override
    public String toString() {

	return "[" + min + "," + max + "]";
    }
}
