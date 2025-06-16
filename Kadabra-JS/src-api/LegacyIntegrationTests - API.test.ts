import { KadabraWeaverTester } from "./LegacyIntegrationTestsHelpers.test.js";
import path from "path";
import "@specs-feup/kadabra/api/Joinpoints.js";

/* eslint-disable jest/expect-expect */
describe("ApiTest", () => {
    function newTester() {
        return new KadabraWeaverTester(
            path.resolve("../JavaWeaver/resources/kadabra/test/api")
        )
            .setResultPackage("results")
            .setSrcPackage("src");
    }

    it("Logger", async () => {
        await newTester().test("LoggerTest.js", "LoggerTest.java.test");
    });

    it("Timer", async () => {
        await newTester().test("TimerTest.js", "TimerTest.java.test");
    });

    it("Energy", async () => {
        await newTester().test("EnergyTest.js", "EnergyTest.java.test");
    });

    it("BinaryExpressionMutator", async () => {
        await newTester().test(
            "BinaryExpressionMutatorTest.js",
            "BinaryExpressionMutator.java.test"
        );
    });

    it("BinaryExpressionMutation", async () => {
        await newTester().test(
            "BinaryExpressionMutationTest.js",
            "BinaryExpressionMutation.java.test"
        );
    });

    it("IterativeMutator", async () => {
        await newTester().test(
            "IterativeMutatorTest.js",
            "IterativeMutator.java.test"
        );
    });

    it("Mutations", async () => {
        await newTester().test("MutationsTest.js", "Mutations.java.test");
    });

    it("KadabraNodes", async () => {
        await newTester().test("KadabraNodesTest.js", "KadabraNodes.java.test");
    });

    it("KadabraAst", async () => {
        await newTester().test("KadabraAstTest.js", "KadabraAst.java.test");
    });

    it("EAPKInternalGetter", async () => {
        await newTester().test(
            "EapkInternalGetterTest.js",
            "EapkInternalGetter.java.test"
        );
    });

    it("EAPKExcessiveMethodCalls", async () => {
        await newTester().test(
            "EapkExcessiveMethodCallsTest.js",
            "EapkExcessiveMethodCalls.java.test"
        );
    });

    it("EAPKHashMapUsage", async () => {
        await newTester().test(
            "EapkHashMapUsageTest.js",
            "EapkHashMapUsage.java.test"
        );
    });

    it("EAPKMemberIgnoringMethod", async () => {
        await newTester().test(
            "EapkMemberIgnoringMethodTest.js",
            "EapkMemberIgnoringMethod.java.test"
        );
    });
});
