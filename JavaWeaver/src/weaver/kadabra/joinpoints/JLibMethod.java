/**
 * Copyright 2018 SPeCS.
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

package weaver.kadabra.joinpoints;

import java.lang.reflect.Method;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ALibMethod;
import weaver.kadabra.abstracts.joinpoints.ANamedType;
import weaver.kadabra.exceptions.JavaWeaverException;

public class JLibMethod<Self extends JLibMethod<Self>> extends ALibMethod<Self> {

    private final Method method;

    public JLibMethod(Method m, JWeaver weaver) {
        super((spoon.reflect.declaration.CtElement) null, weaver);
        this.method = m;
    }

    @Override
    public spoon.reflect.declaration.CtElement getNodeImpl() {
        // Lib methods have no Spoon node
        return null;
    }

    @Override
    public String getNameImpl() {
        return method.getName();
    }

    @Override
    public ANamedType<?> getDeclaratorImpl() {
        throw new JavaWeaverException("Select declaration in LibMethod not implemented");
    }

    @Override
    public String getReturnTypeImpl() {
        return method.getReturnType().getName();
    }

}
