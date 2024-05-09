/**
 * Copyright 2017 SPeCS.
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

package weaver.kadabra.spoon.extensions.nodes;

import java.util.Optional;

import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.visitor.CtVisitor;
import spoon.support.reflect.code.CtCodeSnippetExpressionImpl;
import spoon.support.reflect.declaration.CtElementImpl;

public class CtKadabraSnippetExpression<T> extends CtElementImpl {

    private final CtCodeSnippetExpressionImpl<T> original;
    private Integer customLine;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CtKadabraSnippetExpression(CtCodeSnippetExpressionImpl<T> original) {
        this.original = original;
        this.customLine = null;
    }

    public CtCodeSnippetExpressionImpl<T> getOriginal() {
        return original;
    }
    //
    // @Override
    // public CtElement getParent() throws ParentNotInitializedException {
    // return original.getParent();
    // }

    @Override
    public void accept(CtVisitor visitor) {
        original.accept(visitor);
    }

    // @Override
    // public String getValue() {
    // return original.getValue();
    // }
    //
    // @Override
    // public <C extends CtCodeSnippet> C setValue(String value) {
    // return original.setValue(value);
    // }
    //
    // @Override
    // @SuppressWarnings("unchecked")
    // public <E extends CtExpression<T>> E compile() throws SnippetCompilationError {
    // return original.compile();
    // }

    @Override
    public CtCodeSnippetExpression<T> clone() {
        return original.clone();
    }

    public Optional<Integer> getLine() {
        return Optional.ofNullable(customLine);
    }

    public void setLine(int line) {
        this.customLine = line;
    }

}
