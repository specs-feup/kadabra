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

package pt.up.fe.specs.kadabra;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.suikasoft.jOptions.Interfaces.DataStore;
import org.suikasoft.jOptions.storedefinition.StoreDefinitions;

import pt.up.fe.specs.kadabra.ast.App;
import pt.up.fe.specs.kadabra.ast.CompilationUnit;
import pt.up.fe.specs.kadabra.ast.GenericKadabraNode;
import pt.up.fe.specs.kadabra.ast.KadabraContext;
import pt.up.fe.specs.kadabra.ast.KadabraNode;

public class KadabraNodeFactory {

    private final KadabraContext context;
    private final DataStore baseData;

    public KadabraNodeFactory(KadabraContext context, DataStore baseData) {
        this.context = context;
        this.baseData = baseData;
    }

    public KadabraNodeFactory(KadabraContext context) {
        this(context, null);
    }

    public DataStore newDataStore(Class<? extends KadabraNode> nodeClass) {

        DataStore data = DataStore.newInstance(StoreDefinitions.fromInterface(nodeClass), true);

        // Add base node, if present
        if (baseData != null) {
            data.addAll(baseData);
        }

        // Set context
        data.set(KadabraNode.CONTEXT, context);

        // Set id
        data.set(KadabraNode.ID, context.get(KadabraContext.ID_GENERATOR).next("node_"));

        // Set factory
        data.set(KadabraNode.FACTORY, this);

        return data;
    }

    public GenericKadabraNode genericKadabraNode(String nodeType) {
        var node = new GenericKadabraNode(newDataStore(GenericKadabraNode.class), null);

        node.set(GenericKadabraNode.NODE_TYPE, nodeType);

        return node;
    }

    @Override
    public String toString() {
        return "KadabraNodeFactory " + hashCode();
    }

    /**
     * Creates an empty node of the given class.
     * 
     * @param <T>
     * @param nodeClass
     * @return
     */
    public <T extends KadabraNode> T newNode(Class<T> nodeClass) {
        return newNode(nodeClass, Collections.emptyList());
    }

    public <T extends KadabraNode> T newNode(Class<T> nodeClass, Collection<? extends KadabraNode> children) {
        try {
            DataStore data = newDataStore(nodeClass);

            return nodeClass.getDeclaredConstructor(DataStore.class, Collection.class)
                    .newInstance(data, children);
        } catch (Exception e) {
            throw new RuntimeException("Could not create KadabraNode", e);
        }
    }

    public App app(List<CompilationUnit> compilationUnits) {
        var app = newNode(App.class);
        app.setChildren(compilationUnits);
        return app;
        // DataStore data = newDataStore(App.class);
        // return new App(data, compilationUnits);
    }

    // public CompilationUnit compilationUnit() {
    // return new CompilationUnit(newDataStore(CompilationUnit.class), null);
    // }

}
