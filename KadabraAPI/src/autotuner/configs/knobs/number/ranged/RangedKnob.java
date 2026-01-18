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

package autotuner.configs.knobs.number.ranged;

import java.util.List;

import pt.up.fe.specs.util.SpecsCollections;

/**
 * Representation of a range that contains an upper limit, lower limit and the current value
 * 
 * @author tdrc
 *
 * @param <T>
 */
public interface RangedKnob<T extends Number> {

    T getUpperLimit();

    T getLowerLimit();

    T getValue();

    /**
     * Increments the value by the defined step if the value maintains inside the bounds
     * 
     * @return true if it was possible to increment i.e., it is still inside bounds
     */
    boolean inc();

    /**
     * Increments the value given by the defined step, does not guarantee bound correctness (use
     * {@link #inBounds(Number)} method)
     * 
     * @return the incremented value
     */
    T ascend(T reference);

    /**
     * Decrements the value by the defined step if the value maintains inside the bounds
     * 
     * @return true if it was possible to decrement, i.e., it is still inside bounds
     */
    boolean dec();

    /**
     * Decrements the value given by the defined step, does not guarantee bound correctness (use
     * {@link #inBounds(Number)} or {@link #canDescend(Number)} method)
     * 
     * @return the decremented value
     */
    T descend(T reference);

    /**
     * Verifies if it is possible to descend further than the given value, based on the defined step
     * 
     * @return true if possible, false otherwise
     */
    default boolean canDescend(T reference) {
        T value = descend(reference);
        return inBounds(value);
    }

    /**
     * Invokes {@link #descend(Number)} and {@link #ascend(Number)} methods and only adds the new values to the list if
     * they are {@link #inBounds(Number)}.
     * 
     * @param referenceValue
     * @return
     */
    default List<T> getSurrounding(T reference) {
        return getSurrounding(reference, false);
    }

    default List<T> getSurrounding(T reference, boolean includeSelf) {
        List<T> sides = SpecsCollections.newArrayList();
        T next = descend(reference);
        if (inBounds(next)) {
            sides.add(next);
        }
        next = ascend(reference);
        if (inBounds(next)) {
            sides.add(next);
        }
        if (includeSelf) {
            sides.add(reference);
        }
        return sides;
    }

    /**
     * Verifies if it is possible to descend further than the given value, based on the defined step
     * 
     * @return true if possible, false otherwise
     */
    default boolean canClimb(T reference) {
        T value = ascend(reference);
        return inBounds(value);
    }

    void setValue(T value);

    boolean inBounds(T value);

    int estimateVersions();

    void setValueFromNumber(Number value);

    // boolean canInc();
    //
    // boolean canDec();
}
