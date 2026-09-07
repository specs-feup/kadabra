/**
 * Copyright 2018 SPeCS.
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

import com.google.common.reflect.ClassPath.ClassInfo;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ALibClass;

public class JLibClass<Self extends JLibClass<Self>> extends ALibClass<Self> {

    public JLibClass(ClassInfo info, JWeaver weaver) {
        super(info, weaver);
    }

}
