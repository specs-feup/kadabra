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
import pt.up.fe.specs.util.xml.XmlNode;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.AXmlElement;
import weaver.kadabra.abstracts.joinpoints.AXmlNode;
import weaver.utils.weaving.converters.XmlNode2JoinPoint;

public class JXmlNode<Self extends JXmlNode<Self>> extends AXmlNode<Self> {

    private final XmlNode node;

    public JXmlNode(XmlNode node, JWeaver weaver) {
        super((spoon.reflect.declaration.CtElement) null, weaver);
        this.node = node;
    }

    @Override
    public spoon.reflect.declaration.CtElement getNodeImpl() {
        // XML nodes have no Spoon node
        return null;
    }

    @Override
    public AJoinpoint<?> getParentImpl() {
        return XmlNode2JoinPoint.convert(node.getParent(), getWeaverEngine());
    }

    @Override
    public AJoinpoint<?>[] getChildrenImpl() {
        return node.getChildren().stream()
                .map(child -> XmlNode2JoinPoint.convert(child, getWeaverEngine()))
                .toArray(length -> new AJoinpoint[length]);
    }

    @Override
    public AJoinpoint<?>[] getDescendantsImpl() {
        return node.getDescendants().stream()
                .map(child -> XmlNode2JoinPoint.convert(child, getWeaverEngine()))
                .toArray(length -> new AJoinpoint[length]);
    }

    @Override
    public AXmlElement<?>[] getElementsImpl() {
        return node.getDescendants().stream()
                .filter(descendant -> descendant instanceof XmlElement)
                .map(element -> (AXmlElement<?>) XmlNode2JoinPoint.convert(element,
                        getWeaverEngine()))
                .toArray(length -> new AXmlElement[length]);

    }

    @Override
    public AXmlElement<?>[] getElementsByNameImpl(String name) {
        return node.getElementsByName(name).toArray(length -> new AXmlElement[length]);
    }

    @Override
    public String getTextImpl() {
        return node.getText();
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
