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

package weaver.utils;

import org.lara.interpreter.weaver.ast.AAstMethods;
import org.lara.interpreter.weaver.interf.JoinPoint;
import org.lara.interpreter.weaver.interf.WeaverEngine;

import spoon.reflect.code.CtBodyHolder;
import spoon.reflect.declaration.CtElement;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class KadabraAstMethods extends AAstMethods<CtElement> {

    public KadabraAstMethods(WeaverEngine weaverEngine) {
        super(weaverEngine);
    }

    @Override
    public Class<CtElement> getNodeClass() {
        return CtElement.class;
    }

    @Override
    protected JoinPoint toJavaJoinPointImpl(CtElement node) {
        return CtElement2JoinPoint.convert(node);
    }

    @Override
    protected String getJoinPointNameImpl(CtElement node) {
        return KadabraCommonLanguage.getJoinPointName(node);
    }

    @Override
    protected Object[] getChildrenImpl(CtElement node) {
        return SpoonUtils.getChildren(node).toArray();
    }

    @Override
    protected Object[] getScopeChildrenImpl(CtElement node) {
        if (node instanceof CtBodyHolder) {
            var body = ((CtBodyHolder) node).getBody();
            return SpoonUtils.getChildren(body).toArray();
        }

        return new Object[0];
    }

}
