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

package weaver.utils.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtComment;
import weaver.kadabra.util.KadabraLog;

public class CommentProcessor extends AbstractProcessor<CtComment> {

    // List<CtComment> toReplace = new ArrayList<>();

    @Override
    public void process(CtComment element) {
        // toReplace.add(element);
        KadabraLog.info(element.getContent() + "->" + element.hashCode() + "-"
                + element.getPosition().hashCode());
    }

    @Override
    public void processingDone() {
        // toReplace.forEach(SpoonUtils::replaceCommentWithWrapper);
        // SpoonUtils.replaceCommentWithWrapper(element);
    }
}
