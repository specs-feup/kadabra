package weaver.utils.scanners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import spoon.processing.Processor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.CtScanner;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.utils.weaving.SelectUtils;

/**
 * Make a depth-first search (DFS) to search for nodes of a specific class extending {@link CtElement} starting in the
 * given node and ending on the leaves, or at the cutting node classes given as input
 * 
 * Copyright 2015 SPeCS Research Group.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * @param <T>
 *            Type of node to process, extending {@link CtElement}
 */
public class NodeSearcher extends CtScanner {

    // IR type to search
    private Class<? extends CtElement> search;
    // IR types to cut in the search, i.e., don't search deeper
    private final Collection<Class<? extends CtElement>> cut;
    private final NodeProcessor<? extends CtElement> processor;
    private final Collection<Class<? extends CtElement>> ignore;

    /**
     * Create a DFS without cutting nodes
     */
    public <T extends CtElement> NodeSearcher(Class<T> searchClass, NodeProcessor<T> processor) {
	this(searchClass, processor, Collections.emptyList(), Collections.emptyList());
    }

    /**
     * Create a DFS with cutting nodes
     */
    public <T extends CtElement> NodeSearcher(Class<T> searchClass, NodeProcessor<T> processor,

	    Collection<Class<? extends CtElement>> ignore,
	    Collection<Class<? extends CtElement>> cut) {
	this.processor = processor;
	search = searchClass;
	this.cut = cut;
	this.ignore = ignore;
    }

    private boolean canBeProcessed(CtElement e) {

	// if (search.isAssignableFrom(e.getClass())) {
	if (search.isInstance(e) && !ignoreIfInstance(e)) {
	    return true;
	}
	// }
	return false;
    }

    @Override
    public void scan(Collection<? extends CtElement> elements) {
	if ((elements != null)) {
	    for (final CtElement e : new ArrayList<CtElement>(elements)) {
		scan(e);
	    }
	}
    }

    /**
     * Applies the processing to the given element. To apply the processing, this method upcalls, for all the registered
     * processor in, the {@link Processor#process(CtElement)} method if {@link Processor#isToBeProcessed(CtElement)}
     * returns true.
     */
    @Override
    public void scan(CtElement e) {
	if (e == null) {
	    return;
	}
	if (canBeProcessed(e)) {
	    processor.processRaw(e);
	}
	if (!pruneIfInstance(e)) {
	    super.scan(e);
	}

    }

    public boolean pruneIfInstance(CtElement e) {

	if (cut.isEmpty()) {
	    return false;
	}

	Class<? extends CtElement> eClass = e.getClass();
	// return cut.contains(class1); // this is not enough with subclasses!
	for (Class<?> prunner : cut) {
	    if (prunner.isAssignableFrom(eClass)) {
		return true;
	    }
	}
	return false;
    }

    public boolean ignoreIfInstance(CtElement e) {

	if (ignore.isEmpty()) {
	    return false;
	}
	Class<? extends CtElement> eClass = e.getClass();
	// return cut.contains(class1); // this is not enough with subclasses!
	for (Class<?> ignoreClass : ignore) {
	    if (ignoreClass.isAssignableFrom(eClass)) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Scan the root element in search of nodes of the given class type and process them with the given processor
     * 
     * @param root
     * @param searchClass
     * @param processor
     */
    public static <T extends CtElement> void scan(CtElement root, Class<T> searchClass, NodeProcessor<T> processor) {
	scan(root, searchClass, processor, Collections.emptyList(), Collections.emptyList());
    }

    public static <T extends CtElement> void scan(CtElement root, Class<T> searchClass, NodeProcessor<T> processor,
	    Collection<Class<? extends CtElement>> ignore,
	    Collection<Class<? extends CtElement>> cuts) {
	new NodeSearcher(searchClass, processor, ignore, cuts).scan(root);
    }

    /**
     * Scan the root element in search of nodes of the given class type and process them with the given processor
     * 
     * @param root
     * @param searchClass
     * @param processor
     */
    public static <T extends CtElement> List<T> list(Class<T> searchClass, CtElement root,
	    Collection<Class<? extends CtElement>> ignore) {
	return list(searchClass, root, ignore, Collections.emptyList());
    }

    /**
     * Scan the root element in search of nodes of the given class type and process them with the given processor.
     * 
     * @param root
     * @param searchClass
     * @param processor
     * @param prune
     *            a list of types to prune on
     */
    public static <T extends CtElement> List<T> list(Class<T> searchClass, CtElement root,
	    Collection<Class<? extends CtElement>> ignore,
	    Collection<Class<? extends CtElement>> prune) {
	final List<T> elements = new ArrayList<>();
	NodeSearcher.scan(root, searchClass, elements::add, ignore, prune);

	return elements;
    }

    public static <T extends CtElement, V extends AJavaWeaverJoinPoint> List<V> searchAndConvert(Class<T> searchClass,
	    CtElement root, NodeConverter<T, V> converter) {
	return searchAndConvert(searchClass, root, converter, Collections.emptyList(), Collections.emptyList());
    }

    public static <T extends CtElement, V extends AJavaWeaverJoinPoint> List<V> searchAndConvert(Class<T> searchClass,
	    CtElement root, NodeConverter<T, V> converter, Collection<Class<? extends CtElement>> ignore,
	    Collection<Class<? extends CtElement>> prune) {

	final List<T> elements = list(searchClass, root, ignore, prune);
	final List<V> joinPoints = SelectUtils.nodeList2JoinPointList(elements, converter);

	// List<V> joinPoints = list(searchClass, root).stream()
	// .map(e -> converter.toJoinPoint(e, factory))
	// .collect(Collectors.toList());

	return joinPoints;
    }

    @Override
    protected void enter(CtElement e) {
	super.enter(e);
    }

    // public static <T extends CtElement, V extends AJoinPoint> List<V>
    // nodes2JoinPointList(Class<T> searchClass,
    // Class<V> convertClass,
    // CtElement root,
    // DFSJoinPointConverter<T, V> converter) {
    //
    // List<V> elements = list(searchClass, root).stream()
    // .map(p -> converter.processRaw(p))
    // .collect(Collectors.toList());
    //
    // return elements;
    // }

    /**
     * Process a found target node
     * 
     * @param e
     *            the node that was found in the IR
     */
    // public abstract void process(T e);
    /*
     * @SuppressWarnings("unchecked") private Class<T> getSearchType() {
     * 
     * for (Method m : processor.getClass().getMethods()) { System.out.println(
     * "class " + processor.getClass() + " method: " + m.getName() + " (" +
     * Arrays.toString(m.getParameterTypes()) + ")"); if
     * (m.getName().equals("process") && (m.getParameterTypes().length == 1)) {
     * Class<?> c = m.getParameterTypes()[0]; if
     * (CtElement.class.isAssignableFrom(c)) { System.out.println("CLASS:" + c);
     * // if (this.search != null) { // throw new RuntimeException(
     * "Only one method named \"process\" is allowed per class"); // } return
     * ((Class<T>) c); } } }
     * 
     * throw new RuntimeException(
     * "The type of the search node must extend/be CtElement");
     * 
     * }
     */
}