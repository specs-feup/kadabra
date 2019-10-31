/**
 * Copyright 2015 SPeCS.
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

import spoon.reflect.code.CtFieldAccess;

public class JFieldAccess<T> extends JVar<T> {

    private final CtFieldAccess<T> node;

    protected JFieldAccess(CtFieldAccess<T> var) {
        super(var);
        node = var;
    }

    public static <T> JFieldAccess<T> newInstance(CtFieldAccess<T> var) {
        return new JFieldAccess<>(var);
    }

    @Override
    public String getNameImpl() {
        return node.toString();
    }

}
