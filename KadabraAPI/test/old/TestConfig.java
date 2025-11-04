package old;
import autotuner.configs.knobs.number.old.simpleinteger.IntegerConfig;
import autotuner.configs.knobs.number.old.simpleinteger.IntegerDefaultConfig;

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

public class TestConfig {
    public static void main(String[] args) throws InterruptedException {
        // ExplorationConfig config = new AroundConfig(2, 20, 2);
        IntegerConfig config = new IntegerDefaultConfig(2, 20, 2);
        int ref = 8;
        while (config.hasNext(ref)) {
            int next = config.next();
            System.out.println(next);
            Thread.sleep(ref * 200);
            if (next < ref) {
                ref = next;
            }
        }
        System.out.println("DONE: " + ref);
    }
}
