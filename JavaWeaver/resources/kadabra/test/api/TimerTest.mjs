laraImport("lara.code.Timer");
laraImport("lara.code.Logger");
import Query from "@specs-feup/lara/api/weaver/Query.js";

// Instrument call to 'Calculate'
const timer = new Timer();

for (const call of Query.search("type").search("executable").search("call")) {
    if (call.name !== "bar" && call.name !== "foo") {
        continue;
    }

    timer.time(call, "Time:");
}

// Disable printing result
timer.setPrint(false);

for (const call of Query.search("type")
    .search("executable")
    .search("call", "bar2")) {
    const bar2TimeVar = timer.time(call);
    const logger = new Logger();
    logger
        .text("I want to print the value of the elapsed time (")
        .double(bar2TimeVar)
        .text(
            "), which is in the unit '" +
                timer.getUnit().getUnitsString() +
                "' and put other stuff after it"
        )
        .ln()
        .log(timer.getAfterJp());
}

// Enable printing again
timer.setPrint(true);

for (const call of Query.search("type")
    .search("executable")
    .search("call", "bar3")) {
    timer.time(call);
    const logger = new Logger();
    logger
        .text("This should appear after the timer print")
        .ln()
        .log(timer.getAfterJp());
}

for (const file of Query.search("file")) {
    console.log(file.srcCode);
}
