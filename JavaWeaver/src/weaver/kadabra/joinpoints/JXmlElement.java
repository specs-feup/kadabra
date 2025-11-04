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
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.joinpoints;

import pt.up.fe.specs.util.xml.XmlElement;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.abstracts.joinpoints.AXmlElement;

public class JXmlElement extends AXmlElement {

    private final XmlElement element;

    public JXmlElement(XmlElement element) {
        super(new JXmlNode(element));

        this.element = element;
    }

    @Override
    public CtElement getNode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNameImpl() {
        return element.getName();
    }

    @Override
    public String attributeImpl(String name) {
        return element.getAttribute(name);
    }

    @Override
    public String setAttributeImpl(String name, String value) {
        return element.setAttribute(name, value);
    }

    @Override
    public String[] getAttributeNamesArrayImpl() {
        return element.getAttributes().toArray(length -> new String[length]);
    }
}
