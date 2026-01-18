/**
 * Copyright 2016 SPeCS.
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

package weaver.kadabra.joinpoints;

import spoon.reflect.code.CtReturn;
import weaver.kadabra.abstracts.joinpoints.AReturn;

public class JReturn<T> extends AReturn {

    private final CtReturn<T> node;

    public static <T> JReturn<T> newInstance(CtReturn<T> node) {
	return new JReturn<>(node);
    }

    private JReturn(CtReturn<T> node) {
	super(new JStatement(node));
	this.node = node;
    }

    @Override
    public CtReturn<T> getNode() {
	return node;
    }

}
