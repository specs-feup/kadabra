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

import pt.up.fe.specs.kadabra.KadabraNodeFactory;
import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.kadabra.parser.spoon.datafiller.DataFillers;
import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.declaration.CtElement;

public abstract class SpoonParsers {

    private final MainParser mainParser;

    protected SpoonParsers(MainParser mainParser) {
        this.mainParser = mainParser;
        registerParsers(mainParser.getNodeBuilders());
    }

    protected abstract void registerParsers(FunctionClassMap<CtElement, KadabraNode> parsers);

    protected MainParser parser() {
        return mainParser;
    }

    protected DataFillers dataFillers() {
        return mainParser.getDataFillers();
    }

    protected KadabraNodeFactory factory() {
        return mainParser.getFactory();
    }
}
