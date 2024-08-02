import lara.code.LoggerBase;
import lara.util.PrintOnce;

/**
 * Redefines print format flags according to Java standard
 */
Logger.prototype.printfFormat[Logger.prototype.Type.LONG] = "%d";


/**
 * Adds code that prints the  message built up to that point with the append() functions.
 *
 * TODO: Improve this comment, add JSDoc tags
 */
Logger.prototype.log = function($jp, insertBefore) {
    
	// .ancestor does not support (yet?) aliased names, so it cannot retrive 'function', even if it exists in the joinpoint model
	if(!this._validateJp($jp, 'executable')) {
		return;
	}

//	if($jp.instanceOf("call") && $jp.name==="<init>"){
//		this._clear();
//		return;
//	}	
	
	var code = this._log_code($jp);
	if (code === undefined) {
        return;
    }

	this._insert($jp, insertBefore, code);
	/*
    //call LoggerInsert($jp, code, insertBefore);
    var insertBeforeString = insertBefore ? "before" : "after";
	
    $jp.insert(insertBeforeString, code);

    // Clear internal state
    this._clear();
	*/
    return this;
}

Logger.prototype._log_code = function($jp) {
	if(this.filename === undefined) {
		return this._log_code_console($jp);
	}
	
	return this._log_code_file($jp);
}

Logger.prototype._log_code_console = function($jp) {
	//println("JP TYPE: " + $jp.joinPointType);

	// Define suffix as ")", since Kadabra 'insert' automatically adds ';',
	// unless it is inserted before/after a join point 'statement'
	//var suffix = $jp.joinPointType === "statement" || $jp.joinPointType === "call" ? ");" : ")";
	
	// Could not understand how Kadabra decides to put a semi-colon in inserted code
	var suffix = undefined;
	//println("CODE TO INSERT: " + this._printfFormat("System.out.printf", undefined, suffix));
	return this._printfFormat("System.out.printf", undefined, suffix);
}

Logger.prototype._log_code_file = function($jp) {
	PrintOnce.message("Weaved code has dependency to project SpecsUtils, which can be found at https://github.com/specs-feup/specs-java-libs");

	//var suffix = $jp.joinPointType === "statement" ? ");" : ")";
	// Could not understand how Kadabra decides to put a semi-colon in inserted code
	//var suffix = undefined;
	
	// This code will be called from inside a function call
	var code = this._printfFormat("String.format", undefined, ")");
	
	code = "pt.up.fe.specs.util.SpecsIo.append(new java.io.File(\"" + this.filename + "\"), " + code + ");";
		
	return code;
}