laraImport("weaver.Query");
laraImport("lara.Io");
laraImport("lara.Collections")

class BaseDetector {
  constructor(name, debugEnabled = false) {
    this.name = name;
    this.results = [];
    this.debugEnabled = debugEnabled;
  }

  analyse(packageFilter = (_) => true) {
    console.log(`Running ${this.name}...`);

    let classes = Query.search("class", {
      isTopLevel: true,
      package: packageFilter,
    }).get();

    classes.forEach((c) => this.analyseClass(c));

    return this;
  }

  analyseClass(jpClass) {
    if (
      !jpClass ||
      !("instanceOf" in jpClass) ||
      !jpClass.instanceOf("class")
    ) {
      console.log("Argument is not a joinpoint of type 'class'");
    }
  }

  print() {
    throw new Error(
      `Method 'print()' was not implemented for detector '${this.name}'`
    );
  }

  save() {
    throw new Error(
      `Method 'save()' was not implemented for detector '${this.name}'`
    );
  }
}
