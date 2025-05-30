import Query from "@specs-feup/lara/api/weaver/Query.js";

for (const $if of Query.search("method", "ifChildren").search("if")) {
    console.log("Children: " + $if.children.length);
}
