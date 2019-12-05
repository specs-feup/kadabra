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

package weaver.kadabra.importable;

import java.util.Arrays;

import spoon.reflect.code.CtComment.CommentType;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.joinpoints.JComment;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class KadabraJoinPoints {

    /**
     * Creates a new comment join point.
     * 
     * @param comment
     *            the contents of the comment
     * @param type
     *            the type of comment, according to CtComment.CommentType
     * @return
     */
    public static JComment comment(String comment, String type) {
        // Convert the type
        CommentType typeEnum = null;

        try {
            typeEnum = CommentType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException(
                    "Comment type not supported: '" + type + "'. Use one of " + Arrays.toString(CommentType.values()));
        }

        return (JComment) CtElement2JoinPoint.convert(JavaWeaver.getFactory().comment(comment, typeEnum));
    }
}
