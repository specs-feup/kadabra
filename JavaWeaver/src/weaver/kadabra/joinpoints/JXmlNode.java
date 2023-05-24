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

import java.util.Arrays;
import java.util.List;

import pt.up.fe.specs.util.xml.XmlElement;
import pt.up.fe.specs.util.xml.XmlNode;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AXmlElement;
import weaver.kadabra.abstracts.joinpoints.AXmlNode;
import weaver.utils.weaving.converters.XmlNode2JoinPoint;

public class JXmlNode extends AXmlNode {

    private final XmlNode node;

    public JXmlNode(XmlNode node) {
        this.node = node;
    }

    @Override
    public CtElement getNode() {
        return null;
    }

    @Override
    public AJoinPoint getParentImpl() {
        return XmlNode2JoinPoint.convert(node.getParent());
    }

    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        return node.getChildren().stream()
                .map(XmlNode2JoinPoint::convert)
                .toArray(length -> new AJoinPoint[length]);
    }

    @Override
    public AJoinPoint[] getDescendantsArrayImpl() {
        return node.getDescendants().stream()
                .map(XmlNode2JoinPoint::convert)
                .toArray(length -> new AJoinPoint[length]);
    }

    @Override
    public AXmlElement[] getElementsArrayImpl() {
        return node.getDescendants().stream()
                .filter(node -> node instanceof XmlElement)
                .map(element -> (AXmlElement) XmlNode2JoinPoint.convert(element))
                .toArray(length -> new AXmlElement[length]);

    }

    @Override
    public List<? extends AXmlElement> selectElement() {
        return Arrays.asList(getElementsArrayImpl());
    }

    @Override
    public AXmlElement[] elementsArrayImpl(String name) {
        return node.getElementsByName(name).toArray(length -> new AXmlElement[length]);
    }

    @Override
    public String getTextImpl() {
        return node.getText();
    }

    @Override
    public void defTextImpl(String value) {
        setTextImpl(value);
    }

    @Override
    public String setTextImpl(String text) {
        return node.setText(text);
    }

    @Override
    public String getSrcCodeImpl() {
        return node.getString();
    }
}
