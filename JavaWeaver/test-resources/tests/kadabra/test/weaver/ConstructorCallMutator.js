import Mutator from "@specs-feup/lara/api/lara/mutation/Mutator.js";
import Io from "@specs-feup/lara/api/lara/Io.js";
import Query from "@specs-feup/lara/api/weaver/Query.js";
import Weaver from "@specs-feup/lara/api/weaver/Weaver.js";
import { arrayFromArgs } from "@specs-feup/lara/api/lara/core/LaraCore.js";

function saveFile() {
  const outputFolder = Io.mkdir("./mutatedFilesTest/");
  Io.deleteFolderContents(outputFolder);

  // Write modified code
  Weaver.writeCode(outputFolder);

  // Print contents
  for (const mutatedFile of Io.getFiles(outputFolder, "*.java")) {
    console.log("<File '" + mutatedFile.getName() + "'>");
    console.log(Io.readFile(mutatedFile));
  }

  Io.deleteFolder(outputFolder);
}

/**
 *  @param {$joinPoint} $joinPoint - A join point to use as startpoint to search for constructor calls to replace with null.
 */
class ConstructorCallMutator extends Mutator {
  constructor($joinPoint) {
    super();

    if ($joinPoint === undefined) {
      $joinPoint = Query.root();
    }

    // Instance variables
    this.$joinPoint = $joinPoint;
    this.extraArgs = arrayFromArgs(arguments, 1);

    this.toMutate = [];
    this.totalMutations = -1;
    this.currentIndex = 0;

    this.$reference = undefined;
    this.$original = undefined;

    // Checks
    if (this.extraArgs.length != 0)
      throw new Error(
        "Expected only 1 argument but received " + (this.extraArgs.length + 1)
      );

    for (const $ref of Query.searchFrom(this.$joinpoint, "new")
      .get()
      .reverse()) {
      // TODO: Not sure if this check is still needed
      if ($ref.name === "<init>" && $ref.parent.srcCode !== "super()") {
        this.toMutate.push($ref);
      }
    }

    this.totalMutations = this.toMutate.length;
    if (this.totalMutations == 0)
      console.log("Found no suitable code to mutate");
  }

  hasMutations() {
    return this.currentIndex < this.totalMutations;
  }

  mutatePrivate() {
    this.$reference = this.toMutate[this.currentIndex++];

    this.$original = this.$reference.copy();
    this.$reference = this.$reference.insertReplace("null");

    console.log("/*--------------------------------------*/");
    console.log(
      "Mutating operator n." +
        this.currentIndex +
        ": " +
        this.$original +
        " to " +
        this.$reference
    );
    console.log("/*--------------------------------------*/");
  }

  restorePrivate() {
    this.$reference = this.$reference.insertReplace(this.$original);
    this.$original = undefined;
    this.$reference = undefined;
  }
}

const mutator = new ConstructorCallMutator(Query.root());

while (mutator.hasMutations()) {
  // Mutate
  mutator.mutate();

  saveFile();
  // Restore operator
  mutator.restore();
}
