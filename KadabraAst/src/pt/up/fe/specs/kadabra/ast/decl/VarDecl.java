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
import java.util.List;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;
import org.suikasoft.jOptions.Interfaces.DataStore;

import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.kadabra.ast.data.Modifier;

/**
 * Represents a variable declaration (e.g. local variable, enum value, field, parameter...)
 * 
 * @author Joao Bispo
 *
 */
public class VarDecl extends Decl {

    /// DATAKEYS BEGIN

    /**
     * Modifiers (e.g. public, private) associated with this declaration.
     */
    public static final DataKey<List<Modifier>> MODIFIERS = KeyFactory.list("modifiers", Modifier.class);

    /**
     * Initialization associated with this declaration, if present.
     */
    // public static final DataKey<Optional<Expression>> INIT = KeyFactory.optional("init");

    /// DATAKEYS END

    public VarDecl(DataStore data, Collection<? extends KadabraNode> children) {
        super(data, children);
    }

}
