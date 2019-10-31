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

package weaver.utils.weaving;

import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.reference.CtLocalVariableReference;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.element.RankCalculator;

public class AttributeUtils {

    private static final String PRAGMA_DEFINITION = "@";

    public static String getSimpleName(String fullName) {

        final int lastDot = fullName.lastIndexOf(CtPackage.PACKAGE_SEPARATOR);
        if (lastDot < 0) {
            return fullName;
        }
        return fullName.substring(lastDot + 1);
    }

    /**
     * Get the package of a given class name
     * 
     * @param fullName
     * @return
     */
    public static String getPackage(String fullName) {

        final int lastDot = fullName.lastIndexOf(CtPackage.PACKAGE_SEPARATOR);
        if (lastDot < 0) {
            return "";
        }
        return fullName.substring(0, lastDot);
    }

    public static CtLocalVariable<Integer> getControlVarAsInteger(CtFor ctFor) {
        CtLocalVariable<?> cv = AttributeUtils.getControlVar(ctFor);
        Class<?> actualClass = cv.getType().getActualClass();
        if (actualClass.isAssignableFrom(Integer.class) || actualClass.isAssignableFrom(int.class)) {
            @SuppressWarnings("unchecked")
            CtLocalVariable<Integer> cast = CtLocalVariable.class.cast(cv);
            return cast;
        }
        String message = "Control variable for loop "
                + RankCalculator.calculateString(ctFor, CtLoop.class)
                + " Cannot be casted to Integer. Actual class: " + actualClass;

        throw new JavaWeaverException("When converting control var to integer", new ClassCastException(message));
    }

    /**
     * Fetch the control variable declaration of a For loop. <br>
     * <b>Current Conditions:</b> only one init may be defined in the loop
     * 
     * @param ctfor
     * @return
     */
    public static CtLocalVariable<?> getControlVar(CtFor ctfor) {

        if (ctfor.getForInit().size() != 1) {
            KadabraLog.warning("Cannot find control variable for loop "
                    + RankCalculator.calculateString(ctfor, CtLoop.class)
                    + ". Init should have 1 statement");
            return null;
        }
        CtStatement ctStatement = ctfor.getForInit().get(0);

        if (ctStatement instanceof CtLocalVariable) {
            CtLocalVariable<?> localVariable = (CtLocalVariable<?>) ctStatement;
            return localVariable;
        }
        return null;
    }

    /**
     * Fetch the control variable of a For loop. <br>
     * <b>Current Conditions:</b> only one init may be defined in the loop
     * 
     * @param ctfor
     * @return
     */
    public static CtLocalVariableReference<?> getControlVarReference(CtFor ctfor) {

        CtLocalVariable<?> localVariable = getControlVar(ctfor);

        if (localVariable != null) {
            return localVariable.getReference();
        }
        return null;
    }

    public static String getPragmaDefinition() {
        return PRAGMA_DEFINITION;
    }

}
