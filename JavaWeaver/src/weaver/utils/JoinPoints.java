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

import pt.up.fe.specs.util.utilities.StringLines;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;

/**
 * Utility methods related to join points.
 * 
 * @author JoaoBispo
 *
 */
public class JoinPoints {

    public static String toAst(AJavaWeaverJoinPoint node, String prefix) {
        var builder = new StringBuilder();
        toAst(node, prefix, builder);
        return builder.toString();
    }

    private static void toAst(AJavaWeaverJoinPoint node, String prefix,
            StringBuilder builder) {

        builder.append(prefix);

        builder.append(node.getJoinPointType());
        var nodeString = node.toString();

        if (!nodeString.isBlank() && StringLines.getLines(nodeString).size() < 2) {
            builder.append(" (");
            builder.append(node.toString());
            builder.append(")");
        }
        builder.append("\n");

        for (var child : node.getChildrenArrayImpl()) {
            toAst((AJavaWeaverJoinPoint) child, prefix + "  ", builder);
        }

    }
}
