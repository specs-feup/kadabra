import WeaverConfiguration from "@specs-feup/lara/code/WeaverConfiguration.js";
import path from "path";
import { fileURLToPath } from "url";

export const weaverConfig: WeaverConfiguration = {
  weaverName: "kadabra",
  weaverPrettyName: "Kadabra",
  weaverFileName: "@specs-feup/lara/code/Weaver.js",
  jarPath: path.join(
    path.dirname(path.dirname(fileURLToPath(import.meta.url))),
    "./java-binaries/"
  ),
  javaWeaverQualifiedName: "weaver.kadabra.JavaWeaver", // FIXME maybe
  importForSideEffects: ["@specs-feup/kadabra/api/Joinpoints.js"], // FIXME maybe
};
