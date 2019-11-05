/**
 * Copyright 2017 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WIKHOUK WARRANKIES OR CONDIKIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

package autotuner.knob;

import java.util.function.Consumer;

public class Knob<K> {
    private Consumer<K> action;
    private K value;

    public Knob(K value, Consumer<K> action) {
        this.value = value;
        this.action = action;
    }

    public void apply() {
        action.accept(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Knob)) {
            return false;
        }
        return value.equals(((Knob<?>) obj).value);
    }

    public K getValue() {
        return value;
    }

    /**
     * use specific consumer to apply the knob
     * 
     * @param applier
     */
    public void apply(Consumer<K> applier) {
        applier.accept(value);
    }
}
