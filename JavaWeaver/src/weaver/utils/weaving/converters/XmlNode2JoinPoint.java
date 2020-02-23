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

package weaver.utils.weaving.converters;

import java.util.Optional;

import pt.up.fe.specs.util.classmap.FunctionClassMap;
import pt.up.fe.specs.util.xml.XmlElement;
import pt.up.fe.specs.util.xml.XmlNode;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.joinpoints.JXmlElement;
import weaver.kadabra.joinpoints.JXmlNode;

public class XmlNode2JoinPoint {

    private static final FunctionClassMap<XmlNode, AJavaWeaverJoinPoint> CONVERTER = new FunctionClassMap<>(
            JXmlNode::new);

    static {
        CONVERTER.put(XmlElement.class, JXmlElement::new);

    }

    public static AJavaWeaverJoinPoint convert(XmlNode node) {
        return CONVERTER.apply(node);

    }

    public static Optional<AJavaWeaverJoinPoint> convertTry(XmlNode node) {
        try {
            return Optional.ofNullable(CONVERTER.apply(node));
        } catch (Exception e) {
            return Optional.empty();
        }

    }

}
