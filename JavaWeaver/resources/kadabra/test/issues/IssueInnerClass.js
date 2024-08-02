laraImport("weaver.Query");
const loop = Query.search("method", "sortedListOfMuscleIdsFrom")
  .search("loop")
  .first();

println("Loop in inner class ancestors:");
println("Parent x 1: " + loop.parent.joinPointType);
//println(loop.parent.code);

println("Parent x 2: " + loop.parent.parent.joinPointType);
//println(loop.parent.parent.code);

println("Parent x 3: " + loop.parent.parent.parent.joinPointType);
//println(loop.parent.parent.parent.code);

//println("------");
println("Parent x 4: " + loop.parent.parent.parent.parent.joinPointType);
//println(loop.parent.parent.parent.parent.code);

//const $loop = Query.search("method", "getNewId").search("loop").first();
//println("LOOP: " + $loop.code);
//getNewId.getAncestor("")

/*
laraImport("kadabra.analysis.energy.EnergyAwareAndroidPatterns");

let result = new InternalGetterDetector().analyse();
println(result.results[0].getAncestor("method")); //undefined
println(result.results[0].getAncestor("class").parent); //inner class
println(result.results[0].getAncestor("class").ast); //file node
*/
