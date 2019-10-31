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

import org.lara.interpreter.weaver.interf.AGear;
import org.lara.interpreter.weaver.interf.events.data.AspectEvent;

import weaver.kadabra.util.KadabraLog;

@Deprecated
public class JavaWeaverGear extends AGear {

    public static final String TIMER_CLASS = "weaver/kadabra/monitor/CodeTimer.tmpl";
    private static final String TIMER_ASPECT = "kadabra$monitor$Timer$TimeMonitor";
    private boolean timerUsed = false;

    @Override
    public void onAspect(AspectEvent data) {
        if (!timerUsed && data.getAspectCallee().equals(TIMER_ASPECT)) {
            KadabraLog.info("Timer.java will be copied to workspace");
            timerUsed = true;
        }
    }

    public boolean isTimerUsed() {
        return timerUsed;
    }
}