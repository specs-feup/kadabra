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

package weaver.kadabra.control.utils;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

import weaver.kadabra.concurrent.Product;

public class ProviderUtils {
    public static VersionProvider<Integer> getRangedProvider(int begin, int end,
            int increment) {
        int diff = end - begin;

        // incrementing test
        if (diff > 0) {
            return (report) -> {
                if (report.getVersion() == null) {
                    return begin;
                }
                int newV = report.getVersion() + increment;
                if (newV > end) { // stops when higher than end
                    return null;
                }
                return newV;
            };
        }
        // decrementing test
        return (report) -> {
            if (report.getVersion() == null) {
                return begin;
            }
            int newV = report.getVersion() + increment;
            if (newV < end) { // stops when lower than end
                return null;
            }
            return newV;
        };
    }

    public static VersionProvider<Integer> getRangedProvider(int begin, int end) {
        return getRangedProvider(begin, end, 1);
    }

    public static VersionProvider<Integer> getRangedProvider(int begin, int end,
            VersionProvider<Integer> versionCalculator) {
        int diff = end - begin;

        // incrementing test
        if (diff > 0) {
            return (report) -> {
                if (report.getVersion() == null) {
                    return begin;
                }
                int newV = versionCalculator.apply(report);
                if (newV > end) { // stops when higher than end
                    return null;
                }
                return newV;
            };
        }
        // decrementing test
        return (report) -> {
            if (report.getVersion() == null) {
                return begin;
            }
            int newV = versionCalculator.apply(report);
            if (newV < end) { // stops when lower than end
                return null;
            }
            return newV;
        };
    }

    /**
     * This method does exactly as it is called!
     * 
     * @param version
     */
    public static <T> void doNothing(T version) {
    }

    /**
     * This method does exactly as it is called!
     * 
     * @param version
     */
    public static <T, V> void doNothing(T xVersion, V yVersion) {
    }

    public interface ProductProvider<T, V> extends BiFunction<T, V, Product<T, V>> {
    }

    public static <T> VersionProvider<T> fromList(
            List<T> list) {
        Iterator<T> iterator = list.iterator();
        return r -> iterator.hasNext() ? iterator.next() : null;
    };

}
