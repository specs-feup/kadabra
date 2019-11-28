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

import java.util.Optional;

import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.reference.CtReference;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.joinpoints.JBody;
import weaver.kadabra.joinpoints.JComment;
import weaver.kadabra.joinpoints.JDeclaration;
import weaver.kadabra.joinpoints.JField;
import weaver.kadabra.joinpoints.JGenericJoinPoint;
import weaver.kadabra.joinpoints.JReference;

/**
 * Converts a given element to the correct Join point type
 * 
 * @author tiago
 *
 */
public class CtElement2JoinPoint {
    private static final FunctionClassMap<CtElement, AJavaWeaverJoinPoint> CONVERTER = new FunctionClassMap<>(
            JGenericJoinPoint::newInstance);

    static {

        // CONVERTER.put(CtInterface.class, JInterface::newInstance);
        // CONVERTER.put(CtClass.class, JClass::newInstance);
        // CONVERTER.put(CtMethod.class, JMethod::newInstance);
        // CONVERTER.put(CtConstructor.class, JConstructor::newInstance);

        // Elements without specific converter
        CONVERTER.put(CtBlock.class, JBody::newInstance);
        CONVERTER.put(CtField.class, JField::newInstance);
        CONVERTER.put(CtVariable.class, JDeclaration::newInstance);
        CONVERTER.put(CtComment.class, JComment::newInstance);

        // Elements with own converter
        CONVERTER.put(CtExecutable.class, CtExecutable2AExecutable::convert);
        CONVERTER.put(CtType.class, CtType2AType::convert);
        CONVERTER.put(CtStatement.class, CtStatement2AStatement::convert);
        CONVERTER.put(CtExpression.class, CtExpression2AExpression::convert);
        CONVERTER.put(CtReference.class, JReference::newInstance);

        // CONVERTER.setDefaultFunction(element -> null);
        /**/
        CONVERTER.setDefaultFunction(JGenericJoinPoint::newInstance);
    }

    public static AJavaWeaverJoinPoint convert(CtElement element) {
        return CONVERTER.apply(element);

    }

    public static Optional<AJavaWeaverJoinPoint> convertTry(CtElement element) {
        try {
            return Optional.ofNullable(CONVERTER.apply(element));
        } catch (Exception e) {
            return Optional.empty();
        }

    }

}
