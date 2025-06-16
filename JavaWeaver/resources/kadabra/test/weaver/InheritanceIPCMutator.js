import Mutator from "@specs-feup/lara/api/lara/mutation/Mutator.js";
import Io from "@specs-feup/lara/api/lara/Io.js";
import Weaver from "@specs-feup/lara/api/weaver/Weaver.js";
import Query from "@specs-feup/lara/api/weaver/Query.js";

/**
 *  @param {$joinpoint} $joinpoint - Joinpoint used as starting point to search for super constructor calls to be removed.
 */
class InheritanceIPCMutator extends Mutator {
    constructor($joinpoint) {
        super();

        if ($joinpoint === undefined) {
            $joinpoint = Query.root();
        }

        // Instance variables
        this.toMutate = [];
        this.currentIndex = 0;

        this.originalSuperCall = undefined;
        this.$superCall = undefined;

        // Checks
        const extraArgs = arrayFromArgs(arguments, 1);
        if (extraArgs.length != 0)
            throw new Error(
                "Expected only 1 argument but received " +
                    (this.extraArgs.length + 1)
            );

        this.extractMutationTargets($joinpoint);

        if (this.toMutate.length == 0)
            console.log("Found no suitable code to mutate");
    }

    /* Store super constructor calls */
    extractMutationTargets($joinpoint) {
        for (const $constructor of Query.searchFrom(
            $joinpoint,
            "constructor"
        ).get()) {
            // Check if constructor contains a super call in its source code
            if (!$constructor.srcCode.includes("super(")) continue;

            // Search for super calls inside constructors (since they can only appear inside constructors, we avoid searching the whole program)
            for (const $descendant of $constructor.descendants) {
                if (
                    $descendant.instanceOf("call") &&
                    $descendant.srcCode.startsWith("super(") &&
                    $descendant.srcCode.endsWith(")")
                ) {
                    this.toMutate.push($descendant);
                    break;
                }
            }
        }
    }

    hasMutations() {
        return this.currentIndex < this.toMutate.length;
    }

    mutatePrivate() {
        this.$superCall = this.toMutate[this.currentIndex++];
        this.originalSuperCall = this.$superCall.copy();

        // Replace super constructor call by a comment
        this.$superCall = this.$superCall.insertReplace(
            "// Super constructor call has been removed"
        );

        console.log("/*--------------------------------------*/");
        console.log(
            "Mutating operator n." +
                this.currentIndex +
                ": " +
                this.originalSuperCall +
                " to " +
                this.$superCall
        );
        console.log("/*--------------------------------------*/");
    }

    restorePrivate() {
        this.$superCall = this.$superCall.insertReplace(this.originalSuperCall);

        this.originalSuperCall = undefined;
        this.$superCall = undefined;
    }

    getMutationPoint() {
        if (this.isMutated) {
            console.log("MUTATION POINT AFTER: " + this.$superCall.code);
            return this.$superCall;
        } else {
            if (this.currentIndex < this.toMutate.length) {
                console.log(
                    "MUTATION POINT BEFORE: " +
                        this.toMutate[this.currentIndex].code
                );
                return this.toMutate[this.currentIndex];
            } else {
                return undefined;
            }
        }
    }
}

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

var mutator = new InheritanceIPCMutator(Query.root());

while (mutator.hasMutations()) {
    // Mutate
    mutator.mutate();
    // Print
    //console.log(mutator.getMutationPoint().parent.code);
    saveFile();
    // Restore operator
    mutator.restore();
}
