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

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ASnippetStmt;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetStatement;

public class JSnippetStmt<Self extends JSnippetStmt<Self>> extends ASnippetStmt<Self> {

    private final CtKadabraSnippetStatement snippetStatement;

    public JSnippetStmt(CtKadabraSnippetStatement stmt, JWeaver weaver) {
        super(stmt, weaver);
        this.snippetStatement = stmt;
    }

    @Override
    public CtKadabraSnippetStatement getNodeImpl() {
        return snippetStatement;
    }

    @Override
    public void setLineImpl(Integer line) {
        snippetStatement.setLine(line);
    }

    @Override
    public Integer getLineImpl() {
        return snippetStatement.getLine().orElse(null);
    }
}
