/**
 * Copyright 2020 SPeCS.
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

import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.abstracts.joinpoints.ACall;
import weaver.kadabra.abstracts.joinpoints.ACallStatement;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;

/**
 * This is a "synthetic" join point, to emulate statements around single statement calls.
 * 
 * @author JoaoBispo
 *
 * @param <T>
 */
public class JCallStatement<T> extends ACallStatement {

    private final CtInvocation<T> call;

    public JCallStatement(CtInvocation<T> call) {
        super(new JStatement(call));
        this.call = call;
    }

    @Override
    public CtElement getNode() {
        return call;
    }

    @Override
    public ACall getCallImpl() {
        return JCall.newInstance(call);
    }

    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        var children = new AJoinPoint[1];
        children[0] = getCallImpl();
        return children;
    }

    // @Override
    // public String getCodeImpl() {
    // var origCode = super.getCodeImpl();
    // System.out.println("JCALLSTMT ORIG: " + origCode);
    // var endsWithSemi = origCode.trim().endsWith(";");
    // System.out.println("ENDS WITH SEMI? " + endsWithSemi);
    // // JCallStatement is a wrapper around an invocation, add ;
    // var mod = origCode.trim().endsWith(";") ? origCode : origCode + ";";
    // System.out.println("JCALLSTMT MOD: " + mod);
    // return mod;
    // }

}
