import PrintOnce from "@specs-feup/lara/api/lara/util/PrintOnce.js";
import LoggerBase from "@specs-feup/lara/api/lara/code/LoggerBase.js";
import { Joinpoint } from "../Joinpoints.js";

export default class Logger extends LoggerBase<Joinpoint> {
    constructor(isGlobal: boolean = false, filename?: string) {
        super(isGlobal, filename);

        const formatType = this.Type.get("LONG");
        if (formatType === undefined) {
            throw new Error("Could not find LONG format type");
        }

        /**
         * Redefines print format flags according to Java standard
         */
        this.printfFormat[formatType] = "%d";
    }

    /**
     * Adds code that prints the  message built up to that point with the append() functions.
     *
     * TODO: Improve this comment, add JSDoc tags
     */
    log($jp: Joinpoint, insertBefore: boolean = false) {
        // .ancestor does not support (yet?) aliased names, so it cannot retrive 'function', even if it exists in the joinpoint model
        if (!this._validateJp($jp, "executable")) {
            return;
        }

        const code = this._log_code();
        if (code === undefined) {
            return;
        }

        this._insert($jp, insertBefore, code);

        return this;
    }

    _log_code() {
        if (this.filename === undefined) {
            return this._log_code_console();
        }

        return this._log_code_file();
    }

    _log_code_console() {
        // Define suffix as ")", since Kadabra 'insert' automatically adds ';',
        // unless it is inserted before/after a join point 'statement'
        //var suffix = $jp.joinPointType === "statement" || $jp.joinPointType === "call" ? ");" : ")";

        // Could not understand how Kadabra decides to put a semi-colon in inserted code
        const suffix = undefined;
        return this._printfFormat("System.out.printf", undefined, suffix);
    }

    _log_code_file() {
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
