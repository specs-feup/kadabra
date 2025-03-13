laraImport("weaver.Query");
laraImport("kadabra.KadabraNodes");

for(const $if of Query.search("method", "ifChildren").search("if")) {
	console.log("Children: " + $if.children.length);
}
