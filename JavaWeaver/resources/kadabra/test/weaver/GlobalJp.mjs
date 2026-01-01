import Query from "@specs-feup/lara/api/weaver/Query.js";

// App children
const $app = Query.root();
console.log("app num children: " + $app.numChildren);

// File children
for (const $file of Query.search("file")) {
    console.log("file num children: " + $file.numChildren);
}

// Statement children
const $method = Query.search("type").search("method", "foo").getFirst();
const $statement = Query.searchFrom($method.body, "statement").getFirst();
console.log("stmt children: " + $statement.children);
console.log("stmt child 0: " + $statement.child(0));
console.log("stmt num children: " + $statement.numChildren);

// App ast
const $newApp = Query.root();
console.log("app ast:\n" + $newApp.ast);
