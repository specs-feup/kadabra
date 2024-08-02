laraImport("lara.mutation.Mutator");
laraImport("lara.Io");
laraImport("weaver.Weaver");
laraImport("weaver.Query");

function ConstructorCallMutatorTest() {
    const mutator = new ConstructorCallMutator(Query.root());

    while (mutator.hasMutations()) {
        // Mutate
        mutator.mutate();

        saveFile();
        // Restore operator
        mutator.restore();
    }
}

function saveFile() {
    const outputFolder = Io.mkdir("./mutatedFilesTest/");
    Io.deleteFolderContents(outputFolder);

    // Write modified code
    Weaver.writeCode(outputFolder);

    // Print contents
    for (const mutatedFile of Io.getFiles(outputFolder, "*.java")) {
        println("<File '" + mutatedFile.getName() + "'>");
        println(Io.readFile(mutatedFile));
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

        this.$referenceParent = undefined;
        this.$originalParent = undefined;

        // Checks
        if (this.extraArgs.length != 0)
            throw new Error(
                "Expected only 1 argument but received " +
                    (this.extraArgs.length + 1)
            );

        for (const $ref of Query.searchFrom(this.$joinpoint, "reference")
            .get()
            .reverse()) {
            // Check it is a constructor call reference
            if (
                $ref.name === "<init>" &&
                $ref.type === "Executable" &&
                $ref.parent.srcCode !== "super()"
            )
                this.toMutate.push($ref);
        }

        this.totalMutations = this.toMutate.length;
        if (this.totalMutations == 0)
            println("Found no suitable code to mutate");
    }

    hasMutations() {
        return this.currentIndex < this.totalMutations;
    }

    mutatePrivate() {
        this.$referenceParent = this.toMutate[this.currentIndex++].parent;

        this.$originalParent = this.$referenceParent.copy();
        this.$referenceParent = this.$referenceParent.insertReplace("null");

        println("/*--------------------------------------*/");
        println(
            "Mutating operator n." +
                this.currentIndex +
                ": " +
                this.$originalParent +
                " to " +
                this.$referenceParent
        );
        println("/*--------------------------------------*/");
    }

    restorePrivate() {
        this.$referenceParent = this.$referenceParent.insertReplace(
            this.$originalParent
        );
        this.$originalParent = undefined;
        this.$referenceParent = undefined;
    }
}
