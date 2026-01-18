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

import spoon.reflect.code.CtCodeSnippetExpression;
import weaver.kadabra.abstracts.joinpoints.ASnippetExpr;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetExpression;

public class JSnippetExpr extends ASnippetExpr {

    private CtKadabraSnippetExpression<?> node;

    private JSnippetExpr(CtKadabraSnippetExpression<?> expr) {
        super(new JExpression(expr.getOriginal()));
        this.node = expr;
    }

    public static JSnippetExpr newInstance(CtKadabraSnippetExpression<?> expr) {
        return new JSnippetExpr(expr);
    }

    @Override
    public CtCodeSnippetExpression<?> getNode() {
        return node.getOriginal();
    }

    @Override
    public void setLineImpl(Integer line) {
        node.setLine(line);
    }

    @Override
    public Integer getLineImpl() {
        return node.getLine().orElse(null);
    }
}
