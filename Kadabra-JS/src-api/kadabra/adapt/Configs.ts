import kadabra.Utils;
/**
 * Class defining the structure of an autotuner 
 */
function Configs(){
//	this.type = type;
//	this.provider = undefined;
}


///////////////////////////////////////////////////
//////////////// USEFUL CONSTANTS /////////////////
///////////////////////////////////////////////////
Configs.PACKAGE = "autotuner.configs.";
Configs.FACTORY = Configs.PACKAGE+"factory.ConfigFactory";
Configs.RANGED = Configs.PACKAGE+"knobs.number.ranged.";
Configs.Tuple = "tdrc.tuple.Tuple";

Configs.order = {

	'normal': Configs.FACTORY+'::normal',
	'random': Configs.FACTORY+'::random',
}


function Configuration(knobs, configCode, type){
	if(Array.isArray(knobs)){
		var applierCode = '(knobs) -> {\n';
		for(var i =0; i < knobs.length; i++){
			var k = knobs[i];
			validateKnob(k);
			applierCode +='\t'+k.staticAccess+' = ('+k.type+')knobs.get('+i+');\n';
		}
		this.applier = applierCode+'}';
	}else{
		validateKnob(knobs);
		//if(!knobs.isStatic) <-- create attribute
		this.applier = %{(knob)-> [[knobs.staticAccess]] = knob}%;
	}
	this.type = type;
	this.configCode = configCode;
}

function validateKnob(knob){
	if(!Weaver.isJoinPoint(knob)){
		throw 'The knobs used in the configuration must be join points';
	}

	if(!knob.instanceOf('field')){
		throw 'A join point of type ' + knob.joinPointType +' was given. The current supported knobs are: field.';
	}
}


Configuration.prototype.declare = function(name){
	return Configs.PACKAGE+'Configuration<'+this.type+'> '+name+' = '+this.get();
}

Configuration.prototype.declareProvider = function(name){
	return 'java.util.function.Supplier< '+Configs.PACKAGE+'Configuration<'+this.type+'>> '+name+' = '+this.provider;
}

Configuration.prototype.provider = function(){
	return '()-> '+this.configCode;
}

Configuration.prototype.get = function(){
	return this.configCode;
}

Configuration.prototype.toString = function(){
	return this.get();
}


    //////////////////////////////////////////////////////////////
    //////////////// GENERIC CONFIGS USING LISTS /////////////////
    //////////////////////////////////////////////////////////////

function list2Config(knobs, constructor, values, acceptsSingle, type){
	if(values.length == 0){
		throw 'At least one value must be provided to create a '+constructor+' configuration';
	}

	if(values.length == 1 && acceptsSingle){
		return  new Configuration(knobs, Configs.FACTORY+'.'+constructor+'('+values.join(',')+')',type);
	}
//	return  new Configuration(knobs, Configs.FACTORY+'.'+constructor+'(java.util.Arrays.asList('+values.join(',')+'))',type);
	return  new Configuration(knobs, Configs.FACTORY+'.'+constructor+'('+values.join(',')+')',type);
}


/**
	A list of values to test in the order they are given.
**/
Configs.default = function(knobs, values, type){
     return list2Config(knobs, 'normal', values, false, type);
}
/**
	A list of values to test in random order.
**/
Configs.random = function(knobs, values, type){
     return list2Config(knobs, 'random', values, false, type);
}


Configs.combine = function(knobs, values, type){
	if(!Array.isArray(values) || values.length < 2){ 
		throw 'At least two values must be provided to combine values';
	}
     args = values.map(function(arg) {
    		return 'java.util.Arrays.asList('+arg.join(',')+')';
	});
     return new Configuration(knobs, Configs.FACTORY+'.normal(tdrc.utils.ListUtils.createTuplesFromList('+args.join(',')+'))', type);
}




    ///////////////////////////////////////////////////////
    //////////////// CONFIGS USING RANGES /////////////////
    ///////////////////////////////////////////////////////
/*
	This configuration uses Ranged knobs, which are defined in the lower part of this file (e.g. IntegerRange)
*/
Configs.randomOf = function(knobs, ranges){
     return rangedConfig(knobs, ranges,'random', true);	
}


Configs.range = function(knobs, ranges){
	return rangedConfig(knobs, ranges,'range', true);	
}
Configs.around = function(knobs, ranges){
	return rangedConfig(knobs, ranges,'around',true);	
}

Configs.linear = function(knobs, ranges){
	return rangedConfig(knobs, ranges,'linear',true);	
}

Configs.custom = function(knobs, configCode){
	 return new Configuration(knobs, configCode);
}

function rangedConfig(knobs, ranges, type, acceptsSingle){
	var knobType = '';
	if(Array.isArray(ranges)){
		if(ranges.length == 0){ 
			throw 'At least one value must be provided to create a(n) '+type+' configuration';
		}
		
	     args = ranges.map(function(range) {
	     	if(range.instance !== undefined){
	    			return range.instance();
	    		}
	    		throw 'when defining a ranged configuration: one of the given arguments is not a RangedKnob: '+range;
		});
	}else{
		if(ranges.instance === undefined){
	    		throw 'when defining a ranged configuration: one of the given arguments is not a RangedKnob: '+ranges;
		}
		args = [ranges.instance()];
	}
     return list2Config(knobs, type,args,acceptsSingle);
}

/*
	Ranged knob is used in the configurations, use this if you intend to create knobs containing multiple parameters (one RangedKnob per knob) and combine them in a configuration
*/
function PrimitiveRange(type, lowerLimit, upperLimit, step, value){
	this.type = primitive2Class(type);
	this.lowerLimit = lowerLimit;
	this.upperLimit = upperLimit;
	this.step = step || 1;
	this.value = value;
	this.descend = undefined;
	this.ascend = undefined;
}

PrimitiveRange.prototype.setClimbers= function(descend, ascend){
	this.descend = descend;
	this.ascend = ascend;
	this.step = undefined;
	return this;
}

PrimitiveRange.prototype.initValue = function(value){
	this.value = value;
	return this;
}

PrimitiveRange.prototype.toConfig = function(){
	var instanceCode = this.instance();
	return Configs.FACTORY+".range("+instanceCode+")";
}

PrimitiveRange.prototype.instance = function(){
	
	if(this.ascend != undefined){
		var newKnob = "new "+ Configs.RANGED +"CustomStep"+this.type+"Knob("+this.lowerLimit+","+this.upperLimit+",";
		if(this.value != undefined){
			newKnob+= this.value+",";
		}
		return newKnob+this.descend+","+this.ascend+")";
	}
	
	var newKnob = "new "+ Configs.RANGED +this.type+"Step("+this.lowerLimit+","+this.upperLimit+",";
	if(this.value != undefined){
		newKnob+= this.value+",";
	}
	return newKnob+this.step+")";
	
}

PrimitiveRange.prototype.declare = function(name){
	return Configs.RANGED+'RangedKnob<'+this.type+'>'+name+' = '+this.instance();
}	

function IntegerRange(lowerLimit, upperLimit, step, value){
	return new PrimitiveRange("Integer", lowerLimit, upperLimit, step, value);
}

function FloatRange(lowerLimit, upperLimit, step, value){
	return new PrimitiveRange("Float", lowerLimit, upperLimit, step, value);
}


/*
IntegerRange.prototype.setClimbers= function(descend, ascend){
	this.descend = descend;
	this.ascend = ascend;
	this.step = undefined;
	return this;
}

IntegerRange.prototype.initValue = function(value){
	this.value = value;
	return this;
}

IntegerRange.prototype.instance = function(){
	
	if(this.ascend != undefined){
		var newKnob = "new "+ Configs.RANGED +"CustomStepIntegerKnob("+this.lowerLimit+","+this.upperLimit+",";
		if(this.value != undefined){
			newKnob+= this.value+",";
		}
		return newKnob+this.descend+","+this.ascend+")";
	}
	
	var newKnob = "new "+ Configs.RANGED +"IntegerStep("+this.lowerLimit+","+this.upperLimit+",";
	if(this.value != undefined){
		newKnob+= this.value+",";
	}
	return newKnob+this.step+")";
	
}

IntegerRange.prototype.declare = function(name){
	return Configs.RANGED+'RangedKnob<'+this.type+'>'+name+' = '+this.instance();
}
/**/