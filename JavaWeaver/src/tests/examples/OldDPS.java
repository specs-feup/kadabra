package tests.examples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import spoon.processing.Processor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.Factory;
import spoon.reflect.visitor.CtScanner;

/**
 * Copyright 2015 SPeCS Research Group.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License. under the License.
 */
public class OldDPS extends CtScanner {

	// IR types to search
	private final List<Class<? extends CtElement>> search;

	// IR types to cut in the search, i.e., don't search deeper
	private final List<Class<? extends CtElement>> cut;

	private final Factory factory;

	/**
	 * The constructor.
	 */
	public OldDPS(Factory factory) {
		this.factory = factory;
		search = new ArrayList<>();
		cut = new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	public OldDPS(Factory factory, Class<? extends CtElement>... searchElements) {
		this(factory);
	}

	private boolean canBeProcessed(CtElement e) {
		if (!factory.getEnvironment().isProcessingStopped()) {
			for (final Class<? extends CtElement> class1 : search) {
				if (class1.isAssignableFrom(e.getClass())) {
					return true;
				}
			}
		}
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

	@SuppressWarnings("unchecked")
	public void scan(Collection<? extends CtElement> elements, Class<? extends CtElement>... searchElements) {
		clearSearch();
		addSearchElements(searchElements);
		if ((elements != null)) {
			for (final CtElement e : new ArrayList<CtElement>(elements)) {
				scan(e);
			}
		}
	}

	/**
	 * Applies the processing to the given element. To apply the processing,
	 * this method upcalls, for all the registered processor in, the
	 * {@link Processor#process(CtElement)} method if
	 * {@link Processor#isToBeProcessed(CtElement)} returns true.
	 */
	@Override
	public void scan(CtElement e) {
		if (e == null) {
			return;
		}
		if (canBeProcessed(e)) {
			// process(e);
		}
		super.scan(e);

	}

	/**
	 * Add an array of node types to search for
	 * 
	 * @param searchElements
	 */
	@SuppressWarnings("unchecked")
	private void addSearchElements(Class<? extends CtElement>... searchElements) {
		for (final Class<? extends CtElement> class1 : searchElements) {
			search.add(class1);
		}
	}

	@SuppressWarnings("unchecked")
	public void addCutElements(Class<? extends CtElement>... cutElements) {
		for (final Class<? extends CtElement> class1 : cutElements) {
			cut.add(class1);
		}
	}

	public void clearSearch() {
		search.clear();
	}

	public void clearCut() {
		cut.clear();
	}
}