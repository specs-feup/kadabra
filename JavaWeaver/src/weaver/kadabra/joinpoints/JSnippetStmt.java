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
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.joinpoints;

import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.joinpoints.ASnippetStmt;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetStatement;

public class JSnippetStmt extends ASnippetStmt {

    private CtKadabraSnippetStatement node;

    private JSnippetStmt(CtKadabraSnippetStatement stmt, JavaWeaver weaver) {
        super(new JStatement(stmt, weaver), weaver);
        this.node = stmt;
    }

    public static JSnippetStmt newInstance(CtKadabraSnippetStatement stmt, JavaWeaver weaver) {
        return new JSnippetStmt(stmt, weaver);
    }

    @Override
    public CtKadabraSnippetStatement getNode() {
        return node;
    }

    @Override
    public void setLineImpl(Integer line) {
        node.setLine(line);
    }

    @Override
    public Integer getLineImpl() {
        return node.getLine().orElse(null);
    }
}
