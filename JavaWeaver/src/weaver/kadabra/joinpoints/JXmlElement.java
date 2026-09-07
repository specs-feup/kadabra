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

import pt.up.fe.specs.util.xml.XmlElement;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AXmlElement;

public class JXmlElement<Self extends JXmlElement<Self>> extends AXmlElement<Self> {

    private final XmlElement element;

    public JXmlElement(XmlElement element, JWeaver weaver) {
        super(element, weaver);
        this.element = element;
    }

    @Override
    public String getNameImpl() {
        return element.getName();
    }

    @Override
    public String getAttributeImpl(String name) {
        return element.getAttribute(name);
    }

    @Override
    public String setAttributeImpl(String name, String value) {
        return element.setAttribute(name, value);
    }

    @Override
    public String[] getAttributeNamesImpl() {
        return element.getAttributes().toArray(length -> new String[length]);
    }
}
