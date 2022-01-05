/**
 * Copyright 2022 SPeCS.
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

package pt.up.fe.specs.kadabra.ast.decl;

import java.util.Collection;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;
import org.suikasoft.jOptions.Interfaces.DataStore;

import pt.up.fe.specs.kadabra.ast.KadabraNode;

/**
 * A local variable definition.
 * 
 * @author Joao Bispo
 *
 */
public class LocalVarDecl extends VarDecl {

    /// DATAKEYS BEGIN

    /**
     * True if this variable's type was defined using the 'var' keyword of Java 10.
     */
    public static final DataKey<Boolean> IS_INFERRED = KeyFactory.bool("isInferred");

    /// DATAKEYS END

    public LocalVarDecl(DataStore data, Collection<? extends KadabraNode> children) {
        super(data, children);
    }

}
