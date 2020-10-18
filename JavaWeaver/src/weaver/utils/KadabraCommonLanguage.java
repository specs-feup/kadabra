/**
 * Copyright 2020 SPeCS.
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

package weaver.utils;

import pt.up.fe.specs.util.classmap.ClassMap;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtCompilationUnit;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtNamedElement;
import spoon.reflect.reference.CtFieldReference;
import weaver.kadabra.spoon.extensions.nodes.CtApp;

public class KadabraCommonLanguage {

    private static final ClassMap<CtElement, String> JOINPOINT_MAPPER;
    static {
        JOINPOINT_MAPPER = new ClassMap<>();
        JOINPOINT_MAPPER.put(CtFieldReference.class, "FieldRefJp");
        JOINPOINT_MAPPER.put(CtField.class, "FieldJp");
        JOINPOINT_MAPPER.put(CtInvocation.class, "MemberCallJp");
        JOINPOINT_MAPPER.put(CtExpression.class, "ExprJp");
        JOINPOINT_MAPPER.put(CtMethod.class, "MethodJp");
        JOINPOINT_MAPPER.put(CtNamedElement.class, "DeclJp");
        JOINPOINT_MAPPER.put(CtClass.class, "ClassJp");
        JOINPOINT_MAPPER.put(CtCompilationUnit.class, "FileJp");
        JOINPOINT_MAPPER.put(CtApp.class, "ProgramJp");
        JOINPOINT_MAPPER.put(CtElement.class, "JoinPoint");
    }

    public static String getJoinPointName(CtElement node) {
        return JOINPOINT_MAPPER.get(node.getClass());
    }

}
