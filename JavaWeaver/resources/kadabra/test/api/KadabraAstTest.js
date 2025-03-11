import kadabra.KadabraAst;
import weaver.Query;

aspectdef KadabraAstTest

	for(var $constant of KadabraAst.getConstantInitializations()) {
		console.log("Constant: " + $constant.code);
	}
	
	/*
	for(var $chain of Query.search("method", "loops").search("loop").search("body").children("loop").chain()) {
		console.log("Loop:\n"+$chain["loop"].code);
	}
	*/

end

