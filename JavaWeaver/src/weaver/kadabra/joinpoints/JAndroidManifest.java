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

import org.json.XML;

import pt.up.fe.specs.util.xml.XmlDocument;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AAndroidManifest;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;

public class JAndroidManifest<Self extends JAndroidManifest<Self>> extends AAndroidManifest<Self> {

    private final XmlDocument manifest;

    public JAndroidManifest(XmlDocument manifest, JWeaver weaver) {
        super(manifest, weaver);
        this.manifest = manifest;
    }

    @Override
    public String getAsJsonImpl() {
        return XML.toJSONObject(manifest.toString()).toString(4);
    }

    @Override
    public String getToStringImpl() {
        return "AndroidManifest";
    }

    @Override
    public AJoinpoint<?> getParentImpl() {
        // Parent is App
        return (JApp<?>) getWeaverEngine().getRootJp();
    }

}
