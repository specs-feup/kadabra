laraImport("weaver.Query");
laraImport("kadabra.KadabraNodes");

for(var $if of Query.search("method", "ifChildren").search("if")) {
	console.log("Children: " + $if.children.length);
}
