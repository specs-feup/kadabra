import { WeaverLegacyTester } from "@specs-feup/lara/vitest/WeaverLegacyTester.ts";
import JavaTypes from "@specs-feup/lara/api/lara/util/JavaTypes.ts";

export class KadabraWeaverTester extends WeaverLegacyTester {
  public constructor(basePackage: string) {
    super(basePackage);

    this.set(
      JavaTypes.LaraiKeys.OUTPUT_FOLDER,
      new JavaTypes.File("woven_code")
    );
  }
}
