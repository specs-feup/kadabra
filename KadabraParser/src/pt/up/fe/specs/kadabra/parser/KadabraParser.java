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

package pt.up.fe.specs.kadabra.parser;

import java.io.File;
import java.util.List;

import org.suikasoft.jOptions.DataStore.ADataClass;
import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;

import pt.up.fe.specs.kadabra.ast.App;
import pt.up.fe.specs.kadabra.parser.spoon.SpoonParser;

/**
 * Parses Java code into a Kadabra AST.
 * 
 * @author JoaoBispo
 *
 */
public abstract class KadabraParser extends ADataClass<KadabraParser> {

    // BEGIN DATAKEY

    public static final DataKey<Boolean> SHOW_AST = KeyFactory.bool("showAst");
    public static final DataKey<Boolean> SHOW_CODE = KeyFactory.bool("showCode");

    // END DATAKEY

    public abstract App parse(List<File> sources);

    /**
     * Creates a new KadabraParser instance.
     * 
     * @return
     */
    public static KadabraParser newInstance(KadabraParserConfig config) {
        return new SpoonParser(config);
    }

    public static KadabraParser newInstance() {
        return newInstance(new KadabraParserConfig());
    }
}
