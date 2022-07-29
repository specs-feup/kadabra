laraImport("weaver.Query");
laraImport("lara.Io");

class BaseDetector {
  constructor(name, debugEnabled = false) {
    this.name = name;
    this.results = [];
    this.debugEnabled = debugEnabled;
  }

  analyse(packageFilter = (_) => true) {
    println(`Running ${this.name}...`);

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
      println("Argument is not a joinpoint of type 'class'");
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

//https://stackoverflow.com/questions/29151435/javascript-place-elements-that-dont-match-filter-predicate-into-seperate-array
// ----- partition function declaration -----
/** Returns an array with two arrays at index
 * 0 and 1. The array at index 0 is all the items
 * in `arr` that passed the `predicate` truth test by
 * returning a truthy value. The array at index 1 is all the items
 * in `arr` that failed the `predicate` truth test by returning
 * a falsy value.
 * @template {any} T
 * @param {Array<T>} arr
 * @param {(el:T, index:number, arr:Array<T>) => any} predicate
 * @returns {[Array<T>, Array<T>]}
 */
 function partition(arr, predicate) {
  return arr.reduce(
    // this callback will be called for each element of arr
    function (partitionsAccumulator, arrElement, i, arr) {
      if (predicate(arrElement, i, arr)) {
        // predicate passed push to left array
        partitionsAccumulator[0].push(arrElement);
      } else {
        // predicate failed push to right array
        partitionsAccumulator[1].push(arrElement);
      }
      // whatever is returned from reduce will become the new value of the
      // first parameter of the reduce callback in this case
      // partitionsAccumulator variable if there are no more elements
      // this return value will be the return value of the full reduce
      // function.
      return partitionsAccumulator;
    },
    // the initial value of partitionsAccumulator in the callback function above
    // if the arr is empty this will be the return value of the reduce
    [[], []]
  );
}

/** Prints tabular data using 3 arrays,
 * first for headers ie. ["HeaderA", "HeaderB", ...],
 * second for the row data ie. [row1Obj, row2Obj, ...] where row1Obj.length == headers.length == spacing.length
 * third for spacing ie. [10, 100, ...]
 * @param {Array<string>} headers
 * @param {Array<Array<string>>} rowData
 * @param {Array<int>} spacing
 */
function printTable(headers, rowData, spacing) {
  let headerStr = headers.map((h, i) => h.padEnd(spacing[i])).join("");
  println(headerStr);
  rowData.forEach((row) => {
    let rowStr = row.map((d, i) => d.padEnd(spacing[i])).join("");
    println(rowStr);
  });
}
