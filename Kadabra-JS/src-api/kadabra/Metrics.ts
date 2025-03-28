import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Body, Case, Field, If, Method, Statement, Type } from "../Joinpoints.js";

export class Metric {
    /**
     * Main function to extract metrics and print the report.
     */
    static main(): void {
        const metrics = this.extract();
        console.log(metrics);
    }

    /**
     * Extracts metrics for the given packages and classes.
     *
     * @param packages - The package pattern to match (default: ".*").
     * @param classes - The class pattern to match (default: ".*").
     * @returns The extracted metrics report.
     */
    static extract(packages: string = ".*", classes: string = ".*"): MetricsReport {
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
            this.mergeReports(report, this.reportType(type));
        }

        return report;
    }

    /**
     * Converts a metrics report into a CSV line.
     *
     * @param report - The metrics report.
     * @returns The CSV line as a string.
     */
    static toCSVLine(report: MetricsReport): string {
        return `${report.numOf.classes},${report.numOf.interfaces},${report.numOf.enums},${report.numOf.methods},${report.numOf.fields}`;
    }

    /**
     * Merges two metrics reports.
     *
     * @param mainReport - The main report to merge into.
     * @param typeReport - The type report to merge.
     */
    static mergeReports(mainReport: MetricsReport, typeReport: TypeReport): void {
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
    static reportType(type: Type): TypeReport {
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
        for (const field of Query.searchFrom(type, Field)) {
            report.numOf.fields++;
        }

        // Count methods and generate method reports
        for (const method of Query.searchFrom(type, Method)) {
            report.numOf.methods++;
            report.methods.push(this.reportMethod(method));
        }

        return report;
    }

    /**
     * Generates a report for a specific method.
     *
     * @param method - The method join point.
     * @returns The method report.
     */
    static reportMethod(method: Method): MethodReport {
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
            this.processBody(body, (stmt: Statement) => {
				report.numOf.statements++;
			});
        }

        return report;
    }

    /**
     * Processes a body join point and applies a function to each statement.
     *
     * @param body - The body join point.
     * @param func - The function to apply to each statement.
     */
    static processBody(body: Body, func: (stmt: Statement) => void): void {
        for (const statement of Query.searchFrom(body, Statement)) {
            this.processStatement(statement, func);
        }
    }

    /**
     * Processes a statement join point and applies a function to it.
     *
     * @param stmt - The statement join point.
     * @param func - The function to apply to the statement.
     */
    static processStatement(stmt: Statement, func: (stmt: Statement) => void): void {
        func(stmt);

        switch (stmt.kind) {
            case "for":
            case "forEach":
				for (const body of Query.searchFrom(stmt, Body)) {
					this.processBody(body, func);
				}
                break;
            case "if":
				for (const ifStmt of Query.searchFrom(stmt, If)) {
					if (ifStmt.then) {
						this.processBody(ifStmt.then, func);
					}
					if (ifStmt.else) {
						this.processBody(ifStmt.else, func);
					}
				}
                break;
            case "switch":
                for (const caseStmt of Query.searchFrom(stmt, Case)) {
                    this.processStatement(caseStmt, func);
                }
                break;
            case "case":
                this.processBody(stmt as Body, func);
                break;
            default:
                break;
        }
    }
}

/**
 * Metrics report type definition.
 */
export interface MetricsReport {
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
export interface TypeReport {
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
export interface MethodReport {
    type: string;
    name: string;
    qualifiedName: string;
    numOf: {
        statements: number;
    };
}