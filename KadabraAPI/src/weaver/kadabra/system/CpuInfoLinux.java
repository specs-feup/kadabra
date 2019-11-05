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

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import pt.up.fe.specs.util.SpecsSystem;
import pt.up.fe.specs.util.system.ProcessOutputAsString;

public class CpuInfoLinux implements CpuInfoProvider {

    public Map<String, String> cpuInfo;

    public CpuInfoLinux() {
        cpuInfo = new HashMap<>();
        // ProcessBuilder builder = new ProcessBuilder("cat", "/sys/devices/system/cpu/cpu0/cache/index2/size");
        ProcessBuilder builder = new ProcessBuilder("lscpu");
        ProcessOutputAsString runProcess = SpecsSystem.runProcess(builder, true, false);
        String output = runProcess.getOutput();
        try (Scanner scanner = new Scanner(output);) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(":");
                cpuInfo.put(split[0], split[1].trim());
            }
        }

        // output = output.substring(0, output.length() - 2); // remove the K
    }

    @Override
    public int getL2CacheSize() {
        if (!cpuInfo.containsKey("L2 cache")) { // e.g. Odroid does not retrieve this info
            ProcessBuilder builder = new ProcessBuilder("cat", "/sys/devices/system/cpu/cpu0/cache/index2/size");
            ProcessOutputAsString runProcess = SpecsSystem.runProcess(builder, true, false);
            String output = runProcess.getOutput();
            output = output.substring(0, output.length() - 2); // remove the K
            return Integer.parseInt(output);
        }
        String string = cpuInfo.get("L2 cache");
        string = string.substring(0, string.length() - 2);
        return Integer.parseInt(string);

    }

    @Override
    public int getCPUThreads() {
        String string = cpuInfo.get("CPU(s)");
        return Integer.parseInt(string);
    }
}
