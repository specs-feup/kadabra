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

public class JavaApiTest {

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
        return new JavaWeaverTester("kadabra/test/api/")
                .setSrcPackage("src/")
                .setResultPackage("results/")
                .setKeepWeavedFiles(false);

    }

    @Test
    public void testLogger() {
        newTester().test("LoggerTest.js", "LoggerTest.java.test");
    }

    @Test
    public void testTimer() {
        newTester().test("TimerTest.js", "TimerTest.java.test");
    }

    @Test
    public void testEnergy() {
        newTester().test("EnergyTest.js", "EnergyTest.java.test");
    }

    @Test
    public void testBinaryExpressionMutator() {
        newTester().test("BinaryExpressionMutatorTest.js", "BinaryExpressionMutator.java.test");
    }

    @Test
    public void testBinaryExpressionMutation() {
        newTester().test("BinaryExpressionMutationTest.js", "BinaryExpressionMutation.java.test");
    }

    @Test
    public void testIterativeMutator() {
        newTester().test("IterativeMutatorTest.js", "IterativeMutator.java.test");
    }

    @Test
    public void testMutations() {
        newTester().test("MutationsTest.js", "Mutations.java.test");
    }

    @Test
    public void testKadabraNodes() {
        newTester().test("KadabraNodesTest.js", "KadabraNodes.java.test");
    }

    @Test
    public void testKadabraAst() {
        newTester()
                // .set(LaraiKeys.VERBOSE, VerboseLevel.all).set(LaraiKeys.DEBUG_MODE)
                .test("KadabraAstTest.js", "KadabraAst.java.test");
    }

    @Test
    public void testEAPKInternalGetter() {
        newTester().test("EapkInternalGetterTest.js", "EapkInternalGetter.java.test");
    }

    @Test
    public void testEAPKExcessiveMethodCalls() {
        newTester().test("EapkExcessiveMethodCallsTest.js", "EapkExcessiveMethodCalls.java.test");
    }

    @Test
    public void testEAPKHashMapUsage() {
        newTester().test("EapkHashMapUsageTest.js", "EapkHashMapUsage.java.test");
    }

    @Test
    public void testEAPKMemberIgnoringMethod() {
        newTester()
                .test("EapkMemberIgnoringMethodTest.js", "EapkMemberIgnoringMethod.java.test");
    }
}
