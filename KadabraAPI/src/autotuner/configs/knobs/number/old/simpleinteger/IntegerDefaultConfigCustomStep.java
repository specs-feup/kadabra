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

import java.util.function.Function;

/**
 * The default configuration is a simple exhaustive search that tries all possible values/combinations with a provided
 * step
 * 
 * @author tiago
 *
 */
public class IntegerDefaultConfigCustomStep extends IntegerConfig {
    private Function<Integer, Integer> stepProvider;

    public IntegerDefaultConfigCustomStep(int lowerBound, int upperBound, Function<Integer, Integer> stepProvider) {
        super(lowerBound, upperBound, 0);
        this.stepProvider = stepProvider;
    }

    @Override
    public boolean hasNext(Integer reference) {

        next = stepProvider.apply(next);
        return next < upperBound;
    }

    @Override
    public int estimateVersions() {

        return -1;
    }
}
