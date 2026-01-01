import Query from "@specs-feup/lara/api/weaver/Query.js";

const loop = Query.search("method", "sortedListOfMuscleIdsFrom")
    .search("loop")
    .first();

console.log("Loop in inner class ancestors:");
console.log("Parent x 1: " + loop.parent.joinPointType);

console.log("Parent x 2: " + loop.parent.parent.joinPointType);

console.log("Parent x 3: " + loop.parent.parent.parent.joinPointType);

console.log("Parent x 4: " + loop.parent.parent.parent.parent.joinPointType);
