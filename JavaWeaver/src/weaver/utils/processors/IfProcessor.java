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

package weaver.utils.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;

public class IfProcessor extends AbstractProcessor<CtIf> {

    @Override
    public void process(CtIf element) {
        CtStatement thenStatement = element.getThenStatement();
        if (thenStatement != null && !(thenStatement instanceof CtBlock)) {
            CtBlock<?> newBlock = element.getFactory().Code().createCtBlock(thenStatement);
            element.setThenStatement(newBlock);
            newBlock.setPosition(thenStatement.getPosition());
        }
        CtStatement elseStatement = element.getElseStatement();
        if (elseStatement != null && !(elseStatement instanceof CtBlock)) {
            CtBlock<?> newBlock = element.getFactory().Code().createCtBlock(elseStatement);
            element.setElseStatement(newBlock);
            newBlock.setPosition(elseStatement.getPosition());
        }
    }
}
