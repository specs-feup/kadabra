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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import autotuner.configs.Configuration;
import autotuner.configs.factory.ConfigFactory;
import autotuner.configs.knobs.number.ranged.RangedKnob;

public class RandomConfig<T> extends DefaultConfig<T> {

    public RandomConfig(List<T> values) {
        super(values);

    }

    public static <T> RandomConfig<T> newInstance(List<T> values) {
        return new RandomConfig<>(values);
    }

    public static <T> RandomConfig<T> newInstance(Configuration<T> config) {
        return new RandomConfig<>(ConfigFactory.getAllValues(config));
    }

    public static <N extends Number> RandomConfig<N> newInstance(RangedKnob<N> config) {
        return new RandomConfig<>(ConfigFactory.getAllValues(config));
    }

    @Override
    public boolean hasNext(T reference) {
        if (iterator == null) {
            shuffledIterator();
        }
        return iterator.hasNext();
    }

    @Override
    public T getFirst() {
        shuffledIterator();
        if (first != null) {
            return first;
        }
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    private void shuffledIterator() {
        ArrayList<T> arrayList = new ArrayList<>(values);
        Collections.shuffle(arrayList);
        iterator = arrayList.iterator();
    }
}
