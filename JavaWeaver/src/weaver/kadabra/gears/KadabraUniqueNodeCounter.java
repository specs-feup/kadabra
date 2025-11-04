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

package weaver.kadabra.gears;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.lara.interpreter.weaver.interf.JoinPoint;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtNamedElement;
import weaver.kadabra.joinpoints.JApp;

public class KadabraUniqueNodeCounter {

    public Map<String, Set<Object>> map;

    public KadabraUniqueNodeCounter() {
        this.map = new HashMap<>();
    }

    public long getTotalNodes() {
        Optional<Integer> reduce = map.values().stream().map(Set::size)
                .reduce((acc, curr) -> acc + curr);
        return reduce.orElse(0);
    }

    public void addNode(String key, JoinPoint joinPoint) {
        if (!map.containsKey(key)) {
            map.put(key, new HashSet<>());
        }
        Set<Object> source = map.get(key);
        if (joinPoint instanceof JApp) {
            source.add(joinPoint);
            // } else if (joinPoint instanceof JFile) {
            // source.add(((JFile) joinPoint).getPathImpl());
        } else {
            source.add(joinPoint.getNode());
        }
    }

    public void addIteration(String key, List<JoinPoint> joinpoints) {
        for (JoinPoint joinPoint : joinpoints) {
            addNode(key, joinPoint);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String strKey : map.keySet()) {
            builder.append(strKey + ": {");
            for (Object key : map.get(strKey)) {
                if (key instanceof CtNamedElement) {
                    builder.append("\t" + key.getClass() + "#" + ((CtNamedElement) key).getSimpleName() + "@"
                            + key.hashCode() + "\n");
                } else if (key instanceof CtElement) {
                    builder.append("\t" + key.getClass() + "@" + key.hashCode() + "\n");
                } else {
                    builder.append("\t" + key + "@" + key.hashCode() + "\n");
                }
            }
            builder.append("}");
        }
        return builder.toString();
    }
}
