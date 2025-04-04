import Query from "@specs-feup/lara/api/weaver/Query.js";
import { object2string } from "@specs-feup/lara/api/core/output.js";
import { Body, Case, Field, If, Method, Statement, Type } from "../Joinpoints.js";

/**
 * Main function to extract metrics and print the report.
 */
export default function main(): void {
    const metrics = extract();
    console.log(object2string(metrics));
}

/**
 * Extracts metrics for the given packages and classes.
 *
 * @returns The extracted metrics report.
 */
function extract(): MetricsReport {
    const report: MetricsReport = {
        types: {},
        numOf: {
            classes: 0,
            interfaces: 0,
            enums: 0,
            methods: 0,
            fields: 0,
        },
    };

    // Iterate over all types and process their metrics
    for (const type of Query.search(Type)) {
        mergeReports(report, reportType(type));
    }
    return report;
}

/**
 * Merges two metrics reports.
 *
 * @param mainReport - The main report to merge into.
 * @param typeReport - The type report to merge.
 */
function mergeReports(mainReport: MetricsReport, typeReport: TypeReport): void {
    mainReport.types[typeReport.qualifiedName] = typeReport;
    mainReport.numOf.methods += typeReport.numOf.methods;
    mainReport.numOf.fields += typeReport.numOf.fields;

    switch (typeReport.type) {
        case "class":
            mainReport.numOf.classes++;
            break;
        case "interface":
            mainReport.numOf.interfaces++;
            break;
        case "enum":
            mainReport.numOf.enums++;
            break;
        default:
            console.warn(`Unknown type: ${typeReport.type}`);
            break;
    }
}

/**
 * Generates a report for a specific type.
 *
 * @param type - The type join point.
 * @returns The type report.
 */
function reportType(type: Type): TypeReport {
    const report: TypeReport = {
        type: type.joinPointType,
        name: type.name,
        qualifiedName: type.qualifiedName,
        numOf: {
            methods: 0,
            fields: 0,
        },
        methods: [],
    };

    // Count fields
    report.numOf.fields += Query.searchFrom(type, Field).get().length;

    // Count methods and generate method reports
    for (const method of Query.searchFrom(type, Method)) {
        report.numOf.methods++;
        report.methods.push(reportMethod(method));
    }

    return report;
}

/**
 * Generates a report for a specific method.
 *
 * @param method - The method join point.
 * @returns The method report.
 */
function reportMethod(method: Method): MethodReport {
    const report: MethodReport = {
        type: method.returnType,
        name: method.name,
        qualifiedName: method.toQualifiedReference,
        numOf: {
            statements: 0,
        },
    };

    // Process the method body
    for (const body of Query.searchFrom(method, Body)) {
        processBody(body, () => {report.numOf.statements++;});
    }

    return report;
}

/**
 * Processes a body join point and applies a function to each statement.
 *
 * @param body - The body join point.
 * @param func - The function to apply to each statement.
 */
function processBody(body: Body, func: () => void): void {
    for (const statement of Query.searchFrom(body, Statement)) {
        processStatement(statement, func);
    }
}

/**
 * Processes a statement join point and applies a function to it.
 *
 * @param stmt - The statement join point.
 * @param func - The function to apply to the statement.
 */
function processStatement(stmt: Statement, func: () => void): void {
    func();

    switch (stmt.kind) {
        case "for":
        case "forEach":
            for (const body of Query.searchFrom(stmt, Body)) {
                processBody(body, func);
            }
            break;
        case "if":
            for (const ifStmt of Query.searchFrom(stmt, If)) {
                if (ifStmt.then) {
                    processBody(ifStmt.then, func);
                }
                if (ifStmt.else) {
                    processBody(ifStmt.else, func);
                }
            }
            break;
        case "switch":
            for (const caseStmt of Query.searchFrom(stmt, Case)) {
                processStatement(caseStmt, func);
            }
            break;
        case "case":
            processBody(stmt as Body, func);
            break;
        default:
            break;
    }
}

/**
 * Metrics report type definition.
 */
interface MetricsReport {
    types: Record<string, TypeReport>;
    numOf: {
        classes: number;
        interfaces: number;
        enums: number;
        methods: number;
        fields: number;
    };
}

/**
 * Type report type definition.
 */
interface TypeReport {
    type: string;
    name: string;
    qualifiedName: string;
    numOf: {
        methods: number;
        fields: number;
    };
    methods: MethodReport[];
}

/**
 * Method report type definition.
 */
interface MethodReport {
    type: string;
    name: string;
    qualifiedName: string;
    numOf: {
        statements: number;
    };
}