/**
 * Copyright 2015 SPeCS.
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

import java.util.List;

import spoon.reflect.code.CtStatement;
import tdrc.utils.StringUtils;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.ACall;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.kadabra.abstracts.joinpoints.AVar;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.converters.CtStatement2AStatement;

public class JStatement extends AStatement {

    private final CtStatement node;

    public JStatement(CtStatement node) {

        this.node = node;
    }

    public static AStatement newInstance(CtStatement node) {

        return CtStatement2AStatement.convert(node);
    }

    @Override
    public List<? extends AVar> selectVar() {
        return SelectUtils.selectVar(node);
    }

    @Override
    public List<? extends ACall> selectCall() {
        return SelectUtils.selectCall(node);

        // List<JCall<?>> select = SelectUtils.select(node, CtInvocation.class, JCall::newInstance);
        //
        // select.stream().forEach(call -> System.out.println("Call name:" + call.getNameImpl()));
        //
        // return select.stream()
        // .filter(call -> !call.getNameImpl().equals("<init>"))
        // .collect(Collectors.toList());
        // return select;
    }

    @Override
    public AJoinPoint[] insertImpl(String position, String code) {
        return new AJoinPoint[] { insertImplJStatement(position, code) };
    }

    public AJavaWeaverJoinPoint insertImplJStatement(String position, String code) {
        return ActionUtils.insert(position, code, node, getWeaverProfiler());
    }

    @Override
    public AJoinPoint insertAfterImpl(String code) {
        return insertImplJStatement("after", code);
    }

    @Override
    public CtStatement getNode() {
        return node;
    }

    @Override
    public Integer getEndLineImpl() {
        return node.getPosition().getEndLine();
    }

    @Override
    public String getKindImpl() {
        String tempType = node.getClass().getSimpleName().replace("Ct", "").replace("Impl", "");
        return StringUtils.firstCharToLower(tempType);
    }
}
