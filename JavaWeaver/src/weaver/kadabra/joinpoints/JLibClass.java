/**
 * Copyright 2018 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.joinpoints;

import com.google.common.reflect.ClassPath.ClassInfo;

import spoon.reflect.declaration.CtElement;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.joinpoints.ALibClass;

public class JLibClass extends ALibClass {

    private JLibClass(ClassInfo info, JavaWeaver weaver) {
        super(JNamedType.newInstance(info, weaver), weaver);
        // this.info = info;
    }

    public static JLibClass newInstance(ClassInfo info, JavaWeaver weaver) {
        return new JLibClass(info, weaver);
    }

    @Override
    public CtElement getNode() {
        return null;
    }
}
