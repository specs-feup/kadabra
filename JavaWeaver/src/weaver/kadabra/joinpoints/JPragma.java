/**
 * Copyright 2017 SPeCS.
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import spoon.reflect.code.CtComment;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AComment;
import weaver.kadabra.abstracts.joinpoints.APragma;
import weaver.kadabra.enums.CommentType;

public class JPragma<Self extends JPragma<Self>> extends APragma<Self> {

    private static String PRAGMA_NAME_REGEX = "@(\\S+)";
    private static final Pattern PRAGMA_REGEX = Pattern.compile(PRAGMA_NAME_REGEX);

    public JPragma(CtComment comment, JWeaver weaver) {
        super(comment, weaver);
    }

    public JPragma(JComment<?> comment, JWeaver weaver) {
        this(comment.getNodeImpl(), weaver);
    }

    @Override
    public String getNameImpl() {
        return extractName();
    }

    @Override
    public String getContentImpl() {
        return extractContent();
    }

    private String extractName() {
        String content = getNodeImpl().getContent();
        Matcher matcher = PRAGMA_REGEX.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private String extractContent() {
        String content = getNodeImpl().getContent();
        return content.replaceFirst(PRAGMA_NAME_REGEX, "").trim();
    }

    @Override
    public CtComment getNodeImpl() {
        return (CtComment) super.getNodeImpl();
    }

    @Override
    public CommentType getTypeImpl() {

        return CommentType.PRAGMA;
    }

    public static boolean isPragma(CtComment comment) {
        if (!comment.getCommentType().equals(spoon.reflect.code.CtComment.CommentType.INLINE)) {
            return false;
        }
        String content = comment.getContent();
        Matcher matcher = PRAGMA_REGEX.matcher(content);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
}
