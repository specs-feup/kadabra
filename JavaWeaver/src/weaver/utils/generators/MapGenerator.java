/**
 * Copyright 2015 SPeCS.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

package weaver.utils.generators;

import org.specs.generators.java.types.JavaTypeFactory;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtCompilationUnit;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import weaver.kadabra.abstracts.joinpoints.AInterfaceType;
import weaver.kadabra.entities.Pair;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.utils.SpoonUtils;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.TypeUtils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapGenerator {

    private static final String GET = "get";

    private static final String CONTAINS = "contains";

    private static final String ADD = "put";

    private static final String[] DEFAULT_MODIFIERS = {"public", "static"};

    private final Factory factory;
    private final String keyType;
    private final Pair keyTypeParam;
    private final AInterfaceType _interface;
    // private final AMethod method;
    // private String interfaceQualifiedName;
    private final String mapName;
    // private final String defaultName;
    private final CtClass<?> funcMapClass;


    private MapGenerator(Factory factory, String keyType, AInterfaceType _interface,
                         String methodName, CtClass<?> funcMapClass) { // AMethod method
        // String upperName = method.getName().toUpperCase();
        String upperName = methodName.toUpperCase();
        this.factory = factory;
        this.keyType = keyType;
        keyTypeParam = new Pair(keyType, "key");
        // method = method;
        this._interface = _interface;
        // this.interfaceQualifiedName = _interface.getQualifiedName();
        mapName = upperName + "_MAP";
        // defaultName = "DEFAULT_" + upperName;
        this.funcMapClass = funcMapClass;
    }

    /**
     * Generate a new functional mapping class (without any parent nor compilation unit)
     *
     * @param factory
     * @param name
     * @param keyType
     * @param _interface
     * @return
     */
    public static CtClass<?> generate(Factory factory, String name, String keyType, AInterfaceType _interface,
                                      String methodName) {
        CtClass<?> newMapClass = ActionUtils.newClass(name, null, null, factory);
        generate(keyType, _interface, methodName, newMapClass);
        return newMapClass;
    }

    /**
     * Generate a functional mapping class and put it in a new compilation unit
     *
     * @param factory
     * @param name
     * @param keyType
     * @param _interface
     * @param outputDir
     * @return
     */
    public static CtCompilationUnit generate(Factory factory, String name, String keyType, AInterfaceType _interface,
                                             String methodName, File outputDir) {
        var cu = ActionUtils.compilationUnitWithClass(name, null, null, outputDir, factory);
        CtClass<?> funcMapClass = (CtClass<?>) cu.getMainType();
        generate(keyType, _interface, methodName, funcMapClass);
        return cu;
    }

    /**
     * Add functional mapping methods inside a given class
     *
     * @param factory
     * @param keyType
     * @param _interface
     * @param methodName
     * @param funcMapClass
     */
    public static void generate(String keyType, AInterfaceType _interface,
                                String methodName, CtClass<?> funcMapClass) {
        MapGenerator functionalMapperGenerator = new MapGenerator(funcMapClass.getFactory(), keyType,
                _interface, methodName, funcMapClass);
        functionalMapperGenerator.generate();
    }

    public void generate() {
        addFieldAndStaticBlock(funcMapClass);
        addGetOrDefaultMethod(funcMapClass);
        addGetMethod(funcMapClass);
        addContainsMethod(funcMapClass);
        addAddMethod(funcMapClass);
    }

    /**
     * Add the statics fields necessary: map and default method
     *
     * @param funcMapClass
     */
    private void addFieldAndStaticBlock(CtClass<?> funcMapClass) {

        CtTypeReference<Object> declTypeRef = getDeclaringType();
        /* FIELDS */
        CtTypeReference<Object> interfaceRef = TypeUtils.typeOf(_interface.getQualifiedNameImpl(), factory);

        /* Declare map with the declaringType and the interface */
        CtTypeReference<Object> mapRef = SpoonUtils.newCtTypeReference(Map.class, factory, declTypeRef, interfaceRef);
        CtField<Object> mapField = addField(mapName, funcMapClass, mapRef);
        initMap(mapField);

        /* Declare default method implementation */
        // CtField<Object> defaultField = addField(defaultName, funcMapClass, interfaceRef); // DEFAULT

        /* Initialize default field with the given $method */
        // initDefaultField(defaultField);

    }

    // public void initDefaultField(CtField<Object> defaultField) {
    // CtMethod<Object> node = ((JMethod) method).getNode();
    // @SuppressWarnings("unchecked")
    // CtTypeAccess<Object> newTypeAccess = (CtTypeAccess<Object>) newTypeAccess(
    // node.getDeclaringType().getReference());
    // CtExecutableReference<Object> reference = node.getReference();
    // CtExecutableReferenceExpression<Object, CtExpression<?>> execRefExpr = factory.Core()
    // .createExecutableReferenceExpression();
    // execRefExpr.setExecutable(reference);
    // execRefExpr.setTarget(newTypeAccess);
    // defaultField.setDefaultExpression(execRefExpr);
    // }

    public void initMap(CtField<Object> mapField) {
        /* Initialize with new ConcurrentHashMap<>() */
        CtTypeReference<?> ref = factory.Type().createReference("");
        CtTypeReference<Object> concHMRef = SpoonUtils.newCtTypeReference(ConcurrentHashMap.class,
                factory, ref);

        try {
            CtExecutableReference<Object> constrRef = factory.Constructor().createReference(concHMRef, concHMRef,
                    ConcurrentHashMap.class.getSimpleName());
            CtConstructorCall<Object> initMap = factory.Core().createConstructorCall();
            initMap.setType(concHMRef);
            initMap.setExecutable(constrRef);

            mapField.setDefaultExpression(initMap);
        } catch (Exception e) {
            throw new JavaWeaverException(
                    "When creating reference to constructor of ConcurrentHashMap", e);
        }
    }

    public <T> CtExecutableReference<T> getConstructorReference(Class<T> owner, Class<?>... parameters) {
        try {
            Constructor<T> constr = owner.getConstructor(parameters);
            CtExecutableReference<T> constrRef = factory.Constructor()
                    .createReference(constr);
            return constrRef;
        } catch (Exception e) {
            throw new JavaWeaverException("When creating reference to constructor of class " + owner.getName(), e);
        }

    }

    private CtTypeReference<Object> getDeclaringType() {
        String declaringType;
        if (JavaTypeFactory.isPrimitive(keyType)) {
            declaringType = JavaTypeFactory.getPrimitiveWrapper(keyType).getSimpleType();
        } else {
            declaringType = keyType;
        }
        CtTypeReference<Object> declTypeRef = TypeUtils.typeOf(declaringType, factory);
        return declTypeRef;
    }

    // private <T> void addDefaultExpression(CtField<Object> mapField, CtTypeAccess<Object> newTypeAccess) {
    // mapField.setDefaultExpression(newTypeAccess);
    // }

    // private <T> CtTypeAccess<T> newTypeAccess(CtTypeReference<T> defaultMethodClass) {
    // CtTypeAccess<T> typeAccess = factory.Core().createTypeAccess();
    // typeAccess.setType(defaultMethodClass);
    // return typeAccess;
    // }

    // private static CtCodeSnippetStatement addStatement(Factory factory, CtBlock<?> staticBody, String codeStr) {
    // CtCodeSnippetStatement snippet = SnippetFactory.snippetStatement(codeStr, factory);
    // staticBody.addStatement(snippet);
    // snippet.setParent(staticBody);
    // return snippet;
    // }

    // private static <T extends Object> CtField<T> addField(String mapName, CtClass<?> funcMapClass,
    private static CtField<Object> addField(String mapName, CtClass<?> funcMapClass,
                                            CtTypeReference<Object> type) {
        CtField<Object> field = ActionUtils.newFieldWithSnippet(funcMapClass, mapName, type, null, DEFAULT_MODIFIERS);
        field.addModifier(ModifierKind.FINAL);
        return field;
    }

    private void addGetOrDefaultMethod(CtClass<?> funcMapClass) {
        String defaultMethod = "defaultMethod";
        Pair defaultParam = new Pair(_interface.getQualifiedNameImpl(), defaultMethod);
        String[] paramLeft = {keyTypeParam.getLeft(), defaultParam.getLeft()};
        String[] paramRight = {keyTypeParam.getRight(), defaultParam.getRight()};
        String codeStr = "return " + mapName + ".getOrDefault(key, " + defaultMethod + ");";
        ActionUtils.newMethod(funcMapClass, GET, _interface.getQualifiedNameImpl(), paramLeft, paramRight, DEFAULT_MODIFIERS, codeStr);

        // addStatement(factory, getter.getBody(), codeStr);
    }

    private void addGetMethod(CtClass<?> funcMapClass) {
        String[] paramLeft = {keyTypeParam.getLeft()};
        String[] paramRight = {keyTypeParam.getRight()};
        String codeStr = "return " + mapName + ".get(key);";
        ActionUtils.newMethod(funcMapClass, GET, _interface.getQualifiedNameImpl(), paramLeft, paramRight, DEFAULT_MODIFIERS, codeStr);

        // addStatement(factory, getter.getBody(), codeStr);
    }

    private void addContainsMethod(CtClass<?> funcMapClass) {
        String[] paramLeft = {keyTypeParam.getLeft()};
        String[] paramRight = {keyTypeParam.getRight()};
        String codeStr = "return " + mapName + ".containsKey(key);";
        ActionUtils.newMethod(funcMapClass, CONTAINS, "boolean", paramLeft, paramRight, DEFAULT_MODIFIERS, codeStr);

    }

    private void addAddMethod(CtClass<?> funcMapClass) {
        String localVarName = _interface.getNameImpl();
        String[] paramLeft = {keyTypeParam.getLeft(), _interface.getQualifiedNameImpl()};
        String[] paramRight = {keyTypeParam.getRight(), localVarName};

        String codeStr = mapName + ".put(key," + localVarName + ");";
        ActionUtils.newMethod(funcMapClass, ADD, "void", paramLeft, paramRight, DEFAULT_MODIFIERS, codeStr);
    }

}
