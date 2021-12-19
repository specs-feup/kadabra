/**
 * Copyright 2020 SPeCS.
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
import java.util.Collections;
import java.util.List;

import org.suikasoft.jOptions.DataStore.ADataClass;
import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;

/**
 * Represents Java parsing information.
 * 
 * @author JoaoBispo
 *
 */
public class KadabraParserConfig extends ADataClass<KadabraParserConfig> {

    public static final DataKey<List<File>> CLASSPATH = KeyFactory.list("classpath", File.class)
            .setDefault(() -> Collections.emptyList());
    public static final DataKey<Boolean> NO_CLASSPATH = KeyFactory.bool("noClasspath")
            .setDefault(() -> true);
    // .setDefault(() -> false);
    public static final DataKey<Integer> JAVA_VERSION = KeyFactory.integer("javaVersion").setDefault(() -> 11);

}
