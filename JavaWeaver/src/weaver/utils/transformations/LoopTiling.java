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

package weaver.utils.transformations;

import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtOperatorAssignment;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.kadabra.joinpoints.JFor;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.SpoonUtils;
import weaver.utils.element.RankCalculator;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.AttributeUtils;
import weaver.utils.weaving.SnippetFactory;

public class LoopTiling {

    /**
     * Apply loop tiling using directly the size of the block
     * 
     * @param ctFor
     * @param block
     */
    public static void tile(CtFor ctFor, int block) {
        if (!isTileFriendly(ctFor)) {
            KadabraLog.warning("Cannot apply tile to loop "
                    + RankCalculator.calculateString(ctFor, CtLoop.class) + ". It is not tile friendly");
            return;
        }
        Factory factory = ctFor.getFactory();

        // Get the controlVariable
        CtLocalVariable<Integer> controlVar = AttributeUtils.getControlVarAsInteger(ctFor);
        // CtLocalVariableReference<?> controlVar = controlVar2.getReference();

        // Create a new tiling for
        CtFor tilingFor = wrapWithFor(ctFor);
        // set init, condition and step
        CtLocalVariable<Integer> tileVar = addTileInit(factory, controlVar, tilingFor);
        CtExpression<Boolean> condition = ctFor.getExpression();
        addTileCond(factory, condition, tileVar, tilingFor);
        addTileStep(factory, tileVar, tilingFor, block);

        // now change init and condition of original for
        setOriginalForInit(factory, controlVar, tileVar);
        setOriginalForCond(factory, condition, tileVar, block);
    }

    /**
     * Apply loop tiling using a field for the of the block. Allows runtime definition of the tile size
     * 
     * @param ctFor
     * @param block
     * @param aroundStatement
     */
    public static CtField<Integer> tile(JFor jFor, String tileName, String block, boolean unique,
            CtStatement aroundStatement) {
        CtFor ctFor = jFor.getNode();
        if (!isTileFriendly(ctFor)) {
            KadabraLog.warning("Cannot apply tile to loop "
                    + RankCalculator.calculateString(ctFor, CtLoop.class) + ". It is not tile friendly");
            return null;
        }
        Factory factory = ctFor.getFactory();

        CtField<Integer> tileField = createTileField(jFor, tileName, block, unique);
        // Get the controlVariable
        CtLocalVariable<Integer> controlVar = AttributeUtils.getControlVarAsInteger(ctFor);
        // CtLocalVariableReference<?> controlVar = controlVar2.getReference();

        // Create a new tiling for
        CtFor tilingFor = wrapWithFor(aroundStatement);
        // set init, condition and step
        CtLocalVariable<Integer> tileVar = addTileInit(factory, controlVar, tilingFor);
        CtExpression<Boolean> condition = ctFor.getExpression();
        addTileCond(factory, condition, tileVar, tilingFor);
        addTileStep(factory, tileVar, tilingFor, tileField);

        // now change init and condition of original for
        setOriginalForInit(factory, controlVar, tileVar);
        setOriginalForCond(factory, condition, tileVar, tileField);

        return tileField;
    }

    private static CtField<Integer> createTileField(JFor jFor, String tileName, String block, boolean unique) {

        return SpoonUtils.getAncestorTry(jFor.getNode(), CtType.class)
                .map(ancestor -> buildField(jFor, tileName, block, unique, ancestor))
                .orElseThrow(LoopTiling::noTypeFound);

    }

    private static JavaWeaverException noTypeFound() {
        return new JavaWeaverException("When creating field for tile",
                new RuntimeException("No class/enum/interface owner was found for the given for"));
    }

    /**
     * Create a new field inside the given ancestor
     * 
     * @param ctFor
     * @param tileName
     * @param block
     * @param ancestor
     * @return
     */

    private static CtField<Integer> buildField(JFor jFor, String tileName, String block, boolean unique,
            CtType<?> ancestor) {
        String[] mods = { "public", "static" };
        CtFor ctFor = jFor.getNode();
        CtCodeSnippetExpression<Integer> initExpr = SnippetFactory
                .createSnippetExpression(ctFor.getFactory(), block);
        CtTypeReference<Integer> intType = ctFor.getFactory().Type().INTEGER_PRIMITIVE;
        if (unique) {

            CtField<Integer> tileField = ActionUtils.newFieldWithType(ancestor, tileName, intType, initExpr, mods);
            return tileField;
        }

        CtField<?> field = ancestor.getField(tileName);

        if (field != null) {
            if (field.getType().isSubtypeOf(intType)) {
                @SuppressWarnings("unchecked") // Because getField from CtType does not return with specific type
                CtField<Integer> iField = (CtField<Integer>) field;
                return iField;
            }
        }
        CtField<Integer> tileField = ActionUtils.newFieldWithType(ancestor, tileName, intType, initExpr, mods);
        return tileField;

    }

    private static void setOriginalForInit(Factory factory, CtLocalVariable<Integer> controlVar,
            CtLocalVariable<Integer> tileVar) {
        CtVariableAccess<Integer> init = factory.Code().createVariableRead(tileVar.getReference(), false);
        controlVar.setDefaultExpression(init);
    }

    /**
     * Create tile step with a fixed size block
     * 
     * @param factory
     * @param tileVar
     * @param tilingFor
     * @param block
     */
    private static void addTileStep(Factory factory, CtLocalVariable<Integer> tileVar, CtFor tilingFor, int block) {
        // CtVariableAccess<Integer> varRead = factory.Code().createVariableRead(tileVar.getReference(), false);

        CtVariableAccess<Integer> varRead = factory.Core().createVariableWrite();

        varRead.setVariable(tileVar.getReference());
        CtLiteral<Integer> litBlock = factory.Code().createLiteral(block);

        CtOperatorAssignment<Integer, Integer> inc = factory.Core().createOperatorAssignment();
        inc.setKind(BinaryOperatorKind.PLUS);
        inc.setAssigned(varRead);
        inc.setAssignment(litBlock);
        tilingFor.addForUpdate(inc);
    }

    /**
     * Create tile step using a field for the block size
     * 
     * @param factory
     * @param tileVar
     * @param tilingFor
     * @param tileField
     */
    private static void addTileStep(Factory factory, CtLocalVariable<Integer> tileVar, CtFor tilingFor,
            CtField<Integer> tileField) {

        // CtVariableAccess<Integer> varRead = factory.Code().createVariableRead(tileVar.getReference(), false);

        CtVariableAccess<Integer> varRead = factory.Core().createVariableWrite();

        varRead.setVariable(tileVar.getReference());
        // CtLiteral<Integer> litBlock = factory.Code().createLiteral(tileField);
        CtVariableAccess<Integer> fieldRead = factory.Code().createVariableRead(tileField.getReference(),
                tileField.hasModifier(ModifierKind.STATIC));

        CtOperatorAssignment<Integer, Integer> inc = factory.Core().createOperatorAssignment();
        inc.setKind(BinaryOperatorKind.PLUS);
        inc.setAssigned(varRead);
        inc.setAssignment(fieldRead);
        tilingFor.addForUpdate(inc);
    }

    private static void addTileCond(Factory factory, CtExpression<Boolean> condition,
            CtLocalVariable<Integer> tileVar, CtFor tilingFor) {

        CtBinaryOperator<Boolean> op = (CtBinaryOperator<Boolean>) condition;
        // CtBinaryOperator<Boolean> newOp = Cloner.clone(op);
        // CtExpression<?> rhs = Cloner.clone(op.getRightHandOperand());
        CtExpression<?> rhs = factory.Core().clone(op.getRightHandOperand());

        CtExpression<Integer> lhs = factory.Code().createVariableRead(tileVar.getReference(), false);

        CtBinaryOperator<Boolean> tileCondition = factory.Code().createBinaryOperator(lhs, rhs, BinaryOperatorKind.LT);
        tilingFor.setExpression(tileCondition);
    }

    /**
     * Redefine the original condition using a fixed size for the block
     * 
     * @param factory
     * @param condition
     * @param tileVar
     * @param tileField
     */
    private static void setOriginalForCond(Factory factory, CtExpression<Boolean> condition,
            CtLocalVariable<Integer> tileVar, int block) {
        CtLiteral<Integer> blockLit = factory.Code().createLiteral(block);

        replaceCondition(factory, condition, tileVar, blockLit);

    }

    /**
     * Redefine the original condition using tileField as condition
     * 
     * @param factory
     * @param condition
     * @param tileVar
     * @param tileField
     */
    private static void setOriginalForCond(Factory factory, CtExpression<Boolean> condition,
            CtLocalVariable<Integer> tileVar, CtField<Integer> tileField) {

        CtVariableAccess<Integer> tileFieldref = factory.Code().createVariableRead(tileField.getReference(),
                tileField.hasModifier(ModifierKind.STATIC));
        // CtLiteral<Integer> blockLit = factory.Code().createLiteral(tileField);

        replaceCondition(factory, condition, tileVar, tileFieldref);
    }

    private static void replaceCondition(Factory factory, CtExpression<Boolean> condition,
            CtLocalVariable<Integer> tileVar, CtExpression<Integer> blockExpr) {
        CtVariableAccess<Integer> tileref = factory.Code().createVariableRead(tileVar.getReference(), false);
        CtBinaryOperator<Integer> tileSum = factory.Code().createBinaryOperator(tileref, blockExpr,
                BinaryOperatorKind.PLUS);

        CtBinaryOperator<Boolean> op = (CtBinaryOperator<Boolean>) condition;
        CtExpression<?> rhs = op.getRightHandOperand();
        CtTypeReference<Math> math = factory.Type().createReference(Math.class);
        CtTypeReference<Integer> intType = factory.Type().INTEGER_PRIMITIVE;
        CtExecutableReference<Integer> minMethod = factory.Executable().createReference(math, intType, "min", intType,
                intType);

        CtTypeAccess<Math> target = factory.Code().createTypeAccess(math);
        CtInvocation<Integer> minInvocation = factory.Code().createInvocation(target, minMethod, rhs, tileSum);
        op.setRightHandOperand(minInvocation);
    }

    private static CtLocalVariable<Integer> addTileInit(Factory factory, CtLocalVariable<Integer> var, CtFor tileFor) {

        CtExpression<Integer> init = var.getDefaultExpression();
        String blockIt = var.getSimpleName() + "Block";
        CtTypeReference<Integer> integer = factory.Type().INTEGER_PRIMITIVE;
        CtLocalVariable<Integer> newCVar = factory.Code().createLocalVariable(integer,
                blockIt, init);
        tileFor.addForInit(newCVar);
        return newCVar;
    }

    private static CtFor wrapWithFor(CtStatement aroundStatement) {
        Factory factory = aroundStatement.getFactory();
        CtFor tilingFor = factory.Core().createFor();
        CtBlock<Object> tileBlock = factory.Core().createBlock();
        tilingFor.setBody(tileBlock);

        // Replace for with tiling for and add the node as child statement
        aroundStatement.replace(tilingFor);
        tileBlock.addStatement(aroundStatement);
        return tilingFor;
    }

    private static boolean isTileFriendly(CtFor ctfor) {
        if (!hasUniqueInit(ctfor)) {
            KadabraLog.warning("Loop init should only have 1 statement");
            return false;
        }
        if (AttributeUtils.getControlVarReference(ctfor) == null) {
            return false;
        }
        // Need more verifications!
        return true;
    }

    private static boolean hasUniqueInit(CtFor ctfor) {
        return ctfor.getForInit().size() == 1;
    }
}
