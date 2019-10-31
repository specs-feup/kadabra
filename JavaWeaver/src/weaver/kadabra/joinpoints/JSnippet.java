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

package weaver.kadabra.joinpoints;

import java.util.Collections;
import java.util.List;

import weaver.kadabra.abstracts.joinpoints.ACall;
import weaver.kadabra.abstracts.joinpoints.AVar;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetStatement;

public class JSnippet extends JStatement {

    private CtKadabraSnippetStatement node;

    private JSnippet(CtKadabraSnippetStatement stmt) {
        super(stmt);
        this.node = stmt;
    }

    public static JSnippet newInstance(CtKadabraSnippetStatement stmt) {
        return new JSnippet(stmt);
    }

    @Override
    public String getKindImpl() {
        return "Snippet";
    }

    @Override
    public Integer getEndLineImpl() {
        return null;
    }

    @Override
    public List<? extends AVar> selectVar() {
        return Collections.emptyList();
    }

    @Override
    public List<? extends ACall> selectCall() {
        return Collections.emptyList();
    }

    @Override
    public CtKadabraSnippetStatement getNode() {
        return node;
    }

}
