/**
 * Copyright 2021 SPeCS.
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

package pt.up.fe.specs.kadabra.parser.spoon.datafiller;

import pt.up.fe.specs.kadabra.ast.expr.Expression;
import pt.up.fe.specs.kadabra.ast.expr.LiteralExpr;
import pt.up.fe.specs.kadabra.parser.spoon.elementparser.MainParser;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLiteral;

public class ExprDataFiller extends DataFiller {

    public ExprDataFiller(MainParser mainParser) {
        super(mainParser);
    }

    public void ctExpression(Expression node, CtExpression<?> element) {
        element().ctElement(node, element);

        // TODO: Add casts

    }

    public void ctLiteral(LiteralExpr node, CtLiteral<?> element) {
        // Hierarchy
        ctExpression(node, element);

        // TODO: Add value
        // TODO: Add literalKind

    }

}
