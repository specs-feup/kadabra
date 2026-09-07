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

import spoon.reflect.code.CtLiteral;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ALiteral;
import weaver.utils.SpoonLiterals;

public class JLiteral<Self extends JLiteral<Self>> extends ALiteral<Self> {

    public JLiteral(CtLiteral node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public String getValueImpl() {
        return String.valueOf(getNodeImpl().getValue());
    }

    @Override
    public CtLiteral<?> getNodeImpl() {
        return (CtLiteral<?>) super.getNodeImpl();
    }

    @Override
    public String getToStringImpl() {
        return getValueImpl();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setValueImpl(String value) {
        // decode literal value transforms the value into the literal's type
        Object decodedValue = SpoonLiterals.decodeLiteralValue(getTypeImpl().toString(), value);
        ((CtLiteral) getNodeImpl()).setValue(decodedValue);
    }
}
