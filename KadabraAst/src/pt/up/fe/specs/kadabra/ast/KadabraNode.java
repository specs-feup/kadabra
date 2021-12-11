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

package pt.up.fe.specs.kadabra.ast;

import java.util.Collection;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;
import org.suikasoft.jOptions.Interfaces.DataStore;
import org.suikasoft.jOptions.treenode.DataNode;

import pt.up.fe.specs.kadabra.KadabraNodeFactory;

/**
 * Represents a node of the Kadabra AST.
 * 
 * @author JoaoBispo
 *
 */
public abstract class KadabraNode extends DataNode<KadabraNode> {

    // DATAKEYS BEGIN

    /**
     * Global object with information about the program.
     */
    public final static DataKey<KadabraContext> CONTEXT = KeyFactory.object("context", KadabraContext.class);

    /**
     * Id of the node.
     */
    public final static DataKey<String> ID = KeyFactory.string("id");

    /**
     * Factory for building nodes.
     */
    public final static DataKey<KadabraNodeFactory> FACTORY = KeyFactory.object("factory", KadabraNodeFactory.class);

    // DATAKEYS END

    public KadabraNode(DataStore data, Collection<? extends KadabraNode> children) {
        super(data, children);
    }

    @Override
    protected KadabraNode getThis() {
        return this;
    }

    @Override
    public String toContentString() {
        return getData().toInlinedString();
    }
}
