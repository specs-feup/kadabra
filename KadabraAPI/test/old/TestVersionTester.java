package old;
import java.util.HashMap;
import java.util.Map;

import weaver.kadabra.monitor.CodeTimer;
import weaver.kadabra.monitor.VersionTester;

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
 * specific language governing permissions and limitations under the License.
 */

public class TestVersionTester {
    public static void main(String[] args) throws InterruptedException {

        Map<Integer, Integer> map = new HashMap<>();
        // Consumer<Integer> onTestFinish = i -> {
        // map.put(0, i);
        // };

        VersionTester<Integer> tester = VersionTester.newInstance(CodeTimer.NanoTimer(), 10, 5, true);
        tester.setTests(50, 100, 10, 300);

        for (int i = 0; i < 100; i++) {
            int value;
            if (map.containsKey(0)) {
                value = map.get(0);
            } else {
                value = tester.start();
            }
            tester.startTimer();
            System.out.println(value);
            Thread.sleep(value);
            tester.stopAndUpdate();
            System.out.println(tester.getTime());
        }
        System.out.println(map);
    }
}
