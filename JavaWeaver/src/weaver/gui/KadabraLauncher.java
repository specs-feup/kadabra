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

package weaver.gui;

import org.suikasoft.jOptions.Interfaces.DataStore;

import larai.LaraI;
import pt.up.fe.specs.lara.WeaverLauncher;
import pt.up.fe.specs.util.SpecsLogs;
import pt.up.fe.specs.util.SpecsSystem;
import weaver.kadabra.JavaWeaver;

public class KadabraLauncher {

    /**
     * Method for executing Kadabra if you want to launch it from your Java code.
     * 
     * @param args
     * @return
     */
    public static boolean execute(String[] args) {
        // return LaraLauncher.launch(args, new JavaWeaver());
        return new WeaverLauncher(new JavaWeaver()).launch(args);
    }

    public static boolean execute(DataStore data) {
        return LaraI.exec(data, new JavaWeaver());
    }

    /**
     * Main method.
     * 
     * <p>
     * If you need to launch Kadabra from your own code, please use execute() instead.
     * 
     * @param args
     */
    public static void main(String[] args) {
        SpecsSystem.programStandardInit();

        boolean success = execute(args);

        // Only exit if GUI is not running
        if (!LaraI.isRunningGui()) {
            int exitValue = success ? 0 : 1;
            SpecsLogs.debug("Calling System.exit() on KadabraLauncher, no running GUI detected");
            System.exit(exitValue);
        }
    }
}
