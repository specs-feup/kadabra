/**
 * Copyright 2019 SPeCS.
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

import java.util.ArrayList;

import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.ANew;
import weaver.utils.weaving.SelectUtils;

public class JNew<Self extends JNew<Self>> extends ANew<Self> {

    public JNew(CtConstructorCall node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public CtConstructorCall<?> getNodeImpl() {
        return (CtConstructorCall<?>) super.getNodeImpl();
    }

    @Override
    public String getNameImpl() {
        return getNodeImpl().getExecutable().getSimpleName();
    }

    @Override
    public AExpression<?>[] getArgumentsImpl() {
        return SelectUtils.nodeList2JoinPointList(getNodeImpl().getArguments(),
                arg -> JExpression.newInstance(arg, getWeaverEngine()))
                .toArray(new AExpression[0]);
    }

    @Override
    public void setArgumentsImpl(AExpression<?>[] newArguments) {
        var newArgs = new ArrayList<CtExpression<?>>();
        for (var arg : newArguments) {
            newArgs.add((CtExpression<?>) arg.getNodeImpl());
        }

        getNodeImpl().setArguments(newArgs);
    }
}
