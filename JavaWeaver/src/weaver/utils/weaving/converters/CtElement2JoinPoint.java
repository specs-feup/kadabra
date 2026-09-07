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
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.joinpoints.JApp;
import weaver.kadabra.joinpoints.JBody;
import weaver.kadabra.joinpoints.JCatch;
import weaver.kadabra.joinpoints.JComment;
import weaver.kadabra.joinpoints.JDeclaration;
import weaver.kadabra.joinpoints.JEnumValue;
import weaver.kadabra.joinpoints.JField;
import weaver.kadabra.joinpoints.JFile;
import weaver.kadabra.joinpoints.JJoinpoint;
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
    private static final BiFunctionClassMap<CtElement, JWeaver, AJoinpoint<?>> CONVERTER = new BiFunctionClassMap<>();

    static {
        // Elements without specific converter
        CONVERTER.put(CtCatch.class, JCatch::new);
        CONVERTER.put(CtBlock.class, JBody::new);
        CONVERTER.put(CtEnumValue.class, JEnumValue::new);
        CONVERTER.put(CtField.class, JField::new);
        CONVERTER.put(CtVariable.class, JDeclaration::new);
        CONVERTER.put(CtComment.class, JComment::new);
        CONVERTER.put(CtCompilationUnit.class, JFile::new);
        CONVERTER.put(CtApp.class, JApp::new);

        // Elements with own converter
        CONVERTER.put(CtExecutable.class, CtExecutable2AExecutable::convert);
        CONVERTER.put(CtType.class, CtType2AType::convert);
        CONVERTER.put(CtInvocation.class, CtStatement2AStatement::convert);
        CONVERTER.put(CtStatement.class, CtStatement2AStatement::convert);
        CONVERTER.put(CtExpression.class, CtExpression2AExpression::convert);
        CONVERTER.put(CtTypeReference.class, JTypeReference::new);
        CONVERTER.put(CtReference.class, JReference::new);

        CONVERTER.put(CtElement.class, CtElement2JoinPoint::defaultFactory);
    }

    public static AJoinpoint<?> defaultFactory(CtElement element, JWeaver weaver) {
        return new JJoinpoint<>(element, weaver);
    }

    public static AJoinpoint<?> convert(CtElement element, JWeaver weaver) {
        if (element == null) {
            return null;
        }

        return CONVERTER.apply(element, weaver);
    }

    public static <T extends AJoinpoint<?>> T convert(CtElement element, JWeaver weaver, Class<T> jpClass) {
        var jp = convert(element, weaver);
        return jpClass.cast(jp);
    }

    public static Optional<AJoinpoint<?>> convertTry(CtElement element, JWeaver weaver) {
        try {
            return Optional.ofNullable(CONVERTER.apply(element, weaver));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static <T extends AJoinpoint<?>> T[] convertList(List<? extends CtElement> elements,
            JWeaver weaver, Class<T> jpClass) {

        @SuppressWarnings("unchecked")
        T[] jps = (T[]) Array.newInstance(jpClass, elements.size());

        for (int i = 0; i < elements.size(); i++) {
            jps[i] = convert(elements.get(i), weaver, jpClass);
        }

        return jps;
    }

}
