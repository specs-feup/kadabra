/**
 * Copyright 2015 SPeCS Research Group.
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

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.lara.interpreter.profile.WeaverProfiler;

import pt.up.fe.specs.util.SpecsCheck;
import spoon.compiler.Environment;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtStatement;
import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.CompilationUnitFactory;
import spoon.reflect.factory.ConstructorFactory;
import spoon.reflect.factory.Factory;
import spoon.reflect.factory.MethodFactory;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.entities.Pair;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.kadabra.spoon.extensions.launcher.JWEnvironment;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetElement;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.SpoonUtils;
import weaver.utils.scanners.NodeConverter;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class ActionUtils {

    public enum Location {
        BEFORE,
        AFTER,
        AROUND,
        REPLACE;

        public static Location getLocation(String location) {
            return Location.valueOf(location.toUpperCase());
        }
    }

    public static AJavaWeaverJoinPoint insert(String position, CtElement newElement,
            final CtElement node, WeaverProfiler weavingProfiler) {

        Location posIntert = Location.valueOf(position.toUpperCase());
        if (posIntert.equals(Location.REPLACE) && newElement == null) {
            // Then it is just to remove the given element
            node.delete();
            return null;
        }

        // Can only insert before/after statements
        final CtStatement referenceStmt = SpoonUtils.getInsertableParent(node);

        // LOC for the profiler has to be calculated outside this method
        switch (Location.getLocation(position)) {
        case BEFORE:
            if (!(newElement instanceof CtStatement)) {
                // // If new element is a KadabraSnippet, convert to StatementSnippet
                // if (newElement instanceof CtKadabraSnippetElement) {
                // newElement = SnippetFactory.createSnippetStatement(
                // ((CtKadabraSnippetElement) newElement).getValue(), node.getFactory());
                // } else {
                KadabraLog.info("Can only insert statements before nodes, tried to insert a '"
                        + newElement.getClass() + "':" + newElement);
                return null;
                // }

            }
            referenceStmt.insertBefore((CtStatement) newElement);
            break;
        case AFTER:
            if (!(newElement instanceof CtStatement)) {
                // // If new element is a KadabraSnippet, convert to StatementSnippet
                // if (newElement instanceof CtKadabraSnippetElement) {
                // newElement = SnippetFactory.createSnippetStatement(
                // ((CtKadabraSnippetElement) newElement).getValue(), node.getFactory());
                // } else {
                KadabraLog.info("Can only insert statements after nodes, tried to insert a '"
                        + newElement.getClass() + "': " + newElement);
                return null;
                // }
            }
            referenceStmt.insertAfter((CtStatement) newElement);
            break;
        case AROUND:
        case REPLACE:
            // If new join point is a statement, use the reference statement
            if (newElement instanceof CtStatement) {
                referenceStmt.replace(newElement);
            }
            // Otherwise, use original node
            else {
                node.replace(newElement);
            }

            break;
        default:
            throw new RuntimeException("Code insertion must only be done: " + Arrays.toString(Location.values())
                    + "; used '" + position + "'");
        }

        var joinPoint = CtElement2JoinPoint.convert(newElement);

        String[] lines = joinPoint.getSrcCodeImpl().split("\r\n|\r|\n");
        reportLOCs(weavingProfiler, lines.length, true);

        return joinPoint;
    }

    /**
     * Inject code before/after/around a given statement
     * <p>
     * <b>NOTE:</b> this method searches for a parent statement (or the given node) that has a block as its parent
     *
     * @param position
     *            position of the insertion: before, after or around
     * @param snippetStr
     *            the code to inject
     * @param node
     *            the node used as reference for the insertion
     */
    // public static void insert(String position, String snippetStr, CtStatement statement) {
    //
    // final CtElement parent = statement.getParent();
    public static AJavaWeaverJoinPoint insert(String position, String snippetStr, CtElement node,
            WeaverProfiler weavingProfiler) {

        var snippet = snippetStr.trim().isEmpty() ? null
                : SnippetFactory.createSnippetStatement(snippetStr, node.getFactory());
        //
        // var snippet = snippetStr.trim().isEmpty() ? null
        // : SnippetFactory.createSnippetElement(node.getFactory(), snippetStr);

        if (snippet == null) {
            SpecsCheck.checkArgument(Location.valueOf(position.toUpperCase()).equals(Location.REPLACE),
                    () -> "Can only use empty string as insert argument when it is a 'replace'");
        }
        return insert(position, snippet, node, weavingProfiler);

        /*
        Location posIntert = Location.valueOf(position.toUpperCase());
        if (posIntert.equals(Location.REPLACE) && snippetStr.trim().isEmpty()) {
            // Then it is just to remove the given element
            node.delete();
            return null;
        }
        final CtCodeSnippetStatement snippet = SnippetFactory.createSnippetStatement(snippetStr, node.getFactory());
        final CtStatement parent = SpoonUtils.getInsertableParent(node);
        insert(position, snippet, parent);
        String[] lines = snippetStr.split("\r\n|\r|\n");
        reportLOCs(weavingProfiler, lines.length, true);
        
        return CtElement2JoinPoint.convert(snippet);
        // return SelectUtils.node2JoinPoint(snippet, JSnippet::newInstance);
        
        // snippet.setParent(parent); <-- get parent of parent for this, if not dealt by spoon
        // System.out.println("injected");
        // This also helps to avoid the warning:
        // WARN spoon.support.StandardEnvironment - warning: ignoring
        // inconsistent parent for CtCodeSnippetStatementImpl
        
         */
    }

    public static <T> AJavaWeaverJoinPoint replaceExpression(String position, String snippetStr, CtExpression<T> node,
            WeaverProfiler weaverProfiler) {

        var snippet = snippetStr.trim().isEmpty() ? null
                : SnippetFactory.createSnippetExpression(node.getFactory(), snippetStr);

        if (snippet == null) {
            SpecsCheck.checkArgument(Location.valueOf(position.toUpperCase()).equals(Location.REPLACE),
                    () -> "Can only use empty string as an expression insert argument when it is a 'replace'");
        }

        return replaceExpression(position, snippet, node, weaverProfiler);
        /*
        Location posIntert = Location.valueOf(position.toUpperCase());
        if (posIntert.equals(Location.REPLACE) && snippetStr.trim().isEmpty()) {
            // Then it is just to remove the given element
            node.delete();
            return null;
        }
        final CtCodeSnippetExpression<T> snippet = SnippetFactory.createSnippetExpression(node.getFactory(),
                snippetStr);
        node.replace(snippet);
        String[] lines = snippetStr.split("\r\n|\r|\n");
        reportLOCs(weaverProfiler, lines.length, true);
        
        return CtElement2JoinPoint.convert(snippet);
        */
    }

    public static <T> AJavaWeaverJoinPoint replaceExpression(String position, CtExpression<?> expression,
            CtExpression<T> target, WeaverProfiler weaverProfiler) {

        CtElement snippet = expression;
        CtElement node = target;

        // Then it is just to remove the given element
        Location posIntert = Location.valueOf(position.toUpperCase());
        if (posIntert.equals(Location.REPLACE) && snippet == null) {
            node.delete();
            return null;
        }

        // var snippet2 = SnippetFactory.createSnippetStatement(snippet.toString(), node.getFactory());
        // var snippet2 = SnippetFactory.createSnippetExpression(node.getFactory(), snippet.toString());
        // node.replace(snippet2);

        // Special case if expression is a block statement
        if (SpoonUtils.isStatementInBlock(node)) {
            var code = snippet.toString();
            snippet = SnippetFactory.createSnippetStatement(code, node.getFactory());
        }

        node.replace(snippet);
        // System.out.println("SNIPPET: " + snippet.getClass());

        // Removes parent from original node
        // Dropped - apparently Spoon nodes do not loose their parents
        // node.setParent(null);

        var joinPoint = CtElement2JoinPoint.convert(snippet);
        String[] lines = joinPoint.getSrcCodeImpl().split("\r\n|\r|\n");
        reportLOCs(weaverProfiler, lines.length, true);

        return joinPoint;
    }

    /**
     * Use this method to insert arbitrary code around a CtTypeMember: methods, fields, constructors, innerTypes, and
     * anonymous executables
     *
     * @param referenceNode
     * @param codeSnippet
     * @param location
     * @param weavingProfiler
     */
    public static AJavaWeaverJoinPoint insertMember(CtElement referenceNode, String codeSnippet, String location,
            WeaverProfiler weavingProfiler) {

        Factory factory = referenceNode.getFactory();
        // CtCodeSnippetStatement snippet = SnippetFactory.createSnippetStatement(codeSnippet, factory);
        CtKadabraSnippetElement snippet = SnippetFactory.createSnippetElement(factory, codeSnippet);

        return insertMember(referenceNode, snippet, location, weavingProfiler);
    }

    public static AJavaWeaverJoinPoint insertMember(CtElement referenceNode, CtElement snippet, String location,
            WeaverProfiler weavingProfiler) {

        location = location.toUpperCase();
        Factory factory = referenceNode.getFactory();
        JWEnvironment env = getKadabraEnvironment(factory);

        switch (Location.getLocation(location)) {
        case BEFORE:
            // AnnotationsTable.getStaticTable().addBefore(referenceNode, snippet);
            env.getTable().addBefore(referenceNode, snippet);
            break;
        case AFTER:
            env.getTable().addAfter(referenceNode, snippet);
            break;
        case AROUND:
        case REPLACE:

            // System.out.println("PARENT: " + referenceNode.getParent().getClass());
            // System.out.println("REPLACING WITH '" + snippet + "'");
            // // Insert before node
            // env.getTable().addBefore(referenceNode, snippet);
            //
            // // Set same parent
            // snippet.setParent(referenceNode.getParent());
            //
            // // Remove node from tree
            // referenceNode.replace(Collections.emptyList());

            env.getTable().addReplace(referenceNode, snippet);

            break;
        default:
            throw new RuntimeException("Code insertion must only be done: " + Arrays.toString(Location.values())
                    + "; used '" + location + "'");
        }

        var joinPoint = CtElement2JoinPoint.convert(snippet);
        String[] lines = joinPoint.getCodeImpl().split("\r\n|\r|\n");
        // String[] lines = codeSnippet.split("\r\n|\r|\n");
        reportLOCs(weavingProfiler, lines.length, true);

        return CtElement2JoinPoint.convert(snippet);
    }

    public static AJavaWeaverJoinPoint insertInTable(CtElement referenceNode, String codeSnippet, String location,
            WeaverProfiler weavingProfiler) {
        location = location.toUpperCase();
        Factory factory = referenceNode.getFactory();

        JWEnvironment env = getKadabraEnvironment(factory);
        CtCodeSnippetStatement snippet = SnippetFactory.createSnippetStatement(codeSnippet, factory);
        switch (Location.getLocation(location)) {
        case BEFORE:
            // AnnotationsTable.getStaticTable().addBefore(referenceNode, snippet);
            env.getTable().addBefore(referenceNode, snippet);
            break;
        case AFTER:
            env.getTable().addAfter(referenceNode, snippet);
            break;
        case AROUND:
        case REPLACE:
            env.getTable().addReplace(referenceNode, snippet);
            break;
        default:
            throw new RuntimeException("Code insertion must only be done: " + Arrays.toString(Location.values())
                    + "; used '" + location + "'");
        }
        String[] lines = codeSnippet.split("\r\n|\r|\n");
        reportLOCs(weavingProfiler, lines.length, true);

        return CtElement2JoinPoint.convert(snippet);
    }

    public static CtInterface<Object> compilationUnitWithInterface(String name, String[] _extends, File outputDir,
            Factory factory, WeaverProfiler weavingProfiler) {

        CompilationUnit cu = newCompilationUnit(name, outputDir, factory);

        CtInterface<Object> newInterface = newInterface(name, _extends, factory, weavingProfiler);

        cu.addDeclaredType(newInterface);
        return newInterface;
    }

    public static CtInterface<Object> newInterface(String name, String[] _extends, Factory factory,
            WeaverProfiler weavingProfiler) {
        int packageSeparator = name.lastIndexOf(".");
        String simpleName = packageSeparator < 0 ? name : name.substring(packageSeparator + 1);
        String _package = packageSeparator < 0 ? "" : name.substring(0, packageSeparator);
        final CtPackage parentPackage = factory.Package().getOrCreate(_package);

        CtInterface<Object> newInterface = factory.Core().createInterface();
        newInterface.setSimpleName(simpleName);
        parentPackage.getTypes().add(newInterface);
        newInterface.setParent(parentPackage);
        newInterface.addModifier(ModifierKind.PUBLIC);

        if (_extends != null) {
            for (final String implement : _extends) {

                final CtType<Object> ctType = factory.Type().get(implement);
                if (ctType == null) {
                    throw new JavaWeaverException("When adding extends to the new class '" + name + "'",
                            new ClassNotFoundException("interface '" + implement + "'is invalid/non existent."));
                }
                final CtInterface<Object> extInterface = (CtInterface<Object>) ctType;
                newInterface.addSuperInterface(extInterface.getReference());
            }
        }
        reportLOCs(weavingProfiler, 2, false); // interface header and brackets
        return newInterface;
    }

    public static CompilationUnit compilationUnitWithClass(String name, String extend, String[] _implements,
            File outputDir, Factory factory, WeaverProfiler weavingProfiler) {
        final CtClass<Object> newClass = newClass(name, extend, _implements, factory, weavingProfiler);
        CompilationUnit cu = newCompilationUnit(name, outputDir, factory);
        cu.addDeclaredType(newClass);
        return cu;
    }

    public static CtClass<Object> newClass(String name, String extend, String[] _implements, Factory factory,
            WeaverProfiler weavingProfiler) {
        CtTypeReference<Object> typeOf = TypeUtils.typeOf(name, factory);
        final CtClass<Object> newClass = factory.Class().create(typeOf.getQualifiedName());
        newClass.addModifier(ModifierKind.PUBLIC);
        if (extend != null && !extend.isEmpty()) {
            CtTypeReference<Object> extendsType = TypeUtils.typeOf(extend, factory);
            newClass.setSuperclass(extendsType);
        }
        if (_implements != null) {
            for (final String implement : _implements) {
                CtTypeReference<Object> implementRef = factory.Type().createReference(implement);
                newClass.addSuperInterface(implementRef);
            }
        }
        reportLOCs(weavingProfiler, 2, false); // class header and brackets
        return newClass;
    }

    private static CompilationUnit newCompilationUnit(String name, File outputDir, Factory factory) {
        CompilationUnitFactory compilationUnitF = factory.CompilationUnit();

        String property = System.getProperty("file.separator");
        String filePath = new File(outputDir, name.replace(".", property) + ".java").getAbsolutePath();
        // return compilationUnitF.create(filePath);
        return compilationUnitF.getOrCreate(filePath);
    }

    public static CtField<Object> newField(CtType<?> node, String name, String fieldType, String initialValue,
            String[] modifiers, WeaverProfiler weavingProfiler) {
        final Factory factory = node.getFactory();
        final CtTypeReference<Object> fieldTypeRef = TypeUtils.typeOf(fieldType, factory);
        return newFieldWithSnippet(node, name, fieldTypeRef, initialValue, modifiers, weavingProfiler);
    }

    public static <T> CtField<T> newFieldWithType(CtType<?> node, String baseName, CtTypeReference<T> fieldType,
            T initialValue, String[] modifiers, WeaverProfiler weavingProfiler) {
        CtExpression<T> init = null;
        if (initialValue != null) {
            init = SpoonUtils.createLiteral(initialValue, node.getFactory());
        }
        return newFieldWithType(node, baseName, fieldType, init, modifiers, weavingProfiler);
    }

    public static <T> CtField<T> newFieldWithType(CtType<?> node, String baseName, CtTypeReference<T> fieldType,
            CtExpression<T> initialValue,
            String[] modifiers, WeaverProfiler weavingProfiler) {
        final CtField<T> newField = createField(node, baseName, fieldType, modifiers, weavingProfiler);
        if (initialValue != null) {
            // CtExpression<T> init = node.getFactory().Code().createLiteral(initialValue);
            newField.setDefaultExpression(initialValue);
        }
        reportLOCs(weavingProfiler, 1, false);
        return newField;
    }

    public static <T> CtField<T> newFieldWithSnippet(CtType<?> node, String baseName, CtTypeReference<T> fieldType,
            String initialValue,
            String[] modifiers, WeaverProfiler weavingProfiler) {
        final CtField<T> newField = createField(node, baseName, fieldType, modifiers, weavingProfiler);
        if (initialValue != null) {
            final CtExpression<T> snippet = SnippetFactory.snippetExpression(initialValue.toString(),
                    node.getFactory());
            newField.setDefaultExpression(snippet);
            String[] lines = initialValue.split("\r\n|\r|\n");
            reportLOCs(weavingProfiler, lines.length - 1, false); // 1 is already for the field declaration
        }
        return newField;
    }

    private static <T> CtField<T> createField(CtType<?> node, String baseName, CtTypeReference<T> fieldType,
            String[] modifiers, WeaverProfiler weavingProfiler) {
        final Set<ModifierKind> modifiersSet = SpoonUtils.setOfModifiers(modifiers, ModifierKind.PRIVATE);

        String name = baseName;
        int i = 0;
        boolean anyMatch = node.getAllFields().stream().anyMatch(f -> f.getSimpleName().equals(baseName));
        while (anyMatch) {
            String newName = baseName + "_" + i++;
            name = newName;
            anyMatch = node.getAllFields().stream().anyMatch(f -> f.getSimpleName().equals(newName));
        }
        final CtField<T> newField = node.getFactory().Field().create(null, modifiersSet, fieldType, name);
        node.addFieldAtTop(newField);
        reportLOCs(weavingProfiler, 1, false);
        return newField;
    }

    private static void reportLOCs(WeaverProfiler weavingProfiler, int LOCs, boolean isInsert) {
        // if (weavingProfiler != null) {
        // TODO - see why some join points do not have the WeaverEngine setted
        weavingProfiler.reportLOCs(LOCs, isInsert);
        // }
    }

    public static CtMethod<Object> newMethod(CtType<?> node, String name, String returnType, Pair[] param,
            String[] modifiers, String code, WeaverProfiler weavingProfiler) {
        final Factory factory = node.getFactory();
        final MethodFactory methodF = factory.Method();
        final CtTypeReference<Object> returnTypeRef = TypeUtils.typeOf(returnType, factory);
        final Set<ModifierKind> modifiersSet = SpoonUtils.setOfModifiers(modifiers, ModifierKind.PUBLIC);

        final CtMethod<Object> newMethod = methodF.create(node, modifiersSet, returnTypeRef, name, null, null);
        // Current Best approach for inserting parameters in a method and
        // directly associate to parent method
        for (final Pair pair : param) {
            // final CtTypeReference<Object> typeRef = factory.Type().createReference(pair.getLeft());
            final CtTypeReference<Object> typeRef = TypeUtils.typeOf(pair.getLeft(), factory);

            methodF.createParameter(newMethod, typeRef, pair.getRight());
        }

        CtBlock<Object> createBlock = factory.Core().createBlock();
        CtCodeSnippetStatement statement = SnippetFactory.createSnippetStatement(code, factory);
        createBlock.addStatement(statement);

        newMethod.setBody(createBlock);
        String[] lines = code.split("\r\n|\r|\n");
        reportLOCs(weavingProfiler, lines.length, false); // does not count the 2 LOC for the method header and brackets
        return newMethod;
    }

    public static CtConstructor<?> newConstructor(CtClass<?> node, Pair[] param, String[] modifiers,
            WeaverProfiler weavingProfiler) {
        final Factory factory = node.getFactory();
        final Set<ModifierKind> modifiersSet = SpoonUtils.setOfModifiers(modifiers, ModifierKind.PUBLIC);
        ConstructorFactory constFac = factory.Constructor();
        CtConstructor<?> constr = constFac.create(node, modifiersSet, Collections.emptyList(), null);
        for (final Pair pair : param) {
            final CtTypeReference<Object> typeRef = factory.Type().createReference(pair.getLeft());
            constFac.createParameter(constr, typeRef, pair.getRight());
        }
        constr.setBody(factory.Core().createBlock());
        reportLOCs(weavingProfiler, 2, false); // 2 for the method header and the brackets
        return constr;
    }

    public static <N extends CtElement> N cloneElement(N node) {
        return node.getFactory().Core().clone(node);
    }

    public static <N extends CtElement, JP extends AJavaWeaverJoinPoint> JP cloneJP(N node,
            NodeConverter<N, JP> converter) {
        N newNode = node.getFactory().Core().clone(node);
        return converter.toJoinPoint(newNode);

    }

    public static JWEnvironment getKadabraEnvironment(Factory factory) {
        Environment environment = factory.getEnvironment();
        if (!(environment instanceof JWEnvironment)) {
            throw new RuntimeException(
                    "Kadabra pretty printer requires the use of KadabraEnvironment, otherwise the inserts around some elements, such as methods and fields, will not work.");
        }
        return (JWEnvironment) environment;
    }

}
