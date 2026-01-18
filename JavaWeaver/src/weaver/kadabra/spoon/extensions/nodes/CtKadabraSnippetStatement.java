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
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.spoon.extensions.nodes;

import java.util.Optional;

import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.CtCodeSnippet;
import spoon.reflect.factory.Factory;
import spoon.reflect.visitor.CtVisitor;
import spoon.support.reflect.code.CtCodeSnippetStatementImpl;

/**
 * {@link CtCodeSnippetStatement} extension to include a snippet 'as is' (i.e., no extra ';' at the end of the statement
 * 
 * @author Tiago
 *
 */
public class CtKadabraSnippetStatement extends CtCodeSnippetStatementImpl {

    private Integer customLine;

    public CtKadabraSnippetStatement(Factory factory) {
        setFactory(factory);

        customLine = null;
    }

    @Override
    public <C extends CtCodeSnippet> C setValue(String value) {
        // Remove trailing spaces
        value = value.trim();
        return super.setValue(value);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void accept(CtVisitor v) {
        v.visitCtCodeSnippetStatement(this);
    }

    public Optional<Integer> getLine() {
        return Optional.ofNullable(customLine);
    }

    public void setLine(int line) {
        this.customLine = line;
    }

}
