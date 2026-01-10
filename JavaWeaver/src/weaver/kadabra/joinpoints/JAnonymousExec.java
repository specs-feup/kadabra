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
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.joinpoints;

import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.joinpoints.AAnonymousExec;

public class JAnonymousExec extends AAnonymousExec {

    private CtAnonymousExecutable node;

    public JAnonymousExec(CtAnonymousExecutable executable, JavaWeaver weaver) {
        super(JExecutable.newInstance(executable, weaver), weaver);
        this.node = executable;
    }

    public static JAnonymousExec newInstance(CtAnonymousExecutable executable, JavaWeaver weaver) {
        return new JAnonymousExec(executable, weaver);
    }

    @Override
    public CtElement getNode() {
        return node;
    }

}
