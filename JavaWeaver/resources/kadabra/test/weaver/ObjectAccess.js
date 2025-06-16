import Query from "@specs-feup/lara/api/weaver/Query.js";

console.log("Call: " + Query.search("method").search("call").first().code);

console.log(
    "Arg type type: " +
        Query.search("method", { name: "test" }).search("call").first()
            .arguments[0].typeReference
);
console.log(
    "Arg type package: " +
        Query.search("method", { name: "test" }).search("call").first()
            .arguments[0].typeReference.packageName
);
