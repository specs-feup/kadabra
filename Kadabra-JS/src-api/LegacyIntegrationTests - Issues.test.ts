import { KadabraWeaverTester } from "../Jest/KadabraLegacyTester.js";
import path from "path";
import "@specs-feup/kadabra/api/Joinpoints.js";
import KadabraJavaTypes from "./kadabra/KadabraJavaTypes.js";

/* eslint-disable jest/expect-expect */
describe("IssuesTest", () => {
    function newTester() {
        return new KadabraWeaverTester(
            path.resolve("../JavaWeaver/resources/kadabra/test/issues")
        )
            .setResultPackage("results")
            .setSrcPackage("src");
    }

    it("Issue168", async () => {
        await newTester()
            .set(KadabraJavaTypes.JavaWeaverKeys.NO_CLASSPATH)
            .test("IssueInnerClass.js", "Muscle.java.test");
    });
});
