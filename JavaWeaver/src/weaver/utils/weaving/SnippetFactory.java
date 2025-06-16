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

package weaver.utils.weaving;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;

import pt.up.fe.specs.util.SpecsLogs;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.Factory;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetElement;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetStatement;

public class SnippetFactory {

    public static CtCodeSnippetStatement createSnippetStatement(String snippetStr, CtElement nodeReference) {
        return createSnippetStatement(snippetStr, nodeReference.getFactory());
    }

    public static CtCodeSnippetStatement createSnippetStatement(String snippetStr, Factory factory) {
        final CtCodeSnippetStatement snippet = SnippetFactory.createSnippetStatement(factory);

        snippet.setValue(snippetStr);
        snippet.setDocComment("Inserted by Kadabra");
        return snippet;
    }

    private static CtCodeSnippetStatement createSnippetStatement(Factory factory) {
        // return factory.Core().createCodeSnippetStatement();
        return new CtKadabraSnippetStatement(factory);

    }

    public static <T> CtCodeSnippetExpression<T> createSnippetExpression(Factory factory, String value) {
        CtCodeSnippetExpression<T> expr = createSnippetExpression(factory);
        expr.setValue(value);
        return expr;
    }

    private static <T> CtCodeSnippetExpression<T> createSnippetExpression(Factory factory) {
        // return factory.Core().createCodeSnippetStatement();
        return factory.Core().createCodeSnippetExpression();

    }

    public static CtKadabraSnippetElement createSnippetElement(Factory factory) {
        CtKadabraSnippetElement element = new CtKadabraSnippetElement(factory);
        return element;
    }

    public static CtKadabraSnippetElement createSnippetElement(Factory factory, String value) {
        CtKadabraSnippetElement element = createSnippetElement(factory);
        element.setValue(value);
        return element;
    }

    public static <T extends Object> CtCodeSnippetExpression<T> snippetExpression(String snippetStr,
            Factory factory) {
        final CtCodeSnippetExpression<T> snippet = factory.Core().createCodeSnippetExpression();
        snippet.setValue(snippetStr);
        // snippet.setDocComment("Inserted by Kadabra");
        return snippet;
    }

    @Deprecated
    public static String processSnippet(String snippetStr) {
        StringBuilder builder = new StringBuilder();
        int level = 0;
        String indent = "\t";
        String newLine = System.getProperty("line.separator");
        try (BufferedReader br = new BufferedReader(new StringReader(snippetStr))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                int alreadyTook = 0;
                if (line.startsWith("}")) {
                    level--;
                    alreadyTook = 1;
                }
                builder.append(indent.repeat(level));
                builder.append(line);
                builder.append(newLine);
                level += StringUtils.countMatches(line, "{") - StringUtils.countMatches(line, "}") + alreadyTook;
                if (level < 0) {
                    level = 0;
                }
            }
        } catch (IOException e) {
            SpecsLogs.warn("Error message:\n", e);
        }

        return builder.toString();
    }

}
