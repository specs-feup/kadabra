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

package weaver.kadabra.agent;

import java.lang.instrument.Instrumentation;

class PremainAgent {
    private static Instrumentation instrumentation = null;

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        PremainAgent.instrumentation = instrumentation;
    }

    static void addAdapter(MethodAdapter/*<?>*/ adapter) {
        if (instrumentation == null) {
            // throw new RuntimeException(
            // "Could not add adapter: kadabra agent is not loaded. Please execute the application with jvm argument:
            // -javaagent:<path_to_kadabra.jar>");
            System.err.println("[KADABRA] Could not add adapter: kadabra agent is not loaded");
            return;
        }
        instrumentation.addTransformer(adapter, true);
        adapter.setInstrumentation(instrumentation);
    }

}
