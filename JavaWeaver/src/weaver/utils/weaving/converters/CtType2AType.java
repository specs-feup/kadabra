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
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtType;
import weaver.kadabra.abstracts.joinpoints.AType;
import weaver.kadabra.joinpoints.JClass;
import weaver.kadabra.joinpoints.JInterface;
import weaver.kadabra.joinpoints.JType;

/**
 * Converts a given statement to the correct Join point type
 * 
 * @author tiago
 *
 */
public class CtType2AType {
    private static final FunctionClassMap<CtType<?>, AType> CONVERTER = new FunctionClassMap<>(
            type -> JType.newInstance(type, null));

    static {

        CONVERTER.put(CtClass.class, JClass::newInstance);
        CONVERTER.put(CtInterface.class, JInterface::newInstance);
    }

    // Package protected so only CtElement2JoinPoint can use this method
    public static AType convert(CtType<?> element) {
        return CONVERTER.apply(element);

    }
}
