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

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

import pt.up.fe.specs.util.classmap.BiFunctionClassMap;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtCompilationUnit;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtEnumValue;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.reference.CtReference;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.joinpoints.JApp;
import weaver.kadabra.joinpoints.JBody;
import weaver.kadabra.joinpoints.JCatch;
import weaver.kadabra.joinpoints.JComment;
import weaver.kadabra.joinpoints.JDeclaration;
import weaver.kadabra.joinpoints.JEnumValue;
import weaver.kadabra.joinpoints.JField;
import weaver.kadabra.joinpoints.JFile;
import weaver.kadabra.joinpoints.JGenericJoinPoint;
import weaver.kadabra.joinpoints.JReference;
import weaver.kadabra.joinpoints.JTypeReference;
import weaver.kadabra.spoon.extensions.nodes.CtApp;

/**
 * Converts a given element to the correct Join point type
 *
 * @author tiago
 *
 */
public class CtElement2JoinPoint {
    private static final BiFunctionClassMap<CtElement, JavaWeaver, AJavaWeaverJoinPoint> CONVERTER = new BiFunctionClassMap<>();

    static {
        // Elements without specific converter
        CONVERTER.put(CtCatch.class, JCatch::new);
        CONVERTER.put(CtBlock.class, JBody::newInstance);
        CONVERTER.put(CtEnumValue.class, JEnumValue::newInstance);
        CONVERTER.put(CtField.class, JField::newInstance);
        CONVERTER.put(CtVariable.class, JDeclaration::newInstance);
        CONVERTER.put(CtComment.class, JComment::newInstance);
        CONVERTER.put(CtCompilationUnit.class, JFile::new);
        CONVERTER.put(CtApp.class, JApp::newInstance);

        // Elements with own converter
        CONVERTER.put(CtExecutable.class, CtExecutable2AExecutable::convert);
        CONVERTER.put(CtType.class, CtType2AType::convert);
        CONVERTER.put(CtInvocation.class, CtStatement2AStatement::convert);
        CONVERTER.put(CtStatement.class, CtStatement2AStatement::convert);
        CONVERTER.put(CtExpression.class, CtExpression2AExpression::convert);
        CONVERTER.put(CtTypeReference.class, JTypeReference::newInstance);
        CONVERTER.put(CtReference.class, JReference::newInstance);

        CONVERTER.put(CtElement.class, CtElement2JoinPoint::defaultFactory);
    }

    public static AJavaWeaverJoinPoint defaultFactory(CtElement element, JavaWeaver weaver) {
        return JGenericJoinPoint.newInstance(element, weaver);
    }

    public static AJavaWeaverJoinPoint convert(CtElement element, JavaWeaver weaver) {
        if (element == null) {
            return null;
        }

        return CONVERTER.apply(element, weaver);
    }

    public static <T extends AJavaWeaverJoinPoint> T convert(CtElement element, JavaWeaver weaver, Class<T> jpClass) {
        var jp = convert(element, weaver);
        return jpClass.cast(jp);
    }

    public static Optional<AJavaWeaverJoinPoint> convertTry(CtElement element, JavaWeaver weaver) {
        try {
            return Optional.ofNullable(CONVERTER.apply(element, weaver));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static <T extends AJavaWeaverJoinPoint> T[] convertList(List<? extends CtElement> elements,
            JavaWeaver weaver, Class<T> jpClass) {

        @SuppressWarnings("unchecked")
        T[] jps = (T[]) Array.newInstance(jpClass, elements.size());

        for (int i = 0; i < elements.size(); i++) {
            jps[i] = convert(elements.get(i), weaver, jpClass);
        }

        return jps;
    }

}
