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

package autotuner.configs.knobs.number.old.simpleinteger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Function;

/**
 * The default configuration is a simple exhaustive search that tries all possible values/combinations with a provided
 * step
 * 
 * @author tiago
 *
 */
public class IntegerAroundConfigCustomStep extends IntegerConfig {
    private Deque<Integer> queue = new ArrayDeque<>();
    private Function<Integer, Integer> increaseStep;
    private Function<Integer, Integer> decreaseStep;

    public IntegerAroundConfigCustomStep(int lowerBound, int upperBound, Function<Integer, Integer> decreaseStep,
            Function<Integer, Integer> increaseStep) {
        super(lowerBound, upperBound, 0);
        this.decreaseStep = decreaseStep;
        this.increaseStep = increaseStep;
    }

    @Override
    public void setFirst(Integer value) {
        first = value;
    }

    @Override
    public boolean hasNext(Integer reference) {
        if (queue.isEmpty()) {

            int left = decreaseStep.apply(reference);
            int right = increaseStep.apply(reference);
            if (right < upperBound && !visited.contains(right)) {
                queue.push(right);
            }
            if (left >= lowerBound && !visited.contains(left)) {
                queue.push(left);
            }
            if (!visited.contains(reference)) {
                queue.push(reference);
            }
            if (queue.isEmpty()) {
                return false;
            }
        }

        next = queue.pop();
        return true;
    }

    @Override
    public int estimateVersions() {

        return -1;
    }
}
