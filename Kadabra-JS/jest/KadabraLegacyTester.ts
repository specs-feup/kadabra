import { WeaverLegacyTester } from "@specs-feup/lara/jest/WeaverLegacyTester.js";
import JavaTypes from "@specs-feup/lara/api/lara/util/JavaTypes.js";

export class KadabraWeaverTester extends WeaverLegacyTester {
  public constructor(basePackage: string) {
    super(basePackage);

    this.set(
      JavaTypes.LaraiKeys.OUTPUT_FOLDER,
      new JavaTypes.File("woven_code")
    );
  }
}
