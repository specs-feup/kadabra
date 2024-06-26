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
import pt.up.fe.specs.util.SpecsCollections;

public class TypeDecl extends Decl {

    /// DATAKEYS BEGIN

    /**
     * The prefix of the qualified name. The fully qualified name is build using Decl.NAME
     */
    public final static DataKey<List<String>> QUALIFIED_PREFIX = KeyFactory.list("qualifiedPrefix", String.class);

    public static final DataKey<Optional<TypeDecl>> SUPER = KeyFactory.optional("super");

    public static final DataKey<List<TypeDecl>> INTERFACES = KeyFactory.list("interfaces", TypeDecl.class);

    /// DATAKEYS END

    public TypeDecl(DataStore data, Collection<? extends KadabraNode> children) {
        super(data, children);
    }

    public List<String> getQualifiedName() {
        return SpecsCollections.concat(get(QUALIFIED_PREFIX), get(NAME));
    }

}
