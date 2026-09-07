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

import spoon.reflect.code.CtSwitch;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ACase;
import weaver.kadabra.abstracts.joinpoints.ASwitch;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JSwitch<Self extends JSwitch<Self>> extends ASwitch<Self> {

    public JSwitch(CtSwitch node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public CtSwitch<?> getNodeImpl() {
        return (CtSwitch<?>) super.getNodeImpl();
    }

    @Override
    public ACase<?>[] getCasesImpl() {
        return CtElement2JoinPoint.convertList(getNodeImpl().getCases(), getWeaverEngine(), ACase.class);
    }

}
