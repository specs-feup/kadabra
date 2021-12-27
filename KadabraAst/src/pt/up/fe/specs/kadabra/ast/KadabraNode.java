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
import pt.up.fe.specs.kadabra.ast.decl.TypeDecl;
import pt.up.fe.specs.util.utilities.PrintOnce;

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
     * If this node was automatically generated from another node, returns the id of that node, otherwise returns empty.
     */
    public final static DataKey<String> PREVIOUS_ID = KeyFactory.string("previousId");

    /**
     * Factory for building nodes.
     */
    public final static DataKey<KadabraNodeFactory> FACTORY = KeyFactory.object("factory", KadabraNodeFactory.class);

    /**
     * If using noclasspath mode, node may not have access to source code
     */
    public final static DataKey<Boolean> HAS_SOURCE = KeyFactory.bool("hasSource");

    /**
     * The type returned by this node, or TypeDecl.getNoType() if node does not have a return type.
     */
    public final static DataKey<TypeDecl> TYPE = KeyFactory.object("type", TypeDecl.class)
            .setDefault(() -> TypeDecl.getNoType());

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

    @Override
    protected Class<KadabraNode> getBaseClass() {
        return KadabraNode.class;
    }

    /**
     * By default, copying a node creates an new, unique id for the new copy.
     */
    @Override
    public KadabraNode copy() {
        return copy(false);
    }

    /**
     * 
     * @param keepId
     *            if true, the id of the copy will be the same as the id of the original node
     * @return
     */
    public KadabraNode copy(boolean keepId) {
        // Copy node, without children
        var newNode = copyPrivate();

        // Change id, if needed
        if (!keepId) {
            String previousId = newNode.get(ID);
            String newId = get(CONTEXT).get(KadabraContext.ID_GENERATOR).next("node_");

            newNode.set(ID, newId);
            newNode.set(PREVIOUS_ID, previousId);
        }

        // Copy children
        for (var child : getChildren()) {
            var newChild = child.copy(keepId);
            newNode.addChild(newChild);
        }

        return newNode;
    }

    public boolean hasType() {
        return !TypeDecl.isNoType(get(TYPE));
    }

    public String getCode() {
        PrintOnce.info("getCode() not implemented for nodes of type " + getClass());

        return "\n/*<.getCode() not implemented for node " + this.getClass() + ">*/";
    }
}
