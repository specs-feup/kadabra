/**
 * Copyright 2019 SPeCS.
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

package weaver.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SpoonLiterals {

    private static final Map<String, Function<String, Object>> LITERAL_DECODER;
    static {
        LITERAL_DECODER = new HashMap<>();
        LITERAL_DECODER.put("int", Integer::decode);
        LITERAL_DECODER.put("long", Long::decode);
        LITERAL_DECODER.put("float", Float::valueOf);
        LITERAL_DECODER.put("double", Double::valueOf);
        LITERAL_DECODER.put("char", value -> value.charAt(0));
        LITERAL_DECODER.put("String", value -> value);
        LITERAL_DECODER.put("boolean", Boolean::valueOf);
    }

    public static Object decodeLiteralValue(String type, String literalValue) {
        var decoder = LITERAL_DECODER.get(type);
        if (decoder == null) {
            throw new RuntimeException("Decoding of literal values not implemented for type '" + type + "'");
        }

        Object value = decoder.apply(literalValue);

        if (value instanceof Number) {
            if (((Number) value).doubleValue() < 0) {
                throw new RuntimeException("Literal values cannot be negative: " + value);
            }
        }

        return value;
    }
}
