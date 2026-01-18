import { KadabraWeaverTester } from "../jest/KadabraLegacyTester.js";
import path from "path";

/* eslint-disable jest/expect-expect */
describe("ApiTest", () => {
    function newTester() {
        return new KadabraWeaverTester(
            path.resolve("../JavaWeaver/test-resources/tests/kadabra/test/api")
        )
            .setResultPackage("results")
            .setSrcPackage("src");
    }

    it("Logger", async () => {
        await newTester().test("LoggerTest.js", "LoggerTest.java");
    });

    it("Timer", async () => {
        await newTester().test("TimerTest.js", "TimerTest.java");
    });

    it("Energy", async () => {
        await newTester().test("EnergyTest.js", "EnergyTest.java");
    });

    it("BinaryExpressionMutator", async () => {
        await newTester().test(
            "BinaryExpressionMutatorTest.js",
            "BinaryExpressionMutator.java"
        );
    });

    it("BinaryExpressionMutation", async () => {
        await newTester().test(
            "BinaryExpressionMutationTest.js",
            "BinaryExpressionMutation.java"
        );
    });

    it("IterativeMutator", async () => {
        await newTester().test(
            "IterativeMutatorTest.js",
            "IterativeMutator.java"
        );
    });

    it("Mutations", async () => {
        await newTester().test("MutationsTest.js", "Mutations.java");
    });

    it("KadabraNodes", async () => {
        await newTester().test("KadabraNodesTest.js", "KadabraNodes.java");
    });

    it("KadabraAst", async () => {
        await newTester().test("KadabraAstTest.js", "KadabraAst.java");
    });

    it("EAPKInternalGetter", async () => {
        await newTester().test(
            "EapkInternalGetterTest.js",
            "EapkInternalGetter.java"
        );
    });

    it("EAPKExcessiveMethodCalls", async () => {
        await newTester().test(
            "EapkExcessiveMethodCallsTest.js",
            "EapkExcessiveMethodCalls.java"
        );
    });

    it("EAPKHashMapUsage", async () => {
        await newTester().test(
            "EapkHashMapUsageTest.js",
            "EapkHashMapUsage.java"
        );
    });

    it("EAPKMemberIgnoringMethod", async () => {
        await newTester().test(
            "EapkMemberIgnoringMethodTest.js",
            "EapkMemberIgnoringMethod.java"
        );
    });
});
