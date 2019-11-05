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

package weaver.kadabra.system;

import weaver.kadabra.util.KadabraLog;

public interface CpuInfoProvider {

    public int getL2CacheSize();

    public default int getL2CacheSizeOrElse(int defaultValue) {
        try {
            return getL2CacheSize();
        } catch (Exception e) {

            KadabraLog.warning("Could not get cache size. Exception: " + e.getMessage() + ". Will use default value.");
        }
        return defaultValue;
    }

    public int getCPUThreads();

    public default int getCPUThreads(int defaultValue) {
        try {
            return getCPUThreads();
        } catch (Exception e) {
            KadabraLog.warning(
                    "Could not get number of threads. Exception: " + e.getMessage() + ". Will use default value.");
        }
        return defaultValue;
    }
}
