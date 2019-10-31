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

package weaver.utils.weaving.converters;

import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import weaver.kadabra.abstracts.joinpoints.AExecutable;
import weaver.kadabra.joinpoints.JAnonymousExec;
import weaver.kadabra.joinpoints.JConstructor;
import weaver.kadabra.joinpoints.JExecutable;
import weaver.kadabra.joinpoints.JMethod;

/**
 * Converts a given statement to the correct Join point type
 * 
 * @author tiago
 *
 */
public class CtExecutable2AExecutable {
    private static final FunctionClassMap<CtExecutable<?>, AExecutable> CONVERTER = new FunctionClassMap<>(
            JExecutable::newInstance);

    static {

        CONVERTER.put(CtMethod.class, JMethod::newInstance);
        CONVERTER.put(CtConstructor.class, JConstructor::newInstance);
        CONVERTER.put(CtAnonymousExecutable.class, JAnonymousExec::newInstance);
    }

    // Package protected so only CtElement2JoinPoint can use this method
    public static AExecutable convert(CtExecutable<?> element) {
        AExecutable converted = CONVERTER.apply(element);
        return converted;

    }
}
