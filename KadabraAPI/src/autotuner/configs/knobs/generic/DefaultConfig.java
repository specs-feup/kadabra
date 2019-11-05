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

import java.util.Iterator;
import java.util.List;

import autotuner.configs.Configuration;

public class DefaultConfig<T> implements Configuration<T> {

    protected List<T> values;
    protected T first;

    protected Iterator<T> iterator;

    public DefaultConfig(List<T> values) {
        this.values = values;
    }

    @Override
    public T next() {
        return iterator.next();
    }

    @Override
    public boolean hasNext(T reference) {
        if (iterator == null) {
            iterator = values.iterator();
        }
        // TODO Auto-generated method stub
        return iterator.hasNext();
    }

    @Override
    public T getFirst() {
        iterator = values.iterator();
        if (first != null) {
            return first;
        }
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    @Override
    public void setFirst(T t) {
        first = t;
    }

    @Override
    public int estimateVersions() {
        return values.size();
    }

}
