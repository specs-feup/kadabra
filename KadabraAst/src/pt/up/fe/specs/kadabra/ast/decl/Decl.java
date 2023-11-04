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

package pt.up.fe.specs.kadabra.ast.decl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;
import org.suikasoft.jOptions.Interfaces.DataStore;

import pt.up.fe.specs.kadabra.ast.KadabraNode;

public class Decl extends KadabraNode {

    /// DATAKEYS BEGIN

    /**
     * The name of the decl.
     */
    public final static DataKey<String> NAME = KeyFactory.string("name");

    /**
     * If using noclasspath mode, node may not have all information.
     */
    public final static DataKey<Boolean> IS_INCOMPLETE = KeyFactory.bool("isIncomplete");

    /**
     * The generic parameters, if this declaration has any.
     */
    public static final DataKey<List<TypeDecl>> GENERIC_PARAMS = KeyFactory.list("genericParams", TypeDecl.class);

    /**
     * Types thrown by this decl, if any.
     */
    public static final DataKey<List<TypeDecl>> THROWN_TYPES = KeyFactory.list("thrownTypes", TypeDecl.class);

    /**
     * The type declaration that declares this member, if this declaration a class member.
     */
    public static final DataKey<Optional<TypeDecl>> DECLARING_TYPE = KeyFactory.optional("declaringType");

    /**
     * The top level type declaration that declares this member, if this type declaration is an inner type.
     * 
     */
    public static final DataKey<Optional<TypeDecl>> TOP_LEVEL_TYPE = KeyFactory.optional("topLevelType");

    /// DATAKEYS END

    public Decl(DataStore data, Collection<? extends KadabraNode> children) {
        super(data, children);
    }

    public boolean isClassMember() {
        return get(DECLARING_TYPE).isPresent();
    }

    public boolean isTopLevelType() {
        return get(TOP_LEVEL_TYPE).isEmpty();
    }
}
