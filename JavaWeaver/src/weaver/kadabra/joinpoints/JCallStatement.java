/**
 * Copyright 2020 SPeCS.
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

import spoon.reflect.code.CtInvocation;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ACall;
import weaver.kadabra.abstracts.joinpoints.ACallStatement;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;

/**
 * This is a "synthetic" join point, to emulate statements around single
 * statement calls.
 *
 * @author JoaoBispo
 */
public class JCallStatement<Self extends JCallStatement<Self>> extends ACallStatement<Self> {

    public JCallStatement(CtInvocation call, JWeaver weaver) {
        super(call, weaver);
    }

    @Override
    public CtInvocation<?> getNodeImpl() {
        return (CtInvocation<?>) super.getNodeImpl();
    }

    @Override
    public ACall<?> getCallImpl() {
        return new JCall<>(getNodeImpl(), getWeaverEngine());
    }

    @Override
    public AJoinpoint<?>[] getChildrenImpl() {
        var children = new AJoinpoint[1];
        children[0] = getCallImpl();
        return children;
    }

    @Override
    public String getCodeImpl() {
        var origCode = super.getCodeImpl();

        return origCode.trim().endsWith(";") ? origCode : origCode + ";";
    }

    /**
     * TODO: This is an example where the getSrcCodeImpl() in JJoinpoint
     * does not call the overridden getCodeImpl()
     */
    @Override
    public String getSrcCodeImpl() {
        return getCodeImpl();
    }

}
