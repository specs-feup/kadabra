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
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtType;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.joinpoints.AType;
import weaver.kadabra.joinpoints.JClass;
import weaver.kadabra.joinpoints.JInterfaceType;
import weaver.kadabra.joinpoints.JType;

/**
 * Converts a given statement to the correct Join point type
 *
 * @author tiago
 *
 */
public class CtType2AType {
    private static final BiFunctionClassMap<CtType<?>, JavaWeaver, AType> CONVERTER = new BiFunctionClassMap<>();

    static {
        CONVERTER.put(CtClass.class, JClass::newInstance);
        CONVERTER.put(CtInterface.class, JInterfaceType::newInstance);
        CONVERTER.put(CtType.class, CtType2AType::defaultFactory);
    }

    public static AType defaultFactory(CtType<?> element, JavaWeaver weaver) {
        return JType.newInstance(element, null, weaver);
    }

    // Package protected so only CtElement2JoinPoint can use this method
    public static AType convert(CtType<?> element, JavaWeaver weaver) {
        return CONVERTER.apply(element, weaver);
    }
}
