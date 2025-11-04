/**
 * Copyright 2018 SPeCS.
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

package autotuner.configs.knobs.generic;

import autotuner.configs.Configuration;

/**
 * Only returns the first value (getFirst only). HasNext will always be false
 * 
 * @author tdrc
 *
 */
public class SingleConfig<T> implements Configuration<T> {
    private T value;

    public SingleConfig(T value) {
        this.value = value;
    }

    @Override
    public T next() {
        return null;
    }

    @Override
    public boolean hasNext(T reference) {
        return false;
    }

    @Override
    public T getFirst() {
        return value;
    }

    @Override
    public void setFirst(T t) {
        this.value = t;
    }

    @Override
    public int estimateVersions() {
        return 1;
    }

}
