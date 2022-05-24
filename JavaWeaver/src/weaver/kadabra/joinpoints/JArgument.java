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

package weaver.kadabra.joinpoints;

import java.util.List;

import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtParameter;
import weaver.kadabra.abstracts.joinpoints.AArgument;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.utils.SpoonUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JArgument<T> extends AArgument {

    private final CtExpression<T> node;
    private final CtInvocation<?> call;

    private JArgument(CtExpression<T> expr) {// , CtInvocation<K> call) {
        super(new JExpression<>(expr));
        node = expr;
        this.call = SpoonUtils.getAncestor(node, CtInvocation.class);
    }

    public static <T> JArgument<T> newInstance(CtExpression<T> expr) {
        return new JArgument<>(expr);
    }
    // public static <T, K> JArgument<T, K> newInstance(CtExpression<T> expr, CtInvocation<K> call) {
    // System.out.println(expr.getParent().equals(call));
    // return new JArgument<>(expr, call);
    // }

    @Override
    public Integer getIndexImpl() {
        int index = call.getArguments().indexOf(node);
        return index;
    }

    @Override
    public String getNameImpl() {
        int index = getIndexImpl();
        if (index < 0) {
            // ???
        }
        // List<CtTypeReference<?>> parameters = call.getExecutable().getParameters().get(index);
        CtExecutable<?> executable = call.getExecutable().getDeclaration();
        if (executable == null) {
            throw new JavaWeaverException("When retrieving argument name",
                    new RuntimeException("The actual executable used in the call could not be found"));
        }
        // if (executable.isConstructor()) {
        // Constructor<?> constructor = executable.getActualConstructor();
        // if (constructor == null) {
        // throw new JavaWeaverException("When retrieving argument name",
        // new RuntimeException("The actual constructor used in the call could not be found"));
        // }
        // Parameter parameter = constructor.getParameters()[index];
        //
        // return parameter.getName();
        // }

        List<CtParameter<?>> parameters = executable.getParameters();
        CtParameter<?> parameter = parameters.get(index);
        String simpleName = parameter.getSimpleName();
        // System.out.println(simpleName);
        return simpleName;
    }

    @Override
    public CtExpression<?> getNode() {
        return node;
    }

    @Override
    public String toString() {
        return getSrcCodeImpl();
    }

    @Override
    public AExpression getExprImpl() {
        return CtElement2JoinPoint.convert(node, AExpression.class);
    }

}
