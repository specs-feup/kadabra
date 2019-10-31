/**
 * Copyright 2018 SPeCS.
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

import java.lang.reflect.Method;
import java.util.List;

import spoon.reflect.declaration.CtElement;
import weaver.kadabra.abstracts.joinpoints.ADeclaration;
import weaver.kadabra.abstracts.joinpoints.ALibMethod;
import weaver.kadabra.abstracts.joinpoints.ANamedType;
import weaver.kadabra.exceptions.JavaWeaverException;

public class JLibMethod extends ALibMethod {

    private Method method;

    private JLibMethod(Method m) {
        method = m;
    }

    public static JLibMethod newInstance(Method m) {
        return new JLibMethod(m);
    }

    @Override
    public List<? extends ADeclaration> selectParam() {
        throw new JavaWeaverException("Select declaration in LibMethod not implemented");
    }

    @Override
    public CtElement getNode() {
        return null;
    }

    @Override
    public String getNameImpl() {
        return method.getName();
    }

    @Override
    public ANamedType getDeclaratorImpl() {
        throw new JavaWeaverException("Select declaration in LibMethod not implemented");
    }

    @Override
    public String getReturnTypeImpl() {
        return method.getReturnType().getName();
    }

}
