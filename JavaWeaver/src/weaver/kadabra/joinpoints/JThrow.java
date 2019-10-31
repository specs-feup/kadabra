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

import spoon.reflect.code.CtThrow;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AThrow;
import weaver.utils.weaving.SelectUtils;

public class JThrow extends AThrow {

    private final CtThrow node;

    private JThrow(CtThrow node) {
        super(new JStatement(node));
        this.node = node;
    }

    public static JThrow newInstance(CtThrow node) {
        return new JThrow(node);
    }

    @Override
    public List<? extends AExpression> selectExpression() {
        return SelectUtils.expression2JoinPointList(node.getThrownExpression());
    }

    @Override
    public List<? extends AExpression> selectExpr() {
        return selectExpression();
    }

    @Override
    public CtThrow getNode() {
        return node;
    }
}
