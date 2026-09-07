/**
 * Copyright 2017 SPeCS.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.joinpoints;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import org.lara.interpreter.weaver.interf.enums.InsertPosition;

import pt.up.fe.specs.util.SpecsEnums;
import pt.up.fe.specs.util.SpecsLogs;
import pt.up.fe.specs.util.exceptions.NotImplementedException;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtStatement;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AAnnotation;
import weaver.kadabra.abstracts.joinpoints.ACallStatement;
import weaver.kadabra.abstracts.joinpoints.AExecutable;
import weaver.kadabra.abstracts.joinpoints.AExpression;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.AStatement;
import weaver.kadabra.abstracts.joinpoints.AType;
import weaver.kadabra.spoon.extensions.launcher.JWEnvironment;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.JoinPoints;
import weaver.utils.SpoonUtils;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;
import weaver.utils.weaving.converters.CtExpression2AExpression;
import weaver.utils.weaving.converters.CtExecutable2AExecutable;
import weaver.utils.weaving.converters.CtStatement2AStatement;
import weaver.utils.weaving.converters.CtType2AType;

/**
 * Editable class which contains the implementation shared by all join points.
 * This class will NOT be overwritten by the generator.
 * <p>
 * It is also the default join point, used to represent Spoon nodes that do not
 * have a more specific join point.
 */
public class JJoinpoint<Self extends JJoinpoint<Self>> extends AJoinpoint<Self> {

    public JJoinpoint(CtElement node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public JWeaver getWeaverEngine() {
        return (JWeaver) super.getWeaverEngine();
    }

    @Override
    public boolean getSameImpl(AJoinpoint<?> other) {
        return this.get_class().equals(other.get_class()) && this.getCompareNodesImpl(other);
    }

    @Override
    public boolean getCompareNodesImpl(AJoinpoint<?> aJoinPoint) {
        return getNodeImpl().equals(aJoinPoint.getNodeImpl());
    }

    @Override
    public boolean getEqualsImpl(Self jp) {
        if (!(jp instanceof AJoinpoint<?>)) {
            return false;
        }

        return this.getSameImpl(jp);
    }

    @Override
    public AJoinpoint<?> getRootImpl() {
        return getWeaverEngine().getRootJp();
    }

    @Override
    public String getSrcCodeImpl() {
        return getCodeImpl();
    }

    @Override
    public String getCodeImpl() {
        return getWeaverEngine().getSourceCodePrinter().getSourceCode(getNodeImpl());
    }

    @Override
    public void setLineImpl(int value) {
        CtElement node = getNodeImpl();
        if (node == null) {
            KadabraLog.warning("Cannot change line in this join point: " + get_class());
        }
        SourcePosition position = node.getPosition();
        if (position == null) {
            KadabraLog.warning("Cannot change line in this join point: " + get_class());
        }
        int line = position.getLine();
        KadabraLog.info("Changing line number from " + line + " to " + value);
    }

    @Override
    public void setLineImpl(String value) {
        setLineImpl(Integer.parseInt(value));
    }

    @Override
    public AJoinpoint<?> getParentImpl() {
        // If no node, no parent
        // This condition is a bit suspicious,
        // because if a join point returns a null node
        // (e.g., JApp or JFile), they should have their
        // own implementations of getParentImpl()
        if (getNodeImpl() == null) {
            return null;
        }

        CtElement parent = getNodeImpl().getParent();
        if (parent == null) {
            return null;
        }

        var parentJp = CtElement2JoinPoint.convert(parent, getWeaverEngine());

        // Special case: if parent is a CallStatement, return the Call instead
        if (parentJp instanceof ACallStatement<?> callStatement) {
            return callStatement.getCallImpl();
        }

        return parentJp;
    }

    @Override
    public AJoinpoint<?> getAstParentImpl() {
        return getParentImpl();
    }

    /**
     * @param ancestorType
     * @return
     */
    public <T extends CtElement> T getAncestor(Class<T> ancestorType) {
        return SpoonUtils.getAncestor(getNodeImpl(), ancestorType);
    }

    public <T extends CtElement> Optional<T> getAncestorTry(Class<T> ancestorType) {
        return SpoonUtils.getAncestorTry(getNodeImpl(), ancestorType);
    }

    @Override
    public AJoinpoint<?> getGetAncestorImpl(String type) {
        Preconditions.checkNotNull(type, "Missing type of ancestor in attribute 'ancestor'");

        AJoinpoint<?> currentNode = getParentImpl();
        while (currentNode != null) {
            if (currentNode.getInstanceOfImpl(type)) {
                return currentNode;
            }
            currentNode = currentNode.getParentImpl();
        }

        return currentNode;
    }

    protected AExpression<?> toAExpression(CtExpression<?> expression) {
        return CtExpression2AExpression.convertToExpression(expression, getWeaverEngine());
    }

    protected AStatement<?> toAStatement(CtStatement statement) {
        return (AStatement<?>) CtStatement2AStatement.convert(statement, getWeaverEngine());
    }

    protected AType<?> toAType(CtType<?> type) {
        return CtType2AType.convert(type, getWeaverEngine());
    }

    protected AExecutable<?> toAExecutable(CtExecutable<?> exec) {
        return CtExecutable2AExecutable.convert(exec, getWeaverEngine());
    }

    @Override
    public Integer getLineImpl() {
        if (getNodeImpl() == null) {
            return null;
        }
        SourcePosition position = getNodeImpl().getPosition();
        if (position == null || !position.isValidPosition()) {
            return null;
        }

        return position.getLine();
    }

    @Override
    public Integer getColumnImpl() {
        if (getNodeImpl() == null) {
            return null;
        }
        SourcePosition position = getNodeImpl().getPosition();
        if (position == null || !position.isValidPosition()) {
            return null;
        }

        return position.getColumn();
    }

    /**
     * Required, because original insertImpl returns JoinPoint, but abstract join
     * points return AJoinpoint.
     */
    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, String code) {
        throw new RuntimeException("Not implemented yet for join point type '" + get_class() + "'");
    }

    @Override
    public AJoinpoint<?> insertBeforeImpl(AJoinpoint<?> node) {
        insertImpl(InsertPosition.AFTER, node);
        return node;
    }

    @Override
    public AJoinpoint<?> insertBeforeImpl(String code) {
        return insertImpl(InsertPosition.BEFORE, code)[0];
    }

    @Override
    public AJoinpoint<?> insertAfterImpl(AJoinpoint<?> node) {
        insertImpl(InsertPosition.AFTER, node);
        return node;
    }

    @Override
    public AJoinpoint<?> insertAfterImpl(String code) {
        return insertImpl(InsertPosition.AFTER, code)[0];
    }

    @Override
    public AJoinpoint<?> insertReplaceImpl(AJoinpoint<?> jp) {
        return replaceWithImpl(jp);
    }

    @Override
    public AJoinpoint<?> insertReplaceImpl(String code) {
        return replaceWithImpl(code);
    }

    @Override
    public AJoinpoint<?> replaceWithImpl(AJoinpoint<?> jp) {
        return insertImpl(InsertPosition.REPLACE, jp)[0];
    }

    @Override
    public AJoinpoint<?> replaceWithImpl(String code) {
        return insertImpl(InsertPosition.REPLACE, code)[0];
    }

    @Override
    public AJoinpoint<?>[] insertImpl(InsertPosition position, AJoinpoint<?> joinPoint) {
        throw new NotImplementedException(this);
    }

    @Override
    public AJoinpoint<?>[] getDescendantsImpl() {
        return getJpDescendantsStream().toArray(size -> new AJoinpoint[size]);
    }

    @Override
    public boolean getIsStatementImpl() {
        return this instanceof AStatement<?>;
    }

    @Override
    public boolean getIsBlockImpl() {
        return getNodeImpl() instanceof CtBlock;
    }

    @Override
    public AJoinpoint<?> copyImpl() {
        // Clone the node
        var copy = getNodeImpl().clone();
        // Set the parent
        copy.setParent(getNodeImpl().getParent());

        return CtElement2JoinPoint.convert(copy, getWeaverEngine());
    }

    private List<? extends CtElement> getChildrenNodes() {
        return SpoonUtils.getChildren(getNodeImpl());
    }

    @Override
    public java.util.stream.Stream<AJoinpoint<?>> getJpChildrenStream() {
        return Arrays.stream(getChildrenImpl());
    }

    @Override
    public AJoinpoint<?> getJpParent() {
        return getParentImpl();
    }

    @Override
    public AJoinpoint<?>[] getScopeNodesImpl() {
        return getChildrenImpl();
    }

    @Override
    public AJoinpoint<?>[] getChildrenImpl() {
        return getChildrenNodes().stream()
                .map(node -> CtElement2JoinPoint.convert(node, getWeaverEngine()))
                .toArray(size -> new AJoinpoint[size]);
    }

    @Override
    public AJoinpoint<?> getChildImpl(int index) {
        return getChildrenImpl()[index];
    }

    @Override
    public int getNumChildrenImpl() {
        return getChildrenImpl().length;
    }

    @Override
    public String getAstImpl() {
        var node = getNodeImpl();

        // If no node, start from join point
        if (node == null) {
            return JoinPoints.toAst(this, "");
        }

        // This method is more robust regarding the initial node
        return SpoonUtils.toAst(getNodeImpl(), "", getWeaverEngine());
    }

    @Override
    public String getToStringImpl() {
        if (getNodeImpl() == null) {
            return "Joinpoint '" + get_class() + "'";
        }
        return getNodeImpl().toString();
    }

    @Override
    public void removeImpl() {
        // Delete from annotations
        JWEnvironment env = ActionUtils.getKadabraEnvironment(getWeaverEngine().getFactory().getSpoonFactory());
        // TODO: This remove can be optimized
        env.getTable().remove(getNodeImpl());

        getNodeImpl().delete();
    }

    @Override
    public String[] getModifiersImpl() {
        return modifiersToString(SpoonUtils.getModifiers(getNodeImpl()));
    }

    public String[] modifiersToString(Collection<ModifierKind> modifiers) {
        return modifiers.stream()
                .map(ModifierKind::name)
                .toArray(length -> new String[length]);
    }

    @Override
    public boolean getHasModifierImpl(String modifier) {
        ModifierKind modifierKind = null;
        try {
            modifierKind = ModifierKind.valueOf(modifier.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Invalid modifier '" + modifier + "', please use one of: "
                    + Arrays.toString(ModifierKind.values()));
        }

        return SpoonUtils.getModifiers(getNodeImpl()).contains(modifierKind);
    }

    @Override
    public boolean getIsFinalImpl() {
        return SpoonUtils.getModifiers(getNodeImpl()).contains(ModifierKind.FINAL);
    }

    @Override
    public boolean getIsStaticImpl() {
        return SpoonUtils.getModifiers(getNodeImpl()).contains(ModifierKind.STATIC);
    }

    @Override
    public AAnnotation<?>[] getAnnotationsImpl() {

        return getNodeImpl().getAnnotations().stream()
                .map(annotation -> (AAnnotation<?>) SelectUtils.expression2JoinPoint(annotation, getWeaverEngine()))
                .collect(Collectors.toList())
                .toArray(size -> new AAnnotation[size]);
    }

    @Override
    public void removeAnnotationImpl(AAnnotation<?> annotation) {
        getNodeImpl().removeAnnotation((CtAnnotation<?>) annotation.getNodeImpl());
    }

    @Override
    public String getIdImpl() {
        var node = getNodeImpl();
        return node.getClass().getSimpleName() + "_" + node.hashCode();
    }

    @Override
    public AJoinpoint<?>[] getLeftImpl() {
        var parent = getParentImpl();

        if (parent == null) {
            SpecsLogs.info("$jp.left: no parent, could not fetch siblings");
            return new AJoinpoint[0];
        }

        var siblings = parent.getChildrenImpl();

        // Find self index
        int selfIndex = -1;
        for (int i = 0; i < siblings.length; i++) {
            // Using equality on purpose
            if (siblings[i].getNodeImpl() == getNodeImpl()) {
                selfIndex = i;
                break;
            }
        }

        if (selfIndex == -1) {
            SpecsLogs.info("$jp.left: could not find self in siblings");
            return new AJoinpoint[0];
        }

        return Arrays.copyOfRange(siblings, 0, selfIndex);
    }

    @Override
    public AJoinpoint<?>[] getRightImpl() {
        var parent = getParentImpl();

        if (parent == null) {
            SpecsLogs.info("$jp.right: no parent, could not fetch siblings");
            return new AJoinpoint[0];
        }

        var siblings = parent.getChildrenImpl();

        // Find self index
        int selfIndex = -1;
        for (int i = 0; i < siblings.length; i++) {
            // Using equality on purpose
            if (siblings[i].getNodeImpl() == getNodeImpl()) {
                selfIndex = i;
                break;
            }
        }

        if (selfIndex == -1) {
            SpecsLogs.info("$jp.right: could not find self in siblings");
            return new AJoinpoint[0];
        }

        return Arrays.copyOfRange(siblings, selfIndex + 1, siblings.length);
    }

    @Override
    public void removeModifierImpl(String modifier) {
        var modifiersSet = SpoonUtils.getModifiers(getNodeImpl());

        if (modifiersSet.isEmpty()) {
            SpecsLogs.debug(() -> "No modifiers to remove");
            return;
        }

        // Convert modifier to enum
        var modifierEnum = SpecsEnums.valueOfTry(ModifierKind.class, modifier.toUpperCase());

        if (modifierEnum.isEmpty()) {
            SpecsLogs.info("Could not obtain modifier from string '" + modifier + "'. Available modifiers: "
                    + Arrays.toString(ModifierKind.values()));
            return;
        }

        var newModifiers = new HashSet<>(modifiersSet);

        var hadModifier = newModifiers.remove(modifierEnum.get());
        if (hadModifier) {
            SpoonUtils.setModifiers(getNodeImpl(), newModifiers);
        } else {
            SpecsLogs.debug(() -> "Could not remove modifier '" + modifier + "', not present in node");
        }

    }

    @Override
    public void setModifiersImpl(String[] modifiers) {

        Set<ModifierKind> modifiersSet = new HashSet<>();

        for (String modifier : modifiers) {
            // Convert modifier to enum
            var modifierEnum = SpecsEnums.valueOfTry(ModifierKind.class, modifier.toUpperCase());

            if (modifierEnum.isEmpty()) {
                SpecsLogs.info("Could not obtain modifier from string '" + modifier + "'. Available modifiers: "
                        + Arrays.toString(ModifierKind.values()));
                return;
            }

            // Add modifier to modifiersSet
            modifiersSet.add(modifierEnum.get());
        }

        SpoonUtils.setModifiers(getNodeImpl(), modifiersSet);
    }

    @Override
    public boolean getIsInsideLoopHeaderImpl() {
        var node = getNodeImpl();

        Optional<CtLoop> ancestor = SpoonUtils.getAncestorTry(node, CtLoop.class);
        if (!ancestor.isPresent()) {
            return false;
        }
        CtLoop loop = ancestor.get();

        return SpoonUtils.insideHeader(loop, node);
    }
}
