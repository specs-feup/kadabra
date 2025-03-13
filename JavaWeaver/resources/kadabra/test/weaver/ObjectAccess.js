laraImport("weaver.Query");


//console.log("AST:\n" + Query.root().ast);

//console.log("Code:\n" + Query.root().code);

console.log("Call: " + Query.search("method").search("call").first().code);

//console.log("Arg arg ast: " + Query.search("method").search("call").first().arguments[0].ast);
console.log("Arg type type: " + Query.search("method", {name: "test"}).search("call").first().arguments[0].typeReference);
console.log("Arg type package: " + Query.search("method", {name: "test"}).search("call").first().arguments[0].typeReference.packageName);


for (const $expr of Query.search("expression", {code: /^R\.*/})) {
	console.log("Code -> " + $expr.code + ": " + $expr.type);
	console.log("Qualified name -> " + $expr.qualifiedName);
}
