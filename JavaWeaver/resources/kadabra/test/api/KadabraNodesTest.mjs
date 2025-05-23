import Query from "@specs-feup/lara/api/weaver/Query.js";
laraImport("kadabra.KadabraNodes");

// Insert a join point before
const foo = Query.search("method", "foo").search("expression").getFirst();
foo.insertBefore(KadabraNodes.comment("A comment"));

// Replace a join point
const foo2 = Query.search("method", "foo2").getFirst();
foo2.insertReplace(KadabraNodes.comment("A comment replacing foo2"));

// Remove a join point
const foo3 = Query.search("method", "foo3").getFirst();
foo3.remove();

const foo4 = Query.search("method", "foo4").getFirst();
const commentStart = foo4.insertBefore("/*");
const commentEnd = foo4.insertAfter("*/");

commentStart.remove();
commentEnd.remove();

const foo5LocalVar = Query.search("method", "foo5")
    .search("localVariable")
    .getFirst();

// Create write reference to local variable
const foo5Var = KadabraNodes.var(foo5LocalVar, true);

// Get initialization, and remove it from declaration
const foo5Lhs = foo5LocalVar.init.copy();
foo5LocalVar.init.remove();

// Create assignment
const foo5Assign = KadabraNodes.assignment(foo5Var, foo5Lhs);

// Add assignment after initialization
foo5LocalVar.insertAfter(foo5Assign);

for (const chainJp of Query.search("method", "snippetExpr")
    .search("new")
    .chain()) {
    chainJp["new"].insertReplace(KadabraNodes.snippetExpr("null"));
}

// Print modified file
console.log(Query.search("file").getFirst().code);
