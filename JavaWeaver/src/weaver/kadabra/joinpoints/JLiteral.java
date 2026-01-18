/**
 * Copyright 2018 SPeCS.
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

import spoon.reflect.code.CtLiteral;
import weaver.kadabra.abstracts.joinpoints.ALiteral;
import weaver.utils.SpoonLiterals;

public class JLiteral<T> extends ALiteral {

    private CtLiteral<T> node;

    private JLiteral(CtLiteral<T> node) {
        super(new JExpression<>(node));
        this.node = node;
        // System.out.println("VALUE TYPE: " + node.getValue().getClass());
        // System.out.println("TYPE:" + getTypeImpl());
    }

    public static <T> JLiteral<T> newInstance(CtLiteral<T> node) {
        return new JLiteral<>(node);
    }

    @Override
    public String getValueImpl() {
        return String.valueOf(node.getValue());
    }

    @Override
    public CtLiteral<T> getNode() {
        return node;
    }

    @Override
    public String toString() {
        return getValueImpl();
    }

    @Override
    public void setValueImpl(String value) {
        @SuppressWarnings("unchecked") // decode literal value transform the value into T
        T decodedValue = (T) SpoonLiterals.decodeLiteralValue(getTypeImpl().toString(), value);
        node.setValue(decodedValue);
    }
}
