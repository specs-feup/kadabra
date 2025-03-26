import Query from "@specs-feup/lara/api/weaver/Query.js";
import KadabraJavaTypes from "./KadabraJavaTypes.js";
import { Assignment, Class, Field, LocalVariable } from "../Joinpoints.js";
import { LaraJoinPoint } from "@specs-feup/lara/api/LaraJoinPoint.js";

/**
 * Utility methods related to searching join points and AST properties.
 */
export class KadabraAst {
    private static _BINARY_OP_SET: Set<string> | undefined;
    private static _UNARY_OP_SET: Set<string> | undefined;

    /**
     * Searches constant values. Currently, three types of constants are returned:
     * - Class fields which are final and initialized when declared;
     * - Assignments to final fields;
     * - Local variables which are final and initialized when declared.
     *
     * @param startingPoint - The join point where the search for constants will start (inclusive).
     * @returns An array of join points, which can either be a field, an assignment, or a local variable.
     */
    static getConstantInitializations(startingPoint?: LaraJoinPoint): (Field | Assignment | LocalVariable)[] {
        if (!startingPoint) {
            startingPoint = Query.root();
        }

        const constants: (Field | Assignment | LocalVariable)[] = [];

        // Search for final fields with initializations
        for (const field of Query.searchFromInclusive(startingPoint, Field)) {
            if (field.init !== undefined && field.isFinal) {
                constants.push(field);
            }
        }

        // Search for assignments to final fields
        for (const assign of Query.searchFromInclusive(startingPoint, Assignment)) {
            if (assign.lhs.isFinal) {
                constants.push(assign);
            }
        }

        // Search for final local variables with initializations
        for (const localVar of Query.searchFromInclusive(startingPoint, LocalVariable)) {
            if (localVar.init !== undefined && localVar.isFinal) {
                constants.push(localVar);
            }
        }
        return constants;
    }

    /**
     * Retrieves a set of valid binary operators.
     *
     * @returns A Set<string> with valid binary operators.
     */
    static getBinaryOperators(): Set<string> {
        if (!this._BINARY_OP_SET) {
            this._BINARY_OP_SET = new Set<string>();
            for (const binaryOp of KadabraJavaTypes.OperatorUtils.getValidBinaryStrings()) {
                this._BINARY_OP_SET.add(binaryOp);
            }
        }
        return this._BINARY_OP_SET;
    }

    /**
     * Retrieves a set of valid unary operators.
     *
     * @returns A Set<string> with valid unary operators.
     */
    static getUnaryOperators(): Set<string> {
        if (!this._UNARY_OP_SET) {
            this._UNARY_OP_SET = new Set<string>();
            for (const unaryOp of KadabraJavaTypes.OperatorUtils.getValidUnaryStrings()) {
                this._UNARY_OP_SET.add(unaryOp);
            }
        }
        return this._UNARY_OP_SET;
    }

    /**
     * Creates an object representing the class hierarchy of the current program.
     *
     * @returns A map where the keys are strings with the fully qualified name of the class,
     * and the value is an array with class join points that are subclasses of the class represented by the key.
     * If the map returns undefined, this means that the class has no subclasses.
     */
    static getHierarchy(): Record<string, Class[]> {
        const hierarchy: Record<string, Class[]> = {};

        for (const cls of Query.search(Class)) {
            const superClass = cls.superClass;
			let subClasses = hierarchy[superClass];

			if (subClasses === undefined) {
				subClasses = [];
				hierarchy[superClass] = subClasses;
			}
			subClasses.push(cls);
        }

        return hierarchy;
    }
}
