laraImport("weaver.Query")

const $emptyForLoop = Query.search("method", "emptyFor").search("loop").first()
console.log("Empty for condition: " + $emptyForLoop.cond)