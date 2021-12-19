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

import pt.up.fe.specs.kadabra.parser.spoon.elementparser.MainParser;

public class DataFillers {

    private final MainParser generalParser;
    private final ElementDataFiller elementFiller;
    private final DeclDataFiller declFiller;

    public DataFillers(MainParser generalParser) {
        this.generalParser = generalParser;

        this.elementFiller = new ElementDataFiller(generalParser);
        this.declFiller = new DeclDataFiller(generalParser);
    }

    public DeclDataFiller decl() {
        return declFiller;
    }

    public ElementDataFiller element() {
        return elementFiller;
    }
}
