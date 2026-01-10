/**
 * Copyright 2022 SPeCS.
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

package weaver.kadabra.joinpoints;

import spoon.reflect.code.CtThisAccess;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.joinpoints.AThis;

public class JThis extends AThis {

    private final CtThisAccess<?> thisNode;

    public JThis(CtThisAccess<?> thisNode, JavaWeaver weaver) {
        super(new JExpression<>(thisNode, weaver), weaver);

        this.thisNode = thisNode;
    }

    @Override
    public CtElement getNode() {
        return thisNode;
    }

}
