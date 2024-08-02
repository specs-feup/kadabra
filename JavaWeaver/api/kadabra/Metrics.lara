aspectdef Main

	var metrics = call Extract();
	printObject(metrics.report);

end

aspectdef Extract
	input packages=".*", classes=".*" end
	output report={
			types:{},
			numOf:{
				classes: 0,
				interfaces: 0,
				enums: 0,
				methods: 0,
				fields: 0
			}
		}
	end
	
	select type end
	apply
		typeReport = call ReportType($type);
		merge(report, typeReport.report);
	end
//	var line = toCSVLine(report);
//	console.log(line);
end

function toCSVLine(report){
	var line = report.numOf.classes
		+ "," + report.numOf.interfaces
		+ "," + report.numOf.enums
		+ "," + report.numOf.methods
		+ "," + report.numOf.fields
		;
	return line;
}

function merge(mainReport, typeReport){
	mainReport.types[typeReport.qualifiedName] = typeReport;
	mainReport.numOf.methods+=typeReport.numOf.methods;
	mainReport.numOf.fields+=typeReport.numOf.fields;
	switch(typeReport.type){
		case 'class': mainReport.numOf.classes++; break;
		case 'interface': mainReport.numOf.interfaces++; break;
		case 'enum': mainReport.numOf.enums++; break;
		default: console.log("Unknown type: "+typeReport.type); break;
	}
}


aspectdef ReportType
	input $type end
	output report = {
			type: undefined,
			name: undefined,
			qualifiedName: undefined,
			numOf:{
				methods: 0,
				fields: 0
			},
			methods:[]
		}
	end

	report.name = $type.name;
	report.qualifiedName = $type.qualifiedName;
	report.type = $type.joinPointType;
	
	select $type.field end
	apply
		report.numOf.fields++;
	end
	select $type.method end
	apply
		report.numOf.methods++;
		methodReport = call ReportMethod($method);
		report.methods.push(methodReport.report);
	end
end

aspectdef ReportMethod
	input $method end
	output report = {
			type: undefined,
			name: undefined,
			qualifiedName: undefined,
			numOf:{
				statements: 0,
				//loops: 0, //todo
				//...
			} 
		}
	end

	report.type = $method.returnType;
	report.name = $method.name;
	report.qualifiedName = $method.toQualifiedReference;
	select $method.body end
	apply
		var statementAction = function ($stmt) { report.numOf.statements++;};
		call ProcessBody($body, statementAction);
	end
end

aspectdef ProcessStatement
	input $stmt, func end
	func($stmt);
	//console.log($stmt.srcCode);
	switch($stmt.kind){
		case "for":
		case "forEach":
			call ProcessBodyFrom($stmt, func);
			break;
		case "if":
			call ProcessIfElse($stmt, func);
			break;
		case "switch":
			call ProcessSwitch($stmt, func);
			break;
		case "case":
			call ProcessBody($stmt, func);
			break;
	}
end

aspectdef ProcessSwitch
	input $switch, func end
	select $switch.case end
	apply
		call ProcessStatement($case, func);
	end
end

aspectdef ProcessIfElse
	input $if, func end
	select $if.then end
	apply
		call ProcessBody($then, func);
	end
	select $if.else end
	apply
		call ProcessBody($else, func);
	end
end

aspectdef ProcessBodyFrom
	input $stmt, func end
	select $stmt.body end
	apply
		call ProcessBody($body, func);
	end
end

aspectdef ProcessBody
	input $body, func end
	select $body.statement end
	apply
		call ProcessStatement($statement, func);
	end
	
end