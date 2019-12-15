/**
 * Copyright 2019 SPeCS.
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

package weaver.kadabra.joinpoints;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.abstracts.joinpoints.ATypeReference;

public class JTypeReference<T> extends ATypeReference {

    private final CtTypeReference<T> typeReference;

    public JTypeReference(CtTypeReference<T> typeReference) {
        super(new JReference(typeReference));

        this.typeReference = typeReference;
    }

    public static <T> JTypeReference<T> newInstance(CtTypeReference<T> node) {
        return new JTypeReference<>(node);
    }

    @Override
    public CtElement getNode() {
        return typeReference;
    }

    @Override
    public Boolean getIsPrimitiveImpl() {
        return typeReference.isPrimitive();
    }

}
