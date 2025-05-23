import Query from "@specs-feup/lara/api/weaver/Query.js";

const loop = Query.search("method", "sortedListOfMuscleIdsFrom")
    .search("loop")
    .first();

console.log("Loop in inner class ancestors:");
console.log("Parent x 1: " + loop.parent.joinPointType);
//console.log(loop.parent.code);

console.log("Parent x 2: " + loop.parent.parent.joinPointType);
//console.log(loop.parent.parent.code);

console.log("Parent x 3: " + loop.parent.parent.parent.joinPointType);
//console.log(loop.parent.parent.parent.code);

//console.log("------");
console.log("Parent x 4: " + loop.parent.parent.parent.parent.joinPointType);
//console.log(loop.parent.parent.parent.parent.code);

//const $loop = Query.search("method", "getNewId").search("loop").first();
//console.log("LOOP: " + $loop.code);
//getNewId.getAncestor("")

/*
laraImport("kadabra.analysis.energy.EnergyAwareAndroidPatterns");

let result = new InternalGetterDetector().analyse();
console.log(result.results[0].getAncestor("method")); //undefined
console.log(result.results[0].getAncestor("class").parent); //inner class
console.log(result.results[0].getAncestor("class").ast); //file node
*/
