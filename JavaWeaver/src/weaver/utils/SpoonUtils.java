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
 * specific language governing permissions and limitations under the License. under the License.
 */

package weaver.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.lara.interpreter.profile.WeaverProfiler;

import spoon.compiler.Environment;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCase;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtModifiable;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.DefaultJavaPrettyPrinter;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.kadabra.spoon.extensions.nodes.CtCommentWrapper;
import weaver.kadabra.spoon.extensions.printer.KadabraPrettyPrinter;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.visitors.VisitLoopHeaders;
import weaver.utils.weaving.ActionUtils;

public class SpoonUtils {

    public static DefaultJavaPrettyPrinter createPrettyPrinter(Environment env) {
        // return new JavaPoetPrettyPrinter(new JWJavaPrettyPrinter(env));
        return new KadabraPrettyPrinter(env);
    }

    public static KadabraPrettyPrinter createSourcePrinter(Environment env) {
        // return new SourceCodePrinter(env);
        return new KadabraPrettyPrinter(env);
    }

    public static void replaceCommentWithWrapper(CtComment toWrap) {
        new CtCommentWrapper(toWrap);
    }

    /**
     * Converts, if necessary, a single statement into a block
     *
     * @param block
     * @return
     */
    public static void sanitizeBody(CtLoop loop) {
        final CtStatement block = loop.getBody();
        if (block == null || block instanceof CtBlock) {
            return;
        }
        final CtBlock<?> newBlock = block.getFactory().Core().createBlock();

        loop.setBody(newBlock);
        newBlock.setParent(loop);
        newBlock.getStatements().add(block);
        block.setParent(newBlock);
    }

    // CtBlock<?> block = target.getFactory().Core().createBlock();
    // block.getStatements().add(stat);
    // ((CtLoop) targetParent).setBody(block);

    public static CtTypeReference<Object> newCtTypeReference(Class<?> reference, Factory factory,
            List<CtTypeReference<?>> generics) {
        return newCtTypeReference(reference.getCanonicalName(), factory, generics);
    }

    /**
     * Create a new type reference based on the qualified name of the type
     *
     * @param qualifiedName
     * @param factory
     * @param generics
     * @return
     */
    public static CtTypeReference<Object> newCtTypeReference(String qualifiedName, Factory factory,
            List<CtTypeReference<?>> generics) {
        CtTypeReference<Object> newRef = factory.Type().createReference(qualifiedName);
        if (generics != null) {
            newRef.setActualTypeArguments(generics);
        }
        return newRef;
    }

    /**
     * Create a new type reference that may contain an array of specified types for the generics
     *
     * @param reference
     * @param factory
     * @param generics
     * @return
     */
    public static CtTypeReference<Object> newCtTypeReference(Class<?> reference, Factory factory,
            CtTypeReference<?>... generics) {
        return newCtTypeReference(reference, factory, Arrays.asList(generics));
    }

    /**
     * Get an ancestor of the given type, or a default value if no ancestor of that type exists
     *
     * @param node
     * @param ancestorClass
     * @param orElse
     * @return
     */
    // public static CtElement getAncestorOrElse(CtElement node, Class<CtElement> ancestorClass,
    // CtElement orElse) {
    // Optional<CtElement> parent = getAncestorTry(node, ancestorClass)
    // return parent.isPresent() ? ancestorClass.cast(parent) : orElse;
    // }

    /**
     * Get an ancestor of the given type
     *
     * @param node
     * @param ancestorClass
     * @return
     */
    public static <T extends CtElement> T getAncestor(CtElement node, Class<T> ancestorType) {
        return getAncestorTry(node, ancestorType).orElse(null);
        // .orElseThrow(() -> new RuntimeException("Could not find ancestor of type '" + ancestorType + "'"));
    }

    /**
     * Try to get an ancestor of the given type
     *
     * @param node
     * @param ancestorClass
     * @return
     */
    public static <T extends CtElement> Optional<T> getAncestorTry(CtElement node, Class<T> ancestorType) {
        // If node is null, return empty
        if (node == null) {
            return Optional.empty();
        }
        // If no parent, return empty
        CtElement parent = node.getParent();
        if (parent == null) {
            return Optional.empty();
        }

        if (ancestorType.isInstance(parent)) {
            return Optional.of(ancestorType.cast(parent));
        }

        return getAncestorTry(parent, ancestorType);
    }

    /*
    public static <T extends CtElement> Optional<T> getAncestor(CtElement node, Class<T> ancestorClass) {
        CtElement parent = node.getParent();
        while (parent != null && !ancestorClass.isInstance(parent)) {
    
            parent = parent.getParent();
        }
    
        return Optional.ofNullable(ancestorClass.cast(parent));
    }
    */
    /**
     * Get an ancestor of the given type, or node itself if it is of that type
     *
     * @param node
     * @param ancestorClass
     * @return
     */

    public static <T extends CtElement> Optional<T> getAncestorIncludeSelf(CtElement node, Class<T> ancestorClass) {
        if (ancestorClass.isInstance(node)) {
            return Optional.of(ancestorClass.cast(node));
        }
        return getAncestorTry(node, ancestorClass);
    }

    /**
     * Get an ancestor of the given type
     *
     * @param node
     * @param ancestorClass
     * @return
     */
    /*
    public static <T extends CtElement> T getAncestor2(CtElement node, Class<T> ancestorClass) {
        CtElement parent = node.getParent();
        while (parent != null && !ancestorClass.isInstance(parent)) {
    
            parent = parent.getParent();
        }
    
        return ancestorClass.cast(parent);
    }*/

    /**
     * Get the closest executable ancestor
     *
     * @param node
     * @return
     */
    public static CtElement getExecutableAncestor(CtElement node) {
        CtElement parent = node.getParent();

        while (parent != null && !isExecutable(parent)) {

            parent = parent.getParent();
        }
        return parent;
    }

    /**
     * It the given element a executable method? it can be anonymous, method, constructor,...
     *
     * @param node
     * @return
     */
    public static boolean isExecutable(CtElement node) {
        return node instanceof CtAnonymousExecutable || node instanceof CtExecutable;
    }

    public static <T> CtLocalVariable<T> extract(CtExpression<T> expression, String varName, CtStatement target,
            String position, WeaverProfiler weaverProfiler) {
        Factory factory = expression.getFactory();

        CtTypeReference<T> type = expression.getType();
        // Create the new local variable
        CtLocalVariable<T> newVar = factory.Code().createLocalVariable(type,
                varName, null);
        // Replace the expression with a reference to the local variable
        CtLocalVariableReference<T> reference = newVar.getReference();
        CtVariableAccess<T> varRead = factory.Code().createVariableRead(reference, false);
        expression.replace(varRead);

        // add the expression as the variable initialization
        newVar.setDefaultExpression(expression);

        // and insert the variable declaration before/after the given target statement
        ActionUtils.insert(position, newVar, target, weaverProfiler);
        return newVar;
    }

    public static Set<ModifierKind> setOfModifiers(String[] modifiers, ModifierKind... defaultModifiers) {
        final Set<ModifierKind> modifiersSet = new HashSet<>();
        if (modifiers == null || modifiers.length == 0) {

            for (final ModifierKind modifierKind : defaultModifiers) {
                modifiersSet.add(modifierKind);
            }
        } else {

            for (final String modifier : modifiers) {

                try {
                    final ModifierKind modKind = ModifierKind.valueOf(modifier.toUpperCase());
                    modifiersSet.add(modKind);
                } catch (final EnumConstantNotPresentException exc) {
                    KadabraLog.warning("The given modifier does not exist: " + modifier
                            + "The modifier will be ignored.");
                    KadabraLog.warning("Available modifiers: " + Arrays.asList(ModifierKind.values()));
                }
            }
        }
        return modifiersSet;
    }

    @Deprecated
    public static CtStatement getInsertableParentOld(CtStatement node) {
        CtElement parent = node.getParent();
        if (parent == null) {
            throw new JavaWeaverException("When inserting code",
                    new NullPointerException("Could not find an insertable statement"));
        }

        if (parent instanceof CtBlock<?>) {
            return node;
        }
        return SpoonUtils.getInsertableParentOld(node.getParent());
    }

    /**
     *
     * @param node
     * @return
     */
    @Deprecated
    public static CtStatement getInsertableParentOld(CtElement node) {

        do {
            if (node instanceof CtStatement) {
                return getInsertableParentOld((CtStatement) node);
            }
            node = node.getParent();
        } while (node != null && !(node instanceof CtStatement));

        // if (node == null) {
        throw new JavaWeaverException("When inserting code",
                new NullPointerException("Could not find an insertable statement for the target"));
        // }

        // return getInsertableParent((CtStatement) node);

    }

    public static CtStatement getInsertableParent(CtElement node) {
        // if (node instanceof CtStatement) {
        // return (CtStatement) node;
        // }
        CtElement parent = node.getParent();
        // if (parent == null) {
        // throw new JavaWeaverException("When inserting code",
        // new NullPointerException("Node given does not have a parent: " + node.toString()));
        // }
        while (parent != null) {
            if (parent instanceof CtBlock<?>) {
                if (!(node instanceof CtStatement)) {

                    throw new JavaWeaverException("When inserting code",
                            new NullPointerException(
                                    "Inconsistent structure in spoon tree: child of CtBlock has to be of type CtStatement, but was "
                                            + node.getClass().getSimpleName()));
                }
                return (CtStatement) node;
            }
            if (parent instanceof CtCase) {
                return (CtStatement) node;
            }

            node = parent;
            parent = node.getParent();
        }

        throw new JavaWeaverException("When inserting code",
                new NullPointerException("Could not find an insertable statement for the target"));
    }

    private static int negCounter = 0;

    /**
     * Used in "insert after" for a method, field, constructor,...
     * 
     * @return
     */
    public static int getNegativeCounter() {
        return --negCounter;
    }

    public static void resetCounter() {
        negCounter = 0;
    }

    public static <T> CtExpression<T> createLiteral(T value, Factory factory) {
        CtExpression<T> expr = factory.Code().createLiteral(value);
        return expr;
    }

    public static boolean insideHeader(CtLoop loop, CtElement node) {
        VisitLoopHeaders visitor = new VisitLoopHeaders(node);
        loop.accept(visitor);
        return visitor.isInLoopHeader();
    }

    public static boolean isAncestor(CtElement target, CtElement ancestor, CtElement cut) {
        CtElement parent = target.getParent();
        CtPackage rootPackage = ancestor.getFactory().Package().getRootPackage();
        while (parent != null && !parent.equals(rootPackage) && !parent.equals(cut)) {
            if (parent.equals(ancestor)) {
                return true;
            }
            parent = parent.getParent();
        }

        return false;
    }

    public static boolean isAncestor(CtElement target, CtElement ancestor) {
        CtElement parent = target.getParent();
        CtPackage rootPackage = ancestor.getFactory().Package().getRootPackage();
        while (parent != null && !parent.equals(rootPackage)) {
            if (parent.equals(ancestor)) {
                return true;
            }
            parent = parent.getParent();
        }

        return false;
    }

    public static boolean isStatementInBlock(CtElement target) {
        if (!(target instanceof CtStatement)) {
            return false;
        }

        // If parent not initialized, then is not in Block
        // if (!target.isParentInitialized()) {
        // return false;
        // }

        return target.getParent() instanceof CtBlock;
    }

    public static List<? extends CtElement> getChildren(CtElement node) {

        // Special case: if node is a list of statements, return the statements
        // if (node instanceof CtStatementList) {
        // System.out.println("GET STATEMENTS: " + ((CtStatementList) node).getStatements());
        // System.out.println("GET ELEMENTS: " + node.getElements(element -> element.getParent() == node));
        // return ((CtStatementList) node).getStatements();
        // }

        // System.out.println("NODE: " + node);
        // System.out.println("IS PARENT: " + node.hasParent(node));
        // System.out.println("IS PARENT INITIALIZED: " + node.isParentInitialized());

        // var originalNode = AJavaWeaverJoinPoint.CLONED_NODES.get(node);
        // var nodeToCompare = originalNode == null ? node : originalNode;

        // return node.getElements(element -> element.getParent() == nodeToCompare);
        // return node.getElements(element -> element.getParent() == node && !element.isImplicit());
        // return node.getElements(element -> element.hasParent(node));

        return node.getDirectChildren().stream()
                // Remove implicit nodes
                // .filter(child -> !child.isImplicit())
                .filter(SpoonUtils::isValidChild)
                .collect(Collectors.toList());
    }

    public static CtElement[] getChildrenArray(CtElement node) {
        return node.getDirectChildren().stream()
                // Remove implicit nodes
                // .filter(child -> !child.isImplicit())
                .filter(SpoonUtils::isValidChild)
                .toArray(size -> new CtElement[size]);
    }

    private static boolean isValidChild(CtElement node) {

        // Always return blocks of statements, even if implicit
        if (node instanceof CtBlock) {
            return true;
        }

        // If node is implicit, remove by default
        return !node.isImplicit();
    }

    /**
     * If node implements CtModifiable, returns the modifiers. Otherwise, returns empty set.
     * 
     * @return
     */
    // @SuppressWarnings("unchecked")
    public static Set<ModifierKind> getModifiers(CtElement node) {
    
        if (node instanceof CtModifiable) {
            return ((CtModifiable) node).getModifiers();
        }
    
        if (node instanceof CtVariableAccess varAccess) {
            var decl = varAccess.getVariable().getDeclaration();
            return getModifiers(decl);
        }
    
        return Collections.emptySet();
    
    }
}
