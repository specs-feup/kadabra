/**
 * Copyright 2016 SPeCS.
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

import spoon.reflect.code.CtAssert;
import weaver.kadabra.abstracts.joinpoints.AAssert;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.utils.weaving.SelectUtils;

public class JAssert<T> extends AAssert {

    private final CtAssert<T> node;

    private JAssert(CtAssert<T> node) {
        super(new JStatement(node));
        this.node = node;
    }

    public static <T> JAssert<T> newInstance(CtAssert<T> node) {
        return new JAssert<>(node);
    }

    @Override
    public List<? extends AExpression> selectExpression() {
        return SelectUtils.expression2JoinPointList(node.getExpression());
    }

    @Override
    public List<? extends AExpression> selectExpr() {
        return selectExpression();
    }

    @Override
    public CtAssert<T> getNode() {
        return node;
    }
}
