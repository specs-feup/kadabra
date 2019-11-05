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

import org.lara.interpreter.joptions.gui.LaraLauncher;

import pt.up.fe.specs.util.SpecsSystem;
import weaver.kadabra.JavaWeaver;

public class KadabraLauncher {

    public static boolean execute(String[] args) {
        return LaraLauncher.launch(args, new JavaWeaver());
    }

    public static void main(String[] args) {
        SpecsSystem.programStandardInit();

        execute(args);
    }
}
