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

package weaver.utils.dev;

public class DebugUtils {
    public static void printObjectInfo(Object obj) {
        System.out.println(obj);
    }

    public static void printClass(Object obj) {
        System.out.println(obj.getClass());
    }

    public static String getClass(Object obj) {
        return obj.getClass().getCanonicalName();
    }
}
