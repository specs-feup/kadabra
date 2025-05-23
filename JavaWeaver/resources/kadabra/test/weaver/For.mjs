import Query from "@specs-feup/lara/api/weaver/Query.js";

const $emptyForLoop = Query.search("method", "emptyFor").search("loop").first();
console.log("Empty for condition: " + $emptyForLoop.cond);
