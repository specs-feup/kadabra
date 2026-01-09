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
 * specific language governing permissions and limitations under the License. under the License.
 */

package weaver.kadabra.joinpoints;

import org.json.XML;
import pt.up.fe.specs.util.xml.XmlDocument;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.abstracts.joinpoints.AAndroidManifest;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;

public class JAndroidManifest extends AAndroidManifest {

    private final XmlDocument manifest;

    public JAndroidManifest(XmlDocument manifest) {
        super(new JXmlNode(manifest));
        this.manifest = manifest;
    }

    @Override
    public CtElement getNode() {
        return null;
    }

    @Override
    public String getAsJsonImpl() {
        return XML.toJSONObject(manifest.toString()).toString(4);
    }

    @Override
    public String toString() {
        return "AndroidManifest";
    }

    @Override
    public AJoinPoint getParentImpl() {
        // Parent is App
        return (JApp) getWeaverEngine().getRootJp();
    }

}
