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
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.control;

public class VersionReport<T> {

    private T version;
    private boolean improved;
    private long measurement;

    public static <T> VersionReport<T> newInstance(T version, boolean improved, long measurement) {
        return new VersionReport<>(version, improved, measurement);
    }

    private VersionReport(T version, boolean improved, long measurement) {
        this.version = version;
        this.improved = improved;
        this.measurement = measurement;
    }

    /**
     * Returns the total value of the measurements
     * 
     * @return
     */
    public long getMeasurement() {
        return measurement;
    }

    /**
     * Returns the current version that was tested
     * 
     * @return
     */
    public T getVersion() {
        return version;
    }

    /**
     * Defines if has improved the execution and is now the best version
     * 
     * @return
     */
    public boolean isImproved() {
        return improved;
    }
}
