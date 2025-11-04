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
 * specific language governing permissions and limitations under the License.
 */

package weaver.utils.visitors;

import pt.up.fe.specs.util.SpecsLogs;
import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtTypeMember;

public class TypeMemberKeyGenerator {

    private static final FunctionClassMap<CtTypeMember, String> KEY_GENERATOR;

    static {
        KEY_GENERATOR = new FunctionClassMap<>(TypeMemberKeyGenerator::generateNormalKey);
        KEY_GENERATOR.put(CtType.class, TypeMemberKeyGenerator::qualifiedName);
        KEY_GENERATOR.put(CtAnonymousExecutable.class, TypeMemberKeyGenerator::anonymousKey);
        KEY_GENERATOR.put(CtConstructor.class, TypeMemberKeyGenerator::constructor);
        KEY_GENERATOR.put(CtMethod.class, TypeMemberKeyGenerator::method);
        KEY_GENERATOR.put(CtField.class, TypeMemberKeyGenerator::field);
    }

    public static String generate(CtTypeMember member) {
        return KEY_GENERATOR.apply(member);
    }

    /**
     * Generate a key based on the qualified name of "m" owner and the short representation of "m"
     * 
     * @param m
     * @return
     */
    private static String generateNormalKey(CtTypeMember m) {
        SpecsLogs.info("TypeMemberKeyGenerator: using default key for type " + m.getClass()
                + ", please add a custom generator");
        return m.getDeclaringType().getQualifiedName() + "#" + m.getShortRepresentation();
    }

    /**
     * Generate a key based on the qualified name of t
     * 
     * @param m
     * @return
     */
    private static String qualifiedName(CtType<?> t) {
        return t.getQualifiedName();
    }

    /**
     * TODO
     * 
     * @param m
     * @return
     */
    private static String anonymousKey(CtAnonymousExecutable e) {
        // SourcePosition position = e.getPosition();
        // if (position != null) {
        // return position.toString();
        // }
        return e.getSignature();
    }

    /**
     * 
     * @param m
     * @return
     */
    private static String constructor(CtConstructor<?> e) {
        return e.getDeclaringType().getQualifiedName() + "#" + e.getSignature();
    }

    /**
     * 
     * @param m
     * @return
     */
    private static String method(CtMethod<?> e) {
        return e.getDeclaringType().getQualifiedName() + "#" + e.getSignature();
    }

    /**
     * 
     * @param m
     * @return
     */
    private static String field(CtField<?> e) {
        return e.getDeclaringType().getQualifiedName() + "#" + e.getSimpleName();
    }
}
