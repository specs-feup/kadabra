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

public class CpuInfo {
    static {
        String property = System.getProperty("os.name");
        if (property.startsWith("Windows")) {
            provider = new CpuInfoWindows();
        } else {
            provider = new CpuInfoLinux();
        }
    }

    private static final CpuInfoProvider provider;

    public static int getL2CacheSize() {
        return provider.getL2CacheSize();
    }

    public static int getCPUThreads() {
        int cpuThreads = provider.getCPUThreads();
        System.out.println(cpuThreads);
		return cpuThreads;
    }

    public static int getL2CacheSizeOrElse(int defaultValue) {
        return provider.getL2CacheSizeOrElse(defaultValue);
    }

    public static void main(String[] args) {
        System.out.println(getL2CacheSize());
    }
}
