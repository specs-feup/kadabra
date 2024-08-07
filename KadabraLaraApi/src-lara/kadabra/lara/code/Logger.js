laraImport("lara.code.LoggerBase");
laraImport("lara.util.PrintOnce");

class Logger extends LoggerBase {
    constructor(isGlobal = false, filename = undefined) {
		super(isGlobal, filename);

        /**
         * Redefines print format flags according to Java standard
         */
        this.printfFormat[this.Type.get("LONG")] = "%d";
    }

    /**
     * Adds code that prints the  message built up to that point with the append() functions.
     *
     * TODO: Improve this comment, add JSDoc tags
     */
    log($jp, insertBefore) {
        // .ancestor does not support (yet?) aliased names, so it cannot retrive 'function', even if it exists in the joinpoint model
        if (!this._validateJp($jp, "executable")) {
            return;
        }

        const code = this._log_code($jp);
        if (code === undefined) {
            return;
        }

        this._insert($jp, insertBefore, code);

        return this;
    }

    _log_code($jp) {
        if (this.filename === undefined) {
            return this._log_code_console($jp);
        }

        return this._log_code_file($jp);
    }

    _log_code_console($jp) {
        // Define suffix as ")", since Kadabra 'insert' automatically adds ';',
        // unless it is inserted before/after a join point 'statement'
        //var suffix = $jp.joinPointType === "statement" || $jp.joinPointType === "call" ? ");" : ")";

        // Could not understand how Kadabra decides to put a semi-colon in inserted code
        const suffix = undefined;
        return this._printfFormat("System.out.printf", undefined, suffix);
    }

    _log_code_file($jp) {
        PrintOnce.message(
            "Weaved code has dependency to project SpecsUtils, which can be found at https://github.com/specs-feup/specs-java-libs"
        );

        //var suffix = $jp.joinPointType === "statement" ? ");" : ")";
        // Could not understand how Kadabra decides to put a semi-colon in inserted code
        //var suffix = undefined;

        // This code will be called from inside a function call
        let code = this._printfFormat("String.format", undefined, ")");

        code =
            'pt.up.fe.specs.util.SpecsIo.append(new java.io.File("' +
            this.filename +
            '"), ' +
            code +
            ");";

        return code;
    }
}
