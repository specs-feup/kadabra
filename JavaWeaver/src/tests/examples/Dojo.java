package tests.examples;

import java.util.ArrayList;

/**
 * Copyright 2015 SPeCS.
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
public class Dojo extends ArrayList<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int b;
	public double c;

	void foo(Object t, int i) {
		int a;
		a = 2 + 3;
		t.hashCode();
		b = i + 3;
		b = i - 5;
		b = i - 98;
		b = b + i + a;
	}

}

enum Test {
	first, second, third;
}