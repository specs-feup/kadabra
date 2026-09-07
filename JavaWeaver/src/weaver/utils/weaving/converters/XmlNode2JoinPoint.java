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

package weaver.utils.weaving.converters;

import java.util.Optional;

import pt.up.fe.specs.util.classmap.BiFunctionClassMap;
import pt.up.fe.specs.util.xml.XmlElement;
import pt.up.fe.specs.util.xml.XmlNode;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.joinpoints.JXmlElement;
import weaver.kadabra.joinpoints.JXmlNode;

public class XmlNode2JoinPoint {

    private static final BiFunctionClassMap<XmlNode, JWeaver, AJoinpoint<?>> CONVERTER;

    static {
        CONVERTER = new BiFunctionClassMap<>();

        CONVERTER.put(XmlElement.class, JXmlElement::new);
        CONVERTER.put(XmlNode.class, XmlNode2JoinPoint::defaultFactory);
    }

    public static AJoinpoint<?> defaultFactory(XmlNode node, JWeaver weaver) {
        return new JXmlNode(node, weaver);
    }

    public static AJoinpoint<?> convert(XmlNode node, JWeaver weaver) {
        return CONVERTER.apply(node, weaver);

    }

    public static Optional<AJoinpoint<?>> convertTry(XmlNode node, JWeaver weaver) {
        try {
            return Optional.ofNullable(CONVERTER.apply(node, weaver));
        } catch (Exception e) {
            return Optional.empty();
        }

    }

}
