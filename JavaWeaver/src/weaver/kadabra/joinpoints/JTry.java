/**
 * Copyright 2017 SPeCS.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.joinpoints;

import spoon.reflect.code.CtTry;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ABody;
import weaver.kadabra.abstracts.joinpoints.ACatch;
import weaver.kadabra.abstracts.joinpoints.ATry;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JTry<Self extends JTry<Self>> extends ATry<Self> {

    public JTry(CtTry node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public CtTry getNodeImpl() {
        return (CtTry) super.getNodeImpl();
    }

    @Override
    public ABody<?> getBodyImpl() {
        return CtElement2JoinPoint.convert(getNodeImpl().getBody(), getWeaverEngine(), ABody.class);
    }

    @Override
    public ACatch<?>[] getCatchesImpl() {

        return getNodeImpl().getCatchers().stream()
                .map(catchNode -> CtElement2JoinPoint.convert(catchNode,
                        getWeaverEngine(), ACatch.class))
                .collect(java.util.stream.Collectors.toList())
                .toArray(new ACatch[0]);

    }

}
