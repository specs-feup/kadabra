/**
 * Copyright 2018 SPeCS.
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

package autotuner.configs.knobs.tuples.utils;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Triplet;

public class TupleUtils {
    public static <L, R> List<Pair<L, R>> combine(List<L> lefts, List<R> rights) {
        List<Pair<L, R>> pairs = new ArrayList<>();
        for (L left : lefts) {
            for (R right : rights) {
                pairs.add(Pair.with(left, right));
            }
        }
        return pairs;
    }

    public static <L, M, R> List<Triplet<L, M, R>> combine(List<L> lefts, List<M> middles, List<R> rights) {
        List<Triplet<L, M, R>> triplets = new ArrayList<>();
        for (L left : lefts) {
            for (M middle : middles) {
                for (R right : rights) {
                    triplets.add(Triplet.with(left, middle, right));
                }
            }
        }
        return triplets;
    }

    public static <A, B, C, D> List<Quartet<A, B, C, D>> combine(List<A> as, List<B> bs, List<C> cs, List<D> ds) {
        List<Quartet<A, B, C, D>> quartet = new ArrayList<>();
        for (A a : as) {
            for (B b : bs) {
                for (C c : cs) {
                    for (D d : ds) {
                        quartet.add(Quartet.with(a, b, c, d));
                    }
                }
            }
        }
        return quartet;
    }

    public static <A, B, C, D, E> List<Quintet<A, B, C, D, E>> combine(List<A> as, List<B> bs, List<C> cs, List<D> ds,
            List<E> es) {
        List<Quintet<A, B, C, D, E>> quintet = new ArrayList<>();
        for (A a : as) {
            for (B b : bs) {
                for (C c : cs) {
                    for (D d : ds) {
                        for (E e : es) {
                            quintet.add(Quintet.with(a, b, c, d, e));
                        }
                    }
                }
            }
        }
        return quintet;
    }
}
