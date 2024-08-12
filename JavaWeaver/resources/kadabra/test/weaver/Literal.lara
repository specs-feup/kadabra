import weaver.Query;
import kadabra.KadabraNodes;


aspectdef Literal

	for(var $literal of Query.search('method', {name: 'foo1'}).search('literal').get()) {
		console.log("old value: " + $literal.value);	
		changeType($literal);
		console.log("new value: " + $literal.value);	
	}
	
		
	// Get the literal
	var $negativeLiteral = Query.search('method', {name: 'foo2'}).search('literal').first();

	// Create a new negative literal	
	var $newLiteral = KadabraNodes.literal("-1", $negativeLiteral.type);
	
	// Replace it
	$negativeLiteral.insertReplace($newLiteral);	

	// Print
	console.log(Query.search('method', {name: 'foo2'}).first().code);	


end

function changeType($literal) {
		if($literal.type === 'boolean') {
			$literal.value = 'false';
			return;
		}
		
		// Default
		$literal.value = "100";
}
