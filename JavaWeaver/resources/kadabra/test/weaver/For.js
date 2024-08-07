laraImport("weaver.Query")

var $emptyForLoop = Query.search("method", "emptyFor").search("loop").first()
console.log("Empty for condition: " + $emptyForLoop.cond)