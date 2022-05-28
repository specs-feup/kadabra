laraImport("weaver.Query")

var $emptyForLoop = Query.search("method", "emptyFor").search("loop").first()
println("Empty for condition: " + $emptyForLoop.cond)