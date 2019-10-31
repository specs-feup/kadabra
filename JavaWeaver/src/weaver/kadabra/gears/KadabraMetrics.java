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

package weaver.kadabra.gears;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.lara.interpreter.profile.BasicWeaverProfiler;
import org.lara.interpreter.profile.ReportWriter;
import org.lara.interpreter.profile.utils.UniqueMap;
import org.lara.interpreter.weaver.interf.JoinPoint;
import org.lara.interpreter.weaver.interf.events.Stage;
import org.lara.interpreter.weaver.interf.events.data.ActionEvent;
import org.lara.interpreter.weaver.interf.events.data.ApplyIterationEvent;
import org.lara.interpreter.weaver.interf.events.data.JoinPointEvent;
import org.lara.interpreter.weaver.interf.events.data.SelectEvent;
import org.lara.interpreter.weaver.joinpoint.LaraJoinPoint;

import pt.up.fe.specs.util.collections.AccumulatorMap;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtType;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AClass;
import weaver.kadabra.abstracts.joinpoints.AField;
import weaver.kadabra.abstracts.joinpoints.AMethod;

public class KadabraMetrics extends BasicWeaverProfiler {

    private String currentClass;
    private String currentMethod;
    private String currentField;
    private SelectEvent currentSelect;

    private Set<CtElement> advisedJPs = new HashSet<>();
    private AccumulatorMap<String> actionsPerformed = new AccumulatorMap<>();
    private UniqueMap<String, Object> applyIteration = new UniqueMap<>();
    private UniqueMap<String, Object> selects = new UniqueMap<>();
    private UniqueMap<String, Object> filteredSelects = new UniqueMap<>();
    private Set<CtType<?>> advisedTypes = new HashSet<>();
    private Set<CtExecutable<?>> advisedExecutables = new HashSet<>();
    // private KadabraUniqueNodeCounter selects = new KadabraUniqueNodeCounter();
    // private KadabraUniqueNodeCounter filteredSelects = new KadabraUniqueNodeCounter();

    @Override
    protected void resetImpl() {
        advisedJPs = new HashSet<>();
        actionsPerformed = new AccumulatorMap<>();
        applyIteration = new UniqueMap<>();
        selects = new UniqueMap<>();
        filteredSelects = new UniqueMap<>();
    }

    @Override
    protected void onSelectImpl(SelectEvent data) {
        switch (data.getStage()) {

        case BEGIN:
            currentSelect = data;
            break;
        case END:
            Optional<LaraJoinPoint> pointcut = data.getPointcut();
            if (pointcut.isPresent()) {
                LaraJoinPoint laraJoinPoint = pointcut.get();
                String key = data.getAspect_name() + data.getLabel();
                // JoinPoint reference = laraJoinPoint.getReference();
                // int total = countJPs(laraJoinPoint, 0);
                iterateJPs(laraJoinPoint, jp -> filteredSelects.put(key, jp.getNode()));
            }
            currentSelect = null;
            break;
        default:
            break;
        }
    }

    // private void iterateJPs(LaraJoinPoint laraJoinPoint, Consumer<JoinPoint> jp) {
    // jp.accept(laraJoinPoint.getReference());
    // if (!laraJoinPoint.isLeaf()) {
    // laraJoinPoint.getChildren().forEach(ljp -> iterateJPs(ljp, jp));
    // }
    // }

    // private int countJPs(LaraJoinPoint laraJoinPoint, int acc) {
    // acc++;
    // if (!laraJoinPoint.isLeaf()) {
    // for (LaraJoinPoint childJP : laraJoinPoint.getChildren()) {
    // acc = countJPs(childJP, acc);
    // }
    // }
    // return acc;
    // }

    @Override
    protected void onJoinPointImpl(JoinPointEvent data) {
        String key = currentSelect.getAspect_name() + currentSelect.getLabel();
        switch (data.getStage()) {

        case BEGIN:
            selects.put(key, data.getJoinPoint().getNode());
            break;
        case END:
            // if (data.isApprovedByFilter()) {
            // filteredSelects.addNode(key, data.getJoinPoint());
            // }
            break;
        default:
            break;
        }
    }

    @Override
    protected void onApplyImpl(ApplyIterationEvent data) {
        if (data.getStage().equals(Stage.BEGIN)) {
            String key = data.getAspect_name() + data.getSelect_label() + data.getLabel();
            for (JoinPoint jp : data.getPointcutChain()) {
                applyIteration.put(key, jp.getNode());
                if (jp instanceof AClass) {
                    currentClass = ((AClass) jp).getQualifiedNameImpl();
                    continue;
                }
                if (jp instanceof AMethod) {
                    currentMethod = ((AMethod) jp).getNameImpl();
                    continue;
                }
                if (jp instanceof AField) {
                    currentField = ((AField) jp).getNameImpl();
                    continue;
                }
            }
        } else {
            currentClass = null;
            currentMethod = null;
        }
    }

    @Override
    protected void onActionImpl(ActionEvent data) {

        if (data.getStage().equals(Stage.END)) {
            String name = "";
            if (currentClass != null) {
                name = currentClass;
            }
            if (currentMethod != null) {
                name += (currentClass != null ? "#" : "") + currentMethod;
            } else if (currentField != null) {
                name += (currentClass != null ? "#" : "") + currentField;
            }
            if (name.isEmpty()) {
                return; // What to do?
            }
            actionsPerformed.add(name);
            AJavaWeaverJoinPoint joinPoint = (AJavaWeaverJoinPoint) data.getJoinPoint();
            advisedJPs.add(joinPoint.getNode());

            CtExecutable<?> executableParent = joinPoint.getAncestor(CtExecutable.class);
            CtType<?> typeParent = joinPoint.getAncestor(CtType.class);

            if (typeParent != null) {
                advisedTypes.add(typeParent);
            }
            if (executableParent != null) {
                advisedExecutables.add(executableParent);
            }
        }
    }

    @Override
    protected void buildReport(ReportWriter writer) {
        writer.report("_selectedJoinPoints", selects.getTotalNodes());
        writer.report("_filteredJoinPoints", filteredSelects.getTotalNodes());
        writer.report("_iteratedJoinPoints", applyIteration.getTotalNodes());
        writer.report("_advisedJoinPoints", advisedJPs.size());
        writer.report("_actionsPerTarget", actionsPerformed.getAccMap());
        writer.report("_targetedTypes",
                advisedTypes.stream().map(t -> t.getQualifiedName()).collect(Collectors.toList()));
        writer.report("_targetedExecutables",
                advisedExecutables.stream()
                        .map(e -> e.getParent(CtType.class).getQualifiedName() + "#" + e.getSimpleName())
                        .collect(Collectors.toList()));
        // System.out.println("SELECTS: \n");
        // System.out.println(selects);
        // System.out.println("ITERATED: \n");
        // System.out.println(applyIteration);

        //
        // }
        // }
        // }
    }

}
