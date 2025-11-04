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
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.gears;

import java.util.HashSet;
import java.util.Set;

import org.lara.interpreter.weaver.interf.AGear;
import org.lara.interpreter.weaver.interf.JoinPoint;
import org.lara.interpreter.weaver.interf.events.data.ActionEvent;

import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.util.KadabraLog;

public class Report extends AGear {

    private final Set<Object> advisedJoinPoints = new HashSet<>();

    @Override
    public void onAction(ActionEvent data) {
        JoinPoint joinPoint = data.getJoinPoint();
        if (!(joinPoint instanceof AJoinPoint)) {
            KadabraLog.warning("Report: unrecognized type of join point: " + joinPoint.getClass());
            return;
        }
        AJoinPoint aJP = (AJoinPoint) joinPoint;
        advisedJoinPoints.add(aJP.getNode());
    }

    public Set<Object> getAdvisedJoinPoints() {
        return advisedJoinPoints;
    }

    @Override
    public void reset() {
        advisedJoinPoints.clear();
    }
}
