laraImport("weaver.Query");

// App children
const $app = Query.root();
console.log("app num children: " + $app.numChildren);

// File children
for (const $file of Query.search("file")) {
    console.log("file num children: " + $file.numChildren);
}

// Statement children
const $statement = Query.search("type").search("method", "foo").search("statement").getFirst();
console.log("stmt children: " + $statement.children);
console.log("stmt child 0: " + $statement.child(0));
console.log("stmt num children: " + $statement.numChildren);

// App ast
const $newApp = Query.root();
console.log("app ast:\n" + $newApp.ast);
