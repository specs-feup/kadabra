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

package pt.up.fe.specs.kadabra.ast;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;

/**
 * Interface for literal nodes.
 * 
 * <p>
 * Literal nodes are nodes that contain the literal code that will appear when the source code is generated.
 * 
 * @author JoaoBispo
 *
 */
public interface LiteralNode {

    /// DATAKEYS BEGIN

    public final static DataKey<String> LITERAL_CODE = KeyFactory.string("literalCode");

    /// DATAKEYS END

    <T> T get(DataKey<T> key);

    default String getLiteralCode() {
        return get(LITERAL_CODE);
    }

}
