import weaver.Query;

aspectdef LambdasTest

//	console.log(Query.root().ast);
//	console.log(Query.root().code);



	var code = "";
	for(var $jp of Query.root().descendants) {
		//console.log("Adding code for method "+$method.name+":\n" + $method.code);
		if($jp.joinPointType === 'method' || $jp.joinPointType === 'constructor') {
			code += $jp.code;		
		}

	}
	
	console.log("Code from methods:\n"+code);


	console.log("Code from app:\n" + Query.root().code);
end
