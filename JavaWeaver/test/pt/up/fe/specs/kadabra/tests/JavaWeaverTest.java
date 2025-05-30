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

package pt.up.fe.specs.kadabra.tests;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import pt.up.fe.specs.kadabra.JavaWeaverTester;
import pt.up.fe.specs.util.SpecsSystem;
import weaver.options.JavaWeaverKeys;

public class JavaWeaverTest {

    @BeforeClass
    public static void setupOnce() {
        SpecsSystem.programStandardInit();
        JavaWeaverTester.clean();
    }

    @After
    public void tearDown() {
        JavaWeaverTester.clean();
    }

    private static JavaWeaverTester newTester() {
        return new JavaWeaverTester("kadabra/test/weaver/")
                .setSrcPackage("src/")
                .setResultPackage("results/")
                .setKeepWeavedFiles(false);

    }

    @Test
    public void testMutateOperator() {
        newTester().test("MutateOperator.mjs", "MutateOperator.java.test");
    }

    @Test
    public void testGenericJoinPoint() {
        newTester().test("GenericJoinPoint.mjs", "GenericJoinPoint.java.test");
    }

    @Test
    public void testBinaryOperator() {
        newTester().test("BinaryOperator.mjs", "BinaryOperator.java.test");
    }

    @Test
    public void testLiteral() {
        newTester().test("Literal.mjs", "Literal.java.test");
    }

    @Test
    public void testGlobalJp() {
        newTester().test("GlobalJp.mjs", "GlobalJp.java.test");
    }

    @Test
    public void testAst() {
        newTester().test("Ast.mjs", "Ast.java.test");
    }

    @Test
    public void testAst2() {
        newTester().test("Ast2.mjs", "Ast2.java.test");
    }

    @Test
    public void testType() {
        newTester().test("Type.mjs", "Type.java.test");
    }

    @Test
    public void testSelects() {
        newTester().test("Selects.mjs", "Selects.java.test");
    }

    @Test
    public void testAndroid() {
        newTester().test("Android.mjs", "src/AndroidTest.java.test", "src/main/AndroidManifest.xml");
    }

    @Test
    public void testAndroid2() {
        newTester().test("Android2.mjs", "src/AndroidTest2.java.test", "src/main/AndroidManifest.xml");
    }

    // @Test
    public void testCompilationError() {

        newTester()
                .set(JavaWeaverKeys.NO_CLASSPATH)
                .test("CompilationError.mjs", "CompilationError.java.test");
    }

    @Test
    public void testObjectAccess() {
        newTester().set(JavaWeaverKeys.NO_CLASSPATH).set(JavaWeaverKeys.FULLY_QUALIFIED_NAMES).test("ObjectAccess.mjs",
                "ObjectAccess.java.test");
    }

    @Test
    public void testInheritanceIPCMutator() {
        newTester()
                // .set(JavaWeaverKeys.NO_CLASSPATH)
                // .set(JavaWeaverKeys.FULLY_QUALIFIED_NAMES)
                .test("InheritanceIPCMutator.mjs", "InheritanceIPCTest.java.test");
    }

    @Test
    public void testConstructorCallMutator() {
        newTester().set(JavaWeaverKeys.FULLY_QUALIFIED_NAMES).test("ConstructorCallMutator.mjs",
                "ConstructorCallTest.java.test");
    }

    @Test
    public void testLambdas() {
        newTester().set(JavaWeaverKeys.NO_CLASSPATH).test("Lambdas.mjs", "Lambdas.java.test");
    }

    @Test
    public void testConstructor() {
        newTester().test("Constructor.mjs", "Constructor.java.test");
    }

    @Test
    public void testFor() {
        newTester().test("For.mjs", "For.java.test");
    }

    @Test
    public void testTypeReference() {
        newTester().test("TypeReference.mjs", "TypeReference.java.test");
    }
}
