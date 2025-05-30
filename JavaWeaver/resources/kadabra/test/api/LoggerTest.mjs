import Logger from "@specs-feup/kadabra/api/lara/code/Logger.js";
import Query from "@specs-feup/lara/api/weaver/Query.js";

const loggerConsole = new Logger();
const loggerFile = new Logger(false, "log.txt");

for (const call of Query.search("type").search("executable").search("call")) {
    loggerConsole
        .append("Print double ")
        .appendDouble(2)
        .append(" after " + call.name)
        .ln();

    loggerConsole.log(call, true);

    loggerConsole.append("Printing again").ln();
    loggerConsole.log(call);

    loggerFile.append("Logging to a file").ln();
    loggerFile.log(call, true);

    loggerFile.append("Logging again to a file").ln();
    loggerFile.log(call);
}

const appendLogger = new Logger().appendLong("10l");

for (const declaration of Query.search("method", "testAppend").search(
    "localVariable",
    "a"
)) {
    appendLogger.log(declaration);
}

for (const testClass of Query.search("class")) {
    console.log(testClass.srcCode);
}
