laraImport("weaver.Query");

for (const $chain of Query.search("var").chain()) {
	console.log("Var type: " + $chain["var"].type);
}
