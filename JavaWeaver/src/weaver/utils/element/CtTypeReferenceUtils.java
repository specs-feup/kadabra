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
 * specific language governing permissions and limitations under the License.
 */

package weaver.utils.element;

import spoon.reflect.reference.CtArrayTypeReference;
import spoon.reflect.reference.CtTypeReference;

public class CtTypeReferenceUtils {

    public static String getType(CtTypeReference<?> type) {

        try {
            if (getIsArray(type)) {
                return type.getActualClass().getComponentType().toString();
            }
        } catch (Exception e) {
            // Do nothing, sometimes it can launch exception, such as when the type of the class is not on the classpath
        }

        return type.toString();
    }

    public static boolean getIsArray(CtTypeReference<?> type) {
        return type.getActualClass().isArray();
    }

    public static int getDimension(CtTypeReference<?> type) {

        boolean isArray = type.getActualClass().isArray();
        if (isArray) {
            return ((CtArrayTypeReference<?>) type).getDimensionCount();
        }
        return 0;
    }

    public static boolean getIsPrimitive(CtTypeReference<?> type) {
        return type.isPrimitive();
    }

}
