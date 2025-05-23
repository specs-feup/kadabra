import Query from "@specs-feup/lara/api/weaver/Query.js";

for (const $chain of Query.search("var").chain()) {
    console.log("Var type: " + $chain["var"].type);
}
