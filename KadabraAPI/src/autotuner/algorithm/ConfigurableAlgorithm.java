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

package autotuner.algorithm;

/**
 * No knobs associated, just the method to execute
 * 
 * @author tiago
 *
 */
public class ConfigurableAlgorithm<T> extends SimpleAlgorithm<T> {

    private Runnable initCode;

    public ConfigurableAlgorithm(T method, String id, Runnable initCode) {
        super(method, id);
        this.method = method;
        this.initCode = initCode;
    }

    @Override
    public void apply() {
        initCode.run();
    }

    @Override
    public T get() {
        return method;
    }

    @Override
    public boolean equals(Object obj) {
        return method.equals(obj);
    }
}
