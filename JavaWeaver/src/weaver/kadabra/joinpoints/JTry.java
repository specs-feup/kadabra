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

import java.util.stream.Collectors;

import spoon.reflect.code.CtTry;
import weaver.kadabra.abstracts.joinpoints.ABody;
import weaver.kadabra.abstracts.joinpoints.ACatch;
import weaver.kadabra.abstracts.joinpoints.ATry;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JTry extends ATry {

    private CtTry node;

    private JTry(CtTry node) {
        super(new JStatement(node));
        this.node = node;
    }

    public static JTry newInstance(CtTry node) {
        return new JTry(node);
    }

    @Override
    public CtTry getNode() {
        return node;
    }

    @Override
    public ABody getBodyImpl() {
        return CtElement2JoinPoint.convert(node.getBody(), ABody.class);
    }

    @Override
    public ACatch[] getCatchesArrayImpl() {

        return node.getCatchers().stream()
                .map(catchNode -> CtElement2JoinPoint.convert(catchNode, ACatch.class))
                .collect(Collectors.toList())
                .toArray(new ACatch[0]);

    }

}
