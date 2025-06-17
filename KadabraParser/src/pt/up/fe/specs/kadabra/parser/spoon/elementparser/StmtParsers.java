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

package pt.up.fe.specs.kadabra.parser.spoon.elementparser;

import java.util.Collection;

import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.kadabra.ast.stmt.BlockStmt;
import pt.up.fe.specs.kadabra.parser.spoon.datafiller.StmtDataFiller;
import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.code.CtBlock;
import spoon.reflect.declaration.CtElement;

public class StmtParsers extends SpoonParsers {

    protected StmtParsers(MainParser mainParser) {
        super(mainParser);
    }

    @Override
    protected void registerParsers(FunctionClassMap<CtElement, KadabraNode> parsers) {
        parsers.put(CtBlock.class, this::ctBlock);
    }

    public static void registerParsers(MainParser mainParser) {
        new StmtParsers(mainParser);
    }

    private StmtDataFiller stmt() {
        return dataFillers().stmt();
    }

    public BlockStmt ctBlock(CtBlock<?> ctBlock) {
        return newParser(BlockStmt.class, stmt()::ctBlock, this::getCtBlockChildren).parse(ctBlock);
    }

    public Collection<? extends CtElement> getCtBlockChildren(CtBlock<?> element) {
        return element.getStatements();
    }
}
