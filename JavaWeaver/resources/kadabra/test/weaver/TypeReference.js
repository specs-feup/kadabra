laraImport("weaver.Query");

for (const varDecl of Query.search("method", "types").search("localVariable")) {
  println("VarDecl: " + varDecl.code);
  const typeRef = varDecl.typeReference;
  println("type: " + typeRef.qualifiedName);
  println("isBoolean: " + typeRef.isBoolean);
  println("isNumeric: " + typeRef.isNumeric);
}
