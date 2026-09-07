import { KadabraWeaverTester } from "../vitest/KadabraLegacyTester.ts";
import path from "path";
import KadabraJavaTypes from "./kadabra/KadabraJavaTypes.ts";

/* oxlint-disable vitest/expect-expect */
describe("IssuesTest", () => {
    function newTester() {
        return new KadabraWeaverTester(
            path.resolve(
                "../JavaWeaver/test-resources/tests/kadabra/test/issues"
            )
        )
            .setResultPackage("results")
            .setSrcPackage("src");
    }

    it("Issue168", async () => {
        await newTester()
            .set(KadabraJavaTypes.JavaWeaverKeys.NO_CLASSPATH)
            .test("IssueInnerClass.js", "Muscle.java");
    });
});
