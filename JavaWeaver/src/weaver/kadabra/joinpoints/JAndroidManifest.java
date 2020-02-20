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

package weaver.kadabra.joinpoints;

import org.json.XML;

import pt.up.fe.specs.util.SpecsIo;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.abstracts.joinpoints.AAndroidManifest;
import weaver.utils.android.AndroidResources;

public class JAndroidManifest extends AAndroidManifest {

    private final AndroidResources androidResources;

    public JAndroidManifest(AndroidResources androidResources) {
        this.androidResources = androidResources;
    }

    @Override
    public CtElement getNode() {
        return null;
    }

    @Override
    public Object getAsJsonImpl() {
        var manifest = androidResources.getAndroidManifest();

        if (manifest == null) {
            return null;
        }

        return getWeaverEngine().getScriptEngine()
                .eval("var lara_android_manifest = " + XML.toJSONObject(SpecsIo.read(manifest)).toString(4)
                        + "; lara_android_manifest;");
    }

    @Override
    public String toString() {
        return "AndroidManifest (" + androidResources.getAndroidManifest() + ")";
    }

}
