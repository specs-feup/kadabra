import { KadabraWeaverTester } from "../Jest/KadabraLegacyTester.js";
import path from "path";
import "@specs-feup/kadabra/api/Joinpoints.js";
import KadabraJavaTypes from "./kadabra/KadabraJavaTypes.js";

/* eslint-disable jest/expect-expect */
describe("WeaverTest", () => {
    function newTester() {
        return new KadabraWeaverTester(
            path.resolve(
                "../JavaWeaver/test-resources/tests/kadabra/test/weaver"
            )
        )
            .setResultPackage("results")
            .setSrcPackage("src");
    }

    it("MutateOperator", async () => {
        await newTester().test("MutateOperator.js", "MutateOperator.java");
    });

    it("GenericJoinPoint", async () => {
        await newTester().test(
            "GenericJoinPoint.js",
            "GenericJoinPoint.java"
        );
    });

    it("BinaryOperator", async () => {
        await newTester().test("BinaryOperator.js", "BinaryOperator.java");
    });

    it("Literal", async () => {
        await newTester().test("Literal.js", "Literal.java");
    });

    it("GlobalJp", async () => {
        await newTester().test("GlobalJp.js", "GlobalJp.java");
    });

    it("Ast", async () => {
        await newTester().test("Ast.js", "Ast.java");
    });

    it("Ast2", async () => {
        await newTester().test("Ast2.js", "Ast2.java");
    });

    it("Type", async () => {
        await newTester().test("Type.js", "Type.java");
    });

    it("Selects", async () => {
        await newTester().test("Selects.js", "Selects.java");
    });

    it("Android", async () => {
        await newTester().test(
            "Android.js",
            "src/AndroidTest.java",
            "src/main/AndroidManifest.xml"
        );
    });

    it("Android2", async () => {
        await newTester().test(
            "Android2.js",
            "src/AndroidTest2.java",
            "src/main/AndroidManifest.xml"
        );
    });

    it("CompilationError", async () => {
        await newTester()
            .set(KadabraJavaTypes.JavaWeaverKeys.NO_CLASSPATH)
            .test("CompilationError.js", "CompilationError.java");
    });

    it("ObjectAccess", async () => {
        await newTester()
            .set(KadabraJavaTypes.JavaWeaverKeys.NO_CLASSPATH)
            .set(KadabraJavaTypes.JavaWeaverKeys.FULLY_QUALIFIED_NAMES)
            .test("ObjectAccess.js", "ObjectAccess.java");
    });

    it("InheritanceIPCMutator", async () => {
        await newTester().test(
            "InheritanceIPCMutator.js",
            "InheritanceIPCTest.java"
        );
    });

    it("ConstructorCallMutator", async () => {
        await newTester()
            .set(KadabraJavaTypes.JavaWeaverKeys.FULLY_QUALIFIED_NAMES)
            .test("ConstructorCallMutator.js", "ConstructorCallTest.java");
    });

    it("Lambdas", async () => {
        await newTester()
            .set(KadabraJavaTypes.JavaWeaverKeys.NO_CLASSPATH)
            .test("Lambdas.js", "Lambdas.java");
    });

    it("Constructor", async () => {
        await newTester().test("Constructor.js", "Constructor.java");
    });

    it("For", async () => {
        await newTester().test("For.js", "For.java");
    });

    it("TypeReference", async () => {
        await newTester().test("TypeReference.js", "TypeReference.java");
    });
});
