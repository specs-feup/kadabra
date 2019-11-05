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

import pt.up.fe.specs.util.SpecsSystem;
import pt.up.fe.specs.util.system.ProcessOutputAsString;

public class CpuInfoWindows implements CpuInfoProvider {

    @Override
    public int getL2CacheSize() {
        ProcessBuilder builder = new ProcessBuilder("wmic", "cpu", "get", "L2CacheSize");
        ProcessOutputAsString runProcess = SpecsSystem.runProcess(builder, true, false);
        String output = runProcess.getOutput();
        String[] outputs = output.split("\r\n");
        if (outputs.length < 3) {
            return -1;
        }
        String size = outputs[2].trim();
        return Integer.parseInt(size);
    }

    @Override
    public int getCPUThreads() {
        ProcessBuilder builder = new ProcessBuilder("wmic", "cpu", "get", "NumberOfLogicalProcessors");
        ProcessOutputAsString runProcess = SpecsSystem.runProcess(builder, true, false);
        String output = runProcess.getOutput();
        String[] outputs = output.split("\r\n");
        if (outputs.length < 3) {
            return -1;
        }
        String size = outputs[2].trim();
        return Integer.parseInt(size);
    }

}
