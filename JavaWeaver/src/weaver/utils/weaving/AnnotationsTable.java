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
package weaver.utils.weaving;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtTypeMember;
import weaver.utils.visitors.TypeMemberKeyGenerator;

public class AnnotationsTable {

    private static final AnnotationsTable ANNOT_TABLE = new AnnotationsTable();

    // Change to CtTypedElement<?>, Snippet
    private Map<String, List<CtElement>> before;
    private Map<String, List<CtElement>> after;
    private Map<String, List<CtElement>> replace;
    // private TypeMemberKeyGeneratorVisitor keyGenerator;

    // Keep track of added snippets
    private Set<CtElement> snippets;

    public static AnnotationsTable getStaticTable() {
        return ANNOT_TABLE;
    }

    public AnnotationsTable() {
        before = new HashMap<>();
        after = new HashMap<>();
        replace = new HashMap<>();
        // keyGenerator = new TypeMemberKeyGeneratorVisitor();

        this.snippets = new HashSet<>();
    }

    public void reset() {
        before.clear();
        after.clear();
        replace.clear();
    }

    /**********************************
     * Methods for specific insertion *
     **********************************/

    /***********
     * Getters *
     ***********/
    /**
     * Returns the annotations before the target statement
     * 
     * @param target
     * @return
     */
    public List<CtElement> getBefore(CtElement target) {
        return get(before, target);
    }

    /**
     * Returns the annotations after the target statement <br>
     * <b>NOTE:<b> for code insertion the returned list should be reversely iterated
     * 
     * @param target
     * @return
     */
    public List<CtElement> getAfter(CtElement target) {
        return get(after, target);
    }

    /**
     * Returns the annotations to replace the target statement
     * 
     * @param target
     * @return
     */
    public List<CtElement> getReplace(CtElement target) {
        return get(replace, target);
    }

    /**
     * Returns the annotations after the target statement in reversed order
     * 
     * @param target
     * @return
     */
    public List<CtElement> getReversedAfter(CtElement target) {
        List<CtElement> afters = get(after, target);
        if (afters.isEmpty()) {
            return afters;
        }
        List<CtElement> reversed = new ArrayList<>(afters);
        Collections.reverse(reversed);
        return reversed;
    }

    /**********
     * Removers *
     **********/

    /**
     * If the snippet is found in any of the annotations, removes it.
     * 
     * @param snippet
     * @return number of removals
     */
    public void remove(CtElement snippet) {
        // If the snippet is not present, return immediately
        if (!snippets.contains(snippet)) {
            return;
        }

        before.values().stream()
                .forEach(list -> {
                    while (list.remove(snippet))
                        ;
                });
        after.values().stream()
                .forEach(list -> {
                    while (list.remove(snippet))
                        ;
                });
        replace.values().stream()
                .forEach(list -> {
                    while (list.remove(snippet))
                        ;
                });

    }

    /**********
     * Adders *
     **********/
    /**
     * Adds an annotation before the target element
     * 
     * @param target
     * @param snippet
     * @return
     */
    public int addBefore(CtElement target, CtElement snippet) {
        return add(before, target, snippet);
    }

    /**
     * Adds an annotation after the target element
     * 
     * @param target
     * @param snippet
     * @return
     */
    public int addAfter(CtElement target, CtElement snippet) {
        return add(after, target, snippet);
    }

    /**
     * Adds an annotation after the target element
     * 
     * @param target
     * @param snippet
     * @return
     */
    public int addReplace(CtElement target, CtElement snippet) {
        return add(replace, target, snippet);
    }

    /************
     * Contains *
     ************/
    /**
     * true if contains annotation before OR after the target node
     * 
     * @param target
     * @return
     */
    public boolean contains(CtElement target) {
        return containsBefore(target) || containsAfter(target) || containsReplace(target);
    }

    /**
     * true if contains annotations after target node
     * 
     * @param target
     * @return
     */
    public boolean containsAfter(CtElement target) {
        return contains(after, target);
    }

    /**
     * true if contains annotations before target node
     * 
     * @param target
     * @return
     */
    public boolean containsBefore(CtElement target) {
        return contains(before, target);
    }

    /**
     * true if contains annotations to replace the target node
     * 
     * @param target
     * @return
     */
    public boolean containsReplace(CtElement target) {
        return contains(replace, target);
    }

    /******************
     * Static Methods *
     ******************/
    /**
     * 
     * @param map
     * @param target
     * @return
     */
    private static List<CtElement> get(Map<String, List<CtElement>> map, CtElement target) {
        String key = getKey(target);
        return map.getOrDefault(key, Collections.emptyList());
    }

    private static boolean contains(Map<String, List<CtElement>> map, CtElement target) {
        String key = getKey(target);
        return map.containsKey(key);
    }

    private int add(Map<String, List<CtElement>> map, CtElement target, CtElement snippet) {
        // Bookkeeping
        snippets.add(snippet);

        String key = getKey(target);
        List<CtElement> annots;
        if (map.containsKey(key)) {
            annots = map.get(key);
        } else {
            // map.put(key, annots = new ArrayList<>());
            map.put(key, annots = new ArrayList<>());
        }
        annots.add(snippet);
        return annots.size();
    }

    private static String getKey(CtElement target) {
        if (target instanceof CtTypeMember) {
            return TypeMemberKeyGenerator.generate((CtTypeMember) target);
        }
        return target.hashCode() + "@" + target.getPosition();
    }

}
