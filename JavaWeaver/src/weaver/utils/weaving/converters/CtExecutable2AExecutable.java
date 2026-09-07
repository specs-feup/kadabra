/**
 * Copyright 2017 SPeCS.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package weaver.utils.weaving.converters;

import pt.up.fe.specs.util.classmap.BiFunctionClassMap;
import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import weaver.kadabra.JWeaver;
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
    private static final BiFunctionClassMap<CtExecutable<?>, JWeaver, AExecutable<?>> CONVERTER = new BiFunctionClassMap<>();

    static {

        CONVERTER.put(CtMethod.class, JMethod::new);
        CONVERTER.put(CtConstructor.class, JConstructor::new);
        CONVERTER.put(CtAnonymousExecutable.class, JAnonymousExec::new);
        CONVERTER.put(CtExecutable.class, CtExecutable2AExecutable::defaultFactory);
    }

    public static AExecutable<?> defaultFactory(CtExecutable<?> element, JWeaver weaver) {
        return new JExecutable<>(element, weaver);
    }

    // Package protected so only CtElement2JoinPoint can use this method
    public static AExecutable<?> convert(CtExecutable<?> element, JWeaver weaver) {
        AExecutable<?> converted = CONVERTER.apply(element, weaver);
        return converted;
    }
}
