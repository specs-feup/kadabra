/**
 * Copyright 2016 SPeCS.
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

package weaver.utils.element;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import pt.up.fe.specs.util.lazy.Lazy;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.UnaryOperatorKind;

public class OperatorUtils {

    private static final Lazy<Map<String, BinaryOperatorKind>> STRING_TO_BINARY_OP = Lazy
            .newInstance(OperatorUtils::buildBinaryOpToStringMap);

    private static final Lazy<Map<String, UnaryOperatorKind>> STRING_TO_UNARY_OP = Lazy
            .newInstance(OperatorUtils::buildUnaryOpToStringMap);

    private static Map<String, BinaryOperatorKind> buildBinaryOpToStringMap() {
        var map = new HashMap<String, BinaryOperatorKind>();

        for (BinaryOperatorKind op : BinaryOperatorKind.values()) {
            map.put(convert(op), op);
        }

        return map;
    }

    private static Map<String, UnaryOperatorKind> buildUnaryOpToStringMap() {
        var map = new HashMap<String, UnaryOperatorKind>();

        for (UnaryOperatorKind op : UnaryOperatorKind.values()) {
            map.put(convert(op), op);
        }

        // Special cases
        map.put("++", UnaryOperatorKind.POSTINC);
        map.put("--", UnaryOperatorKind.POSTDEC);

        return map;
    }

    public static String convert(UnaryOperatorKind kind) {

        switch (kind) {
        case POS:
            return "+";

        case NEG:
            return "-";
        case NOT:
            return "!";
        case COMPL:
            return "~";
        case PREINC:
            return "++_";
        case PREDEC:
            return "--_";
        case POSTINC:
            return "_++";
        case POSTDEC:
            return "_--";

        default:
            break;
        }
        throw new RuntimeException("The unary operator given is not supported: " + kind);

    }

    public static String convert(BinaryOperatorKind kind) {
        switch (kind) {
        case OR:
            return ("||");
        case AND:
            return ("&&");

        case BITOR:
            return ("|");

        case BITXOR:
            return ("^");

        case BITAND:
            return ("&");

        case EQ:
            return ("==");

        case NE:
            return ("!=");

        case LT:
            return ("<");

        case GT:
            return (">");

        case LE:
            return ("<=");

        case GE:
            return (">=");

        case SL:
            return ("<<");

        case SR:
            return (">>");

        case USR:
            return (">>>");

        case PLUS:
            return ("+");

        case MINUS:
            return ("-");

        case MUL:
            return ("*");

        case DIV:
            return ("/");

        case MOD:
            return ("%");

        case INSTANCEOF:
            return ("instanceof");

        default:
            break;
        }
        throw new RuntimeException("The binary operator given is not supported: " + kind);

    }

    public static Optional<BinaryOperatorKind> parseBinary(String kind) {
        // Check binary to operator map
        return Optional.ofNullable(STRING_TO_BINARY_OP.get().get(kind));
    }

    public static Optional<UnaryOperatorKind> parseUnary(String kind) {
        // Check unary to operator map
        return Optional.ofNullable(STRING_TO_UNARY_OP.get().get(kind));
    }
}
