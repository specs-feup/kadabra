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
     * If this decl a class member, returns the type that declares this member. If it is not a class member, returns
     * TypeDecl.getNoType().
     */
    public static final DataKey<TypeDecl> DECLARING_TYPE = KeyFactory.object("declaringType", TypeDecl.class)
            .setDefault(() -> TypeDecl.getNoType());

    /**
     * If this decl is an inner type of type member, returns the top level type that declares this member. If not,
     * returns TypeDecl.getNoType().
     */
    public static final DataKey<TypeDecl> TOP_LEVEL_TYPE = KeyFactory.object("topLevelType", TypeDecl.class)
            .setDefault(() -> TypeDecl.getNoType());

    /// DATAKEYS END

    public Decl(DataStore data, Collection<? extends KadabraNode> children) {
        super(data, children);
    }

    public boolean isClassMember() {
        return !TypeDecl.isNoType(get(DECLARING_TYPE));
    }

    public boolean isTopLevelType() {
        return TypeDecl.isNoType(get(TOP_LEVEL_TYPE));
    }
}
