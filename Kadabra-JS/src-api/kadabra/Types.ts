//import kadabra.Utils;

var KTypes = {
	Thread: "weaver.kadabra.concurrent.KadabraThread",
	Channel: "weaver.kadabra.concurrent.KadabraChannel",
	Product: "weaver.kadabra.concurrent.Product",
	ControlPoint: "weaver.kadabra.control.ControlPoint",
	ControlPointFactory: "weaver.kadabra.control.ControlPointFactory",
	VersionProviderUtils: "weaver.kadabra.control.utils.ProviderUtils",
	VersionProvider: "weaver.kadabra.control.utils.VersionProvider",
	Tuple:  "tdrc.tuple.Tuple",
	Pair: "tdrc.utils.Pair",
	Atomic: "java.util.concurrent.atomic.Atomic",
	channelOf: function a(key: string, value: string): string {
		return this.Channel + "<" +(key || 'Object') + "," + (value || 'Object') + ">";
	},
	productOf: function a(key: string, value: string): string {
		if (key == undefined || value == undefined){
			return ;
		}
		return this.Product + "<" + (key || 'Object') + "," + (value || 'Object') + ">";
	},
	controlPointOf: function a(type: string): string {
		return this.ControlPoint + "<" + (type || "Object") + ">";
	},
	providerOf: function(type: string): string {
		return this.VersionProvider + "<" + (type || "Object") + ">";
	},
	tupleOf: function (type: string): string {
	return this.Tuple + "<" + type + ">";
	},
	listOf: function (type: string): string {
		return "java.util.List<" + type + ">";
	},
	pairOf: function (type1: string, type2: string): string {
		return this.Pair + "<" + type1 + "," + type2 + ">";
	},

	atomicOf: function(type: string): string {
		if (isPrimitive(type)){
			return this.Atomic + toWrapper(type);
		}
		return this.Atomic + "Reference<" + type + ">";
	},
};

function toWrapper(type: string): string {
	switch(type) {
		case 'bool':
			return 'Boolean';
		case 'int':
			return 'Integer';
		case 'char': 
			return 'Character';
		case 'void': 
		case 'byte': 
		case 'short':
		case 'long': 
		case 'float':
		case 'double':
			return type.charAt(0).toUpperCase() + type.slice(1);
		default: 
			return type;
	}
}

var PRIMITIVE_TYPES = [ 'bool','int','char','void','byte','short','long','float','double'];

function isPrimitive(type: string): boolean {
	return PRIMITIVE_TYPES.indexOf(type) > -1;
}

var Types = new Enumeration('bool','int','char','void','byte','short','long','float','double');
