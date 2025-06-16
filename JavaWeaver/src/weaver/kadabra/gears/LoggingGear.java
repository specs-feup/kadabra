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

package weaver.kadabra.gears;

import java.util.Optional;

import org.lara.interpreter.weaver.interf.AGear;
import org.lara.interpreter.weaver.interf.events.Stage;
import org.lara.interpreter.weaver.interf.events.data.ApplyEvent;
import org.lara.interpreter.weaver.joinpoint.LaraJoinPoint;

import weaver.kadabra.util.KadabraLog;

public class LoggingGear extends AGear {

    @Override
    public void onApply(ApplyEvent data) {
        Optional<LaraJoinPoint> jpset = data.getJpset();
        if (data.getStage().equals(Stage.BEGIN) && !jpset.isPresent()) {
            String message = "Apply '" + data.getLabel()
                    + "' was not executed for select '" + data.getSelect_label()
                    + "'. Reason: the select is empty.";
            printWarning("SomewhereInCode", message);
        }

    }

    private static void printWarning(String aspect_name, String message) {
        KadabraLog.warning("aspect '" + aspect_name + "': " + message);
    }

    @Override
    public void reset() {

    }

}