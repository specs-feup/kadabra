/**
 * Copyright 2020 SPeCS.
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

import spoon.reflect.declaration.CtAnnotation;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AAnnotation;

public class JAnnotation<Self extends JAnnotation<Self>> extends AAnnotation<Self> {

    public JAnnotation(CtAnnotation annotation, JWeaver weaver) {
        super(annotation, weaver);
    }

    @Override
    public CtAnnotation<?> getNodeImpl() {
        return (CtAnnotation<?>) super.getNodeImpl();
    }

}
