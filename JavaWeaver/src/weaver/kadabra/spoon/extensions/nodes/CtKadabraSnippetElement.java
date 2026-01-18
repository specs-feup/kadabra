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

import spoon.reflect.declaration.CtCodeSnippet;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.CtVisitor;
import spoon.support.reflect.declaration.CtTypeImpl;

/**
 * Used to inject arbitrary code inside a class
 * 
 * @author tiago
 *
 */
public class CtKadabraSnippetElement extends CtTypeImpl<Object> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final String PREFIX = "Snippet_";
    private static int counter = 0;

    private CtKadabraSnippetStatement aStatement;

    public CtKadabraSnippetElement(Factory factory) {
        setFactory(factory);
        this.setSimpleName(getNewName());
        aStatement = new CtKadabraSnippetStatement(factory);
    }

    @Override
    public void accept(CtVisitor visitor) {

        visitor.visitCtCodeSnippetStatement(aStatement);
    }

    public String getValue() {
        return aStatement.getValue();
    }

    public <C extends CtCodeSnippet> C setValue(String value) {
        return aStatement.setValue(value);
    }

    @Override
    public CtKadabraSnippetElement clone() {
        CtKadabraSnippetElement newSnippet = new CtKadabraSnippetElement(getFactory());
        newSnippet.setValue(aStatement.getValue());
        return newSnippet;
    }

    private static String getNewName() {
        return PREFIX + (counter++);
    }

    @Override
    public boolean isSubtypeOf(CtTypeReference<?> type) {
        return false;
    }

}
