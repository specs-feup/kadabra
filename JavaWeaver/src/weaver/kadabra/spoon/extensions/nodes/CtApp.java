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

package weaver.kadabra.spoon.extensions.nodes;

import java.util.List;
import java.util.stream.Collectors;

import spoon.Launcher;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.CtVisitor;
import spoon.support.reflect.declaration.CtElementImpl;

public class CtApp extends CtElementImpl {

    public final Launcher spoon;

    public CtApp(Launcher spoon) {
        this.spoon = spoon;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void accept(CtVisitor visitor) {

    }

    @Override
    public List<CtElement> getDirectChildren() {
        return spoon.getFactory().CompilationUnit().getMap().values().stream()
                .map(CtElement.class::cast)
                .collect(Collectors.toList());
    }
}
