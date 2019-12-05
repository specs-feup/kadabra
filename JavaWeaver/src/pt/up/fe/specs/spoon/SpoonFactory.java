/**
 * Copyright 2019 SPeCS.
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

package pt.up.fe.specs.spoon;

import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtComment.CommentType;
import spoon.reflect.factory.Factory;

/**
 * Factory methods for CtElements.
 * 
 * @author JoaoBispo
 *
 */
public class SpoonFactory {

    private final Factory factory;

    public SpoonFactory(Factory factory) {
        this.factory = factory;
    }

    public Factory getFactory() {
        return factory;
    }

    public CtComment comment(String comment, CommentType type) {
        return factory.Code().createComment(comment, type);
    }
}
