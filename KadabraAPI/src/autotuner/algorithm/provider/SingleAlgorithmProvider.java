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

package autotuner.algorithm.provider;

import autotuner.algorithm.Algorithm;
import autotuner.algorithm.SimpleAlgorithm;

/**
 * A provider that only returns the "first" and only algorithm
 * 
 * @author tiago
 *
 * @param <A>
 */
public class SingleAlgorithmProvider<A> implements AlgorithmProvider<A> {

    protected A method;
    protected String id;

    public SingleAlgorithmProvider(A method, String id) {
        this.method = method;
        this.id = id;
    }

    @Override
    public SimpleAlgorithm<A> next() {
        return null;
    }

    @Override
    public boolean hasNext(Algorithm<A> reference) {
        return false;
    }

    @Override
    public SimpleAlgorithm<A> getFirst() {
        return new SimpleAlgorithm<>(method, id);
    }

    @Override
    public boolean isASingleAlgorithm() {
        return true;
    }

    @Override
    public int estimateVersions() {
        return 1;
    }

}
