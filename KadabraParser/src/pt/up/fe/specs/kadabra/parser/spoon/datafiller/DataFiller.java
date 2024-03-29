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

package pt.up.fe.specs.kadabra.parser.spoon.datafiller;

import java.util.List;
import java.util.stream.Collectors;

import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.kadabra.ast.data.Modifier;
import pt.up.fe.specs.kadabra.ast.data.ModifierKind;
import pt.up.fe.specs.kadabra.ast.decl.TypeDecl;
import pt.up.fe.specs.kadabra.parser.spoon.elementparser.MainParser;
import spoon.reflect.declaration.CtModifiable;

public abstract class DataFiller {

    private final MainParser mainParser;

    public DataFiller(MainParser mainParser) {
        this.mainParser = mainParser;
    }

    public MainParser parser() {
        return mainParser;
    }

    protected ElementDataFiller element() {
        return mainParser.getDataFillers().element();
    }

    protected TypeDecl toTypeDecl(KadabraNode node) {
        return mainParser.toTypeDecl(node);
    }

    public List<Modifier> getModifiers(CtModifiable element) {

        return element.getExtendedModifiers().stream()
                .map(modifier -> new Modifier(translate(modifier.getKind()), modifier.isImplicit()))
                .collect(Collectors.toList());
    }

    public ModifierKind translate(spoon.reflect.declaration.ModifierKind modifier) {
        return ModifierKind.valueOf(modifier.name());
    }
}
