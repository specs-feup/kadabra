import kadabra.Factory;
function DataFeature(name){
	this.name = name;
	this.qualifiedName = name;
	this.features = [];
}

DataFeature.PACKAGE = "kadabra.adapt.features";
DataFeature.prototype.generate = function(package){
	package = package || DataFeature.PACKAGE;
	this.qualifiedName = package+"."+this.name;
	$nc = call NewClass(this.qualifiedName, "Comparable<"+this.name+">");
	return $nc;
}

DataFeature.prototype.addFeature = function(type, name){
	
	return $nc;
}