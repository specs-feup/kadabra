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

package autotuner.configs.knobs.generic;

import java.util.function.Supplier;

import autotuner.configs.Configuration;

public class ConfigFromSupplier<T> implements Configuration<T> {

    protected Supplier<T> values;
    private T next;
    private T first;

    public ConfigFromSupplier(Supplier<T> values) {
        this.values = values;
    }

    @Override
    public T next() {
        return next;
    }

    @Override
    public boolean hasNext(T reference) {
        next = values.get();
        return next != null;
    }

    @Override
    public T getFirst() {
        return first != null ? first : values.get();
    }

    @Override
    public void setFirst(T t) {
        first = t;
    }

    @Override
    public int estimateVersions() {
        return 1;
    }

}
