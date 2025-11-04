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

import java.util.ArrayList;
import java.util.List;

import autotuner.configs.Configuration;

/**
 * A configuration that provides the next exploration point. For now it deals only with ints or int tuples
 * 
 * @author tiago
 *
 */
public abstract class IntegerConfig implements Configuration<Integer> {
    protected int lowerBound;
    protected int upperBound;
    protected int step;
    protected int next;
    protected boolean init;
    protected List<Integer> visited;
    protected Integer first;

    public IntegerConfig(int lowerBound, int upperBound, int step) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.step = step;
        init = false;
        visited = new ArrayList<>();
    }

    @Override
    public final Integer getFirst() {
        next = getFirstImpl();
        return next();
    }

    protected int getFirstImpl() {
        return first != null ? first : lowerBound;
    }

    /**
     * Get the next value to explore
     * 
     * @param reference
     *            a reference version
     * @return
     */
    @Override
    public Integer next() {
        visited.add(next);
        return next;
    }

    @Override
    public void setFirst(Integer first) {
        this.first = first;
    }

    // @Override
    // public boolean hasNext(Integer reference) {
    // if (!init) {
    // init();
    // }
    // return hasNextImpl(reference);
    // }

    // private void init() {
    // visited = new ArrayList<>();
    // initConfig();
    // init = true;
    // }
    //
    // public abstract void initConfig();

}
