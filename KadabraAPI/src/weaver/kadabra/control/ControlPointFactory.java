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

package weaver.kadabra.control;

import java.util.Arrays;
import java.util.List;

import tdrc.tuple.Tuple;
import tdrc.tuple.TupleList;
import tdrc.utils.ListUtils;
import weaver.kadabra.control.utils.ProviderUtils;
import weaver.kadabra.control.utils.VersionProvider;

public class ControlPointFactory {

    /**
     * Creates a control point with list of values to combine into tuples
     * 
     * @param name
     * @param begin
     * @param end
     * @return
     */
    @SafeVarargs
    public static <T> ControlPoint<Tuple<T>> createTuples(String name, T[]... parameters) {
        return createTuples(name, Arrays.asList(parameters));
    }

    /**
     * Creates a control point with list of values to combine into tuples
     * 
     * @param name
     * @param begin
     * @param end
     * @return
     */
    public static <T> ControlPoint<Tuple<T>> createTuples(String name, List<T[]> parameters) {
        TupleList<T> createTuples = ListUtils.createTuples(parameters);
        VersionProvider<Tuple<T>> provider = ProviderUtils.fromList(createTuples);
        return new ControlPoint<Tuple<T>>(name).setProvider(provider);
    }

    /**
     * Creates a control point with only a name
     * 
     * @param name
     * @param begin
     * @param end
     * @return
     */
    public static <T> ControlPoint<T> newInstance(String name) {
        return new ControlPoint<>(name);
    }

    /**
     * Creates a control point with the given number of warmups and tests
     * 
     * @param name
     * @param begin
     * @param end
     * @return
     */
    public static <T> ControlPoint<T> newInstance(String name, int warmup, int runs) {
        return new ControlPoint<>(name, runs, warmup);
    }

    /**
     * Creates a control point with the given number of warmups and tests
     * 
     * @param name
     * @param begin
     * @param end
     * @return
     */
    public static <T> ControlPoint<T> newInstance(String name, int runs) {
        return new ControlPoint<>(name, runs, 0);
    }

    /**
     * Creates a control point that profiles an array of versions
     * 
     * @param name
     * @param begin
     * @param end
     * @return
     */
    @SafeVarargs
    public static <T> ControlPoint<T> testVersions(String name, int warmup, int runs, T... versions) {
        return new ControlPoint<T>(name, runs, warmup).setTests(versions);
    }

    /**
     * Creates a control point that profiles an array of versions
     * 
     * @param name
     * @param begin
     * @param end
     * @return
     */
    public static <T> ControlPoint<T> testVersions(String name, int warmup, int runs, List<T> versions) {
        return new ControlPoint<T>(name, runs, warmup).setTests(versions);
    }

    /**
     * Creates a control point that profiles tests from begin to end with a given incremental value for the step
     * 
     * @param name
     * @param begin
     * @param end
     * @return
     */
    public static ControlPoint<Integer> rangedTest(String name, int begin, int end, int increment) {
        return new ControlPoint<>(name, ProviderUtils.getRangedProvider(begin, end, increment));
    }

    /**
     * Creates a control point that profiles tests from begin to end with a given incremental value for the step
     * 
     * @param name
     * @param begin
     * @param end
     * @return
     */
    public static ControlPoint<Integer> rangedTest(String name, int begin, int end,
            VersionProvider<Integer> versionCalculator) {
        return new ControlPoint<>(name, ProviderUtils.getRangedProvider(begin, end, versionCalculator));
    }

    /**
     * Creates a control point that profiles tests from begin to end with a given using Math.power for the step
     * 
     * @param name
     * @param begin
     * @param end
     * @param power
     * @return
     */
    public static ControlPoint<Integer> powerOf(String name, int begin, int end, int power) {
        return rangedTest(name, begin, end, report -> (int) Math.pow(report.getVersion(), power));
    }

}
