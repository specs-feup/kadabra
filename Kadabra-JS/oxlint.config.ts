import config from "@specs-feup/lara/oxlint.config.ts";
import { defineConfig } from "oxlint";

export default defineConfig({
  extends: [config],
  overrides: [
    {
      // Generated file: the enums use the const + type merged declaration
      // pattern, and 'if'/'ternary' join points have a 'then' attribute.
      files: ["api/Joinpoints.ts"],
      rules: {
        "eslint/no-redeclare": "off",
        "unicorn/no-thenable": "off",
      },
    },
    {
      // Legacy API code that predates the lara-4 migration: uses template
      // literals with non-string values and detached method references.
      files: ["api/kadabra/**"],
      rules: {
        "typescript/restrict-template-expressions": "off",
        "typescript/unbound-method": "off",
      },
    },
  ],
});
