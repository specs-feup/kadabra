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

import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.abstracts.joinpoints.ANew;

public class JNew<T> extends ANew {

    private final CtConstructorCall<T> constructorCall;

    public JNew(CtConstructorCall<T> constructorCall) {
        super(new JExpression<>(constructorCall));

        this.constructorCall = constructorCall;
    }

    public static <T> JNew<T> newInstance(CtConstructorCall<T> node) {
        return new JNew<>(node);
    }

    @Override
    public CtElement getNode() {
        return constructorCall;
    }

}
