import Query from "@specs-feup/lara/api/weaver/Query.js";

for (const varDecl of Query.search("method", "types").search("localVariable")) {
    console.log("VarDecl: " + varDecl.code);
    const typeRef = varDecl.typeReference;
    console.log("type: " + typeRef.qualifiedName);
    console.log("isBoolean: " + typeRef.isBoolean);
    console.log("isNumeric: " + typeRef.isNumeric);
}
