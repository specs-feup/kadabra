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

import java.util.List;

import spoon.reflect.code.CtCase;
import spoon.reflect.code.CtSwitch;
import weaver.kadabra.abstracts.joinpoints.ACase;
import weaver.kadabra.abstracts.joinpoints.ASwitch;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JSwitch<S> extends ASwitch {

    private final CtSwitch<S> node;

    private JSwitch(CtSwitch<S> node) {
        super(new JStatement(node));
        this.node = node;
    }

    public static <S> JSwitch<S> newInstance(CtSwitch<S> node) {
        return new JSwitch<>(node);
    }

    @Override
    public List<? extends ACase> selectCase() {
        List<CtCase<? super S>> cases = node.getCases();
        return SelectUtils.nodeList2JoinPointList(cases, JCase::newInstance);
    }

    @Override
    public CtSwitch<S> getNode() {
        return node;
    }

    @Override
    public ACase[] getCasesArrayImpl() {
        return CtElement2JoinPoint.convertList(node.getCases(), ACase.class);
    }

}
