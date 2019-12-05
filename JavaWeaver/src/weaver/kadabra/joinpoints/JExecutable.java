/**
 * Copyright 2017 SPeCS.
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

package weaver.kadabra.joinpoints;

import java.util.Collections;
import java.util.List;

import spoon.reflect.code.CtBlock;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtParameter;
import weaver.kadabra.abstracts.joinpoints.ABody;
import weaver.kadabra.abstracts.joinpoints.ADeclaration;
import weaver.kadabra.abstracts.joinpoints.AExecutable;
import weaver.utils.scanners.NodeConverter;
import weaver.utils.weaving.SelectUtils;

public class JExecutable<R> extends AExecutable {

    private CtExecutable<R> node;

    private JExecutable(CtExecutable<R> node) {
        this.node = node;
    }

    public static <R> JExecutable<R> newInstance(CtExecutable<R> node) {
        return new JExecutable<>(node);
    }

    @Override
    public String getNameImpl() {
        return node.getSimpleName().toString();
    }

    @Override
    public void defNameImpl(String value) {
        node.setSimpleName(value);
    }

    @Override
    public String setNameImpl(String name) {
        var currentName = node.getSimpleName();
        defNameImpl(name);
        return currentName;
    }

    @Override
    public String getReturnTypeImpl() {
        return node.getType().getSimpleName();
    }

    @Override
    public List<? extends ABody> selectBody() {

        final CtBlock<?> body = node.getBody();
        if (body == null) {
            return Collections.emptyList();
        }
        return SelectUtils.node2JoinPointList(body, JBody::newInstance);
    }

    @Override
    public List<? extends ADeclaration> selectParam() {

        List<CtParameter<?>> parameters = node.getParameters();
        NodeConverter<CtParameter<?>, JDeclaration<?>> converter = JDeclaration::newInstance;
        final List<JDeclaration<?>> params = SelectUtils.nodeList2JoinPointList(parameters, converter);
        return params;
    }

    @Override
    public CtExecutable<R> getNode() {
        return node;
    }

    @Override
    public String toString() {
        return node.getSignature();
    }

}
